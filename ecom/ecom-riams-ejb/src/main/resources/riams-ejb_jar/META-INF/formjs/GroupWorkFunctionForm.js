/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx){
	var list =null;
    list = aCtx.manager.createQuery("from WorkFunction where"
        	+" DTYPE='GroupWorkFunction' and groupName=:name"
//        	+" and lpu_id = :lpu "
        	)
//        	.setParameter("lpu",aForm.lpu)
        	.setParameter("name",aForm.groupName)
        	.getResultList() ;
        errorThrow(list) ;
	
}
function onPreSave(aForm,aEntity , aCtx) {
	var thisid = aForm.getId() ;
	var list =null;
    list = aCtx.manager.createQuery("from WorkFunction where"
        	+" DTYPE='GroupWorkFunction' and groupName=:name"
//        	+" and lpu_id = :lpu "
        	+" and id != '"+thisid+"'"
        )
//        	.setParameter("lpu",aForm.lpu)
        	.setParameter("name",aForm.groupName)
        	.getResultList() ;
	errorThrow(list) ;
	
}

function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var calendar = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar() ;
	calendar.workFunction = aEntity ;
	aCtx.manager.persist(calendar) ;
	aEntity.workCalendar = calendar ;
	
	
	//throw aEntity.worker.doctorInfo+""
}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if(aEntity.workCalendar==null) {
		var calendar = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar() ;
		calendar.workFunction = aEntity ;
		aCtx.manager.persist(calendar) ;
		aEntity.workCalendar = calendar ;
	}
	
	
	
	
	//aContext.serviceInvoke("Hello", "helll", "asdf") ;
	//throw "asdf";
}
function errorThrow(aList) {
	if (aList!=null && aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var workFunction = aList.get(i) ;
			error = error+"<br> "+(i+1)+"."+workFunction.id+"."+ workFunction.workFunctionInfo + "; " ;
		}
		throw "<u>Уже существует групповая функция с таким именем:</u>" + error ;
	}
}
function delCal(calendar,aCtx) {
	if(calendar.journals.size()>0) {
		throw "У рабочего календаря есть шаблоны. Сначала нужно их удалить" ;
	}
	var napr = aCtx.manager.createNativeQuery("select count(*) from WorkCalendarTime as wct"
		+ " left join WorkCalendarDay as wcd on wct.workCalendarDay_id=wcd.id"
		+ " where wcd.workCalendar_id=:calenid and wct.medCase_id is not null"
		).setParameter("calenid",calendar.id).getSingleResult() ;
	if ((0+napr)>0) {
		throw "По рабочему календарю сделаны направления на прием и/или есть исполненные визиты. Сначала нужно их удалить"
	}
	var workFunction = calendar.workFunction ;
	calendar.workFunction = null ;
	workFunction.workCalendar = null ;
	//aCtx.manager.remove(workFunction) ;
}

function onPreDelete(aFunctionId, aCtx) {
	var workFunction = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, new java.lang.Long(aFunctionId)) ;
	var workCalendar = workFunction.workCalendar ;
	workFunction.workCalendar = null ;
	//if(workCalendar!=null) {
	//	workCalendar.workFunction = null ;
	//	delCal(workCalendar) ;
	//	aCtx.manager.remove(workCalendar) ;
	//} else {
		var list = aCtx.manager.createQuery("from WorkCalendar where workFunction=:f")
			.setParameter("f", workFunction)
			.getResultList()  ;
		for(var i = 0 ;i < list.size(); i++) {
			var cal = list.get(i) ;
			delCal(cal, aCtx) ;
			cal.workFunction = null ;
			aCtx.manager.remove(cal) ;
		}	
	//}
}