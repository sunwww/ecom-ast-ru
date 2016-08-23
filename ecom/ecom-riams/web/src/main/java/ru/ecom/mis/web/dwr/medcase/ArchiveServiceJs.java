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
		IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String wf = wqs.executeNativeSql("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login = '"+username+"'").iterator().next().get1().toString();
		return service.createArchiveCase(aStatCardIds, Long.valueOf(wf), username);
					//err.append(service.createrArchiveCase());
				
	}
	
	public String getCardFromArchive (String aStatCardIds, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService wqs = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		String SQL_t = "DELETE FROM archivecase where id = (select archivecase from statisticstub where id="+aStatCardIds+")";
		wqs.executeUpdateNativeSql(SQL_t);
		
		SQL_t = "update statisticstub set archivecase = null where id = "+aStatCardIds;
		
		return " "+wqs.executeUpdateNativeSql(SQL_t);
	}
	
}