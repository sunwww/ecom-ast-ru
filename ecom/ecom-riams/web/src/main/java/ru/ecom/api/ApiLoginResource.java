package ru.ecom.api;

import ru.ecom.api.util.ApiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

@Path("login")
public class ApiLoginResource {

    @GET
    @Path("login/{token}")
    public String login(@Context HttpServletRequest aRequest, @PathParam("token") String login) {
        ApiUtil.login(login, aRequest);
        return "404 :-)";
    }

    @GET
    @Path("logout")
    public String logout(@Context HttpServletRequest aRequest) {
        ApiUtil.logout(aRequest);
        return "404 :-(";

    }
}
