function onPreCreate(aForm, aContext) {
    checkStantAmount(aForm, aContext);
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
}

function onPreSave(aForm,aEntity, aContext) {
    checkStantAmount(aForm, aContext);
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
}

function onView(aForm, aEntity, aContext) {
	aForm.setFinanceSource(aEntity.medCase.serviceStream.id);
	aForm.setDiagnosis(aEntity.method!=null?(aEntity.method.diagnosis!=null?aEntity.method.diagnosis:""):"");
	aForm.setPatientModel(aEntity.method!=null?(aEntity.method.patientModel!=null?aEntity.method.patientModel:""):"");
}

function checkStantAmount(aForm, aCtx) {
	if (aForm.getKind()==null) {
		throw "Вид ВМП не указан!";
	}
	var kind =aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocKindHighCare, aForm.getKind());
	if (kind.getIsStentRequired()&&(aForm.getStantAmount()==null||aForm.getStantAmount()==0)) {
        throw "Для данного вида ВМП необходимо указать количество установленных стентов!";
	}

}