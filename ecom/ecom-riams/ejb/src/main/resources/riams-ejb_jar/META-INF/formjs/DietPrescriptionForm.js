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
	var diets = aCtx.manager.createNativeQuery("select p.id from prescription p where p.prescriptionList_id="+aForm.prescriptionList+" and p.dtype='DietPrescription' and p.planEndDate is null").getResultList();
	if (diets.size()>0) { // Отмечаем остальные диеты как оконченные
		for (var i=0;i<diets.size();i++) {
			var id = new java.lang.Long(""+diets.get(i));
		//	throw "id="+id;
			var diet = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.DietPrescription,id) ;
			diet.setPlanEndDate(aEntity.getPlanStartDate());
			diet.setPlanEndTime(aEntity.getPlanStartTime());
			aCtx.manager.persist(diet);
		}
	} 
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aEntity.setPrescriptSpecial(wf) ;

}