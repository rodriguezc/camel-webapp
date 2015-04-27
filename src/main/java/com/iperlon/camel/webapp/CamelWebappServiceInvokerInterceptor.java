package com.iperlon.camel.webapp;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.ServiceInvokerInterceptor;
import org.apache.cxf.jaxrs.JAXRSInvoker;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class CamelWebappServiceInvokerInterceptor extends AbstractPhaseInterceptor<Message> {

    Map<String, Object> mapOfservices = new HashMap<String, Object>();
    private JAXRSInvoker invoker = new JAXRSInvoker();

    public CamelWebappServiceInvokerInterceptor(List<Object> services) {
        super(Phase.PRE_INVOKE);
        for(Object service : services) {
            mapOfservices.put(service.getClass().getSimpleName(), service);
        }
    }

    public void handleMessage(final Message message) {
        String className = (String) message.get(CamelWebappConstants.OPERATION_CLASS);

        final Object serviceInstance = mapOfservices.get(className);

        if(serviceInstance != null) {
            final Exchange exchange = message.getExchange();
            final Endpoint endpoint = exchange.get(Endpoint.class);
            final Service service = endpoint.getService();

            Exchange runableEx = message.getExchange();
            Object result = invoker.invoke(runableEx, getInvokee(message), serviceInstance);
            if (!exchange.isOneWay()) {
                Endpoint ep = exchange.get(Endpoint.class);

                Message outMessage = runableEx.getOutMessage();
                if (outMessage == null) {
                    outMessage = new MessageImpl();
                    outMessage.setExchange(exchange);
                    outMessage = ep.getBinding().createMessage(outMessage);
                    exchange.setOutMessage(outMessage);
                }
                copyJaxwsProperties(message, outMessage);
                if (result != null) {
                    MessageContentsList resList = null;
                    if (result instanceof MessageContentsList) {
                        resList = (MessageContentsList) result;
                    } else if (result instanceof List) {
                        resList = new MessageContentsList((List<?>) result);
                    } else if (result.getClass().isArray()) {
                        resList = new MessageContentsList((Object[]) result);
                    } else {
                        outMessage.setContent(Object.class, result);
                    }
                    if (resList != null) {
                        outMessage.setContent(List.class, resList);
                    }
                }


            }
            MessageContentsList messageContentsList = (MessageContentsList)result;

            Object resultInvokation = messageContentsList.get(0);

            if(resultInvokation instanceof Response) {
                message.getExchange().put(Response.class, (Response)resultInvokation);
            } else {
                message.getExchange().put(Response.class, Response.ok(resultInvokation).build());
            }
            message.getInterceptorChain().doInterceptStartingAt(message, ServiceInvokerInterceptor.class.getName());
            message.getInterceptorChain().abort();


        }

    }

    private Object getInvokee(Message message) {
        Object invokee = message.getContent(List.class);
        if (invokee == null) {
            invokee = message.getContent(Object.class);
        }
        return invokee;
    }

    private void copyJaxwsProperties(Message inMsg, Message outMsg) {
        outMsg.put(Message.WSDL_OPERATION, inMsg.get(Message.WSDL_OPERATION));
        outMsg.put(Message.WSDL_SERVICE, inMsg.get(Message.WSDL_SERVICE));
        outMsg.put(Message.WSDL_INTERFACE, inMsg.get(Message.WSDL_INTERFACE));
        outMsg.put(Message.WSDL_PORT, inMsg.get(Message.WSDL_PORT));
        outMsg.put(Message.WSDL_DESCRIPTION, inMsg.get(Message.WSDL_DESCRIPTION));
    }
}
