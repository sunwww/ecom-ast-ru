
function printInfoPatientByMedcase(aCtx, aParams) {
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
			, new java.lang.Long(aParams.get("id"))) ;
	return printInfoByPatient(medCase,aCtx) ;
}
function printInfoByPatient(aPatient,aCtx) {
	var map = new java.util.HashMap() ;
	var polList = aPatient.medPolicies ;
	var policy = null ;
	//for(var i = 0; i<polList.size(); i++) {	
	policy = polList.size()>0?polList.get(polList.size()-1):"" ;
	//}	
	map.put("pat", aPatient) ;
	
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(currentDate) ;
	cal.add(java.util.Calendar.DATE,14) ;
	map.put("dateTo",FORMAT_2.format(cal.getTime())) ;
	map.put("policyText", policy!=null? policy.text : "") ;
	var medcard = aPatient.medcard.size()>0?aPatient.medcard.get(0):null ;
	if (medcard!=null) {
		map.put("card.code",medcard.number) ;
		map.put("card.cardIndex",medcard.cardIndex!=null?medcard.cardIndex.name:"") ;
	} else {
		map.put("card.code","") ;
		map.put("card.cardIndex","") ;
	}
	var listInv = aCtx.manager.createQuery("from Invalidity where patient_id=:pat and (nextRevisionDate>=CURRENT_DATE or withoutExam='1') order by dateFrom desc,revisionDate desc,lastRevisionDate desc")
	.setParameter("pat",aPatient.id).setMaxResults(1).getResultList() ;
	var inv=listInv.size()>0?listInv.get(0):null;
	var groupInv = inv!=null?inv.group:null ;
	map.put("groupInv",groupInv) ;
	var listPriv =  aCtx.manager.createQuery("from Privilege where person=:pat and endDate is null order by beginDate desc")
		.setParameter("pat",aPatient).setMaxResults(1).getResultList() ;
	var priv = listPriv.size()>0?listPriv.get(0):null ;
	map.put("priv.info","") ;
	map.put("priv.obj",priv) ;
	map.put("priv.doc",priv!=null?priv.document:null) ;
	map.put("priv.code",priv!=null?(priv.privilegeCode!=null?priv.privilegeCode:null):null) ;
	var listPolicy = aCtx.manager.createQuery("from MedPolicy where patient=:pat and (actualDateTo is null or actualDateTo>=current_date)")
		.setParameter("pat",aPatient).setMaxResults(1).getResultList() ;
	if (listPolicy.size()>0) {
		map.put("strax",listPolicy.get(0)) ;
	} else {
		map.put("strax",null) ;
	}
	var date = new java.util.Date() ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(date) ;
	cal.add(java.util.Calendar.DAY_OF_MONTH, 12) ;
	var toDate = cal.getTime() ;
	map.put("date", date) ;
	map.put("todate", toDate) ;
	
	var area = aPatient.getLpuArea() ;
	var areaText = "" ;
	if(area!=null) {
		areaText = area.getName() + ",  "+area.getComment()  ;
	}
	map.put("areaText", areaText) ;
	var ddD = null;var ddMkb=null ;
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry")) {
		var sqlD = "select to_char(case when (select max(po1.startDate) from psychiaticObservation po1"
			+" 				 left join VocpsychambulatoryCare vpac1 on vpac1.id=po1.ambulatoryCare_id"
			+" 				 where po.careCard_id=po1.careCard_id and vpac1.code!='Д') is null"
			+" 				then"
			+" 				(select min(po2.startDate) from psychiaticObservation po2 where" 
			+" 				po.careCard_id=po2.careCard_id)"
			+" 					else"
			+" 					(select min(po2.startDate) from psychiaticObservation po2 where"
			+" 					po.careCard_id=po2.careCard_id and po2.startDate>"
			+" 					 (select max(po1.startDate) from psychiaticObservation po1"
			+" 					 left join VocpsychambulatoryCare vpac1 on vpac1.id=po1.ambulatoryCare_id"
			+" 					 where po.careCard_id=po1.careCard_id and vpac1.code!='Д')"
			+" 					)"
			+" 					end,'dd.mm.yyyy')"
			+" 					as ddmin"

			+" 					,case when vpac.code='Д' then vpac.code else null end as pocode"

			+" 					 ,(select mkb.code from diagnosis d left join vocidc10 mkb on mkb.id=d.idc10_id "
			+" 							 left join vocprioritydiagnosis vpd on vpd.id=d.priority_id "
			+" 							 where d.patient_id='"+aPatient.id+"' and d.medcase_id is null and vpd.code='1'"
			+" 					 and d.establishDate=(select max(d1.establishDate) from diagnosis d1 "
			+" 							 left join vocprioritydiagnosis vpd1 on vpd1.id=d1.priority_id "
			+" 							 where d1.patient_id='"+aPatient.id+"' and d1.medcase_id is null and vpd1.code='1'"
			+" 					 and d1.establishDate<=current_date)) as mkbcode"

			+" 					 from psychiaticObservation po"
			+" 					 left join VocpsychambulatoryCare vpac on vpac.id=po.ambulatoryCare_id"
			+" 					 left join PsychiatricCareCard pcc on pcc.id=po.careCard_id"
			+" 					 where pcc.patient_id='"+aPatient.id+"'"
				 
			+" 					 having (select max(po1.startDate) from psychiaticObservation po1"
			+" 					 left join VocpsychambulatoryCare vpac1 on vpac1.id=po1.ambulatoryCare_id"
			+" 					 where po.careCard_id=po1.careCard_id"
			+" 					 )=po.startdate";

			
			
			
		var listD = aCtx.manager.createNativeQuery(sqlD).setMaxResults(1).getResultList() ;
		if (listD.size()>0) {
			var objD = listD.get(0) ;
			if (objD[1]!=null ) {
				ddD=objD[0] ;
				ddMkb=objD[2] ;
			}
			
		}
				
	} 
	map.put("ddiag",ddD) ;
	map.put("ddates",ddMkb) ;
	return map ;
}
function printInfo(aCtx, aParams) {
	
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aParams.get("id"))) ;
	return printInfoByPatient(patient,aCtx) ;
	//throw aParams+"" ;
}
function printAgreement(aCtx, aParams) {
	var map = new java.util.HashMap() ;
	return map ;
}