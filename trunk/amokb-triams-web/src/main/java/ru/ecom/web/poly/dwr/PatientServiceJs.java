package ru.ecom.web.poly.dwr;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.poly.webservice.FondWebService;
import ru.ecom.web.util.Injection;

public class PatientServiceJs {
	public String getPatient(String aLastname,String aFirstname, String aMiddlename
			,String aBirthday,HttpServletRequest aRequest) throws Exception {
		StringBuilder sqlSearchPat = new StringBuilder() ;
		sqlSearchPat.append("select id,LASTNAME from patient")
			.append(" where lastname='").append(aLastname)
			.append("' and firstname='").append(aFirstname).append("'")
			.append(" and middlename='").append(aMiddlename)
			.append("' and birthday=to_date('").append(aBirthday).append("','yyyy-mm-dd')") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sqlSearchPat.toString()) ;
		if (list.size()==1) {
			String obj = FondWebService.checkPatientByFioDr(aRequest, aLastname, aFirstname, aMiddlename, aBirthday) ;
			return obj ;
		}
		return "" ;
	}
}
