function getWorkerName(aContext) {
	return aContext.serviceInvoke("WorkerService", "findLogginedWorker").doctorInfo+"" ;
}

function getFunctionName(aContext) {
	return aContext.sessionContext.principal ;
}
/**
 * Поиск по текущей дате WorkCalendarDay
 */
function findCurrentWorkCalendarDay(aContext) {
	var list = aContext.manager.createQuery("from WorkCalendarDay where calendarDate=:date")
		.setParameter("date", new java.sql.Date(new java.util.Date().getTime())).getResultList() ;
	return list.isEmpty() ? null : list.iterator().next() ;	
} 

function createWorkFunctionClause(aContext) {
	var list = aContext.serviceInvoke("WorkerService", "findLogginedWorkFunctionList") ;
	var ret = "" ;
	if(!list.isEmpty()) {
		var firstPassed = false ;
		for(var i=0; i<list.size(); i++) {
			if(firstPassed) {	
				ret = ret + " or " ;
			} else {
				firstPassed = true ;
			}
			ret = ret + "workFunctionPlan_id="+(list.get(i).getGroup()==null?list.get(i).getId():list.get(i).getGroup().getId()) ;
		}
	}
	return ret ;
	
}

/**
 * Поиск направлений по календарному дню.
 * Если день null - поиск по текущему дню, иначе - пусто
 */
function findPolyAdmissions(aContext,aCalendarDayId) {
	var ret = new java.util.ArrayList() ;
	
	if(aCalendarDayId==null) {
		aCalendarDayId = findCurrentWorkCalendarDay(aContext) ;
		if(aCalendarDayId==null) {
			return ret ; // не найдена дата
		} else {
			aCalendarDayId = aCalendarDayId.getId()+"" ;
		}
	}
	var query =  "from Visit where ("
		+ createWorkFunctionClause(aContext) ;
	if(aCalendarDayId!=null) {
		query += ") and (datePlan_id="+new java.lang.Long(aCalendarDayId) ;
	}	
	query += ") order by datePlan, timePlan";
	//throw query ;	
	var list = aContext.manager.createQuery(
		query
	).getResultList() ;
	var it = list.iterator() ;
	var i = 0 ;
	while(it.hasNext()) {
		i++ ;
		var visit = it.next() ;
		var map = new java.util.HashMap(4) ;
		map.put("sn", i+"")
		map.put("id", ""+visit.id)
		map.put("specialist", visit.workFunctionPlan.name);
		//map.put("doctor", visit.workFunctionPlan.worker.doctorInfo);
		map.put("patient", visit.patient!=null ? visit.patient.patientInfo : "");
		map.put("dateTimeOrder", visit.datePlanText+"&nbsp;"+visit.timePlanText) ;
		map.put("date", visit.datePlanText) ;
		map.put("time", visit.timePlanText) ;
		map.put("orderWorker", visit.orderWorkerText) ;
		map.put("orderLpu", visit.orderLpu?visit.orderLpu.name:"") ;
		if(visit.getDateStart()!=null) {
			map.put("dateTimeStart"
				, Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(visit.getDateStart())
					 +"&nbsp;"
					 +Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(visit.getTimeExecute())) ;
			
		}
		if(visit.getWorkFunctionExecute()!=null && visit.getWorkFunctionExecute().getWorker()) {
		    map.put("executer",  visit.getWorkFunctionExecute().getWorker().getDoctorInfo()) ;
		}
		if (visit.visitResult!=null) {
			map.put("visitResult",visit.visitResult.name) ;
		}
		ret.add(map);
	}
	return ret ;
}

/**
 * Поиск направлений по календарному дню.
 * Если день null - поиск по текущему дню, иначе - пусто
 */
 
function findPolyAdmissionsByWorkFunc(aContext,aCalendarDayId, aWorkFunction) {
	var ret = new java.util.ArrayList() ;
	
	if(aCalendarDayId==null) {
		aCalendarDayId = findCurrentWorkCalendarDay(aContext) ;
		if(aCalendarDayId==null) {
			return ret ; // не найдена дата
		} else {
			aCalendarDayId = aCalendarDayId.getId()+"" ;
		}
	}
	var query =  "from Visit where (workFunctionPlan_id="
		+ aWorkFunction ;
	if(aCalendarDayId!=null) {
		query += ") and (datePlan_id="+new java.lang.Long(aCalendarDayId) ;
	}	
	query += ") order by datePlan, timePlan";
	//throw query ;	
	var list = aContext.manager.createQuery(
		query
	).getResultList() ;
	var it = list.iterator() ;
	var i = 0 ;
	while(it.hasNext()) {
		i++ ;
		var visit = it.next() ;
		var map = new java.util.HashMap(4) ;
		map.put("sn", i+"")
		map.put("id", ""+visit.id)
		map.put("specialist", visit.workFunctionPlan.name);
		//map.put("doctor", visit.workFunctionPlan.worker.doctorInfo);
		map.put("patient", visit.patient!=null ? visit.patient.patientInfo : "");
		map.put("date", visit.datePlanText) ;
		map.put("time", visit.timePlanText) ;
		map.put("orderWorker", visit.orderWorkerText) ;
		map.put("orderLpu", visit.orderLpu?visit.orderLpu.name:"") ;
		if(visit.getDateStart()!=null) {
			map.put("dateTimeStart"
				, Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(visit.getDateStart())
					 +"&nbsp;"
					 +Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(visit.getTimeExecute())) ;
			
		}
		if(visit.getWorkFunctionExecute()!=null && visit.getWorkFunctionExecute().getWorker()) {
		    map.put("executer",  visit.getWorkFunctionExecute().getWorker().getDoctorInfo()) ;
		}
		ret.add(map);
	}
	return ret ;
}