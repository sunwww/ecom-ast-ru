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
		
		var t = aCtx.manager.createNativeQuery("select m1.dtype,case when m1.dtype='DepartmentMedCase' and m2.dischargeTime is not null then m2.dateFinish else null end as datefinish from medcase m1 left join medcase m2 on m2.id=m1.parent_id where m1.id="+aForm.medCase).getResultList() ;
		
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
			var isCheck = null ;
			if (t.get(0)[1]!=null) {
				var param1 = new java.util.HashMap() ;
				param1.put("obj","Protocol") ;
				param1.put("permission" ,"editAfterDischarge") ;
				param1.put("id", +aForm.id) ;
				isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
				if (+isCheck!=1) throw "У Вас стоит ограничение на редактирование данных после выписки!!!";
			}
		}
		if (aForm.getDateRegistration()!=null && aForm.getDateRegistration()!='' && isCheck==null) {
			var curDate = java.util.Calendar.getInstance();
			var maxVisit = java.util.Calendar.getInstance();
			var dateVisit = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.getDateRegistration(),aForm.getTimeRegistration()) ;
			maxVisit.setTime(dateVisit);
			var cntHour = +getDefaultParameterByConfig("count_hour_edit_protocol", 24, aCtx) ;
			maxVisit.add(java.util.Calendar.HOUR,cntHour);
			if (curDate>maxVisit) {
				var param1 = new java.util.HashMap() ;
				param1.put("obj","Protocol") ;
				param1.put("permission" ,"editAfterCertainHour") ;
				param1.put("id", +aForm.id) ;
				var isCheck = aCtx.serviceInvoke("WorkerService", "checkPermission", param1)+""; 
				//throw isDeleteClose ;
				if (+isCheck!=1) throw "У Вас стоит ограничение "+cntHour+" часов на создание (редактирование) протокола!!!";
			}
		}
		
	}
	
}
function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
	l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'").getResultList();
	if (l.isEmpty()) {
		return aValueDefault ;
	} else {
		return l.get(0)[1] ;
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
