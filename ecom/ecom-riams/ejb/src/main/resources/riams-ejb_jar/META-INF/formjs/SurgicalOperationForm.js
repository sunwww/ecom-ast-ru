function onCreate(aForm, aSurgOper, aCtx) {
    if (aSurgOper.getMedCase().getPatient() != null) {
        aSurgOper.patient = aSurgOper.getMedCase().getPatient();
    } else {
        if (aSurgOper.getMedCase().getParent() != null && aSurgOper.getMedCase().getParent().getPatient() != null)
            aSurgOper.patient = aSurgOper.getMedCase().getParent().getPatient();
    }
    if (+aForm.isAnesthesia > 0) {
        var ch = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocYesNo, aForm.isAnesthesia);
        if (ch != null && ch.code.equals("1")) {
            if (+aForm.anaesthetist > 0 && +aForm.anesthesia > 0 && +aForm.anesthesiaType > 0 && +aForm.anesthesiaDuration > 0) {
                var anes = new Packages.ru.ecom.mis.ejb.domain.medcase.Anesthesia();
                var anesthetist = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction
                    , aForm.anaesthetist);
                var anesthesia = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesiaMethod
                    , aForm.anesthesia);
                var anesthesiaType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia
                    , aForm.anesthesiaType);
                var anesthesiaService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService
                    , aForm.anesthesiaService);
                anes.setMethod(anesthesia);
                anes.setType(anesthesiaType);
                anes.setMedService(anesthesiaService);
                anes.setAnesthesist(anesthetist);
                anes.setDuration(+aForm.anesthesiaDuration);
                anes.setSurgicalOperation(aSurgOper);
                var date = new java.util.Date();
                anes.setCreateDate(new java.sql.Date(date.getTime()));
                anes.setCreateTime(new java.sql.Time(date.getTime()));
                anes.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());

                aCtx.manager.persist(anes);
            } else {
                throw "Не заполнены данные по анастезии";
            }
        }
    }
    if (+aForm.medImplantType>0) { //создаем мед. имплант
        var medImplant = new Packages.ru.ecom.mis.ejb.domain.medcase.SurgicalImplant();
        medImplant.setOperation(aSurgOper);
        medImplant.setSerialNumber(aForm.getMedImplantSerialNumber());
        medImplant.setType(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocSurgicalImplant, aForm.medImplantType));
        aCtx.manager.persist(medImplant);

    }
    checkParent(aSurgOper, aCtx); //Находим родителя по дате и времени операции
    saveComplications(aForm, aSurgOper, aCtx);
    createBraceletIfNeed(aForm, aSurgOper, aCtx);
}

function onPreSave(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    checkPeriod(aForm, aCtx);
}

function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    checkPeriod(aForm, aCtx);
    if (+aForm.isAnesthesia > 0) {
        var ch = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocYesNo, aForm.isAnesthesia);
        if (ch != null && ch.code.equals("1")) {
            if (+aForm.anaesthetist > 0 && +aForm.anesthesia > 0 && +aForm.anesthesiaType > 0 && +aForm.anesthesiaDuration > 0) {

            } else {
                throw "Необходимо заполнить все обязательные поля по анестезии!!!";
            }
        }
    } else {
        throw "Необходимо указать была анестезия или нет!!!";
    }

    var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService, aForm.medService);
    if (medService.isAbortRequired && true == medService.isAbortRequired && !+aForm.abortion > 0) {
        throw "Для операции " + medService.code + " " + medService.name + " необходимо заполнить тип аборта!";
    }
}


function checkPeriod(aForm, aCtx) {
    aManager = aCtx.manager;
    var operDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.operationDate);
    var medCase = aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
        , aForm.medCase);
    if (medCase.dateStart.getTime() > operDate.getTime()) {
        throw "Дата операции " + Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(operDate) + " должна быть больше или равна началу СМО "
        + Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(medCase.dateStart);
    }
    var l = aManager.createNativeQuery("select vlaeo.id from mislpu ml left join VocLpuAccessEnterOperation vlaeo on vlaeo.id=ml.accessEnterOperation_id where ml.id='"
        + aForm.department + "' and (vlaeo.code='NOT_SURGICAL_DEPARTMENT' or vlaeo.code='ALL_DEPARTMENT')").getResultList();
    if (l.size() > 0) throw "Запрет в отделение на регистрацию, что в нем проводилась операция!!!"

}

function onSave(aForm, aEntity, aCtx) {
    checkParent(aEntity, aCtx);
    aCtx.manager.createNativeQuery("delete from surgcomplication where surgicaloperation_id=" + aEntity.id).executeUpdate();
    saveComplications(aForm, aEntity, aCtx);
    checkBraceleteAndClose(aForm, aEntity, aCtx);
}

function checkParent(aEntity, aCtx) {
    //Находим родителя по дате и времени операции
    Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SurgicalOperationCreateInterceptor.setParentByOperation(aEntity, aCtx.manager);
}

function getMedCaseType(aId, aCtx) {
    var list = aCtx.manager.createNativeQuery("select dtype from medcase where id=" + aId).getResultList();
    return list.size() > 0 ? "" + list.get(0) : "";
}

function onPreDelete(aEntityId, aCtx) {
    var so = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.SurgicalOperation, new java.lang.Long(aEntityId));
    var dtype = getMedCaseType(so.getMedCase().getId(), aCtx);
    if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/EditAfterOut")
        && (dtype == 'DepartmentMedCase' || dtype == 'HospitalMedCase')) {
        var parent = (dtype == 'DepartmentMedCase') ? so.getMedCase().getParent() : so.getMedCase();
        if (parent.getDateFinish() != null) throw "Пациент выписан. Нельзя удалять операцию в закрытом СЛС!";
    }
    //очищение в ContractAccountOperationByService, чтобы потом вновь можно было увидеть операцию в списке оплаченных
    aCtx.manager.createNativeQuery("update ContractAccountOperationByService set serviceid=null where serviceid=" + aEntityId).executeUpdate();
    //удаление осложнений
    aCtx.manager.createNativeQuery("delete from surgcomplication where surgicaloperation_id=" + aEntityId).executeUpdate();
    //если есть браслет, связанный с операцией
    var list = aCtx.manager.createNativeQuery("select id from coloridentitypatient where surgoperation_id=" + aEntityId).getResultList();
    if (list.size() > 0) {
        var aColorIdentityId = list.get(0);
        aCtx.manager.createNativeQuery("update coloridentitypatient set surgoperation_id=null,finishdate=current_date, finishtime=current_time,editusername='"
            + aCtx.getSessionContext().getCallerPrincipal().toString() + "' where id=" + aColorIdentityId).executeUpdate();
    }
}

//сохранение осложнений
function saveComplications(aForm, aEntity, aCtx) {
    var allComps = aForm.getAllComps();
    if (allComps != '') {
        var obj = new Packages.org.json.JSONObject(allComps);
        var comps = obj.getJSONArray("list");
        for (var i = 0; i < comps.length(); i++) {
            var child = comps.get(i);
            var surgComp = new Packages.ru.ecom.mis.ejb.domain.medcase.SurgComplication();
            surgComp.setSurgicalOperation(aEntity);
            surgComp.setComplicationString('' + child.get("compString"));
            surgComp.setCompReasonString('' + child.get("reasonString"));

            var format = new java.text.SimpleDateFormat("dd.MM.yyyy");
            surgComp.setDateComp(new java.sql.Date(format.parse(java.lang.String.valueOf(child.get("date"))).getTime()));

            var vocCompId = java.lang.Long.valueOf(child.get("comp"));
            var vocComp = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocComplication, vocCompId);

            if (vocComp != null) {
                surgComp.setComplication(vocComp);
                aCtx.manager.persist(surgComp);
            }
        }
    }
}

//Проверка, нужно ли создавать браслет и, если нужно, его создание
function createBraceletIfNeed(aForm, aSurgOper, aCtx) {
    if (aForm.operationDateTo == '' && aForm.operationTimeTo == '') {  //если не стоят дата-время окончания
        var list = aCtx.manager.createNativeQuery("select vocColorIdentity_id from medservice where id=" + aForm.medService + " and vocColorIdentity_id is not null").getResultList();
        if (list.size() > 0) {  //если есть браслет у услуги
            var idB = new java.lang.Long(list.get(0));
            var cip = new Packages.ru.ecom.mis.ejb.domain.patient.ColorIdentityPatient();
            cip.setVocColorIdentity(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient, idB));
            cip.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
            cip.setStartDate(aSurgOper.operationDate);
            cip.setStartTime(aSurgOper.operationTime);
            cip.setSurgOperation(aSurgOper);
            aCtx.manager.persist(cip);

            aSurgOper.medCase.parent.addColorsIdentity(cip);
            aCtx.manager.persist(aSurgOper.medCase.parent);
        }
    }
}

//Проверка, нужно ли закрывать браслет (если есть браслет у услуги и проставили дату-время окончания
function checkBraceleteAndClose(aForm, aEntity, aCtx) {
    var list = aCtx.manager.createNativeQuery("select id from ColorIdentityPatient where surgoperation_id=" + aEntity.id).getResultList();
    if (list.size() > 0 && list.get(0) != null) {  //если есть браслет с такой id операции (который может быть уже снят, но дату-время окончания операции могут менять
        var idB = new java.lang.Long(list.get(0));
        if (aForm.operationDateTo != '' && aForm.operationTimeTo != '') {  //если стоят дата-время окончания
            aCtx.manager.createNativeQuery("update ColorIdentityPatient set editusername='" +
                aCtx.getSessionContext().getCallerPrincipal().toString() + "',finishdate = '" + aEntity.operationDateTo
                + "', finishtime = '" + aEntity.operationTimeTo + "' where id=" + idB).executeUpdate();
        } else { //если убрали дату-время редактирования, браслет возвращается
            aCtx.manager.createNativeQuery("update ColorIdentityPatient set editusername='" +
                aCtx.getSessionContext().getCallerPrincipal().toString() + "',finishdate = null, finishtime = null where id=" + idB).executeUpdate();
        }
    }
}