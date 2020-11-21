function onPreSave(aForm, aEntity, aCtx) {
	if (aEntity.parent!=null) {
		if ((0+aForm.parent)>0 && !((0+aEntity.parent.id)==(0+aForm.parent))) {
			//var parentNew = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu,aForm.parent) ;
			//throw "Нельзя переместить подразделение в подчиненное" ;
			if ((0+aForm.parent)==(0+aForm.id)) throw "Нельзя сделать чтоб совпадало головное ЛПУ с текущим!!!!" ;
			if (isUnitLpu(aForm.id,aForm.parent,aCtx)) throw "Нельзя переместить подразделение в подчиненное" ;
		}
	}
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
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}
function isUnitLpu(aMainLpuId,aChildLpuId,aCtx) {
	//var mainId = aMainLpu.id ;
	var child = true ;
	
	while (child==true) {
		if (aChildLpuId==aMainLpuId) return true ;
		//throw "select top 1 parent_id from MisLpu where id="+aChildLpuId ;
		var idparent = aCtx.manager.createNativeQuery("select parent_id,count(*) from MisLpu where id="+aChildLpuId+" group by parent_id").getSingleResult() ;
		if (idparent[0]==null) break ;
		aChildLpuId = 0+ idparent[0] ;
		if(aChildLpuId<1) child=false ;
	}
	return false ;
}
function onPreDelete(aEntityId, aCtx){
	aCtx.manager.createNativeQuery("delete from RepMisLpuChild where lpu_id=:lpu or childLpu_id=:lpu")
	.setParameter("lpu", aEntityId).executeUpdate();
}
	