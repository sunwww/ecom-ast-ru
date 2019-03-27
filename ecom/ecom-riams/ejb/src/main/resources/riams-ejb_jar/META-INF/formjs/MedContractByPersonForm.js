function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	var username=aCtx.getSessionContext().getCallerPrincipal().toString() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(username) ;

	var account=new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccount() ;
	//account.setServedPerson(servedPerson) ;
	account.setContract(aEntity) ;
	account.setDateFrom(aEntity.dateFrom) ;
	account.setDateTo(aEntity.dateTo) ;
	account.setCreateDate(new java.sql.Date(date.getTime())) ;
	account.setCreateTime(new java.sql.Time(date.getTime())) ;
	account.setCreateUsername(username) ;
	if (aForm.discountDefault!=null && aForm.discountDefault!="") {
		account.setDiscountDefault(new java.math.BigDecimal(aForm.discountDefault)) ;
	}

	if(aForm.privilege!=null && aForm.privilege!="" && aForm.privilege>0) {
        var privilege = aCtx.manager.find(Packages.ru.ecom.mis.ejb.uc.privilege.domain.Privilege, aForm.privilege);
        account.setPrivilege(privilege);
    }

	aCtx.manager.persist(account) ;
	var servedPerson = new Packages.ru.ecom.mis.ejb.domain.contract.ServedPerson() ;
	var person = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractPerson,aForm.servedPerson) ;
	servedPerson.setContract(aEntity) ;
	servedPerson.setAccount(account) ;
	servedPerson.setPerson(person) ;
	servedPerson.setDateFrom(aEntity.dateFrom) ;
	servedPerson.setDateTo(aEntity.dateTo) ;
	servedPerson.setCreateDate(new java.sql.Date(date.getTime())) ;
	servedPerson.setCreateTime(new java.sql.Time(date.getTime())) ;
	servedPerson.setCreateUsername(username) ;
	aCtx.manager.persist(servedPerson) ;
	var addMedServicies = aForm.priceMedServicies.split("#") ;
	if (addMedServicies.length>0 && aForm.priceMedServicies!=null && aForm.priceMedServicies !="") {
		//var id = aEntity.id ;
		//var account = aEntity.account ;
		for (var i=0; i<addMedServicies.length; i++) {
			var param = addMedServicies[i].split(":") ;
			//throw ""+ addMedServicies[i] ;
			var par1 = java.lang.Long.valueOf(param[0]) ;
	//		var par2 = (param[1])?java.lang.Long.valueOf(param[1]):null ;
			var par3 = (param[2]) ?  +param[2] : null ;
			var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.PriceMedService,par1) ;
			var cnt = java.lang.Integer.valueOf(param[1]) ;
			if (+cnt>0 && medService!=null) {
				var adMedService=new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService() ;
				adMedService.setAccount(account) ;
				adMedService.setMedService(medService) ;
				adMedService.setCountMedService(cnt) ;
				adMedService.setCost(medService.pricePosition.cost) ;
				adMedService.setServedPerson(servedPerson) ;
				if (par3!=null && +par3>0) adMedService.setDoctor(par3);
				aCtx.manager.persist(adMedService) ;
			}
		}
	}
}
function onPreDelete(aId, aCtx) {
	var obj=aCtx.manager.createNativeQuery("select count(*) from contractaccount where contract_id='"+aId+"' and (isDeleted is null or isDeleted='0')").getSingleResult() ;
	if (+obj>0) throw "Сначала нужно аннулировать счета!!!" ;
}
