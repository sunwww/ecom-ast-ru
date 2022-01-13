package ru.ecom.api.promed;


import com.google.gson.GsonBuilder;
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
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

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

    /**
     * Выгрузка в промед всех СПО с потоком (ОМС, военкомат, бюджет), в которых есть посещения, созданные за вчерашний день
     *
     * @return пусто
     */
    @GET
    @Path("/exportYesterdayOmc")
    @Produces(MediaType.APPLICATION_JSON)
    public String autoExportOmcYesterday(@Context HttpServletRequest aRequest,
                                         @QueryParam("daysAgo") Integer daysAgo,
                                         @WebParam(name = "token") String aToken) throws NamingException {
        if (aToken != null) {
            ApiUtil.login(aToken, aRequest);
        }
        ApiUtil.init(aRequest, aToken);
        LocalDate ld = LocalDate.now();
        if (daysAgo != null) {
            ld = ld.minus(daysAgo, DAYS);
        }

        String[] serviceStream = {"OBLIGATORYINSURANCE", "BUDGET"}; //выгружаем только ОМС + все виды бюджета
        List<PromedPolyclinicTapForm> forms = Injection.find(aRequest).getService(IApiPolyclinicService.class).getPolyclinicCaseByVisitDateStart(ld, serviceStream);
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(new PromedPolyclinicTapForm.TapList(forms));
    }


}