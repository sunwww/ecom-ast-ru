function onSave(aForm, aEntity, aCtx) {
	saveAdditionData(aForm,aEntity,aCtx) ;
	
}
function onCreate(aForm, aEntity, aContext) {
	saveAdditionData(aForm,aEntity,aContext) ;
}
function getObject(aCtx,aId,aClazz) {
	return (aId==null||aId=='0'||aId=='')?null:aCtx.manager.find(aClazz, aId) ;
}

function saveAdditionData(aForm,aEntity,aCtx) {
	//aEntity.setFinishFunction
	//aEntity.setOwnerFunction
	//aEntity.setOwnerFunction
	var medcard = getObject(aCtx, aForm.getMedcard(), Packages.ru.ecom.poly.ejb.domain.Medcard);
	//throw ""+medcard ;
	if (aEntity.patient==null) aEntity.patient=medcard.person;
	aEntity.setFinishFunction(aEntity.getOwnerFunction());
	aEntity.setStartFunction(aEntity.getOwnerFunction());
	aCtx.manager.persist(aEntity) ;
	//throw ""+ aForm.getOtherTicketDates() ;
		if ((aForm.getOtherTicketDates()!=null&&aForm.getOtherTicketDates()!='')||(aForm.getDateStart()!=null&&aForm.getDateStart()!='')) {
			var otherDates = aForm.getOtherTicketDates().split(":");
			var ddate = true;
			var date = Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(aEntity.getDateStart());
			//throw ""+date+" - "+otherDates.length;
			for (var i=0;i<otherDates.length;i++) {
				if (otherDates[i].equals(date)) {
					ddate=false;
				}
			}
			//throw ""+aForm.getOtherTicketDates()+"<>"+date;
			if (ddate) {
				otherDates = (aForm.getOtherTicketDates()+":"+date).split(":");
			}
			//var servStream = getObject(aCtx, aForm.getServiceStream(), Packages.ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream);
			var visitReason = getObject(aCtx, aForm.getVisitReason(), Packages.ru.ecom.poly.ejb.domain.voc.VocReason);
			var visitResult = getObject(aCtx, aForm.getVisitResult(), Packages.ru.ecom.poly.ejb.domain.voc.VocVisitResult);
			var workPlaceType = getObject(aCtx, aForm.getWorkPlaceType(), Packages.ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType);
			var vip =  getObject(aCtx, aForm.getConcludingActuity(), Packages.ru.ecom.poly.ejb.domain.voc.VocIllnesPrimary);
			var vtt = getObject(aCtx, aForm.getConcludingTrauma(), Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocTraumaType) ;
			var mkb = getObject(aCtx, aForm.getIdc10(),Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10) ;
			var ordLpu = getObject(aCtx, aForm.getOrderLpu(), Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu) ;
			var vocConcomType = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DischargeMedCaseSaveInterceptor.getVocByCode(aCtx.manager,"VocPriorityDiagnosis","1");
			//throw aForm.getServiceStream()+""+servStream ;
		if (otherDates.length>0) {
			for (var i=0;i<otherDates.length;i++) {
				if (""+otherDates[i]=="") {continue;}
				if (Packages.ru.nuzmsh.util.format.DateFormat.isHoliday(""+otherDates[i]).booleanValue()) {continue;} //На всякий случай
				var ticket = new Packages.ru.ecom.mis.ejb.domain.medcase.ShortMedCase();
				ticket.setOrderLpu(ordLpu);
				ticket.setPatient(aEntity.getPatient());
				ticket.setWorkFunctionExecute(aEntity.getOwnerFunction());
				ticket.setUsername(aEntity.getUsername());
				ticket.setCreateDate(aEntity.getCreateDate());
				ticket.setNoActuality(aEntity.getNoActuality());
				ticket.setServiceStream(aEntity.getServiceStream());
				ticket.setVisitReason(visitReason);
				ticket.setVisitResult(visitResult);
				ticket.setWorkPlaceType(workPlaceType);
				ticket.setCreateTime(aEntity.getCreateTime());
				ticket.setMedcard(medcard);
				ticket.setParent(aEntity);
				ticket.setEmergency(aEntity.getEmergency());
				ticket.setDateStart(new java.sql.Date(Packages.ru.nuzmsh.util.format.DateFormat.parseDate(""+otherDates[i]).getTime()));
				
				//ticket.setIsTalk(aEntity.getIsTalk());
				aCtx.manager.persist(ticket);
				
				if (aEntity.getDateStart()==null||aEntity.getDateStart().getTime()>ticket.getDateStart().getTime()){
					aEntity.setDateStart(ticket.getDateStart());
				}
				if (aEntity.getDateFinish()==null||aEntity.getDateFinish().getTime()<ticket.getDateStart().getTime()) {
					aEntity.setDateFinish(ticket.getDateStart());
				}
				
				if (aForm.getConcludingDiagnos()!=null) {
					//var vipObj = VocIllnesPrimary.class
					var diag = new Packages.ru.ecom.mis.ejb.domain.medcase.Diagnosis;
					aEntity.setIdc10(mkb) ;
					diag.setIllnesPrimary(vip) ;
					diag.setTraumaType(vtt) ;
					diag.setIdc10(mkb) ;
					diag.setName(aForm.getConcludingDiagnos()) ;
					diag.setEstablishDate(ticket.getDateStart()) ;
					diag.setMkbAdc(aForm.getMkbAdc());
					diag.setMedCase(ticket); 
					diag.setPriority(vocConcomType) ;
					aCtx.manager.persist(diag) ;
				}
				
			}
			//aCtx.manager.persist(spo);
		}
	}
}