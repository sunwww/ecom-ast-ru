function onPreSave(aForm, aEntity, aCtx) {
    checkDouble(aForm.patient, aForm.createDate, aForm.id, aCtx);
    var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction");
    aForm.setWorkFunction(wf.id);
}

/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
    if (!chekOphtDayEnabled(aForm, aCtx))
        throw "Этот день недоступен для предварительной госпитализации в офтальмологическое отделение!";
    checkPatientCountPerDay(aForm, aCtx);
    var date = new java.util.Date();
    aEntity.setEditDate(new java.sql.Date(date.getTime()));
    aEntity.setEditTime(new java.sql.Time(date.getTime()));
    aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
}

/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    aEntity.setCreateDate(new java.sql.Date(date.getTime()));
    aEntity.setCreateTime(new java.sql.Time(date.getTime()));
    aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction");
    aEntity.setWorkFunction(wf);
}

function onPreCreate(aForm, aCtx) {
    if (!chekOphtDayEnabled(aForm, aCtx))
        throw "Этот день недоступен для предварительной госпитализации в офтальмологическое отделение!";
    /*Проверка - есть ли у пациента направление на это число*/
    checkDouble(aForm.patient, aForm.createDate, 0, aCtx);
    checkPatientCountPerDay(aForm, aCtx);
}

function checkDouble(aPatientId, aDateCreate, aId, aCtx) {
    var sql = "select pre.id from WorkCalendarHospitalBed pre where pre.dtype='PlanOphtHospital' and pre.patient_id=" + aPatientId + " and pre.createDate = current_date";
    if (+aId > 0) sql += " and pre.id!=" + aId;
    if (!aCtx.manager.createNativeQuery(sql).getResultList().isEmpty()) {
        throw "У пациента уже создано направление на введение ингибиторов ангиогенеза в этот день, создание еще одного невозможно!";
    }
}

//Проверка на количество предв. госпитализаций в день
function checkPatientCountPerDay(aForm, aCtx) {
    if (aForm.dateFrom && typeof aForm.dateFrom !== 'undefined') {
        var sql = "select count(pre.id)" +
            " from WorkCalendarHospitalBed pre" +
            " left join mislpu lpu on lpu.id=pre.department_id" +
            " where pre.datefrom = to_date('" + aForm.dateFrom + "','dd.MM.yyyy')" +
            " and lpu.id='" + aForm.department + "' and lpu.isophthalmic";
        var cnt = +aCtx.manager.createNativeQuery(sql).getResultList().get(0);
        var maxCntList = aCtx.manager
            .createNativeQuery("select keyvalue from softconfig  where key='patientCountPreHospPerDay'")
            .getResultList();
        var maxCnt = maxCntList.isEmpty() ? 20 : +maxCntList.get(0);
        if (cnt >= maxCnt)
            if (cnt >= maxCnt)
                throw "На этот день в этом отделении уже создано максимально допустимое количество ("
                + maxCnt + ")  предварительных госпитализаций. Выберите другую дату.";
    }
}

//проверка на доступность дня для плановой госпит. (для офт. отд.)
function chekOphtDayEnabled(aForm, aCtx) {
    var sql = "select id from mislpu where id=" + aForm.department + " and isophthalmic";
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    if (list.size() > 0) { //офтальмология
        sql = "select day from datepreopht where day=to_date('" + aForm.dateFrom + "','dd.MM.yyyy')";
        list = aCtx.manager.createNativeQuery(sql).getResultList();
        return list.isEmpty();
    }
    return true;
}