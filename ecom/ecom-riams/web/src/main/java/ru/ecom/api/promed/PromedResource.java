package ru.ecom.api.promed;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.api.entity.export.ExportType;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/promed")
public class PromedResource {
    private static final Logger LOG = Logger.getLogger(PromedResource.class);


    @POST
    @Path("setEvnTap")
    @Produces(MediaType.APPLICATION_JSON)
    /*
     * Установить случаю promed_id и отметку о выгрузке.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param medcase_id Long id случая
     * @param tap_id String promed_id
     * @return JSON in String
     */
    public String setEvnTap(@Context HttpServletRequest aRequest, @WebParam(name = "token") String aToken
            , @QueryParam("medcase_id") Long medcaseId, @QueryParam("tap_id") Long tapId
    ) throws NamingException {
        if (aToken != null) {
            ApiUtil.login(aToken, aRequest);
        }
        ApiUtil.init(aRequest, aToken);
        IApiPolyclinicService service = Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.setEvnTap(medcaseId, tapId);
    }


    @GET
    @Path("getWfInfo")
    @Produces(MediaType.APPLICATION_JSON)
    /*
     * Получить ФИО, отделение и promedcode_lpusection и promedcode_workstaff.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param workfunction_id Long Рабочая функция
     * @return JSON in String
     */
    public String getWfInfo(@Context HttpServletRequest aRequest, @WebParam(name = "token") String aToken
            , @QueryParam("workfunction_id") Long workfunction_id) throws NamingException {
        if (aToken != null) {
            ApiUtil.login(aToken, aRequest);
        }
        ApiUtil.init(aRequest, aToken);
        IApiPolyclinicService service = Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.getWfInfo(workfunction_id);
    }


    @POST
    @Path("setWfInfo")
    @Produces(MediaType.APPLICATION_JSON)
    /*
     * Установить promedcode_lpusection и promedcode_workstaff (используется промедосом.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param workfunction_id Long Рабочая функция
     * @param promedcode_lpusection String promedcode_lpusection
     * @param promedcode_workstaff String promedcode_workstaff
     * @return JSON in String
     */
    public String setWfInfo(@Context HttpServletRequest aRequest, @Context HttpServletResponse aResponse, String jsonData) throws NamingException {
        aResponse.setHeader("Access-Control-Allow-Origin", "*");
        aResponse.setHeader("Access-Control-Allow-Methods", "POST");
        JSONObject req = new JSONObject(jsonData);
        String token = req.has("token") ? req.getString("token") : null;
        if (token != null) {
            ApiUtil.login(token, aRequest);
        }
        ApiUtil.init(aRequest, token);
        Long workFunctionId = req.getLong("workfunctionId");
        Long promedcodeLpuSection = req.getLong("promedcodeLpuSection");
        Long promedcodeWorkstaff = req.getLong("promedcodeWorkstaff");
        IApiPolyclinicService service = Injection.find(aRequest).getService(IApiPolyclinicService.class);
        return service.setWfInfo(workFunctionId, promedcodeLpuSection, promedcodeWorkstaff);
    }

    /**
     * Создаем запись об экспорте случая (для тех случаев, когда инициатор экспорта - не медос)
     *
     * @param medcaseId  ИД СМО
     * @param packetGuid ГУИД пакета
     * @return
     */
    @POST
    @Path("/createPacketLog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPacketLog(@Context HttpServletRequest aRequest,
                                    @WebParam(name = "token") String token,
                                    @QueryParam("medcaseId") Long medcaseId,
                                    @QueryParam("packetGuid") UUID packetGuid) throws NamingException {
        if (token != null) {
            ApiUtil.login(token, aRequest);
        }
        ApiUtil.init(aRequest, token);
        Injection.find(aRequest).getService(IApiPolyclinicService.class).createPacketLog(medcaseId, packetGuid, ExportType.AUTO);
        return Response.ok().build();
    }

    /**
     * Проставляем промедовский код департаменту (используется промедосом)
     * @param request
     * @param response
     * @param departmentId ИД MisLpu
     * @param promedcodeLpuSection ИД подразделения в системе промед
     * @param token
     * @return
     * @throws NamingException
     */
    @POST
    @Path("/setDepInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setDepInfo(@Context HttpServletRequest request,
                               @Context HttpServletResponse response,
                               @QueryParam("departmentId") Long departmentId,
                               @QueryParam("promedcodeLpuSection") String promedcodeLpuSection,
                               @QueryParam("token") String token
    ) throws NamingException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        LOG.info("got setDepInfo message: " + departmentId + ">" + promedcodeLpuSection);
        if (token != null) {
            ApiUtil.login(token, request);
        }
        ApiUtil.init(request, token);
        String str = Injection.find(request).getService(IApiPolyclinicService.class).setDepInfo(departmentId, promedcodeLpuSection);
        return Response.ok(str).build();
    }


}
