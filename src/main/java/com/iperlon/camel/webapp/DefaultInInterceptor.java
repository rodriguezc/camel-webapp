package com.iperlon.camel.webapp;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.ServiceInvokerInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class DefaultInInterceptor extends AbstractPhaseInterceptor<Message> {

    private ResourceResolver resourceResolver;

    private String welcome;

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultInInterceptor.class);

    public DefaultInInterceptor(ResourceResolver resourceResolver, String welcome) {
        super(Phase.PRE_LOGICAL);
        this.resourceResolver = resourceResolver;
        if (welcome == null) {
            welcome = "/static/index.html";
        }
        this.welcome = welcome;


    }

    @Override
    public void handleMessage(Message message) throws Fault {
        if ("DefaultInterface.staticFiles".equals(message.get(CamelWebappConstants.OPERATION))) {
            String filePath ="";
            try {
                String requestUri = (String) message.get("org.apache.cxf.request.uri");
                String basePath = (String) message.get("org.apache.cxf.message.Message.BASE_PATH");
                filePath = requestUri.substring(basePath.length() + 7, requestUri.length());
                StaticResource staticResource = resourceResolver.get(filePath);
                if (staticResource != null) {
                    Response response = Response.ok(staticResource.createIs(), staticResource.getMimeType()).header("Content-Length", staticResource.getLength()).build();
                    message.getExchange().put(Response.class, response);
                    message.getInterceptorChain().doInterceptStartingAt(message, ServiceInvokerInterceptor.class.getName());
                } else {
                    Response response = Response.status(404).build();
                    message.getExchange().put(Response.class, response);
                    message.getInterceptorChain().doInterceptStartingAt(message, ServiceInvokerInterceptor.class.getName());
                }

            } catch (Exception e) {
                LOGGER.error("Can't resolve resource "+filePath, e);
                Response response = Response.status(500).entity(CamelWebappUtils.printStackTrace(e)).type("text/plain" +
                        "").build();
                message.getExchange().put(Response.class, response);
                message.getInterceptorChain().doInterceptStartingAt(message, ServiceInvokerInterceptor.class.getName());
            }
        } else if ("DefaultInterface.welcome".equals(message.get(CamelWebappConstants.OPERATION))) {
            String basePath = (String) message.get(Message.BASE_PATH);
            Response response = Response.status(301).header("Location", basePath + welcome).build();
            message.getExchange().put(Response.class, response);
        }


    }
}