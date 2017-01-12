
/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	onCreate(aForm, aEntity, aCtx);
}
//FullOpenTime,FullOpenDate,PangsStartTime,PangsStartDate,WatersTime,WatersDate,TravailStartDate,TravailStartTime,
/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
	aCtx.manager.createNativeQuery("update uservalue set useByDefault='0' where domain_id="+aEntity.domain.id+" and id!="+aEntity.id).executeUpdate();
}