package com.camunda.myBPM;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.TaskAssert;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;



public class ProcessUnitTest {
    @ClassRule
    @Rule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    private static final String PROCESS_DEFINITION_KEY = "BPMN_deployment";

    static {
        LogFactory.useSlf4jLogging(); // Set up logging
    }

    // Set up the Fixture that will run before each test
    @Before
    public void setup() {
        init(rule.getProcessEngine());
    }

    /**
     * Testing if the process definition is deployable.
     */
    @Test
    @Deployment(resources = "process.bpmn")
    public void testParsingAndDeployment() {
        // nothing is done here, as we just want to check for exceptions during
        // deployment
    }
    @Test
    @Deployment(resources = "process.bpmn")
    public void testCurrentStatus() {

        // Obtain test run of BPMN
        ProcessInstanceWithVariables processInstance =
                (ProcessInstanceWithVariables) processEngine()
                        .getRuntimeService()
                        .startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

        // Obtain the value of the weatherOK variable
        int creditScore = (int) processInstance.getVariables()
                .get("creditScore");
        System.out.println("creditScore: " + creditScore);

        // Obtain a reference to the current task
        TaskAssert task = assertThat(processInstance).task();

        if (creditScore < 400) {
            assertThat(processInstance)
                    .isWaitingAt("Activity_1y9vtcf");
            task.hasName("Inform customer");
        } else if (creditScore >= 400 && creditScore <600){
            assertThat(processInstance)
                    .isWaitingAt("Activity_0k6byvc");
            task.hasName("Call credit assessment");

        } else if (creditScore >= 600) {
            assertThat(processInstance).isWaitingAt("Activity_10a8ean");
            task.hasName("Sell phone");
        }
        task.isNotAssigned();

    }
    @Test
    @Deployment(resources = "process.bpmn")
    public void testCompletionTask() {

        // Obtain test run of BPMN
        ProcessInstanceWithVariables processInstance =
                (ProcessInstanceWithVariables) processEngine()
                        .getRuntimeService()
                        .startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

        // Obtain a reference to the current task
        TaskAssert taskAssert =
                assertThat(processInstance).task();
        int creditScore = (int) processInstance.getVariables()
                .get("creditScore");
        System.out.println("creditScore: " + creditScore);

        boolean creditRefer = (boolean) processInstance.getVariables().get("creditRefer");
        System.out.println("creditRefer: " + creditRefer);

        TaskEntity task = (TaskEntity)taskAssert.getActual();
        task.delegate("user");

        task.resolve();

        if (creditScore >= 400 && creditScore < 600) {
            TaskEntity secondTask = (TaskEntity)taskAssert.getActual();
            secondTask.delegate("user");

            secondTask.resolve();
        }

        assertThat(processInstance.isEnded());

    }


}
