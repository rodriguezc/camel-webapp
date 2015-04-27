package com.iperlon.camel.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.camel.Endpoint;
import org.apache.camel.component.cxf.jaxrs.CxfRsComponent;
import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.camel.util.CamelContextHelper;
import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class WebappComponent extends CxfRsComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        parameters.put("binding", new WebappCxfRsBinding());

        CxfRsEndpoint cxfRsEndpoint = (CxfRsEndpoint) super.createEndpoint(uri, remaining, parameters);


        cxfRsEndpoint.addResourceClass(DefaultInterface.class);
        cxfRsEndpoint.getInInterceptors().add(new MethodInfosInterceptor());

        cxfRsEndpoint.getInInterceptors().add(new GZIPInInterceptor());
        cxfRsEndpoint.getOutInterceptors().add(new GZIPOutInterceptor());

        //Init jackson provider
        JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
        jacksonJaxbJsonProvider.locateMapper(JsonNode.class, MediaType.APPLICATION_JSON_TYPE);
        cxfRsEndpoint.setProvider(jacksonJaxbJsonProvider);

        ResourceResolver resourceResolver = null;
        String resourceResolverParam = (String) parameters.get("resourceResolver");
        if(resourceResolverParam == null) {
            resourceResolver = new ClasspathResourceResolver(getCamelContext().getClassResolver());
        }
        //Resource is a service
        else if(resourceResolverParam.startsWith("#")) {
            resourceResolver = (ResourceResolver) CamelContextHelper.lookup(getCamelContext(),resourceResolverParam.substring(1, resourceResolverParam.length()));
            if(resourceResolver == null) {
                throw new RuntimeException("ResourceResolver bean not found "+resourceResolverParam);
            }
        }
        //Resource is a defined class
        else {
            resourceResolver = (ResourceResolver) Class.forName(resourceResolverParam).newInstance();
        }

        String resourceCacheMaxSize = (String) parameters.get("rsCacheMaxSize");
        if(resourceCacheMaxSize !=null) {
           resourceResolver = new CacheResourceResolver(resourceResolver, Integer.valueOf(resourceCacheMaxSize));
        }

        String welcomeParam = (String) parameters.get("welcome");

        cxfRsEndpoint.getInInterceptors().add(new DefaultInInterceptor(resourceResolver, welcomeParam));


        String externalServices = (String) parameters.get("externalServices");
        if(externalServices != null) {
            List<Object> services = new ArrayList<Object>();
            for(String serviceInstance : externalServices.split(",")) {
                Object service = CamelContextHelper.lookup(getCamelContext(), serviceInstance);
                cxfRsEndpoint.addResourceClass( service.getClass());
                services.add(service);
            }
            cxfRsEndpoint.getInInterceptors().add(new CamelWebappServiceInvokerInterceptor(services));
        }


        return cxfRsEndpoint;
    }


}
