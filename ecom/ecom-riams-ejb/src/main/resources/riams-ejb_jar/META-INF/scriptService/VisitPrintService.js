var map = new java.util.HashMap() ;
// Печать визитов
function printVisits(aCtx, aParams) {
	var ids1 = aParams.get("id") ;
	var ids2 = aParams.get("id") ;
	var ids = ids1.split(",") ;
	var ret = new java.lang.StringBuilder () ;
	
	
	var ret = new java.util.ArrayList() ;
	var FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("HH:mm") ;
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	
	var curTime = new java.sql.Time(current.getTime()) ;
	map.put("ids",ids.length) ;
	
	
	for (var i=1; i < ids.length; i++) {
		var id1=ids[i] ;
		if (id1.indexOf("!")!=-1) {
		
		
		var indlast = id1.lastIndexOf("!") ;
		var protId = id1.substring(indlast+1) ;
		var id1=id1.substring(0,indlast) ;
		indlast = id1.lastIndexOf("!") ;
		var visId = id1.substring(indlast+1) ;
		var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
				, new java.lang.Long(visId))
		var protocol = +protId>0?aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.protocol.Protocol
		, new java.lang.Long(protId)):null ;
		var mapS= new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		mapS.set1(medCase!=null?medCase:"");
		mapS.set2(recordMultiText(protocol!=null?protocol.record:""));
		mapS.set3(medCase!=null?medCase.patient:null);
		mapS.set4(protocol);
		mapS.set5(medCase!=null?(medCase.workFunctionExecute!=null?medCase.workFunctionExecute.getWorkFunctionInfo():""):"");
		
		
		var diagText1 = "",diagText2="",diagText3="",diagText4="" ;
		var i1=0,i2=0,i3=0,i4=0 ;
		var diagMkb1= "",diagMkb2="",diagMkb3="",diagMkb4="" ;
		var diagAcuity= "";
		var diagPrimary= "";
		var diagTravm="";
		var diagTravmMkb = "";
		var diagnosis = medCase.getDiagnosis() ;
		for(var ii=0; ii<diagnosis.size(); ii++) {
			var diag = diagnosis.get(ii);
			
			if (diag.priority!=null && diag.priority.id == 1) {
			if (!diagText1.equals("")) diagText1 = diagText1 +"; "; 
				diagText1 = diagText1 +  diag.name ;
				diagMkb1 = diagMkb1 + " " + +diag.idc10!=null?diag.idc10.code:"";
				diagAcuity = diagAcuity + " " + ( diag.acuity!=null?diag.acuity.name:"");
				diagPrimary = diagPrimary + " " + ( diag.primary!=null?diag.primary.name:"");
				if (diag.traumaType!=null) {diagTravm = diagTravm + " " + diag.traumaType.name}
				if (diag.idc10Reason!=null) {diagTravmMkb = diagTravmMkb + " " + diag.idc10Reason.code + " " + diag.idc10Reason.name}
			}
			if (diag.priority!=null && diag.priority.id == 2) {
			if (!diagText2.equals("")) diagText2 = diagText2 +"; ";
				diagText2 = diagText2 +  diag.name ;
				diagMkb2 = diagMkb2+" "+diag.idc10!=null?diag.idc10.code:"" ; 
			
			}
			if (diag.priority!=null && diag.priority.id == 3) {
				if (!diagText3.equals("")) diagText3 = diagText3 +"; ";
				diagText3 = diagText3 +  diag.name ;
				diagMkb3 = diagMkb3 + " " + +diag.idc10!=null?diag.idc10.code:"";
			
			}
			if (diag.priority!=null && diag.priority.id == 4) {
				if (!diagText4.equals("")) diagText4 = diagText4 +"; ";
				diagText4 = diagText4 +  diag.name ;
				diagMkb4 = diagMkb4 + " " + +diag.idc10!=null?diag.idc10.code:"";
			
			}
		}
		mapS.set6(diagMkb1+" "+diagText1) ;
		
		ret.add(mapS) ;
		
	}}
	
	map.put("visits",ret) ;
	
	return map ;
}

function categoryForeignNationals(aCtx,aParams) {
	var finishDate = aParams.get("finishDate");
	var beginDate = aParams.get("beginDate");
	var sql = "select coalesce(p.nationality_id,0)||':${param.beginDate}:${param.finishDate}',vn.name as vnname,count(*) as cntAll"
			+" ,count(distinct case when m.dtype='Visit' then m.id else null end) as polic"
			+" ,count(distinct case when m.dtype='Visit' and vss.code='CHARGED' then m.id else null end) as policCh"
			+" ,count(distinct case when m.dtype='HospitalMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then m.id else null end) as hospitAll"
			+" ,count(distinct case when m.dtype='HospitalMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then m.id else null end) as hospitAllCh"
			+" ,count(distinct case when m.dtype='HospitalMedCase' and vht.code='DAYTIMEHOSP' then m.id else null end) as hospitDn"
			+" ,count(distinct case when m.dtype='HospitalMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then m.id else null end) as hospitDnCh"
			+" from medcase m"
			+" left join patient p on p.id=m.patient_id"
			+" left join Omc_Oksm vn on vn.id=p.nationality_id"
			+" left join VocHospType vht on vht.id=m.hospType_id"
			+" left join VocServiceStream vss on vss.id=m.serviceStream_id"
			+" where (m.dtype='Visit' or m.dtype='HospitalMedCase')" 
			+" and m.dateStart between to_date('"+beginDate+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy')"
			+" group by p.nationality_id,vn.name" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	
	var ret = new java.util.ArrayList() ;
	
	for (var i=0;i<list.size();i++) {
		var wq = Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		var obj = list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<=7;j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		ret.add(wq) ;
	}
	map.put("list",ret) ;
	
	var sql="select coalesce(p.nationality_id,0)||':${param.beginDate}:${param.finishDate}',vn.name as vnname"
		+" ,count(distinct t.id) as polic"
		+" ,count(distinct case when vss.code='CHARGED' then t.id else null end) as policCh "
		+" from Ticket t"
		+" left join MedCard m on m.id=t.medCard_id"
		+" left join patient p on p.id=m.person_id"
		+" left join Omc_Oksm vn on vn.id=p.nationality_id"
		+" left join VocServiceStream vss on vss.id=t.vocPaymentType_id"
		+" where t.date between to_date('"+beginDate+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy')"
		+" group by p.nationality_id,vn.name " ;
	var list1 = aCtx.manager.createNativeQuery(sql).getResultList() ;
	
	var ret1 = new java.util.ArrayList() ;
	
	for (var i=0;i<list1.size();i++) {
		var wq = Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		var obj = list1.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<=7;j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		ret1.add(wq) ;
	}
	map.put("list1",ret1) ;
	map.put("period",beginDate+"-"+finishDate) ;
	return map ;
}
function printPlanHospital(aCtx,aParams) {
	//var map = new java.util.HashMap() ;
	var doc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed
			, new java.lang.Long(aParams.get("id"))) ;
	var medCase = doc.visit ;
	var patient = doc.patient ;
	var list = aCtx.manager.createNativeQuery("select mp.id,mp.patient_id from MedPolicy mp where mp.patient_id='"+doc.patient.id+"' and mp.DTYPE like 'MedPolicyOmc%' AND (CURRENT_DATE >= mp.actualDateFrom and (mp.actualDateTo is null or mp.actualDateTo >=CURRENT_DATE))")
	.getResultList();
	if (list.size()>0) {
		var pol = list.get(0)[0] ;
		var policy = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy
				, new java.lang.Long(pol)) ;
		map.put("policyOmc", policy!=null? policy.text : "") ;
	} else {
		map.put("policyOmc", "") ;
	}
	map.put("pat", patient) ;
	map.put("medCase", medCase) ;
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	map.put("diagnosis",recordMultiText(doc.diagnosis)) ;
	var wf = doc.workFunction ;
	var pers = wf.worker!=null?wf.worker.person:null;
	var spec = "_____________________" ;
	if (pers!=null) {
		spec=pers.lastname+" "+pers.firstname+" "+pers.middlename
	} 
	map.put("specCODE",wf.code!=null?wf.code:"_____________") ;
	map.put("doc.workFunctionInfo",(wf.workFunction!=null?wf.workFunction.name:"_____________")+" " + spec) ;
	map.put("doc.date",medCase.dateStart!=null?FORMAT_2.format(medCase.dateStart):"") ;
	map.put("doc.plandate",doc.dateFrom!=null?FORMAT_2.format(doc.dateFrom):"") ;
	map.put("doc.mkbCode",doc.idc10!=null?doc.idc10.code:"") ;
	map.put("department",doc.department!=null?doc.department.name:"") ;
	return map ;
}
function printDocument(aCtx,aParams) {
	//var map = new java.util.HashMap() ;
	var doc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.licence.InternalDocuments
		, new java.lang.Long(aParams.get("id"))) ;
	var medCase = doc.medCase ;
	var list = aCtx.manager.createNativeQuery("select mp.id,mp.patient_id from MedPolicy mp where mp.patient_id='"+doc.medCase.patient.id+"' and mp.DTYPE like 'MedPolicyOmc%' AND (CURRENT_DATE >= mp.actualDateFrom and (mp.actualDateTo is null or mp.actualDateTo >=CURRENT_DATE))")
	.getResultList();
	if (list.size()>0) {
		var pol = list.get(0)[0] ;
		var policy = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.MedPolicy
				, new java.lang.Long(pol)) ;
		map.put("policyOmc", policy!=null? policy.text : "") ;
	} else {
		map.put("policyOmc", "") ;
	}
	map.put("pat", medCase.patient) ;
	map.put("id", medCase.getId()) ;
	map.put("medCase", medCase) ;
	map.put("document",doc) ;
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	map.put("history", recordMultiText(doc.history)) ;
	map.put("diagnosis", recordMultiText(doc.diagnosis)) ;
	var wf = medCase.workFunctionPlan ;
	var pers = wf.worker!=null?wf.worker.person:null;
	var spec = "_____________________" ;
	if (pers!=null) {
		spec=pers.lastname+" "+pers.firstname+" "+pers.middlename
	} 
	map.put("specCODE",wf.code!=null?wf.code:"_____________") ;
	map.put("doc.workFunctionInfo",(wf.workFunction!=null?wf.workFunction.name:"_____________")+" " + spec) ;
	map.put("doc.date",medCase.dateStart!=null?FORMAT_2.format(medCase.dateStart):"") ;
	map.put("doc.mkbCode",doc.idc10!=null?doc.idc10.code:"") ;
	map.put("doc.dateIssued",doc.dateIssued!=null?FORMAT_2.format(doc.dateIssued):"") ;
	return map ;
}
function printBakExp(aCtx, aParams) {
	var map = new java.util.HashMap() ;
	var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, new java.lang.Long(aParams.get("id"))) ;
	map.put("pat", visit.patient) ;
	map.put("id", visit.getId()) ;
	map.put("visit", visit) ;
	map.put("dateReg", visit.dateStart) ;
	return map ;
}
function printMedService(aCtx,aParams) {
	var service = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	var list = aCtx.manager.createQuery("from Protocol where medCase_id=:visit")
		.setParameter("visit",service.id).getResultList();
	var protocol = !list.isEmpty()?list.iterator().next().record:"";
	map.put("protocol",recordMultiText(protocol)) ;
	map.put("id", service.getId()) ;
	map.put("service",service) ;
	map.put("pat", visit.patient) ;
	var zac="ЗАКЛЮЧЕНИЕ";
	map.put("zac",zac);
	map.put("infoParent", service.parent!=null?service.parent.info:"") ;
	
}
function printVisit(aCtx, aParams) {
	//var map = new java.util.HashMap() ;
	var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase
		, new java.lang.Long(aParams.get("id"))) ;
	var list = aCtx.manager.createQuery("from Protocol where medCase_id=:visit")
		.setParameter("visit",visit.id).getResultList();
	var protocol = !list.isEmpty()?list.iterator().next().record:"";
	map.put("protocol", recordMultiText(protocol)) ;
	//map.put("protocol",protocol);
	map.put("id", visit.getId()) ;
	map.put("visit", visit) ;
	map.put("pat", visit.patient) ;
	var zac="ЗАКЛЮЧЕНИЕ";
	map.put("zac",zac);
	var diagText1 = "",diagText2="",diagText3="",diagText4="" ;
	var i1=0,i2=0,i3=0,i4=0 ;
	var diagMkb1= "",diagMkb2="",diagMkb3="",diagMkb4="" ;
	var diagAcuity= "";
	var diagPrimary= "";
	var diagTravm="";
	var diagTravmMkb = "";
	var diagnosis = visit.getDiagnosis() ;
	for(var i=0; i<diagnosis.size(); i++) {
		var diag = diagnosis.get(i);
		
		if (diag.priority!=null && diag.priority.id == 1) {
		if (!diagText1.equals("")) diagText1 = diagText1 +"; "; 
			diagText1 = diagText1 +  diag.name ;
			diagMkb1 = diagMkb1 + " " + +diag.idc10!=null?diag.idc10.code:"";
			diagAcuity = diagAcuity + " " + ( diag.acuity!=null?diag.acuity.name:"");
			diagPrimary = diagPrimary + " " + ( diag.primary!=null?diag.primary.name:"");
			if (diag.traumaType!=null) {diagTravm = diagTravm + " " + diag.traumaType.name}
			if (diag.idc10Reason!=null) {diagTravmMkb = diagTravmMkb + " " + diag.idc10Reason.code + " " + diag.idc10Reason.name}
			//diagTravmMkb = diagTravmMkb + " " + diag.idc10Reason.getCode() + " " + diag.idc10Reason.getName();
		}
		if (diag.priority!=null && diag.priority.id == 2) {
		if (!diagText2.equals("")) diagText2 = diagText2 +"; ";
			diagText2 = diagText2 +  diag.name ;
			diagMkb2 = diagMkb2+" "+diag.idc10!=null?diag.idc10.code:"" ; 
		
		}
		if (diag.priority!=null && diag.priority.id == 3) {
			if (!diagText3.equals("")) diagText3 = diagText3 +"; ";
			diagText3 = diagText3 +  diag.name ;
			diagMkb3 = diagMkb3 + " " + +diag.idc10!=null?diag.idc10.code:"";
		
		}
		if (diag.priority!=null && diag.priority.id == 4) {
			if (!diagText4.equals("")) diagText4 = diagText4 +"; ";
			diagText4 = diagText4 +  diag.name ;
			diagMkb4 = diagMkb4 + " " + +diag.idc10!=null?diag.idc10.code:"";
		
		}
	}
	map.put("diag1", diagText1) ;
	map.put("diag2", diagText2) ;
	map.put("diag3", diagText3) ;
	map.put("diag4", diagText4) ;
	map.put("diagMkb",diagMkb1);	
	map.put("diagMkb1",diagMkb1);
	map.put("diagMkb2",diagMkb2);
	map.put("diagMkb3",diagMkb3);
	map.put("diagMkb4",diagMkb4);
	map.put("diagAcuity",diagAcuity);
	map.put("diagPrimary",diagPrimary);
	map.put("diagTravm",diagTravm);
	map.put("diagTravmMkb",diagTravmMkb);
	var vacText="";
	var vaccination=visit.getVaccinations();
	for(var i=0;i<vaccination.size();i++){
		var vac = vaccination.get(i);
		vacText = (i+1) + ". " + vacText + vac.name ;
	}
	
	map.put("vac",vacText);
	var postName="";
	
	if(visit.workFunctionExecute!=null && visit.workFunctionExecute.workFunction!=null){
		postName=visit.workFunctionExecute.workFunction.name
	}
	
	map.put("postName",postName);
	
	
    
    //recordVocProba("disDocSt", ticket.disabilityDocumentStatus, 1, 2);
    //recordVocProba("disReas", ticket.disabilityReason, 1, 6);
    //recordVocProba("disSex", ticket.sex, 1, 2);
	visit.setIsPrint(new java.lang.Long(2)) ;
	return map ;
}
function printTalon1(aCtx,aParams) {
	//map = printVisit(aCtx, aParams) ;
	var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
			, new java.lang.Long(aParams.get("id"))) ;
	
	var pat = visit.patient ;
	map.put("pat", pat) ;
	
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	var cal = java.util.Calendar.getInstance() ;
	cal.setTime(currentDate) ;
	cal.add(java.util.Calendar.DATE,14) ;
	map.put("dateTo",FORMAT_2.format(cal.getTime())) ;
	map.put("sex", pat.sex!=null?pat.sex.omcCode=="1"?"М":"Ж":"М Ж");
	map.put("serviceStream",visit.serviceStream!=null?visit.serviceStream.name:"1-ОМС,2-бюджет,3-плат.усл.,4-ДМС,5-др") ;
	map.put("rayon",pat.rayon!=null?pat.rayon.omcCode:"____");
	
	if (pat.address!=null) {
		var city = pat.address.addressIsCity ;
		var vil = pat.address.addressIsVillage ;
		if (city!=null && city) {
			map.put("resident","гор.") ;
		} else if(vil!=null && vil) {
			map.put("resident","сел.") ;
		} else {
			map.put("resident","___")
		}
		
	} else {
		map.put("resident","___") ;
	}
	map.put("whomDirect",visit.orderLpu!=null?visit.orderLpu.name:"_____________");
	var wf = visit.workFunctionPlan ;
	var pers = wf.worker!=null?wf.worker.person:null;
	if (pers!=null) {
		map.put("specFIO",pers.lastname+" "+pers.firstname+" "+pers.middlename) ;
	} else {
		map.put("specFIO","_____________________") ;
	}
	map.put("specCODE",wf.code!=null?wf.code:"_____________") ;
	map.put("workfunction",wf.workFunction!=null?wf.workFunction.name:"_____________") ;
	map.put("usluga","") ;
	map.put("polSer","") ;
	map.put("polNum","") ;
	map.put("polSK","") ;
	
	return map ;
}
function printTalon(aCtx, aParams) {
	map = printVisit(aCtx, aParams) ;
	var visit = map.get("visit") ;
	
	
	recordVocProba("ill", visit.diagnosis.size()>1?visit.diagnosis.get(0).primary:null, 1, 2);
	//map.put("ill", visit.diagnosis.size());
    recordVocProba("tr", visit.diagnosis.traumaType, 1, 3);
	recordVocProba("sex", visit.patient.sex, 1, 2);
    recordVocProba("paym", visit.serviceStream, 1, 5);
    recordVocProba("servPl", visit.workPlaceType, 1, 3);
    recordVocProba("reas", visit.visitReason, 1, 3);
    recordVocProba("res", visit.visitResult, 1, 10);
    recordVocProba("disp", visit.dispRegistration, 1, 4);
    recordBoolean("city",visit.patient.address.addressIsCity);
	recordBoolean("village",visit.patient.address.addressIsVillage);
		
		
	return map ;
}				
function recordBoolean(aKey,aBool) {
	if (aBool!=null && aBool==true) {
		map.put(aKey+".k1","<text:span text:style-name=\"T14\">") ;
		map.put(aKey+".k2","</text:span>");
	} else {
		map.put(aKey+".k1","") ;
		map.put(aKey+".k2","");
	}
}
function recordVocProba(aKey, aValue, aMin, aMax) {
	for (i=aMin;i<=aMax;i++) {
		map.put(aKey+i+".k2","");
		map.put(aKey+i+".k1","");
	}

	if (aValue!=null) {
		var ind= aValue.id ;
		map.put(aKey+ind+".k1","<text:span text:style-name=\"T13\">");
		map.put(aKey+ind+".k2","</text:span>");
	}
}

function recordMultiText(aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	var items = val.split(n);
	var list = new java.util.ArrayList() ;
	for (var i = 0; i < items.length; i++) {
		var prot = Packages.ru.ecom.poly.ejb.form.protocol.ProtocolForm() ;
		prot.setRecord(items[i]);
		list.add(prot);
	}
	return list;
}
