var map = new java.util.HashMap() ;
function printNoActuality(aCtx, aParams) {
	var typeDocument = aParams.get("typeDocument") ;
	var typeDate = aParams.get("typeDate") ;
	var beginDateR = aParams.get("beginDate") ;
	var endDateR = aParams.get("endDate") ;
	var noActuality = aParams.get("noActuality") ;
	var closeReason = aParams.get("closeReason") ;
	var primarity = aParams.get("primarity") ;
	var disabilityReason = aParams.get("disabilityReason") ;
	var sn =aParams.get("sn") ;
	if (+sn<1) {sn=1} else {sn=+sn}
	var sqlReason = "" ;
	if (+disabilityReason>0) {sqlReason = sqlReason+" and dd.disabilityReason_id="+disabilityReason ;}
	if (+closeReason>0) {sqlReason = sqlReason+" and dd.closeReason_id="+closeReason ;}
    if (+primarity>0) { sqlReason= " and dd.primarity_id="+primarity ;} 
	var status ="";
	if (typeDocument!=null && typeDocument=="1") {
    	status="cast(dd.isclose as int) =1 and " ;
    } else if (typeDocument!=null && typeDocument=="2") {
    	status="(dd.isclose is null or cast(dd.isclose as int) =0) and " ;        	
    } 
	var dateGroup ;
	if (typeDate!=null && typeDate=="1") {
    	dateGroup="(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
    } else if (typeDate!=null&&typeDate=="2") {
    	dateGroup="(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
    } else {
    	dateGroup="dd.issueDate" ;
    }
	//var statusNoActuality="cast(dd.noActuality as int) =1 and " ;
    var beginDate = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(beginDateR) ;
    
    var endDate = beginDate ;
    if (endDateR!=null && !endDateR.equals("")) {
    	endDate = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(endDateR)  ;
    }
    var sql = "select dd.id as ddid,to_char(dd.issueDate,'dd.MM.yyyy') as date1"
       +" ,dd.number as dnumber,dd.hospitalizedNumber as ddhosnumber"
       +" ,p.lastname||' '||p.firstname||' '||p.middlename as pat"
       +" ,dd.job as ddjob,dd.workComboType_id as ddcombo"
		+" ,COALESCE(vddcr.shortName,vddcr.name) as vddcrname"
		+", p.id as pid,dd.anotherLpu_id as ddanother,mdd.number as mnumber"
		+" , cast(vds.code as int) as mddvdscode,su.fullname as sufullname"
        +" from disabilitydocument as dd" 
    	+" left join disabilitycase dc on dc.id=dd.disabilityCase_id"
        +"	left join patient p on p.id=dc.patient_id"
		+" left join disabilitydocument mdd on mdd.duplicate_id=dd.id"
		+" left join vocDisabilityStatus vds on vds.id=mdd.status_id"
		+" left join SecUser su on su.login=dd.createUsername"
		+" left join vocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id "
        +" 	where "+status+" "
        //+statusNoActuality
        +" "+ dateGroup +" between cast('"+beginDate+"' as date) and cast('"+endDate+"' as date) "+sqlReason+" and mdd.id is not null order by "+dateGroup+",dd.number " ;
    //throw sql ;
    var list=aCtx.manager.createNativeQuery(sql).getResultList() ;
    var listR = new java.util.ArrayList() ;
    
    for (var i=0;i<list.size();i++) {
    	var o = list.get(i) ;
    	var obj = Packages.ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm() ;
    	var dateRecordF=null,dateRecordL=null ;
    	obj.setId(new java.lang.Long(o[0])) ;
		obj.setSn(sn) ;
		if (o[11]!=null && +o[11]==1) {
	    	obj.setNumber(o[10]) ;
	    	obj.setOtherNumber("") ;
	    	obj.setDateFrom("1") ;
	    	obj.setDateTo("") ;
		} else {
	    	obj.setNumber("") ;
	    	obj.setOtherNumber(o[10]) ;
	    	obj.setDateFrom("") ;
	    	obj.setDateTo("1") ;
		}
    	obj.setHospitalizedNumber(o[3]) ;
    	obj.setPatientFio(o[4]) ;
    	obj.setDuration(java.lang.Integer.valueOf("1").intValue()) ;
    	obj.setCreateUsername(o[12]) ;
    	obj.setIssueDate(o[1]) ;
    	listR.add(obj) ;
    	sn++ ;
    }
    map.put("listDocuments",listR);
	return map ;
}
function printJournal(aCtx, aParams) {
	var typeDocument = aParams.get("typeDocument") ;
	var typeDate = aParams.get("typeDate") ;
	var beginDateR = aParams.get("beginDate") ;
	var endDateR = aParams.get("endDate") ;
	var noActuality = aParams.get("noActuality") ;
	var closeReason = aParams.get("closeReason") ;
	var disabilityReason = aParams.get("disabilityReason") ;
	var primarity = aParams.get("primarity") ;
	var sn = aParams.get("sn") ;
	if (+sn<1) {sn=1} else {sn=+sn};
	var sqlReason = "" ;
	if (+disabilityReason>0) {sqlReason = sqlReason+" and dd.disabilityReason_id="+disabilityReason ;}
	if (+closeReason>0) {sqlReason = sqlReason+" and dd.closeReason_id="+closeReason ;}
    if (+primarity>0) { sqlReason= " and dd.primarity_id="+primarity ;} 
    var status="" ;
	if (typeDocument!=null && typeDocument=="1") {
    	status="cast(dd.isclose as int) =1 and " ;
    } else if (typeDocument!=null && typeDocument=="2") {
    	status="(dd.isclose is null or cast(dd.isclose as int) =0) and " ;        	
    } 
	var dateGroup ;
	if (typeDate!=null && typeDate=="1") {
    	dateGroup="(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
    } else if (typeDate!=null&&typeDate=="2") {
    	dateGroup="(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id)" ;
    } else {
    	dateGroup="dd.issueDate" ;
    }
	var statusNoActuality = "" ;
    if (noActuality!=null && noActuality.equals("1")) {
    	statusNoActuality="(dd.noActuality is null or cast(dd.noActuality as int) =0) and " ;        	

    } else if (noActuality!=null&&noActuality.equals("2")) {
    	statusNoActuality="cast(dd.noActuality as int) =1 and " ;
    }
    var beginDate = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(beginDateR) ;
    
    var endDate = beginDate ;
    if (endDateR!=null && !endDateR.equals("")) {
    	endDate = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(endDateR)  ;
    }
    var sql = "select dd.id as ddid,to_char(dd.issueDate,'dd.MM.yyyy') as date1"
       +" ,dd.number as ddnumber,dd.hospitalizedNumber as ddhosnumber"
       +" ,p.lastname||' '||p.firstname||' '||p.middlename as pat"
       +" ,dd.job as ddjob,dd.workComboType_id as ddcombo"
		+" ,mkbP.code as mkbPcode,mkbF.code as mkbFcode"
		+" ,COALESCE(vddcr.shortName,vddcr.name) as vddcrname"
		+" , case when (cast(dd.noActuality as int)=1) then ' испорчен' else '' end as ddnoA"
		+", p.id as pid,dd.anotherLpu_id"
        +"  	from disabilitydocument as dd" 
    	+"   	left join disabilitycase dc on dc.id=dd.disabilityCase_id"
        +"	left join patient p on p.id=dc.patient_id"
		+" left join vocidc10 mkbP on mkbP.id=dd.idc10_id"
		+" left join vocidc10 mkbF on mkbF.id=dd.idc10Final_id"
		+" left join vocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id "
        +" 	where "+status+" "+statusNoActuality+" "+ dateGroup +" between cast('"+beginDate+"' as date) and cast('"+endDate+"' as date) "+sqlReason+" order by "+dateGroup+" " ;
    
    var list=aCtx.manager.createNativeQuery(sql).getResultList() ;
    var listR = new java.util.ArrayList() ;
    
    for (var i=0;i<list.size();i++) {
    	var o = list.get(i) ;
    	var obj = Packages.ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm() ;
    	var dateRecordF=null,dateRecordL=null ;
    	var listD = aCtx.manager.createNativeQuery(
    			"select to_char(dr.dateFrom,'dd.MM.yyyy') as date1,to_char(dr.dateTo,'dd.MM.yyyy') as date2"
    				+" ,COALESCE(vwf.shortName,vwf.name)||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor"
    				+" ,dr.dateFrom as drdateFrom, dr.dateTo as drdateTo"
    				+" from DisabilityDocument dd "
    				+" left join DisabilityRecord dr on dr.disabilityDocument_id=dd.id"
    				+" left join workfunction wf on wf.id=dr.workFunction_id"
    				+" left join vocworkfunction vwf on vwf.id=wf.workFunction_id"
    				+" left join worker w on w.id=wf.worker_id"
    				+" left join patient wp on wp.id=w.person_id"
    				+" where dd.id="+o[0]+" order by dr.dateFrom").getResultList() ;
    	if (listD.size()>0) {
    		dateRecordF = listD.get(0)[3] ;
    		dateRecordL = listD.get(listD.size()-1)[4]
    		obj.setDateFrom(listD.get(0)[0]) ;
    		obj.setCreateUsername(listD.get(0)[2]) ;
    		obj.setDateTo(listD.get(listD.size()-1)[1]) ;
    		obj.setEditUsername(listD.get(listD.size()-1)[2]) ;
    	} else {
    		
    	}
    	var pat = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,new java.lang.Long(o[11]))
    	obj.setId(new java.lang.Long(o[0])) ;
		obj.setSn(sn) ;
		if (o[12]!=null && +o[12]>0) {
	    	obj.setNumber("") ;
	    	obj.setOtherNumber(o[2]+o[10]) ;
		} else {
	    	obj.setNumber(o[2]+o[10]) ;
	    	obj.setOtherNumber("") ;
		}
    	obj.setHospitalizedNumber(o[3]) ;
    	obj.setPatientFio(o[4]) ;
    	obj.setPatientAddress(pat.getAddressRegistration()) ;
    	obj.setDiagnosis(o[7]!=null?o[7]:"") ;
    	obj.setDiagnosisFinal(o[8]!=null?o[8]:"") ;
		obj.setCloseReasonInfo(o[9]!=null?o[9]:"") ;
    	obj.setDuration(Packages.ru.ecom.ejb.util.DurationUtil.getDurationMedCase(dateRecordF, dateRecordL, java.lang.Integer.valueOf("0").intValue(), java.lang.Integer.valueOf("1").intValue() )) ;
    	if (o[5]!=null && o[5]!="") {
    		if (o[6]!=null && +o[6]>0) {
    			obj.setJob("внешнее совместительство "+o[5]) ;
    		} else {
    			obj.setJob("основной "+o[5]) ;
    		}
    	} else {
    		obj.setJob(o[5]) ;
    	}
    	
    	obj.setIssueDate(o[1]) ;
    	listR.add(obj) ;
    	sn++;
    }
    map.put("listDocuments",listR);
	return map ;
}
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
	recordChar(+pat.middlename.length()>1?pat.middlename:"",28,"doc.middlename") ;
	recordDate(pat.birthday,"pat.birthday") ;
	recordCode(pat.sex!=null?pat.sex.omcCode:"",2,"pat.sex" );
	recordChar(doc.job,29,"pat.work.org.name") ;
	
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
	
	var duplicate = aCtx.manager.createNativeQuery("select dd.id,vds.code from DisabilityDocument dd left join VocDisabilityStatus vds on vds.id=dd.status_id where dd.duplicate_id="+id).setMaxResults(1).getResultList() ;
	if (duplicate.size()>0 && +duplicate.get(0)[1]==2) {
		recordCode(1,1,"doc.duplicate") ;
		recordChar("",12,"doc.prevdoc.number") ;
		recordCode("",2,"doc.primary") ;
	} else {
		recordCode("",1,"doc.duplicate") ;
		recordCode(doc.primarity!=null?doc.primarity.code:"",2,"doc.primary") ;
		recordChar(doc.prevDocument!=null?doc.prevDocument.number:"",12,"doc.prevdoc.number") ;
		
	}
	
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
	var records = aCtx.manager
		.createQuery("from DisabilityRecord where disabilityDocument_id="+id+" order by dateFrom").setMaxResults(3).getResultList() ;
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
			lastDoctor = doctor ;
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
		recordChar(closeReason!=null?closeReason.codeF:"",2,"doc.otherEnd.code") ;
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
function createDubble(aCtx,aDocument) {
	
}
