function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aForm.setTime(new java.sql.Time (date.getTime())) ;
	var wf =aCtx.manager.createQuery("from WorkFunction where secUser.login = :login")
		.setParameter("login", aForm.username).getResultList() ;
	if (wf.size()>0) {
		var protocols ;
		//throw "select d.id,d.record from Diary d where d.dtype='Protocol'"
		//	+" and  d.medCase_id='"+aForm.medCase+"' and d.specialist_id='"+aForm.specialist+"'"
		//	+" and d.dateRegistration=$$ei^Zcdat('"+aForm.dateRegistration+"') and d.timeRegistration=cast('"+aForm.timeRegistration+"' as TIME) "
		//	;
		protocols = aCtx.manager.createNativeQuery("select d.id,d.record from Diary d where d.dtype='Protocol'"
			+" and  d.medCase_id='"+aForm.medCase+"' and d.specialist_id='"+wf.get(0).id+"'"
			+" and d.dateRegistration=$$ei^Zcdat('"+aForm.dateRegistration+"') and d.timeRegistration=cast('"+aForm.timeRegistration+"' as TIME) "
			)
			.getResultList() ;
		errorThrow(protocols, "В базе уже существует заключение созданное Вами в это время") ;	
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
