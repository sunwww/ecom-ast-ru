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
		dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateTo) ;
	} catch(e) {
		throw "Неправильно введена дата начала или окончания" ;
	}
	if (dateTo==null || (dateTo.getTime() < dateFrom.getTime())) throw "Дата окончания должна быть больше, чем дата начала";
}