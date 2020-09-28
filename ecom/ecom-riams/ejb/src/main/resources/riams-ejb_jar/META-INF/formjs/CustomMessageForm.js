/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
	var date = new java.util.Date();
	var dispatchDate = new java.sql.Date(date.getTime());

	var dispatchTime = new java.sql.Time(date.getTime());
	var cal = java.util.Calendar.getInstance();
	var dateMax = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(dispatchDate, dispatchTime);
	cal.setTime(dateMax);
	cal.add(java.util.Calendar.MINUTE, 15);
	var username = aContext.getSessionContext().getCallerPrincipal().toString();
	aEntity.setDispatchDate(dispatchDate);
	aEntity.setDispatchTime(dispatchTime);
	if (aForm.isEmergency != null && aForm.isEmergency.equals(java.lang.Boolean.TRUE)) {
		aEntity.setValidityDate(new java.sql.Date(cal.getTime().getTime()));
		aEntity.setValidityTime(new java.sql.Time(cal.getTime().getTime()));
	}
	aEntity.setUsername(username);
	var validityDate = aEntity.validityDate;
	var validityTime = aEntity.validityTime;
	var lpus = getLpuString(aForm.getLpus());
	var sql = "";
	if (+aForm.secUser) {
		var user = aContext.manager.find(Packages.ru.ecom.jaas.ejb.domain.SecUser, aForm.secUser);
		aEntity.setRecipient(user.login);
	} else if (+aForm.secRole) {
		sql = "select su.id,su.login from SecUser su left join secuser_secrole susr on susr.secuser_id=su.id where susr.roles_id='"
			+ aForm.secRole + "' and (su.disable='0' or su.disable is null) ";
	} else if (lpus != '') {
		sql = "select distinct su.id,su.login" +
			" from WorkFunction wf" +
			" left join WorkFunction gwf on gwf.id=wf.group_id" +
			" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" +
			" left join Worker w on w.id=wf.worker_id" +
			" left join MisLpu ml on ml.id=w.lpu_id" +
			" left join Worker sw on sw.person_id=w.person_id" +
			" left join WorkFunction swf on swf.worker_id=sw.id" +
			" left join SecUser su on su.id=swf.secUser_id" +
			" where su.id is not null and (wf.archival is null or wf.archival='0') and w.lpu_id in (" + lpus + ")";
	} else {
		sql = "select su.id,su.login from SecUser su where su.disable='0' or su.disable is null ";
	}
	if (sql!='') {
		var listUser = aContext.manager.createNativeQuery(sql).getResultList();
		for (var i = 0; i < listUser.size(); i++) {
			var user = listUser.get(i);
			if (i == 0)
				aEntity.setRecipient("" + user[1]);
			else
				createMessage(aContext, aForm.messageText, aForm.messageTitle, "" + user[1]
					, dispatchDate, dispatchTime, username, validityDate, validityTime, aForm.getIsEmergency(),aForm.getMessageUrl());
		}
	}
}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	if ((aForm.isAllUsers==null || aForm.isAllUsers==false) &&(+aForm.secRole==0)
		&& (+aForm.secUser==0) && getLpuString(aForm.getLpus())=="") {
		throw "Необходимо заполнить либо отправить сообщение всем пользователям, либо конкретного пользователя, либо отметить пользователей с ролью, либо указать отделения пользвателя" ;
	}
	
}

/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
	throw "Нельзя изменить отправленное сообщение" ;
}

//Создать сообщение с переданными свойствами
function createMessage(aContext,messageText,messageTitle,recipient,dispatchDate,dispatchTime,username,validityDate,validityTime,isEmergency,messageUrl) {
	var mes = new Packages.ru.ecom.ejb.services.live.domain.CustomMessage() ;
	mes.setMessageText(messageText) ;
	mes.setMessageTitle(messageTitle) ;
	mes.setRecipient(recipient) ;
	mes.setDispatchDate(dispatchDate) ;
	mes.setDispatchTime(dispatchTime) ;
	mes.setUsername(username) ;
	mes.setValidityDate(validityDate) ;
	mes.setValidityTime(validityTime) ;
	mes.setIsEmergency(isEmergency) ;
	mes.setMessageUrl(messageUrl)
	aContext.manager.persist(mes) ;
}

//Получить отделения через запятую из json many
function getLpuString(aJsonString) {
	var obj = new Packages.org.json.JSONObject(aJsonString) ;
	var ar = obj.getJSONArray("childs");
	var ids = new Packages.java.lang.StringBuilder() ;

	for (var i = 0; i < ar.length(); i++) {
		var child = ar.get(i);
		var jsId = java.lang.String.valueOf(child.get("value"));
		if (jsId!=null && jsId!="" || jsId=="0") {
			var jsonId = java.lang.Long.valueOf(jsId);
			ids.append(",").append(jsonId);
		}
	}
	return (''+ids).length>0?
		ids.substring(1) : '';
}