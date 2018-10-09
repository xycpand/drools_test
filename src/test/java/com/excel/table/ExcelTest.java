package com.excel.table;

import entity.Person;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.util.Properties;


/**
 * Created by lcc on 2018/10/8.
 */
public class ExcelTest {


    /**
     * 测试点：决策表的使用
     */
    @Test
    public  void execlDirectTest() {
        DecisionTableConfiguration dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        dtableconfiguration.setInputType(DecisionTableInputType.XLSX);//枚举  表示执行的是xls，当然还有一种csv的
        String filePath = ExcelTest.class.getClassLoader().getResource("com/drools/excel/table/test1/personAge.xlsx").getPath();
        System.out.println("文件路径"+filePath);
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Resource resource = ResourceFactory      //add方法  *添加一个给定的资源类型的资源,使用ResourceConfiguration提供
                .newClassPathResource(filePath,  //找到指定目录的xls文件，
                        ExcelTest.class);
        kbuilder.add(resource, ResourceType.DTABLE, dtableconfiguration);   //xls的标识
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();//获取base实现方法
        kbase.addPackages(kbuilder.getKnowledgePackages());   //将集合添加到KnowledgeBase中
        StatelessKieSession ksession = kbase.newStatelessKieSession();//通过base获取 kession 实现
        Person person=new Person("张三",30);
        ksession.execute(person);
    }



    /**
     * 测试点：决策表 先翻译成drl 后执行
     */
    @Test
    public  void excel1() {
//        //把excel翻译成drl文件
//        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
//        String drl = compiler.compile(ResourceFactory.newClassPathResource(RULES_PATH + File.separator + "rule.xls", "UTF-8"), "rule-table");
//        System.out.println(drl);
//        Long start = System.currentTimeMillis();        //执行决策表
//        try {
//            // load up the knowledge base
//            //
//              KieServices ks = KieServices.Factory.get();
//              KieContainer kContainer = ks.getKieClasspathContainer();
//              KieSession kSession = kContainer.newKieSession("tablesKiession");
//              Person person = new Person();
//              person.setName("Tony");
//              kSession.insert(person);
//              kSession.fireAllRules();
//              kSession.dispose();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//        System.out.println("COST:"+String.valueOf(System.currentTimeMillis()-start));



    }



    /**
     * 测试点：决策表的使用
     */
    @Test
    public  void excel2() {

        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession( "exceltest" );

        Person p1 = new Person("奥巴马", 68);
        Person p2 = new Person("普京", 32);
        Person p3 = new Person("朴槿惠", 18);
        Person p4 = new Person("川普", 10);
        Person p5 = new Person("金正恩", 66);

        System.out.println("before p1 : " + p1);
        System.out.println("before p2 : " + p2);
        System.out.println("before p3 : " + p3);
        System.out.println("before p4 : " + p4);
        System.out.println("before p5 : " + p5);
        kieSession.insert(p1);
        kieSession.insert(p2);
        kieSession.insert(p3);
        kieSession.insert(p4);
        kieSession.insert(p5);

        int count = kieSession.fireAllRules();

        System.out.println("总执行了" + count + "条规则------------------------------");
        System.out.println("after p1 : " + p1);
        System.out.println("after p2 : " + p2);
        System.out.println("after p3 : " + p3);
        System.out.println("after p4 : " + p4);
        System.out.println("after p4 : " + p5);
        kieSession.dispose();

    }




}
