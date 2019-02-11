

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
    checkParent(aSurgOper,aCtx); //Находим родителя по дате и времени операции
}
function onPreSave(aForm,aEntity, aCtx) {
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	checkPeriod(aForm,aCtx) ;
}
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	checkPeriod(aForm,aCtx) ;
	if (+aForm.isAnesthesia>0) {
		var ch = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocYesNo,aForm.isAnesthesia) ;
		if (ch!=null && ch.code.equals("1")) {
			if (+aForm.anaesthetist>0 && +aForm.anesthesia>0 && +aForm.anesthesiaType>0 && +aForm.anesthesiaDuration>0) {

			} else {
				throw "Необходимо заполнить все обязательные поля по анестезии!!!" ;
			}
		}
	} else {throw "Необходимо указать была анестезия или нет!!!" ;}

	var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,aForm.medService) ;
	if (medService.isAbortRequired &&  true==medService.isAbortRequired &&!+aForm.abortion>0) {
		throw "Для операции "+medService.code+" "+medService.name+" необходимо заполнить тип аборта!";
	}
}


function checkPeriod(aForm,aCtx) {
	aManager = aCtx.manager ;
	var isCreateAnyTime = aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateAnyTime") ;
	if (!isCreateAnyTime) {
	Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.checkPolicyCreateHour(aCtx.getSessionContext()
	        , aForm.getOperationDate(), aForm.getOperationTime());
	}
	var operDate = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.operationDate) ;
	var medCase = aManager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase
			,aForm.medCase) ;
	if (medCase.dateStart.getTime()>operDate.getTime()) {
		throw "Дата операции "+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(operDate)+" должна быть больше или равна началу СМО "
		+Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(medCase.dateStart) ;
	}
	var l = aManager.createNativeQuery("select vlaeo.id from mislpu ml left join VocLpuAccessEnterOperation vlaeo on vlaeo.id=ml.AccessEnterOperation_id where ml.id='"
				+aForm.department+"' and (vlaeo.code='NOT_SURGICAL_DEPARTMENT' or vlaeo.code='ALL_DEPARTMENT')").getResultList() ;
	if (l.size()>0) throw "Запрет в отделение на регистрацию, что в нем проводилась операция!!!"
	
}
function onSave(aForm, aEntity, aCtx) {
	checkParent(aEntity,aCtx);
}
function checkParent(aEntity, aCtx) {
	//Находим родителя по дате и времени операции
       var interceptor = new Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SurgicalOperationCreateInterceptor.setParentByOperation(aEntity,aCtx.manager);
}
