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
function isUnitLpu(aMainLpuId,aChildLpuId,aCtx) {
	//var mainId = aMainLpu.id ;
	var child = true ;
	
	while (child==true) {
		if (aChildLpuId==aMainLpuId) return true ;
		//throw "select top 1 parent_id from MisLpu where id="+aChildLpuId ;
		var idparent = aCtx.manager.createNativeQuery("select top 1 parent_id,count(*) from MisLpu where id="+aChildLpuId).getSingleResult() ;
		if (idparent[0]==null) break ;
		aChildLpuId = 0+ idparent[0] ;
		if(aChildLpuId<1) child=false ;
	}
	return false ;
}
	