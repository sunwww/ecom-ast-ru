package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.util.Collection;

import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.nuzmsh.util.voc.VocValue;

/**
 * Список свойств у документа по проверке (Check)
 */
public class ImportDocumentByCheckAllValues extends ImportDocumentPropertiesAllValues {


    public Collection<VocValue> listAll(AllValueContext aContext) {
        String checkId = aContext.getVocAdditional().getParentId();
        Check check = aContext.getEntityManager().find(Check.class, Long.parseLong(checkId)) ;

        return listByDocumentId(String.valueOf(check.getDocument().getId())
                ,aContext.getEntityManager()) ;
    }


}
