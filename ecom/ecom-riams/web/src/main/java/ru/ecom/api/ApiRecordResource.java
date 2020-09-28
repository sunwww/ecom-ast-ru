package ru.ecom.api;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.record.IApiRecordService;
import ru.ecom.api.util.ApiRecordUtil;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


@Path("/record")
public class ApiRecordResource {
    private static final Logger LOG = Logger.getLogger(ApiRecordResource.class);
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
            LOG.error(e);
        }
        return message;
    }

    @GET
    @Path("/getNull")
    public String returnEmpty(){
        return "Enter test ID";
    }

    private static final String[][] serviceStreams = {{"OMC","ОМС"},{"CHARGED","Платно"}};
    /** Получаем список поддерживаемых потоков обслуживания*/
    @GET
    @Path("/getServiceStream")
    @Produces(MediaType.APPLICATION_JSON)
    public String getServiceStream(@Context HttpServletRequest aRequest) {
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
            , @QueryParam("wantDefMedServices") String aWantDefMedServices
            , @QueryParam("token") String aToken) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return new ApiRecordUtil().getDoctors(serviceStream,vocWorkfunction,aLpu,aWantDefMedServices,service);
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
            , @QueryParam("token") String aToken
            , @QueryParam("requestId") String aRequestId
    ) throws NamingException {

        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String ret = new ApiRecordUtil().getFreeCalendarDaysByWorkFunction(workfunction,vocWorkfunction,serviceStream,aLpu,service);
        if (aRequestId!=null) LOG.info("Запрос №"+aRequestId+" получен, вернули: "+ret); //debug
        return ret;
    }

    /** Список свободных времен по дате */
    @GET
    @Path("/getFreeCalendarTime")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreeCalendarTimes(@Context HttpServletRequest aRequest
            , @QueryParam("workfunction_id") String workfunction
            , @QueryParam("vocWorkfunction_id") String vocWorkfunction
            , @QueryParam("serviceStream") String serviceStream
            , @QueryParam("calendarDay_id") String calendarDayId
            , @QueryParam("calendarDate") String calendarDate
            , @QueryParam("lpu") String aLpu
            , @QueryParam("token") String aToken
            , @QueryParam("requestId") String aRequestId
    ) throws NamingException {
        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String ret = new ApiRecordUtil().getFreeCalendarTimesByCalendarDate(calendarDayId,vocWorkfunction,calendarDate,serviceStream,aLpu,service);
        if (aRequestId!=null) LOG.info("Запрос №"+aRequestId+" получен, вернули: "+ret); //debug
        return ret ;
    }

    /** Запись пациента на время */
    @POST
    @Path("makeRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeRecord(@Context HttpServletRequest aRequest
            ,  String jsonData) {
        return makeRecordOrAnnul(aRequest,new JSONObject(jsonData)).toString();
    }

    /** Аннулирование записи пациента */
    @POST
    @Path("annulRecord")
    @Produces(MediaType.APPLICATION_JSON)
    public String annulRecord(@Context HttpServletRequest aRequest, String jsonData) {
        JSONObject root = new JSONObject(jsonData);
        root.put("annul","annul");
        return makeRecordOrAnnul(aRequest,root).toString();
    }

    /**Находим подходящее свободное время по врачу, дате и времени
     * для записи пациента из промеда
     * @param aDoctorPromedId ИД рабочего места в промеде
     * @param aDate Дата приема в формате yyyy-MM-dd
     * @param aTime Время приема в формате HH:MI
     * @param aService сервис
     * @return ИД времени*/
    private String getWorkCalendarTimeByDateTime(Long aDoctorPromedId, String aDate, String aTime, IWebQueryService aService) {
        String sql = "select wct.id, wcd.calendardate, wct.timefrom, vsrt.code" +
                " from workfunction wf" +
                " left join workcalendar wc on wc.workfunction_id=wf.id" +
                " left join workcalendarday wcd on wcd.workcalendar_id=wc.id " +
                " left join workcalendartime wct on wct.workcalendarday_id=wcd.id" +
                " left join VocServiceReserveType vsrt on vsrt.id=wct.reservetype_id" +
                " where wf.promedcode_workstaff ='"+aDoctorPromedId+"' and wcd.calendardate=to_date('"+aDate+"','yyyy-MM-dd')" +
                " and wct.timefrom between '"+aTime+"'- interval '4 min' and '"+aTime+"'+interval '4 min' "+
                " and vsrt.code='PROMED'"+ //Ищем только времена с резервом промеда
                " and wct.medcase_id is null and wct.prepatient_id is null and (wct.prepatientInfo is null or wct.prepatientInfo='')" +
                " and (wct.isDeleted is null or wct.isDeleted='0') and (wcd.isDeleted is null or wcd.isDeleted='0')";
        Collection<WebQueryResult> resultList = aService.executeNativeSql(sql,3);
        return resultList.isEmpty() ? null : resultList.iterator().next().get1().toString();


    }
    private JSONObject makeRecordOrAnnul(HttpServletRequest aRequest,  JSONObject root) {
        try {
            String token = getJsonField(root,"token");
            ApiUtil.init(aRequest,token);
            Long requestId = root.has("requestId") ? root.getLong("requestId") : null;
            if (requestId!=null) LOG.info("Запрос №"+requestId+" (makeRecordOrAnnul) получен :"+root); //debug
            String calendarId = getJsonField(root,"calendarTime_id");
            Long doctorPromedCode = root.has("doctorPromedId") ? root.getLong("doctorPromedId") : null;
            if (doctorPromedCode != null) { //Если получаем ИД врача в промеде - находим ИД самостоятельно
                String calendarDate = getJsonField(root,"recordCalendarDate");
                String calendarTime = getJsonField(root,"recordCalendarTime");
                IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
                calendarId = getWorkCalendarTimeByDateTime(doctorPromedCode, calendarDate, calendarTime,service);
                if (calendarId==null ) {
                    return ApiRecordUtil.getErrorJsonObj("BUSY_CALENDARTIME","Не найдено свободное время");
                }
            }
            if (calendarId==null ) {
                return ApiRecordUtil.getErrorJsonObj("NO_CALENDARTIME","Не указано время записи");
            }
            Long calendarTimeId = Long.valueOf(calendarId);
            String lastname = getJsonField(root,"lastname");
            String firstname = getJsonField(root,"firstname");
            String middlename = getJsonField(root,"middlename");
            String birthday = getJsonField(root,"birthday");
            String patientComment = getJsonField(root,"comment");
            String patientPhone = getJsonField(root,"phone");
            String recordType = getJsonField(root,"recordType");
        //    String debug = getJsonField(root,"debug");

            String annul = getJsonField(root,"annul");
            JSONObject list;

            ApiUtil.init(aRequest,token);
            IApiRecordService service =Injection.find(aRequest).getService(IApiRecordService.class);
            if (!StringUtil.isNullOrEmpty(annul)) {
                list = new JSONObject(new ApiRecordUtil().annulRecord(calendarTimeId,lastname,firstname,middlename, (birthday!=null?DateFormat.parseSqlDate(birthday,"yyyy-MM-dd"):null),service));
            } else {
                String recordInfo = ApiRecordUtil.recordPatient(calendarTimeId,lastname,firstname,middlename,(birthday!=null ? DateFormat.parseSqlDate(birthday,"yyyy-MM-dd") : null)
                       ,patientComment ,patientPhone ,service, recordType);
                if (recordInfo == null) {
                    list=ApiRecordUtil.getErrorJsonObj("No make record","ERROR_RECORD");
                } else {
                    list = new JSONObject(recordInfo);
                }
            }
            if (requestId!=null) LOG.info("Запрос №"+requestId+" (makeRecordOrAnnul) обработан, вот ответ: "+list); //debug
            list.put("requestId",requestId);
            return list;
        } catch (Exception e) {
            JSONObject ret =ApiRecordUtil.getErrorJsonObj(e.getLocalizedMessage(),e.toString());
            LOG.error("ERROR, We GET = "+root.toString()+", WE SEND = "+ret,e);
            return ret;
        }
    }

    private <T>T getJsonField(JSONObject obj, String aProperty) {
        if (obj.has(aProperty)) {
            try {
                Object o = obj.get(aProperty)!=null && !obj.get(aProperty).equals("") ? obj.get(aProperty) : null;
                return (T) o;
            } catch (JSONException e) {
                LOG.error(e);
            }
        }
        return null;
    }

}
