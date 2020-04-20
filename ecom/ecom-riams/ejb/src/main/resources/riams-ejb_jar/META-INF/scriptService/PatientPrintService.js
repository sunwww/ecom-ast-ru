var map = new java.util.HashMap() ;
/**Печать произвольных реквизитов для ЛПУ по умолчанию*/
function printDefaultLpuRequisites(aCtx, aFldName) {
    var lpu =aCtx.manager.createNativeQuery( "select keyvalue from softconfig  where key = 'DEFAULT_LPU' ").getResultList();
    if (lpu.size()>0) {
        printLpuRequisites(aCtx,+lpu.get(0),aFldName);
    }
}
/** Печать произвольных реквизитов по ЛПУ*/
function printLpuRequisites(aCtx, aLpuId, aFldName) {
    var map = new java.util.HashMap() ;
    var sql = "select code, value, name from MisLpuRequisite where lpu_id="+aLpuId;
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    for (var i=0;i<list.size();i++) {
        var obj = list.get(i);
        map.put(aFldName+"_"+obj[0],""+obj[1]);
        //throw ""+aFldName+"_"+obj[0]+"<>"+map.get(aFldName+"_"+obj[0]);
        map.put(aFldName+"_"+obj[0]+"Name",""+obj[2]);
    }
}

function printInfoPatientByMedcase(aCtx, aParams) {
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
			, new java.lang.Long(aParams.get("id"))) ;
	return printInfoByPatient(medCase,aCtx) ;
}
function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
	l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'").getResultList();
	if (l.isEmpty()) {
		return aValueDefault ;
	} else {
		return l.get(0)[1] ;
	}
}

function printInfoByPatient(aPatient,aCtx) {
	var map = new java.util.HashMap() ;
	
	var polList = aCtx.manager.createQuery("from MedPolicy where patient_id="+aPatient.id+" and actualDateTo is null order by actualDateFrom").getResultList() ; 
	var policy = null ;
    printDefaultLpuRequisites(aCtx,"DefaultLpu");
	map.put("address_lpu",getDefaultParameterByConfig("address_lpu","___________________",aCtx)) ;
	map.put("name_lpu",getDefaultParameterByConfig("name_lpu","___________________",aCtx)) ;
	map.put("ogrn_lpu",getDefaultParameterByConfig("ogrn_lpu","___________________",aCtx)) ;
	map.put("okud_lpu",getDefaultParameterByConfig("okud_lpu","_________",aCtx)) ;
	map.put("okpo_lpu",getDefaultParameterByConfig("okpo_lpu","_________",aCtx)) ;
	policy = polList.size()>0?polList.get(polList.size()-1):"" ;
	map.put("pat", aPatient) ;
	
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var psychDate = null ;
	//if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry")) {
		var psa=aCtx.manager.createNativeQuery("select pcc.id,to_char(pcc.firstPsychiatricVisitDate,'dd.mm.yyyy')"+
				" from PsychiatricCareCard pcc where pcc.patient_id="+aPatient.id).getResultList() ;
		if (psa.isEmpty()) {
			map.put("currentDate",FORMAT_2.format(currentDate)) ;
		} else {
			psychDate = psa.get(0)[1] ;
		}
	//} 
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	map.put("psychDate",psychDate) ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(currentDate) ;
	cal.add(java.util.Calendar.DATE,14) ;
	map.put("dateTo",FORMAT_2.format(cal.getTime())) ;
	map.put("policyText", policy!=null? policy.text : "") ;
	var medcardL = aCtx.manager.createQuery("from Medcard where person_id="+aPatient.id).getResultList() ;
	var medcard = medcardL.size()>0?medcardL.get(0):null ;
	map.put("card.code",medcard!=null ? medcard.number : aPatient.patientSync) ;
    map.put("card.cardIndex","") ;
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
	var ddList = new java.util.ArrayList() ;
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MisLpu/Psychiatry")) {
		
		var sqlD = "select to_char(lpcc.startdate,'dd.mm.yyyy') as p1ocode";
		sqlD =sqlD+"  , to_char(coalesce(lpcc.finishdate,lpcc.transferdate),'dd.mm.yyyy')  as p2ocode";
		sqlD =sqlD +" ,(select mkb.code||'#'||mkb.name from diagnosis d left";
		sqlD =sqlD +" join vocidc10 mkb on mkb.id=d.idc10_id";
		sqlD =sqlD +" left join vocprioritydiagnosis vpd on vpd.id=d.priority_id";
		sqlD =sqlD +" where d.patient_id=pcc.patient_id and d.medcase_id is ";
		sqlD =sqlD +" null and vpd.code='1'";
		sqlD =sqlD +" and d.establishDate=(select max(d1.establishDate) from";
		sqlD =sqlD +" diagnosis d1";
		sqlD =sqlD +" left join vocprioritydiagnosis vpd1 on vpd1.id=d1.priority_id";
		sqlD =sqlD +" where d1.patient_id=pcc.patient_id and d1.medcase_id";
		sqlD =sqlD +"  is null and vpd1.code='1'";
		sqlD =sqlD +"   and case when coalesce(lpcc.finishdate,lpcc.transferdate) is not null and d1.establishDate<coalesce(lpcc.finishdate,lpcc.transferdate) then '1' when coalesce(lpcc.finishdate,lpcc.transferdate) is null and d1.establishDate<=current_date then '1' else null end ='1')) as mkbcode";

		sqlD =sqlD +"   from psychiaticObservation po";
		sqlD =sqlD +"  left join LpuAreaPsychCareCard lpcc on lpcc.id=po.lpuAreaPsychCareCard_id";
		sqlD =sqlD +"   left join VocpsychambulatoryCare vpac on";
		sqlD =sqlD +"  vpac.id=po.ambulatoryCare_id";

		sqlD =sqlD +" left join PsychiatricCareCard pcc on pcc.id=po.careCard_id";
		sqlD =sqlD +" where pcc.patient_id='"+aPatient.id+"' and vpac.code='Д' and po.lpuAreaPsychCareCard_id is not null";
		sqlD =sqlD +" order by po.startdate" ;
			
		//throw sqlD ;
		
		var listD = aCtx.manager.createNativeQuery(sqlD).getResultList() ;
		//throw "listD"+listD.size() ;
		var d1=null ; var d2=null ;
		var iF=null;
		for (var i=0;i<listD.size();i++) {
			var objD = listD.get(i) ;
			
			if ((d2!=null && d1!=null &&(""+d2)!=(""+objD[0])
					)||
					((listD.size()-1)==i)) {
				if (listD.size()-1==i) {
					if (d2!=null && d1!=null &&(""+d2)!=(""+objD[0])) {
						var dd1 = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
						//throw ""+d2 ;
						dd1.set1(d1) ;
						dd1.set2(d2) ;
						dd1.set3(d3!=null?d3.split("#")[0]:null) ;
						dd1.set4(d3!=null?d3.split("#")[1]:null) ;
						ddList.add(dd1) ;
						d1=objD[0];d2=null;
					}
				}
				var dd = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				
				if (listD.size()-1==i) {
					dd.set1(d1!=null?d1:objD[0]) ;
					
					dd.set2(objD[1]) ;
				} else {
					dd.set1(d1!=null?d1:objD[0]) ;
					dd.set2(d2!=null?d2:objD[1]) ;
				}
				dd.set3(objD[2]!=null?objD[2].split("#")[0]:null) ;
				dd.set4(objD[2]!=null?objD[2].split("#")[1]:null) ;
				ddList.add(dd) ;
				d1=objD[0] ; d2 = objD[1] ; d3 = objD[2] ;
			} else {
				if (d1==null) d1=objD[0] ;
				d2 = objD[1] ; d3 = objD[2] ;
			}
		}	
	} 
	map.put("ddates",null) ;
	map.put("ddiag",null) ;
	map.put("ddiagn",null) ;
	map.put("ddlist",ddList) ;
	return map ;
}
function printInfo(aCtx, aParams) {
	
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aParams.get("id"))) ;
	return printInfoByPatient(patient,aCtx) ;
	//throw aParams+"" ;
}
function printAgreement(aCtx, aParams) {
	//var map = new java.util.HashMap() ;
	//return map ;
	return printInfo(aCtx, aParams);
}

function printCovid(aCtx, aParams) {
	var covidCard = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Covid19, new java.lang.Long(aParams.get(("id"))));
	map.put("card", covidCard);
	map.put("pat", covidCard.patient);
	var hosp = covidCard.medCase;
	map.put("hosp", hosp);
	map.put("isLabConfirmed",covidCard.labResult=="1" ? "да":"нет");
	var age = Packages.ru.nuzmsh.util.date.AgeUtil.calcAgeYear(covidCard.patient.birthday,hosp.dateStart) ;
	map.put("age",age+"");
	map.put("contactList",aCtx.manager.createNamedQuery("Covid19Contact.getAllByPatient")
		.setParameter("patient",covidCard.getPatient()).getResultList());

	return map;


}