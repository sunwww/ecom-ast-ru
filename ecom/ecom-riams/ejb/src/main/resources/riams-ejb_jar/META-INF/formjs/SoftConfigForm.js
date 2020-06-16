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
function onPreSave(form, softConfig, aContext) {
    if (!softConfig.getKey().equals(form.getKey()) ||
        !softConfig.getKeyValue().equals(form.getKeyValue())) {
        var username = aContext.getSessionContext().getCallerPrincipal().getName();
        //При изменении настроек пишем кто, когда что на что поменял.
        var j = new Packages.ru.ecom.mis.ejb.domain.prescription.AdminChangeJournal("SoftConfigChange");
        j.setCreateUsername(username);
        var s = "from: {key: '"+softConfig.getKey()+"', val: '"+softConfig.getKeyValue()+"'} to: {"+
            "{key: '"+form.getKey()+"', val: '"+form.getKeyValue()+"'}";
        j.setAnnulRecord(s);
        aContext.manager.persist(j);
    }
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

