/**
 * @author stkacheva
 */
function onPreCreate(aForm, aCtx) {
    checkNumSerSmo(aCtx, aForm, "");
    if (+aForm.type == 3 && aForm.commonNumber.trim() == "") {
        throw "При заполнение нового полиса поле ЕДИНЫЙ НОМЕР (RZ) является ОБЯЗАТЕЛЬНЫМ!!!";
    }
    aForm.setConfirmationDate("");
}

function onPreSave(aForm, aEntity, aCtx) {
    if (+aForm.type == 3 && aForm.commonNumber.trim() == "") {
        throw "При заполнение нового полиса поле ЕДИНЫЙ НОМЕР (RZ) является ОБЯЗАТЕЛЬНЫМ!!!";
    }
    var add = " and id!=" + aForm.id;
    checkNumSerSmo(aCtx, aForm, add);
    aForm.setConfirmationDate("");
}

function onSave(aForm, aEntity, aCtx) {
    if (aEntity.commonNumber != null && aEntity.commonNumber != '') {
        aEntity.patient.setCommonNumber(aEntity.commonNumber);
    }
}

function onCreate(aForm, aEntity, aCtx) {
    if (aEntity.commonNumber != null && aEntity.commonNumber != '') {
        aEntity.patient.setCommonNumber(aEntity.commonNumber);
    }
}

function errorThrow(aList, aError) {
    if (aList.size() > 0) {
        var error = ":";
        for (var i = 0; i < aList.size(); i++) {
            var doc = aList.get(i);
            if (doc.patient != null) {
                error = error + " <br/><a href='entitySubclassView-mis_medPolicy.do?id=" + doc.id + "'>"
                error = error + " ПЕРСОНА:" + doc.patient.lastname + " " + doc.patient.firstname;
                error = error + " ПОЛИС: " + doc.text + "</a><br/>";
            } else {
                error = error + " <br/><a href='entitySubclassView-mis_medPolicy.do?id=" + doc.id + "'>" + "ОШИБКА !!! НЕ УКАЗАНА ПЕРСОНА!!! УДАЛИТЕ ЭТОТ ПОЛИС И ЗАНОВО СОЗДАЙТЕ ЕГО" + "</a><br/>"
            }
        }
        throw aError + error;
    }
}

function checkNumSerSmo(aCtx, aForm, aSqlAdd) {
    var number = aForm.polNumber;
    var series = aForm.series;
    var smo = aForm.company;
    var dateFrom, dateTo;
    try {
        dateFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateFrom);
    } catch (e) {
        throw "Неправильно введена дата начала";
    }
    try {
        dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateTo);
    } catch (e) {
        dateTo = null;
        //throw "Неправильно введена дата начала или окончания" ;
    }
    if (dateFrom == null) throw "Поле даты начала действия полиса является обязательным";
    if (dateTo != null && (dateTo.getTime() < dateFrom.getTime())) throw "Дата окончания должна быть больше, чем дата начала";

    var policies = aCtx.manager.createQuery("from MedPolicyOmc where"
        + " series=:series and polnumber=:number and company_id=:company" + aSqlAdd
    )
        .setParameter("series", series)
        .setParameter("number", number)
        .setParameter("company", smo)
        .getResultList();
    errorThrow(policies, "В базе уже существует полис с такой серией, номером и страховой компанией");
}

function onPreDelete(aEntityId, aCtx) {
    var manager = aCtx.manager;
    if (!manager.createNativeQuery("select id from medcase_medpolicy mm where policies_id=" + aEntityId).getResultList().isEmpty()) {
        throw "К полису прикреплены случаи лечения в стационаре, необходимо удалить полис из случая";
    }
    var list = aCtx.manager.createNativeQuery("select patient_id,dtype from medpolicy where id='" + aEntityId + "'").setMaxResults(1).getResultList();
    if (list.size() > 0) {
        aCtx.manager.createNativeQuery("update Patient set attachedOmcPolicy_id=null where id=" + list.get(0)[0]).executeUpdate();
    }
    aCtx.manager.createNativeQuery("update patientattachedimport set medpolicy_id=null where medpolicy_id=" + aEntityId).executeUpdate();
}