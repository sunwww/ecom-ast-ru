/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aForm.histology!=null && +aForm.histology>0) {
		aForm.placentaHistologyOrder=true ;
	}
}
/**
 * Перед сохранением
 */
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aForm.histology!=null && +aForm.histology>0) {
		aForm.placentaHistologyOrder=true ;
	}
}
function onCreate(aForm, aEntity, aContext) {
	aEntity.sls.pregnancy=aEntity.pregnancy ;
}
