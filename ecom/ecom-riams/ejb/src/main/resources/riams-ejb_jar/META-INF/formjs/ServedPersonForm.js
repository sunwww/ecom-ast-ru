/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aCtx) {
    var account = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccount();
    if (aEntity.autoAccount != null && aEntity.autoAccount == true) {
        account.setServedPerson(aEntity);
        account.setDateFrom(aEntity.dateFrom);
        account.setDateTo(aEntity.dateTo);
        aCtx.manager.persist(account);
    }
}

/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setEditTime(new java.sql.Time(date.getTime()));
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
}