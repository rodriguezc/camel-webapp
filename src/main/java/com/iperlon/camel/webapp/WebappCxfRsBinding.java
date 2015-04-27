package com.iperlon.camel.webapp;

import org.apache.camel.component.cxf.jaxrs.SimpleCxfRsBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class WebappCxfRsBinding extends SimpleCxfRsBinding {
    Set<String> mapOfHeadersToExclude = null;

    public WebappCxfRsBinding() {
        mapOfHeadersToExclude = new HashSet<String>();
        mapOfHeadersToExclude.add("Accept");
        mapOfHeadersToExclude.add("Accept-Encoding");
        mapOfHeadersToExclude.add("Accept-Language");
        mapOfHeadersToExclude.add("breadcrumbId");
        mapOfHeadersToExclude.add("Host");
        mapOfHeadersToExclude.add("Pragma");
        mapOfHeadersToExclude.add("Referer");
        mapOfHeadersToExclude.add("User-Agent");
    }


    @Override
    protected Map<String, String> filterCamelHeadersForResponseHeaders(Map<String, Object> headers, org.apache.camel.Exchange camelExchange) {
        Map<String, String> mapOfValues = super.filterCamelHeadersForResponseHeaders(headers, camelExchange);
        for(String key:  new ArrayList<>(mapOfValues.keySet())) {
            if(key.startsWith("X-") || mapOfHeadersToExclude.contains(key)) {
                mapOfValues.remove(key);
            }
        }
        return mapOfValues;
    }


}
