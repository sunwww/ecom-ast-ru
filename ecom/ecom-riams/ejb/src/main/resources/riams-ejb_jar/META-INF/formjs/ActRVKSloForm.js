/**
 * Перед созданием
 */
function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date() ;
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    var pat = aCtx.manager.createNativeQuery("select patient_id,department_id from medcase where id="+aForm.getMedCase()).getResultList();
    if (!pat.isEmpty()) {
        aForm.setPatient(java.lang.Long.valueOf(pat.get(0)[0]));
        aForm.setDepartment(java.lang.Long.valueOf(pat.get(0)[1]));
    }
    var wf=getWorkFunction(aCtx);
    if (typeof wf[0]!=='undefined')
        aForm.setWorkFunctionStart(wf[0]);
    if (typeof wf[1]!=='undefined')
        aForm.setSpecName(wf[1]);
}

// Получить рабочую функцию пользователя
function getWorkFunction(aCtx) {
    var wf=[];
    var specP = aCtx.manager.createNativeQuery("select wf.id as wfid,vwf.id as vwfid from secuser su left join workFunction wf on wf.secuser_id=su.id "
        + "left join vocworkfunction vwf on vwf.id=wf.workfunction_id where su.login='"
        + aCtx.getSessionContext().getCallerPrincipal().toString()+"'").getResultList();
    if (!specP.isEmpty()) {
        wf[0]=java.lang.Long.valueOf(specP.get(0)[0]);
        wf[1]=java.lang.Long.valueOf(specP.get(0)[1]);
    }
    return wf;
}

//проверка на соответствиее vocworkfunction
function checkSpecName(aForm, aCtx) {
    var wf=getWorkFunction(aCtx);
    if (typeof wf[1]!=='undefined' && wf[1]!=java.lang.Long.valueOf(aForm.getSpecName()))
        throw 'Редактировать акт РВК можно пользователь с той же рабочей функцией, что и пользователь, создавший его!';
}

function onPreSave(aForm, aEntity, aCtx) {
    checkDates(aForm);
    checkSpecName(aForm,aCtx);
    checkPatientDischarged(aForm.getMedCase(),aCtx);
    var date = new java.util.Date() ;
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
    var wf=getWorkFunction(aCtx);
    if (typeof wf[0]!=='undefined')
        aEntity.setWorkFunctionFinish(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,wf[0]));
}


//проверка на редактирование акта РВК выписанного пациента
function checkPatientDischarged(aSloId,aCtx) {
    var list = aCtx.manager.createNativeQuery("select case when sls.datefinish is null then '0' else '1' end" +
        " from medcase slo" +
        " left join medcase sls on sls.id=slo.parent_id" +
        " where sls.dtype='PolyclinicMedCase'" +
        " and slo.id=" + aSloId).getResultList();
    if (!list.isEmpty())
        if (list.get(0)==1)
            throw 'Нельзя редактировать акт РВК после выписки пациента!';
}

//дата закрытия >=даты открытия акта
function checkDates(aForm) {
    var theStartDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getDateStart()) ;
    var theFinishDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getDateFinish()) ;
    if (theStartDate!=null && theFinishDate!=null && theStartDate.getTime() > theFinishDate.getTime())
        throw 'Дата закрытия акта РВК не может быть меньше даты открытия!';
}

/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {

}

function onPreDelete(aEntityId, aCtx) {

}