package com.zkn.flowable.learn;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author conanzhang@木森
 * @description processes 节点
 * @date 2020-05-20 19:27
 * @classname MySqlFlowableApplication
 */
public class H2FlowableApplication {

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/zkn_flowable?characterEncoding=UTF-8")
                .setJdbcUsername("root")
                .setJdbcPassword("zkn123456")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //获取flowable引擎
        ProcessEngine processEngine = cfg.buildProcessEngine();
        //获取bmps部署
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //部署flowable
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("flowable/holiday-request.bpmn20.xml").deploy();
        //获取工作流信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println("processDefinition name:" + processDefinition.getName());
        //获取工作流运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //工作流中用到的定义变量
        Map<String, Object> varMap = new HashMap<>(4);
        varMap.put("employee", "张三");
        varMap.put("nrOfHolidays", 2);
        varMap.put("description", "请年假");
        //工作流运行时实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", varMap);
        System.out.println("processInstance:" + processInstance.getId());

        //获取任务查询服务
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + taskList.size() + " tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + ") " + taskList.get(i).getName());
        }
        //获取具体的某一个任务
        Task task = taskList.get(0);
        System.out.println("taskInfo:" + task.toString());
        varMap = new HashMap<>(2);
        varMap.put("approved", true);
        //结束任务
        taskService.complete(task.getId(), varMap);
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId())
                .finished().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance activityInstance : activityInstances) {
            System.out.println(activityInstance.getActivityId() + " took " + activityInstance.getDurationInMillis() + " milliseconds");
        }

    }
}
