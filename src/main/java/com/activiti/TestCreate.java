package com.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

public class TestCreate {
    @Test
    public void testCreateTable(){
        //1、默认创建方式
       ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
        //2、使用自定义方式---配置文件名字可以自定义--bean 的名字也可以自定义
   //     ProcessEngineConfiguration processEngineConfigurationFromResource = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
     //   ProcessEngine processEngine = processEngineConfigurationFromResource.buildProcessEngine();
     //   System.out.println(processEngine);
    }
}
