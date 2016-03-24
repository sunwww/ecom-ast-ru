function onPreDelete(aEntityId, aContext) {
	//aContext.manager.createNativeQuery("delete from medcase where parent_id="+aEntityId).executeUpdate() ;
}

function onCreate(aForm, aEntity, aCtx) {
	if (aForm.isCreatePlanHosp!=null && aForm.isCreatePlanHosp) {
		var plan = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed ;
		var date = new java.util.Date() ;
		var username=aCtx.getSessionContext().getCallerPrincipal().toString() ;
		plan.dateFrom=aEntity.planDateFrom ;
		plan.dateTo=aEntity.planDateTo ;
		plan.diagnosis=aEntity.diagnosis ;
		plan.phone=aEntity.phonePatient ;
		plan.isOperation=aEntity.isPlanOperation ;
		plan.serviceStream=aEntity.serviceStream ;
		plan.bedType=aEntity.bedType ;
		plan.bedSubType=aEntity.bedSubType ;
		plan.patient=aEntity.medCase.patient ;
		plan.idc10=aEntity.idc10 ;
		plan.department=aEntity.department ;
		plan.createDate=new java.sql.Date(date.getTime()) ;
		plan.createTime=new java.sql.Time(date.getTime()) ;
		plan.createUsername=username ;
		var workFunc = +aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic"
				,+aEntity.medCase.workFunctionExecute.id) ;
		plan.workFunction=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, java.lang.Long.valueOf(workFunc)) ;
		plan.visit=aEntity.medCase ;
		aCtx.manager.persist(plan) ;
		aEntity.planHospitalBed=plan ;
	}
}