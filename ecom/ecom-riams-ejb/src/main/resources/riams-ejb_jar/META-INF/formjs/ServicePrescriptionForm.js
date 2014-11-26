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
	var check1S = 0 ;
	var pat = aEntity.prescriptionList.medCase.patient ;
//	throw ""+aForm.labList;	
	if (aForm.labList!=null && aForm.labList !="") {
		var addMedServicies = aForm.labList.split("#") ;
		var labMap = new java.util.HashMap() ;
		var prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
	
		if (addMedServicies.length>0  ) {
//			throw "All OK"+addMedServicies.length;
			for (var i=0; i<addMedServicies.length; i++) {
				var param = addMedServicies[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (param[2])?java.lang.Long.valueOf(param[2]):null ;
				var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
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
						/*matId = labMap.get(""+par2);
						//throw ""+matId ; 
						if (matId==null) {
							var lPl =aCtx.manager.createNativeQuery("select p.materialId from prescription p left join PrescriptionList pl on pl.id=p.prescriptionList_id left join medcase mc on mc.id=pl.medCase_id where mc.patient_id='"+pat.id+"' and p.planStartDate=to_date('"+param[1]+"','dd.mm.yyyy') and p.materialId is not null and p.materialId!='' order by p.materialId desc ").getResultList();
							if (lPl.size()>0) {
								matId = lPl.get(0) ;
							}
							if (matId==null) {
								var seqHelper = Packages.ru.ecom.ejb.sequence.service.SequenceHelper.getInstance() ;
								matId=seqHelper.startUseNextValueNoCheck("Prescription#Lab#"+par2, aCtx.manager);
							}
							if (matId!=null) {
								
								labMap.put(""+par2,matId) ;
							} 
							//	throw "matID"+matId;

						}*/
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
					if (par3!=null&&!par3.equals(java.lang.Long(0))) {
						var medServiceCabinet = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par3) ;
						adMedService.setPrescriptCabinet(medServiceCabinet);	
					}
					aCtx.manager.persist(adMedService) ;
				}
			}
		}
	}
}	

