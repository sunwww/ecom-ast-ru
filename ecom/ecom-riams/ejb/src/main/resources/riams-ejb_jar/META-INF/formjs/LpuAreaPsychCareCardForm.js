function onCreate(aForm, aEntity, aContext) {
    if (+aForm.ambulatoryCare > 0) {
        var observation = new Packages.ru.ecom.mis.ejb.domain.psychiatry.PsychiaticObservation();
        observation.setLpuAreaPsychCareCard(aEntity);
        observation.setCareCard(aEntity != null ? aEntity.careCard : null);
        observation.setStartDate(aEntity.startDate);
        var ambCard = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychAmbulatoryCare, aForm.ambulatoryCare);
        observation.setAmbulatoryCare(ambCard);
        if (+aForm.dispensaryGroup > 0) {
            var dispGroup = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychDispensaryGroup, aForm.dispensaryGroup);
            observation.setDispensaryGroup(dispGroup);
        }
        aContext.manager.persist(observation);
        aContext.manager.createNativeQuery("update LpuAreaPsychCareCard set transferDate=to_date('" + aForm.startDate + "','dd.mm.yyyy') where careCard_id='" + aForm.careCard + "' and id!='" + aEntity.id + "' and finishDate is null and transferDate is null").executeUpdate();
    }
    if (+aForm.stikeOffReason > 0) {
        aContext.manager.createNativeQuery("update PsychiatricCareCard set strikeOffReason_id='" + aForm.stikeOffReason + "',finishDate=to_date('" + aForm.finishDate + "','dd.mm.yyyy') where id='" + aForm.careCard + "'").executeUpdate();
    }
}

/**
 * После сохранения
 */
function onSave(aForm, aEntity, aContext) {
    if (+aForm.stikeOffReason > 0) {
        aContext.manager.createNativeQuery("update PsychiatricCareCard set strikeOffReason_id='" + aForm.stikeOffReason + "',finishDate=to_date('" + aForm.finishDate + "','dd.mm.yyyy') where id='" + aForm.careCard + "'").executeUpdate();
    }
}