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
	
	var pms = aForm.getMedService();
	if (+pms>0) {
		var ms = aContext.manager.createNativeQuery ("select medservice_id from pricemedservice where id="+pms).getResultList().get(0);
		if (ms!=null&&+ms>0) aEntity.setServiceIn(+ms);
	}
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