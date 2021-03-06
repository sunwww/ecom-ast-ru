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
	var sql_obj="select top 1 count(*),vop.id,vop.namePermissionTable,vop.namePermissionDate from VocObjectPermission vop where vop.code='"+obj+"' " ;
	var list_obj = aCtx.manager.createNativeQuery(sql_obj).getResultList();
	var vop= list_obj.size()>0?list_obj.get(0):"" ;
	if (vop==""||+vop[0]==0) {
		// Нет существует ограничение
		return 0 ;
	}
	var sql ="select count(*),cast(p.editPeriodFrom as integer),cast(p.editPeriodTo as integer),to_char(p.editPeriodFrom,'dd.mm.yyyy'),to_char(p.editPeriodTo,'dd.mm.yyyy')" ;
	sql=sql+" from Permission p"
		+" left join vocObjectPermission vop on vop.id=p.object_id"
		+" left join vocPermission vp on vp.id=p.permission_id"
		+" left join SecUser su on su.id=p.username_id"
		+" where p.dtype='UserPermission'"
		+" and CURRENT_DATE between isnull(p.dateFrom,CURRENT_DATE) and isnull(p.dateTo,CURRENT_DATE) "
		+" and (su.login='"+username+"' or p.username_id is null)"
		+" and p.object_id='"+vop[1]+"' "
		+" and vp.code='"+permission+"' "
	if (id>0) sql=sql+" and (p.idObject='"+id+"' or p.idObject ='' or p.idObject is null) " ;
	var list = aCtx.manager.createNativeQuery(sql)
		.getResultList();
	//throw sql ;
	if (+list.size()==0) {
		// Нет существует ограничение
		return 0 ;
	}
	var check ;
	if (id>0) {
		var sql_check = "select count(*), cast("+vop[3]+" as integer) from "+vop[2]
			+" where id="+id ;
		//throw sql_check ;
		
		check = aCtx.manager.createNativeQuery(sql_check).getResultList().get(0);
	} else {
		var dat = aParam.get("date");
		var sql_check ="select vocsex.code,cast(cast('"+dat+"' as date) as integer) as dat from vocsex" ;
		check = aCtx.manager.createNativeQuery(sql_check).getResultList().get(0);
		//throw "check="+check[1] + ' sql='+sql_check ;
	}
	for (var i=0;i<list.size();i++) {
		var perm = list.get(i) ;
		//if (id==0) throw "perm1="+perm[1] +"perm2="+perm[2] +" check="+ check[1] +"sql"+sql_check;
			var c =0;
		if (+perm[1]>0 && +check[1]>0 && +perm[1]>check[1]) {
			//throw "perm1="+perm[1] +" check="+ check[1] ;
		} else {
			//throw "perm1e="+perm[1] +" check="+ check[1] ;
			c=1 ;
		}
		if (+check[1]>0 && +perm[2]>0 && +perm[2]<check[1]) {
			//throw "perm2="+perm[2] +" check="+ check[1] ;
		} else {
			//throw "perm2="+perm[2] +" check="+ check[1] ;
			if (c==1) {
				return 1 ; 
			}
		}
	} 

	return 0 ;
}
function findLogginedSecUserId(aCtx) {
	var username = aCtx.sessionContext.callerPrincipal.name ;
	var list = aCtx.manager.createQuery("from SecUser where login = :login")
		.setParameter("login", username) 
		.getResultList() ;
	if(list.size()==0) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между "+username+" и SecUser" ;	
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
    if(functions.size()==0) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser" ;	
	if(functions.size()>1) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Больше одной рабочей функции у пользователя" ;	
	return functions.iterator().next() ;
}

function getWorkCalendarDayCalendarDate(aCtx, aCalDayId) {
	return aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay
			, new java.lang.Long(aCalDayId)).getCalendarDate() ;
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
	if(list.size()==0) throw "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между WorkFunction и SecUser" ;	
	return list ;
}

function getWorkFunctionByCalenDay(aCtx, aCalenDayId) {
	if (aCalenDayId==null) {
		return new java.lang.Long.valueOf("0") ;
	} else {
		var calenday = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay
			, new java.lang.Long.valueOf(""+aCalenDayId)) ;
	}
	return calenday.workFunction!=null?calenday.workFunction.id:new java.lang.Long.valueOf("0") ;
}

function getWorkFunctionInfo(aCtx,aWorkFunctionId) {
	var workFunction = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction
		, java.lang.Long.valueOf(""+aWorkFunctionId)) ;
	return workFunction!=null?workFunction.workFunctionInfo:"" ;
} 

