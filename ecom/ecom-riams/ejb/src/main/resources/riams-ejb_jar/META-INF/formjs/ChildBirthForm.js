function checkBirthDate(aForm, aCtx) {
	var sql = "select case when slo.datestart<to_date('"+aForm.getPangsStartDate()+"','dd.MM.yyyy') then '1'"+
		" when slo.datestart=to_date('"+aForm.getPangsStartDate()+"','dd.MM.yyyy') and slo.entrancetime<=cast('"+aForm.getPangsStartTime()+":00' as time) then '1' else '0' end" +
		" from medcase slo where slo.id="+aForm.getMedCase();
	var res = aCtx.manager.createNativeQuery(sql).getSingleResult();
	if (res!=null&&""+res=="1") {		
	} else {
		throw "Дата родов не может быть раньше даты начала СЛО!";
	}
}
function onView (aForm, aEntity, aCtx) {
	var list = aCtx.manager.createNativeQuery ("select id from newBorn where childBirth_id="+aEntity.id).getResultList();
	aForm.setNewBornAmount(list.size());
}
/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	checkBirthDate(aForm, aCtx);
	var date = new java.util.Date() ;
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aForm.histology!=null && +aForm.histology>0) {
		aForm.placentaHistologyOrder=true ;
	}
}
//FullOpenTime,FullOpenDate,PangsStartTime,PangsStartDate,WatersTime,WatersDate,TravailStartDate,TravailStartTime,
/**
 * Перед сохранением
 */
function onPreCreate(aForm, aCtx) {
	checkBirthDate(aForm, aCtx);
	if (!aCtx.manager.createNativeQuery("select cb.id from medcase slo " +
		" left join medcase allSlo on allSlo.parent_id=slo.parent_id " +
		" left join childbirth cb on cb.medcase_id in (allSlo.id)" +
		" where slo.id=" + aForm.getMedCase() +" and cb.id is not null ").getResultList().isEmpty()) {
		throw "В госпитализации уже заведен случай родов!";
	}
	var date = new java.util.Date() ;
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (aForm.histology!=null && +aForm.histology>0) {
		aForm.placentaHistologyOrder=true ;
	}
	
}
function onCreate(aForm, aEntity, aCtx) {
	var theFld = [['Дата рождения',3,1,'BirthDate']
	,['Время',4,1,'BirthTime']
	,['Родился',1,1,'LiveBorn',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocLiveBorn]
	,['Родился',5,1,'PartBodyBorn',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocPartBodyBorn]
	,['Пол',1,1,'Sex',Packages.ru.ecom.mis.ejb.domain.patient.voc.VocSex]
	,['Масса',2,1,'BirthWeight']
	,['Рост',2,1,'BirthHeight']
	,['Окружность головы',2,1,'HeadCircle']
	,['Окружность плеч',2,0,'ShouldersCircle']
	,['Окружность груди',2,0,'BreastCircle']
	,['Обвитие',1,1,'Entanglement',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocBirthEntanglement] 
	,['Кол-во обвитий',1,0,'EntanglementMultiplicity',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocBirthEntanglementMultiplicity] 
	,['Где обвитие',1,0,'WhereEntanglement',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocBirthWhereEntanglement]
	,['Передан в отделение',1,0,'Department',Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu]
	,['Зрелость',1,1,'Maturity',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocNewBornMaturity]
	,['Умер до начала родовой деятельности',6,0,'DeadBeforeLabors']
	
	] ;
	if (aForm.getNewBornsInfo()!="") {
		var childs = aForm.getNewBornsInfo().split("@") ;
		var currentDate = new java.util.Date();
		var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
		var username = aCtx.getSessionContext().getCallerPrincipal().toString();
		var startDateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.pangsStartDate,aForm.pangsStartTime);
		var finishDateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(aForm.birthFinishDate,aForm.birthFinishTime);
		
		for (var ind = 0 ; ind<childs.length; ind++) {
			
			var child = childs[ind].split("#") ;
			var newBorn = new Packages.ru.ecom.mis.ejb.domain.birth.NewBorn() ; 
			var childBirthDiaryText = "Протокол рождения ребенка "+(childs.length>1?""+(ind+1):"")+"\n";
			var childBD = null, childBT = null;
			for (var j=0;j<theFld.length;j++) {
				if (childBD!=null&&childBT!=null) break;
				if (theFld[j][3]=='BirthDate') {childBD = ""+child[j];}
				else if (theFld[j][3]=='BirthTime') {childBT = ""+child[j];}
			}
			if (childBD!=null&&childBT!=null) {
				var birthDateTime = Packages.ru.nuzmsh.util.format.DateConverter.createDateTime(childBD,childBT);
				if (startDateTime>birthDateTime) {
					throw "Дата рождения ребенка "+(childs.length>1?""+(ind+1):"")+"не может быть раньше даты начала родов!";
				} else if (startDateTime.getTime()>currentDate.getTime()) {
					throw "Дата начала родов не может быть позднее текущей даты!";
				} else if (birthDateTime.getTime()>currentDate.getTime()) {
					throw "Дата рождения ребенка "+(childs.length>1?""+(ind+1):"")+"не может быть позднее текущей даты!";
				} else if (finishDateTime.getTime()>currentDate.getTime()) {
					throw "Дата окончания родов не может быть позднее текущей даты!";
				}
			}


            if (child[2]==2) {//Акушерский диагноз если родился мертвым
                if (child.length==15 ||child[16]=="" || child[17]=="")
                	throw "Если ребёнок родился мертвым, обязательно должен быть заполнен акушерский диагноз (МКБ и наименование)!";
            }
			for (var j=0;j<theFld.length;j++) {
			//	throw theFld.length+"   "+child[3]+"<<"+theFld[3][1];
				//if (j==3)("===== "+j+"<<   "+child[j]+"<<"+theFld[j][1]);
				var birthDate;
				var birthTime;
				var obj ;
				if (child[j]=="" && theFld[j][2]==1) throw "Не все обязательные поля по новорожденному заполнены!!!: "+ theFld[j][0];
				if (theFld[j][1]==1) {
					obj = child[j]==""?null:aCtx.manager.find(theFld[j][4],java.lang.Long(child[j]));
					try {
						if (obj!=null) childBirthDiaryText +="\n"+theFld[j][0]+": "+obj.getName();
					} catch (e) {
					//	throw "=== EXCEPTION "+theFld[j][0]+": "+obj;
					}
				} else if (theFld[j][1]==2) {
					obj = child[j]==""?null:java.lang.Integer.parseInt(child[j]) ;
				//	throw "TYPE2 obj="+obj+", val="+child[j];
					childBirthDiaryText +="\n"+theFld[j][0]+": "+child[j];
				} else if (+theFld[j][1]==5) {  //Long
					obj = child[j]==""?null:java.lang.Long.valueOf(child[j]) ;
					try {
						var val = aCtx.manager.find(theFld[j][4],obj);
						if (val!=null) childBirthDiaryText +="\n"+theFld[j][0]+": "+val.getName();
					} catch (e) {
						//throw "=== EXCEPTION "+theFld[j][0]+": "+val+"<<"+e.toString();
					}
				} else if (theFld[j][1]==3) {
					obj = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(child[j]) ;
					if (theFld[j][3]=='BirthDate') {
						birthDate = obj;
					}
					childBirthDiaryText +="\n"+theFld[j][0]+": "+child[j];
					//throw ""+obj ;
				} else if (theFld[j][1]==4) {
					obj = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(child[j]) ;
					if (theFld[j][3]=='BirthTime') {
						birthTime = obj;
					}
					childBirthDiaryText +="\n"+theFld[j][0]+": "+child[j];
				} else if (theFld[j][1]==6) { //boolean
					if (child[j]!=null &&""+child[j]!="") {
						if (child[j]=="YES") {
							obj=true;
							childBirthDiaryText +="\n"+theFld[j][0]+": ДА";
						} else if (child[j]=="NO") {
							obj=false;
							childBirthDiaryText +="\n"+theFld[j][0]+": НЕТ";
						} else {
							throw "Значение - умер до родов или после = "+child[j];
						}
						
					} else {
						obj=null;
					}	
				} else {
					obj = child[j] ;
				}
				eval("newBorn.set"+theFld[j][3]+"(obj)") ;
				
			}
			
			if (childBirthDiaryText!='') {
				var prot = new Packages.ru.ecom.poly.ejb.domain.protocol.Protocol;
				prot.setDate(new java.sql.Date(currentDate.getTime()));
				prot.setType (aCtx.manager.find(Packages.ru.ecom.poly.ejb.domain.voc.VocTypeProtocol,java.lang.Long(4)));
				prot.setState (aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageState,java.lang.Long(1)));
				prot.setTime(new java.sql.Time (currentDate.getTime()));
				prot.setDateRegistration(birthDate);
				prot.setTimeRegistration(birthTime?birthTime:null);
				prot.setUsername(username);
				prot.setMedCase(aEntity.getMedCase());
				prot.setSpecialist(wf); 
				prot.setRecord(childBirthDiaryText); 
				aCtx.manager.persist(prot);
			}
			
			newBorn.setChildBirth(aEntity) ;
			var vocChild ; 
			var addprefix="" ;
			if (childs.length>1) {
				vocChild = aCtx.manager.createQuery("from VocNewBorn where isPolycarpous='1' and birthOrder='"+(ind+1)+"'").getResultList() ;
				addprefix=ind+1 ;
			} else {
				vocChild = aCtx.manager.createQuery("from VocNewBorn where isPolycarpous='0'").getResultList() ;
			}
			var vch = (vocChild.size()>0)?vocChild.get(0):null ;
			newBorn.setChild(vch) ;
			var motherSls = aEntity.medCase.parent;
			var mother = motherSls.patient ;
			if (newBorn.getLiveBorn().getCode()=='1'){
			var pnb = aCtx.manager.createNativeQuery("select pnb.id from kinsman km left join patient pnb on pnb.id=km.person_id left join vockinsmanrole vkr on km.kinsmanrole_id=vkr.id where km.kinsman_id="+mother.id+" and vkr.omccode='1' and pnb.birthday=to_date('"+child[0]+"','dd.mm.yyyy') and pnb.newborn_id"+(vch!=null?"="+vch.id:" is null")).getResultList() ;
			//throw ""+pnb.size() ;
			var patient ;
			if (!pnb.isEmpty()) {
				var lPat = aCtx.manager.createQuery("from Patient where id='"+pnb.get(0)+"'").getResultList() ;
				patient = lPat.get(0) ;
			} else {
				var sql = "select mp.id from MedCase_MedPolicy as mc left join MedPolicy as mp on mp.id=mc.policies_id left join reg_ic as ri on ri.id=mp.company_id where mc.MedCase_id="+motherSls.id+" and mp.DTYPE='MedPolicyOmcForeign' ";
				patient = new Packages.ru.ecom.mis.ejb.domain.patient.Patient() ;
				var lPol = aCtx.manager.createNativeQuery(sql).getResultList() ;
				var socStatus = aCtx.manager.createQuery("from VocSocialStatus where omcCode='55'").getResultList() ;
				var addStatus = aCtx.manager.createQuery("from VocAdditionStatus where code='0'").getResultList() ;
				patient.socialStatus = socStatus.size()>0?socStatus.get(0):null ;
				patient.additionStatus = addStatus.size()>0?addStatus.get(0):null ;
				if (!lPol.isEmpty()) {
					patient.passportType=mother.passportType ;
					patient.passportSeries=mother.passportSeries ;
					patient.passportDateIssued=mother.passportDateIssued ;
					patient.passportWhomIssued=mother.passportWhomIssued ;
					patient.passportCodeDivision=mother.passportCodeDivision ;
				} else {
					var idenCard = aCtx.manager.createQuery("from VocIdentityCard where omcCode='3'").getResultList() ;
					patient.passportType = idenCard.size()>0?idenCard.get(0):null ;
				}
				patient.lastname=mother.lastname ;
				patient.firstname = "Х";
				patient.middlename =  (newBorn.sex!=null ? (newBorn.sex.omcCode=="1" ? "У" : "Х") : "Х") ;
				patient.birthday = newBorn.birthDate ;
				patient.sex = newBorn.sex ;
				patient.newborn=newBorn.child ;
				patient.newbornWeight =newBorn.birthWeight.longValue() ;
				patient.nationality=mother.nationality ;
				patient.address = mother.address ;
				patient.flatNumber = mother.flatNumber ;
				patient.houseBuilding = mother.houseBuilding ;
				patient.houseNumber = mother.houseNumber ;
				patient.realRayon = mother.realRayon ;
				patient.rayon = mother.rayon ;
				patient.realAddress = mother.realAddress ;
				patient.realFlatNumber = mother.realFlatNumber ;
				patient.realHouseBuilding = mother.realHouseBuilding ;
				patient.realHouseNumber = mother.realHouseNumber ;
				aCtx.manager.persist(patient) ;
				patient.setPatientSync("Н"+patient.getId()) ;
				aCtx.manager.persist(patient) ;
				var kinsman = new Packages.ru.ecom.mis.ejb.domain.patient.Kinsman() ;
				kinsman.person =  patient ;
				kinsman.kinsman = mother ; 
				var role = aCtx.manager.createQuery("from VocKinsmanRole where omccode = '1'").getResultList() ;
			     
				kinsman.kinsmanRole = role.size()>0?role.get(0):null ;
				aCtx.manager.persist(kinsman);
				var sls = new Packages.ru.ecom.mis.ejb.domain.medcase.HospitalMedCase() ;
				sls.patient = patient ;
				var serviceStream = aCtx.manager.createQuery("from VocServiceStream where omcCode='4'").getResultList() ;
				sls.dateStart = newBorn.birthDate ;
				sls.entranceTime = newBorn.birthTime ;
				sls.serviceStream = serviceStream!=null?serviceStream.get(0):motherSls.serviceStream ;
				sls.emergency = java.lang.Boolean.TRUE ;
				var lDep = aCtx.manager.createQuery("from MisLpu where id='"+child[13]+"'").getResultList() ;
				var dep = lDep.get(0) ;
				sls.department = dep ;
				sls.lpu = dep.parent ;
				var lOR = aCtx.manager.createQuery("from VocHospType where code='ALLTIMEHOSP'").getResultList() ;
				sls.hospType = lOR.size()>0?lOR.get(0):null ;
				
				lOR = aCtx.manager.createQuery("from OmcFrm where voc_code='С'").getResultList() ;
				sls.orderType = lOR.size()>0?lOR.get(0):null ;
				
				lOR = aCtx.manager.createQuery("from VocPreAdmissionTime where code='1'").getResultList() ;
				sls.preAdmissionTime = lOR.size()>0?lOR.get(0):null ;
				
				lOR = aCtx.manager.createQuery("from VocHospitalization where code='1'").getResultList() ;
				sls.hospitalization = lOR.size()>0?lOR.get(0):null ;
				sls.kinsman = kinsman ;
				aCtx.manager.persist(sls) ;
				var ss = new Packages.ru.ecom.mis.ejb.domain.medcase.StatisticStubExist() ;
				ss.medCase = sls ;
				ss.year = java.lang.Long.valueOf(child[0].substring(6)) ;
				var ssMother = motherSls.statisticStub.code ;
				ss.code = ssMother.substring(1)+"Н"+addprefix ;
				aCtx.manager.persist(ss) ;
				sls.statisticStub = ss ;
				aCtx.manager.persist(sls) ;
				//диабет у матери в родах будет проставляться только при создании. Если нужно редактировать - путь меняют в СЛС
                if (aForm.getDiabetIdentity()!=null && aForm.getDiabetIdentity()!=''){
                	var vocColorIdentity = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient,java.lang.Long.valueOf(aForm.getDiabetIdentity()));
                	if (vocColorIdentity!=null) {
                        var colorIdentity = new Packages.ru.ecom.mis.ejb.domain.patient.ColorIdentityPatient();
                        colorIdentity.startDate=sls.dateStart;
                        colorIdentity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
                        colorIdentity.setVocColorIdentity(vocColorIdentity);
                        aCtx.manager.persist(colorIdentity) ;
                        var colIds = new java.util.ArrayList() ;
                        colIds.add(colorIdentity);
                        sls.setColorsIdentity(colIds);
					}
                }
			}
			newBorn.setPatient(patient) ;			
			}
			else if (newBorn.getLiveBorn().getCode()=='2') {  //Milamesher #74: если ребёнок - мёртвый
				var dcase = new Packages.ru.ecom.mis.ejb.domain.medcase.hospital.DeathCase;
				dcase.setDeathDate(newBorn.birthDate);
                dcase.setDeathTime(newBorn.birthTime);
                dcase.setAccidentDate(new java.sql.Date(newBorn.birthDate.getTime()));
                dcase.setAccidentCircumstance("Мертворождение");
                dcase.setIsNeonatologic(true);
                dcase.setMedCase(aEntity.getMedCase().getParent());
                dcase.setReasonMainMkb(aCtx.manager.find(Packages.ru.ecom.expomc.ejb.domain.med.VocIdc10,java.lang.Long.valueOf(child[16])));
                dcase.setCommentReason(child[17]);
                //var medCase = aCtx.manager.find(HospitalMedCase.class, aEntity.getMedCase().getParent());
                var motherSls = aEntity.medCase.parent;
                var ssMother = motherSls.statisticStub.code ;
                var stub=ssMother.substring(1)+" -Н";
                var history = "История развития новорождённого № " + stub +"\nДата родов " + newBorn.birthDate + " ребёнок ";
                var sex=(newBorn.sex.omcCode=="1")?"мужского":"женского";
                history=history+sex+" пола, вес " + newBorn.birthWeight + " гр, рост " + newBorn.birthHeight + " см, окружность головки " + newBorn.headCircle + " см, мертворождённый";
                dcase.setNewBornHistory(history);
                aCtx.manager.persist(dcase) ;
			}
			aCtx.manager.persist(newBorn) ;
		}
	}
    createOrUpdateRobson(aForm, aCtx);
}
function onPreDelete(aId, aCtx) {
	var obj=aCtx.manager.createNativeQuery("select count(*) from newBorn where childBirth_id='"+aId+"'").getSingleResult() ;
	if (+obj>0) throw "Сначала нужно удалить данные по новорожденным" ;
}
//классификация Робсона
function createOrUpdateRobson(aForm, aCtx) {
    var robsonClass=aForm.getRobsonClass();
    if (robsonClass!=null && robsonClass!='') {
        var rEnt = new Packages.ru.ecom.mis.ejb.domain.birth.RobsonClass;
        rEnt.setMedCase(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.MedCase,java.lang.Long.valueOf(aForm.getMedCase())));
        rEnt.setRobsonType(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.voc.VocRobsonClass,java.lang.Long.valueOf(robsonClass)));
        var date = new java.util.Date() ;
        rEnt.setCreateDate(new java.sql.Date(date.getTime())) ;
        rEnt.setCreateTime(new java.sql.Time (date.getTime())) ;
        rEnt.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
        var robsonSub=aForm.getRobsonSub();
        if (robsonSub!=null && robsonSub!='')
            rEnt.setRobsonSub(aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.birth.voc.VocSubRobson,java.lang.Long.valueOf(robsonSub)));
        aCtx.manager.persist(rEnt) ;
    }
}
function onSave(aForm, aEntity, aCtx) {
    aCtx.manager.createNativeQuery("delete from robsonclass where medcase_id="+aForm.getMedCase()).executeUpdate() ;
    createOrUpdateRobson(aForm, aCtx);
}