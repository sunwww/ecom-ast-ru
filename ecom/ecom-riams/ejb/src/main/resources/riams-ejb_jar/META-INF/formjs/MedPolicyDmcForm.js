/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	checkPeriod(aForm) ;
	
}

function onPreSave(aForm, aEntity, aCtx) {
	checkPeriod(aForm) ;
}
function checkPeriod(aForm) {
	var dateFrom, dateTo ;
	try {
		dateFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateFrom) ;
		//dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateTo) ;
	} catch(e) {
		//throw "Неправильно введена дата начала или окончания" ;
		throw "Неправильно введена дата начала действия" ;
	}
	try {
		dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateTo) ;
	} catch(e) {
		dateTo=null;
		//throw "Неправильно введена дата начала или окончания" ;
	}
	if (dateTo==null||(dateTo.getTime() < dateFrom.getTime())) throw "Дата окончания должна быть больше, чем дата начала";
}
function onPreDelete(aEntityId, aCtx) {
	var list = aCtx.manager.createNativeQuery("select patient_id,dtype from medpolicy where id='"+aEntityId+"'").setMaxResults(1).getResultList() ;
	if (list.size()>0) {
		aCtx.manager.createNativeQuery("update Patient set attachedOmcPolicy_id=null where id="+list.get(0)[0]).executeUpdate() ;
	}
}