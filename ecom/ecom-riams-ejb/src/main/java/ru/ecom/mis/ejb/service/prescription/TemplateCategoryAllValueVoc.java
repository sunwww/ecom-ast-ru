package ru.ecom.mis.ejb.service.prescription;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

public class TemplateCategoryAllValueVoc implements IAllValue{
    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId!=null && !aId.equals("") && !aId.equals("0")) {
        	List<Object[]> categs = aContext.getEntityManager().createNativeQuery("select id,name from TemplateCategory where id="+aId+" order by name").getResultList() ;
    		if (categs.size()>0) ret = ""+categs.get(0)[1] ;
        }
        return ret;
    }
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
