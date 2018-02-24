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

function checkDoubleByPricePosition(aForm, aCtx) {
    var sql = "select id from pricemedservice where priceposition_id =" + aForm.getPricePosition() + " and dateTo is null";
    var id = +aForm.getId();
    if (id > 0) {
        sql += " and id!=" + id;
    }
    if (aCtx.manager.createNativeQuery(sql).getResultList().size() > 0) {
        throw "У позиции прейскуранта уже есть прикрепление к услуге!";
    }
}


