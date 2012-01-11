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
	//visit.workFunctionExecute = ;
	//visit.startWorker = ;
	if(visit.startWorker==null) {
		visit.startWorker = aContext.serviceInvoke("WorkerService", "findLogginedWorker") ;
	}
	// FIXME определять функцию правильно
	if(visit.workFunctionExecute==null) {
		visit.workFunctionExecute = aContext.serviceInvoke("WorkerService", "findLogginedWorkFunctionList")
			.iterator().next() ;
	}
	return visit.getId();
}
/**
 * Закрыть СПО
 */
function closeSpoByVisit(aContext, aVisitId) {
	var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId)) ;
	if(visit.getDateStart()==null) throw "У визита нет даты приема" ;	
	// FIXME последний ли визит в СПО?
	var spo = visit.getParent() ;
	if(spo==null) throw "Визит не присоединен к СПО" ;
	if(spo.getDateFinish()!=null) throw "СПО уже закрыто" ;
	spo.setDateFinish(visit.getDateStart()) ;
	spo.setFinishWorker(visit.getWorkFunctionExecute().getWorker()) ;
	// FIXME диагноз
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
	var order = obj[6]!=null?obj[6]:"dateStart,timeExecute" ;
	var func = obj[7] ;
	if (+sn<1) sn=1 ;
	
	var sql = "where t.dtype='Visit' and patient_id is not null and t.dateStart between to_date('"+startDate+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy')" ;
	if (func!=null && (+func>0)) {
		sql = sql + " and wf.workFunction_id='"+func+"'"
	}
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunctionExecute_id='"+spec+"'"
	}
		
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = "left join patient p on p.id = t.patient_id "+sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = 
		"select t.id ,to_char(t.dateStart,'dd.MM.yyyy')||' '||cast(timeExecute as varchar(5)) as dateexecute"
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
		//+"               coalesce(' д.'||p.HouseNonresident,'') ||coalesce(' корп.'|| p.BuildingHousesNonresident,'') ||coalesce(' кв. '|| p.ApartmentNonresident,'')"
		+"               case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end" 
		+" ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end" 
		+"||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end"
		+"   else '' "
		+"  end as address"
		//+",$$ByPatient^ZAddressLib(p.id)"
		+",vr.name as vrname"
		+",t.id"
		+",(select list(mp.series||' '||mp.polNumber||' '||ri.name||' ('||to_char(mp.actualDateFrom,'dd.MM.yyyy')||'-'||to_char(mp.actualDateTo,'dd.MM.yyyy')||')') from medpolicy mp left join reg_ic ri on ri.id=mp.company_id where mp.patient_id=p.id "
		+" and t.dateStart between mp.actualDateFrom and COALESCE(mp.actualDateTo,CURRENT_DATE)) as policy"
		+", vh.code as vhcode "
		//+",list(mkb.code) as mkbcode,t.directHospital, "
		+", (select list (mkb.code) from diagnosis d left join vocidc10 mkb on mkb.id=d.idc10_id where d.medCase_id=t.id) as diag"
		+", vss.name as vssname"
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
		+" and (t.noActuality is null or cast(t.noActuality as integer)=0) and t.dateStart is not null order by "+order
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
	return map1 ;
}

