function onPreSave(aForm,aEntity, aCtx) {
	if (aEntity.dischargeTime!=null) {
		throw "Нельзя сохранить предварительную выписку, если пациент уже выписан!!!" ;
	}
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function getObject(aCtx,aId,aClazz) {
	return (aId==null||aId=='0'||aId=='')?null:aCtx.manager.find(aClazz, aId) ;
}
function onSave(aForm,aEntity, aCtx) {
	Packages.ru.ecom.mis.ejb.service.medcase.HospitalMedCaseServiceBean.saveDischargeEpicrisis(aForm.id,aForm.getDischargeEpicrisis(),aCtx.manager) ;
	if (+aForm.reasonDischarge>0 && aEntity.statisticStub!=null) {
		var reasonDischarge = getObject(aCtx, aForm.reasonDischarge, Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocReasonDischarge);
		aEntity.statisticStub.setReasonDischarge(reasonDischarge) ;
		aCtx.manager.persist(aEntity) ;
	}
	if (+aForm.resultDischarge>0 && aEntity.statisticStub!=null) {
		var resultDischarge = getObject(aCtx, aForm.resultDischarge, Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocResultDischarge);
		aEntity.statisticStub.setResultDischarge(resultDischarge) ;
		aCtx.manager.persist(aEntity) ;
	}
	if (+aForm.childBirth>0 && aEntity.statisticStub!=null) {
		var childBirth = getObject(aCtx, aForm.resultDischarge, Packages.ru.ecom.mis.ejb.domain.birth.voc.VocChildBirth);
		aEntity.statisticStub.setChildBirth(childBirth) ;
		aCtx.manager.persist(aEntity) ;
	}

}