/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {

    var pms = aForm.getMedService();
    if (+pms > 0) {
        var ms = aContext.manager.createNativeQuery("select medservice_id from pricemedservice where id=" + pms).getResultList().get(0);
        if (ms != null && +ms > 0) aEntity.setServiceIn(+ms);
    }
}