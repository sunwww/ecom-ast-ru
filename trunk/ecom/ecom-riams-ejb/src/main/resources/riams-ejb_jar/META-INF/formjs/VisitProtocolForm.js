function onPreCreate(aForm, aCtx) {
	check(aForm,aCtx) ;
	var date = new java.util.Date() ;
	aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setUsername(aCtx.getUsername()) ;
	aForm.setTime(new java.sql.Time (date.getTime())) ;
	var wfe =aCtx.manager.createNativeQuery("select id,workFunctionExecute_id from MedCase where id = :medCase")
		.setParameter("medCase", aForm.medCase).getResultList() ;
	var wfeid = java.lang.Long.valueOf(0) ;
	if (wfe.size()>0) {
		wfeid=wfe.get(0)[1] ;
	}

	var wf =java.lang.Long.valueOf(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunctionListByPoliclinic"
			,wfeid)) ;
	if (wf!=null) {
		var protocols ;
		//throw "select d.id,d.record from Diary d where d.dtype='Protocol'"
		//	+" and  d.medCase_id='"+aForm.medCase+"' and d.specialist_id='"+aForm.specialist+"'"
		//	+" and d.dateRegistration=$$ei^Zcdat('"+aForm.dateRegistration+"') and d.timeRegistration=cast('"+aForm.timeRegistration+"' as TIME) "
		//	;
		var dat = Packages.ru.nuzmsh.util.format.DateFormat.formatToJDBC(aForm.dateRegistration) ;
		protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.dtype='Protocol'"
			+" and  d.medCase_id='"+aForm.medCase+"' and d.specialist_id='"+wf+"'"
			+" and d.dateRegistration=cast('"+dat+"' as date) and d.timeRegistration=cast('"+aForm.timeRegistration+"' as TIME) "
			)
			.getResultList() ;
		errorThrow(protocols, "В базе уже существует заключение созданное Вами в это время") ;
		aForm.setSpecialist(wf) ;
	}
	
}
function onPreSave(aForm,aEntity, aCtx) {
	check(aForm,aCtx) ;
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.id='"+aEntity.id+"' and d.dtype='Protocol'").getResultList() ;
	if (protocols.isEmpty()) {
		onPreCreate(aForm, aCtx) ;
		
	}
}
/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	//throw "select d.id,d.record from Diary d where d.id='"+aEntity.id+"' and d.dtype='Protocol'" ;
	var protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.id='"+aEntity.id+"' and d.dtype='Protocol'").getResultList() ;
	if (protocols.isEmpty()) {
		//throw "123" ;
		
		aCtx.manager.createNativeQuery("update Diary set dtype='Protocol' where id='"+aEntity.id+"'").executeUpdate() ;
		
	}
}
function check(aForm,aCtx) {
	
	if (aForm.medCase!=null&&(+aForm.medCase)>0) {
		
		var t = aCtx.manager.createNativeQuery("select dtype,id from medcase where id="+aForm.medCase).getResultList() ;
		
		if (!t.isEmpty()) {
			var dtype=""+t.get(0)[0] ;
			//throw dtype ;
			if (dtype=='HospitalMedCase'||dtype=='DepartmentMedCase') { 
				if (aForm.type==null||(+aForm.type==0)) throw "Необходимо заполнить поле Тип протокола" ;
				if (aForm.state==null||(+aForm.state==0)) throw "Необходимо заполнить поле Состояние больного" ;
			}
			if (dtype=='HospitalMedCase' &&(aForm.journalText==null||aForm.journalText.equals(""))) {
				throw "Необходимо заполнить поле Принятые меры для журнала. Если их нет, необходимо ставить: -" ;
			}
		}
		
	}
	
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error =":";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
				error = error+" <br/><a style='color:yellow' href='entityView-smo_visitProtocol.do?id="+doc[0]+"'>"
				error=error+(i+1)+". Заключение: <pre>"+doc[1]+" </pre> " ;
				error=error+"</a>" ;
			
		}
		throw aError + error ;
	}
}
