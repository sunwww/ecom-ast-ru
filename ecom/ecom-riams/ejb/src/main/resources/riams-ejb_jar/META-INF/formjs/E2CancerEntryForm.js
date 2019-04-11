/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    if (aForm.getDirectionDate()!=null && aForm.getDirectionDate()!="") {
        //Создаем направление
        var direction = new Packages.ru.ecom.expert2.domain.E2CancerDirection(aEntity);
        direction.setDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getDirectionDate()));
        direction.setType(aForm.getDirectionType());
        direction.setSurveyMethod(aForm.getDirectionSurveyMethod());
        direction.setMedService(aForm.getDirectionMedService());
        direction.setDirectLpu(aForm.getDirectionDirectLpu());
        aContext.manager.persist(direction);
    }

    if (aForm.getRefusalDate()!=null&& aForm.getRefusalDate()!="") {
        //Создаем противопоказание
        var refusal = new Packages.ru.ecom.expert2.domain.E2CancerRefusal(aEntity);
        refusal.setDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getRefusalDate()));
        refusal.setCode(aForm.getRefusalCode());
        aContext.manager.persist(refusal);
    }

    if (aForm.getDiagnosticType()!=null&& aForm.getDiagnosticType()!="") {
        //Создаем диагностический блок
        var diag = new Packages.ru.ecom.expert2.domain.E2CancerDiagnostic(aEntity);
        diag.setType(aForm.getDiagnosticType());
        diag.setCode(aForm.getDiagnosticCode());
        diag.setResult(aForm.getDiagnosticResult());
        aContext.manager.persist(diag);
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
    aContext.manager.createNativeQuery("delete from e2cancerrefusal where cancerentry_id="+aEntityId).executeUpdate();
    aContext.manager.createNativeQuery("delete from e2cancerdiagnostic where cancerentry_id="+aEntityId).executeUpdate();
    aContext.manager.createNativeQuery("delete from e2cancerdirection where cancerentry_id="+aEntityId).executeUpdate();

}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {

}

