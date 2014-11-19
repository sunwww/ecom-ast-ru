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
	var isTemp = false ;
	var prescriptType = null;
	var pat = aEntity.medCase.patient ;
	if (!aForm instanceof Packages.ru.ecom.mis.ejb.form.prescription.template.PrescriptListForm) {
		prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
	} else {
		isTemp = true ;
	}
	
	
	if (aForm.drugList!=null && aForm.drugList !="") {
		var addDrugs= aForm.drugList.split("#") ;
		var prescriptType =null;
		if (!aForm instanceof Packages.ru.ecom.mis.ejb.form.prescription.template.PrescriptListForm)
		{
			prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
		}
	
		if (addDrugs.length>0  ) {
			//throw "All OK"+addMedServicies.length;
			for (var i=0; i<addDrugs.length; i++) {
				var param = addDrugs[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (param[2])?java.lang.Long.valueOf(param[2]):null ; //method
				var par4 = (param[3])?java.lang.Integer.valueOf(param[3]):null ; //frequency
				var par4u = (param[4])?java.lang.Long.valueOf(param[4]):null ; //frequencyUnit
				var par5 = (param[5])?java.math.BigDecimal.valueOf(param[5]):null ; //amount
				var par5u = (param[6])?java.lang.Long.valueOf(param[6]):null ; //amountUnit
				var par6 = (param[7])?java.lang.Integer.valueOf(param[7]):null ; //duration
				var par6u = (param[8])?java.lang.Long.valueOf(param[8]):null ; //durationUnit
				var drug = aCtx.manager.find(Packages.ru.ecom.mis.ejb.uc.privilege.domain.VocDrugClassify,par1) ;
						
				if (drug!=null) {
					var adMedService=new Packages.ru.ecom.mis.ejb.domain.prescription.DrugPrescription() ;
					adMedService.setPrescriptionList(aEntity) ;
					adMedService.setPrescriptSpecial(aEntity.getWorkFunction()) ;
					adMedService.setDrug(drug) ;
					adMedService.setPlanStartDate(par2) ;
					adMedService.setPrescriptType(prescriptType) ;
					adMedService.setCreateUsername(username) ;
					adMedService.setCreateTime(time) ;
					adMedService.setCreateDate(date) ;
					if (par3!=null&&!par3.equals(java.lang.Long(0))) {
						adMedService.setMethod(aCtx.manager.find(Packages.ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugMethod,par3));
						if (par4!=null&&!par4.equals(java.lang.Long(0))) {
							adMedService.setFrequency(par4);
						}
						if (par4u!=null&&!par4u.equals(java.lang.Long(0))) {
							adMedService.setFrequencyUnit(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocFrequencyUnit,par4u));
						}
						if (par5!=null&&!par5.equals(java.lang.Long(0))) {
							adMedService.setAmount(par5);
						}
						if (par5u!=null&&!par5u.equals(java.lang.Long(0))) {
							adMedService.setAmountUnit(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocDrugAmountUnit,par5u));
						}
						if (par6!=null&&!par6.equals(java.lang.Long(0))) {
							adMedService.setDuration(par6);
						}
						if (par6u!=null&&!par6u.equals(java.lang.Long(0))) {
							adMedService.setDurationUnit(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocDurationUnit,par6u));
						}
						}
					aCtx.manager.persist(adMedService) ;
				}
			}
		}
	}	
	
	if (aForm.labList!=null && aForm.labList !="") {
		var addMedServicies = aForm.labList.split("#") ;
		
		var labMap = new java.util.HashMap() ; ;
		var matId=null ;

		if (addMedServicies.length>0  ) {
			//throw "All OK"+addMedServicies.length;
			for (var i=0; i<addMedServicies.length; i++) {
				var param = addMedServicies[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (param[2])?java.lang.Long.valueOf(param[2]):null ;
				var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
				
				if (medService!=null) {
					var isLab = false ;
					if (medService.serviceType!=null && medService.serviceType.code.equals("LABSURVEY")&&par2!=null) {
						if (!isTemp) {
							matId = labMap.get(""+par2);
							if (matId==null) {
								var lPl =aCtx.manager("select p.materialId from prescription p left join PrescriptList pl on pl.id=p.prescriptionList_id left join medcase mc on mc.id=pl.medCase_id where mc.patient_id='"+pat.id+"' and p.planStartDate=to_date('"+param[1]+"','dd.mm.yyyy')").getResultsList();
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
							}
						
						}
					}
					var adMedService=new Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription() ;
					adMedService.setPrescriptionList(aEntity) ;
					adMedService.setPrescriptSpecial(aEntity.getWorkFunction()) ;
					adMedService.setMedService(medService) ;
					adMedService.setMaterialId(matId!=null?""+matId:"") ;

					adMedService.setPlanStartDate(par2) ;
					adMedService.setPrescriptType(prescriptType) ;
					adMedService.setCreateUsername(username) ;
					adMedService.setCreateTime(time) ;
					adMedService.setCreateDate(date) ;
					if (par3!=null&&!par3.equals(java.lang.Long(0))) {
						var medServiceCabinet = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par3) ;
						adMedService.setPrescriptCabinet(medServiceCabinet);	
					}
					aCtx.manager.persist(adMedService) ;
					matId=null ;
				}
			}
		}
	}
}