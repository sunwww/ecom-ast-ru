package ru.ecom.api.disabilitySign;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import ru.ecom.api.IApiService;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.web.dwr.disability.DisabilityServiceJs;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.sql.SQLException;
import java.util.Collection;

import static ru.ecom.api.util.ApiUtil.cretePostRequest;
import static ru.ecom.api.util.ApiUtil.login;


@Path("/disabilitySign")
public class DisabilitySign {

    private static String usename;

    @GET
    @Path("exportDisabilityDocument")
    @Produces("application/json")
    public String exportDisabilityDocument(@Context HttpServletRequest aRequest,
                                           @WebParam(name = "token") String aToken,
                                           @QueryParam("disDoc") String disDoc)
            throws SQLException, NamingException, JSONException {

        ApiUtil.init(aRequest, aToken);
        DisabilityServiceJs serviceJs = new DisabilityServiceJs();
        return serviceJs.exportDisabilityDoc(disDoc, aRequest);
    }

    @POST
    @Path("getJson")
    public void getJson(@Context HttpServletRequest aRequest, String json) {

        try {
            login("66405d38-a173-4cb7-a1b6-3ada51c16ac5", aRequest);
            JsonParser parser = new JsonParser();
            JsonObject jparse = parser.parse(json).getAsJsonObject();

            DisabilityDocument disabilityDocument = new DisabilityDocument();
            disabilityDocument.setId(Long.parseLong(get(jparse, "DisabilityId")));

            ru.ecom.mis.ejb.domain.disability.DisabilitySign disabilitySign = new ru.ecom.mis.ejb.domain.disability.DisabilitySign();
            disabilitySign.setDisabilityDocumentId(disabilityDocument);
            disabilitySign.setCertificate(get(jparse, "Certificate"));
            disabilitySign.setCode(get(jparse, "Code"));
            disabilitySign.setCounter(get(jparse, "Counter"));
            disabilitySign.setDigestValue(get(jparse, "DigestValue"));
            disabilitySign.setSignatureValue(get(jparse, "SignatureValue"));
            disabilitySign.setElnNumber(get(jparse, "ELN"));
            disabilitySign.setSignatureType(get(jparse, "SignatureType"));
            disabilitySign.setСreateUsername(usename);

            if (get(jparse, "AnotherId") != null && !get(jparse, "AnotherId").equals("")) {
                disabilitySign.setExternalId(Long.valueOf(get(jparse, "AnotherId")));
            }

            try {
                IApiService service = Injection.find(aRequest).getService(IApiService.class);
                service.persistEntity(disabilitySign);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/sendDisabilityRecordJson")
    @Produces("text/html")
    public String getTest(@Context HttpServletRequest request,
                          @WebParam(name = "token") String token,
                          @QueryParam("disRecId") String disabilityRecordId,
                          @QueryParam("docType") String docType,
                          @QueryParam("username") String username,
                          @Context HttpServletResponse response) throws NamingException, SQLException, JSONException {

        ApiUtil.init(request, token);
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);

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
                ",'" + docType + "' as docType\n" +
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
                "where dd.id = (select disabilitydocument_id  from disabilityrecord  where id  = " + disabilityRecordId + ")\n" +
                "order by treat_dt1 asc \n";

        if (docType.equals("close")) {
            sql = "select \n" +
                    "'" + docType + "' as docType \n" +
                    ",dd.id as DDID \n" +
                    ",dd.number as ddnum \n" +
                    ",'2' as num \n" +
                    ",Case when dd.isClose = '1' then '1' else '0' end as IS_CLOSE\n" +
                    ",dd.otherclosedate as other_state_dt\n" +
                    ",coalesce(vddcr.codef,'') as MSE_RESULT\n" +
                    ",dd3.number as NEXT_LN_CODE\n" +
                    ",dateto+1 as returndt \n" +
                    "from disabilitydocument dd\n" +
                    "left join vocdisabilitydocumentclosereason vddcr on vddcr.id = dd.closereason_id\n" +
                    "left join disabilitydocument dd3 on dd3.prevdocument_id=dd.id\n" +
                    "left join disabilityrecord dr on dr.disabilitydocument_id = dd.id\n" +
                    "where\n" +
                    "dd.id =" + disabilityRecordId + " \n" +
                    "and dr.dateto = (select max(dateto) \n" +
                    "from disabilityrecord  \n" +
                    "where disabilitydocument_id = " + disabilityRecordId + ")";
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        String json = service.executeSqlGetJson(sql, 10, "data").toString();
        usename = username;
        return cretePostRequest(getEndpoint(request), "api/sign/getJSON", json, "text/html");
    }

    private String getEndpoint(@Context HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select keyvalue from softconfig  where key = 'FSS_PROXY_SERVICE'");
        String endpoint = "";
        for (WebQueryResult wqr : list) {
            endpoint = wqr.get1().toString();
        }
        return endpoint;
    }

    private String get(JsonObject obj, String name) {
        if (obj.has(name)) {
            if (obj != null && obj.get(name) != null && !obj.get(name).getAsString().equals("")) {
                return obj.get(name).getAsString();
            }
        }
        return null;
    }
}
