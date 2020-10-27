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
    //Информация о выгрузке на портал (exportDate и тп) должна ставиться и в новой карте
    /*aEntity.setExportUsername(null) ;
    aEntity.setExportDate(null) ;
    aEntity.setExportTime(null) ;*/
    if ("1".equals(aEntity.labResult)) { //создаем КВ браслет
        addBracelet(manager,"COVID19", aEntity, username);
    }
    if (true == aEntity.isPregnant) {
        addBracelet(manager,"PREGNANT", aEntity, username);
    }
    if (true == aEntity.isDoctor) {
        addBracelet(manager,"DOCTOR", aEntity, username);
    }
}

function addBracelet(manager, code,aEntity, username) {
    var cipTypes = manager.createNamedQuery("VocColorIdentityPatient.getByCode")
        .setParameter("code",code).getResultList();
    if (!cipTypes.isEmpty()) {
        var cipType = cipTypes.get(0);
        //Только если нет браслета
        if (manager.createNativeQuery("select b.medcase_id  from medcase_coloridentitypatient b " +
            " left join colorIdentityPatient cip on cip.id=b.colorsidentity_id where b.medcase_id=:id and cip.voccoloridentity_id=:colorId")
            .setParameter("id",aEntity.medCase.id).setParameter("colorId",cipType.getId()).getResultList().isEmpty()) {
            var cip = new Packages.ru.ecom.mis.ejb.domain.patient.ColorIdentityPatient();
            cip.setVocColorIdentity(cipType);
            cip.setCreateUsername(username);
            cip.setStartDate(aEntity.medCase.dateStart);
            cip.setStartTime(aEntity.medCase.createTime);
            manager.persist(cip);
            aEntity.medCase.addColorsIdentity(cip);
            manager.persist(aEntity.medCase);
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

