
function onPreDelete(aId, aCtx) {

}

/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}

/**
* Перед созданием
 */
function onPreCreate(aForm, aCtx) {

	var duration = +aForm.duration;
	if (duration>0) { //операция
		var manager = aCtx.manager;
		var startTime = aForm.planStartTime;
		//есть длительность операции, проверяем, есть ли свободное время на столько минут.
		var sql =" select list(''||wct.id) as f1_list , count(wct.id) as cntAll, count(case when wct.rest='1' or wct.medcase_id is not null " +
			" or (wct.prepatientInfo is not null and wct.prepatientInfo!='') or wct.prepatient_id is not null or wct.prescription is not null then wct.id end ) as f2_cntBusy" +
            ", cast(max('"+startTime+"'+interval '"+duration+" minutes') as varchar(5)) as f3_endTime" +
			"  from workcalendartime wct " +
			" where wct.workcalendarday_id=" +aForm.surgCalDate +
			" and wct.timefrom between '"+startTime+"' and '"+startTime+"'+interval '"+duration+" minutes'";
		var list = manager.createNativeQuery(sql).getResultList();
		if (!list.isEmpty()) {
			var row = list.get(0);
			if (+row[2]>0) {
				throw ("Ошибка назначение - нет свободного времени для операции с началом в "+startTime+" и длительностью "+duration+" минут");
			}
			manager.createNativeQuery("update workcalendartime set rest='1' where id in ("+row[0]+")").executeUpdate();
            var endTime = ""+row[3];
            aForm.setPlanEndDate(aForm.getPlanStartDate());
            aForm.setPlanEndTime(endTime);
		}
	} else {
		throw "Не указана длительность операции!";
	}
}
/**
 * После создания
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date();
	var username = aCtx.getSessionContext().getCallerPrincipal().toString();
	date = new java.sql.Date(date.getTime());
	var time = new java.sql.Time (date.getTime());
	aEntity.setCreateDate(date) ;
	aEntity.setCreateTime(time) ;
	aEntity.setCreateUsername(username) ;
	var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
	aEntity.setPrescriptSpecial(wf) ;

	if (aForm.calendarTime!=null){
		var wct = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime,aForm.calendarTime) ;
		wct.setPrescription(aEntity.id);
		aCtx.manager.persist(wct);
	}
}	

