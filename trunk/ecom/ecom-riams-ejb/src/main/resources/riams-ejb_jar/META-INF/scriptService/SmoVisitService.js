function printDirectionByTime(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	var currentDate = new java.util.Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	//var FORMAT_time = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentTime",new java.sql.Time (currentDate.getTime())) ;
	var wctId = aParams.get("wct") ;
	var sql="" ;
	sql=sql+"select wct.id as wctid,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate, cast(wct.timeFrom as varchar(5)) as wcttimeFrom, vwf.name as vwfname, wp.lastname ||' '||wp.firstname||' '||wp.middlename as wpmiddlename " ;
	sql=sql+" , coalesce(p.lastname ||' '||substring(p.firstname,1,1)||' '||substring(p.middlename,1,1),p1.lastname ||' '||substring(p1.firstname,1,1)||' '||substring(p1.middlename,1,1)) as fio ";
	sql=sql+" , coalesce(p.patientSync,p1.patientSync) as sync, case when wct.medCase_id is null then '(Пред. запись)' else '' end as pred,wct.prePatientInfo ";
	sql=sql+" , case when m.dateStart is null then '' else '+' end as priem";
	sql=sql+", 'Каб.: '||list(wpl.name)||'.' as wpname" ;
	sql=sql+" from WorkCalendarTime wct" ; 
	sql=sql+" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id " 
			+" left join Medcase m on m.id=wct.medCase_id "
			+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id " 
			+" left join WorkFunction wf on wf.id=wc.workFunction_id " 
			+" left join WorkPlace_WorkFunction wpf on wpf.workFunctions_id=wf.id" 
			+" left join WorkPlace wpl on wpl.id=wpf.workPlace_id and wpl.dtype='ConsultingRoom'" 
			+" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id " 
			+" left join Worker w on w.id=wf.worker_id " 
			+" left join patient wp on wp.id=w.person_id " 
			+" left join Patient p on p.id=wct.prePatient_id " 
			+" left join Patient p1 on p1.id=m.patient_id " 
			+" left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE " ;
	sql=sql+" and wct.id = '"+wctId+"' " ;
	sql=sql+" group by wct.id,wct.prePatient_id,m.dateStart, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,wct.medcase_id,p1.patientSync,p1.lastname,p1.firstname,p1.middlename" ;
	sql=sql+" order by wcd.calendarDate,wct.timeFrom" ;
	//throw sql ;
	var ret = new java.util.ArrayList() ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put("pat.fio",obj[5]!=null?obj[5]:obj[8]) ;
		map.put("pat.sync",obj[6]) ;
	}
	for (var ind=0 ; ind< list.size() ; ind++) {
		var obj = list.get(ind) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.setSn(ind+1) ;
		for (var i=1;i<=10;i++) {
			//var i1=i-2 ;
			eval("par.set"+i+"(obj["+i+"]!=null?obj["+i+']:"");') ;
			
		}
		ret.add(par) ;
	}
	map.put("listTime",ret) ;
	return map ;
}
function printDirectionByPatient(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	var patientId = aParams.get("patientId") ;
	var currentDate = new java.util.Date() ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentDate",FORMAT_2.format(currentDate)) ;
	//var FORMAT_time = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	map.put("currentTime",new java.sql.Time (currentDate.getTime())) ;	
	/*
	var lastname = aParams.get("lastname") ;
	var firstname = aParams.get("firstname") ;
	var middlename = aParams.get("middlename") ;
	var birthday = aParams.get("birthday") ;
	var policySeries = aParams.get("policySeries");
	var policyNumber = aParams.get("policyNumber") ;
	var rayon = aParams.get("rayon") ;
	var rayonName = aParams.get("rayonName") ; 
	var sql="" ;
	pretInfo = "" ;
	sql.append("select wct.id as wctid,wct.prePatient_id, to_char(wcd.calendarDate,'dd.mm.yyyy'), cast(wct.timeFrom as varchar(5)), vwf.name, wp.lastname as wplastname,wp.firstname as wpfirstname,wp.middlename as wpmiddlename ") ;
	sql.append(" ,coalesce(");
	//sql.append("pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(p.birthday,'dd.mm.yyyy')||coalesce(' ('||pat.patientSync||')','')") ;
	sql.append(" p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') ||coalesce(' ('||p.patientSync||')','')") ;
	sql.append(",wct.prepatientInfo) as fio") ;
	sql.append(" from WorkCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join Patient p on p.id=wct.prePatient_id left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE and (p.lastname like '").append(aLastname.toUpperCase()).append("%' ") ;
	preInfo.append(aLastname).append(" ") ;
	if (firstname!=null && firstname!=null) {
		sql=sql+" and p.firstname = '"+firstname.toUpperCase()+"' " ;
		preInfo=preInfo+firstname+" " ;
	} else {
		preInfo=preInfo+" " ;
	}
	if (middlename!=null && middlename!="") {
		sql=sql+" and p.middlename = '"+middlename.toUpperCase()+"' " ;
		preInfo=preInfo+middlename+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (birthday!=null && birthday!="") {
		sql=sql+" and p.birthday=to_date('"+birthday+"','dd.mm.yyyy') " ;
		preInfo=preInfo+birthday+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (policySeries!=null && policySeries!="") {
		sql=sql+" and mp.series='"+policySeries.toUpperCase()+"' " ;
		preInfo=preInfo+policySeries.toUpperCase()+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (policyNumber!=null && !policyNumber.equals("")) {
		sql=sql+" and mp.polNumber='"+policyNumber.toUpperCase()+"' " ;
		preInfo=preInfo+policyNumber.toUpperCase()+" ";
	} else {
		preInfo=preInfo+" " ;
	}
	if (rayon!=null && rayon>Long.valueOf(0)) {
		sql=sql+" and p.rayon_id='"+rayon+"' " ;
		preInfo=preInfo+rayonName;
	} else {
		preInfo=preInfo+"" ;
	}
	//System.out.println(preInfo) ;
	sql=sql+"or wct.prePatientInfo = '").append(preInfo).append("') " ;
	*/
	var sql="" ;
	sql=sql+"select wct.id as wctid,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate, cast(wct.timeFrom as varchar(5)) as wcttimeFrom, vwf.name as vwfname, wp.lastname ||' '||wp.firstname||' '||wp.middlename as wpmiddlename " ;
	sql=sql+" , coalesce(p.lastname ||' '||substring(p.firstname,1,1)||' '||substring(p.middlename,1,1),p1.lastname ||' '||substring(p1.firstname,1,1)||' '||substring(p1.middlename,1,1)) as fio ";
	sql=sql+" , coalesce(p.patientSync,p1.patientSync) as sync, case when wct.medCase_id is null then '(Пред. запись)' else '' end as pred ";
	sql=sql+" , case when m.dateStart is null then '' else '+' end as priem";
	sql=sql+" , 'Каб.: '||list(wpl.name)||'.' as wpname";
	sql=sql+" from WorkCalendarTime wct" ; 
	sql=sql+" left join Medcase m on m.id=wct.medCase_id";
	sql=sql+" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id";
	sql=sql+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id";
	sql=sql+" left join WorkFunction wf on wf.id=wc.workFunction_id";
	sql=sql+" left join WorkPlace_WorkFunction wpf on wpf.workFunctions_id=wf.id" ;
	sql=sql+" left join WorkPlace wpl on wpl.id=wpf.workPlace_id and wpl.dtype='ConsultingRoom'" ;
	sql=sql+" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
			" left join Worker w on w.id=wf.worker_id" +
			" left join patient wp on wp.id=w.person_id" +
			" left join Patient p on p.id=wct.prePatient_id" +
			" left join Patient p1 on p1.id=m.patient_id" +
			" left join medpolicy mp on mp.patient_id=p.id where wcd.calendarDate>=CURRENT_DATE " ;
	sql=sql+" and (wct.prePatient_id = '"+patientId+"' or m.patient_id='"+patientId+"') " ;
	sql=sql+" group by wct.id,wct.prePatient_id, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,wct.medCase_id,m.dateStart,p1.patientSync,p1.lastname,p1.firstname,p1.middlename" ;
	sql=sql+" order by wcd.calendarDate,wct.timeFrom" ;
	
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (list.size()>0) {
		var obj=list.get(0) ;
		map.put("pat.fio",obj[5]) ;
		map.put("pat.sync",obj[6]) ;
	}
	var ret = new java.util.ArrayList() ;
	for (var ind=0 ; ind< list.size() ; ind++) {
		var obj = list.get(ind) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.setSn(ind+1) ;
		for (var i=1;i<=9;i++) {
			//var i1=i-2 ;
			eval("par.set"+i+"(obj["+i+"]!=null?obj["+i+']:"");') ;
			
		}
		ret.add(par) ;
	}
	map.put("listTime",ret) ;
	return map ;
}
/**
* Не явка пациента на прием
*/
function visitNoPatient(aContext, aVisitId) {
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId)) ;
	if (visit.timeExecute==null && visit.timePlan!=null) visit.timeExecute = visit.timePlan.timeFrom ;
	if (visit.dateStart==null && visit.datePlan!=null) visit.dateStart = visit.datePlan.calendarDate ;
	var list = aContext.manager.createQuery("from VocVisitResult where omcCode='-1'").getResultList() ;
	if (list.size()>0) {
		visit.visitResult = list.get(0) ;
		visit.noActuality = true ;
	} else {
		visit.noActuality = true ;
	}
	if (visit.timePlan!=null) visit.timePlan.medCase = null ;
	var startWF= aContext.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
	.iterator().next() ;
	/*
	if(visit.startWorker==null) {
		visit.startWorker = aContext.serviceInvoke("WorkerService", "findLogginedWorker") ;
	}*/
	// FIXME определять функцию правильно
	if(visit.workFunctionExecute==null) {
		visit.workFunctionExecute = startWF ;
	}
	return visit.getId();
}
/**
 * Закрыть СПО по визиту
 */
function closeSpoByVisit(aContext, aVisitId) {
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
			, java.lang.Long.valueOf(aVisitId)) ;
	if(visit.getDateStart()==null) throw "У визита нет даты приема" ;	
	// FIXME последний ли визит в СПО?
	var spo = visit.getParent() ;
	if(spo==null) throw "Визит не присоединен к СПО" ;
	if(spo.getDateFinish()!=null) throw "СПО уже закрыто" ;
	
	// FIXME диагноз
	return closeSpo(aContext, spo.id) ;
}
/**
 * Закрыть СПО
 */
function closeSpo(aContext, aSpoId) {
	
	var listVisLast = aContext.manager.createNativeQuery("select vis.id as visid"
			+" ,mkb.id as mkbid"
			+" from MedCase vis"
			+"     left join WorkFunction wf on vis.workFunctionExecute_id = wf.id"
			+"     left join VocWorkFunction vwf on vwf.id=wf.workFunction_id"
			+"     left join Worker w on wf.worker_id = w.id"
			+"     left join Patient pat on w.person_id = pat.id"
			+" left join VocReason vr on vis.visitReason_id = vr.id"
			+" left join VocServiceStream vss on vss.id=vis.serviceStream_id"
			+" left join Diagnosis diag on diag.medcase_id=vis.id"
			+" left join VocIdc10 mkb on mkb.id=diag.idc10_id"
			+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
			+" left join VocVisitResult vvr on vvr.id=vis.visitResult_id"
			+" where vis.parent_id="+aSpoId
			+" and (vis.DTYPE='Visit' OR vis.DTYPE='ShortMedCase')"
			+" and vis.dateStart is not null"
			+" and (vpd.code='1' or vpd.id is null) and (vis.noActuality='0' or vis.noActuality='1')"
			+" group by vis.id, vis.dateStart, vis.timeExecute,vwf.name, pat.lastname,  pat.firstname,  pat.middlename"
			+" ,vr.name ,vss.name,vvr.name,vpd.code,vpd.id,mkb.id"
			+" order by vis.dateStart desc, vis.timeExecute desc").setMaxResults(1).getResultList() ;
	if (listVisLast.size()>0) {
		var visLast = listVisLast.get(0)[0];
		var mkb = listVisLast.get(0)[1];
		var spo = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase
				, java.lang.Long.valueOf(aSpoId)) ;
		var visLastO = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
				, java.lang.Long.valueOf(visLast)) ;
		var mkbO = mkb!=null?aContext.manager.find(Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10
				, java.lang.Long.valueOf(mkb)):null ;
		spo.setDateFinish(visLastO.getDateStart()) ;
		spo.setFinishFunction(visLastO.getWorkFunctionExecute()) ;
		spo.setIdc10(mkbO) ;
	} else {
		if(spo==null) throw "Нет ни одного присоединенного визита к СПО" ;
	}
	return spo.getId();
}
/**
 * Открыть СПО
 */
function reopenSpo(aContext, aSpoId) {
	var spo = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase
		, java.lang.Long.valueOf(aSpoId)) ;
	spo.setDateFinish(null) ;
	spo.setFinishFunction(null) ;
	aContext.manager.persist(spo) ;
	return spo.getId();
}

function findSpoIdByVisit(aContext, aVisitId) {
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId)) ;
	if(!visit) throw "Не найден Визит с ид "+aVisitId ;
	if(!visit.parent) throw "У визита нет СПО" ;
	return visit.parent.id ;	
}
function journalRegisterVisitByMap(aCtx,aParams) {
	return journalRegisterVisit(aCtx,aParams,0) ;
}
function journalRegisterVisitByFrm(aCtx,aParams) {
	var map = new java.util.HashMap() ;
	//throw aParams;
	map.put("listVisits",journalRegisterVisit(aCtx,aParams.get('id'),1))
	return  map;
}

function journalRegisterVisit(aCtx,aParams,frm) {
	var obj = aParams.split(":") ;
	var ret = new java.util.ArrayList() ;
	var startDate = obj[0] ;
	var finishDate = obj[1] ;
	var spec = obj[2] ;
	var rayon = obj[3] ;
	var primary = obj[4] ;
	var sn = +obj[5] ;
	var order = (obj[6]!=null&&obj[6]!="")?obj[6]:"dateStart,timeExecute" ;
	var func = obj[7] ;
	var lpu = obj[8] ;
	var serviceStream = obj[9] ;
	var dtype=+obj[10] ;
	if (+sn<1) sn=1 ;
	var dtypeSql = " (t.dtype='Visit' or t.dtype='ShortMedCase') "
	if (dtype==1) {
		dtypeSql="t.dtype='Visit'" ;
	} else if (dtype==2) {
		dtypeSql="t.dtype='ShortMedCase'" ;
	}
	var sql = "where "+dtypeSql+" and patient_id is not null and t.dateStart between to_date('"+startDate+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy')" ;
	if (lpu!=null && (+lpu>0)) {
		sql = sql + " and w.lpu_id='"+lpu+"'"
	}
	if (func!=null && (+func>0)) {
		sql = sql + " and wf.workFunction_id='"+func+"'"
	}
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunctionExecute_id='"+spec+"'"
	}
		
	if (serviceStream!=null && (+serviceStream>0)) {
		sql = sql+" and t.serviceStream_id='"+serviceStream+"'" ;
	}
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = 
		"select t.id ,to_char(t.dateStart,'dd.MM.yyyy')||' '||coalesce(cast(timeExecute as varchar(5)),'') as dateexecute"
		+",vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as spec"
		+",p.lastname||' '||p.firstname||' '||p.middlename as fio"
		+",vs.name as vsname,to_char(p.birthday,'dd.mm.yyyy') as birthday"
		+", case when p.address_addressId is not null "
		+"          then coalesce(a.fullname,a.name) || "
		+"               case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end" +
				" ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end" +
				"||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end"
		+"       when p.territoryRegistrationNonresident_id is not null"
		+"          then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident"
		+"               ||' '||ost.name||' '||p.StreetNonresident||"
		+"               case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end" 
		+" ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end" 
		+"||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end"
		+"   else '' "
		+"  end as address"
		+",vr.name as vrname"
		+",t.id"
		+",(select list(mp.series||' '||mp.polNumber||' '||ri.name||' ('||to_char(mp.actualDateFrom,'dd.MM.yyyy')||coalesce('-'||to_char(mp.actualDateTo,'dd.MM.yyyy')||')','-нет даты окончания')) from medpolicy mp left join reg_ic ri on ri.id=mp.company_id where mp.patient_id=p.id "
		+" and t.dateStart between mp.actualDateFrom and COALESCE(mp.actualDateTo,CURRENT_DATE)) as policy"
		+", vh.code as vhcode "
		+", (select list (mkb.code) from diagnosis d left join vocidc10 mkb on mkb.id=d.idc10_id where d.medCase_id=t.id) as diag"
		+", vss.name as vssname"
		+", (select list (ms.name) from medcase ser left join medservice ms on ms.id=ser.medService_id where ser.parent_id=t.id and ser.dtype='ServiceMedCase') as usl"
		+" from Medcase t "
		+" left join patient p on p.id=t.patient_id " 
		+" left join vocrayon vr on vr.id=p.rayon_id "
		+" left join workfunction wf on wf.id=t.workFunctionExecute_id "
		+" left join worker w on w.id=wf.worker_id"
		+" left join vocworkfunction vwf on vwf.id=wf.workfunction_id"
		+" left join patient wp on wp.id=w.person_id"
		+" left join vocsex vs on vs.id=p.sex_id"
		+" left join vocidc10 mkb on mkb.id=t.idc10_id"
		+" left join Address2 a on a.addressId=p.address_addressId"
		+" left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id"
		+" left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id"
		+" left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id"
		+" left join vochospitalization vh on vh.id=t.hospitalization_id "	
		+" left join VocServiceStream vss on vss.id=t.serviceStream_id "
		+sql 
		+" and (t.noActuality is null or t.noActuality='0') and t.dateStart is not null order by "+order
		;
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	if (+list.size()>0) {
		for (var i=0 ; i< list.size() ; i++) {
			if (frm>0) {
				ret.add(visitFrm(list.get(i),sn)) ;
			} else {
				ret.add(visitMap(list.get(i),sn)) ;
			}
			sn=sn+1 ;
			
		}
	} 
	return ret ;
}

function visitFrm(aVisit,aSn) {
	var frm = new Packages.ru.ecom.poly.ejb.form.TicketForm() ;
	frm.setSn(aSn) ;
	frm.setId(java.lang.Long.valueOf(aVisit[0])) ;
	frm.setDate(aVisit[1]) ;//date + time execute
	frm.setWorkFunctionInfo(aVisit[2]) ;//spec
	frm.setPatientName(aVisit[3]) ; //fio
	frm.setStatusName(aVisit[4]) ; //sex
	frm.setDateCreate(aVisit[5]); //birthday
	frm.setTicketInfo(aVisit[6]) ; //address
	frm.setTimeCreate(aVisit[7]!=null?aVisit[7]:"") ; //rayon
	frm.setUet(aVisit[8]) ; //medcard
	frm.setTime(aVisit[9]!=null?aVisit[9]:"") ;	 //policy
	frm.setPrevIdc10Date(aVisit[10]) ; //primary in year 
	frm.setConcomitantDiseases(aVisit[11]!=null?aVisit[11]:"") ; //mkb
	frm.setDirectHospital(false) ;
	frm.setMedServices(aVisit[12]); // serviceStream
	frm.setUsernameCreate(aVisit[13]) ; // service
	return frm ;
}

function visitMap(aVisit,aSn) {
	var map1 = new java.util.HashMap() ;
	
	map1.put("id", new java.lang.Long(aVisit[0])) ;
	map1.put("sn",""+aSn) ;
	map1.put("date",aVisit[1]) ;
	map1.put("fio",aVisit[3]) ;
	map1.put("sex",aVisit[4]) ;
	map1.put("birthday",aVisit[5]);
	map1.put("address",aVisit[6]) ;
	map1.put("rayon",aVisit[7]) ;
	map1.put("medcard","") ;
	map1.put("policy",aVisit[9]) ;
	map1.put("mkb",aVisit[11]) ;
	map1.put("primary",aVisit[10]) ;
	map1.put("serviceStream",aVisit[12]) ;
	map1.put("spec",aVisit[2]) ;
	map1.put("usernameCreate",aVisit[13]) ;
	return map1 ;
}

