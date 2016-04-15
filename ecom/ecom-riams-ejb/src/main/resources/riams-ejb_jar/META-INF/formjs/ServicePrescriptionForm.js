
function onPreDelete(aId, aCtx) {
	
	var startedPres = aCtx.manager.createNativeQuery("select p.id from prescription p where id = "+aId+" and p.intakedate is not null").getResultList();
	if (startedPres.size()>0) {
		throw "Удаление назначения невозможно, т.к. был произведен забор биоматериала";
	}
		aCtx.manager.createNativeQuery("update workcalendartime set prescription=null where prescription="+aId).executeUpdate();
}

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
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aEntity.setPrescriptSpecial(wf) ;
	var check1S = 0 ;
	var pat = aEntity.prescriptionList.medCase.patient ;
	//throw ""+aForm.labList;	
	if (aForm.labList!=null && aForm.labList !="") {
		var addMedServicies = aForm.labList.split("#") ;
		var labMap = new java.util.HashMap() ;
		var prescriptType = null;
		if (aForm.prescriptType!=null) {
			prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
		}
		if (addMedServicies.length>0  ) {
			var matId = null;
//			throw "All OK"+addMedServicies.length;
			for (var i=0; i<addMedServicies.length; i++) {
				var param = addMedServicies[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (+param[2]>0)?java.lang.Long.valueOf(param[2]):null ; //Кабинет исследования
				var par4 = (+param[3]>0)?java.lang.Long.valueOf(param[3]):null ; //Место забора крови
				var par5 = (+param[4]>0)?java.lang.Long.valueOf(param[4]):null ; //WorkCalendarTime
				var par6 = (param[5]!=null)?""+param[5]:"" ; //Комментарий
				var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
				matId = null;
				if (medService!=null && par2!=null) {
					var adMedService ;
					if (check1S==0) {
						adMedService = aEntity ;
						check1S=1;
					} else {
						adMedService=new Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription() ;
					}
					if (medService.serviceType.code.equals("LABSURVEY")&&par2!=null) {
						var key =""+pat.id+"#"+par2;
						matId = Packages.ru.ecom.mis.ejb.service.prescription.PrescriptionServiceBean.getPatientDateNumber(labMap, key, pat.id, par2, aCtx.manager); 
						labMap.put(key, matId);
						
					}
					adMedService.setPrescriptionList(aEntity.getPrescriptionList()) ; // ?
					adMedService.setPrescriptSpecial(aEntity.getPrescriptSpecial()) ;
					adMedService.setMedService(medService) ;
					adMedService.setPlanStartDate(par2) ;
					adMedService.setPrescriptType(prescriptType) ;
					adMedService.setMaterialId(matId!=null?""+matId:"") ;
					adMedService.setCreateUsername(username) ;
					adMedService.setCreateTime(time) ;
					adMedService.setCreateDate(date) ;
					adMedService.setComments(par6) ;
					if (par3!=null&&!par3.equals(java.lang.Long(0))) { 
						var medServiceCabinet = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par3) ;
						adMedService.setPrescriptCabinet(medServiceCabinet);	
					}
					if (par4!=null&&!par4.equals(java.lang.Long(0))) { //А стало так 
						var department = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu,par4) ;
						adMedService.setDepartment(department);	
					}								
					aCtx.manager.persist(adMedService) ;
					if (par5!=null){
						var wct = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime,par5) ;
						wct.setPrescription(adMedService.getId());
						adMedService.setCalendarTime(wct);
					}
					
				}
			}
		}
	}
}	

