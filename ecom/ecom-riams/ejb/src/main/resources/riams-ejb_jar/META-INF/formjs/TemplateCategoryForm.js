function onPreCreate(aForm, aCtx) {
	var parent ;
	if (aForm.getParent()>0) {
		parent = " ="+aForm.getParent() ;
	} else {
		parent = " is null" ;
	}
    list = aCtx.manager.createQuery("from TemplateCategory where"
        	+" parent_id " +parent
        	+" and name = :name"
        )
        	.setParameter("name",aForm.getName())
        	.getResultList() ;
	errorThrow(list) ;

	aForm.setDateCreate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
function onPreSave(aForm,aEntity , aCtx) {
	var thisid = aForm.getId() ;
	var list =null;
	var parent ;
	if (aForm.getParent()>0) {
		parent = " ="+aForm.getParent() ;
	} else {
		parent = " is null" ;
	}
    list = aCtx.manager.createQuery("from TemplateCategory where"
        	+" parent_id " +parent
        	+" and name = :name"
        	+" and id != '"+thisid+"'"
        )
        	.setParameter("name",aForm.getName())
        	.getResultList() ;
	errorThrow(list) ;
	
}
function errorThrow(aList) {
	if (aList==null || aList.size()>0) {
		if (aList==null) throw "Категория с таким именем уже существует на данном уровне!!!";
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var user = aList.get(i) ;
			error = error+" ИД = "+user.id+" ПОЛНОЕ ИМЯ: "+ user.name + "; " ;
		}
		throw "Название категории занято!!!" + error ;
	}
}