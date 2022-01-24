/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    aEntity.setEditDate(new java.sql.Date(date.getTime()));
    aEntity.setEditTime(new java.sql.Time(date.getTime()));
    aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().getName());
}

function onPreDelete(id, ctx) {
    ctx.manager.createNativeQuery("delete from prescriptionfulfilment where prescription_id='" + id + "'").executeUpdate();
}

/**
 * Перед созданием
 */
function onCreate(aForm, aEntity, aCtx) {
    var date = new java.util.Date().getTime();
    aEntity.setCreateDate(new java.sql.Date(date));
    aEntity.setCreateTime(new java.sql.Time(date));
    var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction");
    aEntity.setPrescriptSpecial(wf);
    aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().getName());
    var ffForm = aForm.fulfilmentForm;
    if (ffForm != null && ffForm.getFulfilDate() != null) { //time to create fulfilment
        var ff = new Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptionFulfilment();
        ff.setPrescription(aEntity);
        ff.setFulfilDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(ffForm.getFulfilDate()));
        ff.setFulfilTime(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(ffForm.getFulfilTime()));
        ff.setExecutorWorkFunction(aEntity.getPrescriptSpecial());
        ff.setComments(ffForm.comments);
        aCtx.manager.persist(ff);
    }
}