package ru.ecom.mis.web.dwr.oncology;

import org.apache.log4j.Logger;
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
    private static final Logger LOG = Logger.getLogger(OncologyServiceJs.class);
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
        return res.isEmpty() ? "" : res.iterator().next().get1().toString();

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
    public String checkSLO(Long slsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (checkIsNoOMC(slsId, aRequest)) return "1";
        Collection<WebQueryResult> res = service.executeNativeSql("select id from oncologycase where medcase_id=" + slsId);
        return res.isEmpty() ? "0" : "1";
    }
    /**
     * Проверить, ОМС ли.
     *
     * @param slsId MedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean ОМС ли
     * @throws NamingException
     */
    private boolean checkIsNoOMC(Long slsId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> isOmc = service.executeNativeSql("select mc.id " +
                " from medcase mc left join vocservicestream vss on mc.servicestream_id=vss.id" +
                " where mc.id=" + slsId+" and vss.code='OBLIGATORYINSURANCE'");
        return isOmc.isEmpty();
    }
    /**
     * Проверить СПО на необходимость наличия онк. формы.
     *
     * @param slsId MedCase.id
     * @param aRequest HttpServletRequest
     * @return Boolean нужен ли онкологический случай
     * @throws NamingException
     */
    public Boolean checkSPO(Long slsId, HttpServletRequest aRequest) throws NamingException {

       IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (checkIsNoOMC(slsId, aRequest)) return false;
       Collection<WebQueryResult> res = service.executeNativeSql("select mc.id from medcase mc" +
               " left join oncologycase o on o.medcase_id=mc.id" +
               " left join vocIdc10 mkb on mkb.id=mc.idc10_id" +
               " where mc.id=" + slsId+" and mc.dateFinish is not null and mkb.code like 'C%' and o.id is null");
       return !res.isEmpty();
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
        Collection<WebQueryResult> list = service.executeNativeSql("select pat.lastname||' ' ||pat.firstname||' '||pat.middlename, " +
                "case when hmc.dtype='PolyclinicMedCase' then '1' else '0' end from medcase hmc " +
                "left join patient pat on pat.id=hmc.patient_id " +
                "where hmc.id=" + medcaseId);
         res.append(!list.isEmpty()? list.iterator().next().get1().toString()+"#" : "#");
         if (!list.isEmpty() && list.iterator().next().get2().toString().equals("1")) {  //если это - СПО
             //беру основной диагноз последнего визита
             list = service.executeNativeSql("select idc.code,ds.name from medcase spo" +
                     " left join medcase vis on vis.parent_id=spo.id and (vis.dtype='Visit' or vis.dtype='ShortMedCase') " +
                     " left join diagnosis ds on vis.id=ds.medcase_id" +
                     " left join vocidc10 idc on idc.id=ds.idc10_id" +
                     " left join vocprioritydiagnosis pr on pr.id=ds.priority_id" +
                     " where spo.id="+medcaseId +" and pr.code='1' and ds.id is not null  order by vis.datestart desc,vis.timeexecute desc limit 1");
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
            list = service.executeNativeSql("select idc.code,ds.name from diagnosis ds" +
                    " left join vocidc10 idc on idc.id=ds.idc10_id" +
                    " left join medcase hmc on hmc.id=ds.medcase_id" +
                    " left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id" +
                    " left join vocprioritydiagnosis pr on pr.id=ds.priority_id" +
                    " where hmc.dtype='HospitalMedCase' and reg.code='3' and pr.code='1' and hmc.id=" + medcaseId);
            if (!list.isEmpty()) {
                WebQueryResult wqr = list.iterator().next();
                res.append(wqr.get1()).append("# ").append(wqr.get2()).append(" (основной выписной)");
            }
            //Основной клинический последнего СЛО в СЛС
            if (list.isEmpty()) {
                list = service.executeNativeSql("select idc.code,ds.name from diagnosis ds" +
                        " left join vocidc10 idc on idc.id=ds.idc10_id" +
                        " left join medcase dmc on dmc.id=ds.medcase_id" +
                        " left join medcase hmc on hmc.id=dmc.parent_id" +
                        " left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id" +
                        " left join vocprioritydiagnosis pr on pr.id=ds.priority_id" +
                        " where  hmc.id=" + medcaseId + " and dmc.dtype='DepartmentMedCase' and reg.code='4' and pr.code='1'" +
                        " and dmc.transferdate is null"
                        );
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
        Collection<WebQueryResult> list = service.executeNativeSql("select t.code from oncologydirection d" +
                " left join vocOncologyTypeDirection t on d.typedirection_id=t.id" +
                " where d.oncologycase_id=" + caseId);
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
    public String editDirectionsByCase(String caseId,String json,String mkb,HttpServletRequest aRequest) throws NamingException {
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
        Collection<WebQueryResult> list = service.executeNativeSql("select distinct cast(n10.code as integer),n10.name" +
                " ,(select list(cast(case when(finishdate is null or finishdate>=current_date) then code else null end as varchar)) from VocOncologyN011 where marker=n10.code) as l1" +
                " ,(select list(case when(finishdate is null or finishdate>=current_date) then value else  null end) from VocOncologyN011 where marker=n10.code) as l2" +
                " from VocOncologyN010 n10" +
                " left join VocOncologyN011 n11 on n11.marker=n10.code" +
                " where n10.code<>'11' and (n10.finishdate is null or n10.finishdate>=current_date) order by cast(n10.code as integer)");
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
        if (!sqlSelect.contains(";") && !sqlFrom.contains(";") && !sqlWhere.contains(";") && !sqlOrdeby.contains(";")) {
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
        Collection<WebQueryResult> list = service.executeNativeSql("select t.id,case when t.code='1' then n8.code else n11.code end" +
                " from oncologydiagnostic d" +
                " left join voconcologydiagtype t on t.id=d.voconcologydiagtype_id" +
                " left join VocOncologyN008 n8 on n8.id=d.resulthistiology_id" +
                " left join VocOncologyN011 n11 on n11.id=d.valuemarkers_id" +
                " where d.oncologycase_id=" + caseId);
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
    public String getVocInJson(String voc,Boolean isCode,Boolean groupByCode,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql= isCode ? "select code as id,name from " : "select id,name from ";
        String tmpOrder= groupByCode ? "  order by cast(code as integer)" : "";
        return service.executeSqlGetJson(sql+voc+" where (finishdate is null or finishdate>=current_date) "+tmpOrder);
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
    public String checkDiagnosisOnkoForm(Long slsId, String concludingMkb, HttpServletRequest aRequest) throws NamingException {
        //Если есть хотя бы одна форма с основным выписным, то всё ок
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        if (checkIsNoOMC(slsId, aRequest)) return "";
        String res = "";
        if (concludingMkb.equals("")) return res;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select c.mkb||' '||mkb.name||' ('||prior.name||' '||reg.name||')',c.id from oncologycase c")
                    .append(" left join vocidc10 mkb on mkb.code=c.mkb")
                    .append(" left join medcase dmc on dmc.parent_id=c.medcase_id and dmc.dtype='DepartmentMedCase'")
                    .append(" left join diagnosis ds on (ds.medcase_id=dmc.id or ds.medcase_id=dmc.parent_id) and mkb.id=ds.idc10_id")
                    .append(" left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id")
                    .append(" left join vocprioritydiagnosis prior on prior.id=ds.priority_id")
                    .append(" where c.medcase_id=").append(slsId);// and reg.code='3' and pr.code='1'
            //Диагноз онкологической формы
            Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()+" and prior.code='1' and reg.code='3' "); //основной выписной
            if (list.isEmpty()) list = service.executeNativeSql(sql.toString()+" and prior.code='1' and reg.code='4' ");  //основной клинический
            boolean flag=false; //что есть такой же мкб - до этого момента уже выполнена проверка на наличие/отсутствие ЗНО
            String ds="",cId="";
            if (!list.isEmpty()) {
                WebQueryResult wqr = list.iterator().next();
                if (wqr.get1() != null && wqr.get2() != null) {
                    if (wqr.get1().toString().contains(concludingMkb)) flag = true; //проверяю по всем формам
                    ds = wqr.get1().toString();  //беру диагноз первой созданной формы
                    cId = wqr.get2().toString();
                }
            }
            if (!flag && !list.isEmpty()) {
                if (!concludingMkb.startsWith("C") && ds.startsWith("C")) res="Внимание! В случае установки этого диагноза неактуальная онкологическая форма будет удалена. Вы уверены?#"+cId;
                else if (!concludingMkb.startsWith("C") && !ds.startsWith("C")) res="";  //пока так: подозрение на подозрение - менять форму не нужно
                else if (checkIsOncoNeedChange(cId,concludingMkb,aRequest).equals("1") || (concludingMkb.startsWith("C") &&  !ds.startsWith("C"))) //актуализировать форму, если не подозрение
                    res="Внимание! Онкологическая форма была создана для диагноза " + ds + "! Уточните информацию (некоторые поля могут зависеть от диагноза) и сохраните.#"+cId;
                else if (checkIsOncoNeedChange(cId,concludingMkb,aRequest).equals("0") && concludingMkb.startsWith("C") &&  ds.startsWith("C")) //поменять диагноз формы, если не нужно актуализировать
                    service.executeUpdateNativeSql("update oncologycase set mkb='"+concludingMkb+"' where id="+cId);
            }
            else if (list.isEmpty()) {
                list = service.executeNativeSql(sql.toString().replace("c.mkb||' '||mkb.name||' ('||prior.name||' '||reg.name||')'","c.mkb"));  //получаю просто id формы
                if (!list.isEmpty()) {
                    WebQueryResult wqr = list.iterator().next();
                    ds = wqr.get1().toString();
                    cId = wqr.get2().toString();
                }
                if (!ds.equals(concludingMkb) && concludingMkb.startsWith("C"))
                    res="Внимание! Диагноз, с которым была создана онкологическая форма, был удалён либо форма была создана до сохранения диагноза. Уточните информацию (некоторые поля могут зависеть от диагноза) и сохраните.#"+cId;
            }
        } catch (Exception e) {
          LOG.error("Check onco error",e);
        }
        return res;
    }

    /**
     * Получить ФИО пациента и mkb с текстом из формы при просмотре/редактировании.
     *
     * @param caseId OncologyCase.id
     * @param mkb Диагноз, с которым была создана форма.
     * @param aRequest HttpServletRequest
     * @return json ФИО пациента, диагноз, с которым была создана форма и диагноз для актуализации формы(если есть)
     * @throws NamingException
     */
    public String getDsWithName(String caseId, String mkb, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select pat.lastname||' ' ||pat.firstname||' '||pat.middlename as pat" +
                " ,mkb.code||' '||mkb.name as oldmkb" +
                " ,case when '"+mkb+"'<>'' then (select code||' '||name from vocidc10 mkb where code='"+mkb+"') else '' end as newmkb" +
                " from medcase hmc" +
                " left join patient pat on pat.id=hmc.patient_id" +
                " left join oncologycase c on c.medcase_id=hmc.id" +
                " left join vocidc10 mkb on mkb.code=c.mkb" +
                " where c.id=" + caseId);
        JSONObject res = new JSONObject() ;
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            res.put("pat", w.get1())
                    .put("oldmkb", w.get2())
                    .put("newmkb", w.get3());
        }
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

    /**
     * Получить все медикаменты в json.
     *
     * @param caseId OncologyCase.id
     * @return String Выборка в json
     * @throws NamingException,SQLException
     */
    public String getMedsJson(String caseId,HttpServletRequest aRequest) throws NamingException, SQLException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeSqlGetJson("select drug.id as did,vocdrug.name,vocdrug.id as vdid,to_char(drugdate.date,'dd.mm.yyyy') as date" +
                " from oncologydrug drug" +
                " left join oncologydrugdate drugdate on drugdate.oncologydrug_id=drug.id" +
                " left join voconcologyn020 vocdrug on drug.drug_id=vocdrug.id" +
                " where drug.oncologycase_id="+caseId);
    }

    /**
     * Удалить все направления к подозрению на ЗНО и само подозрение.
     *
     * @param caseId OncologyCase.id
     * @throws NamingException
     */
    public void deleteDirectionsByCase(String caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from oncologydirection where oncologycase_id="+caseId);
        service.executeUpdateNativeSql("delete from oncologycase where id="+caseId);
    }

    /**
     * Удалить онкологическую форму (гистологии, маркеры и тп).
     *
     * @param caseId OncologyCase.id
     * @throws NamingException
     */
    public void deleteAllByCase(String caseId,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("delete from oncologycontra where oncologycase_id="+caseId);
        service.executeUpdateNativeSql("delete from oncologydiagnostic where oncologycase_id="+caseId) ;
        service.executeUpdateNativeSql("delete from oncologydrugdate where oncologydrug_id=ANY(select id from oncologydrug where oncologycase_id="+caseId+")") ;
        service.executeUpdateNativeSql("delete from oncologydrug where oncologycase_id="+caseId) ;
        service.executeUpdateNativeSql("delete from oncologycase where id="+caseId);
    }

    /**
     * Проверить, нужно ли актуализировать онкологическую форму при изменении одного С на другой.
     *
     * @param caseId OncologyCase.id
     * @throws NamingException
     */
    public String checkIsOncoNeedChange(String caseId, String ds, HttpServletRequest aRequest) throws NamingException {
        //есть вариант проще - проверять только основные цифры в диагнозе, например
        //C83.1 на C83.2 - актуализировать не надо, т.к. 83 совпадает
        //но в справочниках есть и значения с уточнением, например C44.3 и т.д.
        //поэтому нужен общий способ
        //если симптоматическое лечение, то не нужно актуализировать
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select case when rt.code='6' or rt.code='5' or (stad_id is null or (stad_id is not null and (select count(id) from voconcologyn002 where (ds='"+ds+"' or ds=substring('"+ds+"' from 1 for 3) ) and id=stad_id  and (finishdate is null or finishdate>=current_date))<>0))" +
                " and  (tumor_id is null or (tumor_id is not null and (select count(id) from voconcologyn003 where (ds='"+ds+"' or ds=substring('"+ds+"' from 1 for 3) ) and id=tumor_id and (finishdate is null or finishdate>=current_date))<>0))" +
                " and  (nodus_id is null or (nodus_id is not null and (select count(id) from voconcologyn004 where (ds='"+ds+"' or ds=substring('"+ds+"' from 1 for 3) ) and id=nodus_id and (finishdate is null or finishdate>=current_date))<>0))" +
                " and  (metastasis_id is null or (metastasis_id is not null and (select count(id) from voconcologyn005 where (ds='"+ds+"' or ds=substring('"+ds+"' from 1 for 3) ) and id=metastasis_id and (finishdate is null or finishdate>=current_date))<>0))" +
                " then '0' else '1' end" +
                " from oncologycase c left join voconcologyreasontreat rt on c.voconcologyreasontreat_id=rt.id where c.id="+caseId);
        return list.isEmpty()? "-1": list.iterator().next().get1().toString();
    }
}