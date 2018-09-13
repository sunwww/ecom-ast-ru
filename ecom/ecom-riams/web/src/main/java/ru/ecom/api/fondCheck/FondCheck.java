package ru.ecom.api.fondCheck;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Created by rkurbanov on 21.08.2018. */

@Path("/foncCheck")
public class FondCheck {

    /**
     * Проверить по фонду
     * @param snils
     * @param series
     * @param number
     * @param type
     * @param lastname
     * @param firstname
     * @param middlename
     * @param birthday
     * @return
     */
    @GET
    @Path("check")
    @Produces("application/json")
    public String check(@Context HttpServletRequest aRequest,
                        @WebParam(name="token") String aToken,
                        @QueryParam("snils") String snils,
                        @QueryParam("series") String series,
                        @QueryParam("number") String number,
                        @QueryParam("type") String type,
                        @QueryParam("lastname") String lastname,
                        @QueryParam("firstname") String firstname,
                        @QueryParam("middlename") String middlename,
                        @QueryParam("birthday") String birthday) throws ParserConfigurationException,
            SAXException, IOException, JSONException, NamingException {

        ApiUtil.init(aRequest,aToken);
        return FondCheckUtil.check(snils,series,number,type,lastname,firstname,middlename,birthday);
    }

    /**
     * Проверить и сохранить в базу
     * @param snils
     * @param series
     * @param number
     * @param type
     * @param lastname
     * @param firstname
     * @param middlename
     * @param birthday
     * @return
     */
    @GET
    @Path("sync")
    @Produces("application/json")
    public String sync(@Context HttpServletRequest aRequest,
                       @WebParam(name="token") String aToken,
                       @QueryParam("snils") String snils,
                       @QueryParam("series") String series,
                       @QueryParam("number") String number,
                       @QueryParam("type") String type,
                       @QueryParam("lastname") String lastname,
                       @QueryParam("firstname") String firstname,
                       @QueryParam("middlename") String middlename,
                       @QueryParam("birthday") String birthday) throws ParserConfigurationException,
            SAXException, IOException, JSONException, NamingException {

        ApiUtil.init(aRequest,aToken);

        return FondCheckUtil.sync(aRequest,snils,series,number,type,lastname,firstname,middlename,birthday);
    }

    /**
     * Проверить по пациентам и сохранить в базу
     * @param aRequest
     * @param aToken
     * @param patient_id
     * @param limit
     * @return
     */
    @GET
    @Path("syncByPatinet")
    @Produces("application/json")
    public String syncByPatinet(@Context HttpServletRequest aRequest,
                                @WebParam(name="token") String aToken,
                                @QueryParam("patient_id") String patient_id,
                                @QueryParam("limit") String limit) throws ParserConfigurationException,
            SAXException, IOException, JSONException, NamingException {

        ApiUtil.init(aRequest,aToken);

        if(limit==null) limit="100";

        String sql="select id,lastname,firstname,middlename,birthday,snils from patient where patientfond_id is null and (isCheckFondError=false or isCheckFondError is null) limit "+limit;
        if(patient_id!=null)  sql ="select id,lastname,firstname,middlename,birthday,snils from patient where id="+patient_id;

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        Collection<WebQueryResult> list = service.executeNativeSql(sql);
        List<Patient> patients = new ArrayList<>();


        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) {

                Patient patient = new Patient();
                patient.setId(((BigInteger) wqr.get1()).longValue());
                patient.setLastname(wqr.get2().toString());
                patient.setFirstname(wqr.get3().toString());
                patient.setMiddlename(wqr.get4().toString());
                if(wqr.get5()!=null) patient.setBirthday((Date) wqr.get5());
                if(wqr.get6()!=null) patient.setSnils(wqr.get6().toString());

                patients.add(patient);
            }
        }


        JSONArray jsonArray = new JSONArray();
        for (Patient patient:patients){
            String returnJson=FondCheckUtil.sync(aRequest,patient.getSnils(),"","","",patient.getLastname(),patient.getFirstname(),
                    patient.getMiddlename(), String.valueOf(patient.getBirthday()));

            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(returnJson).getAsJsonObject();

            if(!mainObject.has("error")){

                String patientFond_id = mainObject.get("patientFond_id").getAsString();
                service.executeUpdateNativeSql("update patient set patientfond_id="+patientFond_id+" where id="+patient.getId());
                jsonArray.put(new JSONObject().put("patient_id",patient.getId()));

            }else {
                service.executeUpdateNativeSql("update patient set isCheckFondError=true where id="+patient.getId());
                jsonArray.put(new JSONObject().put("patient_id",patient.getId()).put("error","1"));
            }
        }

        return new JSONObject().put("patients",jsonArray).toString();
    }

    /**
     * Получение RZ по СНИЛСу
     * @param snils
     * @return
     */
    @GET
    @Path("getRzbySnils")
    @Produces("application/json")
    public String getRzbySnils(@Context HttpServletRequest aRequest,
                               @WebParam(name="token") String aToken,
                               @QueryParam("snils") String snils) throws ParserConfigurationException,
            SAXException, IOException {

        return FondCheckUtil.getRzbySnils(snils);
    }

    /**
     * Получение RZ по документам
     * @param type
     * @param series
     * @param number
     * @return
     */
    @GET
    @Path("getRZbyDocs")
    @Produces("application/json")
    public String getRZbyDocs(@Context HttpServletRequest aRequest,
                              @WebParam(name="token") String aToken,
                              @QueryParam("type") String type,
                              @QueryParam("series") String series,
                              @QueryParam("number") String number)
            throws ParserConfigurationException,
            SAXException, IOException {

        ApiUtil.init(aRequest,aToken);
        return FondCheckUtil.getRZbyDocs(type,series,number);
    }

    /**
     * Получение RZ по мед.полису
     * @param series
     * @param number
     * @return
     */
    @GET
    @Path("getRzbyPolis")
    @Produces("application/json")
    public String getRzbyPolis(@Context HttpServletRequest aRequest,
                               @WebParam(name="token") String aToken,
                               @QueryParam("series") String series,
                               @QueryParam("number") String number)
            throws ParserConfigurationException,
            SAXException, IOException {

        ApiUtil.init(aRequest,aToken);
        return FondCheckUtil.getRzbyPolis(series,number);
    }

    /**
     * Получение RZ по ФИО и ДР.
     * @param lastname
     * @param firstname
     * @param middlename
     * @param birthday
     */
    @GET
    @Path("getRZbyFullnameAndBD")
    @Produces("application/json")
    public String getRZbyFullnameAndBD(@Context HttpServletRequest aRequest,
                                       @WebParam(name="token") String aToken,
                                       @QueryParam("lastname") String lastname,
                                       @QueryParam("firstname") String firstname,
                                       @QueryParam("middlename") String middlename,
                                       @QueryParam("birthday") String birthday)
            throws ParserConfigurationException,
            SAXException, IOException {

        ApiUtil.init(aRequest,aToken);
        return FondCheckUtil.getRZbyFullnameAndBD(lastname,firstname,middlename,birthday);
    }
}
