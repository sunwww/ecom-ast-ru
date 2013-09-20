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

function onPreDelete(aEntityId, aContext) {
	var medcard = aContext.manager.find(Packages.ru.ecom.poly.ejb.domain.Medcard
			, new java.lang.Long(aEntityId)) ;
	if (medcard!=null) {
		var other_medcard_sql="select mc.id from Medcard mc where person_id='"+medcard.person.id+"' and mc.id!='"+medcard.id+"'" ;
		var other_medcard_list=aContext.manager.createNativeQuery(other_medcard_sql).getResultList() ;
		if (!other_medcard_list.isEmpty()) {
			var newmedcard=other_medcard_list.get(0) ;
			aContext.manager.createNativeQuery("update medcase set medcard_id="+newmedcard+" where medcard_id="+medcard.id).executeUpdate() ;
			aContext.manager.createNativeQuery("update ticket set medcard_id="+newmedcard+" where medcard_id="+medcard.id).executeUpdate() ;
		} else {
			aContext.manager.createNativeQuery("delete from ticket where medcard_id="+medcard.id).executeUpdate() ;
			aContext.manager.createNativeQuery("delete from medcase where medcard_id="+medcard.id+" and dtype='PolyclinicMedCase'").executeUpdate() ;
		}
	}
	
}
