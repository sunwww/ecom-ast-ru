package ru.ecom.template.web.dwr;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class DynamicServiceJs {

    public String getContentFromType(Long typeId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> results = service.executeNativeSql("select content from VocDynamicDocument where id="+typeId);
        return results.isEmpty() ? "{}" : results.iterator().next().get1().toString();


    }
}
