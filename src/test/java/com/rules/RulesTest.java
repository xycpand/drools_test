package com.rules;

import entity.*;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.time.impl.PseudoClockScheduler;
import org.junit.Test;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.definition.KiePackage;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    public void onLoopTest4() {
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
        KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        kbConf.setProperty( "org.drools.sequential", "true");
        InternalKnowledgeBase kbase= KnowledgeBaseFactory.newKnowledgeBase(kbConf);

        /** 特别要注意 rule的存放位置  */
        String RULES_PATH = "rules/";
// classpath*:" + RULES_PATH + "**/*.*",
        KieServices kss = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kss.newKieFileSystem();
//        kieFileSystem.write(ResourceFactory.newClassPathResource("SchoolRule.drl",RulesTest.class), ResourceType.DRL);

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
                .newKnowledgeBuilder(); kbuilder.add(ResourceFactory.newClassPathResource("SchoolRule.drl",
                RulesTest.class), ResourceType.DRL);

        KieRepository kieRepository = kss.getRepository();
        kieRepository.addKieModule(new KieModule() {
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });

        KieBuilder kieBuilder = kss.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        KieContainer kc = kss.newKieContainer(kieRepository.getDefaultReleaseId());


//        KieContainer kc = kss.getKieClasspathContainer();
//
//
        KieSession ks =kc.newKieSession("test2");
//
//        ks.addEventListener( new DebugAgendaEventListener() );
//        ks.addEventListener( new DebugRuleRuntimeEventListener() );
//
//
        Person person=new Person("张三",30);
        FactHandle insert = ks.insert(person);

        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }


    /**
     * 测试点：运行指定的规则
     */
    @Test
    public void concurrent1() throws Exception{
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        String filePath = RulesTest.class.getClassLoader().getResource("com/rules/Matches.drl").getPath();
        File file = new File(URLDecoder.decode(filePath,"utf-8"));
        kbuilder.add(ResourceFactory.newFileResource(file), ResourceType.DRL);
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        Collection<KiePackage> ss = kbuilder.getKnowledgePackages();
        kbase.addPackages(ss);
        StatelessKieSession ksession = kbase.newStatelessKieSession();
        Person person=new Person("张三",30);
        ksession.execute(person);

    }


    /**
     * 测试点：复杂事件处理
     *
     * 运行结果：
     *
     * ---age---30
     * 总执行了1条规则
     */
    @Test
    public void complex1() throws Exception{
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks =kc.newKieSession("test4");
//        ks.addEventListener( new DebugAgendaEventListener() );
//        ks.addEventListener( new DebugRuleRuntimeEventListener() );
        Person person1  =   new Person(1,"张三",10);
        Person person2  =   new Person(2,"张三",30);
        ks.insert(person1);
        Thread.sleep(4000);
        ks.insert(person2);
        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }


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
        KieSession ks =kc.newKieSession("test4");
//        ks.addEventListener( new DebugAgendaEventListener() );
//        ks.addEventListener( new DebugRuleRuntimeEventListener() );
        Person person1  =   new Person(1,"张三",10);
        Person person2  =   new Person(2,"张三",30);
        ks.insert(person1);
//        Thread.sleep(4000);
        ks.insert(person2);
        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }

    @Test
    public void duration1() throws Exception{
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks =kc.newKieSession("test4");
        Ping person1  =   new Ping(1,10L);
        Ping person2  =   new Ping(2,30L);
        ks.insert(person1);
        ks.insert(person2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ks.fireUntilHalt();
            }
        }).start();
        synchronized (this){
            this.wait(4000);
        }
        ks.halt();
        ks.dispose();

    }



    @Test
    public void ping1() throws Exception{
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks =kc.newKieSession("test4");
        Date date = new Date();
        long aa = date.getTime();

        for(int i= 0;i<30;i++){
            Timestamp datetime  = new Timestamp(aa+i*1000);
            int count = 10;
            if(10 < i && i<20){
                count = count + i;
            }else {
                count = 10;
            }
            Ping person1  =   new Ping(i,count,datetime);
//            System.out.println(person1);
            ks.insert(person1);
        }
        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();

    }


    /**
     * 测试点：定时器
     *
     * @throws InterruptedException
     */
    @Test
    public void timerTest() throws InterruptedException {

        final KieSession kieSession = createKnowledgeSession();
        ResultEvent event = new ResultEvent();
        kieSession.setGlobal("event", event);

        final Server server = new Server(1);

        new Thread(new Runnable() {
            public void run() {
                kieSession.fireUntilHalt();
            }
        }).start();

        FactHandle serverHandle = kieSession.insert(server);

        for (int i = 8; i <= 15; i++) {
            Thread.sleep(2000);
            server.setTimes(++i);
            kieSession.update(serverHandle, server);
        }

        Thread.sleep(3000);
        kieSession.halt();
        System.out.println(event.getEvents());
    }

    private KieSession createKnowledgeSession() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kSession = kieContainer.newKieSession("time_check");
        return kSession;
    }



    /**
     * 测试点：包丢失
     *
     * @throws InterruptedException
     */
    @Test
    public void loosePackageTest() throws InterruptedException {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-rules1");
//        List<BagScannedEvent> events = FactsLoader.loadEvents();
//        events.stream().forEach(event -> { insertAndFire(kieSession, event);});


    }

    private static void insertAndFire(KieSession kieSession, BagScannedEvent event) {

//        System.out.println(event);
        PseudoClockScheduler clock = kieSession.getSessionClock();
        kieSession.insert(event);
        long deltaTime = event.getTimestamp().getTime() - clock.getCurrentTime();
        if (deltaTime > 0) {
//            System.out.println("Advancing clock with: " + deltaTime);
            clock.advanceTime(deltaTime, TimeUnit.MILLISECONDS);
        }
        kieSession.fireAllRules();

        System.out.println();
    }









}
