package ru.ecom.template.web.dwr;

import ru.nuzmsh.util.StringUtil;
import ru.ecom.diary.ejb.service.template.ICategoryTreeService;
import ru.ecom.web.util.Injection;

import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 24.01.2007
 * Time: 13:52:18
 * To change this template use File | Settings | File Templates.
 */
public class CategoryTreeServiceJs {
    public String getVocabulary(String aIdClassif, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aIdClassif)) {
            return "" ;
        } else {
            ICategoryTreeService service = Injection.find(aRequest).getService(ICategoryTreeService.class) ;
            return service.getClazz(Long.valueOf(aIdClassif)) ;
//            return "Mkb" ;
        }
    }

}
