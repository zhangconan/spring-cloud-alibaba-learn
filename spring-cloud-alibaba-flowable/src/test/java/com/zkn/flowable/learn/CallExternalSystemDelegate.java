package com.zkn.flowable.learn;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-05-21 15:47
 * @classname CallExternalSystemDelegate
 */
public class CallExternalSystemDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        
        System.out.println("Calling the external system for employee " + execution.getVariable("employee"));
    }
}
