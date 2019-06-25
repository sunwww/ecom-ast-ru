package ru.ecom.api.promed;


import org.json.JSONObject;
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
    ) throws NamingException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        if (isUpload==null) isUpload=false;
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        SimpleDateFormat format;
        java.sql.Date d;
        try {
            format = new SimpleDateFormat("yyyy-MM-dd");
            d = new java.sql.Date(format.parse(dateTo).getTime());
        }
        catch(NullPointerException | ParseException ex) {
            JSONObject res = new JSONObject();
            res.put("status","error")
                    .put("reason","incorrect dateTo");
            return res.toString();
        }
        if (sstream==null || sstream.equals("")) sstream="OBLIGATORYINSURANCE";
        return service.getPolyclinicCase(d,sstream,isUpload, includeNeoUzi!=null && includeNeoUzi);
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

    @GET
    @Path("getWfInfo")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Получить ФИО, отделение и promedcode_lpusection и promedcode_workstaff.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param workfunction_id Long Рабочая функция
     * @return JSON in String
     */
    public String getWfInfo(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken
            , @QueryParam("workfunction_id") Long workfunction_id
    ) throws NamingException, ParseException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.getWfInfo(workfunction_id);
    }


    @POST
    @Path("setWfInfo")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Установить promedcode_lpusection и promedcode_workstaff.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param workfunction_id Long Рабочая функция
     * @param promedcode_lpusection String promedcode_lpusection
     * @param promedcode_workstaff String promedcode_workstaff
     * @return JSON in String
     */
    public String setWfInfo(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken
            , @QueryParam("workfunction_id") Long workfunction_id
            , @QueryParam("promedcode_lpusection") String promedcode_lpusection, @QueryParam("promedcode_workstaff") String promedcode_workstaff
    ) throws NamingException {
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);
        IApiPolyclinicService service =Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.setWfInfo(workfunction_id,promedcode_lpusection,promedcode_workstaff);
    }
}