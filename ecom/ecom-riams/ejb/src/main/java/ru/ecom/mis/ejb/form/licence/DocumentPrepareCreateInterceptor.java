package ru.ecom.mis.ejb.form.licence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

public class DocumentPrepareCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	
    	InternalDocumentsForm form = (InternalDocumentsForm)aForm ;
        Date date = new Date();
    	form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
    	form.setCreateDate(DateFormat.formatToDate(date));
        /*if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/ShortEnter")) {
        	form.setDateStart(DateFormat.formatToDate(date));
        	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            form.setEntranceTime(timeFormat.format(date));
        } else {
        	
        }*/
        EntityManager manager = aContext.getEntityManager();
        //MedCase parent = manager.find(MedCase.class, aParentId) ;
        List<Object[]> list= manager.createNativeQuery("select to_char(d.dateRegistration,'dd.mm.yyyy'),d.record from Diary d where d.medCase_id='"+aParentId+"' order by d.dateRegistration").getResultList() ;
        StringBuilder res = new StringBuilder() ; 
        for (Object[] obj:list) {
        	res
        	//.append(obj[0]).append("\n")
        	.append(obj[1]).append("\n") ;
        }
        form.setHistory(res.toString()) ;
        list.clear() ;
        list= manager.createNativeQuery("select mkb.code,d.name,mkb.id from Diagnosis d"
        		+" left join VocIdc10 mkb on mkb.id=d.idc10_id"
        		+" where d.medCase_id='"+aParentId+"' order by d.id").getResultList() ;
        res = new StringBuilder() ; 
        boolean isFirst = true ;
        for (Object[] obj:list) {
        	if (isFirst) {
        		form.setIdc10(ConvertSql.parseLong(obj[2])) ;
        	}
        	res.append(obj[0]).append(". ").append(obj[1]).append("\n") ;
        }
        form.setDiagnosis(res.toString()) ;
        list.clear() ;
        list= manager.createNativeQuery("select ms.code,ms.name from MedCase mc  "
        		+" left join MedService ms on ms.id=mc.medService_id"
        		+" where mc.parent_id='"+aParentId+"' and mc.dtype='ServiceMedCase' order by ms.code").getResultList() ;
        res = new StringBuilder() ; 
         isFirst = true ;
        for (Object[] obj:list) {
        	res.append(obj[0]).append(". ").append(obj[1]).append("\n") ;
        }
        form.setServicies(res.toString()) ;
        
    }

}