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
		int cntPatList = list.size() ;
		if (cntPatList>0) {
			String obj = FondWebService.checkPatientByFioDr(aRequest, aLastname, aFirstname, aMiddlename, aBirthday) ;
			
			String[] pol=obj.split("#") ;
			System.out.println("pol length="+pol.length) ;
			if (pol.length>1) {
				
				if (!pol[1].equals("")) {
					String pRegComp = pol[1] ;
					String pSeries = pol[2] ;
					String pNumber = pol[3] ;
					String pDateEnd = pol[5] ;
					sqlSearchPat = new StringBuilder() ;
					sqlSearchPat.append("select p.id,p.LASTNAME from patient p left join medpolicy mp on mp.patient_id=p.id")
						.append(" where p.lastname='").append(aLastname)
						.append("' and p.firstname='").append(aFirstname).append("'")
						.append(" and p.middlename='").append(aMiddlename)
						.append("' and p.birthday=to_date('").append(aBirthday).append("','yyyy-mm-dd') and mp.series='").append(pSeries).append("' and mp.polnumber='").append(pNumber).append("'") ;
					System.out.println("sqlSearchPat="+sqlSearchPat) ;
					list = service.executeNativeSql(sqlSearchPat.toString()) ;
					System.out.println(obj) ;
					if (list.size()==1) return "1#"+obj ;
					System.out.println(obj) ;
					if (cntPatList>1) return "2#"+obj ;
					System.out.println(obj) ;
					if (cntPatList==1) return "3#"+obj ;
					System.out.println(obj) ;
				}
			}
			
			return "0#"+obj ;
		}
		return "0" ;
	}
}
