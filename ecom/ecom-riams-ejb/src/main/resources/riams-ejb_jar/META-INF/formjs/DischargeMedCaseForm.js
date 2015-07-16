function onPreCreate(aForm, aCtx) {
	onPreSave(aForm,aCtx)
}
function onSave(aForm,aEntity, aCtx) {
	Packages.ru.ecom.mis.ejb.service.medcase.HospitalMedCaseServiceBean.saveDischargeEpicrisis(aForm.id,aForm.getDischargeEpicrisis(),aCtx.manager) ;
}
function onPreSave(aForm,aEntity, aCtx) {
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/DotSave"))throw "Вы не можете сохранять выписку!!!!!!"
	checkAllDiagnosis (aCtx, aForm.id) ;
	var date = new java.util.Date() ;
	// Проверка введенных данных
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
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
		var cal3 = java.util.Calendar.getInstance() ;
		cal3.setTime(dateCur) ;		
		cal2.setTime(dateCur) ;		
		cal1.setTime(dateFinish) ;
		
		cal3.add(java.util.Calendar.HOUR_OF_DAY,(-24)) ;
		
		if (cal1.after(cal3)) {
			
			} else{
				var param = new java.util.HashMap() ;
				param.put("obj","DischargeMedCase") ;
				param.put("permission" ,"backdate") ;
				param.put("id", aForm.id) ;
				var check=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
				
				if (+check==0) {
					throw "У Вас стоит ограничение на дату выписки. Вы можете выписывать в течение 24 часов.";
					
				}
			}
	}
	

}

function checkAllDiagnosis (aCtx, aSlsId) {
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DotPrintWithoutDiagnosisInSlo")){
		var sql = "select slo.id as sloid,ml.name||' '||to_char(slo.dateStart,'dd.mm.yyyy')||coalesce('-'||to_char(slo.transferDate,'dd.mm.yyyy'),'') as info from medcase sls "
		 +" left join medcase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'"
		 +" left join mislpu ml on ml.id=slo.department_id"
		 +" left join diagnosis d on slo.id = d.medcase_id"
		 +" left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id"
		 +" left join vocprioritydiagnosis vpd on vpd.id=d.priority_id"
		 +" where sls.id='"+aSlsId+"' and (ml.isnoomc is null or ml.isnoomc='0') "
		 +" group by sls.id,slo.id,ml.name,slo.dateStart,slo.transferDate	"
		 +" having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0  "
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (list.size()>0) {
			var slo ="" ;
			for (var i=0; i<list.size(); i++) {
				slo = slo+" <a href='entitySubclassView-mis_medCase.do?id="+list.get(0)[0]+"' onclick='return  msh.util.FormData.getInstance().isChangedForLink() ;'>" +list.get(0)[1]+"</a>" ;
			}
			throw "Не полностью заполнены данные по диагнозам в отделениях!!! "+ slo ;
		}	
	}
}