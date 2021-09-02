/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    var manager = aContext.manager;
    manager.createNativeQuery("update Covid19 set noActual='1' where patient_id=" + aForm.patient).executeUpdate();
    aEntity.setNoActual(false);
    var username = aContext.getSessionContext().getCallerPrincipal().getName();
    aEntity.setCreateUsername(username);
    if ("1".equals(aEntity.labResult)) { //создаем КВ браслет
        addBracelet(manager, "COVID19", aEntity, username);
    }
    if (true == aEntity.isPregnant) {
        addBracelet(manager, "PREGNANT", aEntity, username);
    }
    if (true == aEntity.isDoctor) {
        addBracelet(manager, "DOCTOR", aEntity, username);
    }
    //удалить старые браслеты по вакцинации
    deleteVacBraceletes(aEntity.medCase.id, aContext);
    //отметка о вакцинации
    if (aEntity.getVaccinated().getId()==1) {
        if (aEntity.getReVaccineСomponent().getId()==1)
            addBracelet(manager, "revac", aEntity, username);
        else if (aEntity.getSecondVaccineСomponent().getId()==1)
            addBracelet(manager, "vac2", aEntity, username);
        else if (aEntity.getFirstVaccineСomponent().getId()==1)
            addBracelet(manager, "vac1", aEntity, username);
    }
}

function deleteVacBraceletes(aMedCaseId, aContext) {
    var listBr = aContext.manager.createNativeQuery("select cip.id from ColorIdentityPatient cip" +
        " left join VocColorIdentityPatient vcip on vcip.id=cip.voccoloridentity_id" +
        " left join medcase_coloridentitypatient mcip on mcip.colorsidentity_id=cip.id" +
        " where mcip.medcase_id=" + aMedCaseId + " and vcip.code in('vac1','vac2','revac')").getResultList();
    for (var i=0; i<listBr.size(); i++) {
        var cId = +listBr.get(i);
        aContext.manager.createNativeQuery("update coloridentitypatient set finishdate=current_date, finishtime=current_time,editusername='"
            + aContext.getSessionContext().getCallerPrincipal().toString() + "' where id=" + cId).executeUpdate();
    }
}

function addBracelet(manager, code, aEntity, username) {
    var cipTypes = manager.createNamedQuery("VocColorIdentityPatient.getByCode")
        .setParameter("code", code).getResultList();
    if (!cipTypes.isEmpty()) {
        var cipType = cipTypes.get(0);
        //Только если нет браслета
        if (manager.createNativeQuery("select b.medcase_id  from medcase_coloridentitypatient b " +
            " left join colorIdentityPatient cip on cip.id=b.colorsidentity_id where b.medcase_id=:id and cip.voccoloridentity_id=:colorId" +
            " and cip.finishdate is null")
            .setParameter("id", aEntity.medCase.id).setParameter("colorId", cipType.getId()).getResultList().isEmpty()) {
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
        " =(select patient_id from covid19 c2 where id=" + aEntityId + ") and c.id!=" + aEntityId).getResultList();
    var sql;
    if (list.isEmpty()) { //remove contacts
        sql = "delete from Covid19Contact where card_id=" + aEntityId;
    } else {
        sql = "update Covid19Contact set card_id=" + list.get(0) + " where card_id=" + aEntityId;
    }
    manager.createNativeQuery(sql).executeUpdate();
}