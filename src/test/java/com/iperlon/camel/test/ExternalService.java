package com.iperlon.camel.test;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
@Service
@Path("/services")
public class ExternalService {

    @GET
    @Path("/test1")
    @Produces("text/plain")
    public Response test1() {
        return Response.ok("test").build();
    }

}
