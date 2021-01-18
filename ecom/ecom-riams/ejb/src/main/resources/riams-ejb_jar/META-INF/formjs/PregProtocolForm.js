function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date() ;
    aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
    var login = aCtx.getSessionContext().getCallerPrincipal().getName();
    aForm.setUsername(login) ;
    aForm.setTime(new java.sql.Time (date.getTime())) ;
    if (aForm.editUsername!=null && aForm.editUsername!="" && !aForm.username.equals(aForm.editUsername)) {
        throw "Не удалось сохранить протокол: <br/><pre>"+aForm.record+"</pre><br/> Попробуйте сохранить протокол еще раз. При возникновении данной ошибки повторно, обращайтесь в службу технической поддержки."+
        "<br><br> Текущий пользователь: "+aForm.username+", протокол был создан пользователем: "+aForm.editUsername ;
    }
    var wfe =aCtx.manager.createNativeQuery("select wf.id from workfunction wf left join secuser su on su.id=wf.secuser_id where su.login=:username")
        .setParameter("username", login).getResultList() ;
    var wfeid = wfe.isEmpty() ? null : wfe.get(0);
    var wf = java.lang.Long.valueOf(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic", wfeid));
    aForm.setSpecialist(wf);
    check(aForm, aCtx);

    if (wf != null) {
        var dat = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.dateRegistration);
        var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.dtype='Protocol'"
            + " and  d.medCase_id='" + aForm.medCase + "' and d.specialist_id='" + wf + "'"
            + " and d.dateRegistration=cast('" + dat + "' as date) and d.timeRegistration=cast('" + aForm.timeRegistration + "' as TIME) "
        )
            .getResultList();
        errorThrow(protocols, "В базе уже существует протокол того же типа, созданный Вами в это время");
    }
}
function onPreSave(aForm, aEntity, aCtx) {
    check(aForm, aCtx);
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setEditTime(new java.sql.Time (date.getTime())) ;
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
    var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.id='" + aEntity.id + "' and d.dtype='Protocol'").getResultList();
    if (protocols.isEmpty()) {
        onPreCreate(aForm, aCtx);
    }
}
function check(aForm, aCtx) {
    if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/AllowCreateDiaryFutureTime")) {
        // Дата регистрации дневника не должна быть больше текущей даты
        var dateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateRegistration, aForm.timeRegistration);
        var currentDate = new java.util.Date();
        if (dateTime.getTime() > currentDate.getTime()) {
            throw "Дата регистрации дневника не может быть больше текущего времени!";
        }
    }

}

function saveParams(aForm, aEntity, aCtx) {
    var username = aCtx.getSessionContext().getCallerPrincipal().getName();
    if (aForm.getParams() != null && aForm.getParams() != "") {
        Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean.saveParametersByProtocol(aForm.getMedCase(), aEntity, aForm.getParams(), username, aCtx.manager);
    }
}

function onCreate(aForm, aEntity, aCtx) {
    saveParams(aForm, aEntity, aCtx);
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
    var username = aCtx.getSessionContext().getCallerPrincipal().getName();
    if (aForm.username!=null && aForm.username!="" && !aForm.username.equals(username)) {
        if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Protocol/AllowEditAllProtocols"))
            throw "У Вас стоит ограничение на редактрование данного протокола!" +
            "<br><br> Текущий пользователь: " + username + ", протокол был создан пользователем: " + aForm.username;
        else {
            //регистрация события
            aCtx.manager.createNativeQuery("insert into ChangeJournal (classname,changedate,changetime,SerializationBefore,objectid) values ('VISITPROTOCOL',current_date,current_time,'"+aForm.username+"- -"+aForm.editUsername+"','"+aForm.id+"')").executeUpdate() ;
        }
    }
    saveParams(aForm, aEntity, aCtx);
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