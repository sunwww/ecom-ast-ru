package ru.ecom.api.fondCheck;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tempuri.WS_MES_SERVER.wsdl.WSLocator;
import org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ru.ecom.api.IApiService;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientFond;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** Created by rkurbanov on 21.08.2018. */

@Path("/foncCheck")
public class FondCheck {

    private static String theAddress = "http://192.168.4.3" ;
    private static String theLpu = "1";

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
        return check(snils,series,number,type,lastname,firstname,middlename,birthday);
    }

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

        return sync(aRequest,snils,series,number,type,lastname,firstname,middlename,birthday);
    }

    public String sync(HttpServletRequest aRequest,String snils,String series,String number,
                       String type,String lastname,String firstname,String middlename, String birthday)
            throws ParserConfigurationException, NamingException,
            SAXException, JSONException, IOException {

        PatientFond patientFond = new PatientFond();
        String json = check(snils,series,number,type,lastname,firstname,middlename,birthday);
        patientFond= parseJSON(json);
        IApiService service = Injection.find(aRequest).getService(IApiService.class);
        patientFond =(PatientFond)service.persistEntityObj(patientFond);
        return new JSONObject().put("patientFond_id",patientFond.getId()).toString();
    }

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

        String sql="select id,lastname,firstname,middlename,birthday,snils from patient where patientfond_id is null limit "+limit;
        if(patient_id!=null)  sql ="select id,lastname,firstname,middlename,birthday,snils from patient where id="+patient_id+" limit "+limit;

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
                patient.setBirthday((Date) wqr.get5());
                patient.setSnils(wqr.get6().toString());
                patients.add(patient);
            }
        }

        JSONArray jsonArray = new JSONArray();
        for (Patient patient:patients){
            System.out.println(patient.getId());
            String retId=sync(aRequest,patient.getSnils(),"","","",patient.getLastname(),patient.getFirstname(),
                    patient.getMiddlename(), String.valueOf(patient.getBirthday()));

            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(retId).getAsJsonObject();
            retId = mainObject.get("patientFond_id").getAsString();

            service.executeUpdateNativeSql("update patient set patientfond_id="+retId+" where id="+patient.getId());
            jsonArray.put(new JSONObject().put("patient_id",patient.getId()));
        }

        return new JSONObject().put("patients",jsonArray).toString();
    }

    private PatientFond parseJSON(String json){

        PatientFond patientFond = new PatientFond();

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(json).getAsJsonObject();
        JsonArray addreses = mainObject.getAsJsonArray("Address");
        JsonArray documents = mainObject.getAsJsonArray("Document");
        JsonArray polises = mainObject.getAsJsonArray("Polis");
        JsonArray persons = mainObject.getAsJsonArray("Person");

        for(JsonElement element:documents){
            JsonObject document = element.getAsJsonObject();

            if (get(document,"date")!=null) patientFond.setDocumentDateIssued((getDate(document.get("date").getAsString())).toString());
            if (get(document,"number")!=null) patientFond.setDocumentNumber(document.get("number").getAsString());
            if (get(document,"series")!=null) patientFond.setDocumentSeries(document.get("series").getAsString());
            if (get(document,"type")!=null) patientFond.setDocumentType(document.get("type").getAsString());
            if (get(document,"issued")!=null) patientFond.setDocumentWhomIssued(document.get("issued").getAsString());

        }

        for(JsonElement element:polises){
            JsonObject policy = element.getAsJsonObject();
            if(policy.get("isActual").getAsString().equals("1")) {

                String serialAndnumber=policy.get("seriesAndNumber").getAsString();
                String[] pol = serialAndnumber.split(" ") ;
                String serPol;
                String numPol;

                if (pol.length>2) {
                    serPol = pol[0]+" "+pol[1] ;
                    numPol = pol[2] ;
                }
                else if (pol.length>1){
                    serPol = pol[0] ;
                    numPol = pol[1] ;
                }
                else {
                    if (pol[0].length()>10) {
                        serPol = pol[0].substring(0,2)+" "+pol[0].substring(2,4) ;
                        numPol = pol[0].substring(4) ;
                    } else {
                        serPol = pol[0].substring(0,3) ;
                        numPol = pol[0].substring(3) ;
                    }
                }

                patientFond.setPolicyNumber(numPol);
                patientFond.setPolicySeries(serPol);
                if (get(policy, "dateStart") != null) patientFond.setPolicyDateFrom((getDate(policy.get("dateStart").getAsString())));
                if (get(policy, "dateEarlyEnd") != null) patientFond.setPolicyDateTo((getDate(policy.get("dateEarlyEnd").getAsString())));
                if (get(policy, "sk") != null) patientFond.setCompanyCode((policy.get("sk").getAsString()));
            }
        }

        for(JsonElement element:persons){
            JsonObject person = element.getAsJsonObject();

            if (get(person,"lastname")!=null) patientFond.setLastname(person.get("lastname").getAsString());
            if (get(person,"firstname")!=null) patientFond.setFirstname(person.get("firstname").getAsString());
            if (get(person,"middlename")!=null) patientFond.setMiddlename(person.get("middlename").getAsString());
            if (get(person,"dateDeath")!=null) patientFond.setDeathDate(getDate(person.get("dateDeath").getAsString()));
            if (get(person,"birthday")!=null) patientFond.setBirthday(getDate(person.get("birthday").getAsString()));
            if (get(person,"docSnils")!=null) patientFond.setDoctorSnils(person.get("docSnils").getAsString());
            if (get(person,"lpu")!=null) patientFond.setLpuAttached(person.get("lpu").getAsString());
            if (get(person,"codeAttach")!=null) patientFond.setDepartment(person.get("codeAttach").getAsString());
            if (get(person,"sp_attach")!=null) patientFond.setAttachedType(person.get("sp_attach").getAsString());
            if (get(person,"dateAttach")!=null) patientFond.setAttachedDate(getDateShort(person.get("dateAttach").getAsString()));
            if (get(person,"snils")!=null) patientFond.setSnils(person.get("snils").getAsString());

        }

        for(JsonElement element:addreses) {
            JsonObject address = element.getAsJsonObject();

            if (get(address,"block")!=null) patientFond.setHouseBuilding(address.get("block").getAsString());
            if (get(address,"aparment")!=null) patientFond.setFlat(address.get("aparment").getAsString());
            if (get(address,"middlename")!=null) patientFond.setMiddlename(address.get("middlename").getAsString());

        }
        return patientFond;
    }

    public String check(String snils,
                        String series,
                        String number,
                        String type,
                        String lastname,
                        String firstname,
                        String middlename,
                        String birthday) throws ParserConfigurationException, SAXException,
            IOException, JSONException, NamingException {
        String rz=null;

        if(is(snils)){
            rz =  getRzbySnils(snils);
        }

        if(is(number) && is(series)){
            rz = getRzbyPolis(series,number);
        }

        if(is(number) && is(series) && is(type)){
            rz = getRZbyDocs(type,series,number);
        }

        if(is(lastname) && is(firstname) && is(middlename) && is(birthday)){
            rz = getRZbyFullnameAndBD(lastname,firstname,middlename,birthday);
        }

        if(is(rz)){
            JSONObject json = new JSONObject();
            json.put("Person", new JSONArray(getPersoninfo(rz)))
                    .put("Address",  new JSONArray(getAddress(rz)))
                    .put("Document",  new JSONArray(getDocuments(rz)))
                    .put("Polis",  new JSONArray(getPolis(rz)))
                    .put("rz",rz);

            return json.toString();
        }

        return new JSONObject().put("RZ is null","Error").toString();
    }

    private String getRzbySnils(String snils) throws IOException, ParserConfigurationException, SAXException {
        return parseXml((String)createRequest().get_RZ_from_SS(snils,theLpu),"rz");
    }

    private String getRzbyPolis(String series,String number) throws IOException, ParserConfigurationException, SAXException {
        return parseXml((String)createRequest().get_RZ_from_POLIS(series,number,theLpu),"rz");
    }

    private String getRZbyFullnameAndBD(String lastname,String firstname,String middlename,
                                        String birthday)
            throws IOException, ParserConfigurationException, SAXException {
        return parseXml((String)createRequest().get_RZ_from_FIODR(lastname,firstname,middlename,birthday,theLpu),"rz");
    }

    private String getRZbyDocs(String type,String serial,String number)
            throws IOException, ParserConfigurationException, SAXException {
        return parseXml((String)createRequest().get_RZ_from_DOCS(type,serial,number,theLpu),"rz");
    }

    public String getPersoninfo(String rz) throws IOException, NamingException, ParserConfigurationException, SAXException, JSONException {

        String result = ((String)createRequest().get_FIODR_from_RZ(rz, theLpu))
                .replace("Windows-1252", "utf-8") ;

        Map<String,String> params = new HashMap<>();
        params.put("f","lastname");
        params.put("i","firstname");
        params.put("o","middlename");
        params.put("dr","birthday");
        params.put("ss","snils");
        params.put("_dead","isDead");
        params.put("datadead","dateDeath");
        params.put("lpu","lpu");
        params.put("sp_prik","sp_attach");
        params.put("date_prik","dateAttach");
        params.put("ssd","docSnils");
        params.put("kodpodr","codeAttach");

        return parseXmltoJSONArray(result,params).toString();
    }

    public String getPolis(String rz) throws IOException, NamingException,
            ParserConfigurationException, SAXException, JSONException {


        String result = ((String)createRequest().get_POLIS_from_RZ(rz, theLpu))
                .replace("Windows-1252", "utf-8")  ;

        Map<String,String> params = new HashMap<>();
        params.put("pz_actual","isActual");
        params.put("vid_pol","typePolicy");
        params.put("sk","sk");
        params.put("sn_pol","seriesAndNumber");
        params.put("datap","dateIssue");
        params.put("datapp","dateStart");
        params.put("datape","dateEnd");
        params.put("d_dosrochno","dateEarlyEnd");
        params.put("data_izgot","dateCreate");
        params.put("blank","blank");

        return parseXmltoJSONArray(result,params).toString();
    }


    public String getDocuments(String rz) throws IOException, NamingException,
            ParserConfigurationException, SAXException, JSONException {

        String result = ((String)createRequest().get_DOCS_from_RZ(rz, theLpu))
                .replace("Windows-1252", "utf-8")  ;

        Map<String,String> params = new HashMap<>();
        params.put("doc_t","type");
        params.put("doc_s","series");
        params.put("doc_n","number");
        params.put("doc_d","date");
        params.put("doc_v","issued");


        return parseXmltoJSONArray(result,params).toString();
    }

    public String getAddress(String rz) throws IOException, NamingException,
            ParserConfigurationException, SAXException, JSONException {

        String result = ((String)createRequest().get_ADRES_from_RZ(rz, theLpu))
                .replace("Windows-1252", "utf-8")  ;

        Map<String,String> params = new HashMap<>();
        params.put("ssity","codeCity");
        params.put("province","codeRegion");
        params.put("region","code");
        params.put("sity","city");
        params.put("rayon","district");
        params.put("street","street");
        params.put("street_gni","houseGni");
        params.put("house","house");
        params.put("section","block");
        params.put("apartment","aparment");
        params.put("street_t","streetT");

        return parseXmltoJSONArray(result,params).toString();
    }


    private static WS_MES_SERVERSoapPort createRequest(){

        WSLocator service = new WSLocator();
        WS_MES_SERVERSoapPort soap=null;
        service.setWS_MES_SERVERSoapPortEndpointAddress(theAddress + "/ws/WS.WSDL");
        try {
            soap = service.getWS_MES_SERVERSoapPort();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return soap;
    }

    private static Document stringToDoc(String xml) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    private static String parseXml(String xml,String param) throws IOException, SAXException,
            ParserConfigurationException {

        Element root = stringToDoc(xml).getDocumentElement();
        return root.getElementsByTagName(param).item(0).getTextContent();
    }

    private static String parseXmltoJSONArray(String xml, Map<String,String> params) throws IOException, SAXException,
            ParserConfigurationException, JSONException {

        Element root = stringToDoc(xml).getDocumentElement();
        JSONArray array = new JSONArray();
        int size = root.getElementsByTagName("cur1").getLength();

        for(int i=0;i<size;i++) {
            JSONObject body = new JSONObject();
            for (Map.Entry map : params.entrySet()){
                Element res = (Element) root.getElementsByTagName(map.getKey().toString()).item(i);
                if(res!=null){
                    body.put(map.getValue().toString(),res.getTextContent());
                }
            }
            array.put(body);
        }

        return array.toString();
    }

    private boolean is(String var){
        return var!=null&&!var.equals("")?true:false;
    }

    private Date getDate(String datetime){
        java.util.Date parsedDate=null;
        try {
            parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(parsedDate.getTime());
    }

    private Date getDateShort(String datetime){
        java.util.Date parsedDate=null;
        try {
            parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(parsedDate.getTime());
    }

    private String get(JsonObject obj,String name){
        if(obj.has(name)){
            if(obj.get(name)!=null && !obj.get(name).getAsString().equals("")){
                return obj.get(name).getAsString();
            }
        }
        return null;
    }
}
