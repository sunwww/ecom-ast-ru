function onPreCreate(aForm, aCtx) {
	/*var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;*/
	
}
function onCreate(aForm,aEntity,aCtx) {
	var pat = aForm.patient ;
	if (pat!=null && +pat>0) {
		var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,pat) ;
		aEntity.lastname = patient.lastname ;
		aEntity.firstname = patient.firstname ;
		aEntity.middlename = patient.middlename ;
		aEntity.birthday = patient.birthday ;
		aEntity.cost=aEntity.medService.pricePosition.cost ;
	}
}
function onPreSave(aForm,aEntity , aCtx) {
	
}

