function onPreCreate(aForm, aCtx) {
	onPreSave(aForm,aCtx)
}
function onPreSave(aForm,aEntity, aCtx) {
	var date = new java.util.Date() ;
	// Проверка введенных данных
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	//aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay") ;
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	var dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
	var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;

	if (aForm.concludingDiagnos!=null &&aForm.concludingDiagnos!="" && (aForm.concludingMkb==null || aForm.concludingMkb==0)) throw "Не указан код МКБ заключительного диагноза!" ;
	if ((aForm.concludingMkb!=null && aForm.concludingMkb>0)&&(aForm.concludingDiagnos==null||aForm.concludingDiagnos=="")) throw "Не сформулирован заключительный диагноз!" ;
	var dateFsql = new java.sql.Date(dateFinish.getTime()) ;
	if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки должна быть больше, чем дата поступления";
	//if ((((dateFsql.getTime()-dateCur.getTime())/1000/60/60)%24)>6) throw "Максимальная дата выписки - сегодняшняя" ;
	
	var ldate = aCtx.manager.createNativeQuery("select transferDate,transferTime from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase' and transferDate is not null order by transferDate desc,transferTime desc")
		.setParameter("parent",aForm.id)
		.setMaxResults(1)
		.getResultList() ;
	//var stat = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.isDischargeOnlyCurrentDay(context) ;
	//throw "date="+dmax[0] ;
	if (ldate.size()>0) {
		var dmax=ldate.get(0) ;
		//var vmax = aCtx.manager.createNativeQuery("select max() from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase' and transferDate=:dmax")
	     //  	.setParameter("parent",aForm.id)
	      // 	.setParameter("dmax",dmax)
	       	
		//	.getSingleResult() ;
		//throw "time="+dmax[0]+" "+dmax[1];
			var dateMax = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(dmax[0],dmax[1]);
			if ((dateFinish.getTime() < dateMax.getTime())) {
				var cal = java.util.Calendar.getInstance() ;
				cal.setTime(dateMax) ;			
				throw "Дата выписки должна быть больше, чем дата последнего перевода "+cal.get(java.util.Calendar.DATE)+"."+(cal.get(java.util.Calendar.MONTH)+1)+"."+cal.get(java.util.Calendar.YEAR)
				+" "+cal.get(java.util.Calendar.HOUR_OF_DAY)
				+":"+cal.get(java.util.Calendar.MINUTE)
				;
		}
	}
	var loper = aCtx.manager.createNativeQuery("select so.id as soid,to_char(so.operationDate,'') as operdate ,so1.id as so1id,so1.operationDate as oper1date "
          + " from MedCase as smo" 
          +" left join surgicaloperation so on so.medcase_id=smo.id"
          +" left join medcase smo1 on smo1.parent_id=smo.id"
          +" left join surgicaloperation so1 on so1.medcase_id=smo1.id"
          +" where smo.id='"+aForm.id+"' "
          +" and (so.operationDate>to_date('"+aForm.dateFinish+"','dd.mm.yyyy')"
          +" or so1.operationDate>to_date('"+aForm.dateFinish+"','dd.mm.yyyy'))"
          +"").getResultList();
	if (loper.size()>0) {
		var obj = loper.get(0)
		throw "Дата выписки должна быть больше, чем дата операции <a href='entityParentEdit-stac_surOperation.do?id="+
		+(obj[0]!=null?obj[0]:obj[2])+"'>"+
		(obj[0]!=null?obj[1]:obj[3])+"</a>"
		;
	}
	if (stat) {
		
			var cal1 = java.util.Calendar.getInstance() ;
			var cal2 = java.util.Calendar.getInstance() ;
			cal2.setTime(dateCur) ;		
			cal1.setTime(dateFinish) ;
			
			if (cal1.get(java.util.Calendar.YEAR)==cal2.get(java.util.Calendar.YEAR) &&
				cal1.get(java.util.Calendar.MONTH)==cal2.get(java.util.Calendar.MONTH) &&
				cal1.get(java.util.Calendar.DATE)==cal2.get(java.util.Calendar.DATE) 
			) {
			
			} else{
				var param = new java.util.HashMap() ;
				param.put("obj","DischargeMedCase") ;
				param.put("permission" ,"backdate") ;
				param.put("id", aForm.id) ;
				var check=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
				
				//var check=0 ;
				if (+check==0) {
				 throw "У Вас стоит ограничение на дату выписки. Вы можете выписывать только текущим числом!";
				}
		}
	}
	

}