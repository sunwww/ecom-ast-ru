/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/Contract/ContractPerson/NaturalPerson/Create")) {
        //Создаем юридическое лицо при создании пациента
        if (aContext.manager.createNativeQuery("select id from contractperson where dtype='NaturalPerson' and patient_id="+aEntity.kinsman.id).getResultList().isEmpty()) {
            aContext.manager.persist(new Packages.ru.ecom.mis.ejb.domain.contract.NaturalPerson(aEntity.kinsman));
        }

    }
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

