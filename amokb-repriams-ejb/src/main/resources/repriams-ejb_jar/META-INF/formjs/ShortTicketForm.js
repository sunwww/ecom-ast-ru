function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setDateCreate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setTimeCreate(new java.sql.Time (date.getTime())) ;
	aForm.setUsernameCreate(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
