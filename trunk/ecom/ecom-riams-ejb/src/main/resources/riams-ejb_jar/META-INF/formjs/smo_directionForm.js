
function findLogginedWorker(aCtx) {
    return aCtx.serviceInvoke("WorkerService", "findLogginedWorker") ;
/*	var username = aCtx.sessionContext.callerPrincipal.name ;
	var list = aCtx.manager.createQuery("from WorkFunction where secUser.login = :login")
		.setParameter("login", username) 
		.getResultList() ;
	if(list.size()==0) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между Worker и SecUser" ;	
	else if(list.size()>1) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного соответсвия между Worker и SecUser" ;	
	else {
		return list.iterator().next() ;
	}
*/	
}
function findLogginedWorkFunction(aCtx) {
	return aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
}

function checks(aVisit) {
	if(aVisit.workFunctionPlan==null) throw "Нет места направления" ;
	if(aVisit.datePlan==null) throw "Нет даты направления" ;
	if(aVisit.timePlan==null) throw "Нет времени направления" ;
}

function createOrSave(aForm, aVisit, aCtx) {
	aVisit.timePlan.medCase = aVisit ;
	//aVisit.orderWorker = findLogginedWorker(aCtx) ;
	var workFunc = findLogginedWorkFunction(aCtx) ;
	aVisit.orderWorkFunction =  workFunc;
	aVisit.orderWorker = workFunc!=null?workFunc.worker:null ;
	
}

function onCreate(aForm, aVisit, aCtx) {
	checks(aVisit) ;
	if(aVisit.timePlan.medCase!=null) throw "На это время уже есть направление: "+ aVisit.timePlan.medCase.id;
	createOrSave(aForm, aVisit, aCtx) ;
}


function onPreSave(aForm, aVisit, aCtx) {
    // освобождение предыдущего времени
	var visit = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit, new java.lang.Long(aForm.id))  ;
	visit.timePlan.medCase = null ;
}

function onSave(aForm, aVisit, aCtx) {
	checks(aVisit) ;
	createOrSave(aForm, aVisit, aCtx) ;
}
function onPreDelete(aId,aCtx) {
	aCtx.manager
		.createNativeQuery("update WorkCalendarTime set medCase_id=null where medCase_id=:medCase")
		.setParameter("medCase",aId).executeUpdate() ;
}