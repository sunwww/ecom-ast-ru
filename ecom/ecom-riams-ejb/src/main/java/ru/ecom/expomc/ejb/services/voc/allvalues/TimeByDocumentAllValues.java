package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev
 * Date: 21.08.2006
 * Time: 11:18:19
 */
public class TimeByDocumentAllValues implements IAllValue {

    
    public Collection<VocValue> listAll(AllValueContext aContext) {
        LinkedList<VocValue> ret = new LinkedList<VocValue>();
        if(!StringUtil.isNullOrEmpty(aContext.getVocAdditional().getParentId())) {
            ImportDocument doc = aContext.getEntityManager().find(ImportDocument.class, Long.parseLong(aContext.getVocAdditional().getParentId())) ;
            for (ImportTime time : doc.getTimes()) {
                ret.add(new VocValue(String.valueOf(time.getId())
                        , DateFormat.formatToDate(time.getImportDate()) + " "+time.getComment())) ;
            }
        }
        return ret ;
    }

    public void destroy() {

    }
}
