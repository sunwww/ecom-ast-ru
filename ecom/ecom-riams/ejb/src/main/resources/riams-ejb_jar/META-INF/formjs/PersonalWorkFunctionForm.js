/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx){
	var login = aForm.getSecUser() ;
	var list =null;
	if (login!=null && !login.equals("")) {		
        list = aCtx.manager.createQuery("from PersonalWorkFunction where"
        	+" secUser_id = :login"
        	)
        	.setParameter("login",login)
        	.getResultList() ;
        errorThrow(list) ;
	}
	
}
function onPreSave(aForm,aEntity , aCtx) {
	var login = aForm.getSecUser() ;
	var thisid = aForm.getId() ;
	var list =null;
	if (login!=null && !login.equals("")) {		
        list = aCtx.manager.createQuery("from WorkFunction where"
        	+" secUser_id = :login"
        	+" and id != '"+thisid+"'"
        )
        	.setParameter("login",login)
        	.getResultList() ;
	} else {
	}
	errorThrow(list) ;
	
}

function onCreate(aForm, aEntity, aContext) {
	if ((0+aForm.group)==0 && aForm.isCalendarCreate!=null&&aForm.isCalendarCreate) {
		var calendar = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar() ;
		calendar.workFunction = aEntity ;
		aContext.manager.persist(calendar) ;
		aEntity.workCalendar = calendar ;
	}
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;

    saveCabinet(aForm, aEntity, aContext);
	stayAdminUnique(aForm, aEntity, aContext);
}

function onSave(aForm, aEntity, aContext) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;

    aContext.manager.createNativeQuery("delete from workplace_workfunction where workfunctions_id="+aEntity.id).executeUpdate() ;
    saveCabinet(aForm, aEntity, aContext);
	stayAdminUnique(aForm, aEntity, aContext);
}
function errorThrow(aList) {
	if (aList!=null && aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var workFunction = aList.get(i) ;
			error = error+"<br> "+(i+1)+"."+workFunction.id+"."+ workFunction.workFunctionInfo + "; " ;
		}
		throw "<u>Имя пользователя уже используется:</u>" + error ;
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
}

function onPreDelete(aFunctionId, aCtx) {
	var workFunction = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, new java.lang.Long(aFunctionId)) ;
	var workCalendar = workFunction.workCalendar ;
	workFunction.workCalendar = null ;
	var list = aCtx.manager.createQuery("from WorkCalendar where workFunction=:f")
		.setParameter("f", workFunction)
		.getResultList()  ;
	for(var i = 0 ;i < list.size(); i++) {
		var cal = list.get(i) ;
		delCal(cal, aCtx) ;
		cal.workFunction = null ;
		aCtx.manager.remove(cal) ;
	}
}
//Сохранение кабинета
function saveCabinet(aForm, aEntity, aCtx) {
    var cabinet=aForm.getCabinet();
    if (cabinet!='') {
        var res = aCtx.manager.createNativeQuery("select id from workplace where name='" + cabinet + "'").setMaxResults(1).getResultList();
        if (res.size() > 0)
            aCtx.manager.createNativeQuery("insert into workplace_workfunction(workplace_id,workfunctions_id) values(" + java.lang.Long.valueOf(res.get(0)) + "," + aEntity.id + ")").executeUpdate();
        else {
            var resId=aCtx.manager.createNativeQuery("insert into workplace(dtype,name) values('ConsultingRoom','"+cabinet+"') returning id").getResultList();
            if (resId.size()>0) {
            	var wp = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.ConsultingRoom,java.lang.Long.valueOf(resId.get(0)));
                var wfs = new java.util.ArrayList() ;
                wfs.add(aEntity);
                wp.setWorkFunctions(wfs);
                aCtx.manager.persist(wp);
			}
        }
    }
}
//Начальник ЛПУ может быть только один
function stayAdminUnique(aForm, aEntity, aCtx) {
	if (+aForm.getIsAdministrator()==1) {
		aCtx.manager.createNativeQuery("update workfunction set isadministrator=false" +
			" where id in (select wf.id from workfunction wf" +
			" left join worker w on wf.worker_id=w.id" +
			" left join worker wnow on wnow.id=" + aForm.getWorker() +
			" where w.lpu_id =wnow.lpu_id and w.id<>wnow.id)").executeUpdate() ;
	}
}