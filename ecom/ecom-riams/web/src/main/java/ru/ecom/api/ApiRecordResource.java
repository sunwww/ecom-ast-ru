package ru.ecom.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.record.IApiRecordService;
import ru.ecom.api.util.ApiRecordUtil;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;

@Path("/record")
public class ApiRecordResource {
    @GET
    @Path("/test/mazafaka/{lastname}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessageById(@Context HttpServletRequest aRequest, @PathParam("lastname") String lastname, @WebParam(name="token") String aToken) {
        String message = "Hello "+lastname;
        //66405d38-a173-4cb7-a1b6-3ada51c16ac5
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}

        try {
            IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
            return service.executeNativeSqlGetJSON(new String[]{"id,name"},"select id, patientinfo from patient where lastname='"+lastname+"'",10);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return message;
    }

    @GET
    @Path("/getNull")
    public String returnEmpty(){
        return "Enter test ID";
    }

    public final String[] serviceStreams = {"OMC","CHARGED"};
    /** Получаем список поддерживаемых потоков обслуживания*/
    @GET
    @Path("/getServiceStream")
    @Produces(MediaType.APPLICATION_JSON)

    public String getServiceStream(@Context HttpServletRequest aRequest) throws JSONException {
        JSONArray array = new JSONArray();
        for (String s: serviceStreams) {
            array.put(new JSONObject().put("code",s));
        }
        return array.toString();
    }

   /** Список специальностей врачей по потоку обслуживания */
    @GET
    @Path("/getSpecializations")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSpecializations(@Context HttpServletRequest aRequest
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("token") String aToken
    ) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getSpecializations(serviceStream,service);

    }

    /** Список врачей по потоку обслуживания и специальности*/
    @GET
    @Path("/getDoctors")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDoctors(@Context HttpServletRequest aRequest
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getDoctors(serviceStream,vocWorkfunction,service);
    }

    /** Список свободных дат по врачу или его профилю*/
    @GET
    @Path("/getFreeCalendarDate")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreeCalendarDates(@Context HttpServletRequest aRequest
            , @QueryParam("workfunction_id") String workfunction
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getFreeCalendarDaysByWorkFunction(workfunction,vocWorkfunction,serviceStream,service);
    }

    /** Список свободных времен по дате */
    @GET
    @Path("getFreeCalendarTime")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreeCalendarTimes(@Context HttpServletRequest aRequest
            , @QueryParam("workfunction_id") String workfunction
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("calendarDay_id") String calendarDay_id
            , @QueryParam("calendarDate") String calendarDate
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getFreeCalendarTimesByCalendarDate(calendarDay_id,vocWorkfunction,calendarDate,serviceStream,service);
    }

    /** Запись пациента на время */
    @POST
    @Path("makeRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeRecord(@Context HttpServletRequest aRequest
            ,  String jsonData) throws NamingException, JSONException, ParseException {
        JSONObject root = new JSONObject(jsonData);
        Long calendarTimeId = getJsonField(root,"calendarTime_id");
        String lastname = getJsonField(root,"lastname");
        String firstname = getJsonField(root,"firstname");
        String middlename = getJsonField(root,"middlename");
        String birthday = getJsonField(root,"birthday");
        String patientGUID = getJsonField(root,"patient_uid");
        String patientComment = getJsonField(root,"comment");
        String debug = getJsonField(root,"debug");
        String annul = (String)aRequest.getAttribute("annul");
        String list;
        IApiRecordService service =Injection.find(aRequest).getService(IApiRecordService.class);
        if (annul!=null&&annul.equals("annul")) {
            list = new ApiRecordUtil().annulRecord(calendarTimeId,lastname,firstname,middlename, birthday!=null?DateFormat.parseSqlDate(birthday,"yyyy-MM-dd"):null,patientGUID,service);
        } else {
            System.out.println("start record, debug="+debug);
            list =  ApiRecordUtil.recordPatient(calendarTimeId,lastname,firstname,middlename,birthday!=null?DateFormat.parseSqlDate(birthday,"yyyy-MM-dd"):null,patientGUID,patientComment,service);
            if (list==null) {
                list=ApiRecordUtil.getErrorJson("No make record","ERROR_RECORD");
            } else { //Записали успешно, пишем файл
                JSONObject recordInfo = new JSONObject(list);
                if (!recordInfo.has("error_code")) {
                    String medcaseID = getJsonField(recordInfo, "medcaseId");
                    String patientID = getJsonField(recordInfo, "patientId");
                    if (root.has("files")) {
                        JSONArray files = root.getJSONArray("files");
                        for (int i = 0; i < files.length(); i++) {
                            JSONObject file = files.getJSONObject(i);
                            String filename = getJsonField(file,"filename");
                            String file64String = getJsonField(file,"filebase64");
                            String fileDocType = getJsonField(file,"fileDocType");
                            service.saveExternalDodument(filename, file64String, patientID!=null?Long.valueOf(patientID):null, medcaseID!=null?Long.valueOf(medcaseID):null, Long.valueOf(calendarTimeId), fileDocType);
                        }
                    } else {
                        String fileContent = getJsonField(root,"filebase64");
                        String filename = getJsonField(root,"filename");
                        service.saveFile(filename, fileContent);
                    }


                }
            }

        }
        return list;
    }

    private <T>T getJsonField(JSONObject obj, String aProperty) {
        if (obj.has(aProperty)) {
            try {
                Object o = obj.get(aProperty)!=null&&!obj.get(aProperty).equals("")?obj.get(aProperty):null;
                return (T) o;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
