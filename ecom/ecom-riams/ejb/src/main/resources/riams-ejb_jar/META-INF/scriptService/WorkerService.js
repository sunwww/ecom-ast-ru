function replaceWorkFunction(aCtx,aParam) {
	if (+aParam>0) {
		
		var username=aCtx.getSessionContext().getCallerPrincipal().toString() ;
		var sqluser = "select su.id as suid,w.person_id as pwid from secuser su left join workfunction wf on wf.secuser_id=su.id left join worker w on w.id=wf.worker_id where login='"+username+"'" ;
		var listuser = aCtx.manager.createNativeQuery(sqluser).getResultList();
		//throw username ;
		if (!listuser.isEmpty()) {
			var suId=listuser.get(0)[0] ;
			var wId=listuser.get(0)[1] ;
			var sqlCheck = "select wf.id,wf.worker_id from workfunction wf  left join worker w on w.id=wf.worker_id where wf.id='"+aParam+"' and w.person_id='"+wId+"'" ;
			var listCheck = aCtx.manager.createNativeQuery(sqlCheck).getResultList();
			if (!listCheck.isEmpty()) {
				//throw suId+" username="+username ;
				aCtx.manager.createNativeQuery("update WorkFunction set secuser_id=null where secuser_id='"+suId+"'").executeUpdate() ;
				aCtx.manager.createNativeQuery("update WorkFunction set secuser_id='"+suId+"' where id='"+aParam+"'").executeUpdate() ;
			} else {
				throw "Вы не можете изменить установить соответствие пользователя и рабочей функции" ;
			}
		}
	}
	
}
/**
 * Информация о текущем сотруднике, вошедшем в систему
 * @return Worker
 */
function findLogginedWorkerName(aCtx) {
	return findLogginedWorker(aCtx).doctorInfo ;
}
function checkPermission(aCtx,  aParam) {
	var obj = aParam.get("obj");
	var permission = aParam.get("permission");
	var id=+aParam.get("id");
	var username=aCtx.sessionContext.callerPrincipal.name ;
	var dat = aParam.get("date");
	var sql_obj="select count(*) from VocObjectPermission vop where vop.code='"+obj+"'" ;
	//throw sql_obj ;
	var vop = aCtx.manager.createNativeQuery(sql_obj).getSingleResult();
	if (vop==null||+vop==0) {
		// Нет существует ограничение
		return 0 ;
	} else {
		sql_obj="select vop.id,vop.namePermissionTable,vop.namePermissionDate from VocObjectPermission vop where vop.code='"+obj+"' " ;
		vop = aCtx.manager.createNativeQuery(sql_obj).setMaxResults(1).getSingleResult();
	}
	
	
	var checkDate ;
	
	if (dat!=null && dat!="" &&dat!="null") {
		
		//var sql_check ="select vocsex.code,cast(cast('"+dat+"' as date) as integer) as dat from vocsex" ;
		checkDate = "to_date('"+dat+"','dd.mm.yyyy')";
	} else if (+id>0){
		var sql_check = "select to_char("+vop[2]+",'dd.mm.yyyy') from "+vop[1]+" where id="+id+" and "+vop[2]+" is not null" ;
		//throw sql_check ;
		
		var lcheck = aCtx.manager.createNativeQuery(sql_check).setMaxResults(1).getResultList();
		if (!lcheck.isEmpty()) {
			checkDate="to_date('"+lcheck.get(0)+"','dd.mm.yyyy')" ;
		} else {
			checkDate="CURRENT_DATE" ;
		}
		
	} else {
		checkDate="CURRENT_DATE" ;
	}
	//throw checkDate+"   "+dat;
	var sql ="select p.editPeriodFrom,p.editPeriodTo" ;
	//throw vop[1] ;
	sql=sql+" from Permission p"
	+" left join vocObjectPermission vop on vop.id=p.object_id"
	+" left join vocPermission vp on vp.id=p.permission_id"
	+" left join SecUser su on su.id=p.username_id"
	+" where p.dtype='UserPermission' and (p.isDeleted is null or p.isDeleted='0')"
	+" and CURRENT_DATE between coalesce(p.dateFrom,CURRENT_DATE) and coalesce(p.dateTo,CURRENT_DATE) "
	+" and "+checkDate+" between coalesce(p.editPeriodFrom,CURRENT_DATE) and coalesce(p.editPeriodTo,CURRENT_DATE) "
	+" and (su.login='"+username+"' or p.username_id is null)"
	+" and p.object_id='"+vop[0]+"' "
	+" and vp.code='"+permission+"' " 
	;
	if (+id>0) {
		sql=sql+" and (p.idObject='"+id+"' or p.idObject ='' or p.idObject is null) " ;
	} else {
		sql=sql+" and (p.idObject ='' or p.idObject is null) " ;
	}
	//throw sql ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList();
	return list.isEmpty() ? 0 : 1 ;

}
function findLogginedSecUserId(aCtx) {
	var username = aCtx.sessionContext.callerPrincipal.name ;
	var list = aCtx.manager.createQuery("from SecUser where login = :login")
		.setParameter("login", username) 
		.getResultList() ;
	if(list.isEmpty()) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответствия между "+username+" и SecUser" ;
	if(list.size()>1)  throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одного раза встречается имя "+username+ " в SecUser" ;
	return list.iterator().next().getId();
}

/**
 * Поиск текущего сотрудника
 * @return Worker
 */
function findLogginedWorker(aCtx) {
	var functions = findLogginedWorkFunctionList(aCtx) ;
	return functions.iterator().next().worker ;
}

/**
 * Поиск текущего сотрудника
 * @return Worker
 */
function findLogginedWorkFunction(aCtx) {
	var functions = findLogginedWorkFunctionList(aCtx) ;
    if(functions.isEmpty()) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и именем пользователя (WorkFunction и SecUser)" ;
	if(functions.size()>1) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одной рабочей функции у пользователя" ;	
	return functions.iterator().next() ;
}

function getWorkCalendarDayCalendarDate(aCtx, aCalDayId) {
	var list = aCtx.manager.createNativeQuery("select wcd.id as wcdid,to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendar from WorkCalendarDay wcd" 
			+" where wcd.id='"+aCalDayId+"'")
			.setMaxResults(1)
		.getResultList() ;
	return list.isEmpty() ? null : list.get(0)[1];
}

/**
 * Поиск текущей функции
 * @return WorkFunction
 */
function findLogginedWorkFunctionList(aCtx) {
	var username = aCtx.sessionContext.callerPrincipal.name ;
	var list = aCtx.manager.createQuery("from WorkFunction where secUser.login = :login")
		.setParameter("login", username) 
		.getResultList() ;
	if(list.isEmpty()) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответствия между рабочей функцией и пользователем (WorkFunction и SecUser)" ;
	return list ;
}
function getGroupByWorkFunction(aCtx,aWorkFunction) {
	var sql = "select group_id,id from workfunction where id='"+aWorkFunction+"'" ;
	var list = aCtx.manager.createNativeQuery(sql)
		.getResultList() ;
	return list.isEmpty() ? 0 : +list.get(0)[0] ;
}

function findLogginedWorkFunctionListByPoliclinic(aCtx,aWorkPlan) {
	var username = aCtx.sessionContext.callerPrincipal.name ;
	var sql = "select wf.id as wfid";
	if (aWorkPlan!=null) { 
		sql=sql+",max(wf1.id) as wf1id";
		sql=sql+",max(wf2.id) as wf2id"
	} else {
		sql=sql+",wf.id as wfid1";
		sql=sql+",wf.id as wfid2";
	}
	sql=sql+" from WorkFunction wf "
		+" left join SecUser su on su.id=wf.secUser_id"
		+" left join worker w on  w.id=wf.worker_id"
		+" left join worker w1 on w.person_id=w1.person_id" ;
	if (aWorkPlan!=null) { 
		sql=sql+" left join workFunction wf1 on wf1.id='"+aWorkPlan+"' and wf1.worker_id=w1.id ";
		sql=sql+" left join workFunction wf2 on wf2.group_id='"+aWorkPlan+"' and wf2.worker_id=w1.id ";
	}
	sql=sql	+" where su.login = '"+username+"'  group by wf.id";
	var list = aCtx.manager.createNativeQuery(sql)
		.getResultList() ;
	if(list.isEmpty()) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и именем пользователя (WorkFunction и SecUser)" ;
	var obj = list.get(0) ;
	return obj[1]!=null ? obj[1]
		: (obj[2]!=null ? obj[2] : obj[0])  ;
}
function getWorkFunctionByCalenDay(aCtx, aCalenDayId) {
	var list = aCtx.manager.createNativeQuery("select wf.id as wcdid,wc.id as wcid from WorkFunction wf left join WorkCalendar wc on wf.id=wc.workFunction_id left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id where wcd.id = '"+aCalenDayId+"'")
		.getResultList() ;
	if(list.isEmpty()) {
		return "0" ;
	}
	var workFunctId = list.get(0)[0] ;
	//Long workFunc = list.get(0).getWorkFunction().getId() ;
	return ""+workFunctId;
}

function getWorkFunctionInfo(aCtx,aWorkFunctionId) {
	var list = aCtx.manager.createNativeQuery("select wf.id as wfid,vwf.name||' '||(case when wf.dtype='GroupWorkFunction' then wf.groupName when wf.dtype='PersonalWorkFunction' then wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename,'') else '' end) as wfinfo from WorkFunction wf left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id where wf.id = '"+aWorkFunctionId+"'")
	.getResultList() ;
	return list.isEmpty() ? "" :list.get(0)[1] ;
}