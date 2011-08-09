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