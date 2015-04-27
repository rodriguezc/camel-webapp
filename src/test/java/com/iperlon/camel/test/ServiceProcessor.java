package com.iperlon.camel.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
@Service
public class ServiceProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody("TEST CamelRoute");
    }
}
