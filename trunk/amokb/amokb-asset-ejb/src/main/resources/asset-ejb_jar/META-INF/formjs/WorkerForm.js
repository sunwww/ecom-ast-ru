/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx){
	var list =null;
    list = aCtx.manager.createQuery("from Worker where"
        	+"  person_id=:person"
        	+" and lpu_id = :lpu "
        	)
        	.setParameter("lpu",aForm.lpu)
        	.setParameter("person",aForm.person)
        	.getResultList() ;
        errorThrow(list) ;
	
}
function onPreSave(aForm,aEntity , aCtx) {
	var thisid = aForm.getId() ;
	var list =null;
    list = aCtx.manager.createQuery("from Worker where"
        	+"  person_id=:person"
        	+" and lpu_id = :lpu "
        	+" and id != '"+thisid+"'"
        )
        	.setParameter("lpu",aForm.lpu)
        	.setParameter("person",aForm.person)
        	.getResultList() ;
	errorThrow(list) ;
	
}
function errorThrow(aList) {
	if (aList!=null && aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var worker = aList.get(i) ;
			error = error+"<br> <a href='entityParentView-mis_worker.do?id="+worker.id+"'>"+(i+1)+". "+ worker.workerInfo + "</a>; " ;
		}
		throw "<u>Уже существует в данном подразделении сотрудник с такой персоной:</u>" + error ;
	}
}
