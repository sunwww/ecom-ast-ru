/**
 * Источник Autocomplete для форматов импорта
 *
 * @author ikouzmin 08.03.2007 20:58:15
 */
package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.expomc.ejb.domain.format.ImportFormat;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

public class ImportFormatByDocumentAllValues implements IAllValue {
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
        LinkedList<VocValue> ret = new LinkedList<VocValue>();
        if(!StringUtil.isNullOrEmpty(aContext.getVocAdditional().getParentId())) {
            ImportDocument doc = aContext.getEntityManager().find(ImportDocument.class, Long.parseLong(aContext.getVocAdditional().getParentId())) ;
            for (ImportFormat format : doc.getImportFormats()){
                ret.add(new VocValue(format.getId()+"", DateFormat.formatToDate(format.getActualDateFrom())+" "+format.getComment())) ;
            }
        }
        return ret ;
    }

    public void destroy() {

    }
}
