/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
    if (!aContext.manager.createNativeQuery("select list.id from e2listentry list where list.id=" + aEntity.getListEntry().getId() + " and list.isClosed='1'").getResultList().isEmpty()) {
        throw "Заполнение закрыто, редактирование записи невозможно!";
    }
    if (aForm.getBillNumber() == null || aForm.getBillNumber() == "") {
        aForm.setBill(null);
    }
}