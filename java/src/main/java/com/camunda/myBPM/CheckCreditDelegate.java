package com.camunda.myBPM;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;
public class CheckCreditDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int min = 200;
        int max = 1000;
        Random rand = new Random();
        execution.setVariable("creditScore", rand.nextInt(max - min) + min);
        execution.setVariable("creditRefer", rand.nextBoolean());

    }
}
