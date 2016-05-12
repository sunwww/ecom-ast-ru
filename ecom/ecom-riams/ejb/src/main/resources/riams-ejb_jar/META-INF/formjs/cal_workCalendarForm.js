function onPreCreate(aCalForm, aCtx) {
	var list = aCtx.manager.createQuery("from WorkCalendar where workFunction_id=:functionId")
		.setParameter("functionId",aCalForm.workFunction).getResultList() ;
	if(list.size()>0) {
		throw list+" Уже есть календарь у рабочей функции: " +list.iterator().next().workFunction.name;
	}
}

function onCreate(aForm, aCalendar, aCtx) {
   aCalendar.workFunction.workCalendar = aCalendar ;
}

function onPreDelete(aCalendarId, aCtx) {
	var calendar = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar, new java.lang.Long(aCalendarId)) ;
	if(calendar.journals.size()>0) {
		throw "У рабочего календаря есть шаблоны. Сначала нужно их удалить" ;
	}
	var napr = aCtx.manager.createNativeQuery("select count(*) from WorkCalendarTime as wct"
		+ " left join WorkCalendarDay as wcd on wct.workCalendarDay_id=wcd.id"
		+ " where wcd.workCalendar_id=:calenid and wct.medCase_id is not null"
		).setParameter("calenid",aCalendarId).getSingleResult() ;
	if ((0+napr)>0) {
		throw "По рабочему календарю сделаны направления на прием и/или есть исполненные визиты. Сначала нужно их удалить"
	}
	var workFunction = calendar.workFunction ;
	calendar.workFunction = null ;
	workFunction.workCalendar = null ;
	//aCtx.manager.remove(workFunction) ;
}