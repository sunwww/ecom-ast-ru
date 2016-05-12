/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	var name = aForm.name ;
	var list ;
    list = aCtx.manager.createQuery("from VocDrugUnlicensedName where name = :name"
       	)
       	.setParameter("name",name)
       	.getResultList() ;
		
	errorThrow(list, "В базе уже существует Лек.Ср с таким названием") ;
	
}

function onPreSave(aForm,aEntity , aCtx) {
	var name = aForm.name ;
	var thisid = aForm.getId() ;
	var list ;
    list = aCtx.manager.createQuery("from VocDrugUnlicensedName where "
       	+" UPPER(name) = UPPER(:name) "
       	+" and id != '"+thisid+"'"
       	)
       	.setParameter("name",name)
       	.getResultList() ;
	errorThrow(list,"В базе уже существует Лек.Ср с таким названием") ;
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error = "";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" <a href='entityParentView-voc_drugUN.do?id="+doc.id+"'>Лек.Ср: "+doc.name + "</a><br/>" ;
		}
		throw aError + error ;
	}
}