
function onPreDelete(aId, aCtx) {
	var manager =  aCtx.manager;
	if (!manager.createNativeQuery("select p.id from prescription p where id = "+aId+" and p.intakedate is not null")
		.getResultList().isEmpty()) {
		throw "Удаление назначения невозможно, т.к. был произведен забор биоматериала";
	}
	//При удалении назначения, если не оформлен визит, удаляем его. Если оформлен - не удаляем назначение
	startedPres = manager.createNativeQuery("select case when vis.datestart is not null then 1 else 0 end as pid, vis.id as visit,p.calendartime_id as cal " +
			" from prescription p " +
			" left join medcase vis on vis.timeplan_id=p.calendartime_id" +
			" where p.id="+aId ).getResultList();
	if (!startedPres.isEmpty()){
		var ids = startedPres.get(0);
		if (+ids[0]>0) {
			throw "По данному направлению был произведен прием, удаление невозможно!";
		} else {
			if (+ids[2]>0){
			manager.createNativeQuery("update workcalendartime set medcase_id=null where id="+ids[2]).executeUpdate();
			manager.createNativeQuery("delete from medcase where parent_id="+ids[1]+"  or (id='"+ids[1]+"' and datestart is null)").executeUpdate();
			}
		}
	}
	manager.createNativeQuery("update workcalendartime set prescription=null where prescription="+aId).executeUpdate();
}

/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date().getTime() ;
	aEntity.setEditDate(new java.sql.Date(date)) ;
	aEntity.setEditTime(new java.sql.Time (date)) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().getName()) ;
}

/**
* Перед созданием
 */
function onPreCreate(aForm, aCtx) {
	var ser = aForm.labList!=null && aForm.labList!="" ? aForm.labList.split("#").length : 0;
	if (ser===0) {
		throw "Не выбрана услуга!";
	}
}

//проверка на дубликаты номеров пробирок при анализе на ковид
function checkDoublesMaterialPCRId(aForm, aEntity, aCtx) {
	if (!aCtx.manager.createNativeQuery("select p.id" +
		" from prescription p " +
		" left join medservice ms on ms.id = p.medservice_id" +
		" where p.dtype='ServicePrescription' and ms.id=22347" +
		" and p.medservice_id is not null and p.canceldate is null and planstartdate=current_date+1" +
		" and p.materialPCRid='" + aForm.materialPCRId + "'").getResultList().isEmpty())
		throw "Уже есть такой анализ с таким номером пробирки на завтрашний день! Измените номер." +
	" <br/><a href='entityParentPrepareCreate-pres_servicePrescription.do?id="+aEntity.prescriptionList.id+"'>"+"Создать назначение заново"+"</a><br/>";
}

/**
 * После создания
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date();
	var username = aCtx.getSessionContext().getCallerPrincipal().toString();
	date = new java.sql.Date(date.getTime());
	var time = new java.sql.Time (date.getTime());
	aEntity.setCreateDate(date) ;
	aEntity.setCreateTime(time) ;
	aEntity.setCreateUsername(username) ;
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aEntity.setPrescriptSpecial(wf) ;
	var check1S = 0 ;
//	var pat = aEntity.prescriptionList.medCase.patient ;
	if (aForm.labList!=null && aForm.labList !="") {
		var addMedServicies = aForm.labList.split("#") ;
	//	var labMap = new java.util.HashMap() ;
		var prescriptType = null;
		var manager = aCtx.manager ;
		if (aForm.prescriptType!=null) {
			prescriptType = manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType,aForm.prescriptType) ;
		}
		if (addMedServicies.length>0  ) {
			var vss = manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream, aForm.serviceStream);
			var isOnlyPayed = vss!=null && vss.getIsPaidConfirmation()!=null && vss.getIsPaidConfirmation();
			var matId = null;
			for (var i=0; i<addMedServicies.length; i++) {
				var param = addMedServicies[i].split(":") ;
				var par1 = java.lang.Long.valueOf(param[0]) ;
				var par2 = (param[1])?Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(param[1]):null ;
				var par3 = (+param[2]>0)?java.lang.Long.valueOf(param[2]):null ; //Кабинет исследования
				var par4 = (+param[3]>0)?java.lang.Long.valueOf(param[3]):null ; //Место забора крови
				var par5 = (+param[4]>0)?java.lang.Long.valueOf(param[4]):null ; //WorkCalendarTime
				var par6 = (param[5]!=null)?""+param[5]:"" ; //Комментарий
				var caosId = param.length>6 ? java.lang.Long.valueOf(param[6]) : 0;
				var medService = manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,par1) ;
				matId = null;
				if (medService!=null && par2!=null) {
					var adMedService ;
					if (check1S==0) {
						adMedService = aEntity ;
						check1S=1;
					} else {
						adMedService=new Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription() ;
					}
					if (medService.code=='A26.08.027.999' && aEntity.prescriptionList.medCase!=null //если стационар и ковид
						&& ((''+ aEntity.prescriptionList.medCase).indexOf('DepartmentMedCase')!=-1 || (''+ aEntity.prescriptionList.medCase).indexOf('HospitalMedCase')!=-1)) {
						checkDoublesMaterialPCRId(aForm, aEntity, aCtx);
						adMedService.setMaterialPCRId(aForm.materialPCRId);
						var nextDate = new java.sql.Date(date.getTime() + 24 * 60 * 60 * 1000);
						adMedService.setIntakeDate(nextDate);

						var FORMAT = new java.text.SimpleDateFormat("hh:mm");
						adMedService.setIntakeTime(new java.sql.Time(FORMAT.parse('06:00').getTime()));

						adMedService.setIntakeUsername(username);
						if (par3 != null && !par3.equals(java.lang.Long(0))) {
							var intakeSpecial = manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, par3);
							adMedService.setIntakeSpecial(intakeSpecial);
						}
					}

					adMedService.setPrescriptionList(aEntity.getPrescriptionList()) ;
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
						var medServiceCabinet = manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par3) ;
						adMedService.setPrescriptCabinet(medServiceCabinet);	
					}
					if (par4!=null&&!par4.equals(java.lang.Long(0))) { //А стало так 
						var department = manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu,par4) ;
						adMedService.setDepartment(department);	
					}
					manager.persist(adMedService) ;
					if (par5!=null){
						var wct = manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime,par5) ;
						wct.setPrescription(adMedService.getId());
						adMedService.setCalendarTime(wct);
					}
					if (isOnlyPayed) {
						if (caosId>0) { //Если есть ИД оплаченной услуги - проставим соответствие
							var caos = manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService, caosId);
							caos.setServiceType('PRESCRIPTION');
							caos.setServiceId(adMedService.getId());
							manager.persist(caos);

						} else {
							if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/Contract/MakeUnpaidServices")
							|| (aForm.getUnpaidConfirmation()!=null && aForm.getUnpaidConfirmation()==true)) {
								var msg = new Packages.ru.ecom.mis.ejb.domain.prescription.AdminChangeJournal();
								msg.setCType("PRESCRIPTION_WITHOUT_PAYMENT");
								msg.setPatient(aEntity.prescriptionList.medCase.patient.id);
								msg.setAnnulRecord("Назначенная услуга '"+medService.code+" "+medService.name+"' не оплачена пациентом");
								msg.setPrescriptWorkFunction(aEntity.getPrescriptSpecial().getId());
								msg.setMedCase(aEntity.prescriptionList.medCase.id);
								msg.setCreateUsername(username);
                                msg.setCreateTime(time) ;
                                msg.setCreateDate(date) ;
                                manager.persist(msg);
							} else {
								throw "Услуга '"+medService.code+" "+medService.name+"' не оплачена пациентом!";
							}
						}
					}
				}
			}
		}
	}
}	

