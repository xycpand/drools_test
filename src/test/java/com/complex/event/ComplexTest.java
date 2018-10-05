package com.complex.event;

import entity.Person;
import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by lcc on 2018/10/4.
 */
public class ComplexTest {

    /**
     * 测试点：复杂事件处理
     *
     * 运行结果：
     *
     * ---age---30
     * ---age---10
     * 总执行了2条规则
     */
    @Test
    public void complex2() throws Exception{
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks = kc.newKieSession("complex2");
        ks.addEventListener( new DebugAgendaEventListener() );
        ks.addEventListener( new DebugRuleRuntimeEventListener() );
        Person person1  =   new Person(1,"张三",10);
        Person person2  =   new Person(2,"张三",30);
        ks.insert(person1);
//        Thread.sleep(4000);
        ks.insert(person2);
        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }

}
