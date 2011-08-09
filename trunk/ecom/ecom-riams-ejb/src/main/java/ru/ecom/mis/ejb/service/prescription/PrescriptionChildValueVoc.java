package ru.ecom.mis.ejb.service.prescription;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocValue;

public class PrescriptionChildValueVoc implements IAllValue{

	public void destroy() {
		
	}

	public Collection<VocValue> listAll(AllValueContext aContext) {
//		List<TemplateCategory> categs = aContext.getEntityManager().createQuery("from TemplateCategory where parent_id is null order by name").getResultList() ;
		List<PrescriptListTemplate> templs = null ;
		String parentId=null;
		if (aContext.getVocAdditional()!=null) {
			parentId = aContext.getVocAdditional().getParentId() ;
		}
//		System.out.println("Родитель:"+parentId) ;
		if (StringUtil.isNullOrEmpty(parentId)) {
//			templs = aContext.getEntityManager().createQuery("from PrescriptionList where PrescriptionList.DTYPE='PrescriptListTemplate'  order by name").getResultList() ;
		} else {
			TemplateCategory categ = aContext.getEntityManager().find(TemplateCategory.class, Long.valueOf(parentId)) ;
			templs = categ.getPrescriptLists() ;
		}
		
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		
//		InterceptorContext context = new InterceptorContext(aContext.getEntityManager(), aContext.getSessionContext()) ;
		
		if (templs!=null) for(PrescriptListTemplate templ : templs) {
			 try {
				 add(ret, templ, "") ;
			 } catch (IllegalStateException e) {
				 
			 }
		}
		return ret;

	}
	
	private static void add(List<VocValue> aValues, PrescriptListTemplate aTempl, String aAppend) {
		String name = aAppend + aTempl.getName() ;
		aValues.add(new VocValue(String.valueOf(aTempl.getId()), name)) ;
//		for(PrescriptListTemplate categ : aCateg.getChild()) {
//			add(aValues, categ, ".    "+aAppend) ;
//		}
	}
}
