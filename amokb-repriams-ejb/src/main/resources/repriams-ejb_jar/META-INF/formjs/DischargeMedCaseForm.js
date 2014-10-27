function onPreCreate(aForm, aCtx) {
	onPreSave(aForm,aCtx)
}
function onPreSave(aForm,aEntity, aCtx) {
	// Проверка введенных данных
	stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay") ;
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	var dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
	var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;

	if (aForm.concludingDiagnos!=null &&aForm.concludingDiagnos!="" && (aForm.concludingMkb==null || aForm.concludingMkb==0)) throw "Не указан код МКБ заключительного диагноза!" ;
	if ((aForm.concludingMkb!=null && aForm.concludingMkb>0)&&(aForm.concludingDiagnos==null||aForm.concludingDiagnos=="")) throw "Не сформулирован заключительный диагноз!" ;
	var dateFsql = new java.sql.Date(dateFinish.getTime()) ;
	if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки должна быть больше, чем дата поступления";
	//if ((((dateFsql.getTime()-dateCur.getTime())/1000/60/60)%24)>6) throw "Максимальная дата выписки - сегодняшняя" ;
	
	var row = aCtx.manager.createNativeQuery("select max(transferDate),max(id) from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase'")
	       	.setParameter("parent",aForm.id)
			.getSingleResult() ;
	//var stat = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.isDischargeOnlyCurrentDay(context) ;
	if (row[0]!=null) {
		var cal = java.util.Calendar.getInstance() ;
		cal.setTime(row[0]) ;
		if ((dateFinish.getTime() < row[0].getTime())) throw "Дата выписки должна быть больше, чем дата последнего перевода "+cal.get(java.util.Calendar.DATE)+"."+(cal.get(java.util.Calendar.MONTH)+1)+"."+cal.get(java.util.Calendar.YEAR);
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