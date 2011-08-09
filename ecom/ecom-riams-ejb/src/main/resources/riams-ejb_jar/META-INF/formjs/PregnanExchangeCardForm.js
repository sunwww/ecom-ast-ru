function onCreate(aForm, aEntity, aCtx) {
   aEntity.pregnancy.pregnanExchangeCard = aEntity ;
}

function onPreDelete(aEntityId, aCtx) {
	var pregnanExchangeCard = 
		aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.PregnanExchangeCard, 
			new java.lang.Long(aEntityId)) ;
	var pregnancy = pregnanExchangeCard.pregnancy ;
	pregnancy.pregnanExchangeCard = null ;
	pregnanExchangeCard.pregnancy = null ;
}