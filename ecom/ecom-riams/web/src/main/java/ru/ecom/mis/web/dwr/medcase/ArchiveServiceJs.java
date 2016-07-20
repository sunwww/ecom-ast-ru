package ru.ecom.mis.web.dwr.medcase;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.mis.ejb.service.archive.IArchiveService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

public class ArchiveServiceJs {
	
	public String putCardInArchive (String aStatCardIds, HttpServletRequest aRequest) throws NamingException {
		IArchiveService service = Injection.find(aRequest).getService(IArchiveService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.createArchiveCase(aStatCardIds, Long.valueOf(123), username);
					//err.append(service.createrArchiveCase());
				
	}
	
}