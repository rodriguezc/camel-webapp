package com.iperlon.camel.webapp;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.lang.reflect.Method;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class MethodInfosInterceptor extends AbstractPhaseInterceptor<Message> {

    public MethodInfosInterceptor() {
        super(Phase.PRE_LOGICAL);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Method method = (Method) message.get("org.apache.cxf.resource.method");
        message.put(CamelWebappConstants.OPERATION_CLASS, method.getDeclaringClass().getSimpleName());
        message.put(CamelWebappConstants.OPERATION_METHOD, method.getName());
        message.put(CamelWebappConstants.OPERATION, method.getDeclaringClass().getSimpleName()+"."+method.getName());
    }
}