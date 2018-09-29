package com.rules;

import entity.Customer;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class SoftDrink {

    @Test
    public void onLoopTest2() {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();

        KieSession ks =kc.newKieSession("test3");

//        ks.addEventListener( new DebugAgendaEventListener() );
//        ks.addEventListener( new DebugRuleRuntimeEventListener() );

        Customer customer=new Customer("小黄", 50, 0, 0);
        FactHandle insert = ks.insert(customer);

        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }


}