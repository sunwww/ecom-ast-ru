

function onCreate(aForm, aSurgOper, aCtx) {
	if (aSurgOper.getMedCase().getPatient()!=null) {
		aSurgOper.patient = aSurgOper.getMedCase().getPatient() ;
	} else {
		if (aSurgOper.getMedCase().getParent()!=null && aSurgOper.getMedCase().getParent().getPatient()!=null)
			aSurgOper.patient = aSurgOper.getMedCase().getParent().getPatient() ;
	}
	if (+aForm.isAnesthesia>0) {
		var ch = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocYesNo,aForm.isAnesthesia) ;
		if (ch!=null && ch.code.equals("1")) {
			if (+aForm.anaesthetist>0 && +aForm.anesthesia>0 && +aForm.anesthesiaType>0 && +aForm.anesthesiaDuration>0) {
				var anes = new Packages.ru.ecom.mis.ejb.domain.medcase.Anesthesia() ;
				var anesthetist = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction
						,aForm.anaesthetist) ;
				var anesthesia = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesiaMethod
						,aForm.anesthesia) ;
				var anesthesiaType = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia
						,aForm.anesthesiaType) ;
				var anesthesiaService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService
						,aForm.anesthesiaService) ;
				anes.setMethod(anesthesia) ;
				anes.setType(anesthesiaType) ;
				anes.setMedService(anesthesiaService) ;
				anes.setAnesthesist(anesthetist) ;
				anes.setDuration(+aForm.anesthesiaDuration) ;
				anes.setSurgicalOperation(aSurgOper) ;
				var date = new java.util.Date() ;
				anes.setCreateDate(new java.sql.Date(date.getTime())) ;
				anes.setCreateTime(new java.sql.Time (date.getTime())) ;
				anes.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

				aCtx.manager.persist(anes) ;
			} else {
				throw "Не заполнены данные по анастезии" ;
			}
		}
	}
	
}
function onPreSave(aForm,aEntity, aCtx) {
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	checkPeriod(aForm,aCtx.manager) ;
}
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	checkPeriod(aForm,aCtx.manager) ;
	if (+aForm.isAnesthesia>0) {
		if (+aForm.anaesthetist>0 && +aForm.anesthesia>0 && +aForm.anesthesiaType>0 && +aForm.anesthesiaDuration>0) {
			
		} else {
			throw "Необходимо заполнить все обязательные поля по анестезии!!!" ;
		}
	} else {throw "Необходимо указать была анестезия или нет!!!" ;}
}


function checkPeriod(aForm,aManager) {
	var operDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.operationDate) ;
	var medCase = aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase
			,aForm.medCase) ;
	if (medCase.dateStart.getTime()>operDate.getTime()) {
		throw "Дата операции "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(operDate)+" должна быть больше или равна началу СМО "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(medCase.dateStart) ;
	}
	
}
