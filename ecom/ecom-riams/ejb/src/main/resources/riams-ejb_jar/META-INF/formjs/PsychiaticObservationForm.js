function onCreate(aForm, aEntity, aContext) {
	if (aEntity.getCareCard()==null) {
		var careCard = aEntity.getLpuAreaPsychCareCard()!=null?aEntity.getLpuAreaPsychCareCard().getCareCard():null ;
		aEntity.setCareCard(careCard) ;
	}
	aContext.manager.persist(aEntity) ;
}
/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	
}

/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aContext) {
	
}
