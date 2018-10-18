function onPreSave(aForm,aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    var res = aCtx.manager.createNativeQuery("select case when transferdate is null then '1' else '0' end from prescription where id=" + aEntity.id).getResultList();
    if (res.size()>0) {
        if (res.get(0) == "0") {
            throw "Назначение, которое уже было передано специалисту, редактировать нельзя!";
        }
    }
}
function onPreCreate(aForm, aCtx) {
    //Только одно не выполненное, активное направление к одному специалисту может быть
    if (aForm.getPrescriptCabinet()!=null && aForm.getPrescriptionList()!=null) {
        var res = aCtx.manager.createNativeQuery("select skg.id  from prescription skg\n" +
            "left join prescriptionlist pl on pl.id=" + aForm.getPrescriptionList() + "\n" +
            "left join medcase slo on slo.id=pl.medcase_id\n" +
            "left join medcase sls on sls.id=slo.parent_id\n" +
            "where skg.prescriptcabinet_id=" + aForm.getPrescriptCabinet() + " and skg.prescriptionlist_id=\n" +
            "ANY(select id from prescriptionlist where medcase_id=ANY(select id from medcase where parent_id=sls.id))\n" +
            "and skg.intakedate is null and skg.canceldate is null limit 1").getResultList();
        if (res.size()>0) {
            throw "Уже есть активное, не выполненное направление к этому специалисту! Вы можете изменить его тип или отменить его: " +
            " <a href='entityParentView-pres_wfConsultation.do?id="+res.get(0)+"'>Направление</a><br/>";
        }
    }
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onCreate(aForm,aEntity, aCtx) {
    //Milamesher 16102018
    //при создании консультации если есть в СЛС/СЛО есть дневники по имени пользователя с его групповой раб. ф-ей
    //и id дневника нет в любом другом назначении, тогда проставляем в пределах текущей даты
    //если в приёмнике сделана консультация, то в отделении назначенные заявки надо выполнять опять, автоматом не будет
    //если в СЛО, то смотрим все СЛО этого СЛС
    var t=false;
    if (aEntity.prescriptionList!=null) {
        var res = aCtx.manager.createNativeQuery("select d.id,d.dateregistration,d.timeregistration from diary d\n" +
            "left join workfunction wf on wf.id=d.specialist_id\n" +
            "left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
            "left join medcase mc on d.medcase_id=mc.id \n" +
            "left join PrescriptionList pl on pl.id="+aEntity.prescriptionList.id+"\n" +
            "where d.dateregistration=current_date\n" +
            "and case when mc.dtype='HospitalMedCase' then mc.id=pl.medcase_id \n" +
            "else case when mc.dtype='DepartmentMedCase' then mc.id=pl.medcase_id \n" +
            "or mc.id=ANY(select id from medcase where dtype='DepartmentMedCase'\n" +
            "and parent_id=(select parent_id from medcase where id=pl.medcase_id)) end end \n" +
            "and pl.id=" + aEntity.prescriptionList.id + " and vwf.id=ANY(select wf.workfunction_id from WorkFunction wf\n" +
            "left join Worker w on w.id=wf.worker_id\n" +
            "left join Worker sw on sw.person_id=w.person_id\n" +
            "left join WorkFunction swf on swf.worker_id=sw.id\n" +
            "left join SecUser su on su.id=swf.secUser_id\n" +
            "where su.login='"+aCtx.getSessionContext().getCallerPrincipal().toString()+
            "' and (wf.archival is null or wf.archival='0'))\n" +
            "and (select count(id) from prescription where diary_id=d.id)=0").getResultList();
        if (res.size() > 0) {
            if (res.get(0) !=null && res.get(0) != "") {
                aEntity.setDiary(aCtx.manager.find(Packages.ru.ecom.diary.ejb.domain.Diary, java.lang.Long.valueOf(res.get(0)[0])));
                aEntity.setIntakeUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
                aEntity.setIntakeDate(res.get(0)[1]);
                aEntity.setIntakeTime(res.get(0)[2]);
                aEntity.setIntakeSpecial(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
                aCtx.manager.persist(aEntity);
                t=true;
            }
        }
    }
    //Milamesher 11102018
    //Плановые - автоматически переданные при создании
    if (aEntity.vocConsultingType != null) {
        var res = aCtx.manager.createNativeQuery("select case when t.code='plan' then '1' else '0' end \n" +
            "from vocconsultingtype t\n" +
            "where t.id=" + aEntity.vocConsultingType.id).getResultList();
        if (res.size() > 0) {
            if (res.get(0) == "1") {
                var date = new java.util.Date();
                aEntity.setTransferDate(new java.sql.Date(date.getTime()));
                aEntity.setTransferTime(new java.sql.Time(date.getTime()));
                aEntity.setTransferUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
                aCtx.manager.persist(aEntity);
            }
            else if (res.get(0) == "0" && aEntity.prescriptCabinet != null && aEntity.prescriptionList != null && !t) {
                //уведомление пользователей об экстренных заявках к ним
                var sls = aCtx.manager.createNativeQuery("select case when mc.dtype='DepartmentMedCase' then mc.parent_id else mc.id end\n" +
                "from medcase mc\n" +
                "left join PrescriptionList pl on pl.medcase_id=mc.id\n" +
                "where pl.id="+aEntity.prescriptionList.id).getResultList();
                if (sls.size()>0) {
                    sls = sls.get(0);
                    var list = aCtx.manager.createNativeQuery("select distinct su.login,\n" +
                        "cast('Экстренная консультация' as varchar(23)) as txt\n" +
                        ",pat.lastname||' '||pat.firstname||' '||pat.middlename||' '\n" +
                        "||to_char(pat.birthday,'dd.mm.yyyy')||' ('||dep.name||')'\n" +
                        "||cast(' к ' as varchar(3))||gwf.groupname\n" +
                        "from WorkFunction wf\n" +
                        "left join Worker w on w.id=wf.worker_id\n" +
                        "left join Worker sw on sw.person_id=w.person_id\n" +
                        "left join WorkFunction swf on swf.worker_id=sw.id\n" +
                        "left join vocworkfunction vwf on vwf.id=wf.workfunction_id\n" +
                        "left join workfunction gwf on gwf.workfunction_id=wf.workfunction_id\n" +
                        "left join SecUser su on su.id=swf.secUser_id\n" +
                        "left join PrescriptionList pl on pl.id=" + aEntity.prescriptionList.id + "\n" +
                        "left join medcase slo on slo.id=pl.medcase_id\n" +
                        "left join patient pat on slo.patient_id=pat.id\n" +
                        "left join mislpu dep on dep.id=slo.department_id\n" +
                        "where wf.group_id is not null and gwf.id=" + aEntity.prescriptCabinet.id +
                        " and (wf.archival is null or wf.archival='0') \n" +
                        "and su.login is not null order by su.login \n").getResultList();
                    for (var i = 0; i < list.size(); i++) {
                        var msg = list.get(i)[2];
                        var title = list.get(i)[1];
                        var recipient = list.get(i)[0];
                        var creator = aEntity.createUsername;
                        var url = "entityParentView-stac_ssl.do?id=" + sls;
                        aCtx.manager.createNativeQuery("insert into CustomMessage (messageText,messageTitle,recipient,dispatchDate,dispatchTime,username,messageUrl)" +
                            "VALUES('" + msg + "','" + title + "','" + recipient + "',current_date,current_time,'" + creator + "','" + url + "')").executeUpdate();
                        aCtx.manager.createNativeQuery("insert into CustomMessage (messageText,messageTitle,recipient,dispatchDate,dispatchTime,username,messageUrl,isEmergency)" +
                            "VALUES('" + msg + "','" + title + "','" + recipient + "',current_date,current_time,'" + creator + "','" + url + "','1')").executeUpdate();
                    }
                }
            }
        }
    }
}
function onPreDelete(aEntityId, aContext) {
    var res = aContext.manager.createNativeQuery("select case when transferdate is null then '1' else '0' end from prescription where id=" + aEntityId).getResultList();
    if (res.size()>0) {
        if (res.get(0) == "0") {
            throw "Назначение, которое уже было передано специалисту, удалять нельзя!";
        }
    }
}