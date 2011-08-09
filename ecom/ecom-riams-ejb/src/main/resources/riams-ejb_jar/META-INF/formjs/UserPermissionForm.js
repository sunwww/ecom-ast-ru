function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aForm.setCreateTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time(date.getTime())))
}