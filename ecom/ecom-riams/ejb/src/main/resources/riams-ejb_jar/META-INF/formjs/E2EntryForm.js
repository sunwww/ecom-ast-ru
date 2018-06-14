/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
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
    if (!aContext.manager.createNativeQuery("select list.id from e2listentry list where list.id="+aEntity.getListEntry().getId()+" and list.isClosed='1'").getResultList().isEmpty()) {
        throw "Заполнение закрыто, редактирование записи невозможно!";
    }
    if (aForm.getBillNumber()==null||aForm.getBillNumber()=="") {aForm.setBill(null);}
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

