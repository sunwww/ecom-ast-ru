package ru.ecom.api.promed;


import ru.ecom.api.util.ApiUtil;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Path("/promed")
public class PolyclinicResource {
    @GET
    @Path("getPolyclinicCase")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Получить cписок случаев поликлинического обслуживания в JSON.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param dateTo String Дата окончания случая в формате yyyy-MM-dd
     * @param sstream String Код потока обслуживания
     * @return JSON in String
     */
    public String getPolyclinicCase(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken
            , @QueryParam("dateTo") String dateTo, @QueryParam("sstream") String sstream
    ) throws NamingException, ParseException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (sstream==null || sstream.equals("")) sstream="OBLIGATORYINSURANCE";
        return service.getPolyclinicCase(new java.sql.Date(format.parse(dateTo).getTime()),sstream);
    }
}