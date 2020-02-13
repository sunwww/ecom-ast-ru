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
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientFond;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
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

/**
 * Created by rkurbanov on 07.09.2018.
 */
public class FondCheckUtil {

    private static String theAddress = "http://192.168.4.3" ;
    private static String theLpu = "1";

    public static JSONArray syncByHospitalDenied(HttpServletRequest aRequest, String dateStart, String dateEnd)
            throws NamingException, ParserConfigurationException, SAXException, JSONException, IOException {

        String sql="select p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils\n" +
                "from medcase m\n" +
                "left join patient p on p.id = m.patient_id\n" +
                "where m.dtype = 'HospitalMedCase' \n" +
                "and m.deniedhospitalizating_id is not null \n" +
                "and m.datestart between '"+dateStart+"' and '"+dateEnd+"'\n" +
                "and p.patientfond_id is null";

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        List<Patient> patients = getPatients(service.executeNativeSql(sql));

        return FondCheckUtil.sync(aRequest,patients);
    }

    public static List<Patient> getPatients(Collection<WebQueryResult> list){
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
        return patients;
    }
    public static JSONArray sync(HttpServletRequest aRequest, List<Patient> patients) throws ParserConfigurationException,
            IOException, SAXException, JSONException, NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JSONArray jsonArray = new JSONArray();
        for (Patient patient:patients){
            String returnJson=FondCheckUtil.sync(aRequest,patient.getSnils(),"","","",patient.getLastname(),patient.getFirstname(),
                    patient.getMiddlename(), String.valueOf(patient.getBirthday()));

            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(returnJson).getAsJsonObject();

            if(!mainObject.has("error")){
                String patientFond_id = mainObject.get("patientFond_id").getAsString();
                service.executeUpdateNativeSql("update patient set patientfond_id="+patientFond_id+" where id="+patient.getId());
                service.executeUpdateNativeSql("update patientfond set patient="+patient.getId()+" where id="+patientFond_id);
                jsonArray.put(new JSONObject().put("patient_id",patient.getId()));

            }else {
                service.executeUpdateNativeSql("update patient set isCheckFondError=true where id="+patient.getId());
                jsonArray.put(new JSONObject().put("patient_id",patient.getId()).put("error","1"));
            }
        }
        return jsonArray;
    }

    /**
     * Проверка по базе фонда с сохранением
     * @param snils
     * @param series
     * @param number
     * @param type
     * @param lastname
     * @param firstname
     * @param middlename
     * @param birthday
     * @return JSON
     */
    protected static String sync(HttpServletRequest aRequest, String snils, String series, String number,
                                 String type, String lastname, String firstname, String middlename, String birthday)
            throws ParserConfigurationException, NamingException,
            SAXException, JSONException, IOException {

        String json = check(snils,series,number,type,lastname,firstname,middlename,birthday);

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(json).getAsJsonObject();
        if(!mainObject.has("error")) {
            PatientFond patientFond = parseJSON(json);

            IApiService service = Injection.find(aRequest).getService(IApiService.class);
            patientFond = (PatientFond) service.persistEntityObj(patientFond);
            return new JSONObject().put("patientFond_id", patientFond.getId()).toString();
        } else {
            return json;
        }
    }

    /**
     * Проверка по базе фонда без сохранения
     * @param snils
     * @param series
     * @param number
     * @param type
     * @param lastname
     * @param firstname
     * @param middlename
     * @param birthday
     * @return JSON
     */
    public static String check(String snils,
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
            System.out.println("Try check by snils");
            rz = FondCheckUtil.getRzbySnils(snils);
        }

        if(!is(rz) && is(lastname) && is(firstname) && is(middlename) && is(birthday)){
            System.out.println("Try check by fullname");
            rz = FondCheckUtil.getRZbyFullnameAndBD(lastname,firstname,middlename,birthday);
        }

        if(!is(rz) && is(number) && is(series) && is(type)){
            System.out.println("Try check by documents");
            rz = FondCheckUtil.getRZbyDocs(type,series,number);
        }

        if(!is(rz) && is(number) && is(series)){
            System.out.println("Try check by policy");
            rz = FondCheckUtil.getRzbyPolis(series,number);
        }
        if (!is(rz) && is(number)) {
            rz=number;
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
        return new JSONObject().put("error","1").toString();
    }

    public static String getDate(Integer day){
        Date today = new java.sql.Date(System.currentTimeMillis());
        return (new Date(today.getTime() + ( (day * 1000) * 60 * 60 * 24))).toString();
    }

    private static PatientFond parseJSON(String json){

        PatientFond patientFond = new PatientFond();

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(json).getAsJsonObject();

            JsonArray addreses = mainObject.getAsJsonArray("Address");
            JsonArray documents = mainObject.getAsJsonArray("Document");
            JsonArray polises = mainObject.getAsJsonArray("Polis");
            JsonArray persons = mainObject.getAsJsonArray("Person");

            for (JsonElement element : documents) {
                JsonObject document = element.getAsJsonObject();

                if (get(document, "date") != null)
                    patientFond.setDocumentDateIssued((getDate(document.get("date").getAsString())).toString());
                if (get(document, "number") != null)
                    patientFond.setDocumentNumber(document.get("number").getAsString());
                if (get(document, "series") != null)
                    patientFond.setDocumentSeries(document.get("series").getAsString());
                if (get(document, "type") != null) patientFond.setDocumentType(document.get("type").getAsString());
                if (get(document, "issued") != null)
                    patientFond.setDocumentWhomIssued(document.get("issued").getAsString());

            }

            for (JsonElement element : polises) {
                JsonObject policy = element.getAsJsonObject();
                if (policy.get("isActual").getAsString().equals("1")) {

                    Map<String, String> serialAndnumber = parseSeriesMedPolicy(policy.get("seriesAndNumber").getAsString());

                    for (Map.Entry mp : serialAndnumber.entrySet()) {
                        patientFond.setPolicyNumber(mp.getKey().toString());
                        patientFond.setPolicySeries(mp.getValue().toString());
                    }
                    if (get(policy, "dateStart") != null)
                        patientFond.setPolicyDateFrom((getDate(policy.get("dateStart").getAsString())));
                    if (get(policy, "dateEarlyEnd") != null)
                        patientFond.setPolicyDateTo((getDate(policy.get("dateEarlyEnd").getAsString())));
                    if (get(policy, "sk") != null) patientFond.setCompanyCode((policy.get("sk").getAsString()));
                    break;
                }
            }

            for (JsonElement element : persons) {
                JsonObject person = element.getAsJsonObject();

                if (get(person, "lastname") != null) patientFond.setLastname(person.get("lastname").getAsString());
                if (get(person, "firstname") != null) patientFond.setFirstname(person.get("firstname").getAsString());
                if (get(person, "middlename") != null)
                    patientFond.setMiddlename(person.get("middlename").getAsString());
                if (get(person, "dateDeath") != null)
                    patientFond.setDeathDate(getDate(person.get("dateDeath").getAsString()));
                if (get(person, "birthday") != null)
                    patientFond.setBirthday(getDate(person.get("birthday").getAsString()));
                if (get(person, "docSnils") != null) patientFond.setDoctorSnils(person.get("docSnils").getAsString());
                if (get(person, "lpu") != null) patientFond.setLpuAttached(person.get("lpu").getAsString());
                if (get(person, "codeAttach") != null)
                    patientFond.setDepartment(person.get("codeAttach").getAsString());
                if (get(person, "sp_attach") != null)
                    patientFond.setAttachedType(person.get("sp_attach").getAsString());
                if (get(person, "dateAttach") != null)
                    patientFond.setAttachedDate(getDateShort(person.get("dateAttach").getAsString()));
                if (get(person, "snils") != null) patientFond.setSnils(person.get("snils").getAsString());

            }

            for (JsonElement element : addreses) {
                JsonObject address = element.getAsJsonObject();

                if (get(address, "block") != null) patientFond.setHouseBuilding(address.get("block").getAsString());
                if (get(address, "aparment") != null) patientFond.setFlat(address.get("aparment").getAsString());

            }
        return patientFond;
    }

    private static Map<String,String> parseSeriesMedPolicy(String serNum){

        String[] pol = serNum.split(" ") ;
        Map<String,String> seriesNumber = new HashMap<>();
        if (pol.length>2) {
            seriesNumber.put(pol[0]+" "+pol[1],pol[2]);
        }
        else if (pol.length>1){
            seriesNumber.put(pol[0],pol[1]);
        }
        else {
            if (pol[0].length()>10) {
                seriesNumber.put(pol[0].substring(0,2)+" "+pol[0].substring(2,4),pol[0].substring(4));
            } else {
                seriesNumber.put(pol[0].substring(0,3),pol[0].substring(3));
            }
        }

        return seriesNumber;
    }
    protected static String getRzbySnils(String snils) throws IOException,
            ParserConfigurationException, SAXException {

        return parseXml((String)createRequest().get_RZ_from_SS(snils,theLpu),"rz");
    }

    protected static String getRzbyPolis(String series,String number) throws IOException,
            ParserConfigurationException, SAXException {

        return parseXml((String)createRequest().get_RZ_from_POLIS(series,number,theLpu),"rz");
    }

    protected static String getRZbyFullnameAndBD(String lastname,String firstname,String middlename,
                                                 String birthday)
            throws IOException,
            ParserConfigurationException, SAXException {

        return parseXml((String)createRequest().get_RZ_from_FIODR(lastname,firstname,middlename,birthday,theLpu),"rz");
    }

    protected static String getRZbyDocs(String type,String serial,String number)
            throws IOException,
            ParserConfigurationException, SAXException {

        return parseXml((String)createRequest().get_RZ_from_DOCS(type,serial,number,theLpu),"rz");
    }

    protected static String getPersoninfo(String rz) throws IOException, ParserConfigurationException, SAXException, JSONException {

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

        return parseXmltoJSONArray(result,params);
    }

    protected static String getPolis(String rz) throws IOException,
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

        return parseXmltoJSONArray(result,params);
    }


    protected static String getDocuments(String rz) throws IOException,
            ParserConfigurationException, SAXException, JSONException {

        String result = ((String)createRequest().get_DOCS_from_RZ(rz, theLpu))
                .replace("Windows-1252", "utf-8")  ;

        Map<String,String> params = new HashMap<>();
        params.put("doc_t","type");
        params.put("doc_s","series");
        params.put("doc_n","number");
        params.put("doc_d","date");
        params.put("doc_v","issued");


        return parseXmltoJSONArray(result,params);
    }

    private static String getAddress(String rz) throws IOException,
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

        return parseXmltoJSONArray(result,params);
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
        if(root.getElementsByTagName(param).getLength()>0){
            return root.getElementsByTagName(param).item(0).getTextContent();
        }else return "0";
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

    protected static boolean is(String var){
        return var!=null&&!var.trim().equals("")&&!var.equals("0");
    }

    protected static Date getDate(String datetime){
        java.util.Date parsedDate=null;
        try {
            parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate ==null ? null : new Date(parsedDate.getTime());
    }

    private static Date getDateShort(String datetime){
        java.util.Date parsedDate=null;
        try {
            parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate ==null ? null : new Date(parsedDate.getTime());
    }

    protected static String get(JsonObject obj, String name){
        if(obj.has(name)){
            if(obj.get(name)!=null && !obj.get(name).getAsString().equals("")){
                return obj.get(name).getAsString();
            }
        }
        return null;
    }

    public static JSONArray syncRecordTomorrow(HttpServletRequest aRequest, String dateStart)
            throws NamingException, ParserConfigurationException, SAXException, JSONException, IOException {

        String sql="select distinct pat.id,pat.lastname,pat.firstname,pat.middlename,pat.birthday,pat.snils\n" +
                "from workcalendartime wct \n" +
                "left join patient pat on pat.id=wct.prepatient_id \n" +
                "left join patientfond pf on pf.patient=pat.id  \n" +
                "left join workcalendarday wcd on wcd.id=wct.workcalendarday_id \n" +
                "where wcd.calendardate='" + dateStart + "'\n" +
                "and pat.id is not null and (pf.checkdate is null or pf.checkdate<current_date-7)\n" +
                "and (wct.isdeleted is null or wct.isdeleted=false)";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        List<Patient> patients = getPatients(service.executeNativeSql(sql));
        return FondCheckUtil.sync(aRequest,patients);
    }
}
