package ru.ecom.web.dwr;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;

/**
 * Для aucotomplete
 */
public class VocServiceJs {
    public String getNameById(HttpServletRequest aRequest, String aVocName, String aId, String aParentId) throws NamingException, VocServiceException {
        return EntityInjection.find(aRequest).getVocService().getNameById(aId, aVocName, new VocAdditional(aParentId)) ;
    }

}
