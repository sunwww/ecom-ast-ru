/**
 * При создании
 * @param aForm форма
 * @param aEntity сущность
 * @param aContext контекст
 */
function onCreate(aForm, aEntity, aContext) {
	/*
	 
	 var napr = aCtx.manager.createNativeQuery("" +
			"insert into contractaccountmedservice " +
			"(medservice_id, account_id) " +
			"VALUES ('1','1')"
			).executeUpdate() ;
			*/
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
function onSave(aForm, aEntity, aContext) {
	
	for(i=1; i<=5000;i++){
		if(request.getParameter("count"+i)!=null){
		query = query + "insertmedser("+id+","+
				i+","+
				request.getParameter("count"+i)+"), ";}
	}
	query = query + "2";
}


/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
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