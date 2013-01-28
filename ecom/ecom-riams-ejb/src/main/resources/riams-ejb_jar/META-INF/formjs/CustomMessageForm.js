/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
	var date = new java.util.Date() ;
	var dispatchDate = new java.sql.Date(date.getTime()) ;
	var dispatchTime = new java.sql.Time(date.getTime()) ;
	var username = aContext.getSessionContext().getCallerPrincipal().toString() ;
	aEntity.setDispatchDate(dispatchDate) ;
	aEntity.setDispatchTime(dispatchTime) ;
	aEntity.setUsername(username) ;
	if (+aForm.secUser) {
		var user = aContext.manager.find(Packages.ru.ecom.jaas.ejb.domain.SecUser,aForm.secUser) ;
		aEntity.setRecipient(user.login) ;
	} else {
		var listUser = aContext.manager.createQuery("from SecUser").getResultList() ;
		var validityDate = aEntity.validityDate ;
		for (var i=0;i<listUser.size();i++) {
			var user=listUser.get(i) ;
			if (i==0) {
				aEntity.setRecipient(user.login) ;
			} else {
				var mes = new Packages.ru.ecom.ejb.services.live.domain.CustomMessage() ;
				mes.setMessageText(aForm.messageText) ;
				mes.setMessageTitle(aForm.messageTitle) ;
				mes.setRecipient(user.login) ;
				mes.setDispatchDate(dispatchDate) ;
				mes.setDispatchTime(dispatchTime) ;
				mes.setUsername(username) ;
				mes.setValidityDate(validityDate) ;
				aContext.manager.persist(mes) ;
			}
		}
	}
	
}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	if ((aForm.isAllUsers==null || aForm.isAllUsers==false) && (+aForm.secUser==0)) {
		throw "Необходимо заполнить либо отправить сообщение всем пользователям, либо конкретного пользователя" ;
	}
	
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
	throw "Нельзя изменить отправленное сообщение" ;
}

/**
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}

