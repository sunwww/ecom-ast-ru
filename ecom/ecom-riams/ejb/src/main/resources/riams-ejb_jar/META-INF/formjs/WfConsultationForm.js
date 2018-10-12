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
    var date = new java.util.Date();
    aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
    aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onCreate(aForm,aEntity, aCtx) {
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
            else if (res.get(0) == "0" && aEntity.prescriptCabinet != null && aEntity.prescriptionList != null) {
                //уведомление пользователей об экстренных заявках к ним
                var list = aCtx.manager.createNativeQuery("select distinct su.login,\n" +
                    "cast('Экстренная консультация' as varchar(23)) as txt\n" +
                    ",pat.lastname||' '||pat.firstname||' '||pat.middlename||' '\n" +
                    "||to_char(pat.birthday,'dd.mm.yyyy')||' ('||dep.name||')'\n" +
                    "||cast(' к ' as varchar(3))||gwf.groupname\n" +
                    ",sls.id as sls\n" +
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
                    "left join medcase sls on slo.parent_id=sls.id\n" +
                    "where gwf.id=" + aEntity.prescriptCabinet.id + " and (wf.archival is null or wf.archival='0') \n" +
                    "and su.login is not null order by su.login \n").getResultList();
                for (var i = 0; i < list.size(); i++) {
                    var msg = list.get(i)[2];
                    var title = list.get(i)[1];
                    var recipient = list.get(i)[0];
                    var creator = aEntity.createUsername;
                    var url = "entityParentView-stac_ssl.do?id=" + list.get(i)[3];
                    aCtx.manager.createNativeQuery("insert into CustomMessage (messageText,messageTitle,recipient,dispatchDate,dispatchTime,username,messageUrl)" +
                        "VALUES('" + msg + "','" + title + "','" + recipient + "',current_date,current_time,'" + creator + "','" + url + "')").executeUpdate();
                    aCtx.manager.createNativeQuery("insert into CustomMessage (messageText,messageTitle,recipient,dispatchDate,dispatchTime,username,messageUrl,isEmergency)" +
                        "VALUES('" + msg + "','" + title + "','" + recipient + "',current_date,current_time,'" + creator + "','" + url + "','1')").executeUpdate();
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