function onCreate(aForm, aEntity, aCtx) {
   aEntity.pregnancy.confinedExchangeCard = aEntity ;
}

function onPreDelete(aEntityId, aCtx) {
	var confinedExchangeCard = 
		aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.ConfinedExchangeCard, 
			new java.lang.Long(aEntityId)) ;
	var pregnancy = confinedExchangeCard.pregnancy ;
	pregnancy.confinedExchangeCard = null ;
	confinedExchangeCard.pregnancy = null ;
}