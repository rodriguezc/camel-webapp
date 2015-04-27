package com.iperlon.camel.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public interface DefaultInterface {

    @GET
    @Path("{path:.*}")
    public Response welcome();


    @GET
    @Path("/static/{path:.*}")
    public Response staticFiles(@PathParam("path") String path);




}
