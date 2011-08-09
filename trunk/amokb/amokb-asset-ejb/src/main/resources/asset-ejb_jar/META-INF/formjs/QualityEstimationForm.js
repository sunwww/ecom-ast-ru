function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

	
}

function onCreate(aForm, aEntity, aContext){
	var crits = aForm.criterions.split("#") ;
	if (crits.length>0 && aForm.criterions!=null && aForm.criterions !="") {
		var id = aEntity.id ;
		for (var i=0; i<crits.length; i++) {
			var param = crits[i].split(":") ;
			aContext.manager.createNativeQuery("insert into QualityEstimationCrit (estimation_id,mark_id) values ('"+id+"','"+param[1]+"')").executeUpdate() ;
		}
	}
}
function onSave(aForm, aEntity, aContext){
	var id=aForm.id ;
	aContext.manager.createNativeQuery("delete from QualityEstimationCrit where estimation_id='"+id+"'").executeUpdate() ;
	onCreate(aForm, aEntity, aContext) ;
	
}
function onPreSave(aForm,aEntity , aCtx) {

}