package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev
 * Date: 21.08.2006
 * Time: 11:18:19
 */
public class ImportDocumentAllValues implements IAllValue {
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
    public Collection<VocValue> listAll(AllValueContext aContext) {
        Collection<ImportDocument> list = aContext.getEntityManager().createQuery("from ImportDocument i ").getResultList() ;
        LinkedList<VocValue> ret = new LinkedList<VocValue>();
        for (ImportDocument document : list) {
            ret.add(new VocValue(document.getId()+"", document.getKeyName()+" "+document.getComment())) ;
        }
        return ret ;
    }

    public void destroy() {
    }

}
