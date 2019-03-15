function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	var username=aCtx.getSessionContext().getCallerPrincipal().toString() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(username) ;

	var account=aEntity ;
	var servedPerson = new Packages.ru.ecom.mis.ejb.domain.contract.ServedPerson() ;
	var person = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractPerson,aForm.servedPerson) ;
	servedPerson.setContract(account.contract) ;
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
				if (par3!=null) adMedService.setDoctor(par3);
				aCtx.manager.persist(adMedService) ;
			}
		}
	}
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
function onPreDelete(aId, aCtx) {
	var obj=aCtx.manager.createNativeQuery("select count(*) from contractaccountoperation where account_id='"+aId+"' and (isDeleted is null or isDeleted='0')").getSingleResult() ;
	if (+obj>0) throw "Сначала нужно аннулировать операции по счету!!!" ;
	aCtx.manager.createNativeQuery("﻿delete from contractaccountmedservice where account_id='"+aId+"'").executeUpdate() ;
	aCtx.manager.createNativeQuery("delete from servedperson where account_id='"+aId+"'").executeUpdate() ;
}
