/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
    var date = aForm.date;
    if (date!=='') {
        var dateDrug = new Packages.ru.ecom.expert2.domain.E2CancerDrugDate(aEntity);
        dateDrug.setDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(date));
        aContext.manager.persist(dateDrug);
    }
}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
}

