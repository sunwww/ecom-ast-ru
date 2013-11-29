/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
}
/**
 * Перед созданием
 */
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aForm.setOperationDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setOperationTime(new java.sql.Time (date.getTime())) ;
	aForm.setWorkFunction(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction")
			.id) ;
}
function onPreDelete(aId, aCtx) {
	aCtx.manager.createNativeQuery("update contractaccountoperation set repealOperation_id=null where repealOperation_id='"+aId+"'").executeUpdate() ;
	aCtx.manager.createNativeQuery("﻿delete from contractaccountoperationbyservice where AccountOperation_id='"+aId+"'").executeUpdate() ;
}