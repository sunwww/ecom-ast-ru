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
import java.util.Collection;

/**
 * Сервис для работы с онкологической формой
 * Created by rkurbanov on 23.07.2018.
 */
public class OncologyServiceJs {
    /**
     * Добавить направление.
     *
     * @param json json с параметрами направлений
     * @param id OncologyCase.id
     * @param mkb диагноз, с которым создана онкологическая форма (подозрение)
     * @param aRequest HttpServletRequest
     * @return String OncologyCase.id (уже известный/созданный в методе)
     * @throws JSONException,NamingException
     */
    public String insertDirection(String json, String id, String mkb,HttpServletRequest aRequest) throws JSONException, NamingException {
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
                Collection<WebQueryResult> res = service.executeNativeSql("INSERT INTO oncologycase(medcase_id,suspiciononcologist, distantmetastasis,mkb)" +
                        "values ("+medcase_id+",true,false,'"+mkb+"') returning id");

                for (WebQueryResult wqr : res) {
                        id = wqr.get1().toString();
                    }
            }
            else service.executeUpdateNativeSql("update oncologycase set mkb='"+mkb+"' where id="+id); //обновить МКБ онк. формы
            if (date==null || date.equals(""))
                service.executeUpdateNativeSql("insert into oncologydirection (oncologycase_id,typeDirection_id,methodDiagTreat_id,medService_id) " +
                        "values(" + id + ",(select id from vocOncologyTypeDirection where code='" + typeDirection + "')," + methodDiagTreat + "," + medService + ") ");
            else
                service.executeUpdateNativeSql("insert into oncologydirection (oncologycase_id,typeDirection_id,methodDiagTreat_id,medService_id,date) " +
                        "values(" + id + ",(select id from vocOncologyTypeDirection where code='" + typeDirection + "')," + methodDiagTreat + "," + medService + ",to_date('" + date + "','dd.mm.yyyy')) ");
        }
        return id;
    }


    /**
     * Получить код по id (нужно для независимости от id в справочниках).
     *
     * @param idDir id
     * @param vocName Название справочника
     * @param aRequest HttpServletRequest
     * @return String code
     * @throws NamingException
     */
    public String getCode(Long idDir, String vocName, HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> res = service.executeNativeSql("select code from "+vocName+" where id="+idDir);
        String code="";
        for (WebQueryResult wqr : res) {
            code = wqr.get1().toString();
        }
        return code;
    }

    /**
     * Преобразовать элемент в json в строку.
     *
     * @param obj JSONObject
     * @param Alias Название элемента
     * @return String Строковое представление/null
     * @throws NamingException
     */
    private String getString(JSONObject obj, String Alias) throws JSONException {

        return obj.has(Alias)
                ?(obj.get(Alias).toString().equals("")?null:obj.get(Alias).toString()):null;
    }

    /**
     * Проверить госпитализацию на наличие онкологической формы.
     *
     * @param slsId MedCase.id
     * @param aRequest HttpServletRequest
     * @return String есть ли онкологический случай в стацинаре
     * @throws NamingException
     */
    public String checkSLO(String slsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (!checkIsOMC(slsId,aRequest).equals("1")) return "1";
        Collection<WebQueryResult> res = service.executeNativeSql("select count(id) from oncologycase where medcase_id=" + slsId);
        return !res.isEmpty() ? res.iterator().next().get1().toString() : "0";
    }
    /**
     * Проверить, ОМС ли.
     *
     * @param slsId MedCase.id
     * @param aRequest HttpServletRequest
     * @return String ОМС ли
     * @throws NamingException
     */
    public String checkIsOMC(String slsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> isOmc = service.executeNativeSql("select case when sstr.code='OBLIGATORYINSURANCE' then '1' else '0' end \n" +
                " from vocservicestream sstr left join medcase mc on mc.servicestream_id=sstr.id\n" +
                "where mc.id=" + slsId);
        return isOmc.iterator().next().get1().toString();
    }
    /**
     * Проверить СПО на необходимость наличия онк. формы.
     *
     * @param slsId MedCase.id
     * @param aRequest HttpServletRequest
     * @return String нужен ли онкологический случай
     * @throws NamingException
     */
    public String checkSPO(String slsId, HttpServletRequest aRequest) throws NamingException {

       IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
    if (!checkIsOMC(slsId,aRequest).equals("1")) return "false";
       Collection<WebQueryResult> res = service.executeNativeSql("select count(id) from oncologycase where medcase_id=" + slsId);
       int count = !res.isEmpty() ? Integer.parseInt(res.iterator().next().get1().toString()) : 0;

       String result="false";
        if (count == 0) {

            res  = service.executeNativeSql("select dateFinish,idc10_id from medcase  where id=" + slsId);
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

    /**
     * Получить ФИО пациента и диагноз для онкологической формы
     *
     * @param medcaseId MedCase.id
     * @param mkb Диагноз, с которым нужно создать форму
     * @param aRequest HttpServletRequest
     * @return String ФИО пациента, диагноз (код МКБ и текст), его тип
     * @throws NamingException
     */
    public String getFIODsPatient(Long medcaseId,String mkb,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        //ФИО пациента
        Collection<WebQueryResult> list = service.executeNativeSql("select pat.lastname||' ' ||pat.firstname||' '||pat.middlename from medcase hmc\n" +
                "left join patient pat on pat.id=hmc.patient_id\n" +
                "where hmc.id=" + medcaseId);
         res.append(!list.isEmpty()? list.iterator().next().get1().toString()+"#" : "#");
         list = service.executeNativeSql("select case when dtype='PolyclinicMedCase' then '1' else '0' end from medcase where id=" + medcaseId);
         if (!list.isEmpty() && list.iterator().next().get1().toString().equals("1")) {  //если это - СПО
             //беру основной диагноз последнего визита
             list = service.executeNativeSql("select idc.code,ds.name from diagnosis ds \n" +
                     "left join vocidc10 idc on idc.id=ds.idc10_id \n" +
                     "left join medcase vis on vis.id=ds.medcase_id and vis.dtype='Visit'\n" +
                     "left join medcase spo on spo.id=vis.parent_id and spo.dtype='PolyclinicMedCase'\n" +
                     "left join vocprioritydiagnosis pr on pr.id=ds.priority_id \n" +
                     "where pr.code='1' and spo.id="+medcaseId+" order by vis.id desc limit 1");
             if (!list.isEmpty()) {
                 WebQueryResult wqr = list.iterator().next();
                 res.append(wqr.get1()).append("# ").append(wqr.get2()).append(" (основной последнего визита в СПО)");
             }
         }
         //Передаваемый диагноз
        else if (!mkb.equals("")) {
            list = service.executeNativeSql("select code,name from vocidc10 mkb where code='"+mkb+"'");
            if (!list.isEmpty()) {
                WebQueryResult wqr = list.iterator().next();
                res.append(wqr.get1()).append("# ").append(wqr.get2()).append(" (основной выписной в выписке)");
            }
        }
        else {
            //Основной выписной диагноз
            list = service.executeNativeSql("select idc.code,ds.name from diagnosis ds\n" +
                    "left join vocidc10 idc on idc.id=ds.idc10_id\n" +
                    "left join medcase hmc on hmc.id=ds.medcase_id\n" +
                    "left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id\n" +
                    "left join vocprioritydiagnosis pr on pr.id=ds.priority_id\n" +
                    "where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=" + medcaseId);
            if (!list.isEmpty()) {
                WebQueryResult wqr = list.iterator().next();
                res.append(wqr.get1()).append("# ").append(wqr.get2()).append(" (основной выписной)");
            }
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
                        "and hmc.id=" + medcaseId);
                if (!list.isEmpty()) {
                    WebQueryResult wqr = list.iterator().next();
                    res.append(wqr.get1()).append("# ").append(wqr.get2()).append(" (основной клинический последнего СЛО в СЛС)");
                }
            }
        }
         return res.toString();
    }

    /**
     * Получить code всех направлений к подозрению на ЗНО
     *
     * @param caseId OncologyCase.id
     * @param aRequest HttpServletRequest
     * @return String Коды всех направлений в подозрении (только те, куда направляли)
     * @throws NamingException
     */
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

    /**
     * Удалить все направления к подозрению на ЗНО. При редактировании подозрения: удалить имеющиеся, добавить новые.
     *
     * @param caseId OncologyCase.id
     * @param json новые направления
     * @param mkb МКБ, с которым создана форма (для обновления в подозрении)
     * @param aRequest HttpServletRequest
     * @return String OncologyCase.id из insertDirection
     * @throws NamingException
     */
    public String editDirectionsByCase(String caseId,String json,String mkb,HttpServletRequest aRequest) throws JSONException, NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from oncologydirection where oncologycase_id="+caseId);
        return insertDirection(json,caseId,mkb,aRequest);
    }

    /**
     * Получить дату направлений подозрения ЗНО.
     *
     * @param caseId OncologyCase.id
     * @param aRequest HttpServletRequest
     * @return String Дата направления
     * @throws NamingException
     */
    public String getDateDirection(Long caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select to_char(date,'dd.mm.yyyy') from oncologydirection where oncologycase_id=" + caseId);
        if (!list.isEmpty()) res.append(list.iterator().next().get1());
        return res.toString();
    }

    /**
     * Получить маркеры с оценками для вывода пользователю.
     *
     * @param aRequest HttpServletRequest
     * @return String Все маркеры с оценками
     * @throws NamingException
     */
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

    /**
     * Получить лечение для вывода пользователю.
     *
     * @param sqlSelect список выборки
     * @param sqlFrom Voc
     * @param sqlWhere условие
     * @param sqlOrdeby порядок
     * @param aRequest HttpServletRequest
     * @return String Лечение для вывода пользователю
     * @throws NamingException
     */
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

    /**
     * Получить гистологию для вывода пользователю.
     *
     * @param caseId OncologyCase.id
     * @param aRequest HttpServletRequest
     * @return String Гистология для вывода пользователю
     * @throws NamingException
     */
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

    /**
     * Получить дату взятия биопсии и проведения консилиума.
     *
     * @param caseId OncologyCase.id
     * @param aRequest HttpServletRequest
     * @return String Даты взятия биопсии и проведения консилиума
     * @throws NamingException
     */
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

    /**
     * Получить данные по отказам и противопоказаниям.
     *
     * @param caseId OncologyCase.id
     * @param aRequest HttpServletRequest
     * @return String Отказы и противопоказания с датами
     * @throws NamingException
     */
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

    /**
     * Получить voc в json  с учётом finishDate для вывода пользователю в input (radio/checkbox).
     *
     * @param voc Voc
     * @param isCode Code/Id
     * @param groupByCode группировка ко code?
     * @param aRequest HttpServletRequest
     * @return String Выборка в json
     * @throws NamingException,SQLException
     */
    public String getVocInJson(String voc,Boolean isCode,Boolean groupByCode,HttpServletRequest aRequest) throws NamingException, SQLException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql=(isCode)? "select code as id,name from ":"select id,name from ";
        String tmpOrder=(groupByCode)? "  order by cast(code as integer)":"";
        return service.executeSqlGetJson(sql+voc+" where finishdate is null or finishdate < current_date "+tmpOrder,null);
    }

    /**
     * Получить по id code и name из voc (например, vocOncologyReasonTreat).
     *
     * @param rtId id
     * @param voc Voc
     * @param aRequest HttpServletRequest
     * @return String Выборка
     * @throws NamingException
     */
    public String getCodeOncology(String rtId, String voc,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select code,name from " + voc + " where id="+rtId);
        if (!list.isEmpty()) {
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2());
        }
        return res.toString();
    }

    /**
     * Получить тип направления и услугу в подозрении.
     *
     * @param caseId OncologyCase.id
     * @param aRequest HttpServletRequest
     * @return String Тип направления и услуга
     * @throws NamingException
     */
    public String getMethodAndService(String caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql("select dtr.id as dtrid,ms.id as msid,dtr.name as n1,ms.name as n2 from oncologydirection d" +
                " left join vocmedservice ms on ms.id=d.medservice_id" +
                " left join VocOncologyMethodDiagTreat dtr on dtr.id=d.methoddiagtreat_id" +
                " where d.oncologycase_id="+caseId);
        if (!list.isEmpty())
            for (WebQueryResult wqr : list) res.append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("#").append(wqr.get4());
        return res.toString();
    }

    /**
     * Проверить на наличие онкологической формы с основным выписным диагнозом.
     *
     * @param slsId HospitalMedCase.id
     * @param concludingMkb Основной выписной, указанный в выписке
     * @param aRequest HttpServletRequest
     * @return String Пустая строка, если всё в порядке/сообщение об ошибке
     * @throws NamingException
     */
    public String checkDiagnosisOnkoForm(String slsId, String concludingMkb, HttpServletRequest aRequest) throws NamingException {
        //Если есть хотя бы одна форма с основным выписным, то всё ок
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (!checkIsOMC(slsId,aRequest).equals("1")) return "";
        String res = "";
        if (concludingMkb.equals("")) return res;
        //Диагноз онкологической формы
        Collection<WebQueryResult> list = service.executeNativeSql(
                "select mkb||' '||mkb.name,c.id from oncologycase c left join vocidc10 mkb on mkb.code=c.mkb where c.medcase_id="+slsId);
        Boolean flag=false; //что есть такой же мкб - до этого момента уже выполнена проверка на наличие/отсутствие ЗНО
        String ds="",cId="";
        for (WebQueryResult wqr : list) {
            if (wqr.get1()!=null && wqr.get2()!=null) {
                if (wqr.get1().toString().contains(concludingMkb)) flag = true; //проверяю по всем формам
                if (ds.equals("")) {
                    ds=wqr.get1().toString();  //беру диагноз первой созданной формы
                    cId=wqr.get2().toString();
                }
            }
        }
        if (!flag) res="Внимание! Онкологическая форма была создана для диагноза " + ds + "! Просьба уточнить диагноз в ЗНО.#"+cId;
        return res;
    }

    /**
     * Получить ФИО пациента и mkb с текстом из формы при просмотре/редактировании.
     *
     * @param caseId OncologyCase.id
     * @param mkb Диагноз, с которым была создана форма.
     * @param aRequest HttpServletRequest
     * @return String Пустая строка, если всё в порядке/сообщение об ошибке
     * @throws NamingException
     */
    public String getDsWithName(String caseId, String mkb, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder res = new StringBuilder();
        //ФИО пациента
        Collection<WebQueryResult> list = service.executeNativeSql("select pat.lastname||' ' ||pat.firstname||' '||pat.middlename from medcase hmc\n" +
                "left join patient pat on pat.id=hmc.patient_id \n" +
                "left join oncologycase c on c.medcase_id=hmc.id\n" +
                "where c.id=" + caseId);
        res.append(!list.isEmpty()? list.iterator().next().get1().toString()+"#" : "#");
        list =  mkb.equals("")?
                service.executeNativeSql("select mkb||' '||mkb.name from oncologycase c left join vocidc10 mkb on mkb.code=c.mkb where c.id="+caseId)
                : service.executeNativeSql("select code||' '||name from vocidc10 mkb where code='"+mkb +"'");
        res.append(!list.isEmpty() && list.iterator().next().get1()!=null ? list.iterator().next().get1().toString(): "#");
        return res.toString();
    }

    /**
     * Получить тип MedCase.
     *
     * @param medCaseId MedCase.id
     * @param aRequest HttpServletRequest
     * @return String hmc: 0, spo: 1, other: -1
     * @throws NamingException
     */
    public String getMedCaseDtype(String medCaseId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select case when dtype='HospitalMedCase' then '0' else case when dtype='PolyclinicMedCase' then '1' else '-1' end end from medcase where id="+medCaseId);
        return list.isEmpty()? "-1": list.iterator().next().get1().toString();
    }
}