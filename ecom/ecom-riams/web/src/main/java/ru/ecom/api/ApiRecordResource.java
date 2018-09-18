package ru.ecom.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.record.IApiRecordService;
import ru.ecom.api.util.ApiRecordUtil;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
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
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}

        try {
            IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
            return service.executeNativeSqlGetJSON(new String[]{"id","name"},"select id, patientinfo from patient where lastname='"+lastname+"'",10);
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

    public final String[][] serviceStreams = {{"OMC","ОМС"},{"CHARGED","Платно"}};
    /** Получаем список поддерживаемых потоков обслуживания*/
    @GET
    @Path("/getServiceStream")
    @Produces(MediaType.APPLICATION_JSON)

    public String getServiceStream(@Context HttpServletRequest aRequest) throws JSONException {
        JSONArray array = new JSONArray();
        for (String[] s: serviceStreams) {
            array.put(new JSONObject().put("code",s[0]).put("name",s[1]));
        }
        return array.toString();
    }

    /**Получение ЛПУ либо подразделения */
    @GET
    @Path("/getLpu")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLpu(@Context HttpServletRequest aRequest
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("token") String aToken
    ) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getDepartments(serviceStream,service);
    }

   /** Список специальностей врачей по потоку обслуживания */
    @GET
    @Path("/getSpecializations")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSpecializations(@Context HttpServletRequest aRequest
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("token") String aToken
            ,@QueryParam("lpu") String aLpu
    ) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getSpecializations(serviceStream,aLpu, service);

    }

    /** Список врачей по потоку обслуживания и специальности*/
    @GET
    @Path("/getDoctors")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDoctors(@Context HttpServletRequest aRequest
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("lpu") String aLpu
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getDoctors(serviceStream,vocWorkfunction,aLpu,service);
    }

    /** Список свободных дат по врачу или его профилю*/
    @GET
    @Path("/getFreeCalendarDate")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreeCalendarDates(@Context HttpServletRequest aRequest
            , @QueryParam("workfunction_id") String workfunction
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("lpu") String aLpu
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getFreeCalendarDaysByWorkFunction(workfunction,vocWorkfunction,serviceStream,aLpu,service);
    }

    /** Список свободных времен по дате */
    @GET
    @Path("/getFreeCalendarTime")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreeCalendarTimes(@Context HttpServletRequest aRequest
            , @QueryParam("workfunction_id") String workfunction
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("calendarDay_id") String calendarDay_id
            , @QueryParam("calendarDate") String calendarDate
            , @QueryParam("lpu") String aLpu
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getFreeCalendarTimesByCalendarDate(calendarDay_id,vocWorkfunction,calendarDate,serviceStream,aLpu,service);
    }

    /** Запись пациента на время */
    @POST
    @Path("makeRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeRecord(@Context HttpServletRequest aRequest
            ,  String jsonData) throws NamingException, JSONException, ParseException {
        return makeRecordOrAnnul(aRequest,new JSONObject(jsonData));
    }

    /** Аннулирование записи пациента */
    @POST
    @Path("annulRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public String annulRecord(@Context HttpServletRequest aRequest
            ,  String jsonData) throws NamingException, JSONException, ParseException {
        JSONObject root = new JSONObject(jsonData);
        root.put("annul","annul");
        return makeRecordOrAnnul(aRequest,root);
    }


    private String makeRecordOrAnnul(HttpServletRequest aRequest,  JSONObject root) {

        try {
            System.out.println("json record = "+root.toString());
            Long calendarTimeId = Long.valueOf((String)getJsonField(root,"calendarTime_id"));
            String lastname = getJsonField(root,"lastname");
            String firstname = getJsonField(root,"firstname");
            String middlename = getJsonField(root,"middlename");
            String birthday = getJsonField(root,"birthday");
            String patientGUID = getJsonField(root,"patient_uid");
            String patientComment = getJsonField(root,"comment");
            String patientPhone = getJsonField(root,"phone");
            String debug = getJsonField(root,"debug");
            String token = getJsonField(root,"token");
            String annul = getJsonField(root,"annul");
            String list;

            ApiUtil.init(aRequest,token);
            IApiRecordService service =Injection.find(aRequest).getService(IApiRecordService.class);
            if (!StringUtil.isNullOrEmpty(annul)) {
                list = new ApiRecordUtil().annulRecord(calendarTimeId,lastname,firstname,middlename, (birthday!=null?DateFormat.parseSqlDate(birthday,"yyyy-MM-dd"):null),patientGUID,service);
            } else {
                list =  ApiRecordUtil.recordPatient(calendarTimeId,lastname,firstname,middlename,birthday!=null?DateFormat.parseSqlDate(birthday,"yyyy-MM-dd"):null,patientGUID,patientComment,patientPhone,service);
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
        } catch (Exception e) {
            e.printStackTrace();
            String ret ="{\"status\":\"error\",\"error_name\":\""+e.getLocalizedMessage()+"\",\"error_code\":\""+e.toString()+"\"}";
            System.out.println(ret);
            return ret;
            }
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
