package ru.ecom.api.promed;


import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import ru.ecom.api.form.PromedPolyclinicTapForm;
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
import java.util.List;

@Path("/promed")
public class PolyclinicResource {

    @GET
    @Path("getPolyclinicCase")
    @Produces(MediaType.APPLICATION_JSON)
    /*
     * Получить cписок случаев поликлинического обслуживания в JSON.
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param dateTo String Дата окончания случая в формате yyyy-MM-dd
     * @param workFunctionId Рабочая функция врача
     * @param limit Лимит по записям
     * @param isUpload Boolean Если true, то не возвращать выгруженные
     * @return JSON in String
     */
    public String getPolyclinicCase(@Context HttpServletRequest aRequest, @WebParam(name = "token") String aToken
            , @QueryParam("dateTo") String dateTo
            , @QueryParam("workFunctionId") Long wfId
            , @QueryParam("limit") Integer limitNum
            , @QueryParam("isUpload") Boolean isUpload
    ) throws NamingException {
        if (aToken != null) {
            ApiUtil.login(aToken, aRequest);
        }
        ApiUtil.init(aRequest, aToken);
        SimpleDateFormat format;
        java.sql.Date d;
        try {
            format = new SimpleDateFormat("yyyy-MM-dd");
            d = new java.sql.Date(format.parse(dateTo).getTime());
        } catch (NullPointerException | ParseException ex) {
            throw new IllegalStateException("incorrect dateTo");
        }
        String serviceStream = "OBLIGATORYINSURANCE";
        List<PromedPolyclinicTapForm> forms = Injection.find(aRequest).getService(IApiPolyclinicService.class).getPolyclinicCase(d, serviceStream, wfId, limitNum, isUpload);
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(new PromedPolyclinicTapForm.TapList(forms));
    }

    @GET
    @Path("getPolyclinicCaseByDateStart")
    @Produces(MediaType.APPLICATION_JSON)
    /*
     * Получить cписок случаев поликлинического обслуживания в JSON (только ОМС).
     *
     * @param aRequest HttpServletRequest
     * @param aToken String
     * @param dateStart String Дата начала СПО в формате yyyy-MM-dd
     * @param workFunctionId Рабочая функция врача
     * @param limit Лимит по записям
     * @return JSON in String
     */
    public String getPolyclinicCaseByDateStart(@Context HttpServletRequest aRequest, @WebParam(name = "token") String aToken
            , @QueryParam("dateStart") String dateStart
            , @QueryParam("workFunctionId") Long wfId
            , @QueryParam("limit") Integer limitNum
    ) throws NamingException {
        if (aToken != null) {
            ApiUtil.login(aToken, aRequest);
        }
        ApiUtil.init(aRequest, aToken);
        IApiPolyclinicService service = Injection.find(aRequest).getService(IApiPolyclinicService.class);
        java.sql.Date d;
        try {
            d = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateStart).getTime());
        } catch (NullPointerException | ParseException ex) {
            JSONObject res = new JSONObject();
            res.put("status", "error")
                    .put("reason", "incorrect dateStart");
            return res.toString();
        }
        String sstream = "OBLIGATORYINSURANCE";
        return service.getPolyclinicCaseByDateStart(d, sstream, wfId, limitNum);
    }


}