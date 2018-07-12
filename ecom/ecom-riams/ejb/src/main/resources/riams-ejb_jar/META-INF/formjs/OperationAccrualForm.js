function onPreCreate(aForm, aCtx) {
	//var wf=aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;

}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}
/**
 * Перед сохранением
 */
function onCreate(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    aEntity.setOperationDate(new java.sql.Date(date.getTime()));
    aEntity.setOperationTime(new java.sql.Time(date.getTime()));
    var username = aCtx.getSessionContext().getCallerPrincipal().toString();
    var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction");
    aEntity.setWorkFunction(wf);
    aEntity.setCreateDate(new java.sql.Date(date.getTime()));
    aEntity.setCreateTime(new java.sql.Time(date.getTime()));
    aEntity.setCreateUsername(username);
    var servicies = aForm.medServicies.split(',');
    for (var i = 0; i < servicies.length; i++) {
        var serv = servicies[i].trim();
        var cams = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService();
        var ams = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService, java.lang.Long.valueOf(serv));
        cams.setAccountOperation(aEntity);
        cams.setAccountMedService(ams);
        aCtx.manager.persist(cams);
    }


    if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/AutoOperationWriteOff")) {
        var writeOff = new Packages.ru.ecom.mis.ejb.domain.contract.OperationWriteOff();
        writeOff.setAccount(aEntity.account);
        writeOff.setCost(aEntity.cost);
        writeOff.setDiscount(aEntity.discount);
        writeOff.setIsPaymentTerminal(aEntity.isPaymentTerminal);
        writeOff.setOperationDate(new java.sql.Date(date.getTime()));
        writeOff.setOperationTime(new java.sql.Time(date.getTime()));
        writeOff.setCreateDate(new java.sql.Date(date.getTime()));
        writeOff.setCreateTime(new java.sql.Time(date.getTime()));
        writeOff.setCreateUsername(username);
        aCtx.manager.persist(writeOff);
        var servicies = aForm.medServicies.split(',');
        for (var i = 0; i < servicies.length; i++) {
            var serv = servicies[i].trim();
            var cams = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService();
            var ams = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService, java.lang.Long.valueOf(serv));
            cams.setAccountOperation(writeOff);
            cams.setAccountMedService(ams);
            aCtx.manager.persist(cams);
        }
    } else {
        var balSumOld = +aEntity.account.balanceSum;
        var balSum = +aEntity.account.balanceSum;
        var cost = aEntity.cost;
        if (+aEntity.discost > 0) cost = cost * aEntity.discost / 100;
        balSum = balSum + cost;
        aEntity.account.setBalanceSum(new java.math.BigDecimal(balSum));
        aEntity.account.setReservationSum(new java.math.BigDecimal(balSum));
    }
    setMedCasesPaid(aEntity, aCtx);

    // Отправляем запрос на ККМ
    if (aCtx.getSessionContext().isCallerInRole("/Policy/Config/KKMWork")) {

		var kkm = new Packages.ru.ecom.mis.ejb.service.contract.ContractServiceBean();
		var worker = wf.worker.person;
		var fio = wf.workFunction.name + " " + worker.lastname + " " + worker.firstname.substring(0, 1) + ". " + (worker.middlename != null ? worker.middlename.substring(0, 1) + "." : "");
		kkm.sendKKMRequest("makePayment", aEntity.getAccount().getId(), aForm.getDiscount(), aEntity.getIsPaymentTerminal() != null ? aEntity.getIsPaymentTerminal() : false, fio, aCtx.manager);
}
    //**** ***//
}
function onPreDelete(aId, aCtx) {
	aCtx.manager.createNativeQuery("﻿delete from contractaccountoperationbyservice where AccountOperation_id='"+aId+"'").executeUpdate() ;
}
//Проставляем признак оплаты для неоплаченных визитов
function setMedCasesPaid(aEntity, aCtx) {
	var contractId = aEntity.account.contract.id;
	var accountId = aEntity.account.id;
	//Отмечаем все направления как оплаченные
	var sql = "update medcase m set isPaid='1' where m.patient_id in (select cp.patient_id from contractperson cp left join servedperson sp on sp.person_id=cp.id where sp.account_id="+accountId+" and sp.contract_id="+contractId+") and m.dtype='Visit' and m.dateStart is null and m.isPaid='0'";
	aCtx.manager.createNativeQuery(sql).executeUpdate();
}
