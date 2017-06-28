function check(aForm) {
	if (
			(aForm.dateReplace!=null && !aForm.dateReplace.equals(""))
	||		(aForm.registrationReplaceDate!=null && !aForm.registrationReplaceDate.equals(""))
	||		(+aForm.lawCourtReplace>0)
	||		(+aForm.courtDecisionReplace>0)
			) {
		if ((aForm.dateReplace==null || aForm.dateReplace.equals(""))
	||		(aForm.registrationReplaceDate==null || aForm.registrationReplaceDate.equals(""))
	||		(+aForm.lawCourtReplace==0)
	||		(+aForm.courtDecisionReplace==0)) {
			throw "Необходимо заполнить все поля по замене (отмене) принудительного лечения!!!" ;
		}
	}
}
/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;

}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	check(aForm) ;
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;

}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
	check(aForm) ;
}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}
