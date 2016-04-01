package ru.ecom.mis.web.dwr.medcase;

import java.util.Calendar;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.mis.web.webservice.FondWebService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.forms.validator.validators.SnilsStringValidator;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class PatientServiceJs {
	
	
	public String showPatientCheckByFondHistory(String aPatientId, String aType, HttpServletRequest aRequest) throws NamingException {
		 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		 StringBuilder ret = new StringBuilder();
		 try{
			 Object[] re = service.executeNativeSqlGetObj("select lastname, firstname, middlename, birthday, commonnumber from patient where id = "+aPatientId).get(0);
			 String whereSql ="";
			 if (aType!=null&&aType.equals("1")) {
				 whereSql = "pf.lastname = '"+re[0]+"' and pf.firstname = '"+re[1]+"' and middlename = '"+re[2]+"' and pf.birthday = '"+re[3]+"'";
			 } else {
				 whereSql = "pf.commonnumber='"+re[4]+"'";
			 }
			  
			 String sql = "select pf.lastname, pf.firstname, pf.middlename, to_char(pf.birthday,'dd.MM.yyyy') as f3_birthday" +
				 		" ,to_char(pf.checkdate,'dd.MM.yyyy') as f4_check_date, coalesce(pf.lpuattached,'') as f5, coalesce(pf.attachedtype,'') as f6" +
				 		" ,coalesce(to_char(pf.attacheddate,'dd.MM.yyyy'),'') as f7_att_date" +
				 		", coalesce(department,'') as f8, coalesce(doctorsnils,'') as f9" +
				 		" from patientfond pf" +
				 		" where " +whereSql +
				 		" order by pf.checkdate desc limit 50";
			 
			 Collection <WebQueryResult> res = service.executeNativeSql(sql);
			 if (!res.isEmpty()) {
				 ret.append("<table border='1'>");
				 ret.append("<tr><td>Фамилия</td><td>Имя</td><td>Отчество</td><td>ДР</td><td>Дата проверки</td><td>ЛПУ прикрепления</td><td>Тип прикрепления</td><td>Дата прикрепления</td><td>Подразделение</td><td>СНИЛС врача</td></tr>");
				
				 for (WebQueryResult wqr: res) {
					 ret.append("<tr>")
					 .append("<td>").append(wqr.get1()).append("</td>")
					 .append("<td>").append(wqr.get2()).append("</td>")
					 .append("<td>").append(wqr.get3()).append("</td>")
					 .append("<td>").append(wqr.get4()).append("</td>")
					 .append("<td>").append(wqr.get5()).append("</td>")
					 .append("<td>").append(wqr.get6()).append("</td>")
					 .append("<td>").append(wqr.get7()).append("</td>")
					 .append("<td>").append(wqr.get8()).append("</td>")
					 .append("<td>").append(wqr.get9()).append("</td>")
					 .append("<td>").append(wqr.get10()).append("</td>")
					 .append("</tr>");
				}
				 ret.append("</table>");
				 return ret.toString();
			 } else {
				 return ret.toString() ;
			 }
		 } catch (Exception e){
			 return ""+e;
		 }
		 
	}
	
	public String getUserDocumentList(String aGroupName, HttpServletRequest aRequest) throws NamingException {
		StringBuilder ret = new StringBuilder();
		String sql = "select ud.id, ud.name, ud.filename from VocUserDocumentGroup vudg" +
				" left join userDocument ud on ud.groupType_id = vudg.id where upper(vudg.code) = upper('"+aGroupName+"')";
	 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	 Collection <WebQueryResult> res = service.executeNativeSql(sql);
	 if (!res.isEmpty()) {
		 for (WebQueryResult r: res) {
			 ret.append(r.get1()+":"+r.get2()+":"+r.get3()+"#");
		 }
	 }
		return ret.length()>0?ret.substring(0, ret.length()-1):"";
	}
	public void changeMedPolicyType(Long aPolicyId, Long aNewPolicyTypeId, HttpServletRequest aRequest) throws NamingException {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
		service.changeMedPolicyType(aPolicyId, aNewPolicyTypeId);
	}
	public boolean updateDataByFondAutomatic(String aPatientFondId, String aCheckId
			, boolean isUpdatePatient, boolean isUpdateDocument, boolean isUpdatePolicy, boolean isUpdateAttachment
			, HttpServletRequest aRequest) throws NamingException {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
		return service.updateDataByFondAutomatic(Long.valueOf(aPatientFondId)
				, Long.valueOf(aCheckId), isUpdatePatient, isUpdateDocument
				, isUpdatePolicy, isUpdateAttachment);
		
	}
	public String checkAllPatients(String updPatient, String updDocument, String updPolicy,String updAttachment, String aType, String aPatientList,  HttpServletRequest aRequest) throws Exception {
		
		return FondWebService.checkAllPatientsByFond(updPatient, updDocument, updPolicy, updAttachment, aType, aPatientList, aRequest).toString(); 
	}
	public String checkDispAttached (String aDispTypeId, String aPatientId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String isDispAttachment = service.executeNativeSql("select case when attachmentpopulation ='1' then '1' else '0' end " +
				"from vocextdisp where id='"+aDispTypeId+"'", 1).iterator().next().get1().toString();
		String isPatAttached = isPatientAttached(aPatientId, aRequest);
		if (isDispAttachment.equals("1")&&isPatAttached.substring(0,1).equals("0")) {
			return "0";
		} else return "1";
	}
	public String checkPatientAttachedOrDead(String aPatientId, String isDeath, String isAttached, HttpServletRequest aRequest) throws NamingException {
		
		boolean checkDeath = (isDeath!=null&&isDeath.equals("1"))?true:false;	
		boolean checkAttachment = (isAttached!=null&&isAttached.equals("1"))?true:false;
		String res = "-";
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> list = service.executeNativeSql("select pf.lpuattached, to_char(pf.checkdate,'dd.mm.yyyy'),case when pf.deathdate is not null then to_char(pf.deathdate,'dd.mm.yyyy') else '' end from patient p " +
					"left join patientfond pf on (pf.lastname=p.lastname and pf.firstname=p.firstname and pf.middlename=p.middlename " +
					"and pf.birthday=p.birthday) where p.id='"+aPatientId+"' and (pf.lpuattached is not null and pf.lpuattached!='') order by pf.checkdate desc", 1);
			Collection<WebQueryResult> defLpu =service.executeNativeSql("select sc.keyvalue, case when sc.description!='' then sc.description else '№ '|| sc.keyvalue end from softconfig sc where sc.key='DEFAULT_LPU_OMCCODE'"); 
			String defaultLpu = null, defaultLpuName = null;
			if (checkAttachment) {
				if (defLpu.isEmpty()) {
					return "0Необходимо указать ЛПУ по умолчанию в настройках (DEFAULT_LPU_OMCCODE)";
				} else {
					defaultLpu = defLpu.iterator().next().get1().toString(); 
					defaultLpuName = defLpu.iterator().next().get2().toString();
				}	
			}			
			if (!list.isEmpty()) {
				
				WebQueryResult wqr = list.iterator().next();
				String lastAttachment = wqr.get1().toString();
				String checkDate = wqr.get2().toString();
				String deathDate = wqr.get3().toString();
					if (checkAttachment) {
						if (lastAttachment.equals(defaultLpu)) {
							res = "1По данным ФОМС на "+checkDate+" пациент прикреплен к ЛПУ "+defaultLpuName;
						} else {
							res =  "0По данным ФОМС на "+checkDate+" пациент не прикреплен к ЛПУ "+defaultLpuName;
						}
					}
					if (checkDeath&&deathDate!=null&&deathDate.length()==10) {
						res= "2По данным ФОМС на "+checkDate+" пациент умер "+deathDate;
					} 
				
			} else {
				if (checkAttachment) {
					res = "0Необходимо выполнить проверку по базе ФОМС";
				}
			} 
			return res;
		}
	
	public String isPatientAttached (String aPatientId, HttpServletRequest aRequest) throws NamingException {
		return checkPatientAttachedOrDead(aPatientId,"0","1",aRequest);
	}
	
	public String getSexByOmccode(String aOmccode,HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
        Collection<WebQueryResult> list = service.executeNativeSql("select id,name from vocSex where omcCode='"+aOmccode+"'",1) ;
        if (list.isEmpty()) {
            return "" ;
        } else {
            WebQueryResult wqr = list.iterator().next() ;
            return new StringBuilder().append(wqr.get1()).append("#").append(wqr.get2()).toString() ;
        }
    }

	public String editColorType(Long aPatient,String aColorTypeCurrent, HttpServletRequest aRequest) throws NamingException  {
		String colorType="1" ;
		if (aColorTypeCurrent!=null && aColorTypeCurrent.trim().equals("1")) {
			colorType="0" ;
		}
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update Patient set colorType='"+colorType+"' where id='"+aPatient+"'") ;
		return "сохранено" ;
	}
	public String getAgeForDisp(Long aPatient, String aFinishDate, HttpServletRequest aRequest) throws NamingException {
		try {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> list = service.executeNativeSql("select id,to_char(birthday,'dd.mm.yyyy') from patient where id='"+aPatient+"'",1) ;
			WebQueryResult wqr = list.iterator().next() ;
			
			String birthDayS = wqr.get2()!=null?""+wqr.get2():"" ;
			//String birthDayYear = birthDayS.substring(5) ;
			java.sql.Date birthday = DateFormat.parseSqlDate(birthDayS) ;
			java.sql.Date finishDate = DateFormat.parseSqlDate(aFinishDate) ;
			Calendar calB = Calendar.getInstance() ;
			calB.setTime(birthday) ;
			Calendar calF = Calendar.getInstance() ;
			calF.setTime(finishDate) ;
			boolean reMonth = (calF.get(Calendar.MONTH) == calB.get(Calendar.MONTH)) ;
			String age=AgeUtil.getAgeCache(finishDate, birthday, 1);
			System.out.println("age:"+age) ;
			int sb1 = age.indexOf(".") ;
			int sb2 = age.indexOf(".",sb1+1) ;
			
			int yearDif = Integer.valueOf(age.substring(0,sb1)).intValue() ;
			System.out.println("yearDif:"+yearDif) ;
			int monthDif = Integer.valueOf(age.substring(sb1+1, sb2)).intValue() ;
			System.out.println("monthDif:"+monthDif) ;
			//int dayDif =  Integer.valueOf(age.substring(sb2+1)).intValue() ;
			if (yearDif==2){
				if (monthDif>=6) {
					return "2.6" ;
				} else {
					return "2" ;
				}
			} else if (yearDif==1){
				if (monthDif==0) return "1" ;
				else if (monthDif==1 && reMonth) return "1" ;
				else if (monthDif==2 && reMonth) return "1.3" ;
				else if (monthDif==3) return "1.3" ;
				else if (monthDif==5 && reMonth) return "1.6" ;
				else if (monthDif==6) return "1.6" ;
				else if (monthDif==8 && reMonth) return "1.9" ;
				else if (monthDif==9) return "1.9" ;
				else if (monthDif==11 && reMonth) return "2" ;
				return "1."+monthDif+"" ;
			} else if (yearDif==0){
				return ""+yearDif+"."+monthDif ;
			//} else if(yearDif<18) {
			//	return ""+yearDif ;
			} else {
				int year1=Integer.valueOf(birthDayS.substring(6)).intValue() ;
				int year2=Integer.valueOf(aFinishDate.substring(6)).intValue() ;
				if (year2<20) year2=year2+2000 ;
				if (year2<100) year2=year2+1900 ;
				System.out.println("year1="+year1) ;
				System.out.println("year2="+year2) ;
				return ""+(year2-year1) ;
			}
			
		} catch(Exception e) {
			System.out.println(e) ;
			return "" ;
		}
		
	}
	
	public String checkPolicy(String aRoles,HttpServletRequest aRequest) throws JspException {
		if (RolesHelper.checkRoles(aRoles, aRequest)) {
			return "1" ;
		}
		return "0" ;
	}
	public String getFactorByProfession(Long aProfession,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql
		.append(" select vdp.id,vdp.factorOfProduction from VocDocumentProfession vdp ")
		.append(" where ")
		.append(" vdp.id='").append(aProfession).append("'") ;
		System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get2()!=null) {
				return ""+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getCodefByRegIcForeign(Long aArea, Long aCompany,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql
		.append(" select smo.id,smo.name from Omc_SprSmo smo ")
		.append(" left join Omc_KodTer ter on ter.okato=smo.fondokato ")
		.append(" left join reg_ic com on com.ogrn=smo.voc_code ")
		.append(" where ")
		.append(" ter.id='").append(aArea)
		.append("' and com.id='").append(aCompany).append("'");
		System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1()!=null) {
				return wqr.get1()+"#"+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getRegIcForeignByCodef(Long aOgrnCompany,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql
			.append(" select com.id,coalesce(com.omcCode,'')||' '||com.name from Omc_SprSmo smo ")
			.append(" left join Omc_KodTer ter on ter.okato=smo.fondokato ")
			.append(" left join reg_ic com on com.ogrn=smo.voc_code ")
			.append(" where ")
			.append(" smo.id='").append(aOgrnCompany).append("'")
			;
		//System.out.println(sql) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1);
		if (!list.isEmpty()) {
			WebQueryResult wqr = list.iterator().next() ;
			if (wqr.get1()!=null) {
				return wqr.get1()+"#"+wqr.get2() ;
			}
		}
		return "" ;
	}
	public String getCodeByMedPolicyOmc(Long aType,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getCodeByMedPolicyOmc(aType);
		
	}
	public String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getInfoVocForFond(aPassportType,aAddress,aPolicy);
		
	}
	public boolean updateDataByFond(Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress
			, boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress, boolean aIsAttachment, HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		return service.updateDataByFond(username,aPatientId, aFiodr, aDocument, aPolicy, aAddress,aIsPatient,aIsPolicy
				, aIsDocument, aIsAddress, aIsAttachment);
		
	}
	public Object checkPatientByPolicy(Long aPatientId, String aSeries, String aNumber,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking snils...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientByMedPolicy(aRequest, getPatientInfo(aPatientId, service),aSeries,aNumber) ;
		
		return res ;
	}
	public Object checkPatientByCommonNumber(Long aPatientId, String aCommonNumber,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking snils...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientByCommonNumber(aRequest, getPatientInfo(aPatientId, service),aCommonNumber) ;
		
		return res ;
	}
	public Object checkPatientBySnils(Long aPatientId, String aSnils,HttpServletRequest aRequest) throws Exception {
		if (aSnils!=null&&!aSnils.equals("")){
		Object res = null;
		//System.out.println("checking snils...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientBySnils(aRequest, getPatientInfo(aPatientId, service),aSnils) ;
		
		return res ;
		} else {
			return "Не заполнено поле \"СНИЛС\"";	
		}
		
	}
	public Object checkPatientByFioDr(Long aPatientId, String aLastname,String aFirstname
			,String aMiddlename, String aBirthday,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking fiodr...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		res = FondWebService.checkPatientByFioDr( aRequest, getPatientInfo(aPatientId, service),aLastname, aFirstname
				, aMiddlename,  aBirthday) ;
		return res ;
	}
	public Object checkPatientByDocument(Long aPatientId, Long aType, String aSeries
			,String aNumber,HttpServletRequest aRequest) throws Exception {
		Object res = null;
		//System.out.println("checking doc...") ;
		//System.out.println("---username:"+LoginInfo.find(aRequest.getSession(true)).getUsername()) ;
		//System.out.println(new Date()) ;
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		String type = service.getOmcCodeByPassportType(aType) ;
		res = FondWebService.checkPatientByDocument(aRequest, getPatientInfo(aPatientId, service),type, aSeries, aNumber) ;
		return res ;
	}
	private PatientForm getPatientInfo(Long aPatientId, IPatientService aService) {
		return (aPatientId!=null &&aPatientId>Long.valueOf(0))?aService.getPatientById(aPatientId):null ;
	}
	public String getAge(String aDate)  {
		try {
			java.sql.Date birthday = DateFormat.parseSqlDate(aDate) ;
			java.sql.Date curdate = new java.sql.Date(new java.util.Date().getTime()) ;
			return AgeUtil.getAgeCache(curdate, birthday, 3);
		} catch(Exception e) {
			System.out.println(e) ;
			return "" ;
		}
	}
	public String getDoubleByFio(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries,HttpServletRequest aRequest) throws NamingException, Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getDoubleByBaseData(aId , aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries) ;
	}
	public void createAdminChangeMessageByPatient (Long aSmo, String aType, String aTextInfo
			, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		String login = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sql.append("insert into AdminChangeJournal ( patient, createDate, createTime")
			.append(", createUsername, ctype,  annulRecord) ")
			.append("values (")	.append(aSmo).append(", current_date, current_time, '")
			.append(login)
			.append("', '")
			.append(aType)
			.append("','")
			.append(aTextInfo)
			.append("')")
						;	
		service.executeUpdateNativeSql(sql.toString()) ;
		
	}
	public void movePatientDoubleData(Long aIdNew, Long aIdOld,HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		service.movePatientDoubleData(aIdNew, aIdOld) ;
		createAdminChangeMessageByPatient(aIdNew, "MOVE_PATIENT_DOUBLE_DATA", "Перенесены данные из персоны "+aIdOld+" в "+aIdNew, aRequest) ;

	}
	public String getDoubleByFio(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries,String aAction, HttpServletRequest aRequest) throws NamingException, Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getDoubleByBaseData(aId , aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries, aAction) ;
	}
	public String getDoubleByFio(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries,String aAction, boolean aChechFull, HttpServletRequest aRequest) throws NamingException, Exception {
		boolean checkFullBirthday = false ;
		if (aChechFull) {
			checkFullBirthday = RolesHelper.checkRoles("/Policy/Mis/Patient/BanDoubleCreate", aRequest) ;
		}
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		return service.getDoubleByBaseData(aId , aLastname, aFirstname, aMiddlename, aSnils, aBirthday, aPassportNumber, aPassportSeries, aAction,checkFullBirthday) ;
	}
	public String addPatient(String aLastname, String aFirstname, String aMiddlename, String aBirthday, Long aSex, Long aSocialStatus, String aSnils, HttpServletRequest aRequest) throws Exception {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ;
		SnilsStringValidator val = new SnilsStringValidator() ;
		val.validate(aSnils, null, aRequest) ;
		
		return service.addPatient(aLastname, aFirstname, aMiddlename, aBirthday, aSex, aSocialStatus, aSnils) ;
	}
	public boolean setAddParamByMedCase(String aParam, Long aMedCase,Long aStatus,HttpServletRequest aRequest)throws Exception  {
		IPatientService service = Injection.find(aRequest).getService(IPatientService.class) ; 
		service.setAddParamByMedCase(aParam,aMedCase,aStatus);
		return true ;
	}
}
