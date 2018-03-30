/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aCtx) {
}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aCtx) {
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aCtx) {
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aCtx) {
    if(!aCtx.manager.createNativeQuery("select id from e2listentry where id="+aEntityId+" and isClosed='1'").getResultList().isEmpty()) {
        throw "Невозможно удалить заполнение, оно закрыто. Для удаления откройте заполнение.";
    }
}

/**
 * При удалении
 */
function onDelete(aEntityId, aCtx) {
}

