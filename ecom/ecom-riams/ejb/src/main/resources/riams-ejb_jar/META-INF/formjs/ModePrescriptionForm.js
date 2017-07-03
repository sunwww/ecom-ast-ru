
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}
/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aEntity.setPrescriptSpecial(wf) ;
	
	var modes = aCtx.manager.createNativeQuery("select p.id from prescription p where p.prescriptionList_id="+aForm.prescriptionList+" and p.dtype='ModePrescription' and p.planEndDate is null").getResultList();
	if (modes.size()>0) { // Отмечаем остальные режимы как оконченные
		for (var i=0;i<modes.size();i++) {
			var id = new java.lang.Long(""+modes.get(i));
			var mode = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.ModePrescription,id) ;
			mode.setPlanEndDate(aEntity.getPlanStartDate());
			mode.setPlanEndTime(aEntity.getPlanStartTime());
			aCtx.manager.persist(mode);
		}
	}
}