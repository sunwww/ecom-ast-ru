package ru.ecom.expomc.web.dwr;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.expomc.ejb.uc.filltime.service.IFillTimeService;
import ru.ecom.web.util.Injection;

public class FillTimeServiceJs {

	public String testInvoke(long aFillTime, String aProperty, String aCode, HttpServletRequest aRequest) throws NamingException {
		IFillTimeService service = Injection.find(aRequest).getService(IFillTimeService.class);
		return service.testInvoke(aFillTime, aProperty, aCode);
		
	}
}
