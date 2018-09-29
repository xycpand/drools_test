package com.rules;

import com.secbro.drools.model.Product;
import entity.Person;
import entity.School;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

/**
 * Created by lcc on 2018/9/28.
 */
public class RulesTest {


    /**
     * 测试博客上的 no-loop 属性 true
     */
    @Test
    public void onLoopTest1() {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks =kc.newKieSession("test1");

        ks.addEventListener( new DebugAgendaEventListener() );
        ks.addEventListener( new DebugRuleRuntimeEventListener() );


        School school=new School();
        school.setCount(50);
        school.setName("一班");
        ks.insert(school);


        Person person=new Person("一班",30);
        FactHandle insert = ks.insert(person);

        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }

    @Test
    public void onLoopTest2() {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();

        KieSession ks =kc.newKieSession("test2");

        ks.addEventListener( new DebugAgendaEventListener() );
        ks.addEventListener( new DebugRuleRuntimeEventListener() );


        Person person=new Person("张三",30);
        FactHandle insert = ks.insert(person);

        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }

    @Test
    public void onLoopTest3() {

        KnowledgeBuilder kbuilder= KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("test.drl", Test.class), ResourceType.DRL);
//        Collection<KnowledgePackage> kpackage=kbuilder.getKnowledgePackages();//产生规则包的集合
//
//        KieServices kss = KieServices.Factory.get();
//        KieContainer kc = kss.getKieClasspathContainer();
//
//
//        KieSession ks =kc.newKieSession("test2");
//
//        ks.addEventListener( new DebugAgendaEventListener() );
//        ks.addEventListener( new DebugRuleRuntimeEventListener() );
//
//
//        Person person=new Person("张三",30);
//        FactHandle insert = ks.insert(person);
//
//        int count = ks.fireAllRules();
//        System.out.println("总执行了"+count+"条规则");
//        ks.dispose();

    }




}
