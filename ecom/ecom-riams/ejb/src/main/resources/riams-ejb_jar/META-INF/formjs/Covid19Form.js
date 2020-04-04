/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    aContext.manager.createNativeQuery("update Covid19 set noActual='1' where patient_id="+aForm.patient).executeUpdate();
    aEntity.setNoActual(false);
    aEntity.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().getName()) ;
}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
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
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
    throw "Редактирование документа не предусмотрено. Необходимо создать копию";
}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
    throw "Удаление документа тоже не предусмотрено.";
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}

