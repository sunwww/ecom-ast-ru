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
			+" left join WorkFunction wf on wfGr.group_id=wf.id"
			+" left join SecUser su on su.id=wf.secUser_id"
			+" where calendarDate=:date and (suGr.login='"+login+"' or su.login='"
			+login+"')")
		.setParameter("date", new java.sql.Date(new java.util.Date().getTime()))
		//.setParameter("login",login)
		.getResultList() ;
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
