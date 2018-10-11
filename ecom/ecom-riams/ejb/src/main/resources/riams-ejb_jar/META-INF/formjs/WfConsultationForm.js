function onPreSave(aForm,aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setEditTime(new java.sql.Time (date.getTime())) ;
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
    aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onCreate(aForm,aEntity, aCtx) {
    //Milamesher 11102018
    //Плановые - автоматически переданные при создании
    if (aEntity.vocConsultingType!=null) {
        var res = aCtx.manager.createNativeQuery("select case when t.code='plan' then '1' else '0' end \n" +
            "from vocconsultingtype t\n" +
            "where t.id=" + aEntity.vocConsultingType.id).getResultList();
        if (res.size() > 0) {
            if (res.get(0) == "1") {
                var date = new java.util.Date();
                aEntity.setTransferDate(new java.sql.Date(date.getTime())) ;
                aEntity.setTransferTime(new java.sql.Time (date.getTime())) ;
                aEntity.setTransferUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
                aCtx.manager.persist(aEntity) ;
            }
        }
    }
}
function onPreDelete(aEntityId, aContext) {
    var res = aContext.manager.createNativeQuery("select case when transferdate is null then '1' else '0' end from prescription where id=" + aEntityId).getResultList();
    if (res.size()>0) {
        if (res.get(0) == "0") {
            throw "Назначение, которое уже было передано специалисту, удалять нельзя!";
        }
    }
}