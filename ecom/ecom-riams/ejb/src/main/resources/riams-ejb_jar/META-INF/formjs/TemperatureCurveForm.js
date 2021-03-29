function onPreSave(aForm, aEntity, aCtx) {
    // var takingDate = '' + aForm.getTakingDate();
    //
    // if (takingDate == getNowDateWithoutTimeAndTimezone(new java.text.SimpleDateFormat("dd.MM.yyyy"))
    //     && checkIsLast(aEntity.id, aCtx)) {
    //     var date = new java.util.Date();
    //     aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    //     aForm.setEditTime(new java.sql.Time(date.getTime()));
    //     aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    // } else
    //     throw("Можно редактировать только последний температурный лист за текущий день!");
    throw "Редактирование запрещено";
}

function onPreDelete(aEntityId, aCtx) {
    var temp = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.hospital.TemperatureCurve, new java.lang.Long(aEntityId));
    var takingDate = '' + temp.takingDate;

    if (takingDate != getNowDateWithoutTimeAndTimezone(new java.text.SimpleDateFormat("yyyy-MM-dd"))
        || !checkIsLast(aEntityId, aCtx))
        throw("Можно удалять только последний температурный лист за текущий день!");
    else
        checkBraceleteAndClose(aEntityId, aCtx);
}

//является ли лист aEntityId последниv темп. лист в случае
function checkIsLast(aEntityId, aCtx) {
    var list = aCtx.manager.createNativeQuery("select max(id) from temperaturecurve " +
        "where medcase_id=(select medcase_id from temperaturecurve where id=" + aEntityId + " limit 1)").setMaxResults(1).getResultList();
    if (!list.isEmpty())
        if (list.get(0) != aEntityId)
            return false;
    return true;
}

function getNowDateWithoutTimeAndTimezone(format2) {
    var format = new java.text.SimpleDateFormat("yyyy-MM-dd");

    var now = format.parse(format.format(new java.util.Date()));
    var nowDate = format2.format(now);
    return nowDate;
}

//Проверка, нужно ли закрывать браслет
function checkBraceleteAndClose(aEntityId, aCtx) {
    var list = aCtx.manager.createNativeQuery("select id from ColorIdentityPatient where tempcurve_id=" + aEntityId).getResultList();
    if (list.size() > 0) {
        if (list.get(0) != null) { //если есть браслет с таким id темп. листа
            var idB = new java.lang.Long(list.get(0));
            aCtx.manager.createNativeQuery("update ColorIdentityPatient set editusername='" +
                aCtx.getSessionContext().getCallerPrincipal().toString() + "',finishdate = current_date"
                + ", finishtime = current_time where id=" + idB).executeUpdate();
        }
    }
}