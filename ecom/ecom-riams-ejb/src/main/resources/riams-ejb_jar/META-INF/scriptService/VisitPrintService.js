var map = new java.util.HashMap() ;
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
	recordMultiText("protocol", protocol) ;
	map.put("id", service.getId()) ;
	map.put("service",service) ;
	map.put("pat", visit.patient) ;
	var zac="ЗАКЛЮЧЕНИЕ";
	map.put("zac",zac);
	map.put("infoParent", service.parent!=null?service.parent.info:"") ;
	
}
function printVisit(aCtx, aParams) {
	//var map = new java.util.HashMap() ;
	var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, new java.lang.Long(aParams.get("id"))) ;
	var list = aCtx.manager.createQuery("from Protocol where medCase_id=:visit")
		.setParameter("visit",visit.id).getResultList();
	var protocol = !list.isEmpty()?list.iterator().next().record:"";
	recordMultiText("protocol", protocol) ;
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
			diagMkb1 = diagMkb1 + " " + diag.idc10Text;
			diagAcuity = diagAcuity + " " + ( diag.acuity!=null?diag.acuity.name:"");
			diagPrimary = diagPrimary + " " + ( diag.primary!=null?diag.primary.name:"");
			if (diag.traumaType!=null) {diagTravm = diagTravm + " " + diag.traumaType.name}
			if (diag.idc10Reason!=null) {diagTravmMkb = diagTravmMkb + " " + diag.idc10Reason.code + " " + diag.idc10Reason.name}
			//diagTravmMkb = diagTravmMkb + " " + diag.idc10Reason.getCode() + " " + diag.idc10Reason.getName();
		}
		if (diag.priority!=null && diag.priority.id == 2) {
		if (!diagText2.equals("")) diagText2 = diagText2 +"; ";
			diagText2 = diagText2 +  diag.name ;
			diagMkb2 = diagMkb2 + " " + diag.idc10Text;
		
		}
		if (diag.priority!=null && diag.priority.id == 3) {
			if (!diagText3.equals("")) diagText3 = diagText3 +"; ";
			diagText3 = diagText3 +  diag.name ;
			diagMkb3 = diagMkb3 + " " + diag.idc10Text;
		
		}
		if (diag.priority!=null && diag.priority.id == 4) {
			if (!diagText4.equals("")) diagText4 = diagText4 +"; ";
			diagText4 = diagText4 +  diag.name ;
			diagMkb4 = diagMkb4 + " " + diag.idc10Text;
		
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

function recordMultiText(aKey, aValue) {
	var ret = new java.lang.StringBuilder () ;
	var val = aValue!=null?"" +aValue:"" ;
	var n = /\n/ ;
	//val=val.replace("&", "&amp;") ;
	//val=val.replace("<", "&lt;");
	//val=val.replace(">", "&gt;");
	
	var items = val.split(n);
	var list = new java.util.ArrayList() ;
	//ret.append("</text:p>") ;
	for (var i = 0; i < items.length; i++) {
		//ret.append("<text:p text:style-name=\"P6\">") ;
		//ret.append("<text:tab/>") ;
		var prot = Packages.ru.ecom.poly.ejb.form.protocol.ProtocolForm() ;
		prot.setRecord(items[i]);
		list.add(prot);
		//ret.append("</text:p>") ;
	}
	//ret.append("<text:p>") ;
	map.put(aKey,list) ;
}
