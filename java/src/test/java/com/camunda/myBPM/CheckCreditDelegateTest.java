package com.camunda.myBPM;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.camunda.myBPM.org.camunda.bpm.engine.delegate.CheckCreditDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.Test;

public class CheckCreditDelegateTest {
    @Test
    public void verifySetVariable() {
        CheckCreditDelegate delegate = new CheckCreditDelegate();

        DelegateExecution mockExecution = mock(DelegateExecution.class);

        try {
            delegate.execute((mockExecution));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("throws exception: " + e.getMessage());
        }
        verify(mockExecution, times(1)).setVariable(eq("creditScore"), any(Integer.class));
        verify(mockExecution, times(1)).setVariable(eq("creditRefer"), any(Boolean.class));
    }
}
