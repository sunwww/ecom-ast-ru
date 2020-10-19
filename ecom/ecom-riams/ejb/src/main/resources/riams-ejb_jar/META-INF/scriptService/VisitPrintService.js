var map = new java.util.HashMap() ;
// Печать визитов
function printVisits(aCtx, aParams) {
	var ids1 = aParams.get("id") ;

	var ids = ids1.split(",") ;
	var ret = new java.lang.StringBuilder () ;
	
	
	var ret = new java.util.ArrayList() ;
	var current = new java.util.Date() ;
	map.put("ids",ids.length) ;
	
	
	for (var i=ids.length-1; i>=0; i--) {
		var id1=ids[i] ;
		if (id1.indexOf("!")!=-1) {
		
		
		var indlast = id1.lastIndexOf("!") ;
		var protId = id1.substring(indlast+1) ;
		var id1=id1.substring(0,indlast) ;
		indlast = id1.lastIndexOf("!") ;
		var visId = id1.substring(indlast+1) ;
		var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
				, new java.lang.Long(visId))
				if (medCase!=null) {
					//medcase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase
					//		, new java.lang.Long(visId)) ;
				
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
		var diagnosis = aCtx.manager.createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList() ;
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
		
	}}}
	
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
	map.put("directLpu",doc.directLpu);
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
	var pers = wf!=null && wf.worker!=null ? wf.worker.person : null;
	var spec = "_____________________" ;
	if (pers!=null) {
		spec=pers.lastname+" "+pers.firstname+" "+pers.middlename
	} 
	map.put("specCODE",wf!=null && wf.code!=null?wf.code:"_____________") ;
	map.put("doc.workFunctionInfo",(wf!=null && wf.workFunction!=null ? wf.workFunction.name : "_____________")+" " + spec) ;
	map.put("doc.date",medCase!=null && medCase.dateStart!=null?FORMAT_2.format(medCase.dateStart):"") ;
	map.put("doc.plandate",doc.dateFrom!=null?FORMAT_2.format(doc.dateFrom):"") ;
	map.put("doc.mkbCode",doc.idc10!=null?doc.idc10.code:"") ;
	map.put("department",doc.department!=null?doc.department.name:"") ;
	map.put("document",doc) ;
	return map ;
}
function parseInt(aNumeric) {
	if (+aNumeric>0){} else{ return "0 руб 00 коп" ;}
	var value = new java.math.BigDecimal(aNumeric) ;
	var kop =(+aNumeric % 1).toFixed(2).slice(2) ;
	var rub = value.intValue();
	return ""+rub+" руб. "+kop+" коп." ;
}
function parseSymRub(aNumeric) {
	if (+aNumeric>0){} else{ return "ноль руб 00 коп" ;}
	var value = new java.math.BigDecimal(aNumeric) ;
	var kop =(+aNumeric % 1).toFixed(2).slice(2) ;
	return Packages.ru.ecom.ejb.services.util.ConvertSql.toWords(value)+" руб. "+ kop +" коп." ;
}

function refenceSMO(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	referenceSMOmap(aCtx,aParams,map);
	return map ;
}
function referenceSMOmap(aCtx,aParams,aMap) {
	aMap.put("dateFrom",aParams.get("dateFrom")!=null?aParams.get("dateFrom"):"") ;
	aMap.put("dateTo",aParams.get("dateTo")!=null?aParams.get("dateTo"):"") ;
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var FORMAT_Y = new java.text.SimpleDateFormat("yyyy") ;
	aMap.put("CURRENT_DATE",FORMAT_2.format(currentDate)) ;
	aMap.put("CURRENT_WORKFUNCTION","") ;
	aMap.put("addressLpu",aParams.get("address_lpu")!=null?aParams.get("address_lpu"):"") ;
	aMap.put("nameLpu",aParams.get("name_lpu")!=null?aParams.get("name_lpu"):"") ;
	var renders = new java.util.ArrayList() ;
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
			, new java.lang.Long(aParams.get("patient"))) ;
	aMap.put("pat",patient);
	var kinsmanPar=aParams.get("kinsman")
	var kinsman = kinsmanPar!=null?aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient
			, new java.lang.Long(aParams.get("kinsman"))):null ;
	aMap.put("kinsman",kinsman);
	//throw ""+aParams.get("render") ;
	
	var refNumber =Packages.ru.ecom.ejb.sequence.service.SequenceHelper.getInstance().startUseNextValueNoCheck("Reference#Cost#"+FORMAT_Y.format(currentDate), aCtx.manager);
	//throw "---"+refNumber ;
	aMap.put("refNumber",refNumber) ;
	var sum=0 ;
	var ren = (""+aParams.get("render")).split("##") ;
	for (var i=0;i<ren.length;i++) {
		var r = ren[i].split("#");
		//throw r[0]+"--------"+r[1] ;
		var parDep = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var sr = +r[0] ;
		sum=sum+sr ;
		parDep.set1(parseInt(r[0])) ;
		parDep.set2(parseSymRub(r[0])) ;
		parDep.set3(r[1]) ;
		renders.add(parDep) ;
	}
	aMap.put("policy",aParams.get("polNumber"));
	aMap.put("card",aParams.get("card"));
	aMap.put("renders",renders) ;
	//throw parseInt(sum) ;
	aMap.put("cost_short",parseInt(sum)) ;
	aMap.put("cost_full",parseSymRub(sum)) ;
	//return map ;
}
function printRequitDirection (aCtx,aParams) {
	var historyNumber = '';
	var doc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.licence.RequitDirectionDocument
			, new java.lang.Long(aParams.get("id"))) ;
	
	var list = aCtx.manager.createNativeQuery("select mc.number from MedCard mc where mc.person_id='"+doc.medCase.patient.id+"'")
	.getResultList();
	if (list.size()>0) {
		historyNumber=""+list.get(0);
	}
	var map = printDocument (aCtx, aParams) ;
	map.put('abuse', recordMultiText(doc.abuses));
	map.put('history', recordMultiText(doc.history));
	map.put('research', recordMultiText(doc.research));
	map.put('labresearch', recordMultiText(doc.labResearch));
	map.put('dateFrom',doc.planDateFrom);
	map.put('dateTo', doc.planDateTo);
	map.put('historyNumber', historyNumber);
	map.put('orderDate', doc.orderDate);
	map.put('orderOffice', doc.orderOffice);
	map.put('orderNum', doc.orderNumber);
	
	return map;
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
	map.put("healthGroup",doc.healthGroup!=null ? doc.healthGroup.name : "");
	var currentDate = new Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	map.put("history", recordMultiText(doc.history)) ;
	map.put("servicies", recordMultiText(doc.servicies)) ;
	map.put("diagnosis", recordMultiText(doc.diagnosis)) ;
	if (aParams.get("render")!=null&&aParams.get("render")!='') {
		var l = new java.util.ArrayList() ;
		l.add(new Packages.ru.ecom.ejb.services.query.WebQueryResult() );
		map.put("renders1",l) ;
		referenceSMOmap(aCtx,aParams,map) ;
	} else {
		map.put("renders1",new java.util.ArrayList()) ;
	}
	var wf = medCase.workFunctionExecute ;
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
	
//	if (aParams.get("typePrint")!=null&&aParams.get("typePrint").equals("all")) {
		if (!list.isEmpty()) {
			var prots = "";
			for (var i =0;i<list.size();i++) {
				prots +=list.get(i).record+"\n";
			}			
			map.put("protocol",recordMultiText(prots));
		} else {
			map.put("protocol","");
		}

/*	} else {
		var protocol = !list.isEmpty()?list.iterator().next().record:"";
		map.put("protocol", recordMultiText(protocol)) ;
	}*/
	
	map.put("id", +visit.getId()) ;
	map.put("visit", visit) ;
	map.put("ticket", visit) ;
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
	var diagnosis = aCtx.manager.createQuery("from Diagnosis where medCase=:med").setParameter("med", visit).getResultList() ;
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
	var policy_list_sql = "select mp.series, mp.polnumber, mp.commonnumber, ri.name from medpolicy mp " +
			" left join reg_ic ri on ri.id=mp.company_id where mp.patient_id='"+visit.patient.id+"' and mp.actualdateto is null" +
			" order by actualdatefrom desc";
	var policySeries = "",policyNumber="", policyRZ="",policyCompany="";
	var policy_list = aCtx.manager.createNativeQuery(policy_list_sql).setMaxResults(1).getResultList();
	if (policy_list.size()>0) {
		var obj = policy_list.get(0);
		policySeries=obj[0];
		policyNumber=obj[1];
		policyRZ=obj[2];
		policyCompany=obj[3];
	}
	
	
	map.put("policySeries",policySeries);
	map.put("policyNumber",policyNumber);
	map.put("policyRZ",policyRZ);
	map.put("policyCompany",policyCompany);
	
	var otherVisits = [];
	var otherVisits_list_sql = "select to_char(m2.datestart,'dd.MM.yyyy') from medcase m" +
			" left join medcase m1 on m1.id=m.parent_id" +
			" left join medcase m2 on m2.parent_id=m1.id" +
			" where m.id='"+visit.getId()+"' and (m2.dtype='Visit' or m2.dtype='ShortMedCase') order by m2.datestart desc";
	var otherVisit_list = aCtx.manager.createNativeQuery(otherVisits_list_sql).getResultList();
	for (var i=0;i<14;i++) {
		otherVisits[i]="";
	}
	if (otherVisit_list.size()>0) {
		for (var i=0;i<otherVisit_list.size();i++) {
			otherVisits[i]=""+otherVisit_list.get(i);
		}
	}
	for (var i=0;i<14;i++) {
		map.put("otherVisit"+i,otherVisits[i]);
	}
	
	var invalidity_list_sql = "select vi.name" +
			" ,case when (i.initial='1') then 'ВПЕРВЫЕ' else 'ПОВТОРНО' end as c1" +
			" ,case when (i.childhoodinvalid='1') then 'ДА' else 'НЕТ' end as c2" +
			" from invalidity i " +
			" left join vocinvalidity vi on vi.id=i.group_id " +
			"where i.patient_id='"+visit.patient.getId()+"' order by i.id desc";
	var inv_primary="", inv_group="",inv_childhood="";
	var invalidity_list = aCtx.manager.createNativeQuery(invalidity_list_sql).getResultList();
	if (invalidity_list.size()>0) {
		var i_obj = invalidity_list.get(0);
		inv_group=i_obj[0];
		inv_primary=i_obj[1];
		inv_childhood=i_obj[2];
	}
	map.put("invalid_primary",inv_primary);
	map.put("invalid_group",inv_group);
	map.put("invalid_childhood",inv_childhood);
	
	var medservice_list_sql = "select mc.id, mss.name, mss.code from medcase mc left join medservice mss on mss.id=mc.medservice_id " +
	"where mc.dtype='ServiceMedCase' and mc.parent_id='"+visit.id+"'";
	var ms_list = aCtx.manager.createNativeQuery(medservice_list_sql).getResultList();
	var medServiceName=["",""];
	var medServiceCode=["",""];
	if (ms_list.size()>0) {
		for (var i=0;i<ms_list.size();i++) {
			if (i==2) break;
			var obj = ms_list.get(i);
			medServiceName[i]=obj[1];
			medServiceCode[i]=obj[2];
		}    	
	} 
	map.put("medService0Name",medServiceName[0]);
	map.put("medService0Code",medServiceCode[0]);
	map.put("medService1Name",medServiceName[1]);
	map.put("medService1Code",medServiceCode[1]);
	/*var vacText="";
	var vaccination=visit.getVaccinations();
	for(var i=0;i<vaccination.size();i++){
		var vac = vaccination.get(i);
		vacText = (i+1) + ". " + vacText + vac.name ;
	}
	
	map.put("vac",vacText);*/
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
	
	/*
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
		*/
		
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
