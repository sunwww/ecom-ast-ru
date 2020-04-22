/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    var manager = aContext.manager;
    manager.createNativeQuery("update Covid19 set noActual='1' where patient_id="+aForm.patient).executeUpdate();
    aEntity.setNoActual(false);
    var username = aContext.getSessionContext().getCallerPrincipal().getName();
    aEntity.setCreateUsername(username) ;
    aEntity.setExportUsername(null) ;
    aEntity.setExportDate(null) ;
    aEntity.setExportTime(null) ;
    if ("1".equals(aEntity.labResult)) { //создаем КВ браслет
        var cipTypes = manager.createNamedQuery("VocColorIdentityPatient.getByCode")
            .setParameter("code","COVID19").getResultList();
        var cip = new Packages.ru.ecom.mis.ejb.domain.patient.ColorIdentityPatient();
        cip.setVocColorIdentity(cipTypes.isEmpty() ? null :  cipTypes.get(0));
        cip.setCreateUsername(username);
        cip.setStartDate(aEntity.medCase.dateStart);
        cip.setStartTime(aEntity.medCase.createTime);
        manager.persist(cip);

        aEntity.medCase.addColorsIdentity(cip);
        manager.persist(aEntity.medCase);

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
    throw "Редактирование документа не предусмотрено. Необходимо создать копию";
}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
    var manager = aContext.manager;
    var list = manager.createNativeQuery("select c.id from covid19 c where c.patient_id " +
        " =(select patient_id from covid19 c2 where id="+aEntityId+") and c.id!="+aEntityId).getResultList();
    var sql;
    if (list.isEmpty()) { //remove contacts
        sql = "delete from Covid19Contact where card_id="+aEntityId;
    } else {
        sql ="update Covid19Contact set card_id="+list.get(0)+" where card_id="+aEntityId;
    }
    manager.createNativeQuery(sql).executeUpdate();
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}

