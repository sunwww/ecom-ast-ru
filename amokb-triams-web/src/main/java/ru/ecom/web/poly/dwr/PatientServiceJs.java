package ru.ecom.web.poly.dwr;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.poly.webservice.FondWebService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

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
		Object idPat="0" ;
		if (cntPatList>0) {
			if (cntPatList==1) idPat=list.iterator().next().get1() ; 
			String obj = FondWebService.checkPatientByFioDr(aRequest, aLastname, aFirstname, aMiddlename, aBirthday) ;
			
			String[] pol=obj.split("#") ;
			System.out.println("pol length="+pol.length) ;
			if (pol.length>1) {
				
				if (!pol[1].equals("")) {
					String pRegComp = pol[1] ;
					String pSeries = pol[2] ;
					String pNumber = pol[3] ;
					String pDateEnd = pol[4] ;
					sqlSearchPat = new StringBuilder() ;
					sqlSearchPat.append("select p.id,p.LASTNAME from patient p left join medpolicy mp on mp.patient_id=p.id")
						.append(" where p.lastname='").append(aLastname)
						.append("' and p.firstname='").append(aFirstname).append("'")
						.append(" and p.middlename='").append(aMiddlename)
						.append("' and p.birthday=to_date('").append(aBirthday).append("','yyyy-mm-dd') and mp.series='").append(pSeries).append("' and mp.polnumber='").append(pNumber).append("'") ;
					System.out.println("sqlSearchPat="+sqlSearchPat) ;
					list = service.executeNativeSql(sqlSearchPat.toString()) ;
					System.out.println(obj) ;
					
					if (list.size()==1) {
						idPat=list.iterator().next().get1() ;
						return "1#"+idPat+"#"+obj ;
					}
					System.out.println(obj) ;
					if (cntPatList>1) return "2##"+obj ;
					System.out.println(obj) ;
					if (cntPatList==1) return "3#"+idPat+"#"+obj ;
					System.out.println(obj) ;
				}
			}
			
			return "0#"+obj ;
		}
		return "0" ;
	}
	public String savePolicy(String aInfo, String aLastname, String aFirstname, String aMiddlename, String aBirthday, HttpServletRequest aRequest) throws NamingException {
		System.out.println("info="+aInfo) ;
		String[] info = aInfo.split("#") ;
		String idPat = info[1] ;
		String polComp = info[3] ;
		String polSeries = info[4] ;
		String polNumber = info[5] ;
		String polDateIssued = info[6] ;
		String polDateEnd = info.length>7?info[7]:"" ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String curDate = DateFormat.formatToDate(new java.util.Date()) ;
		//System.out.println("info="+aInfo) ;
		return service.updateOrCreatePolicyByFond(Long.valueOf(idPat), info[2], 
				aLastname, aFirstname, aMiddlename, aBirthday,
				polComp, polSeries, polNumber, polDateIssued, polDateEnd, curDate)?
						aInfo:"" ;
		//service.updateDataByFond("terminal", Long.valueOf(idPat), aFiodr, aDocument, aPolicy, aAddress, aIsPatient, aIsPolicy, aIsDocument, aIsAddress)
	}
}
