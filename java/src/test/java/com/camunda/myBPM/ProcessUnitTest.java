package com.camunda.myBPM;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.init;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
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


}
