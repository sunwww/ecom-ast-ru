function onPreCreate(aForm, aCtx) {
	aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}