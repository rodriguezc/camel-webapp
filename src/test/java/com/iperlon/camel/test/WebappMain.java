package com.iperlon.camel.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class WebappMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        container.registerShutdownHook();
    }
}
