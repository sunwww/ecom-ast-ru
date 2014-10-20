/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}
/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var addMedServicies = aForm.labList.split("#") ;
	if (addMedServicies.length>0 && aForm.labList!=null && aForm.labList !="") {
		//var id = aEntity.id ;
		//var account = aEntity.account ;
		for (var i=0; i<addMedServicies.length; i++) {
			var param = addMedServicies[i].split(":") ;
			//throw ""+ addMedServicies[i] ;
			var par1 = java.lang.Long.valueOf(param[0]) ;
			var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseDate(param[1]):null ;
			var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
			
			if (medService!=null && par2!=null) {
				var adMedService=new Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription() ;
				adMedService.setPrescriptionList(aEntity) ;
				adMedService.setMedService(medService) ;
				adMedService.setPlanStartDate(par2) ;
				aCtx.manager.persist(adMedService) ;
			}
		}
	}
}