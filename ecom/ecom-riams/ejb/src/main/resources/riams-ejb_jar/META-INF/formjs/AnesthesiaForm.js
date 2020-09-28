function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
