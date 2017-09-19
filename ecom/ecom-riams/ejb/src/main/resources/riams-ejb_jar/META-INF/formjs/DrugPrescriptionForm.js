
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
 * Перед созданием
 */
function onCreate(aForm, aEntity, aCtx) {
    var date = new java.util.Date() ;
    aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
    aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
    aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
    var sql = "select wf.id from secuser su "+
        "left join workfunction wf on wf.secuser_id = su.id "+
        "where su.login = '"+aCtx.getSessionContext().getCallerPrincipal().toString()+"'";
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    var workfunc = ""+list.get(0);
    var Id = aEntity.getId();
    sql = "select (to_date('"+aEntity.getPlanEndDate()+"','yyyy-MM-dd') - to_date('"+aEntity.getPlanStartDate()+"','yyyy-MM-dd')) as dates";
    list = aCtx.manager.createNativeQuery(sql).getResultList();
    var dateCount = ""+list.get(0);
    var countPerDay = aEntity.getAmount();
    var freq = aEntity.getFrequency();

    var result = (dateCount+1)*(freq*countPerDay);
    var functionExist = new Packages.ru.ecom.mis.ejb.service.pharmacy.PharmOperationServiceBean.setFunctionReserve(aCtx.manager);
    sql = "select pharmReseve("+aEntity.getDrug().getId()+","+result+","+Id+","+workfunc+");";
    aCtx.manager.createNativeQuery(sql).getResultList();
}