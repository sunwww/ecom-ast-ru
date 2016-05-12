function sendMessages(aForm,aCtx) {
	var user=Packages.ru.ecom.web.login.LoginInfo.find(aCtx.request.getSession(true)).getUsername() ;
	
	aCtx.request.setAttribute("addParam","cm.username='"+user+"' and (cm.isSystem='0' or cm.isSystem is null) ");
	aCtx.request.setAttribute("title","Список отправленных сообщений");
	return aCtx.createForward("/WEB-INF/actions/mis_customMessage/list.jsp") ;
}
function getMessages(aForm,aCtx) {
	var user=Packages.ru.ecom.web.login.LoginInfo.find(aCtx.request.getSession(true)).getUsername() ;
	aCtx.request.setAttribute("addParam","cm.recipient='"+user+"' and (cm.isSystem='0' or cm.isSystem is null)");
	aCtx.request.setAttribute("title","Список полученных сообщений");
	return aCtx.createForward("/WEB-INF/actions/mis_customMessage/list.jsp") ;
}
function getSystemMessages(aForm,aCtx) {
	var user=Packages.ru.ecom.web.login.LoginInfo.find(aCtx.request.getSession(true)).getUsername() ;
	aCtx.request.setAttribute("addParam","cm.recipient='"+user+"' and cm.dateReceipt is not null and (cm.isSystem='1')");
	aCtx.request.setAttribute("title","Список полученных системных сообщений");
	return aCtx.createForward("/WEB-INF/actions/mis_customMessage/list.jsp") ;
}
function receivedMessages(aForm,aCtx) {
	var user=Packages.ru.ecom.web.login.LoginInfo.find(aCtx.request.getSession(true)).getUsername() ;
	aCtx.request.setAttribute("addParam","cm.username='"+user+"' and cm.dateReceipt is not null and (cm.isSystem='0' or cm.isSystem is null)");
	aCtx.request.setAttribute("title","Список не доставленных сообщений");
	return aCtx.createForward("/WEB-INF/actions/mis_customMessage/list.jsp") ;
}
function notReceivedMessages(aForm,aCtx) {
	var user=Packages.ru.ecom.web.login.LoginInfo.find(aCtx.request.getSession(true)).getUsername() ;
	aCtx.request.setAttribute("addParam","cm.username='"+user+"' and cm.dateReceipt is null and (cm.isSystem='0' or cm.isSystem is null)");
	aCtx.request.setAttribute("title","Список не доставленных сообщений");
	return aCtx.createForward("/WEB-INF/actions/mis_customMessage/list.jsp") ;
}
function overdueMessages(aForm,aCtx) {
	var user=Packages.ru.ecom.web.login.LoginInfo.find(aCtx.request.getSession(true)).getUsername() ;
	
	aCtx.request.setAttribute("addParam","cm.username='"+user+"' and cm.dateReceipt is not null and cm.validityDate>CURRENT_DATE and (cm.isSystem='0' or cm.isSystem is null)");
	aCtx.request.setAttribute("title","Список просроченных сообщений");
	return aCtx.createForward("/WEB-INF/actions/mis_customMessage/list.jsp") ;
}