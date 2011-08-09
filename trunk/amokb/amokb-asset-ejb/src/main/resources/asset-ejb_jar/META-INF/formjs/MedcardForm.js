/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	var number = aForm.getNumber() ;
	var list ;
    list = aCtx.manager.createQuery("from Medcard where number = :number"
       	)
       	.setParameter("number",number)
       	.getResultList() ;
		
	errorThrow(list, "В базе уже существует медкарта с такими номером") ;
	
}

function onPreSave(aForm,aEntity , aCtx) {
	var number = aForm.getNumber() ;
	var thisid = aForm.getId() ;
	var list ;
    list = aCtx.manager.createQuery("from Medcard where "
       	+" number = :number "
       	+" and id != '"+thisid+"'"
       	)
       	.setParameter("number",number)
       	.getResultList() ;
	errorThrow(list,"В базе уже существует медкарта с такими номером") ;
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error = "";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" <a href='entityParentView-poly_medcard.do?id="+doc.id+"'>МЕДКАРТА: "+doc.number + "</a><br/>" ;
		}
		throw aError + error ;
	}
}