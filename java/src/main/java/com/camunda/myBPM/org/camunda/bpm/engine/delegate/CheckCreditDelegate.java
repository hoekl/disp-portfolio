package com.camunda.myBPM.org.camunda.bpm.engine.delegate;
import java.util.Random;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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
