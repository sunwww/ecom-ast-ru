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


function journalRegisterVisit(aCtx,aParams) {
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
	var order = obj[6]!=null?obj[6]:"dateStart,timeExecute" ;
	if (+sn<1) sn=1 ;
	
	var sql = "where t.dtype='Visit' and patient_id is not null and t.dateStart between '"+startDate+"' and '"+finishDate+"'" ;
	if (spec!=null && (+spec>0)) {
		sql = sql + " and t.workFunctionExecute_id='"+spec+"'"
	}
		
	if (primary!=null && (+primary>0)) {
		sql = sql+" and t.hospitalization_id='"+primary+"'" ;
	}
	if (rayon!=null && (+rayon>0)) {
		sql = "left join patient p on p.id = t.patient_id "+sql+" and p.rayon_id='"+rayon+"'" ;
	}
	sql = "select t.id from Medcase t "+sql +" and (t.noActuality is null or cast(t.noActuality as integer)=0) order by "+order;
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	var ids = "" ;
	//throw list.size() ;
	if (+list.size()>0) {
		for (var i=0 ; i< list.size() ; i++) {
			var visitId=+list.get(i) ;
			var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
				, java.lang.Long.valueOf(visitId)) ;
			ret.add(visitMap(visit,sn,FORMAT_1,FORMAT_2,FORMAT_3,aCtx.manager)) ;
			sn=sn+1 ;
			//ids=ids+","+id  ;
			
		}
		//sql = "from Medcase where dtype='Visit' and id in ("+ids.substring(1)+') order by '+order;
		//throw sql ;
		//		list = aCtx.manager.createQuery(sql).getResultList() ;
		
	} else {
	}
	
	return ret ;
}
function visitMap(aVisit,aSn,aFormatSql,aFormat,aFormatTime,aManager) {
	var map = new java.util.HashMap() ;
	var pat = aVisit.patient ;
	map.put("id", new java.lang.Long(aVisit.id)) ;
	map.put("sn",""+aSn) ;
	map.put("date",aFormat.format(aVisit.dateStart)+" "+(aVisit.timeExecute!=null?aFormatTime.format(aVisit.timeExecute):"")) ;
	map.put("fio",pat.lastname+' '+pat.firstname+' '+pat.middlename) ;
	map.put("sex",pat.sex!=null?pat.sex.name:"") ;
	map.put("birthday",pat.birthday);
	map.put("address",pat.addressRegistration) ;
	map.put("rayon",pat.rayon!=null?pat.rayon.code:"") ;
	
	map.put("medcard","") ;
	
	var pol ="";
	
	if (pat!=null) {
		var sqlDate = aFormatSql.format(aVisit.dateStart) ;
		var sql = "from MedPolicy where patient_id="
		+pat.id+" and cast(cast('"+sqlDate+"' as date) as int)<=cast(actualDateTo as int) and cast(cast('"
		+sqlDate+"' as date) as int) >= cast(actualDateFrom as int)" ;
		//throw sql ;
		var policies  = aManager.createQuery(sql).getResultList() ;
		pol = policies.size()>0 ?policies.get(0).text:"" ;
	}
	
	
	
	map.put("policy",pol) ;
	var diag ="";
	
	/*if (pat!=null) {
		var sqlDate = aFormatSql.format(aTic.date) ;
		var sql = "from MedPolicy where patient_id="
		+pat.id+" and cast(cast('"+sqlDate+"' as date) as int)<=cast(actualDateTo as int) and cast(cast('"
		+sqlDate+"' as date) as int) >= cast(actualDateFrom as int)" ;
		//throw sql ;
		var policies  = aManager.createQuery(sql).getResultList() ;
		pol = policies.size()>0 ?policies.get(0).text:"" ;
	}*/
	var diags = aVisit.diagnosis ;
	var mkb="" ;
	if (diags.size()>0) {
		var diag = diags.get(0) ;
		mkb = diag.idc10!=null?diag.idc10.code:"" ;
	}
	map.put("mkb",mkb) ;
	map.put("primary",aVisit.hospitalization!=null?aVisit.hospitalization.code:"") ;
	map.put("serviceStream",aVisit.serviceStream!=null?aVisit.serviceStream.name:"") ;
	//map.put("directHospital",aTic.directHospital) ;
	map.put("spec",aVisit.workFunctionExecuteText) ;
	return map ;
}