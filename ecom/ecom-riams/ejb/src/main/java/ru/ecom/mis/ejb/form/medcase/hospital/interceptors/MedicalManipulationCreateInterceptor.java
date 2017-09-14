package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction;
import ru.ecom.mis.ejb.form.medcase.hospital.BandageForm;
import ru.ecom.mis.ejb.form.medcase.hospital.MedicalManipulationForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Администратор on 01.09.2017.
 */
public class MedicalManipulationCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {

        EntityManager manager = aContext.getEntityManager();

        MedCase parentSSL = manager.find(MedCase.class, aParentId) ;
        BandageForm form=(BandageForm)aForm;
        MedicalManipulationForm mform=(MedicalManipulationForm)aForm;
        if (parentSSL!=null && parentSSL instanceof HospitalMedCase) {
            HospitalMedCase hosp = (HospitalMedCase) parentSSL ;

            if (hosp.getDepartment()!=null) form.setTheDepartment(hosp.getDepartment().getId()) ;
            if (hosp.getServiceStream()!=null) form.setServiceStream(hosp.getServiceStream().getId()) ;
        } else if (parentSSL!=null && parentSSL instanceof DepartmentMedCase){
            DepartmentMedCase slo = (DepartmentMedCase) parentSSL ;

            if (slo.getDepartment()!=null) form.setTheDepartment(slo.getDepartment().getId()) ;
            if (slo.getServiceStream()!=null) form.setServiceStream(slo.getServiceStream().getId()) ;
        } else  if (parentSSL!=null && parentSSL instanceof Visit){
            Visit slo = (Visit) parentSSL ;
            if (slo.getWorkFunctionExecute()!=null) {
                if (slo.getWorkFunctionExecute() instanceof PersonalWorkFunction) {
                    PersonalWorkFunction pwf = (PersonalWorkFunction) slo.getWorkFunctionExecute() ;
                    form.setTheDepartment(pwf.getWorker().getLpu().getId()) ;
                    if (slo.getWorkFunctionExecute().getIsSurgical()!=null&&slo.getWorkFunctionExecute().getIsSurgical().booleanValue()) {
                        form.setTheSurgeon(slo.getWorkFunctionExecute().getId()) ;
                    }
                }
                form.setTheStartDate(DateFormat.formatToDate(slo.getDateStart())); ;
                form.setTheStartTime(DateFormat.formatToTime(slo.getTimeExecute())); ;

            }
            if (slo.getServiceStream()!=null) form.setServiceStream(slo.getServiceStream().getId()) ;
        } else {
            throw new IllegalStateException("Невозможно добавить перевязку. Сначала надо определить  случай стационарного лечения (ССЛ)") ;
        }

        if (parentSSL.getDepartment()!=null) {
            List<Object[]> l = aContext.getEntityManager().createNativeQuery("select vlaeo.id from mislpu ml left join VocLpuAccessEnterOperation vlaeo on vlaeo.id=ml.AccessEnterOperation_id where ml.id='"
                    +parentSSL.getDepartment().getId()+"' and (vlaeo.code='DENIED_IN_DEPARTMENT' or vlaeo.code='ALL_DEPARTMENT')").getResultList() ;
            if (l.size()>0) throw new IllegalStateException("Нельзя добавить перевязку по текущему отделению!!!") ;
        }
        if (parentSSL.getLpu()!=null) {
            form.setLpu(parentSSL.getLpu().getId());
        }
        if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction")
                &&form.getTheDepartment()!=null&&form.getTheDepartment()>Long.valueOf(0)) {
            String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
            List<Object[]> listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and w.lpu_id=:lpu and wf.id is not null and wf.isSurgical='1'")
                    .setParameter("login", username)
                    .setParameter("lpu", form.getTheDepartment())
                    .setMaxResults(1)
                    .getResultList() ;
            if (listwf.size()>0) {
                Object[] wf = listwf.get(0) ;
                form.setTheSurgeon(ConvertSql.parseLong(wf[0])) ;
            } else {
                listwf =  manager.createNativeQuery("select wf.id as wfid,w.id as wid from WorkFunction wf left join Worker w on w.id=wf.worker_id left join SecUser su on su.id=wf.secUser_id where su.login = :login and wf.id is not null and wf.isSurgical='1'")
                        .setParameter("login", username)

                        .setMaxResults(1)
                        .getResultList() ;
                if (listwf.size()>0) {
                    Object[] wf = listwf.get(0) ;
                    form.setTheSurgeon(ConvertSql.parseLong(wf[0])) ;
                }
            }

        }
        //throw new IllegalStateException("я тут") ;
    }
}
