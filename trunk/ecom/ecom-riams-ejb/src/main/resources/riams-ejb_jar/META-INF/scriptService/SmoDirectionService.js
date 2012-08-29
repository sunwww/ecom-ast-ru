function getWorkerName(aContext) {
	return aContext.serviceInvoke("WorkerService", "findLogginedWorker").doctorInfo+"" ;
}

function getFunctionName(aContext) {
	return aContext.sessionContext.callerPrincipal.name ;
}
/**
 * Поиск по текущей дате WorkCalendarDay
 */
function findCurrentWorkCalendarDayByUser(aContext) {
	var login=getFunctionName(aContext) ;
	
	var list = aContext.manager.createNativeQuery("select wcd.id from WorkCalendarDay wcd" 
			+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id"
			+" left join WorkFunction wfGr on wfGr.id=wc.workFunction_id"
			+" left join SecUser suGr on suGr.id=wfGr.secUser_id"
			+" left join WorkFunction wf on wf.group_id=wc.workFunction_id"
			+" left join SecUser su on su.id=wf.secUser_id"
			+" where calendarDate=CURRENT_DATE and (suGr.login='"+login+"' or su.login='"
			+login+"')")
		//.setParameter("date", new java.sql.Date(new java.util.Date().getTime()))
		//.setParameter("login",login)
			.setMaxResults(1)
		.getResultList() ;
	
	var worker = aContext.manager.createNativeQuery("select w.person_id from Worker w "
			+" left join WorkFunction wf on wf.worker_id=w.id "
			+" left join SecUser su on su.id=wf.secUser_id"
			+" where su.login='"+login+"'").setMaxResults(1).getResultList() ;
	if (!worker.isEmpty()){
		var idwork = worker.iterator().next() ;
		list = aContext.manager.createNativeQuery("select wcd.id from WorkCalendarDay wcd" 
				+" left join WorkCalendar wc on wc.id=wcd.workCalendar_id"
				+" left join WorkFunction wfGr on wfGr.id=wc.workFunction_id"
				+" left join Worker wGr on wGr.id=wfGr.worker_id"
				+" left join WorkFunction wf on wf.group_id=wc.workFunction_id"
				+" left join Worker w on w.id=wf.worker_id"
				+" where calendarDate=CURRENT_DATE and (wGr.person_id='"+idwork+"' or w.person_id='"
				+idwork+"')")
			//.setParameter("date", new java.sql.Date(new java.util.Date().getTime()))
			//.setParameter("login",login)
				.setMaxResults(1)
			.getResultList() ;
	}
	if (!list.isEmpty()) {return list.iterator().next() ;}
	/*
	var list = aContext.manager.createNativeQuery("select wcd.id from WorkCalendarDay wcd" 
			+" left join WorkCalendar wcA on wcA.id=wcd.workCalendar_id"
			+" left join WorkFunction wfAGr on wfAGr.id=wcA.workFunction_id"
			//+" left join SecUser suGr on suGr.id=wfGr.secUser_id"
			+" left join WorkFunction wf1 on wf1.up_id=wf.id"
			+" left join SecUser su1 on su1.id=wf1.secUser_id"
			+" left join Worker w1 on w1.id=wf1.worker_id"
			+" where calendarDate=:date and (suGr.login='"+login+"' or su.login='"
			+login+"')")
		.setParameter("date", new java.sql.Date(new java.util.Date().getTime()))
		*/
	return null ;
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
