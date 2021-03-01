function onPreSave(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    // Проверка введенных данных
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
}