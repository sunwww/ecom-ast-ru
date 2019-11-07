package ru.ecom.mis.ejb.form.workcalendar.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.workcalendar.PlanOphtHospitalByVisitForm;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by Milamesher on 06.11.2019.
 */
public class PlanOphtHospitalByVisitCreate implements IParentFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        PlanOphtHospitalByVisitForm form = (PlanOphtHospitalByVisitForm)aForm ;
        Date date = new Date();
        String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
        form.setCreateUsername(username);
        form.setCreateDate(DateFormat.formatToDate(date));

        EntityManager manager = aContext.getEntityManager();
        //MedCase parent = manager.find(MedCase.class, aParentId) ;
        List<Object[]> list = manager.createNativeQuery("select pre.id,to_char(pre.dateFrom,'dd.MM.yyyy') ||' '|| ml.name ||' '||mkb.code||' '|| vwf.name ||' '||wpat.lastname as info" +
                " from medcase vis " +
                " left join workcalendarhospitalbed pre on pre.patient_id = vis.patient_id" +
                " left join mislpu ml on ml.id=pre.department_id" +
                " left join vocbedtype vbt on vbt.id=pre.bedtype_id" +
                " left join vocidc10 mkb on mkb.id=pre.idc10_id" +
                " left join workfunction wf on wf.id=pre.workfunction_id" +
                " left join worker w on w.id=wf.worker_id" +
                " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
                " left join patient wpat on wpat.id=w.person_id" +
                " left join voceye e on e.id=pre.eye_id" +
                " where e.id is null and vis.id =:id and pre.dateFrom>=current_date order by pre.dateFrom").setParameter("id",aParentId).getResultList();
        if (!list.isEmpty()) {
            StringBuilder sb = new StringBuilder("ВНИМАНИЕ, у пациента созданы пред. госпитализации: ");
            for (Object[] obj: list){
                sb.append("<br><a href='entityView-smo_planHospitalByVisit.do?id=").append(obj[0]).append("'>")
                        .append(obj[1]).append("</a>");
            }
            FormMessage formMessage = new FormMessage(sb.toString());
            form.addMessage(formMessage);
        }
        list = manager.createNativeQuery("select pre.id,to_char(pre.dateFrom,'dd.MM.yyyy') ||' '|| e.name ||' ' || vwf.name ||' '||wpat.lastname as info" +
                " from medcase vis " +
                " left join workcalendarhospitalbed pre on pre.patient_id = vis.patient_id" +
                " left join mislpu ml on ml.id=pre.department_id" +
                " left join workfunction wf on wf.id=pre.workfunction_id" +
                " left join worker w on w.id=wf.worker_id" +
                " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
                " left join patient wpat on wpat.id=w.person_id" +
                " left join voceye e on e.id=pre.eye_id" +
                " where e.id is not null and vis.id =:id and pre.dateFrom>=current_date order by pre.dateFrom").setParameter("id",aParentId).getResultList();
        if (!list.isEmpty()) {
            StringBuilder sb = new StringBuilder("ВНИМАНИЕ, у пациента созданы пред. госпитализации на введение ингибиторов ангиогенеза: ");
            for (Object[] obj: list){
                sb.append("<br><a href='entityView-stac_planOphtHospitalByVisit.do?id=").append(obj[0]).append("'>")
                        .append(obj[1]).append("</a>");
            }
            FormMessage formMessage = new FormMessage(sb.toString());
            form.addMessage(formMessage);
        }
    }
}
