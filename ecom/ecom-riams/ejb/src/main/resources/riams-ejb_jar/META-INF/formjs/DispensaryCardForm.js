/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    var d = new java.util.Date().getTime();
    aEntity.setCreateUsername(username);
    aEntity.setCreateDate(new java.sql.Date(d));
    aEntity.setCreateTime(new java.sql.Time(d));
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
    var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    var d = new java.util.Date().getTime();
    aEntity.setEditUsername(username);
    aEntity.setEditDate(new java.sql.Date(d));
    aEntity.setEditTime(new java.sql.Time(d));
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {

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

