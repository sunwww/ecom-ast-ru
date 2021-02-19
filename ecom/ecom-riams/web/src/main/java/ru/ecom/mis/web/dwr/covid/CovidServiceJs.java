package ru.ecom.mis.web.dwr.covid;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Сервис для работы с ковидной картой оценки тяжести заболевания
 * Created by milamesher on 18.09.2020.
 */
public class CovidServiceJs {

    /**
     * Получить все значения из json.
     *
     * @param voc name
     * @return String Выборка в json
     * @throws NamingException
     */
    public String getVocInJson(String voc, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeSqlGetJson("select id,code,name from " + voc);
    }

    /**
     * Получить данные по нарушениям сознания.
     *
     * @param markId   CovidMark.id
     * @param aRequest HttpServletRequest
     * @return String Признаки тяжёлого состояния
     * @throws NamingException
     */
    public String getBadSosts(String markId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql(
                "select list(cast (voc.id as varchar)) from CovidMarkBadSost c" +
                        " left join vocbadsost voc on voc.id=c.badsost_id where c.covidmark_id=" + markId);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Получить id существующей в СЛС формы оценки тяжести заболевания
     *
     * @param aMedCaseId Sls.id
     * @return CovidMark.id
     */
    public String getIdIfAlreadyExists(Long aMedCaseId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select c.id from covidmark c where medcase_id =" + aMedCaseId + " limit 1");
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Получить, нужно ли создавать форму оценки риска (нужно, если выписной U* и формы ещё нет
     *
     * @param aMedCaseId Sls.id
     * @return 1 - да, 0 - нет
     */
    public String checkSlsU(Long aMedCaseId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select case when count(ds.id)>0 and count(cv.id)=0 then '1' else 0 end, count(cv.id) as cnt" +
                " from diagnosis ds" +
                " left join medcase sls on sls.id=ds.medcase_id" +
                " left join vocdiagnosisregistrationtype reg on reg.id=ds.registrationtype_id" +
                " left join vocidc10 idc on idc.id=ds.idc10_id" +
                " left join covidmark cv on cv.medcase_id = sls.id" +
                " where sls.dtype='HospitalMedCase' and reg.code='3' and idc.code like 'U%'" +
                " and sls.id=" + aMedCaseId + " limit 1");
        return list.isEmpty() ? "" : list.iterator().next().get1().toString() + "#" + list.iterator().next().get2().toString();
    }


    /**
     * Получить тяжесть состояние по id
     *
     * @param sostId VocSost.id
     * @return Vocsost.id
     */
    public String getSostById(Long sostId, HttpServletRequest request) throws NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select name from vocSost where id = " + sostId);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }
}