
function onPreSave(aForm, aEntity, aCtx) {
    checkDouble(aForm.patient, aForm.createDate, aForm.id, aCtx);
    var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
    aForm.setWorkFunction(wf.id) ;
}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
    var date = new java.util.Date() ;
    aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
    aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
    aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
    var date = new java.util.Date() ;
    aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
    aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
    aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
    aEntity.setWorkFunction(wf) ;
}

function onPreCreate(aForm, aCtx) {
    /*Проверка - есть ли у пациента направление на это число*/
    checkDouble(aForm.patient, aForm.createDate, 0, aCtx);
}

function checkDouble(aPatientId, aDateCreate, aId, aCtx) {
    var sql = "select pre.id from WorkCalendarHospitalBed pre where pre.dtype='PlanOphtHospital' and pre.patient_id="+aPatientId+" and pre.createDate = current_date" ;
    if (+aId>0) sql +=" and pre.id!="+aId;
    if (!aCtx.manager.createNativeQuery(sql).getResultList().isEmpty()) {
        throw "У пациента уже создано направление на введение ингибиторов ангиогенеза в этот день, создание еще одного невозможно!";
    }
}