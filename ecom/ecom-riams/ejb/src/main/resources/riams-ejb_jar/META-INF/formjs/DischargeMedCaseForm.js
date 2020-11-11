function onPreCreate(aForm, aCtx) {
	onPreSave(aForm,aCtx)
}
function getObject(aCtx,aId,aClazz) {
	return (aId==null||aId=='0'||aId=='') ? null : aCtx.manager.find(aClazz, new java.lang.Long(aId)) ;
}
function closePrescriptions(aForm,aContext) {
	var ids = aContext.manager.createNativeQuery("select list (''||pl.id) from prescriptionlist pl where medcase_id in (select id from medcase where parent_id=:sls and dtype='DepartmentMedCase')").setParameter("sls",aForm.id ).getResultList();
	if (ids!=null && !ids.isEmpty()) {
		var idss = ids.get(0);
		if (idss!=null&&idss!="") {
			aContext.manager.createNativeQuery("update prescription p set planEndDate =to_date(:endDate,'dd.MM.yyyy'), planEndTime=cast(:endTime as time)" +
					" where p.prescriptionList_id in ("+idss+") and p.dtype in ('DietPrescription', 'ModePrescription') and p.planEndDate is null")
					.setParameter("endDate", aForm.dateFinish).setParameter("endTime", aForm.dischargeTime).executeUpdate();
		}
	}
}
function onSave(aForm,aEntity, aCtx) {
	Packages.ru.ecom.mis.ejb.service.medcase.HospitalMedCaseServiceBean.saveDischargeEpicrisisByCase(aEntity,aForm.getDischargeEpicrisis(),aCtx.manager) ;
	if (+aForm.reasonDischarge>0 && aEntity.statisticStub!=null) {
		var reasonDischarge = getObject(aCtx, aForm.reasonDischarge, Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocReasonDischarge);
		aEntity.statisticStub.setReasonDischarge(reasonDischarge) ;
		aCtx.manager.persist(aEntity) ;
	}
	if (+aForm.resultDischarge>0 && aEntity.statisticStub!=null) {
		var resultDischarge = getObject(aCtx, aForm.resultDischarge, Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocResultDischarge);
		aEntity.statisticStub.setResultDischarge(resultDischarge) ;
		aCtx.manager.persist(aEntity) ;
	}
	closePrescriptions(aForm, aCtx);
    checkPaidServicesExecuted(aEntity, aCtx);
}

function totalDenialToEditDischargeAfter(aForm, aCtx) {
	//Полный запрет на редактирование после выписки!
	//Если уже есть дата и время выписки (не в будущем), запретить
	var isDeniedList = aCtx.manager.createNativeQuery("select case when (datefinish is not null and dischargetime is not null" +
		" and current_timestamp>cast((datefinish||' '||dischargetime) as TIMESTAMP)) then '1' else '0' end from medcase where id=:aId")
		.setParameter("aId",aForm.id)
		.getResultList();
	var isDenied = !isDeniedList.isEmpty() ? +isDeniedList.get(0) : null ;
	if (isDenied!=null && +isDenied==1)
		throw "Изменение выписки невозможно, т.к. пациент уже выписан! Обратитесь в КЭО.";
}

function onPreSave(aForm,aEntity, aCtx) {
	if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/DotCheckDatesTimesAdmin"))
		totalDenialToEditDischargeAfter(aForm, aCtx);
    //checkUniqueDiagnosis(aForm,aCtx);
    checkDeathThenPlan(aCtx, +aForm.result, +aForm.reasonDischarge);
    checkNewBornScreeningSecondExists(aForm,aCtx);
    //проверка на наличие ЭК по критериям
    //checkIfIsQECard(aForm, aCtx);
    checkIfExistingActRVKClosed(aForm,aCtx);
	var manager = aCtx.manager;
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/DotSave"))throw "Вы не можете сохранять выписку!!!!!!"
	if (!aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/DontCheckPregnancy")) {
		var slos = manager.createNativeQuery("select id from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase' and transferDate is null").setParameter("parent",aForm.id).getResultList();
		var lastSlo = !slos.isEmpty() ? +slos.get(0) : null ;
		if (lastSlo!=null) {
			var isPregnancyCreate = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentMedCaseCreateInterceptor.isPregnancyExists(aCtx.manager,  +lastSlo);
			if (!isPregnancyCreate) {
				throw "Выписка невозможна, т.к. не заполнены данные по родам!";
			} 
		}
	}

	checkAllDiagnosis (aCtx, aForm.id) ;
	var date = new java.util.Date() ;
	// Проверка введенных данных
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(Packages.ru.nuzmsh.util.format.DateFormat.formatToTime(new java.sql.Time (date.getTime()))) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	var stat=aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/OnlyCurrentDay") ;
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Discharge/DotCheckDatesTimesAdmin"))
		stat=false;
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	var dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
	var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;

	if (aForm.concludingDiagnos!=null &&aForm.concludingDiagnos!="" && (aForm.concludingMkb==null || aForm.concludingMkb==0)) throw "Не указан код МКБ заключительного диагноза!" ;
	if ((aForm.concludingMkb!=null && aForm.concludingMkb>0)&&(aForm.concludingDiagnos==null||aForm.concludingDiagnos=="")) throw "Не сформулирован заключительный диагноз!" ;

	if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки должна быть больше, чем дата поступления";
	
	var ldate = aCtx.manager.createNativeQuery("select transferDate,transferTime from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase' and transferDate is not null order by transferDate desc,transferTime desc")
		.setParameter("parent",aForm.id)
		.setMaxResults(1)
		.getResultList() ;
	if (!ldate.isEmpty()) {
		var dmax=ldate.get(0) ;
			var dateMax = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(dmax[0],dmax[1]);
			if ((dateFinish.getTime() < dateMax.getTime())) {
				var cal = java.util.Calendar.getInstance() ;
				cal.setTime(dateMax) ;			
				throw "Дата выписки должна быть больше, чем дата последнего перевода "+cal.get(java.util.Calendar.DATE)+"."+(cal.get(java.util.Calendar.MONTH)+1)+"."+cal.get(java.util.Calendar.YEAR)
				+" "+cal.get(java.util.Calendar.HOUR_OF_DAY)
				+":"+cal.get(java.util.Calendar.MINUTE)
				;
		}
	}
	var loper = aCtx.manager.createNativeQuery("select so.id as soid,to_char(so.operationDate,'') as operdate ,so1.id as so1id,so1.operationDate as oper1date "
          + " from MedCase as smo" 
          +" left join surgicaloperation so on so.medcase_id=smo.id"
          +" left join medcase smo1 on smo1.parent_id=smo.id"
          +" left join surgicaloperation so1 on so1.medcase_id=smo1.id"
          +" where smo.id='"+aForm.id+"' "
          +" and (so.operationDate>to_date('"+aForm.dateFinish+"','dd.mm.yyyy')"
          +" or so1.operationDate>to_date('"+aForm.dateFinish+"','dd.mm.yyyy'))"
          +"").getResultList();
	if (!loper.isEmpty()) {
		var obj = loper.get(0)
		throw "Дата выписки должна быть больше, чем дата операции <a href='entityParentEdit-stac_surOperation.do?id="+
		+(obj[0]!=null?obj[0]:obj[2])+"'>"+
		(obj[0]!=null?obj[1]:obj[3])+"</a>"
		;
	}
	if (stat) {
		

		var cal1 = java.util.Calendar.getInstance() ;
		var cal2 = java.util.Calendar.getInstance() ;
		var cal3 = java.util.Calendar.getInstance() ;
		cal3.setTime(dateCur) ;		
		cal2.setTime(dateCur) ;		
		cal1.setTime(dateFinish) ;
		var cntHour = +getDefaultParameterByConfig("edit_slsDischarge_after_discharge", 24, aCtx) ;
		cal3.add(java.util.Calendar.HOUR_OF_DAY,(-1*cntHour)) ;
		
		if (cal1.after(cal3)) {
			
			} else{
				var param = new java.util.HashMap() ;
				param.put("obj","DischargeMedCase") ;
				param.put("permission" ,"editAfterDischarge") ;
				param.put("id", aForm.id) ;
				var check=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
				
				if (+check==0) {
					throw "У Вас стоит ограничение на дату выписки. Вы можете выписывать в течение "+cntHour+" часов.";
					
				}
			}
	}
	//Проставить в карте коронавируса Дату,результат госпитализации и основной выписной диагноз
	setCovidDateResultHospAndMkb(aForm,aEntity, aCtx);
}

//Проставить в карте коронавируса Дату,результат госпитализации и основной выписной диагноз
function setCovidDateResultHospAndMkb(aForm,aEntity, aCtx) {
	var sqlAdd='';
	if (aForm.concludingMkb!=null && aForm.concludingMkb>0)
			sqlAdd = ',mkbDischarge_id = ' + aForm.concludingMkb;
	aCtx.manager.createNativeQuery("update Covid19 set ishoddate=to_date('" + aForm.dateFinish + "','dd,mm.yyyy'), hospResult_id="
		+ aForm.result + sqlAdd + " where medcase_id = " +  aForm.id + " and id = (select max(id) from Covid19 where medcase_id = " + aForm.id + ")").executeUpdate();
}

function getDefaultParameterByConfig(aParameter, aValueDefault, aCtx) {
	var l = aCtx.manager.createNativeQuery("select sf.id,sf.keyvalue from SoftConfig sf where  sf.key='"+aParameter+"'").getResultList();
	return l.isEmpty() ? aValueDefault : l.get(0)[1] ;
}
function checkAllDiagnosis (aCtx, aSlsId) {
	if (aCtx.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/DotPrintWithoutDiagnosisInSlo")){
		var sql = "select slo.id as sloid,ml.name||' '||to_char(slo.dateStart,'dd.mm.yyyy')||coalesce('-'||to_char(slo.transferDate,'dd.mm.yyyy'),'') as info from medcase sls "
		 +" left join medcase slo on sls.id=slo.parent_id and slo.dtype='DepartmentMedCase'"
		 +" left join mislpu ml on ml.id=slo.department_id"
		 +" left join diagnosis d on slo.id = d.medcase_id"
		 +" left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id"
		 +" left join vocprioritydiagnosis vpd on vpd.id=d.priority_id"
		 +" where sls.id='"+aSlsId+"' and (ml.isnoomc is null or ml.isnoomc='0') "
		 +" group by sls.id,slo.id,ml.name,slo.dateStart,slo.transferDate	"
		 +" having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0  ";
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (!list.isEmpty()) {
			var slo ="" ;
			for (var i=0; i<list.size(); i++) {
				slo = slo+" <a href='entitySubclassView-mis_medCase.do?id="+list.get(0)[0]+"' onclick='return  msh.util.FormData.getInstance().isChangedForLink() ;'>" +list.get(0)[1]+"</a>" ;
			}
			throw "Не полностью заполнены данные по диагнозам в отделениях!!! "+ slo ;
		}	else {

		}
	}
}
//выписной диагноз должен быть уникальным
function checkUniqueDiagnosis(aForm,aCtx) {
    if (aForm.id != null) {
        var sql = "select priority_id,idc10_id,registrationtype_id from diagnosis where medcase_id=" + aForm.id;
        var list = aCtx.manager.createNativeQuery(sql).getResultList();
        if (!list.isEmpty()) {
            var mas = [];
         //   var all="";
            var str = "";
            for (var i = 0; i < list.size(); i++) {
                str=str + list.get(i)[0] + ";";
                str=str + list.get(i)[1] + ";";
                str=str + list.get(i)[2] + ";" + "#";
               	mas.push(str);
               	str="";
            }
            for (var i = 0; i < mas.length; i++) {
                for (var j = 0; j < mas.length; j++) {
                    if (i != j && mas[i] == mas[j]) {
                        throw "Не должно быть абсолютно одинаковых диагнозов по типу регистрации,приоритету и коду МКБ!";
                    }
                }
            }
        }
    }
}
//проверка на если результат - смерть, то только плановая
function checkDeathThenPlan(aCtx,resultId,reasonId) {
	var result = getObject(aCtx,resultId,Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocHospitalizationResult);
    var reason = getObject(aCtx, reasonId, Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocReasonDischarge);
	if (result!=null && result.code=="11" && reason!=null && reason.code!="DIS_PLAN") {
		throw ("Если результат - смерть, то причина выписки должна быть плановая!");
	}
}
/**
 * Проверить наличие II этапа кардиоскрининга перед выпиской (только при выписке из отделения новорождённых).
 */
function checkNewBornScreeningSecondExists(aForm,aCtx) {
	try { //fix cache
		var sql = "select slo.id" +
			" from medcase sls" +
			" left join mislpu lpu on lpu.id=sls.department_id" +
			" left join medcase slo on slo.parent_id=sls.id" +
			" left join medcase allslo on allslo.parent_id=sls.id" +
			" left join mislpu lpuslo on lpuslo.id=slo.department_id" +
			" left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'" +
			" where sls.id=" + aForm.id + " and sls.dtype='HospitalMedCase' and lpu.IsCreateCardiacScreening='1'  and lpuslo.IsCreateCardiacScreening='1' " +
			" and slo.dtype='DepartmentMedCase'  and allslo.dtype='DepartmentMedCase'" +
			" group by slo.id" +
			" having count(distinct allslo.id)=1 and count(distinct scrII.id)=0";
		var list = aCtx.manager.createNativeQuery(sql).getResultList();
		if (!list.isEmpty()) {
			throw ("<a href='entityParentPrepareCreate-stac_screeningCardiacSecond.do?id=" + list.get(0) + "' target='_blank'>II этап кардиоскрининга</a> в отд. новорождённых должен быть создан до выписки!");
		}
	} catch (e) {
	}
}
//проверка на наличие экспертной карты по критериям. Если нет - выписку запретить
function checkIfIsQECard(aForm,aCtx) {
    if (aForm.id != null) {
        var sql="select qe.id from qualityestimation qe" +
            " left join qualityestimationcard qec on qec.id=qe.card_id" +
            " left join medcase mc on mc.id=qec.medcase_id or mc.parent_id=qec.medcase_id " +
            " left join vocqualityestimationkind vqk on vqk.id=qec.kind_id" +
            " where " +
            " (mc.id=" + aForm.id + " or mc.parent_id=" + aForm.id + " ) and " +
        	" vqk.code='PR203' and qe.experttype='BranchManager'";
        if (aCtx.manager.createNativeQuery(sql).getResultList().isEmpty() ) throw ("Выписка пациента разрешена только после создания заведующим экспертной карты по 203 приказу!")
    }
}

/**
 * Отметить оплаченные назначения выполненными #178
 * @param aEntity сущность
 * @param aCtx контекст
 */
function checkPaidServicesExecuted(aEntity, aCtx) {
	/* Если поток обслуживания - платный,
	в течение days дней до госпитализации по текущий день (настройка приложения)
	получить все CAOS, которые являются частью комплексной услуги (но не самой комплексной услугой)
	и которые не были выполнены в СЛС/СЛО.
	Проставить отметку о выполнении (medcase_id - СЛС)
	 */
	if (aEntity.getServiceStream()!=null && aEntity.getServiceStream().getCode()=='CHARGED') {
		var days = getSettingDaysByKey('checkContractsServicesInSls', 7, aCtx);
        updateListIdCAOS(days,aEntity.id,aCtx);
	}
}

/**
 * Получить настройку кол-ва дней по ключу #178
 * @param aKey ключ
 * @param aCtx контекст
 * @return настройка String
 */
function getSettingDaysByKey(aKey, aDefault, aCtx) {
    var resSet = aCtx.manager.createNativeQuery("select keyvalue from softconfig where key='"+aKey+"'").getResultList();
    return resSet.isEmpty() || +resSet.get(0)==0? aDefault: +resSet.get(0);
}

/**
 * Проставить medcase всем CAOS, которые являются частью комплексной услуги (но не самой комплексной услугой)
 * и которые не были выполнены в СЛС/СЛО #178
 * @param days в течение какого кол-ва дней проверять счета
 * @param aMedcaseId СЛС
 * @param aCtx контекст
 */
function updateListIdCAOS(days,aMedcaseId,aCtx) {
    return aCtx.manager.createNativeQuery("update contractaccountoperationbyservice set medcase_id = " + aMedcaseId +
		" where id=ANY(select caos.id" +
        " from medcase sls" +
        " left join vocservicestream sstream on sstream.id=sls.servicestream_id" +
        " left join patient pat on sls.patient_id=pat.id " +
        " left join contractperson cp on pat.id=cp.patient_id" +
        " left join servedperson sp on cp.id=sp.person_id" +
        " left join contractaccount ca on ca.id=sp.account_id" +
        " left join contractaccountmedservice cams on ca.id=cams.account_id" +
        " left join contractaccountoperationbyservice caos on cams.id=caos.accountmedservice_id" +
        " where sls.datefinish is null" +
        " and sls.id='" + aMedcaseId +
        "' and sstream.code='CHARGED'" +
        " and cams.fromcomplexmedserviceid is not null" +
        " and caos.medcase_id is null and caos.serviceid is null  " +
        " and (ca.createdate between sls.datestart and sls.datestart-" + days + " or ca.createdate=sls.datestart" +
        " or ca.createdate=sls.datestart-"+days+"))").executeUpdate();
}

/**
 * Проверка на наличие открытого акта РВК. Если есть - выписку запретить #183
 * @param aForm форма
 * @param aCtx контекст
 */
function checkIfExistingActRVKClosed(aForm,aCtx) {
    if (aForm.id != null) {
        var sql="select id from actrvk where datefinish is null and medcase_id=ANY(select id from medcase where parent_id=" + aForm.id + ")";
        var list = aCtx.manager.createNativeQuery(sql).getResultList();
        if (!list.isEmpty())
            throw ("<a href='entityEdit-rvk_aktSlo.do?id=" + list.get(0) + "' target='_blank'>Акт РВК</a> необходимо закрыть до выписки!");
    }
}