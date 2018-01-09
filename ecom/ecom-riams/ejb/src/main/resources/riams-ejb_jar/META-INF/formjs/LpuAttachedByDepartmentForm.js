/*
 * Перед созданием проверяем, есть ли действующее прикрепление
 */
function onPreCreate (aForm, aCtx) {
	makeChecks(aForm, aCtx);
}
/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aEntity.getDateTo()!=null&&aEntity.getDateTo!='') {
		aEntity.setNoActuality(true);
	} else {aEntity.setNoActuality(false);}
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
    makeChecks(aForm, aCtx);
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aEntity.getDateTo()!=null&&aEntity.getDateTo!='') {
		aEntity.setNoActuality(true);
	}else {aEntity.setNoActuality(false);}
}

function makeChecks (aForm, aCtx) {
	if (aForm.getDateTo()==null||aForm.getDateTo()=="") { //Запрет на дубли прикреплений

        var list = aCtx.manager.createQuery("from LpuAttachedByDepartment " +
            "where patient_id=:patient and dateTo is null " + (aForm.getId() != null && aForm.getId() > 0 ? " and id!=" + aForm.getId() : ""))
            .setParameter("patient", aForm.patient)
            .getResultList();
        if (list.size() > 0) {
            throw "Сохранение прикрепления невозможно, у пациента уже есть <a href='entityParentView-mis_lpuAttachedByDepartment.do?id=" + list.get(0).getId() + "'>действующее прикрепление!</a>";
        }
    } else if (aForm.getAttachedType() != null) { //
		var type = aCtx.manager.createNativeQuery("select list(code) from vocattachedtype where id=" + aForm.getAttachedType()).getResultList().get(0) + "";
		if (type == "1") {
			throw "Невозможно открепить пациента в прикреплении по территориальному признаку!";
		}
	}
}