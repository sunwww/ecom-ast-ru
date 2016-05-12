function onCreate(aForm, aEntity, aContext){
	var addMedServicies = aForm.additionMedService.split("#") ;
	if (addMedServicies.length>0 && aForm.additionMedService!=null && aForm.additionMedService !="") {
		//var id = aEntity.id ;
		var account = aEntity.account ;
		for (var i=0; i<addMedServicies.length; i++) {
			var param = addMedServicies[i].split(":") ;
			//throw ""+ addMedServicies[i] ;
			var par1 = java.lang.Long.valueOf(param[0]) ;
			var par4 = (param[3])?java.lang.Long.valueOf(param[3]):null ;
			var medService = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.PriceMedService,par1) ;
			var cost = new java.math.BigDecimal(param[1]) ;
			var cnt = java.lang.Integer.valueOf(param[2]) ;
			var wf = (par4!=null&&par4>java.lang.Long.valueOf(0))? aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction,par4):null ;
			var adMedService=new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService() ;
			adMedService.setAccount(account) ;
			adMedService.setMedService(medService) ;
			adMedService.setWorkFunction(wf) ;
			adMedService.setCountMedService(cnt) ;
			adMedService.setCost(cost) ;
			//aContext.manager.createNativeQuery(
			//		"insert into ContractAccountMedService (account_id,medService_id"
			//		+",cost,countMedService,workFunction_id) values ('"
			//		+id+"','"++"','"+param[2]+"','"+param[3]+"','"+param[4]+"')").executeUpdate() ;
			aContext.manager.persist(adMedService) ;
		}
	}
}
function onSave(aForm, aEntity, aContext){
	var id=aForm.id ;
	aContext.manager.createNativeQuery("delete from ContractAccountMedService where account_id='"+id+"'").executeUpdate() ;
	onCreate(aForm, aEntity, aContext) ;
	
}
