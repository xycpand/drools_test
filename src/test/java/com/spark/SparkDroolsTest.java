package com.spark;

import entity.Person;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.File;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * 同卡号单日最大交易金额测试
 * @author ll-t150
 *
 */
public class SparkDroolsTest {

    public static Logger logger = Logger.getLogger(SparkDroolsTest.class);
    public static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) throws Exception {

        SparkConf conf = new SparkConf().setMaster("local[4]").setAppName("NetworkWordCount").set("spark.testing.memory",
                "2147480000");
        JavaStreamingContext jssc =  new JavaStreamingContext(conf, Durations.seconds(5));
        System.out.println(jssc);

        // Create a DStream that will connect to hostname:port, like
        // localhost:9999
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("lcc", 9999);

        JavaDStream<Person> mapRDD = lines.map(new Function<String, Person>() {
            @Override
            public Person call(String s) throws Exception {
                String[] danci = s.split(" ");
                if( danci.length == 2 ){
                    Person apple = new Person();
                    apple.setName(danci[0]);
                    apple.setCount(Integer.parseInt(danci[1]));
                    return apple;
                }
                return null;
            }
        });



        mapRDD.foreachRDD(new VoidFunction<JavaRDD<Person>>() {
            @Override
            public void call(JavaRDD<Person> appleJavaRDD) throws Exception {
                appleJavaRDD.foreach(new VoidFunction<Person>() {
                    @Override
                    public void call(Person apple) throws Exception {
                        if( apple != null ){
                            System.out.println("id: " + apple.getName());
                            System.out.println("proice: " + apple.getCount());

                            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
                            String filePath = SparkDroolsTest.class.getClassLoader().getResource("com/rules/Matches.drl").getPath();
                            File file = new File(URLDecoder.decode(filePath,"utf-8"));
                            kbuilder.add(ResourceFactory.newFileResource(file), ResourceType.DRL);
                            InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
                            Collection<KiePackage> ss = kbuilder.getKnowledgePackages();
                            kbase.addPackages(ss);
                            StatelessKieSession ksession = kbase.newStatelessKieSession();
                            Person person=new Person(apple.getName(),apple.getCount());
                            ksession.execute(person);

                        }
                    }
                });
            }
        });






        jssc.start();
        //System.out.println(wordCounts.count());// Start the computation
        jssc.awaitTermination();   // Wait for the computation to terminate

    }

}
