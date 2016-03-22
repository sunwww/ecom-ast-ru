/**
 * Перед созданием
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aEntity.getDeathCase().isAutopsy=false ;
	aEntity.getDeathCase().dateForensic=null ;
	aEntity.getDeathCase().postmortemBureauNumber="" ;
	aEntity.getDeathCase().postmortemBureauDt=null ;
	aCtx.manager.persist(aEntity.getDeathCase()) ;
}
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	aEntity.getDeathCase().isAutopsy=false ;
	aEntity.getDeathCase().dateForensic=null ;
	aEntity.getDeathCase().postmortemBureauNumber="" ;
	aEntity.getDeathCase().postmortemBureauDt=null ;
	aCtx.manager.persist(aEntity.getDeathCase()) ;
}