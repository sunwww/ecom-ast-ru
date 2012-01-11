/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aCtx) {
	var account = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccount();
	if(aEntity.autoAccount!=null && aEntity.autoAccount==true){
	account.setServedPerson(aEntity);
	account.setDateFrom(aEntity.dateFrom) ;
	account.setDateTo(aEntity.dateTo) ;
	aCtx.manager.persist(account);}
}

/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aContext) {
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	/**sqlQuery = "select count(*) from ContractAccount where servedperson_id="+aEntity.id;
	
	if(+res==0){
	var account = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccount();
	account.setServedPerson(aEntity);
	//sqlQuery = "insert into contractaccount (servedperson_id) VALUES("+aForm.ServedPerson+")";
	aCtx.manager.persist(account);
	//aCtx.manager.persist(aEntity);
	}*/
}


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
 * Перед удалением
 */
function onPreDelete(aEntityId, aContext) {
}

/**
 * При удалении
 */
function onDelete(aEntityId, aContext) {
}

