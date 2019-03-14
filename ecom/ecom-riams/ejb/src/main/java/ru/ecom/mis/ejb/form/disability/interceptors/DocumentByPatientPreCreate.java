package ru.ecom.mis.ejb.form.disability.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentByPatientForm;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Создание формы по документу нетрудоспособности
 * @author stkacheva
 *
 */public class DocumentByPatientPreCreate implements IParentFormInterceptor{
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DisabilityDocumentByPatientForm form = (DisabilityDocumentByPatientForm) aForm ;
    	Patient pat = manager.find(Patient.class, aParentId) ;
    	if (pat!=null) {
    		// Обязательно, чтобы все даты окончания были заполнены 
    		//(Новый период нетрудоспособности не должен заводиться если у последнего
    		//не проставлена да окончания)
    		//Новый период нетрудоспособности должен заводиться с датой последняя дата окончания + 1

			List<DisabilityDocument> list = manager.createQuery("from DisabilityDocument where patient_id = :pat and closeReason_id is null  and (noActuality is null or cast(noActuality as integer)=0)").setParameter("pat", pat.getId()).getResultList() ;
    		if (list.size()>0) {
    			StringBuilder err = new StringBuilder() ;
    			err.append("Невозможно добавить новый случай нетрудоспособности. <br/>В базе имеются не закрытые документы по предыдущему случаю:") ;
    			for (DisabilityDocument doc:list) {
    				err.append("<br/><a href='entityParentView-dis_document.do?id=")
    					.append(doc.getId()).append("'>")
    					.append(doc.getInfo())
    					.append("</a>");
    			}
				throw new IllegalStateException(err.toString()) ;    				
    		} 
    		//String vo =  ;
    		form.setJob(pat.getWorks()) ;
    	} else {
    		throw new IllegalStateException("Невозможно добавить случай. Сначала надо определить пациента") ;
    	}
    }
    
    public static String getShortNameByOrg(VocOrg aOrg) {
		String org = aOrg!=null? aOrg.getCode() :"";
		if (aOrg!=null && (org==null || org.equals(""))) {
			org = aOrg.getName() ;
			org = organizationUpdate(org) ;
 		}
    	return org ;
    }
    
    private static String organizationUpdate(String aOrg) {
    	aOrg=aOrg.replace("АКЦИОНЕРНОЕ ОБЩЕСТВО ОТКРЫТОГО ТИПА", "АООТ") ;
    	aOrg=aOrg.replace("АКЦИОНЕРНОЕ ОБЩЕСТВО ЗАКРЫТОГО ТИПА", "АОЗТ") ;
    	aOrg=aOrg.replace("ТОВАРИЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ", "ТОО") ;
    	aOrg=aOrg.replace("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ", "ООО") ;
    	aOrg=aOrg.replace("ПРОИЗВОДСТВЕННО-КОММЕРЧЕСКАЯ ФИРМА", "ПКФ") ;
    	aOrg=aOrg.replace("НАУЧНО-ПРОИЗВОДСТВЕННОЕ ОБЪЕДИНЕНИЕ", "НПО") ;
    	aOrg=aOrg.replace("ЗАКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", "ЗАО") ;
    	aOrg=aOrg.replace("ОТКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", "ОАО") ;
    	aOrg=aOrg.replace("ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", "ГУЗ") ;
    	aOrg=aOrg.replace("ГОСУДАРСТВЕННОЕ ПРЕДПРИЯТИЕ", "ГП") ;
    	aOrg=aOrg.replace("МУНИЦИПАЛЬНОЕ ПРЕДПРИЯТИЕ", "МП") ;
    	aOrg=aOrg.replace("МУНИЦИПАЛЬНОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", "МУП") ;
    	aOrg=aOrg.replace("ИНДИВИДУАЛЬНОЕ ЧАСТНОЕ ПРЕДПРИЯТИЕ", "ИЧП") ;
    	aOrg=aOrg.replace("ЧАСТНОЕ ПРЕДПРИЯТИЕ", "ЧП") ;
    	aOrg=aOrg.replace("СОВМЕСТНОЕ ПРЕДПРИЯТИЕ", "СП") ;
    	aOrg=aOrg.replace("ОБЛАСТНОЕ ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", "ОГУК") ;
    	aOrg=aOrg.replace("ОБЩЕСТВЕННОЕ ОБЪЕДИНЕНИЕ", "ОО") ;
    	aOrg=aOrg.replace("МЕДИЦИНСКОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", "МУЗ") ;
    	aOrg=aOrg.replace("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", "ФГУП") ;
    	aOrg=aOrg.replace("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", "ФГОУ") ;
    	aOrg=aOrg.replace("ОБЛАСТНОЕ ГОСУДАРСТВЕННОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", "ОГОУ") ;
    	aOrg=aOrg.replace("МУНИЦИПАЛЬНОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", "МОУ") ;
    	aOrg=aOrg.replace("СРЕДНЯЯ ОБЩЕОБРАЗОВАТЕЛЬНАЯ ШКОЛА", "СОШ") ;
    	aOrg=aOrg.replace("СРЕДНЕГО ПРОФЕССИОНАЛЬНОГО ОБРАЗОВАНИЯ", "СПО") ;
    	aOrg=aOrg.replace("ВЫСШЕГО ПРОФЕССИОНАЛЬНОГО ОБРАЗОВАНИЯ", "ВПО") ;
		
    	aOrg=aOrg.replace(",", " ") ;
    	aOrg=aOrg.replace(")", " ") ;
    	aOrg=aOrg.replace("(", " ") ;
    	aOrg=aOrg.replace("-", " ") ;
    	aOrg=aOrg.replace("\"", " ") ;
    	aOrg=aOrg.replace(".", " ") ;
    	aOrg=aOrg.replace("№", " ") ;
    	while (aOrg.indexOf("  ")>-1) {
    		aOrg=aOrg.replace("  ", " ") ;
    	}
    	aOrg = aOrg.trim() ;
		return aOrg ;
    	
    }
}