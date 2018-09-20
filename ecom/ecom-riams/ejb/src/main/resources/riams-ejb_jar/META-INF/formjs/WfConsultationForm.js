function onPreSave(aForm,aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    var res = aCtx.manager.createNativeQuery("select case when transferdate is null then '1' else '0' end from prescription where id=" + aEntity.id).getResultList();
    if (res.size()>0) {
        if (res.get(0) == "0") {
            throw "Назначение, которое уже было передано специалисту, редактировать нельзя!";
        }
    }
}
function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreDelete(aEntityId, aContext) {
    var res = aContext.manager.createNativeQuery("select case when transferdate is null then '1' else '0' end from prescription where id=" + aEntityId).getResultList();
    if (res.size()>0) {
        if (res.get(0) == "0") {
            throw "Назначение, которое уже было передано специалисту, удалять нельзя!";
        }
    }
}