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
        var el = servicies[i].trim().split("#");
        var serv = el[0];
        var amount = el[1]; //Для каждой единицы услуги делаем свой cams
        var ams = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService, java.lang.Long.valueOf(serv));
        for (var j=0;j<amount;j++) {
        var cams = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService();
        cams.setAccountOperation(aEntity);
        cams.setAccountMedService(ams);
        aCtx.manager.persist(cams);
        }
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
     //   var balSumOld = +aEntity.account.balanceSum;
        var balSum = +aEntity.account.balanceSum;
        var cost = +aEntity.cost;
        if (+aForm.discount > 0) {cost = cost-(cost * aForm.discount / 100);}
        balSum = balSum + cost;
        aEntity.account.setBalanceSum(new java.math.BigDecimal(balSum));
        aEntity.account.setReservationSum(new java.math.BigDecimal(balSum));
    }
    setMedCasesPaid(aEntity, aCtx);
    setInnerComplexMedService(aEntity,aForm, aCtx);
    //Milamesher #136 если у пользователя настроен ККМ
    var defaultKkm=wf.getKkmEquipmentDefault();
    if (aCtx.getSessionContext().isCallerInRole("/Policy/Config/KKMWork") && defaultKkm!=null) {
        var kkm = new Packages.ru.ecom.mis.ejb.service.contract.ContractServiceBean();
        var worker = wf.worker.person;
        var fio = wf.workFunction.name + " " + worker.lastname + " " + worker.firstname.substring(0, 1) + ". " + (worker.middlename != null ? worker.middlename.substring(0, 1) + "." : "");
        kkm.sendKKMRequest("makePayment", aEntity.getAccount().getId(), aForm.getDiscount(), aEntity.getIsPaymentTerminal() != null ? aEntity.getIsPaymentTerminal() : false, aForm.getCustomerPhone(), fio, aCtx.manager,defaultKkm.getUrl());
    }
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
//Добавляем начисления услуг в программе коплексной
/**
 * Добавить начисления услуг в комплексной программе #178
 * @param aEntity сущность
 * @param aForm форма
 * @param aCtx контекст
 */
function setInnerComplexMedService(aEntity,aForm, aCtx) {
    var servicies = aForm.medServicies.split(','); //список услуг в договоре
    for (var i = 0; i < servicies.length; i++) {
        var el = servicies[i].trim().split("#");
        var complexCamsId = el[0];
        var complexCams = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService, java.lang.Long.valueOf(complexCamsId));
        var complexMedServiceId = complexCams.getMedService().getMedService().id;
        var amount = el[1];
        //список услуг в программе коплексной услуги
        var listInner=aCtx.manager.createNativeQuery("select innermedservice_id,countinnermedservice from MedServiceComplexLink where complexmedservice_id='"+complexMedServiceId+"'").getResultList() ;
        //если есть, создать cams с отметкой, что это - часть комплексной программы
        if (listInner.size()>0) {
            for (var j=0;j<amount;j++) {
                for (var i = 0; i < listInner.size(); i++) {
                    var innerMedServiceId = listInner.get(i)[0];
                    var innerAmount = listInner.get(i)[1];
                    var innerCams = getAndCreateCAMS(aEntity.account,innerMedServiceId, complexMedServiceId, innerAmount, aCtx,complexCamsId);
                    //Для каждой единицы услуги делаем свой cams
                    if (innerCams!=null)
                        createCAOS(innerCams,innerAmount,aEntity,aCtx);
                }
            }
        }
    }
}

/**
 * Получить PriceMedService для cams #178
 * @param innerMedServiceId MedService.id id услуги, входящей в комплексную программу
 * @param priceList PriceList.id id прейскурант (определяется по комплексной услуге)
 * @param aCtx контекст
 * @return PriceMedService
 */
function getPriceMedService(innerMedServiceId, priceList, aCtx) {
    var list = aCtx.manager.createNativeQuery("select pms.id from pricemedservice pms" +
        "  left join medservice ms on ms.id=pms.medservice_id" +
        "  left join priceposition pp on pp.id=pms.priceposition_id" +
        "  where (pms.dateto is null or pms.dateto<=current_date)" +
        "  and ms.id='"+innerMedServiceId+"' and pp.pricelist_id='"+priceList+"'").getResultList();
    return list.isEmpty()? null: list.get(0);
}

/**
 * Получить и создать ContractAccountMedService cams #178
 * @param account ContractAccount
 * @param innerMedServiceId MedService.id id услуги, входящей в комплексную программу
 * @param complexMedServiceId MedService.id id комлпексная услуга
 * @param innerAmount Кол-во этой услуги в программе
 * @param aCtx контекст
 * @param camsId ContractAccountMedService.id комплексной услуги
 * @return ContractAccountMedService услуги внутри программы
 */
function getAndCreateCAMS(account,innerMedServiceId, complexMedServiceId, innerAmount, aCtx, camsId) {
    //создать cams
    var innerCams=new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService() ;
    innerCams.setAccount(account);
    //нужно проставить позицию прейскуранта по прейскуранту комплексной услуги и мед. услуги в программе
    var camsComplex = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService, java.lang.Long.valueOf(camsId));
    var priceList = camsComplex.getMedService().getPricePosition().getPriceList();
    var priceMedService = getPriceMedService(innerMedServiceId, priceList.id, aCtx);
    if (priceMedService!=null) {
        innerCams.setMedService(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.PriceMedService, java.lang.Long.valueOf(priceMedService)));
        //var innerMedService = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase,innerMedServiceId) ;
        innerCams.setCountMedService(innerAmount);
        innerCams.setServedPerson(camsComplex.getServedPerson());
        innerCams.setFromComplexMedServiceId(complexMedServiceId);
        //workfunction,cost, doctor не буду сохранять
        aCtx.manager.persist(innerCams);
        return innerCams;
    }
    else
        return null;
}

/**
 * Cоздать ContractAccountOperationByService на каждую из внутренних услуг в программе #178
 * @param cams ContractAccountMedService внутренней услуги
 * @param innerAmount Кол-во этой услуги в программе
 * @param aEntity сущность
 * @param aCtx контекст
 */
function createCAOS(innerCams,innerAmount,aEntity,aCtx) {
    for (var j=0;j<innerAmount;j++) {
        var caos = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService();
        caos.setAccountOperation(aEntity);
        caos.setAccountMedService(innerCams);
        aCtx.manager.persist(caos);
    }
}