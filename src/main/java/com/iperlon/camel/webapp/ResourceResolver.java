package com.iperlon.camel.webapp;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public interface ResourceResolver {

    public StaticResource get(String path) throws Exception;

}
