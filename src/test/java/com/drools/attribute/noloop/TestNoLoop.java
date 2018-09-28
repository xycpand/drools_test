package com.drools.attribute.noloop;

import com.secbro.drools.model.Product;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by lcc on 2018/9/28.
 */
public class TestNoLoop {


    /**
     * 测试博客上的 no-loop 属性 true
     */
    @Test
    public void onLoopTest1() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("noLoopSession");

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
     * 测试博客上的 no-loop 属性 fale
     */
    @Test
    public void onLoopTest2() {
        // 构建KieServices
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        // 获取kmodule.xml中配置中名称为ksession-rule的session，默认为有状态的。
        KieSession kSession = kieContainer.newKieSession("noLoopSession2");

        Product product = new Product();
        product.setType(Product.GOLD);
        product.setDiscount(12);

        kSession.insert(product);

        int count = kSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        System.out.println("商品" +product.getType() + "的商品折扣为" + product.getDiscount() + "%。");

        kSession.dispose();

    }

}
