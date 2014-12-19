/*
 * Перед созданием проверяем, есть ли действующее прикрепление
 */
function onPreCreate (aForm, aCtx) {
	var list = aCtx.manager.createQuery("from LpuAttachedByDepartment " +
		"where patient_id=:patient and dateTo is null ")
		.setParameter("patient", aForm.patient)
		.getResultList();
	 if (list.size()>0) {
		 throw "Создание прикрепления невозможно, у пациента уже есть <a href='entityParentView-mis_lpuAttachedByDepartment.do?id="+list.get(0).getId()+"'>действующее прикрепление!</a>";
	 }	
}
/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
