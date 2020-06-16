function onPreDelete(aEntityId, aCtx) {
    var l = aCtx.manager.createNativeQuery("select username,to_char(dateRegistration,'dd.mm.yyyy') as dater,cast(timeRegistration as varchar(5)) as timer from diary where id=" + aEntityId)
        .getResultList();
    if (!l.isEmpty()) {
        var obj = l.get(0);
        if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/DisableDeleteOnlyTheir")
            && aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/EnableDeleteOnlyTheir")) {
            if (aCtx.getSessionContext().getCallerPrincipal().getName() != ("" + obj[0])) {
                throw "У Вас стоит запрет на удаление протоколов (дневников специалиста) других специалистов!";
            }
            var curDate = java.util.Calendar.getInstance();
            var maxVisit = java.util.Calendar.getInstance();
            var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(obj[1], obj[2]);
            maxVisit.setTime(dateVisit);
            maxVisit.add(java.util.Calendar.HOUR, 24);
            if (curDate.after(maxVisit)) {
                throw "У Вас стоит запрет на удаление протоколов (дневников специалиста) спустя сутки!";
            }
        }
    } else {
        throw "В БД нет протокола с таким ИД!!!";
    }

    aCtx.manager.createNativeQuery("delete from forminputprotocol where docprotocol_id=" + aEntityId).executeUpdate();
    //Milamesher #121 очищение данных в конслультации об этом дневнике: сам diary_id,и кто-когда-во сколько
    var res = aCtx.manager.createNativeQuery( "select  scg.id from prescription scg" +
        " left join PrescriptionList pl on pl.id=scg.prescriptionList_id" +
        " left join medcase slo on slo.id=pl.medcase_id" +
        " left join workfunction wf on wf.id=scg.prescriptcabinet_id" +
        " where scg.diary_id='" + aEntityId + "' and scg.transferdate is not null").getResultList();
    if (!res.isEmpty()) {
        if (res.get(0)!=null) {
            var presc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.Prescription,java.lang.Long.valueOf(res.get(0)));
            presc.setDiary(null);
            presc.setIntakeSpecial(null);
            presc.setIntakeDate(null);
            presc.setIntakeTime(null);
            aCtx.manager.persist(presc);
        }
    }
}
function onPreCreate(aForm, aCtx) {

	var date = new java.util.Date() ;
	aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().getName()) ;
	aForm.setTime(new java.sql.Time (date.getTime())) ;
	if (aForm.editUsername!=null && aForm.editUsername!="" && !aForm.username.equals(aForm.editUsername)) {
		//aCtx.manager.createNativeQuery("insert into ChangeJournal (classname,changedate,changetime,SerializationBefore,objectid) values ('VISITPROTOCOL',current_date,current_time,'"+aForm.username+"- -"+aForm.editUsername+"','"+aForm.medCase+"')").executeUpdate() ;
		throw "Не удалось сохранить протокол: <br/><pre>"+aForm.record+"</pre><br/> Попробуйте сохранить протокол еще раз. При возникновении данной ошибки повторно, обращайтесь в службу технической поддержки."+
        "<br><br> Текущий пользователь: "+aForm.username+", протокол был создан пользователем: "+aForm.editUsername ;
	}
	var wfe =aCtx.manager.createNativeQuery("select id,workFunctionExecute_id from MedCase where id = :medCase")
		.setParameter("medCase", aForm.medCase).getResultList() ;
	var wfeid = wfe.isEmpty() ? null : wfe.get(0)[1] ;
    var wf = java.lang.Long.valueOf(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic", wfeid));
    aForm.setSpecialist(wf);
    check(aForm, aCtx, true);

    if (wf != null) {
        var dat = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.dateRegistration);
        var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.dtype='Protocol'"
            + " and  d.medCase_id='" + aForm.medCase + "' and d.specialist_id='" + wf + "'"
            + " and d.dateRegistration=cast('" + dat + "' as date) and d.timeRegistration=cast('" + aForm.timeRegistration + "' as TIME) "
        )
            .getResultList();
        errorThrow(protocols, "В базе уже существует заключение созданное Вами в это время");

    }
    checkCreateDiagnosis(aForm, aCtx);
}
function onCreate(aForm, aEntity, aCtx) {
    var username = aCtx.getSessionContext().getCallerPrincipal().getName();
    if (aForm.getParams() != null && aForm.getParams() != "") {
        Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean.saveParametersByProtocol(aForm.getMedCase(), aEntity, aForm.getParams(), username, aCtx.manager);
    }
    createServiceMedCase(aForm, aEntity, aCtx);
    checkPrescription(aForm, aEntity, aCtx,false);
    makeTitleAndBottom(aForm, aEntity, aCtx);
}

/**
 * Создаем заголовок и подвал дневника
 * Заголовок создаем только для определенный раб. функций
 * */
function makeTitleAndBottom(aForm, aEntity, aCtx) {
    if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/GenerateTitleAndBottom")) {
        var vwf = aEntity.specialist.workFunction;
        if (true == vwf.getIsDiaryTitle()) {
            var pat = aEntity.medCase.patient;
            var title = "Пациент: "+pat.lastname + " " + pat.firstname + " " + (pat.middlename ? pat.middlename : "") + " " + Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(pat.birthday);
            title+="\nМО: ГБУЗ АО \"АМОКБ\"\nДата: "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aEntity.dateRegistration);
            aEntity.setTitle(title);
            aCtx.manager.persist(aEntity);
        }
    }
}

function createServiceMedCase(aForm, aEntity, aCtx) {
    var smc ;
    if (aForm.medService !== null && +aForm.medService > 0) {
        if (aEntity.serviceMedCase != null) {
            smc = aEntity.getServiceMedCase();
        } else {
            smc = new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase();
        }
        var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService, aForm.medService);
        smc.setMedService(medService);
        smc.setParent(aEntity.medCase);
        smc.setPatient(aEntity.medCase.patient);
        smc.setDateStart(aEntity.dateRegistration);
        smc.setTimeExecute(aEntity.timeRegistration);
        smc.setWorkFunctionExecute(aEntity.specialist);
        smc.setUsername(aCtx.getSessionContext().getCallerPrincipal().getName());
        aCtx.manager.persist(smc);
        aEntity.serviceMedCase = smc;

    } else if (aEntity.serviceMedCase != null) { //Если услуга пустая, а сервисмедкейс есть, удаляем сервис медкейс
        smc = aEntity.getServiceMedCase();
        aEntity.serviceMedCase = null;
        aCtx.manager.persist(aEntity);
        aCtx.manager.remove(smc);
    }
}
function onPreSave(aForm, aEntity, aCtx) {
    check(aForm, aCtx, false);
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().getName());
    var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.id='" + aEntity.id + "' and d.dtype='Protocol'").getResultList();
    if (protocols.isEmpty()) {
        onPreCreate(aForm, aCtx);

    }
}
/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
    var username = aCtx.getSessionContext().getCallerPrincipal().getName();
    if (aForm.username!=null && aForm.username!="" && !aForm.username.equals(username)) {
        throw "У Вас стоит ограничение на редактрование данного протокола!"+
        "<br><br> Текущий пользователь: "+username+", протокол был создан пользователем: "+aForm.username ;
    }
    var manager = aCtx.manager;
    var protocols = manager.createNativeQuery("select d.id,d.record from Diary d where d.id='" + aEntity.id + "' and d.dtype='Protocol'").getResultList();
    if (protocols.isEmpty()) {
        manager.createNativeQuery("update Diary set dtype='Protocol' where id='" + aEntity.id + "'").executeUpdate();
    }
    if (aForm.getParams() != null && aForm.getParams() != '') {
        Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean.saveParametersByProtocol(aForm.getMedCase(), aEntity, aForm.getParams(), username, manager);
    }
    createServiceMedCase(aForm, aEntity, aCtx);
    checkPrescription(aForm, aEntity, aCtx,true);
}
//Milamesher #121 22112018 - и при создании, и при сохранении (например, из черновика делают протокол) если дневник ещё не связан ни с какой консультацией, связать
function checkPrescription(aForm, aEntity, aCtx, flagIfSave) {
    //Milamesher #121 19092018 если есть переданные, но не выполненные консультации в этом сло текущего пользователя (с любой раб. ф-ей), то проставить diary_id
    var username = aCtx.getSessionContext().getCallerPrincipal().getName();
    var sql= "select scg.id from prescription scg" +
    " left join PrescriptionList pl on pl.id=scg.prescriptionList_id" +
    " left join medcase slo on slo.id=pl.medcase_id or slo.parent_id=pl.medcase_id" +
    " left join workfunction wf on wf.id=scg.prescriptcabinet_id" +
    " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
    " where scg.diary_id is null" +
    " and vwf.id=ANY(select wf.workfunction_id from WorkFunction wf" +
    " left join Worker w on w.id=wf.worker_id" +
    " left join Worker sw on sw.person_id=w.person_id" +
    " left join WorkFunction swf on swf.worker_id=sw.id" +
    " left join SecUser su on su.id=swf.secUser_id" +
    " where su.login='"+ username +"'" +
    " and wf.group_id=scg.prescriptcabinet_id )" +
    " and scg.dtype='WfConsultation' and scg.canceldate is null and (slo.id=ANY" +
    " (select id from medcase where dtype='DepartmentMedCase' and parent_id=(select parent_id from medcase where id="
    +aForm.getMedCase()+")) or slo.id="+aForm.getMedCase();
    sql+= (flagIfSave)? ") and (select count(id) from prescription where diary_id=" + aEntity.id + ")=0 order by scg.id" : ")  order by scg.id";
    var res = aCtx.manager.createNativeQuery(sql).getResultList();
    if (!res.isEmpty() && res.get(0)!=null) {
            var presc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.Prescription,java.lang.Long.valueOf(res.get(0)));
            presc.setDiary(aEntity);
            presc.setIntakeUsername(username);
            presc.setIntakeDate(aEntity.dateRegistration);
            presc.setIntakeTime(aEntity.timeRegistration);
            presc.setIntakeSpecial(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
            aCtx.manager.persist(presc);
    }
}
function check(aForm, aCtx,isCreate) {
    var manager = aCtx.manager;
    // Дата регистрации дневника не должна быть больше текущей даты
    var dateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateRegistration, aForm.timeRegistration);
    var currentDate = new java.util.Date();
    if (dateTime.getTime() > currentDate.getTime() && !aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/AllowCreateDiaryFutureTime")) {
        throw "Дата регистрации дневника не может быть больше текущего времени!";
    }
    if (aForm.medCase != null && +aForm.medCase > 0) {
        var lother = manager.createNativeQuery("select case when mc.dtype='ShortMedCase' then mc.dtype else null end as dtype,case when mc.datestart=to_date('" + aForm.getDateRegistration() + "','dd.mm.yyyy') and mc.workfunctionexecute_id='" + aForm.specialist + "' then mc.id end as agrmc,to_char(mc.datefinish,'dd.mm.yyyy') as mcfinish,mc.id as mcid from medcase mc where mc.id='" + aForm.medCase + "'").getResultList();
        if (!lother.isEmpty()) {
            var lobj = lother.get(0);
            if (lobj[0] != null && lobj[1] == null)
                throw "Протокол в талоне может быть создан только датой талона и врачом, за которым зарегистрирован талон!!!";
        }

        var t = manager.createNativeQuery("select m1.dtype,case when m1.dtype='DepartmentMedCase' and m2.dischargeTime is not null then m2.dateFinish else null end as datefinish" +
            ", to_char(coalesce(m2.dateStart, m1.dateStart),'dd.MM.yyyy') as dteStart, cast(coalesce(m2.entranceTime,m1.entranceTime) as varchar(5)) as entTime from medcase m1 left join medcase m2 on m2.id=m1.parent_id where m1.id=" + aForm.medCase).getResultList();
        if (!t.isEmpty()) {
            var dtype = "" + t.get(0)[0];
            var slsStartDateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(t.get(0)[2], t.get(0)[3]);
            if (dtype === 'HospitalMedCase' || dtype === 'DepartmentMedCase') {
                if (aForm.type == null || (+aForm.type === 0)) throw "Необходимо заполнить поле Тип протокола";
                if (aForm.state == null || (+aForm.state === 0)) throw "Необходимо заполнить поле Состояние больного";
                if (slsStartDateTime.getTime() > dateTime.getTime()) throw "Дата регистрации дневника не может быть ранее даты начала госпитализации";
            }
            if (dtype === 'HospitalMedCase' && (aForm.journalText == null || aForm.journalText.trim().equals(""))) {
                throw "Необходимо заполнить поле Принятые меры для журнала. Если их нет, необходимо ставить: -";
            }
            //при сохранении шаблона как оригинал нужно заполнять услугу, если она обязательна
            if (getMedServiceNecessaryInDiary(aForm.medCase,aCtx)=="1" && (aForm.medService == null || +aForm.medService === 0)) {
                throw "Заполнение услуги обязательно при создании дневника в СЛО (с потоком обслуживания ДМС/Платный) врачом - не сотрудником текущего отделения!";
            }
            //Milamesher 16102018 - создание дневника специалиста приёмного отделения по времени - только ДО создания СЛО
            if (dtype === 'HospitalMedCase' && aForm.getDateRegistration() != null && aForm.getDateRegistration() != '') {
                var list = manager.createNativeQuery("select case when dmc.id is null then '0' else case when (dmc.dateStart>to_date('"
                    + aForm.dateRegistration + "','dd.mm.yyyy') or dmc.dateStart=to_date('" + aForm.dateRegistration + "','dd.mm.yyyy') and dmc.entranceTime>'"
                    + aForm.timeRegistration + "' ) then '0' else '1' end end" +
                    " from medcase hmc" +
                    " left join medcase dmc on hmc.id=dmc.parent_id and dmc.dtype='DepartmentMedCase'" +
                    " where hmc.id=" + aForm.medCase + " order by dmc.id").setMaxResults(1).getResultList();
                if (!list.isEmpty() && list.get(0) == '1') {
                    throw "Нельзя создавать дневник специалиста приёмного отделения с датой регистрации больше начала СЛО! Нужно изменить дату и время регистрации либо создать дневник в случае лечения в отделении.";
                }
            }
        }
        var isCheck = null;
        var ldm = manager.createNativeQuery("select dm.id,dm.validitydate from diarymessage dm where dm.diary_id=" + aForm.id + " and (dm.validitydate>current_date or dm.validitydate=current_date and dm.validitytime>=current_time)").getResultList();
        if (!ldm.isEmpty()) {
            manager.createNativeQuery("update diarymessage dm set IsDoctorCheck='1' where dm.diary_id=" + aForm.id + "").executeUpdate();
        }
        if (aForm.getDateRegistration() != null && aForm.getDateRegistration() != '' && ldm.isEmpty()) {
            var curDate = java.util.Calendar.getInstance();
            var maxVisit = java.util.Calendar.getInstance();
            var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.getDateRegistration(), aForm.getTimeRegistration());
            maxVisit.setTime(dateVisit);
            var slsId = aForm.medCase;
            if (dtype==='DepartmentMedCase') {
                slsId = manager.createNativeQuery("select parent_id from medcase where id = "+slsId).getResultList().get(0);
            }
            var config = (dtype === 'HospitalMedCase' || dtype === 'DepartmentMedCase') ? "count_hour_edit_hosp_protocol" : "count_hour_edit_protocol";
            var cntHour = +getDefaultParameterByConfig(config, 24, aCtx);
            maxVisit.add(java.util.Calendar.HOUR, cntHour);
            if (curDate.after(maxVisit) || t.get(0)[1] != null && !isCreate) {
                var param1 = new java.util.HashMap();
                if (dtype === 'HospitalMedCase' || dtype === 'DepartmentMedCase') {
                    param1.put("obj", "DischargeMedCase");
                    param1.put("permission", "editAfterDischarge");
                    param1.put("id", +slsId);
                    isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                }
                if (+isCheck!==1) {
                    param1 = new java.util.HashMap();
                    param1.put("obj", "Protocol");
                    param1.put("permission", "editAfterCertainHour");
                    param1.put("id", +aForm.id);
                    isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                    if (+isCheck !== 1 && ldm.isEmpty()) {
                        var tmpStr = (dtype === 'HospitalMedCase' || dtype === 'DepartmentMedCase') ? " госпитализации" : "";
                        if (t.get(0)[1] == null) throw "У Вас стоит ограничение " + cntHour + " часов на редактирование протокола" + tmpStr + "!!!";
                        else throw "У Вас стоит ограничение на редактирование данных после выписки!!!";
                    }
                }

            }
            if (+isCheck!==1 && t.get(0)[1] != null && isCreate)
                throw "У Вас стоит ограничение на создание данных после выписки!!!";
        }
    }
}
function checkCreateDiagnosis(aForm, aCtx) {
    var idc = +aForm.getDiagnosisIdc10();
    var m = aCtx.manager;
    var mc = m.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase, aForm.getMedCase());
    var dtype = "" + m.createNativeQuery("select id, dtype from medcase where id=" + mc.getId()).getSingleResult()[1];
    if (idc === 0 && dtype === 'HospitalMedCase' &&
        aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/MustCreateDiaryInHospitalMedCase")) {
        throw "Необходимо заполнить поле \"Диагноз\"";
    } else if (idc>0) {
        var theDiagnosisPriority = aForm.getDiagnosisPriority();
        var theDiagnosisText = aForm.getDiagnosisText();
        var theDiagnosisIllnessPrimary = aForm.getDiagnosisIllnessPrimary();
        var theDiagnosisRegistrationType = aForm.getDiagnosisRegistrationType(); //not all
        if (+theDiagnosisPriority === 0 || +theDiagnosisIllnessPrimary === 0) {
            throw "При указании кода МКБ необходимо заполнить поля \"Характер заболевания\",\"Приоритет\"";
        }
        if ((dtype === 'HospitalMedCase' || dtype === 'DepartmentMedCase') && +theDiagnosisRegistrationType === 0) {
            throw "При указании кода МКБ в стационаре необходимо заполнить поля \"Тип регистрации\"";
        }

        var d = new Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis();
        d.setName(theDiagnosisText);
        d.setPriority(m.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis, theDiagnosisPriority));
        d.setIdc10(m.find(Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10, java.lang.Long.valueOf(idc)));
        d.setEstablishDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getDateRegistration()));
        d.setMedCase(mc);
        d.setRegistrationType(m.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType, theDiagnosisRegistrationType));
        d.setUsername(aCtx.getSessionContext().getCallerPrincipal().getName());
        d.setMedicalWorker(m.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, aForm.getSpecialist()));
        d.setIllnesPrimary(m.find(Packages.ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary, theDiagnosisIllnessPrimary));
        d.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
        m.persist(d);
    }
}

function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
    var l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='" + aParameter + "'").getResultList();
    return l.isEmpty() ? aValueDefault : l.get(0)[1];
}
function errorThrow(aList, aError) {
    if (!aList.isEmpty()) {
        var error = ":";
        for (var i = 0; i < aList.size(); i++) {
            var doc = aList.get(i);
            error = error + " <br/><a style='color:yellow' href='entityView-smo_visitProtocol.do?id=" + doc[0] + "'>"
            error = error + (i + 1) + ". Заключение: <pre>" + doc[1] + " </pre> ";
            error = error + "</a>";
        }
        throw aError + error;
    }
}
/**
 * Обязательно ли заполнение услуги в дневнике? #122.
 * Обязательно, если:
 * это в СЛО
 * поток облуживания платный/ДМС
 * отделение создающего дневник не совпадает с отделением СЛО
 *
 * @param medCaseId MedCase.id
 * @return String "1" - необходима, "0" - нет
 */
function getMedServiceNecessaryInDiary(medCaseId, aCtx) {
    var l = aCtx.manager.createNativeQuery("select case when (ss.code='PRIVATEINSURANCE' or ss.code='CHARGED') and mc.dtype='DepartmentMedCase'" +
        " and ml.id<>mc.department_id" +
        " then '1' else '0' end" +
        " from medcase mc" +
        " left join vocservicestream ss on mc.servicestream_id=ss.id" +
        " left join workfunction wf on wf.id=" + aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction").id +
        " left join Worker w on w.id=wf.worker_id" +
        " left join MisLpu ml on ml.id=w.lpu_id" +
        " where  mc.id="+medCaseId).getResultList();
    return !l.isEmpty() && l.get(0)=="1" ? "1" : "0";
}