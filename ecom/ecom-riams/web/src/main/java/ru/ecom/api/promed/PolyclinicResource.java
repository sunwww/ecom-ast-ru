package ru.ecom.api.promed;


import ru.ecom.api.util.ApiUtil;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
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
     * @param isUpload Boolean Если true, то не возвращать выгруженные
     * @return JSON in String
     */
    public String getPolyclinicCase(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken
            , @QueryParam("dateTo") String dateTo, @QueryParam("sstream") String sstream
            , @QueryParam("isUpload") Boolean isUpload
            , @QueryParam("includeNeoUzi") Boolean includeNeoUzi
    ) throws NamingException, ParseException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        if (isUpload==null) isUpload=false;
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (sstream==null || sstream.equals("")) sstream="OBLIGATORYINSURANCE";
        return service.getPolyclinicCase(new java.sql.Date(format.parse(dateTo).getTime()),sstream,isUpload, includeNeoUzi!=null && includeNeoUzi);
    }

    @POST
    @Path("setEvnTap")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Установить случаю promed_id и отметку о выгрузке.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param medcase_id Long id случая
     * @param tap_id String promed_id
     * @return JSON in String
     */
    public String setEvnTap(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken
            , @QueryParam("medcase_id") Long medcase_id, @QueryParam("tap_id") String tap_id
    ) throws NamingException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.setEvnTap(medcase_id,tap_id);
    }
}