/**
 * При создании
 */
function onCreate(aForm, aEntity, aContext) {
	var illnesPrimary=aEntity.illnesPrimary  ;
	if (illnesPrimary!=null) {
		aEntity.setAcuity(illnesPrimary.getIllnesType()) ;
		aEntity.setPrimary(illnesPrimary.getPrimary()) ;
		
	}
	if (aEntity.getPatient()==null) {
		var pat = aEntity.getMedCase()!=null?aEntity.getMedCase().getPatient():null ;
		aEntity.setPatient(pat) ;
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
	if (aEntity.getPatient()==null) {
		var pat = aEntity.getMedCase()!=null?aEntity.getMedCase().getPatient():null ;
		aEntity.setPatient(pat) ;
	}
	aContext.manager.persist(aEntity) ;
}