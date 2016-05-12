function onPreCreate(aForm, aCtx) {
	aForm.setDateCreate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
//Создание
function onCreate(aForm, aPregnancy, aCtx) {
	var pregHistory = new Packages.ru.ecom.mis.ejb.domain.birth.PregnancyHistory() ;
	var medCase = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase, aForm.medCase) ;
	pregHistory.pregnancy = aPregnancy ;
	pregHistory.medCase = medCase ;
	aCtx.manager.persist(pregHistory) ;
}