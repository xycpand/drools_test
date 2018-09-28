package com.drools.attribute.agendaGroup;

import com.secbro.drools.model.Product;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by lcc on 2018/9/28.
 */
public class TestAgendaGroup {

    /**
     * 测试点：agenda-group  只有一个   而且没有 focus
     *
     * 其他规则被触发
     * 命中了1条规则！
     * 商品GOLD的商品折扣为10%。
     *
     */
    @Test
    public void onLoopTest0() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("agendaGroup1-session");

        Product product = new Product();
        product.setType(Product.GOLD);
        product.setDiscount(10);

        kSession.insert(product);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        System.out.println("商品" +product.getType() + "的商品折扣为" + product.getDiscount() + "%。");

        kSession.dispose();

    }

    /**
     *  测试点：agenda-group  只有一个   而且有 focus
     *
     *  规则test agenda-group 1被触发
     *  其他规则被触发
     *  命中了2条规则！
     *  商品GOLD的商品折扣为10%。
     */
    @Test
    public void onLoopTest1() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("agendaGroup1-session");
        kSession.getAgenda().getAgendaGroup("abc").setFocus();

        Product product = new Product();
        product.setType(Product.GOLD);
        product.setDiscount(10);

        kSession.insert(product);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        System.out.println("商品" +product.getType() + "的商品折扣为" + product.getDiscount() + "%。");

        kSession.dispose();

    }


    /**
     * 测试点：agenda-group  有2个   而且没有 focus
     *
     * 其他规则被触发
     * 命中了1条规则！
     * 商品GOLD的商品折扣为10%。
     */
    @Test
    public void onLoopTest2() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("agendaGroup2-session");
//        kSession.getAgenda().getAgendaGroup("abc").setFocus();

        Product product = new Product();
        product.setType(Product.GOLD);
        product.setDiscount(10);

        kSession.insert(product);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        System.out.println("商品" +product.getType() + "的商品折扣为" + product.getDiscount() + "%。");

        kSession.dispose();

    }


    /**
     * 测试点：agenda-group  有2个   而且有 focus
     *
     规则test agenda-group 1被触发
     规则test agenda-group 2被触发
     其他规则被触发
     命中了3条规则！
     商品GOLD的商品折扣为10%。
     */
    @Test
    public void onLoopTest3() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("agendaGroup2-session");
        kSession.getAgenda().getAgendaGroup("abc").setFocus();

        Product product = new Product();
        product.setType(Product.GOLD);
        product.setDiscount(10);

        kSession.insert(product);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        System.out.println("商品" +product.getType() + "的商品折扣为" + product.getDiscount() + "%。");

        kSession.dispose();

    }


    /**
     * 测试点：agenda-group  有2个   而且有 focus  有 salience
     *
     规则test agenda-group 2被触发
     规则test agenda-group 1被触发
     其他规则被触发
     命中了3条规则！
     商品GOLD的商品折扣为10%。

     salience 越大 越先执行

     */
    @Test
    public void onLoopTest4() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("agendaGroup3-session");
        kSession.getAgenda().getAgendaGroup("abc").setFocus();

        Product product = new Product();
        product.setType(Product.GOLD);
        product.setDiscount(10);

        kSession.insert(product);
        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        System.out.println("商品" +product.getType() + "的商品折扣为" + product.getDiscount() + "%。");

        kSession.dispose();

    }






}
