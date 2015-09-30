package ru.ecom.diary.ejb.service.protocol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

public class TemplateProtocolByCategoryVoc implements IAllValue{

	public void destroy() {
		
	}
	
	
	public Collection<VocValue> listAll(AllValueContext aContext) {
		VocAdditional voc = aContext.getVocAdditional() ;
		LinkedList<VocValue> ret = new LinkedList<VocValue>() ;
		if (voc!=null && voc.getParentId()!=null && !voc.getParentId().equals("")) {
			Long parent = Long.valueOf(voc.getParentId()) ;
			TemplateCategory categ = aContext.getEntityManager().find(TemplateCategory.class, parent) ;
			aContext.getEntityManager().refresh(categ) ;
			for (TemplateProtocol prot : categ.getProtocols()) {
				System.out.println(prot.getTitle()) ;
				add(ret, prot) ;
			}
			
		} else {
			List<TemplateProtocol> list = aContext.getEntityManager().createQuery("from TemplateProtocol").getResultList() ;
			for (TemplateProtocol prot : list) {
				add(ret,prot) ;
			}
		}
		return ret;

	}
	
	private static void add(List<VocValue> aValues, TemplateProtocol aProtocol) {
		aValues.add(new VocValue(String.valueOf(aProtocol.getId()), aProtocol.getTitle())) ;
	}


    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;
    }

}
