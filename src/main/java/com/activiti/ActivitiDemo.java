package com.activiti;


import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class ActivitiDemo {

    @Test
    public void testDeloyment() {
        //1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3、使用service进行流程部署，定义一个流程的名字，把bpmn和png部署到数据中
        Deployment deploy = repositoryService.createDeployment()
                .name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();
        //4、输出部署信息
        System.out.println("流程部署id=" + deploy.getId());
        System.out.println("流程部署名字=" + deploy.getName());
    }

    @Test
    public void testStartProcess() {
        //1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取runtimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3、根据流程定义的id启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myEvection");
        //4、输出内容
        System.out.println("流程定义id:" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id:" + processInstance.getProcessInstanceId());
        System.out.println("当前活动id:" + processInstance.getActivityId());
    }

    @Test
    public void testFindPersonTaskList() {
        //1、创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取任务service
        TaskService taskService = processEngine.getTaskService();
        //3、根据key 和任务负责人查询任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("zhangsan")
                .list();
        //4、输出
        for (Task task : list) {
            System.out.println("流程实例id=" + task.getProcessInstanceId());
            System.out.println("任务id=" + task.getId());
            System.out.println("任务负责人=" + task.getAssignee());
            System.out.println("任务名称=" + task.getName());
        }
    }

    @Test
    public void completTask01() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("2505");
    }

    @Test
    public void completTask02() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        // taskService.complete("2505");
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("jerry")
                .singleResult();
        System.out.println("流程实例id=" + task.getProcessInstanceId());
        System.out.println("任务id=" + task.getId());
        System.out.println("任务负责人=" + task.getAssignee());
        System.out.println("任务名称=" + task.getName());
        taskService.complete(task.getId());
    }

    @Test
    public void completTask03() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        // taskService.complete("2505");
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("linda")
                .singleResult();
        System.out.println("流程实例id=" + task.getProcessInstanceId());
        System.out.println("任务id=" + task.getId());
        System.out.println("任务负责人=" + task.getAssignee());
        System.out.println("任务名称=" + task.getName());
        taskService.complete(task.getId());
    }

    @Test
    public void completTask04() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        // taskService.complete("2505");
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("linda")
                .singleResult();
        System.out.println("流程实例id=" + task.getProcessInstanceId());
        System.out.println("任务id=" + task.getId());
        System.out.println("任务负责人=" + task.getAssignee());
        System.out.println("任务名称=" + task.getName());
        taskService.complete(task.getId());
    }

    @Test
    public void deployProcessByZip() {
        //打包部署 1、获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //读取资源包文件，构造成inputStream
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
        // 用inputStrem 构造成ZipInputStream
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //使用压缩包的包进行部署流程
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("部署流程id：" + deploy.getId());
        System.out.println("部署流程名称：" + deploy.getName());
    }

    @Test
    public void queryProcessDefinition() {
//        获取引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        获取ProcessDefinitionQuery对象
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
//        查询当前所有的流程定义 返回流程定义集合
        //desc倒叙
        //list查询出所有信息
        List<ProcessDefinition> myEvection = query.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
//        输出信息
        for (ProcessDefinition processDefinition : myEvection) {
            System.out.println("流程定义id:" + processDefinition.getId());
            System.out.println("流程定义名称:"+processDefinition.getName());
            System.out.println("流程定义key:"+processDefinition.getKey());
            System.out.println("流程定义版本:"+processDefinition.getVersion());
        }
    }

    /**
     * 删除流程部署信息
     */
    @Test
    public void deleteDeloyment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String delopmentId = "1";
        repositoryService.deleteDeployment(delopmentId);
    }
}
