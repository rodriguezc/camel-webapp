package com.iperlon.camel.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by xsicrz on 27.04.15.
 */

@Path("/services")
public interface ServiceInterface {

    @GET
    @Path("/test2")
    public Response test2();
}
