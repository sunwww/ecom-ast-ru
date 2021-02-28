/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aCtx) {
    if (!aCtx.manager.createNativeQuery("select id from e2listentry where id=" + aEntityId + " and isClosed='1'").getResultList().isEmpty()) {
        throw "Невозможно удалить заполнение, оно закрыто. Для удаления откройте заполнение.";
    }
    aCtx.manager.createNativeQuery("update e2entry set isDeleted='1' where listentry_id=" + aEntityId).executeUpdate();
}