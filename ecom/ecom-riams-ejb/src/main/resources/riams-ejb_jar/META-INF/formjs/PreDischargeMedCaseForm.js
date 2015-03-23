function onPreSave(aForm,aEntity, aCtx) {
	if (aEntity.dischargeTime!=null) {
		throw "Нельзя сохранить предварительную выписку, если пациент уже выписан!!!" ;
	}
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}