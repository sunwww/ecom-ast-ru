/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setAcuity(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
	}
	aContext.manager.persist(aEntity) ;
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aContext) {
	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setAcuity(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
	}
	aContext.manager.persist(aEntity) ;
}