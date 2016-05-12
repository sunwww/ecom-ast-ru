var mapp = new java.util.HashMap() ;
function print(aCtx, aParams) {
	mapp.put("list",journalRegisterVisit(aCtx,aParams)) ;
	return mapp ;
}
function doNotAddTalk(aContext, aTicketId) {
	var ticket = aContext.manager.find(Packages.ru.ecom.poly.ejb.domain.Ticket
		, java.lang.Long.valueOf(aTicketId)) ;
	//var profs = aContext.manager.createQuery("from VocReason where code='PROFYLACTIC'").getResultList() ;
	//var prof = profs.size() ? profs.get(0) : null ;
	ticket.talk = java.lang.Boolean.FALSE ;
	aContext.manager.persist(ticket);
	return aTicketId;
	
}

function addTalk(aContext, aTicketId) {
	var ticket = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase
		, java.lang.Long.valueOf(aTicketId)) ;
	var profs = aContext.manager.createQuery("from VocReason where code='PROFYLACTIC'").getResultList() ;
	var prof = profs.size() ? profs.get(0) : null ;
	var ticketCopy = new Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase() ;
	// Талон беседы с родственником
	ticketCopy.medcard = ticket.medcard ;
	ticketCopy.patient = ticket.patient ;
	//ticketCopy.illnesPrimary = ticket.illnesPrimary ;
	//ticketCopy.vocIllnesType = ticket.vocIllnesType ;
	ticketCopy.serviceStream = ticket.serviceStream ;
	ticketCopy.visitReason = prof ;
	ticketCopy.workPlaceType = ticket.workPlaceType ;
	ticketCopy.visitResult = ticket.visitResult ;
	ticketCopy.workFunctionExecute = ticket.workFunctionExecute ;
	//ticketCopy.idc10 = ticket.idc10 ;
	//ticketCopy.primary = ticket.primary ;
	ticketCopy.hospitalization = ticket.hospitalization;
	ticketCopy.dateStart = ticket.dateStart ;
	ticketCopy.timeExecute = ticket.timeExecute ;
	ticketCopy.isTalk = java.lang.Boolean.TRUE ;
	//ticketCopy.status = Packages.ru.ecom.poly.ejb.domain.Ticket.STATUS_CLOSED
	aContext.manager.persist(ticketCopy);
	// Диагноз
	var listDiag = aContext.manager.createQuery("from Diagnosis where medCase_id=:medcase and priority.code=:priority")
		.setParameter("medcase",ticket.id)
		.setParameter("priority","1")
		.getResultList() ;
	if (!listDiag.isEmpty()) {
		var diag=listDiag.get(0) ;
		var diagCopy = new Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis() ;
		diagCopy.idc10 = diag.idc10;
		diagCopy.illnesPrimary = diag.illnesPrimary;
		diagCopy.name =diag.name ;
		diagCopy.patient = diag.patient ;
		
		diagCopy.medCase=ticketCopy ;
		diagCopy.priority=diag.priority ;
		aContext.manager.persist(diagCopy);
	}
	// Создание и закрытие СПО
	var spo = new Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase() ;
	
	var workFunction = ticket.getWorkFunctionExecute() ; 
	spo.setOwnerFunction(workFunction) ;
	spo.setDateStart(ticket.getDateStart()) ;
	spo.setLpu(workFunction.worker.getLpu()) ;
	spo.setPatient(ticket.getPatient()) ;
	spo.setStartFunction(workFunction) ;
	spo.setServiceStream(ticket.getServiceStream()) ;
	spo.setNoActuality(false) ;
	aContext.manager.persist(spo) ;
	ticketCopy.setParent(spo) ;
	
	aContext.manager.persist(ticketCopy) ;
	try {
		aCt.serviceInvoke("SmoVisitService", "closeSpo",spo.id) ;
	} catch(e) {
		
	}


	return ticketCopy.getId();
	
}

function f039(aCtx,aParams) {
	
	var obj = aParams.get('id').split(":") ;
	var beginDate = obj[0] ;
	var finishDate = obj[1] ;
	var ticketIs = obj[8] ;
	var specialist=null,workFunction=null, lpu=null, serviceStream=null,workPlaceType=null,groupBy="1" ;
	if (obj.length>=3&&+obj[2]>0) groupBy = obj[2] ;
	if (obj.length>=4&&+obj[3]>0) specialist = java.lang.Long.valueOf(obj[3]) ;
	if (obj.length>=5&&+obj[4]>0) workFunction = java.lang.Long.valueOf(obj[4]) ;
	if (obj.length>=6&&+obj[5]>0) lpu = java.lang.Long.valueOf(obj[5]) ;
	if (obj.length>=7&&+obj[6]>0) serviceStream = java.lang.Long.valueOf(obj[6]) ;
	if (obj.length>=8&&+obj[7]>0) workPlaceType = java.lang.Long.valueOf(obj[7]) ;
	if (obj.length>=9) ticketIs = (+(obj[8])>0)? true: false;
	//throw ticketIs?"yes"+obj[7]:"no" ;
	var service = new Packages.ru.ecom.mis.ejb.service.medcase.ReportsServiceBean() ;
	service.setContext(aCtx.sessionContext) ;
	service.setManager(aCtx.manager) ;
	var queryTextBegin = service.getTextQueryBegin(ticketIs, groupBy
		, beginDate, finishDate, specialist, workFunction, lpu, serviceStream,workPlaceType) ;
	var queryTextEnd = service.getTextQueryEnd(ticketIs, groupBy
		, beginDate, finishDate, specialist, workFunction, lpu, serviceStream,workPlaceType) ;
	var vd = ticketIs?"":"Start" ;
	var sql= queryTextBegin
		+" ,count(*) as cntAll"
		+" ,count(case when (ad1.domen=5 or ad2.domen=5) then 1 else null end) as cntVil"
		+" ,count(case when (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntAll17"
		+" ,count(case when (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-60),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntAll60"
		+" ,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllness"
		+" ,count(case when vr.code='ILLNESS' and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnes17"
		+" ,count(case when vr.code='ILLNESS' and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-60),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntIllnes60"
		+" ,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProfAll"
		+" ,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll"
		+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHomeAll"
		+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnesHome17"
		+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-2),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnesHome01"
		+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-60),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntIllnesHome60"
		+" ,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntProfHome17"
		+" ,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-2),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntProfHome01"
		+" ,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC"
		+" ,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget"
		+" ,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged"
		+" ,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns"
		+" ,count(case when vr.code='CONSULTATION' then 1 else null end) as cntConsultationAll"
		+queryTextEnd;
	
	
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	
	var ret = new java.util.ArrayList() ;
	
	for (var i=0;i<list.size();i++) {
		var wq = Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		var obj = list.get(i) ;
		for (var j=1;j<22;j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		ret.add(wq) ;
	}
	var map = new java.util.HashMap() ;
	map.put("listF039",ret) ;
	map.put("column.name",service.getTitle(groupBy)) ;
	return map ;
}

function f039add(aCtx,aParams) {
	
	var obj = aParams.get('id').split(":") ;
	var beginDate = obj[0] ;
	var finishDate = obj[1] ;
	var specialist=null,workFunction=null, lpu=null, serviceStream=null,groupBy="1" ;
	var ticketIs = true ;
	var specialist=null,workFunction=null, lpu=null, serviceStream=null,workPlaceType=null,groupBy="1" ;
	if (obj.length>=3&&+obj[2]>0) groupBy = obj[2] ;
	if (obj.length>=4&&+obj[3]>0) specialist = java.lang.Long.valueOf(obj[3]) ;
	if (obj.length>=5&&+obj[4]>0) workFunction = java.lang.Long.valueOf(obj[4]) ;
	if (obj.length>=6&&+obj[5]>0) lpu = java.lang.Long.valueOf(obj[5]) ;
	if (obj.length>=7&&+obj[6]>0) serviceStream = java.lang.Long.valueOf(obj[6]) ;
	if (obj.length>=8&&+obj[7]>0) workPlaceType = java.lang.Long.valueOf(obj[7]) ;
	if (obj.length>=9) ticketIs = (+(obj[8])>0)? true: false;
	
	var service = new Packages.ru.ecom.mis.ejb.service.medcase.ReportsServiceBean() ;
	var queryTextBegin = service.getTextQueryBegin(ticketIs, groupBy
		, beginDate, finishDate, specialist, workFunction, lpu, serviceStream,workPlaceType) ;
	//var queryTextEnd = service.getTextQueryEnd(ticketIs, groupBy
	//	, beginDate, finishDate, specialist, workFunction, lpu, serviceStream,workPlaceType) ;
	
	var vd = ticketIs?"":"Start" ;
	var sql= queryTextBegin
	+" ,count(*) as cntAll"
	+" ,count(case when (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntAll14"
	
	+" ,count(case when (ad1.domen=5 or ad2.domen=5) and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntAll14V"
	
	+" ,count(case when (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')>=p.birthday) " 
	+"          and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday)	then 1 else null end) as cntAll17"
	
	+" ,count(case when (ad1.domen=5 or ad2.domen=5) and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')>=p.birthday) " 
	+"          and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday)	then 1 else null end) as cntAll17V"
	
	+" ,count(case when (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntAllold"
	
	+" ,count(case when (ad1.domen=5 or ad2.domen=5) and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntAlloldV"
	
	+" ,count(case when vr.code='ILLNESS' then 1 else null end) as cntIllness"
	
	+" ,count(case when vr.code='ILLNESS' and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnes17"
	
	+" ,count(case when vr.code='ILLNESS' and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')>=p.birthday) " 
	+"          and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnes17"
	
	+" ,count(case when vr.code='ILLNESS' and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntIllnesold"
	
	+" ,count(case when vr.code='PROFYLACTIC' then 1 else null end) as cntProf"
	
	+" ,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHome"
	
	+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHome"
	
	+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnesHome14"
	
	+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')>=p.birthday) " 
	+"          and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntIllnesHome17"
	
	+" ,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntIllnesHomeold"
	
	+" ,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntProfHome"
	
	+" ,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntProfHome14"
	
	+" ,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-15),'dd.mm.yyyy')>=p.birthday) " 
	+"          and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')<p.birthday) then 1 else null end) as cntProfHome17"
	
	+" ,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (to_date(case when to_char(t.date"+vd+",'dd.mm')='29.02' then '28.02' else to_char(t.date"+vd+",'dd.mm') end ||'.'||(cast(to_char(t.date"+vd+",'yyyy') as int)-18),'dd.mm.yyyy')>=p.birthday) then 1 else null end) as cntProfHomeold"
	
	//	+queryTextEnd;
	;
	
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	
	var ret = new java.util.ArrayList() ;
	
	for (var i=0;i<list.size();i++) {
		var wq = Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		var obj = list.get(i) ;
		for (var j=1;j<23;j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		ret.add(wq) ;
	}
	var map = new java.util.HashMap() ;
	map.put("listF039",ret) ;
	map.put("column.name",service.getTitle(groupBy)) ;
	return map ;
}


function journalRegisterVisitByMap(aCtx,aParams) {
	return journalRegisterVisit(aCtx,aParams,0) ;
}
function journalRegisterVisitByFrm(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	map.put("listVisits",journalRegisterVisit(aCtx,aParams.get('id'),1))
	return  map;
}
function journalRegisterVisit(aCtx,aParams,frm) {
	var ret = new java.util.ArrayList() ;
	var obj = aParams.split(":") ;
	var startDate = obj[0] ;
	var finishDate = obj[1] ;
	var spec = obj[2] ;
	var rayon = obj[3] ;
	var primary = obj[4] ;
	var sn = +obj[5] ;
	var func = obj[7] ;
	var order = obj[6]!=null?obj[6]:"t.date,t.recordtime" ;
	var frm= +frm ;
	
	
	var sql = "where t.date between to_date('"+startDate+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') " ;
	
	if (func!=null && (+func>0)) {
		sql = sql + " and wf.workFunction_id='"+func+"'"
	}
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunction_id='"+spec+"'"
	}
		
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = "select t.id ,to_char(t.date,'dd.MM.yyyy') ||' '||cast(t.recordtime as varchar(5))"
	+",coalesce(vwf.name,'')||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as spec"
	+",p.lastname||' '||p.firstname||' '||p.middlename as fio"
	+",vs.name as vsname,to_char(p.birthday,'dd.mm.yyyy')"
	+", case when p.address_addressId is not null "
	+"          then coalesce(a.fullname,a.name) || "
	+"               case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end" 
	+		" ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end" 
	+		"||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end"
	+"       when p.territoryRegistrationNonresident_id is not null"
	+"          then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident"
	+"               ||' '||ost.name||' '||p.StreetNonresident||"
	//+"               coalesce(' д.'||p.HouseNonresident,'') ||coalesce(' корп.'|| p.BuildingHousesNonresident,'') ||coalesce(' кв. '|| p.ApartmentNonresident,'')"
	+"               case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end" 
	+" ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end" 
	+"||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end"
	+"   else p.foreignRegistrationAddress "
	+"  end as address"
	//+",$$ByPatient^ZAddressLib(p.id)"
	+",vr.name as vrname"
	+",m.number"
	+",(select list(mp.series||' '||mp.polNumber||' '||ri.name||' ('||to_char(mp.actualDateFrom,'dd.MM.yyyy')||'-'||to_char(mp.actualDateTo,'dd.MM.yyyy')||')') from medpolicy mp left join reg_ic ri on ri.id=mp.company_id where mp.patient_id=p.id "
	+" and t.date between mp.actualDateFrom and COALESCE(mp.actualDateTo,CURRENT_DATE))"
	+",mkb.code as mkbcode,vh.code as vhcode,t.directHospital,vss.name as vssname"
	+" from Ticket t "
	+" left join medcard m on m.id=t.medcard_id " 
	+" left join patient p on p.id=m.person_id " 
	+" left join vocrayon vr on vr.id=p.rayon_id "
	+" left join workfunction wf on wf.id=t.workfunction_id "
	+" left join worker w on w.id=wf.worker_id"
	+" left join vocworkfunction vwf on vwf.id=wf.workfunction_id"
	+" left join patient wp on wp.id=w.person_id"
	+" left join vocsex vs on vs.id=p.sex_id"
	+" left join vocidc10 mkb on mkb.id=t.idc10_id"
	+" left join vocServiceStream vss on vss.id=t.vocPaymentType_id"
	+" left join Address2 a on a.addressId=p.address_addressId"
	+" left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id"
	+" left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id"
	+" left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id"
	
	+" left join vochospitalization vh on vh.id=t.hospitalization_id "+sql +" and t.status=2"
	+" order by "+order;
	
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	//var ids = "" ;
	if (sn<1) sn=1 ;
	//throw list.size() ;
	for (var i=0 ; i< list.size() ; i++) {
		if (frm>0) {
			ret.add(ticketFrm(list.get(i),sn)) ;
		} else {
			ret.add(ticketMap(list.get(i),sn)) ;
		}
		sn=sn+1 ;
	}
		
	
	return ret ;
}
function journalRegisterGroupByDirectHospital(aCtx,aParams) {
	var ret = new java.util.ArrayList() ;
	var obj = aParams.split(":") ;
	var FORMAT_1 = new java.text.SimpleDateFormat("yyyy-MM-dd") ;
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("hh:mm") ;
	var startDate = FORMAT_1.format( FORMAT_2.parse(obj[0])) ;
	var finishDate = FORMAT_1.format( FORMAT_2.parse(obj[1])) ;
	var spec = obj[2] ;
	var rayon = obj[3] ;
	var primary = obj[4] ;
	var sn = +obj[5] ;
	var func = obj[7] ;
	var order = obj[6]!=null?obj[6]:"date,time" ;
	
	
	
	var sql = "where t.date between '"+startDate+"' and '"+finishDate+"'" ;
	
	if (func!=null && (+func>0)) {
		sql = " left join workfunction wf on wf.id=t.workfunction_id "+sql + " and wf.workFunction_id='"+func+"'"
	}
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunction_id='"+spec+"'"
	}
		
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = " left join medcard m on m.id=t.medcard_id left join patient p on p.id = m.person_id "+sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = "select t.directHospital,count(*)"
	+" from Ticket t "
	+sql +" and t.status=2"
	+" group by t.directHospital";
	
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (+list.size()>0) {
		for (var i=0 ; i< list.size() ; i++) {
			var obj = list.get(i) ;
			var map = new java.util.HashMap() ;
			//var pat = aTic.medcard!=null?aTic.medcard.person:"" ;
			map.put("directHospital", obj[0]) ;
			map.put("count",obj[1]) ;
			ret.add(map) ;
			
		}
		
	}
	return ret ;
}
function ticketMap(aTic,aSn) {
	var map1 = new java.util.HashMap() ;
	//var pat = aTic.medcard!=null?aTic.medcard.person:"" ;
	map1.put("id", aTic[0]) ;
	map1.put("sn",""+aSn) ;
	map1.put("date",aTic[1]) ;
	map1.put("spec",aTic[2]) ;
	map1.put("fio",aTic[3]) ;
	map1.put("sex",aTic[4]) ;
	map1.put("birthday",aTic[5]);
	map1.put("address",aTic[6]) ;
	map1.put("rayon",aTic[7]) ;
	map1.put("medcard",aTic[8]) ;
	map1.put("policy",aTic[9]) ;	
	map1.put("mkb",aTic[10]) ;
	map1.put("primary",aTic[11]) ;
	map1.put("directHospital",+aTic[12]>0?"Да":"") ;

	return map1 ;
}
function ticketFrm(aTic,aSn) {
	var frm = new Packages.ru.ecom.poly.ejb.form.TicketForm() ;
	//var pat = aTic.medcard!=null?aTic.medcard.person:"" ;
	//
	frm.setSn(aSn) ;
	frm.setId(java.lang.Long.valueOf(aTic[0])) ;
	frm.setDate(aTic[1]) ;//date + time execute
	frm.setWorkFunctionInfo(aTic[2]) ;//spec
	frm.setPatientName(aTic[3]) ; //fio
	frm.setStatusName(aTic[4]) ; //sex
	frm.setDateCreate(aTic[5]); //birthday
	frm.setTicketInfo(aTic[6]) ; //address
	frm.setTimeCreate(aTic[7]!=null?aTic[7]:"") ; //rayon
	frm.setUet(aTic[8]) ; //medcard
	frm.setTime(aTic[9]!=null?aTic[9]:"") ;	 //policy
	frm.setConcomitantDiseases(aTic[10]) ; //mkb
	frm.setPrevIdc10Date(aTic[11]) ; //illnesPrimary
	frm.setDirectHospital(+aTic[12]>0?true:false) ;//directHospital
	frm.setMedServices(aTic[13]); // serviceStream
	return frm ;
}