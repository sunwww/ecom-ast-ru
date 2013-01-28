/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	var login = aForm.getLogin() ;
	var list =null;
	if (login!=null && !login.equals("")) {		
        list = aCtx.manager.createQuery("from SecUser where"
        	+" login = :login"
        	)
        	.setParameter("login",login)
        	.getResultList() ;
	} 
	errorThrow(list) ;
	aForm.setPassword(Packages.ru.ecom.jaas.ejb.service.SecUserServiceBean.getHashPassword(aForm.getLogin(),aForm.getPassword())) ;
	aForm.setIsHash(true) ;
}

function onPreSave(aForm,aEntity , aCtx) {
	var login = aForm.getLogin() ;
	var thisid = aForm.getId() ;
	var list =null;
	if (login!=null && !login.equals("")) {		
        list = aCtx.manager.createQuery("from SecUser where"
        	+" login = :login"
        	+" and id != '"+thisid+"'"
        )
        	.setParameter("login",login)
        	.getResultList() ;
	} else {
	}
	errorThrow(list) ;
	aForm.setPassword(Packages.ru.ecom.jaas.ejb.service.SecUserServiceBean.getHashPassword(aForm.getLogin(),aForm.getPassword())) ;	
	aForm.setIsHash(true) ;
}
function onCreate(aForm, aEntity, aContext) {
	if (+aForm.workFunction>0) {
		var wf = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.PersonalWorkFunction
				,aForm.workFunction) ;
		wf.setSecUser(aEntity) ;
		aContext.manager.persist(wf) ;
	}
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	var username=aContext.getSessionContext().getCallerPrincipal().toString() ;
	aEntity.setCreateUsername(username) ;
	if (+aForm.userCopy>0) {
		var userCopy = aContext.manager.find(Packages.ru.ecom.jaas.ejb.domain.SecUser
				,aForm.userCopy) ;
		var roles = userCopy.getRoles() ;
		var newroles = new java.util.ArrayList() ;
		
		for (var i=0;i<roles.size();i++) {
			var role =roles.get(i) ; 
			newroles.add(role)
			var jour = new Packages.ru.ecom.ejb.services.live.domain.journal.ChangeJournal() ;
			jour.setClassName("SecUser_SecRole") ;
			jour.setChangeDate(new java.sql.Date(date.getTime())) ;
			jour.setChangeTime(new java.sql.Time(date.getTime())) ;
			jour.setLoginName(username) ;
			jour.setComment(" copy user:"+aForm.userCopy+" userid="+aEntity.id+" roleid="+role.id) ;
			jour.setSerializationAfter("user:"+aEntity.id) ;
			jour.setSerializationBefore("role:"+role.id) ;
			aContext.manager.persist(jour) ;
		}
		aEntity.setRoles(newroles) ;
	}
}
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}
function errorThrow(aList) {
	if (aList==null || aList.size()>0) {
		if (aList==null) throw "Имя пользователя обязательно!!!";
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var user = aList.get(i) ;
			error = error+" ИД = "+user.id+" ПОЛНОЕ ИМЯ: "+ user.fullname + " ЗАБЛОКИРОВАН: "+ (user.disable==null||user.disable==false?"НЕТ":"ДА") + "; " ;
		}
		throw "Имя пользователя уже занято!!!" + error ;
	}
}