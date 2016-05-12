/**
 * Перед удалением
 */

function onPreDelete(aId, aCtx) {
	var startedPres = aCtx.manager.createNativeQuery("select p.id from prescription p where prescriptionlist_id = "+aId+" and p.intakedate is not null").getResultList();
	if (startedPres.size()>0) {
		throw "Удаление листа назначений невозможно, т.к. по некоторым назначениям произведен забор биоматериала";
	}
	var pres = aCtx.manager.createNativeQuery("select p.id from prescription p where prescriptionlist_id = "+aId).getResultList();
	if (pres.size()>0) {
		for (var i=0;i<pres.size();i++){
			aCtx.manager.createNativeQuery("update workcalendartime set prescription=null where prescription="+pres.get(i)).executeUpdate();
		}
	}
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
	var isTemp = false ;
	var prescriptType = null;
	var pat = null;
	if (aForm.getClass().getName().equals("ru.ecom.mis.ejb.form.prescription.PrescriptListForm")) {
		prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
		pat = aEntity.medCase!=null?aEntity.medCase.patient:null ;
	} else {
		isTemp = true ;
	}
	//throw isTemp ;
	
	
	if (aForm.drugList!=null && aForm.drugList !="") {
		var addDrugs= aForm.drugList.split("#") ;
		var prescriptType =null;
		if (!isTemp) {
			prescriptType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
		}
	
		if (addDrugs.length>0  ) {
			//throw "All OK"+addMedServicies.length;
			for (var i=0; i<addDrugs.length; i++) {
				var param = addDrugs[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (+param[2]>0)?java.lang.Long.valueOf(param[2]):null ; //method
				var par4 = (param[3])?java.lang.Integer.valueOf(param[3]):null ; //frequency
				var par4u = (+param[4]>0)?java.lang.Long.valueOf(param[4]):null ; //frequencyUnit
				var par5 = (param[5])?java.math.BigDecimal.valueOf(param[5]):null ; //amount
				var par5u = (+param[6]>0)?java.lang.Long.valueOf(param[6]):null ; //amountUnit
				var par6 = (+param[7]>0)?java.lang.Integer.valueOf(param[7]):null ; //duration
				var par6u = (+param[8]>0)?java.lang.Long.valueOf(param[8]):null ; //durationUnit
				var par7 = (param[9])?""+param[9]:""; //comment
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
					}
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
					if (par7!=null&&!par7.equals("")) {
						adMedService.setComments(par7);
					}
					
					aCtx.manager.persist(adMedService) ;
				}
			}
		}
	}	
	
	if (aForm.labList!=null && aForm.labList !="") {
		var addMedServicies = aForm.labList.split("#") ;
		
		var labMap = new java.util.HashMap();
		var matId=null ;

		if (addMedServicies.length>0  ) {
			for (var i=0; i<addMedServicies.length; i++) {
				var param = addMedServicies[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (param[2]&&(+param[2]>0))?java.lang.Long.valueOf(param[2]):null ;
				var par4 = (param[3]&&(+param[3]>0))?java.lang.Long.valueOf(param[3]):null ;
				var par5 = (param[4]&&(+param[4]>0))?java.lang.Long.valueOf(param[4]):null ; //WorkCalendarTime
				//var par5 = (param[4])?java.lang.Long.valueOf(param[4]):null ;
				var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
				
				if (medService!=null) {
					if (!isTemp&&pat!=null&&medService.serviceType!=null 
							&& medService.serviceType.code.equals("LABSURVEY")&&par2!=null) {
						var key =""+pat.id+"#"+par2;
						matId = Packages.ru.ecom.mis.ejb.service.prescription.PrescriptionServiceBean.getPatientDateNumber(labMap, key, pat.id, par2, aCtx.manager); 
						labMap.put(key, matId);
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
					var medServiceCabinet ;
					if (par3!=null) {
						medServiceCabinet = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par3) ;
						adMedService.setPrescriptCabinet(medServiceCabinet);	
					}
					if (par4!=null) {
						var dep = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu,par4) ;
						adMedService.setDepartment(dep);
					}
					aCtx.manager.persist(adMedService) ;
				//	throw "WAZZZAP!!!!1"+par5;
					if (par5!=null && !par5.equals(java.lang.Long(0))) {
						if (!isTemp&&pat!=null&&medService.serviceType!=null 
								&& medService.serviceType.code.equals("DIAGNOSTIC")&&par2!=null) {
/*							var wct = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime,par5) ;
							var v = new Packages.ru.ecom.mis.ejb.domain.medcase.Visit() ;
							v.serviceStream(hospStream) ;
							v.setWorkFunctionPlan(medServiceCabinet) ;
							v.setPatient() ;
							v.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(new java.util.Date())) ;
							v.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;							
							if (wct.medCase==null) {
								v.setTimePlan(wct) ;
								v.setDatePlan(wct.workCalendarDay) ;
							}
							v.setOrderWorkFunction() ;*/
						} else if (medService.serviceType.code.equals("OPERATION")) {
							//throw ""+aEntity.getWorkFunction() ;
							var wct = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime,par5) ;
							adMedService.setCalendarTime(wct);
							wct.setPrescription(adMedService.getId());
							
						}
						}
					}
				//	aCtx.manager.refresh(adMedService) ;
					matId=null ;
				}
			}
		}
	}
