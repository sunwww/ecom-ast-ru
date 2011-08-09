package ru.ecom.mis.ejb.service.prescription;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.nuzmsh.util.voc.VocValue;

public class TemplateCategoryAllValueVoc implements IAllValue{

	public void destroy() {
		
	}

	public Collection<VocValue> listAll(AllValueContext aContext) {
		List<TemplateCategory> categs = aContext.getEntityManager().createQuery("from TemplateCategory where parent_id is null order by name").getResultList() ;
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		
//		InterceptorContext context = new InterceptorContext(aContext.getEntityManager(), aContext.getSessionContext()) ;
		
		for(TemplateCategory categ : categs) {
			 try {
				 add(ret, categ, "") ;
			 } catch (IllegalStateException e) {
				 
			 }
		}
		return ret;

	}
	
	private static void add(List<VocValue> aValues, TemplateCategory aCateg, String aAppend) {
		String name = aAppend + aCateg.getName() ;
		aValues.add(new VocValue(String.valueOf(aCateg.getId()), name)) ;
		for(TemplateCategory categ : aCateg.getChild()) {
			add(aValues, categ, ".    "+aAppend) ;
		}
	}

}
