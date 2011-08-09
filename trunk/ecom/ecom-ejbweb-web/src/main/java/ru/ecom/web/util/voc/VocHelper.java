package ru.ecom.web.util.voc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.web.vochelper.IVocHelper;

/**
 * @author esinev
 * Date: 18.08.2006
 * Time: 9:28:40
 */
public class VocHelper implements IVocHelper {

    public  String getNameById(PageContext aPageContext, String aId, String aVocName, VocAdditional aVocAdditional) {
        try {
            HttpServletRequest request = (HttpServletRequest) aPageContext.getRequest() ;
            return EntityInjection.find(request).getVocService().getNameById(aId, aVocName, aVocAdditional) ;
        } catch (Exception e) {
            return e.getMessage() ;
        }
    }

}
