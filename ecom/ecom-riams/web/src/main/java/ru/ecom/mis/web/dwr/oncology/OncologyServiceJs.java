package ru.ecom.mis.web.dwr.oncology;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
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

    public String insertDirection(String json, String id, HttpServletRequest aRequest) throws JSONException, NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JSONObject obj = new JSONObject(json);
        String js = getString(obj,"list");
        JSONArray arr = new JSONArray(js);
        for (int i=0; i<arr.length(); i++) {
             obj = arr.getJSONObject(i);

            String typeDirection= getString(obj,"typeDirection");
            String methodDiagTreat= getString(obj,"methodDiagTreat");
            String medService =getString(obj,"medService");
            String medcase_id =getString(obj,"medCase");
            String date =getString(obj,"date");

            if (id.equals("")) {
                Collection<WebQueryResult> res = service.executeNativeSql("INSERT INTO oncologycase(medcase_id,suspiciononcologist, distantmetastasis)" +
                        "values ("+medcase_id+",true,false) returning id");

                for (WebQueryResult wqr : res) {
                        id = wqr.get1().toString();
                    }
            }
            if (date==null || date.equals(""))
                service.executeUpdateNativeSql("insert into oncologydirection (oncologycase_id,typeDirection_id,methodDiagTreat_id,medService_id) " +
                        "values(" + id + ",(select id from vocOncologyTypeDirection where code='" + typeDirection + "')," + methodDiagTreat + "," + medService + ") ");
            else
                service.executeUpdateNativeSql("insert into oncologydirection (oncologycase_id,typeDirection_id,methodDiagTreat_id,medService_id,date) " +
                        "values(" + id + ",(select id from vocOncologyTypeDirection where code='" + typeDirection + "')," + methodDiagTreat + "," + medService + ",to_date('" + date + "','dd.mm.yyyy')) ");
        }
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

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
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

    public String checkSLO(Long id_medcase, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> res = service.executeNativeSql("select count(id) from oncologycase where medcase_id=" + id_medcase);
        return !res.isEmpty() ? res.iterator().next().get1().toString() : "0";
    }

    public String checkSPO(Long id_medcase, HttpServletRequest aRequest) throws NamingException {

       IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
       Collection<WebQueryResult> res = service.executeNativeSql("select count(id) from oncologycase where medcase_id=" + id_medcase);
       int count = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;

       String result="false";
        if (count == 0) {

            res  = service.executeNativeSql("select dateFinish,idc10_id from medcase  where id=" + id_medcase);
            String finishdate = "";
            String idc10_id = "";

            for (WebQueryResult wqr : res) {
                if(wqr.get1()!=null) finishdate = wqr.get1().toString();
                if(wqr.get2()!=null) idc10_id = wqr.get2().toString();
            }
            if (finishdate != null && !finishdate.equals("")) {
                if (idc10_id != null && !idc10_id.equals("")) {
                    res = service.executeNativeSql("select case when count(id)>0 then true else false end  \n" +
                            "from vocidc10  where  code like 'C%' and id=" + idc10_id);

                    for (WebQueryResult wqr : res) {
                        result = wqr.get1().toString();
                    }
                    return result;
                }
            }
        }
        return result;
    }
    //Milamesher 01102018 получение ФИО пациента для новой формы
    public String getFIODsPatient(Long medcaseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        //ФИО пациента
        Collection<WebQueryResult> list = service.executeNativeSql("select pat.lastname||' ' ||pat.firstname||' '||pat.middlename from medcase hmc\n" +
                "left join patient pat on pat.id=hmc.patient_id\n" +
                "where hmc.id=" + medcaseId);
         res.append(!list.isEmpty()? list.iterator().next().get1().toString()+"#" : "#");
         //Основной выписной диагноз
         list = service.executeNativeSql("select idc.code,ds.name from diagnosis ds\n" +
                 "left join vocidc10 idc on idc.id=ds.idc10_id\n" +
                 "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                 "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                 "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                 "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id="+medcaseId);
         res.append(!list.isEmpty()? list.iterator().next().get1().toString():"");
         //Основной клинический последнего СЛО в СЛС
         if (list.isEmpty()) {
             list = service.executeNativeSql("select idc.code,ds.name from diagnosis ds\n" +
                     "left join vocidc10 idc on idc.id=ds.idc10_id\n" +
                     "left join medcase dmc on dmc.id=ds.medcase_id\n" +
                     "left join medcase hmc on hmc.id=dmc.parent_id\n" +
                     "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                     "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                     "where dmc.dtype='DepartmentMedCase' and reg.code='4' and pr.code='1' \n" +
                     "and dmc.transferdate is null\n" +
                     "and hmc.id="+medcaseId);
             if (!list.isEmpty()) {
                  WebQueryResult wqr = list.iterator().next();
                  res.append(wqr.get1() + " " + wqr.get2());
             }
         }
         return res.toString();
    }
    //Milamesher 03102018 получение code всех направлений к подозрению на ЗНО
    public String getAllDirectionCode(Long caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select t.code from oncologydirection d\n" +
                "left join vocOncologyTypeDirection t on d.typedirection_id=t.id\n" +
                "where d.oncologycase_id=" + caseId);
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#");
        }
        else res.append("##");
        return res.toString();
    }
    //Milamesher 03102018 удаление всех направлений к подозрению на ЗНО
    public String editDirectionsByCase(String idCase,String json,HttpServletRequest aRequest) throws JSONException, NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from oncologydirection where oncologycase_id="+idCase);
        return insertDirection(json,idCase,aRequest);
    }
    //Milamesher 03102018 получение даты направлений подозрения ЗНО
    public String getDateDirection(Long caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select to_char(date,'dd.mm.yyyy') from oncologydirection where oncologycase_id=" + caseId);
        if (!list.isEmpty()) res.append(list.iterator().next().get1());
        return res.toString();
    }
    //Milamesher 05102018 - получение маркеров с оценками
    public String getMarkersAndMarks(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select distinct cast(n10.code as integer),n10.name,\n" +
                "(select list(cast(code as varchar)) from VocOncologyN011 where marker=n10.code) as l1,\n" +
                "(select list(value) from VocOncologyN011 where marker=n10.code) as l2\n" +
                "from VocOncologyN010 n10 \n" +
                "left join VocOncologyN011 n11 on n11.marker=n10.code \n" +
                "where n10.code<>'11' order by cast(n10.code as integer)");
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("#").append(wqr.get4()).append("!");
        }
        else res.append("##");
        return res.toString();
    }
    //Milamesher 09102018 - получение хирургического лечения
    public String getTreatment(String sqlSelect, String sqlFrom, String sqlWhere, String sqlOrdeby,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        if (sqlSelect.indexOf(";")==-1 && sqlFrom.indexOf(";")==-1 &&  sqlWhere.indexOf(";")==-1 && sqlOrdeby.indexOf(";")==-1) {
            Collection<WebQueryResult> list = service.executeNativeSql("select " + sqlSelect + " from " + sqlFrom + " where " + sqlWhere + " order by " + sqlOrdeby);
            if (!list.isEmpty()) {
                for (WebQueryResult wqr : list)
                    res.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("#").append(wqr.get4()).append("!");
            } else res.append("##");
        }
        else res.append("##");
        return res.toString();
    }
    //Milamesher 26102018 - получние гистологии для вывода
    public String getHistology(String caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select t.id,case when t.code='1' then n8.code else n11.code end\n" +
                "from oncologydiagnostic d\n" +
                "left join voconcologydiagtype t on t.id=d.voconcologydiagtype_id\n" +
                "left join VocOncologyN008 n8 on n8.id=d.resulthistiology_id\n" +
                "left join VocOncologyN011 n11 on n11.id=d.valuemarkers_id\n" +
                "where d.oncologycase_id=" + caseId);
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2()).append("!");
        }
        else res.append("##");
        return res.toString();
    }
    //Milanesher 26102018 get dateBiops/Cons
    public String getDates(String caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select datebiops as d1,datecons as d2" +
                ",to_char(datebiops,'dd.mm.yyyy') as ch1,to_char(datecons,'dd.mm.yyyy') as ch2  from oncologycase where id="+caseId);
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("#").append(wqr.get4());
        }
        else res.append("##");
        return res.toString();
    }
    //Milanesher 29102018 получить данные по отказам и противопоказаниям
    public String getContras(String caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql(
                "select c.contraindicationandrejection_id,c.date,to_char(c.date,'dd.mm.yyyy') from OncologyContra c where c.oncologycase_id="+caseId);
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("!");
        }
        else res.append("##");
        return res.toString();
    }
    //12022019 получить voc в json  с учётом finishdate
    public String getVocInJson(String voc,Boolean isCode,Boolean groupByCode,HttpServletRequest aRequest) throws NamingException, SQLException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql=(isCode)? "select code as id,name from ":"select id,name from ";
        String tmpOrder=(groupByCode)? "  order by cast(code as integer)":"";
        return service.executeSqlGetJson(sql+voc+" where finishdate is null or finishdate < current_date "+tmpOrder,null);
    }
    //13022019 получить по id получить code vocOncologyReasonTreat
    public String getCodeOncology(String rtId, String voc,HttpServletRequest aRequest) throws NamingException, SQLException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select code,name from " + voc + " where id="+rtId);
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2());
        }
        return res.toString();
    }
}