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
	/*if (+aForm.childBirth>0 && aEntity.statisticStub!=null) {
		var childBirth = getObject(aCtx, aForm.resultDischarge, Packages.ru.ecom.mis.ejb.domain.birth.voc.VocChildBirth);
		aEntity.statisticStub.setChildBirth(childBirth) ;
		aCtx.manager.persist(aEntity) ;
	}*/
	closePrescriptions(aForm, aCtx);
}
function onPreSave(aForm,aEntity, aCtx) {
    //checkUniqueDiagnosis(aForm,aCtx);
    checkDeathThenPlan(aCtx, +aForm.result, +aForm.reasonDischarge);
    checkNewBornScreeningSecondExists(aForm,aCtx);
    //проверка на наличие ЭК по критериям
    //checkIfIsQECard(aForm, aCtx);
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
	var dateStart = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateStart,aForm.entranceTime);
	var dateFinish = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.dateFinish,aForm.dischargeTime);
	var dateCur = new java.sql.Date(new java.util.Date().getTime()) ;

	if (aForm.concludingDiagnos!=null &&aForm.concludingDiagnos!="" && (aForm.concludingMkb==null || aForm.concludingMkb==0)) throw "Не указан код МКБ заключительного диагноза!" ;
	if ((aForm.concludingMkb!=null && aForm.concludingMkb>0)&&(aForm.concludingDiagnos==null||aForm.concludingDiagnos=="")) throw "Не сформулирован заключительный диагноз!" ;
	//var d=aCtx.manager.find(Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10,aForm.concludingMkb) ;
	//if (d!=null) {d=d.code ;} else {d=""} ;
	//if ((d>='O60.0' && d<='O60.9' || d>='O80.0' && d<='O80.9' ||
	 //		d>='O82.0' && d<='O82.9' || d>='O84.0' && d<='O84.9') && +aForm.childBirth<1) throw "Необходимо указать тип родов" ;
	// var dateFsql = new java.sql.Date(dateFinish.getTime()) ;
	if (!(dateFinish.getTime() > dateStart.getTime())) throw "Дата выписки должна быть больше, чем дата поступления";
	//if ((((dateFsql.getTime()-dateCur.getTime())/1000/60/60)%24)>6) throw "Максимальная дата выписки - сегодняшняя" ;
	
	var ldate = aCtx.manager.createNativeQuery("select transferDate,transferTime from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase' and transferDate is not null order by transferDate desc,transferTime desc")
		.setParameter("parent",aForm.id)
		.setMaxResults(1)
		.getResultList() ;
	//var stat = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.StatisticStubStac.isDischargeOnlyCurrentDay(context) ;
	//throw "date="+dmax[0] ;
	if (!ldate.isEmpty()) {
		var dmax=ldate.get(0) ;
		//var vmax = aCtx.manager.createNativeQuery("select max() from MedCase where parent_id=:parent and DTYPE='DepartmentMedCase' and transferDate=:dmax")
	     //  	.setParameter("parent",aForm.id)
	      // 	.setParameter("dmax",dmax)
	       	
		//	.getSingleResult() ;
		//throw "time="+dmax[0]+" "+dmax[1];
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
				param.put("permission" ,"backdate") ;
				param.put("id", aForm.id) ;
				var check=aCtx.serviceInvoke("WorkerService", "checkPermission", param)+"";
				
				if (+check==0) {
					throw "У Вас стоит ограничение на дату выписки. Вы можете выписывать в течение "+cntHour+" часов.";
					
				}
			}
	}
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
		 +" having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0  "
		var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
		if (!list.isEmpty()) {
			var slo ="" ;
			for (var i=0; i<list.size(); i++) {
				slo = slo+" <a href='entitySubclassView-mis_medCase.do?id="+list.get(0)[0]+"' onclick='return  msh.util.FormData.getInstance().isChangedForLink() ;'>" +list.get(0)[1]+"</a>" ;
			}
			throw "Не полностью заполнены данные по диагнозам в отделениях!!! "+ slo ;
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
    var sql = "select slo.id\n" +
        "from medcase sls\n" +
        "left join mislpu lpu on lpu.id=sls.department_id\n" +
        "left join medcase slo on slo.parent_id=sls.id\n" +
        "left join medcase allslo on allslo.parent_id=sls.id\n" +
        "left join mislpu lpuslo on lpuslo.id=slo.department_id\n" +
        "left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'\n" +
        "where lpu.IsCreateCardiacScreening=true  and lpuslo.IsCreateCardiacScreening=true and\n" +
        "sls.dtype='HospitalMedCase'  and slo.dtype='DepartmentMedCase'  and allslo.dtype='DepartmentMedCase'\n" +
        "and sls.id='" + aForm.id + "'\n"+
        "group by slo.id\n" +
        "having count(distinct allslo.id)=1 and count(distinct scrII.id)=0";
    var list = aCtx.manager.createNativeQuery(sql).getResultList();
    if (!list.isEmpty())
		throw ("<a href='entityParentPrepareCreate-stac_screeningCardiacSecond.do?id=" + list.get(0) + "' target='_blank'>II этап кардиоскрининга</a> в отд. новорождённых должен быть создан до выписки!");
}
//првоерка на наличие экспертной карты по критериям. Если нет - выписку запретить
function checkIfIsQECard(aForm,aCtx) {
    if (aForm.id != null) {
        /*var sql = "select id from qualityestimationcard where medcase_id=" + aForm.id + " and  kind_id=(select id from vocqualityestimationkind where code='PR203')";
        var size1=aCtx.manager.createNativeQuery(sql).getResultList().size();
        var sql2 = "select id from qualityestimationcard where medcase_id=(select id from medcase where parent_id=" + aForm.id+") and  kind_id=(select id from vocqualityestimationkind where code='PR203')";
        var size2=aCtx.manager.createNativeQuery(sql2).getResultList().size();
        throw ""+size1+" ; " +size2;
        //if (size1==0 && size2==0) throw ("Выписка пациента разрешена только после создания экспертной карты!")*/

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