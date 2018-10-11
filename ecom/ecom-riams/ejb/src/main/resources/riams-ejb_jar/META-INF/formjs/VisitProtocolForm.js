function onPreDelete(aEntityId, aCtx) {
    var l = aCtx.manager.createNativeQuery("select username,to_char(dateRegistration,'dd.mm.yyyy') as dater,cast(timeRegistration as varchar(5)) as timer from diary where id=" + aEntityId)
        .getResultList();
    if (l.size() > 0) {
        var obj = l.get(0);
        if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/DisableDeleteOnlyTheir")
            && aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/EnableDeleteOnlyTheir")) {
            if (aCtx.getSessionContext().getCallerPrincipal().toString() != ("" + obj[0])) {
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
    var res = aCtx.manager.createNativeQuery( "select  scg.id from prescription scg\n" +
        "left join PrescriptionList pl on pl.id=scg.prescriptionList_id\n" +
        "left join medcase slo on slo.id=pl.medcase_id\n" +
        "left join workfunction wf on wf.id=scg.prescriptcabinet_id\n" +
        "where scg.transferdate is not null and scg.diary_id='" + aEntityId + "'").getResultList();
    if (res.size()>0) {
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
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aForm.setTime(new java.sql.Time (date.getTime())) ;
	if (aForm.editUsername!=null && aForm.editUsername!="" && !aForm.username.equals(aForm.editUsername)) {
		//aCtx.manager.createNativeQuery("insert into ChangeJournal (classname,changedate,changetime,SerializationBefore,objectid) values ('VISITPROTOCOL',current_date,current_time,'"+aForm.username+"- -"+aForm.editUsername+"','"+aForm.medCase+"')").executeUpdate() ;
		throw "Не удалось сохранить протокол: <br/><pre>"+aForm.record+"</pre><br/> Попробуйте сохранить протокол еще раз. При возникновении данной ошибки повторно, обращайтесь в службу технической поддержки."+
        "<br><br> Текущий пользователь: "+aForm.username+", протокол был создан пользователем: "+aForm.editUsername ;
	}
	var wfe =aCtx.manager.createNativeQuery("select id,workFunctionExecute_id from MedCase where id = :medCase")
		.setParameter("medCase", aForm.medCase).getResultList() ;
	var wfeid = java.lang.Long.valueOf(0) ;
	if (wfe.size()>0) {
		wfeid=wfe.get(0)[1] ;
	}
      var wf = java.lang.Long.valueOf(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic"
        , wfeid));
    aForm.setSpecialist(wf);
    check(aForm, aCtx);

    if (wf != null) {
        var protocols;
        //throw "select d.id,d.record from Diary d where d.dtype='Protocol'"
        //	+" and  d.medCase_id='"+aForm.medCase+"' and d.specialist_id='"+aForm.specialist+"'"
        //	+" and d.dateRegistration=$$ei^Zcdat('"+aForm.dateRegistration+"') and d.timeRegistration=cast('"+aForm.timeRegistration+"' as TIME) "
        //	;
        var dat = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.dateRegistration);
        protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.dtype='Protocol'"
            + " and  d.medCase_id='" + aForm.medCase + "' and d.specialist_id='" + wf + "'"
            + " and d.dateRegistration=cast('" + dat + "' as date) and d.timeRegistration=cast('" + aForm.timeRegistration + "' as TIME) "
        )
            .getResultList();
        errorThrow(protocols, "В базе уже существует заключение созданное Вами в это время");

    }
    checkCreateDiagnosis(aForm, aCtx);
}
function onCreate(aForm, aEntity, aCtx) {

    var username = aCtx.getSessionContext().getCallerPrincipal().toString();
    //throw ""+aForm.getMedCase()+"<>"+ aEntity.id;
    if (aForm.getParams() != null && aForm.getParams() != "") {
        Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean.saveParametersByProtocol(aForm.getMedCase(), aEntity, aForm.getParams(), username, aCtx.manager);
    }
    createServiceMedCase(aForm, aEntity, aCtx);
    var bean = new Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean();
    bean.sendProtocolToExternalResource(aEntity.getId(),null,null,aCtx.manager);
    //Milamesher #121 19092018 если есть переданные, но не выполненные консультации в этом сло текущего пользователя (с любой раб. ф-ей), то проставить diary_id
    //upd Milamesher 11102018 и не отменённые
    //upd2 и не текущего СЛО и любого СЛО, у кого СЛС - parent текущего СЛО
    var res = aCtx.manager.createNativeQuery( "select  scg.id from prescription scg\n" +
        "left join PrescriptionList pl on pl.id=scg.prescriptionList_id\n" +
        "left join medcase slo on slo.id=pl.medcase_id\n" +
        "left join workfunction wf on wf.id=scg.prescriptcabinet_id\n" +
        "left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
        "where scg.transferdate is not null and scg.diary_id is null\n" +
        "and vwf.id=ANY(select wf.workfunction_id from WorkFunction wf\n" +
        "left join Worker w on w.id=wf.worker_id\n" +
        "left join Worker sw on sw.person_id=w.person_id\n" +
        "left join WorkFunction swf on swf.worker_id=sw.id\n" +
        "left join SecUser su on su.id=swf.secUser_id\n" +
        "where su.login='"+ aCtx.getSessionContext().getCallerPrincipal().toString() +
        "' and (wf.archival is null or wf.archival='0'))" +
        " and scg.dtype='WfConsultation' and scg.canceldate is null and slo.id=ANY\n" +
        "(select id from medcase where dtype='DepartmentMedCase' and parent_id=(select parent_id from medcase where id='"
        +aForm.getMedCase()+"')) order by scg.id").getResultList();
    if (res.size()>0) {
        if (res.get(0)!=null) {
            var presc = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.Prescription,java.lang.Long.valueOf(res.get(0)));
            presc.setDiary(aEntity);
            var currentDate = new java.util.Date();
            presc.setIntakeDate(aEntity.dateRegistration);
            presc.setIntakeTime(aEntity.timeRegistration);
            presc.setIntakeSpecial(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
            aCtx.manager.persist(presc);
        }
    }
}
function createServiceMedCase(aForm, aEntity, aCtx) {
    if (aForm.medService !== null && +aForm.medService > 0) {
        var smc = null;
        if (aEntity.serviceMedCase != null) {
            //	throw "ggod:";
            smc = aEntity.getServiceMedCase();
        } else {
            smc = new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase();
        }
        //throw "spec = "+aEntity.specialist;
        //username, createdate, noactuality,parent_id, patient_id,workfunctionexecute_id, medservice_id , createtime,
        var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService, aForm.medService);
        smc.setMedService(medService)
        smc.setParent(aEntity.medCase);
        smc.setPatient(aEntity.medCase.patient);
        smc.setDateStart(aEntity.dateRegistration);
        smc.setTimeExecute(aEntity.timeRegistration);
        smc.setWorkFunctionExecute(aEntity.specialist);
        smc.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
        aCtx.manager.persist(smc);
        aEntity.serviceMedCase = smc;

    } else if (aEntity.serviceMedCase != null) { //Если услуга пустая, а сервисмедкейс есть, удаляем сервис медкейс
        var smc = aEntity.getServiceMedCase();
        aEntity.serviceMedCase = null;
        aCtx.manager.persist(aEntity);
        aCtx.manager.remove(smc);
    }
}
function onPreSave(aForm, aEntity, aCtx) {
    check(aForm, aCtx);
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    //aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.id='" + aEntity.id + "' and d.dtype='Protocol'").getResultList();
    if (protocols.isEmpty()) {
        onPreCreate(aForm, aCtx);

    }
}
/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
    //throw "select d.id,d.record from Diary d where d.id='"+aEntity.id+"' and d.dtype='Protocol'" ;
    var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.id='" + aEntity.id + "' and d.dtype='Protocol'").getResultList();
    if (protocols.isEmpty()) {
        //throw "123" ;

        aCtx.manager.createNativeQuery("update Diary set dtype='Protocol' where id='" + aEntity.id + "'").executeUpdate();

    }
    //throw ""+aForm.getParams();
    if (aForm.getParams() != null && aForm.getParams() != '') {
        //	throw "==="+aForm.getMedCase()+"<>"+aEntity.id+"<>"+aForm.getParams();
        var username = aCtx.getSessionContext().getCallerPrincipal().toString();
        var text = Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean.saveParametersByProtocol(aForm.getMedCase(), aEntity, aForm.getParams(), username, aCtx.manager);
//	throw ""+text;
    }
    createServiceMedCase(aForm, aEntity, aCtx);
}
function check(aForm, aCtx) {
//test
    if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/AllowCreateDiaryFutureTime")) {
        // Дата регистрации дневника не должна быть больше текущей даты
        var dateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateRegistration, aForm.timeRegistration);
        var currentDate = new java.util.Date();
        if (dateTime.getTime() > currentDate.getTime()) {
            throw "Дата регистрации дневника не может быть больше текущего времени!";
        }

    }
    if (aForm.medCase != null && (+aForm.medCase) > 0) {

        var lother = aCtx.manager.createNativeQuery("select case when mc.dtype='ShortMedCase' then mc.dtype else null end as dtype,case when mc.datestart=to_date('" + aForm.getDateRegistration() + "','dd.mm.yyyy') and mc.workfunctionexecute_id='" + aForm.specialist + "' then mc.id end as agrmc,to_char(mc.datefinish,'dd.mm.yyyy') as mcfinish,mc.id as mcid from medcase mc where mc.id='" + aForm.medCase + "'").getResultList();
        if (lother.size() > 0) {
            var lobj = lother.get(0);
            if (lobj[0] != null && lobj[1] == null)
                throw "Протокол в талоне может быть создан только датой талона и врачом, за которым зарегистрирован талон!!!";
        }


        var t = aCtx.manager.createNativeQuery("select m1.dtype,case when m1.dtype='DepartmentMedCase' and m2.dischargeTime is not null then m2.dateFinish else null end as datefinish from medcase m1 left join medcase m2 on m2.id=m1.parent_id where m1.id=" + aForm.medCase).getResultList();
        var hmc = aCtx.manager.createNativeQuery("select case when dtype='HospitalMedCase' or dtype='PolyclinicMedCase' then id else parent_id end from medcase where id=" + aForm.medCase).getSingleResult(); //Если парент_ид = нулл, будет ошибка

        //throw ""+hmc;
        if (!t.isEmpty()) {
            var dtype = "" + t.get(0)[0];
            //throw dtype ;
            if (dtype == 'HospitalMedCase' || dtype == 'DepartmentMedCase') {
                if (aForm.type == null || (+aForm.type == 0)) throw "Необходимо заполнить поле Тип протокола";
                if (aForm.state == null || (+aForm.state == 0)) throw "Необходимо заполнить поле Состояние больного";
            }
            if (dtype == 'HospitalMedCase' && (aForm.journalText == null || aForm.journalText.equals(""))) {
                throw "Необходимо заполнить поле Принятые меры для журнала. Если их нет, необходимо ставить: -";
            }
            var isCheck = null;

            var param1 = new java.util.HashMap();

        }
        var ldm = aCtx.manager.createNativeQuery("select dm.id,dm.validitydate from diarymessage dm where dm.diary_id=" + aForm.id + " and (dm.validitydate>current_date or dm.validitydate=current_date and dm.validitytime>=current_time)").getResultList();
        if (ldm.size() > 0) {
            aCtx.manager.createNativeQuery("update diarymessage dm set IsDoctorCheck='1' where dm.diary_id=" + aForm.id + "").executeUpdate();
        }
        if (dtype == 'HospitalMedCase' || dtype == 'DepartmentMedCase') {
            if (aForm.getDateRegistration() != null && aForm.getDateRegistration() != '' && isCheck == null) {
                if (t.get(0)[1] != null) {

                    param1.put("obj", "DischargeMedCase");
                    param1.put("permission", "editAllHospitalMedCase"); //editAllProtocolsInSLS
                    param1.put("id", +hmc);
                    isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                    if (+isCheck == 1) {

                    } else {
                        param1.put("obj", "DischargeMedCase");
                        param1.put("permission", "editAfterDischarge"); //editAllProtocolsInSLS
                        param1.put("id", +hmc);
                        isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                        if (+isCheck != 1) throw "У Вас стоит ограничение на редактирование (создание) данных после выписки!!!";
                    }

                } else {
                    if (ldm.size() > 0) {

                    } else {
                        var curDate = java.util.Calendar.getInstance();
                        var maxVisit = java.util.Calendar.getInstance();
                        var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.getDateRegistration(), aForm.getTimeRegistration());
                        maxVisit.setTime(dateVisit);
                        var cntHour = +getDefaultParameterByConfig("count_hour_edit_hosp_protocol", 24, aCtx);
                        maxVisit.add(java.util.Calendar.HOUR, cntHour);
                        if (curDate.after(maxVisit)) {
                            var param1 = new java.util.HashMap();
                            param1.put("obj", "HospitalMedCase");
                            param1.put("permission", "editAllHospitalMedCase"); //editAllProtocolsInSLS
                            param1.put("id", +hmc);
                            isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                            if (+isCheck == 1) {

                            } else {
                                var param1 = new java.util.HashMap();
                                param1.put("obj", "Protocol");
                                param1.put("permission", "editAfterCertainHour");
                                param1.put("id", +aForm.id);
                                isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                                if (+isCheck != 1) {
                                    if (ldm.size() == 0) {
                                        throw "У Вас стоит ограничение " + cntHour + " часов на создание (редактирование) протокола госпитализации!!!";
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } else {
            if (aForm.getDateRegistration() != null && aForm.getDateRegistration() != '' && isCheck == null) {
                var curDate = java.util.Calendar.getInstance();
                var maxVisit = java.util.Calendar.getInstance();
                var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.getDateRegistration(), aForm.getTimeRegistration());
                maxVisit.setTime(dateVisit);
                var cntHour = +getDefaultParameterByConfig("count_hour_edit_protocol", 24, aCtx);
                maxVisit.add(java.util.Calendar.HOUR, cntHour);
                if (curDate.after(maxVisit)) {
                    //var param1 = new java.util.HashMap() ;
                    param1.put("obj", "Protocol");
                    param1.put("permission", "editAfterCertainHour");
                    param1.put("id", +aForm.id);
                    isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1) + "";
                    if (+isCheck != 1) {
                        if (ldm.size() == 0) {
                            throw "У Вас стоит ограничение " + cntHour + " часов на создание (редактирование) протокола!!!";
                        }
                    }
                }
            }
        }

    }
}
function checkCreateDiagnosis(aForm, aCtx) {
    var idc = aForm.getDiagnosisIdc10();
    var m = aCtx.manager;
    var mc = m.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase, aForm.getMedCase());
    var dtype = "" + m.createNativeQuery("select id, dtype from medcase where id=" + mc.getId()).getSingleResult()[1];
    if ((idc == null || idc == 0) && dtype == 'HospitalMedCase') {
        if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/MustCreateDiaryInHospitalMedCase")) {
            throw "Необходимо заполнить поле \"Диагноз\"";
        }

    } else if (idc == null || idc == 0) {

    } else {


        var theDiagnosisPriority = aForm.getDiagnosisPriority();
        var theDiagnosisText = aForm.getDiagnosisText();
        var theDiagnosisIllnessPrimary = aForm.getDiagnosisIllnessPrimary();
        var theDiagnosisRegistrationType = aForm.getDiagnosisRegistrationType(); //not all
        if (theDiagnosisPriority == null || theDiagnosisPriority == 0 || theDiagnosisIllnessPrimary == null || theDiagnosisIllnessPrimary == 0) {
            throw "При указании кода МКБ необходимо заполнить поля \"Характер заболевания\",\"Приоритет\"";
        }

        if ((dtype == 'HospitalMedCase' || dtype == 'DepartmentMedCase') && (theDiagnosisRegistrationType == null || theDiagnosisRegistrationType == 0)) {
            throw "При указании кода МКБ в стационаре необходимо заполнить поля \"Тип регистрации\"";
        }

        var d = new Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis();
        d.setName(theDiagnosisText);
        d.setPriority(m.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocPriorityDiagnosis, theDiagnosisPriority));
        d.setIdc10(m.find(Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10, idc));
        d.setEstablishDate(Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.getDateRegistration()));
        d.setMedCase(mc);
        d.setRegistrationType(m.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType, theDiagnosisRegistrationType));
        d.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
        d.setMedicalWorker(m.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, aForm.getSpecialist()));
        d.setIllnesPrimary(m.find(Packages.ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary, theDiagnosisIllnessPrimary));
        d.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
        m.persist(d);
    }
}

function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
    l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='" + aParameter + "'").getResultList();
    if (l.isEmpty()) {
        return aValueDefault;
    } else {
        return l.get(0)[1];
    }
}
function errorThrow(aList, aError) {
    if (aList.size() > 0) {
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
