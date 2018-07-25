package ru.ecom.mis.web.dwr.oncology;


import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Collection;

/** Created by rkurbanov on 23.07.2018. */
public class OncologyServiceJs {


   /* public String insertOncologyCase(String json, HttpServletRequest aRequest) throws NamingException, ParseException, JSONException {

        JSONObject obj = new JSONObject(json);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String vocOncologyReasonTreat= getString(obj,"vocOncologyReasonTreat");
        String distantMetastasis= getString(obj,"distantMetastasis")==null?"false":getString(obj,"distantMetastasis");
        String suspiciononcologist= getString(obj,"suspiciononcologist")==null?"true":getString(obj,"suspiciononcologist");
        String stad =getString(obj,"stad");
        String tumor= getString(obj,"tumor");
        String nodus= getString(obj,"nodus");
        String metastasis= getString(obj,"metastasis");
        String consilium= getString(obj,"consilium");
        String typeTreatment= getString(obj,"typeTreatment");
        String surgTreatment= getString(obj,"surgTreatment");
        String lineDrugTherapy= getString(obj,"lineDrugTherapy");
        String cycleDrugTherapy= getString(obj,"cycleDrugTherapy");
        String typeRadTherapyName= getString(obj,"typeRadTherapyName");
        String sumDose=  obj.has("sumDose")?obj.get("sumDose").toString():"";
        String medCase= getString(obj,"medCase");
        String id_case= getString(obj,"id");



        Collection<WebQueryResult> res = service.executeNativeSql("select id from voconcologyn013 where code='"+typeTreatment+"'");

        for (WebQueryResult wqr : res) {
            typeTreatment = wqr.get1().toString();
        }

        res = service.executeNativeSql("INSERT INTO oncologycase(" +
                "suspiciononcologist, distantmetastasis, sumdose, medcase_id, " +
                "linedrugtherapy_id, cycledrugtherapy_id, typeradtherapy_id, stad_id, " +
                "tumor_id, nodus_id, metastasis_id, typetreatment_id, surgtreatment_id, " +
                "consilium_id, voconcologyreasontreat_id)\n" +
                "VALUES ("+suspiciononcologist+","+distantMetastasis+",'"+sumDose+"',"+medCase+", " +
                lineDrugTherapy+", "+cycleDrugTherapy+","+typeRadTherapyName+","+stad+", " +
                tumor+", "+nodus+", "+metastasis+", "+typeTreatment+", "+surgTreatment+", " +
                consilium+","+vocOncologyReasonTreat+") returning id");

        String id="";
        for (WebQueryResult wqr : res) {
            id = wqr.get1().toString();
        }

        if(id_case!=null){
            deleteOncologyCase(id_case,aRequest);
            editOncologyCase(id_case,id,aRequest);
        }

        return id;
    }*/

    public String insertDirection(String json, HttpServletRequest aRequest) throws JSONException, NamingException, ParseException {

        JSONObject obj = new JSONObject(json);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String typeDirection= getString(obj,"typeDirection");
        String methodDiagTreat= getString(obj,"methodDiagTreat");
        String medService =getString(obj,"medService");
        String medcase_id =getString(obj,"medcase_id");

        Collection<WebQueryResult> res = service.executeNativeSql("INSERT INTO oncologycase(medcase_id,suspiciononcologist, distantmetastasis)" +
                "values ("+medcase_id+",true,false) returning id");

        String id="";
        for (WebQueryResult wqr : res) {
            id = wqr.get1().toString();
        }

        service.executeUpdateNativeSql("insert into oncologydirection (oncologycase_id,typeDirection_id,methodDiagTreat_id,medService_id) " +
                "values("+id+","+typeDirection+","+methodDiagTreat+","+medService+") ");

        return id;
    }

    public void deleteOncologyCase(String id_case, HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from oncologydirection where oncologycase_id="+id_case);
        service.executeUpdateNativeSql("delete from oncologycase where id="+id_case);
    }

    public void fulldeleteOncologyCase(String id_case, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        deleteOncologyCase(id_case,aRequest);
        service.executeUpdateNativeSql("delete from oncologydiagnostic where oncologycase_id="+id_case);
        service.executeUpdateNativeSql("delete from oncologycontra where oncologycase_id="+id_case);
    }

    public void editOncologyCase(String id_case,String newId_case, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        service.executeUpdateNativeSql("update oncologydiagnostic set oncologycase_id="+newId_case+" where oncologycase_id="+id_case);
        service.executeUpdateNativeSql("update oncologycontra set oncologycase_id="+newId_case+" where oncologycase_id="+id_case);
    }



    public String LoadView(Long caseId, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> res = service.executeNativeSql("select suspicionOncologist, distantmetastasis from oncologycase where id="+caseId);
        String suspicionOncologist="";
        String distantmetastasis="";
        for (WebQueryResult wqr : res) {
            suspicionOncologist = wqr.get1().toString();
            distantmetastasis = wqr.get2().toString();
        }
        return suspicionOncologist+","+distantmetastasis;
    }

    public String LoadEdit(Long caseId, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> res = service.executeNativeSql("select code,name from voconcologyn013 where id = " +
                "(select typeTreatment_id from oncologycase  where id="+caseId+")");
        String code="";
        String name="";
        for (WebQueryResult wqr : res) {
            code = wqr.get1().toString();
            name = wqr.get2().toString();
        }
        return code+","+name;
    }


    /////--------------
    public String getCode(Long idDir, String vocName, HttpServletRequest aRequest) throws NamingException, ParseException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);//voconcologytypedirection
        Collection<WebQueryResult> res = service.executeNativeSql("select code from "+vocName+" where id="+idDir);
        String code="";
        for (WebQueryResult wqr : res) {
            code = wqr.get1().toString();
        }
        return code;
    }

    private String getString(JSONObject obj, String Alias) throws JSONException {

        return obj.has(Alias)
                ?(obj.get(Alias).toString().equals("")?null:obj.get(Alias).toString()):null;
    }

    private Long getLong(JSONObject obj, String Alias) throws JSONException {
        String str = getString(obj,Alias);
        if(str!=null) return Long.parseLong(str);
        return null;
    }


}
