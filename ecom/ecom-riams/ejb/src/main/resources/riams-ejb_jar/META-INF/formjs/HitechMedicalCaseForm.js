function onPreCreate(aForm, aContext) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreSave(aForm,aEntity, aContext) {
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
}