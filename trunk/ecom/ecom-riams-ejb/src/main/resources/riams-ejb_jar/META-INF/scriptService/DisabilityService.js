var map = new java.util.HashMap() ;

function printDocument(aCtx, aParams) {
	var id = new java.lang.Long(aParams.get("id")) ;
	var doc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.DisabilityDocument,
		id
	);
	var cas = doc.disabilityCase ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	
	var pat = doc.disabilityCase.patient ;
	
	var next = aCtx.manager.createNativeQuery("select dd.id,dd.number from DisabilityDocument dd where dd.prevDocument_id="+id).setMaxResults(1).getResultList() ;
	if (next.size()>0) {
		var number = next.get(0)[1] ;
		recordChar(""+number,12,"doc.nextdoc.number") ;
	} else {
		recordChar("",12,"doc.nextdoc.number") ;
	}
	recordChar(pat.lastname,28,"doc.lastname") ;
	recordChar(pat.firstname,28,"doc.firstname") ;
	recordChar(pat.middlename,28,"doc.middlename") ;
	recordDate(pat.birthday,"pat.birthday") ;
	recordCode(pat.sex!=null?pat.sex.omcCode:"",2,"pat.sex" );
	recordChar(doc.job,29,"pat.work.org.name") ;
	recordCode(doc.primarity!=null?doc.primarity.code:"",2,"doc.primary") ;
	
	recordChar(doc.disabilityReason!=null?doc.disabilityReason.codeF:"",2,"doc.reason.code") ;
	recordChar(doc.disabilityReason2!=null?doc.disabilityReason2.code:"",3,"doc.reason2.code") ;
	recordChar(doc.disabilityReasonChange!=null?doc.disabilityReasonChange.codeF:"",2,"doc.reasonChange.code") ;
	recordBoolean(cas.placementService,"pat.placementService") ;
	
	recordChar(doc.sanatoriumTicketNumber,7,"doc.sanat.number") ;
	recordChar(doc.sanatoriumOgrn,15,"doc.sanat.ogrn") ;
	recordDate(doc.sanatoriumDateFrom,"doc.sanat.dateFrom") ;
	recordDate(doc.sanatoriumDateTo,"doc.sanat.dateTo") ;

	recordCode(doc.workComboType!=null?doc.workComboType.code:"",2,"doc.workComboType") ;
	recordCode(cas.earlyPregnancyRegistration!=null&&cas.earlyPregnancyRegistration==true?1:2,2,"pat.earlyPregnancyRegistration") ;
	
	recordDate(doc.hospitalizedFrom,"doc.hospitalizedFrom");
	recordDate(doc.hospitalizedTo,"doc.hospitalizedTo");
	recordChar(doc.hospitalizedNumber,10,"pat.card.number") ;
	recordDate(doc.issueDate,"doc.issueDate");
	
	
	recordChar(doc.prevDocument!=null?doc.prevDocument.number:"",12,"doc.prevdoc.number") ;
	var msc = doc.medSocCommission;
	if (msc!=null) {
		recordDate(msc.assignmentDate,"doc.medSocCommission.assignmentDate") ;
		recordDate(msc.registrationDate,"doc.medSocCommission.registrationDate") ;
		recordDate(msc.examinationDate,"doc.medSocCommission.examinationDate") ;
		recordBoolean(msc.invalidityRegistration,"doc.medSocCommission.invalidityReg") ;
	} else {
		recordDate(null,"doc.medSocCommission.assignmentDate") ;
		recordDate(null,"doc.medSocCommission.registrationDate") ;
		recordDate(null,"doc.medSocCommission.examinationDate") ;
		recordBoolean(false,"doc.medSocCommission.invalidityReg") ;
	}
	
	var regV = doc.regimeViolationRecords.size()>0?doc.regimeViolationRecords.get(0):null ;
	
	if (regV!=null) {
		recordDate(regV.dateFrom,"doc.regimeViolation.date") ;
		recordChar(regV.regimeViolationType!=null?regV.regimeViolationType.codeF:"",2,"doc.regimeViolation.type") ;	
	} else {
		recordDate(null,"doc.regimeViolation.date") ;
		recordChar("",2,"doc.regimeViolation.type") ;	
	}
	var records = doc.disabilityRecords ;
	var recordsSize = records.size() ;
	var lastDate ;
	var firstDate=null
	var lastWorker ;
	var lastDoctor ;
	for (var i=1;i<4;i++) {
		var rec = null ;
		if (recordsSize>=i) rec=records.get(i-1) ;
		if (rec==null) {
			recordDate(null,"doc.record"+i+".dateFrom") ;
			recordDate(null,"doc.record"+i+".dateTo") ;
			recordChar("",9,"doc.record"+i+".doctor.post") ;
			recordChar("",14,"doc.record"+i+".doctor.fio") ;
			recordChar("",9,"doc.record"+i+".doctorAdd.post") ;
			recordChar("",14,"doc.record"+i+".doctorAdd.fio") ;
		} else {
			if (firstDate== null) firstDate=rec.dateFrom ;
			lastDate=rec.dateTo ;
			recordDate(rec.dateFrom,"doc.record"+i+".dateFrom") ;
			recordDate(rec.dateTo,"doc.record"+i+".dateTo") ;
			var wf=rec.workFunction ;
			var wfAdd=rec.workFunctionAdd ;
			var vwf=wf!=null?wf.workFunction:null ;
			var vwfAdd=wfAdd!=null?wfAdd.workFunction:null ;
			var worker = wf!=null?wf.worker:null;
			var workerAdd = wfAdd!=null?wfAdd.worker:null;
			lastWorker = workerAdd!=null?workerAdd:worker ;
			
			var doctor = worker!=null?worker.person:null ;
			var doctorAdd = workerAdd!=null?workerAdd.person:null ;
			lastDoctor = doctorAdd!=null?doctorAdd:doctor ;
			recordChar(vwf!=null?vwf.shortName:"",9,"doc.record"+i+".doctor.post") ;
			recordChar(doctor!=null?doctor.lastname+" "+doctor.firstname.substring(0,1)+" "+doctor.middlename.substring(0,1):"",14,"doc.record"+i+".doctor.fio") ;
			recordChar(vwfAdd!=null?vwfAdd.shortName:"",9,"doc.record"+i+".doctorAdd.post") ;
			recordChar(doctorAdd!=null?doctorAdd.lastname+" "+doctorAdd.firstname.substring(0,1)+" "+doctorAdd.middlename.substring(0,1):"",14,"doc.record"+i+".doctorAdd.fio") ;
		}
	}
	recordChar(doc.mainWorkDocumentNumber,12,"doc.maindoc.number") ;
	var closeReason = doc.closeReason ;
	var closeReasonCode = closeReason!=null? closeReason.code:"0" ;
	var dateClose =null;
	
	if (lastDate!=null) {
		var cal = java.util.Calendar.getInstance() ;
		cal.setTime(lastDate) ;
		cal.add(java.util.Calendar.DAY_OF_MONTH,1) ;
		dateClose=new java.sql.Date(cal.getTime().getTime()) ;
	}
	
	if (+closeReasonCode>1) {
		recordDate(null,"doc.endDate") ;
		recordDate(dateClose,"doc.otherEnd.date") ;
		recordChar(closeReason!=null?closeReason.codef:"",2,"doc.otherEnd.code") ;
	} else {
		recordDate(dateClose,"doc.endDate") ;
		recordDate(null,"doc.otherEnd.date") ;
		recordChar("",2,"doc.otherEnd.code") ;
	}
	var lpu=lastWorker!=null?lastWorker.lpu:null ;
	if (lpu==null) {
		recordChar("",38,"doc.lpu.name");
		recordChar("",38,"doc.lpu.address");
		recordChar("",38,"doc.lpu.ogrn");
	} else {
		recordChar(lpu.printName,38,"doc.lpu.name");
		recordChar(lpu.printAddress,38,"doc.lpu.address");
		recordChar(lpu.ogrn>0?""+lpu.ogrn:"",38,"doc.lpu.ogrn");
	}
	
	recordChar(lastDoctor!=null? lastDoctor.lastname+" "+lastDoctor.firstname.substring(0,1)+" "+lastDoctor.middlename.substring(0,1):"",16,"doc.lastworker.fio");
	//.lastname+" "+lastWorker.firstname.substring(0,1)+" "+lastWorker.middlename.substring(0,1)
	//recordChar("ФАМИЛИЯ",28,"doc.lastname") ;
	//recordChar("ИМЯ",28,"doc.firstname") ;
	//recordChar("ОТЧЕСТВО",28,"doc.middlename") ;
	//recordDate(curDate,"pat.birthday") ;
	//recordCode(1,2,"pat.sex" );
	//recordChar("МЕСТО РАБОТЫ",29,"pat.work.org.name") ;
	//recordCode(1,2,"doc.primary") ;
	//recordChar("main Work Document",12,"doc.maindoc.number") ;
	//recordDate(curDate,"doc.hospitalizedFrom");
	//recordDate(curDate,"doc.hospitalizedTo");
	//recordDate(curDate,"doc.issueDate");
	//recordChar("next number",12,"doc.nextdoc.number") ;
	//recordChar("number card",10,"pat.card.number") ;
	//recordChar("НАИМЕНОВАНИЕ МЕД. ОРГАНИЗАЦИИ",38,"doc.lpu.name");
	//recordChar("АДРЕС МЕД. ОРГАНИЗАЦИИ",38,"doc.lpu.address");
	//recordChar("ОГРН",38,"doc.lpu.ogrn");
	
	var care1 = cas.nursingPerson1 ;
	var care2 = cas.nursingPerson2 ;
	for (var i=1;i<3;i++) {
		var care ;
		if (i==1) {
			care = care1 ;
		} else {
			care = care2 ;
		}
		var age_ye = "" ;
		var age_mo = "" ;
		var kinship = "" ;
		var kinpat=null ;
		if (care!=null) { 
			var kinship = care.kinsmanRole.code ;
			var kinpat = care.kinsman ;
			if (kinpat!=null) {
				var age = Packages.ru.nuzmsh.util.date.AgeUtil.calculateAge(kinpat.birthday,firstDate) ;
				var age_ind1 = age.indexOf(".") ;
				var age_ind2 = age.indexOf(".",age_ind1+1) ; 
				age_ye=age.substring(0,age_ind1) ;
				if (+age_ye<10) age_ye="0"+age_ye ;
				age_mo=age.substring(age_ind1+1,age_ind2) ;
				if (+age_mo<10) age_mo="0"+age_mo ;
			}
		}
		recordChar(age_ye,2,"doc.care"+i+".age.year") ;
		recordChar(age_mo,2,"doc.care"+i+".age.month") ;
		
		recordChar(kinship,2,"doc.care"+i+".kinship.code") ;
		recordChar(kinpat!=null?kinpat.lastname+" "+kinpat.firstname+" "+kinpat.middlename:"",39,"doc.care"+i+".kinship.fio") ;
	}
	//recordChar("11",2,"doc.reason.code") ;
	//recordChar("222",3,"doc.reason2.code") ;
	//recordChar("33",2,"doc.reasonChange.code") ;
	//recordBoolean(true,"pat.placementService") ;
	//recordChar("sanatnumber",7,"doc.sanat.number") ;
	//recordChar("ogrn sanat",15,"doc.sanat.ogrn") ;
	//recordDate(curDate,"doc.sanat.dateFrom") ;
	//recordDate(curDate,"doc.sanat.dateTo") ;
	//recordDate(curDate,"doc.medSocCommission.assignmentDate") ;
	//recordDate(curDate,"doc.medSocCommission.registrationDate") ;
	//recordDate(curDate,"doc.medSocCommission.examinationDate") ;
	//recordBoolean(true,"doc.medSocCommission.invalidityReg") ;
	//recordCode(1,2,"doc.workComboType") ;
	//recordCode(true==true?1:2,2,"pat.earlyPregnancyRegistration") ;
	//for (var i=1;i<4;i++) {
	//	recordDate(curDate,"doc.record"+i+".dateFrom") ;
	//	recordDate(curDate,"doc.record"+i+".dateTo") ;
	//	recordChar("POST DOCTOR",18,"doc.record"+i+".doctor.post") ;
	//	recordChar("FIO DOCTOR",28,"doc.record"+i+".doctor.fio") ;
	//}
	//recordDate(curDate,"doc.regimeViolation.date") ;
	//recordChar("33",2,"doc.regimeViolation.type") ;	
	//recordDate(curDate,"doc.endDate") ;
	//recordDate(curDate,"doc.otherEnd.date") ;
	//recordChar("44",2,"doc.otherEnd.code") ;
	return map ;
}
function recordBoolean(aValue,aKey) {
	if (aValue==null || aValue==false) {
		map.put(aKey,"") ;
	} else {
		map.put(aKey,"V") ;
	}
}
function recordChar(aStr,aCnt,aKey) {
	if (aStr==null) aStr="" ;
	aStr=(""+aStr).toUpperCase() ;
	//aStr=aStr;
	for (var i=0;i<aStr.length; i++) {
		map.put(aKey+(i+1),aStr.substring(i,i+1)) ;
	}
	for (var i=aStr.length+1 ; i<=aCnt; i++) {
		map.put(aKey+(i),"") ;
	}
}
function recordCode(aCode,aCnt,aKey) {
	for (var i=1 ; i<=aCnt; i++) {
		if (+aCode==i) {
			map.put(aKey+i,"V") ;
		} else {
			map.put(aKey+i,"") ;
		}
	}
}
function recordDate(aDate,aKey) {
	var FORMAT_0 = new java.text.SimpleDateFormat("ddMMyyyy") ;
	var date = aDate==null?"":""+FORMAT_0.format(aDate);
	//var date = "12345678" ;
	recordChar(date,8,aKey) ;
}
/**
 * Список DisabilityCase по пациенту
 */
function findDisabilityCasesByPatient(aContext, aPatientId) {
	var patient = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
		, new java.lang.Long(aPatientId)) ;
	var list = aContext.manager.createQuery(
		"from DisabilityCase where medCase.patient=:patient")
		.setParameter("patient", patient)
		.getResultList() ;
	var ret = new java.util.ArrayList() ;
	var iterator = list.iterator() ;
	while(iterator.hasNext()) {
		var dis = iterator.next() ;
		var map = new java.util.HashMap() ;
		map.put("id", new java.lang.Long(dis.id)) ;
		map.put("dateFrom", dis.dateFrom) ;
		map.put("dateTo", dis.dateTo) ;
		map.put("duration", dis.duration) ;
		ret.add(map) ; 
	}
	return ret ;	
} 
