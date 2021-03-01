function checkDouble(aForm, aCtx) {
    var serviceId = aForm.getId();
    var code = ("" + aForm.getCode());
    if ("" + aForm.finishDate == "" && code != "") {
        var manager = aCtx.manager;
        var sql = "select id from medservice where upper(code)=trim(upper('" + code + "')) and finishDate is null";
        if (+serviceId > 0) {
            sql += " and id!=" + serviceId;
        }
        var list = manager.createNativeQuery(sql).getResultList();
        if (list.size() > 0) {
            throw "Услуга с таким кодом существует: <a target='_blank' href='entityParentView-mis_medService.do?id=" + list.get(0) + "'>Просмотреть</a>";
        }
    }

}

function onPreCreate(aForm, aCtx) {
    checkDouble(aForm, aCtx);
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    checkPeriod(aForm.startDate, aForm.finishDate);
    checkUsl(aForm.serviceType, aForm.vocMedService, aForm);
}

function onCreate(aForm, aEntity, aContext) {
    updateParent(0, aForm.parent, aContext, aEntity.startDate, aEntity.finishDate);
}

function onPreSave(aForm, aEntity, aContext) {
    checkDouble(aForm, aContext);
    checkPeriod(aForm.startDate, aForm.finishDate);
    checkUsl(aForm.serviceType, aForm.vocMedService, aForm);
}

function onSave(aForm, aEntity, aContext) {
    updateParent(aForm.id, aForm.parent, aContext, aEntity.startDate, aEntity.finishDate);
}

function checkUsl(aServiceType, aMedService, aForm) {
    if (+aMedService > 0 && aServiceType == 0) throw "При указании услуги необходимо указывать ее тип!!!";
}

function checkPeriod(aStartDate, aFinishDate) {
    var startDate = null;
    var finishDate = null;
    try {
        startDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aStartDate);
        finishDate = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aFinishDate);
    } catch (e) {
        startDate = null;
        finishDate = null;
    }
    if (startDate != null && finishDate != null)
        if (startDate.getTime() > finishDate.getTime()) throw "Дата начала актуальности должна быть больше, чем дата окончания";
}

function updateParent(aId, aParent, aCtx, aStartDate, aFinishDate) {
    if (aParent != null && +aParent > 0) {
        var sql = "select "
            + " (select ms1.parent_id from MedService ms1 where ms1.id=" + aParent + ") "
            + " , min(ms.startDate) "
            + " , (select count(*) from MedService ms2 where ms2.parent_id = "
            + aParent + " and ms2.startDate is null and ms2.id!=" + aId + ") "
            + " , max(ms.finishDate) "
            + " , (select count(*) from MedService ms3 where ms3.parent_id = "
            + aParent + " and ms3.finishDate is null and ms3.id!=" + aId + ")  "
            + " from MedService as ms where ms.parent_id = " + aParent + " and id!= " + aId;
        var list = aCtx.manager.createNativeQuery(sql).getSingleResult();
        // Обновление начала даты актуальности
        if (+list[2] > 0 || aStartDate == null) {
            aCtx.manager.createNativeQuery("update MedService set startDate=null where id=" + aParent)
                .executeUpdate();
        } else {
            if (list[1] != null && list[1].getTime() < aStartDate.getTime()) {
                aCtx.manager.createNativeQuery("update MedService set startDate=:sdt where id=" + aParent)
                    .setParameter("sdt", list[1]).executeUpdate();
            } else {
                aCtx.manager.createNativeQuery("update MedService set startDate=:sdt where id=" + aParent)
                    .setParameter("sdt", aStartDate).executeUpdate();
            }
        }
        // Обновление окончания даты актуальности
        if (+list[4] > 0 || aFinishDate == null) {
            aCtx.manager.createNativeQuery("update MedService set finishDate=null where id=" + aParent)
                .executeUpdate();
        }
        if (+list[0] > 0) {
            updateParent(aId, +list[0], aCtx, aStartDate, aFinishDate);
        }
    }
}