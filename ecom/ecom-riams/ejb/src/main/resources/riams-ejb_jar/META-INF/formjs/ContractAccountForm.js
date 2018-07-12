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
	if (aForm.priceMedServicies!=null && aForm.priceMedServicies != "") {
        var addMedServicies = aForm.priceMedServicies.split("#");
        if (addMedServicies.length > 0) {
            //var id = aEntity.id ;
            //var account = aEntity.account ;
            for (var i = 0; i < addMedServicies.length; i++) {
                var param = addMedServicies[i].split(":");
                //throw ""+ addMedServicies[i] ;
                var par1 = java.lang.Long.valueOf(param[0]);
                var par2 = (param[1]) ? java.lang.Long.valueOf(param[1]) : null;
                var medService = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.PriceMedService, par1);
                var cnt = java.lang.Integer.valueOf(param[1]);
                if (+cnt > 0 && medService != null) {
                    var adMedService = new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService();
                    adMedService.setAccount(account);
                    adMedService.setMedService(medService);
                    adMedService.setCountMedService(cnt);
                    adMedService.setCost(medService.pricePosition.cost);
                    adMedService.setServedPerson(servedPerson);
                    aCtx.manager.persist(adMedService);
                }
            }
        }
    }
	//lastrelease milamesher 24.05.2018 #99
    saveVisits(aForm,aCtx,account,servedPerson);
    saveLabs(aForm,aCtx,account,servedPerson);
}
//milamesher 240518 создание направлений к специалисту
function saveVisits(aForm,aCtx,account,servedPerson) {
    if (aForm.getReferralsInfo()!=null && aForm.getReferralsInfo()!="") {
        var obj=new Packages.org.json.JSONObject(aForm.getReferralsInfo()+'');
        var arr = (Packages.org.json.JSONArray)(obj.get("visits"));
        var ltime = new java.util.Date().getTime();
        for (var i=0; i<arr.length(); i++) {
            var vis=(Packages.org.json.JSONObject)(arr.get(i));
            var visit = new Packages.ru.ecom.mis.ejb.domain.medcase.Visit();
            visit.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
            visit.setCreateDate(new java.sql.Date(ltime));
            visit.setCreateTime(new java.sql.Time(ltime));
            visit.setNoActuality(false);
            var patId=aCtx.manager.createNativeQuery("select cp.patient_id from ContractPerson cp where cp.id='"+aForm.servedPerson+"'").getSingleResult() ;
            if (patId!=null) visit.setPatient(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient, java.lang.Long(patId)));
            visit.setIsPaid(false);
            visit.setOrderWorkFunction(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
            visit.setServiceStream(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream, java.lang.Long(vis.get("sstreamId"))));
            visit.setWorkPlaceType(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocWorkPlaceType, java.lang.Long(vis.get("wplaceId"))));
            visit.setEmergency(java.lang.Boolean(vis.get("help")));
            if (vis.get("orderLpuId")!=null && vis.get("orderLpuId")!="")
                visit.setOrderLpu(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu, new java.lang.Long(vis.get("orderLpuId"))));
            visit.setWorkFunctionPlan(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.worker.WorkFunction, new java.lang.Long(vis.get("wf"))));
            visit.setDatePlan(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDay, new java.lang.Long(vis.get("date"))));
            var wct= aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarTime, new java.lang.Long(vis.get("time")));
            visit.setTimePlan(wct);
            aCtx.manager.persist(visit);
            wct.setMedCase(visit);
            var servs = (Packages.org.json.JSONArray)(vis.get("servs"));
            aCtx.manager.createNativeQuery("delete from medcase where parent_id="+visit.id+" and dtype='ServiceMedCase'").executeUpdate();
            for (var j=0; j<servs.length(); j++) {
                var ms=(Packages.org.json.JSONObject)(servs.get(j));
                var medServId=aCtx.manager.createNativeQuery("select medservice_id from pricemedservice  where id='"+ms.get("msid")+"'").getSingleResult() ;
                if (medServId!=null) {
                    var servMC=new Packages.ru.ecom.mis.ejb.domain.medcase.ServiceMedCase() ;
                    servMC.setParent(visit);
                    servMC.setPatient(visit.getPatient());
                    servMC.setMedService(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService,new java.lang.Long(medServId)));
                    servMC.setMedServiceAmount(new java.lang.Integer(ms.get("num")));
                    servMC.setWorkFunctionPlan(visit.getWorkFunctionPlan()); //WFPlan
                    servMC.setUsername(visit.getUsername());
                    servMC.setNoActuality(visit.getNoActuality());
                    servMC.setServiceStream(visit.getServiceStream());
                    servMC.setCreateDate(visit.getCreateDate());
                    servMC.setCreateTime(visit.getCreateTime());
                    aCtx.manager.persist(servMC);
                    //сохранение в CAMS
                    var pms = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.PriceMedService,new java.lang.Long(ms.get("msid")));
                    var adMedService=new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService() ;
                    adMedService.setAccount(account);
                    adMedService.setMedService(pms);
                    adMedService.setCountMedService(new java.lang.Integer(ms.get("num")));
                    adMedService.setCost(pms.pricePosition.cost) ;
                    adMedService.setServedPerson(servedPerson);
                    adMedService.setMedCase(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase,java.lang.Long(visit.id)));
                    aCtx.manager.persist(adMedService) ;
                }
            }
        }
    }
}
//milamesher 240518 создание направлений в лабораторию
function saveLabs(aForm,aCtx,account,servedPerson) {
    if (aForm.getReferralsInfoLab() != null && aForm.getReferralsInfoLab() != "") {
        var obj=new Packages.org.json.JSONObject(aForm.getReferralsInfoLab()+'');
        var arr = (Packages.org.json.JSONArray)(obj.get("labs"));
        var ltime = new java.util.Date().getTime();
        //Создаю визит к регистратору.
        var visit = new Packages.ru.ecom.mis.ejb.domain.medcase.Visit();
        visit.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
        visit.setCreateDate(new java.sql.Date(ltime));
        visit.setCreateTime(new java.sql.Time(ltime));
        visit.setNoActuality(true); //чтобы не висел в активных направлениях
        //visit.setDateStart(visit.getCreateDate());
        var patId=aCtx.manager.createNativeQuery("select cp.patient_id from ContractPerson cp where cp.id='"+aForm.servedPerson+"'").getSingleResult() ;
        if (patId!=null) visit.setPatient(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient, java.lang.Long(patId)));
        visit.setIsPaid(false);
        visit.setOrderWorkFunction(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
        var streamId=aCtx.manager.createNativeQuery("select id from vocservicestream where code='PARTIAL'").getSingleResult() ;
        if (streamId!=null) visit.setServiceStream(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream, java.lang.Long(streamId))); //поток обсл-я
        visit.setWorkFunctionPlan(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction")); //визит к регистратору
        aCtx.manager.persist(visit);
        //Создаю в визите лист назначений
        var pl = Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList();
        pl.setMedCase(visit);
        pl.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
        pl.setCreateDate(new java.sql.Date(ltime));
        pl.setCreateTime(new java.sql.Time(ltime));
        pl.setWorkFunction(visit.getWorkFunctionPlan());
        aCtx.manager.persist(pl);
        //Создаю в листе назначений назначения
        for (var i=0; i<arr.length(); i++) {
            var lab=(Packages.org.json.JSONObject)(arr.get(i));
            var num=new java.lang.Long(lab.get("num"));
            for (var j=0; j<num; j++) {
                var pr = Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription();
                pr.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
                pr.setCreateDate(new java.sql.Date(ltime));
                pr.setCreateTime(new java.sql.Time(ltime));
                pr.setPrescriptType(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType, new java.lang.Long(lab.get("prescriptTypeId"))));
                pr.setDepartment(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu, new java.lang.Long(lab.get("depIntakeId"))));
                pr.setPlanStartDate(new java.sql.Date((new java.text.SimpleDateFormat("dd.MM.yyyy")).parse(lab.get("date")).getTime()));
                pr.setPrescriptSpecial(aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction"));
                //pr.setMedCase(visit);
                pr.setIsUnPaid(true);
                var medServId = aCtx.manager.createNativeQuery("select medservice_id from pricemedservice  where id='" + lab.get("msid") + "'").getSingleResult();
                if (medServId != null) pr.setMedService(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedService, new java.lang.Long(medServId)));
                pr.setPrescriptionList(pl);
                aCtx.manager.persist(pr);
            }
            //сохранение в CAMS
            var pms = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.contract.PriceMedService,new java.lang.Long(lab.get("msid")));
            var adMedService=new Packages.ru.ecom.mis.ejb.domain.contract.ContractAccountMedService() ;
            adMedService.setAccount(account);
            adMedService.setMedService(pms);
            adMedService.setCountMedService(new java.lang.Integer(num));
            adMedService.setCost(pms.pricePosition.cost) ;
            adMedService.setServedPerson(servedPerson);
            adMedService.setMedCase(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase,java.lang.Long(visit.id)));
            //adMedService.setServicePrescription(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.ServicePrescription,java.lang.Long(pr.id)));
            aCtx.manager.persist(adMedService) ;
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