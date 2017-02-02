package ru.ecom.mis.web.dwr.extdisp;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

public class AppointmentServiceJs {

	
	public String getCodeForVocExtDispAppointment(Long aAppointmentId, HttpServletRequest aRequest) throws NamingException {
		 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		 StringBuilder sb = new StringBuilder();
		 String sql ="select ap.code from VocExtDispAppointment ap where ap.id="+aAppointmentId;
		 Collection <WebQueryResult> res = service.executeNativeSql(sql);
		 for (WebQueryResult wqr: res) {
			sb.append(""+wqr.get1());
		 }
		 return sb.toString();
	}
}
