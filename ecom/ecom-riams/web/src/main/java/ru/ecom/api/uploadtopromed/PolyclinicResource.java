package ru.ecom.api.uploadtopromed;


import ru.ecom.api.promed.IApiPolyclinicService;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/promed")
public class PolyclinicResource {
    @POST
    @Path("getPolyclinicCase")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Получить cписок случаев поликлинического обслуживания в JSON.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param dateTo String
     * @return JSON in String
     */
    public String getPolyclinicCase(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken
            , @QueryParam("dateTo") String dateTo
    ) throws NamingException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.getPolyclinicCase(dateTo);
    }
}