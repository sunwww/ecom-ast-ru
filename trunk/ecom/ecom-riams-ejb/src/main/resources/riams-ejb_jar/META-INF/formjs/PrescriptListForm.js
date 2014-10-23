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
	var username = aCtx.getSessionContext().getCallerPrincipal().toString();
	var date = new java.sql.Date(date.getTime());
	var time = new java.sql.Time (date.getTime());
	aEntity.setCreateDate(date) ;
	aEntity.setCreateTime(time) ;
	aEntity.setCreateUsername(username) ;
	//throw ""+aForm.labList;
	if (aForm.labList!=null && aForm.labList !="") {
		var addMedServicies = aForm.labList.split("#") ;
		var prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
	
		if (addMedServicies.length>0  ) {
			//throw "All OK"+addMedServicies.length;
			for (var i=0; i<addMedServicies.length; i++) {
				var param = addMedServicies[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (param[2])?java.lang.Long.valueOf(param[2]):null ;
				var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
								
				if (medService!=null && par2!=null) {
					var adMedService=new Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription() ;
					adMedService.setPrescriptionList(aEntity) ;
					adMedService.setMedService(medService) ;
					adMedService.setPlanStartDate(par2) ;
					adMedService.setPrescriptType(prescriptType) ;
					adMedService.setCreateUsername(username) ;
					adMedService.setCreateTime(time) ;
					adMedService.setCreateDate(date) ;
					if (par3!=null) {
						var medServiceCabinet = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par3) ;
						adMedService.setPrescriptCabinet(medServiceCabinet);	
					}
					aCtx.manager.persist(adMedService) ;
				}
			}
		}
	}
}