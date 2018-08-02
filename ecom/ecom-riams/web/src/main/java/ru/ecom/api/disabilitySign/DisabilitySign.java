package ru.ecom.api.disabilitySign;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.IApiService;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

import static ru.ecom.api.util.ApiUtil.cretePostRequest;
import static ru.ecom.api.util.ApiUtil.login;

/** Created by rkurbanov on 04.05.2018. */
@Path("/disabilitySign")
public class DisabilitySign {

    //TODO IP
    //select keyvalue from softconfig  where key = 'FSS_PROXY_SERVICE'
    String endpoint = "http://192.168.2.45:999";

    @GET
    @Path("exportDisabilityDocument")
    @Produces("application/json")
    public String exportDisabilityDocument(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken,
                                       @QueryParam("disDoc") String disDoc) throws SQLException, NamingException, JSONException {

        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String sql1 = "select\n" +
                "dd.issuedate as ISSUEDATE,\n" +
                "dd.id as DDID,\n" +
                "dd.patient_id as DD_PAT,\n" +
                "dc.patient_id as DC_PAT,\n" +
                "p.snils as SNILS,\n" +
                "p.lastname as SURNAME,\n" +
                "p.firstname as NAME,\n" +
                "p.middlename as PATRONIMIC\n" +
                ",case when (dc.placementservice is not null or dc.placementservice ='1') then '1' else '0' end as BOZ_FLAG\n" +
                ",dd.job as LPU_EMPLOYER\n" +
                ",case when (dd.workcombotype_id is null) then '1' else '0' end as LPU_EMPL_FLAG\n" +
                ",dd.number as LN_CODE\n" +
                ",case when dd.pervelnnumber is not null then dd.pervelnnumber else (select dd2.number from disabilitydocument dd2 where dd2.id = dd.prevdocument_id) end as PREV_LN\n" +
                ",case when (vddp.code ='2') then '0' else '1' end as PRIMARY_FLAG\n" +
                ",case when dd.elnduplicate is not null or dd.elnduplicate = '1' then '1' else case when (select count(a.id) from disabilitydocument a where a.duplicate_id=dd.id) >0 then '1' else '0'end  end as DUPLICATE_FLAG\n" +
                ",dd.issuedate as LN_DATE\n" +
                ",case when dd.anotherlpu_id is not null then dd.anotherlpuname else lpu.name end as LPU_NAME\n" +
                ",case when dd.anotherlpu_id is not null then dd.anotherlpuaddress else lpu.printaddress end as LPU_ADDRESS\n" +
                ",case when dd.anotherlpu_id is not null then dd.anotherlpuogrn else ''||lpu.ogrn end as LPU_OGRN\n" +
                ",p.birthday as BIRTHDAY\n" +
                ",case when sex.omccode = '1' then '0' else '1' end as GENDER\n" +
                ",vdr.codef as REASON1\n" +
                ",vdr2.code as REASON2\n" +
                ",vdr3.code as REASON3\n" +
                ",mkb.code as DIAGNOS\n" +
                ",dd.mainworkdocumentnumber as PARENT_CODE\n" +
                ",dd.sanatoriumdatefrom as DATE1\n" +
                ",dd.sanatoriumdateto as DATE2\n" +
                ",dd.sanatoriumticketnumber as VOUCHER_NO\n" +
                ",dd.sanatoriumogrn as VOUCHER_OGRN\n" +
                ",case when p1.id is not null and p1.id!=p.id then to_char(p1.birthday,'yyyy-MM-dd') else to_char(p12.birthday,'yyyy-MM-dd') end as SERV1_AGE\n" +
                ",case when p1.id is not null and p1.id!=p.id then vkr1.code else vkr1.oppositeRoleCode end as SERV1_RELATION_CODE\n" +
                ",case when p1.id is not null and p1.id!=p.id then p1.lastname||' '||p1.firstname||' '||p1.middlename else p12.lastname||' '||p12.firstname||' '||p12.middlename end as SERV1_FIO\n" +
                ",case when p2.id is not null and p2.id!=p.id then to_char(p2.birthday,'yyyy-MM-dd') else to_char(p22.birthday,'yyyy-MM-dd') end as SERV2_AGE\n" +
                ",case when p2.id is not null and p2.id!=p.id then vkr2.code else vkr2.oppositeRoleCode end as SERV2_RELATION_CODE\n" +
                ",case when p2.id is not null and p2.id!=p.id then p2.lastname||' '||p2.firstname||' '||p2.middlename else p22.lastname||' '||p22.firstname||' '||p22.middlename end as SERV2_FIO\n" +
                ",case when dc.earlypregnancyregistration is null then 'null' else case when dc.earlypregnancyregistration = '0' then '0' else '1' end end as PREGN12W_FLAG\n" +
                ",dd.hospitalizedfrom as HOSPITAL_DT1\n" +
                ",dd.hospitalizedto as HOSPITAL_DT2\n" +
                ",vddcr.name as CLOSE_REASON\n" +
                ",mss.assignmentdate as MSE_DT1\n" +
                ",mss.registrationdate as MSE_DT2\n" +
                ",mss.examinationdate as MSE_DT3\n" +
                ",vi.code as MSE_INVALID_GROUP\n" +
                ",dd.status_id as LN_STATE\n" +
                ",rvr.datefrom as HOSPITAL_BREACH_DT\n" +
                ",vrvr.codef as HOSPITAL_BREACH_CODE\n" +
                ",coalesce(vddcr.codef,'') as MSE_RESULT\n" +
                ",dd.otherclosedate as other_state_dt\n" +
                ",dd3.number as NEXT_LN_CODE\n" +
                ",Case when dd.isClose = '1' then '1' else '0' end as IS_CLOSE\n" +
                "from disabilitydocument dd\n" +
                "left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id\n" +
                "left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id\n" +
                "left join regimeviolationrecord rvr on rvr.disabilitydocument_id = dd.id\n" +
                "left join vocregimeviolationtype vrvr on vrvr.id = rvr.regimeviolationtype_id\n" +
                "left join disabilitycase dc on dc.id=dd.disabilitycase_id\n" +
                "left join patient p on p.id=dc.patient_id\n" +
                "left join vocdisabilitydocumentprimarity vddp on vddp.id=dd.primarity_id\n" +
                "left join vocsex sex on sex.id=p.sex_id\n" +
                "left join vocdisabilityreason vdr on vdr.id=dd.disabilityreason_id\n" +
                "left join vocdisabilityreason2 vdr2 on vdr2.id=dd.disabilityreason2_id\n" +
                "left join vocdisabilityreason vdr3 on vdr3.id=dd.disabilityreasonchange_id\n" +
                "left join vocidc10 mkb on mkb.id=dd.idc10final_id\n" +
                "left join kinsman k1 on k1.id=dc.nursingperson1_id\n" +
                "left join vockinsmanrole vkr1 on vkr1.id=k1.kinsmanrole_id\n" +
                "left join patient p1 on p1.id=k1.kinsman_id\n" +
                "left join patient p12 on p12.id=k1.person_id\n" +
                "left join kinsman k2 on k2.id=dc.nursingperson2_id\n" +
                "left join vockinsmanrole vkr2 on vkr2.id=k2.kinsmanrole_id\n" +
                "left join patient p2 on p2.id=k2.kinsman_id\n" +
                "left join patient p22 on p22.id=k2.person_id\n" +
                "left join medsoccommission mss on mss.disabilitydocument_id=dd.id\n" +
                "left join vocinvalidity vi on vi.id=mss.invalidity_id\n" +
                "left join mislpu lpu on lpu.id=1\n" +
                "left join mislpu anlpu on anlpu.id = dd.anotherlpu_id\n" +
                "where\n" +
                "p.snils is not null and p.snils != ''\n" +
                "and dd.id ="+disDoc;

        String sql2 = "select\n" +
                "dd.id as DDID\n" +
                ",disrec.datefrom as TREAT_DT1 \n" +
                ",disrec.dateto as TREAT_DT2\n" +
                ",case when disrec.docrole is null then vwf.name else disrec.docrole end as TREAT_DOCTOR_ROLE\n" +
                ",case when disrec.docname is null then docname.lastname ||' '|| docname.firstname ||' '|| docname.middlename else disrec.docname end as TREAT_DOCTOR \n" +
                ",case when disrec.vkrole is null then vwf2.name else disrec.vkrole end as TREAT_CHAIRMAN_ROLE\n" +
                ",case when disrec.vkname is null then vkname.lastname ||' '|| vkname.firstname ||' '|| vkname.middlename else disrec.vkname end as TREAT_CHAIRMAN\n" +
                ",disrec.isexport as isexport\n" +
                ",dsdoc.signaturevalue as signdoc\n" +
                ",dsdoc.certificate as certdoc\n" +
                ",dsdoc.digestvalue as digdoc\n" +
                ",dsdoc.counter as counterdoc\n" +
                ",dsvk.signaturevalue as signvk\n" +
                ",dsvk.certificate as certvk\n" +
                ",dsvk.digestvalue as digvk\n" +
                ",dsvk.counter as countervk\n" +
                "from disabilitydocument dd\n" +
                "left join disabilitycase dc on dc.id=dd.disabilitycase_id \n" +
                "left join patient p on p.id=dc.patient_id left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id\n" +
                "left join workfunction wf on wf.id = disrec.workfunction_id \n" +
                "left join worker w on w.id = wf.worker_id\n" +
                "left join patient docname on docname.id = w.person_id \n" +
                "left join VocWorkFunction vwf on vwf.id = wf.workFunction_id\n" +
                "left join workfunction wf2 on wf2.id = disrec.workfunctionadd_id\n" +
                "left join worker w2 on w2.id = wf2.worker_id\n" +
                "left join patient vkname on vkname.id = w2.person_id\n" +
                "left join VocWorkFunction vwf2 on vwf2.id = wf2.workFunction_id\n" +
                "left join disabilitysign dsvk on dsvk.externalid = disrec.id and dsvk.noactual = '0' and dsvk.code = 'vk'\n" +
                "left join disabilitysign dsdoc on dsdoc.externalid = disrec.id and dsdoc.noactual = '0' and dsdoc.code = 'doc'\n" +
                "where dd.id = "+disDoc+"\n" +
                "order by treat_dt1 asc";

        JSONObject body  = new JSONObject(service.executeSqlGetJsonObject(sql1));

        //String body2 = service.executeSqlGetJson(sql2,10);
        JSONArray arr = new JSONArray(service.executeSqlGetJson(sql2,10));
        body.put("treats",arr);

        System.out.println(body);
        //body.put("treats",service.executeSqlGetJsonArray(sql2));
        //System.out.println(body2);

        cretePostRequest(endpoint,"api/sign/getJsonDisabilityDoc",body.toString(),"text/html");

        return "{\"status\":\"ok\"}";
    }



    /**
     * Принимает JSON от удаленного сервиса
     * @param aRequest
     * @param data
     */
    @POST
    @Path("getJson")
    public void getJson(@Context HttpServletRequest aRequest, String data) {

        try {
            login("rkurbanov", aRequest);
            JsonParser parser = new JsonParser();
            JsonObject jparse = parser.parse(data).getAsJsonObject();

            DisabilityDocument disabilityDocument = new DisabilityDocument();
            disabilityDocument.setId(Long.parseLong(get(jparse, "DisabilityId")));

            System.out.println("FROM XML>>>" + data);
            ru.ecom.mis.ejb.domain.disability.DisabilitySign disabilitySign = new ru.ecom.mis.ejb.domain.disability.DisabilitySign();
            disabilitySign.setDisabilityDocumentId(disabilityDocument);
            disabilitySign.setCertificate(get(jparse, "Certificate"));
            disabilitySign.setCode(get(jparse, "Code"));
            disabilitySign.setCounter(get(jparse, "Counter"));
            disabilitySign.setDigestValue(get(jparse, "DigestValue"));
            disabilitySign.setSignatureValue(get(jparse, "SignatureValue"));
            disabilitySign.setElnNumber(get(jparse, "ELN"));
            disabilitySign.setExport(false);
            disabilitySign.setNoactual(false);

            if (get(jparse, "AnotherId") != null && !get(jparse, "AnotherId").equals("")) {
                disabilitySign.setExternalId(Long.valueOf(get(jparse, "AnotherId")));
            }

             //= null;

            try {
                IApiService service = Injection.find(aRequest).getService(IApiService.class);
                service.persistEntity(disabilitySign);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private String get(JsonObject obj, String name){
        if(obj.has(name)){
            System.out.println("has "+name);
            if(obj!=null && obj.get(name)!=null && !obj.get(name).getAsString().equals("")){
                return obj.get(name).getAsString();
            }
        }
        return null;
    }

    /**
     * Отправляет JSON на сервис
     * @param aRequest
     * @param aToken
     * @param disRecId
     * @param docType
     * @param response
     * @return
     * @throws NamingException
     * @throws SQLException
     * @throws JSONException
     */
    @GET
    @Path("/sendDisabilityRecordJson")
    @Produces("text/html")
    //@Produces(MediaType.APPLICATION_JSON)
    public String getTest(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken,
                          @QueryParam("disRecId") String disRecId,
                          @QueryParam("docType") String docType,
                          @Context HttpServletResponse response) throws NamingException, SQLException, JSONException {

        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String sql = "select\n" +
                "number as ddnum" +
                ",(row_number() OVER (order by disrec.datefrom asc) +2) as num\n" +
                ",disrec.id as recordId\n" +
                ",dd.id as DDID    \n" +
                ",disrec.datefrom as TREAT_DT1 \n" +
                ",disrec.dateto as TREAT_DT2\n" +
                ",case when disrec.docrole is null then vwf.name else disrec.docrole end as TREAT_DOCTOR_ROLE\n" +
                ",case when disrec.docname is null then docname.lastname ||' '|| docname.firstname ||' '|| docname.middlename else disrec.docname end as TREAT_DOCTOR \n" +
                ",case when disrec.vkrole is null then vwf2.name else disrec.vkrole end as TREAT_CHAIRMAN_ROLE\n" +
                ",case when disrec.vkname is null then vkname.lastname ||' '|| vkname.firstname ||' '|| vkname.middlename else disrec.vkname end as TREAT_CHAIRMAN\n" +
                ",case when disrec.isexport is null or disrec.isexport = false then false else true end as isexport\n" +
                ",'"+docType+"' as docType\n" +
                "from disabilitydocument dd\n" +
                "left join disabilitycase dc on dc.id=dd.disabilitycase_id \n" +
                "left join patient p on p.id=dc.patient_id left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id\n" +
                "left join workfunction wf on wf.id = disrec.workfunction_id \n" +
                "left join worker w on w.id = wf.worker_id\n" +
                "left join patient docname on docname.id = w.person_id \n" +
                "left join VocWorkFunction vwf on vwf.id = wf.workFunction_id\n" +
                "left join workfunction wf2 on wf2.id = disrec.workfunctionadd_id\n" +
                "left join worker w2 on w2.id = wf2.worker_id\n" +
                "left join patient vkname on vkname.id = w2.person_id\n" +
                "left join VocWorkFunction vwf2 on vwf2.id = wf2.workFunction_id\n" +
                "where dd.id = (select disabilitydocument_id  from disabilityrecord  where id  = "+disRecId+")\n" +
                "order by treat_dt1 asc \n";


        if(docType.equals("close")){
            sql="select "+
                    "'"+docType+"' as docType" +
                    ",dd.id as DDID" +
                    ",dd.number as ddnum" +
                    ",'2' as num" +
                    ",Case when dd.isClose = '1' then '1' else '0' end as IS_CLOSE\n" +
                    ",dd.otherclosedate as other_state_dt\n" +
                    ",coalesce(vddcr.codef,'') as MSE_RESULT\n" +
                    ",dd3.number as NEXT_LN_CODE\n" +
                    "from disabilitydocument dd " +
                    "left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id\n" +
                    "left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id\n" +
                    "where\n" +
                    "dd.id ="+disRecId;
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD");

        String json = service.executeSqlGetJson(sql,10,"data").toString();
        System.out.println(json);

        return cretePostRequest(endpoint,"api/sign/getJSON",json,"text/html");
    }



    @GET
    @Path("/executeSQL")
    @Produces(MediaType.APPLICATION_JSON)
    public String executeSQL(@QueryParam("sql") String sql,
                           @QueryParam("limit") @DefaultValue("100") Integer limit,
                           @Context HttpServletRequest aRequest, @WebParam(name="token") String aToken) throws NamingException, SQLException {

        ApiUtil.init(aRequest,aToken);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeSqlGetJson(sql,limit).toString();
    }
}
