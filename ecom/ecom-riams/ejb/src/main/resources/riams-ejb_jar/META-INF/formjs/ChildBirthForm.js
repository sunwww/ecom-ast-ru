/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
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
	,['Плеч',2,0,'ShouldersCircle']
	,['Груди',2,0,'BreastCircle']
	,['Обвитие',1,1,'Entanglement',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocBirthEntanglement] 
	,['Кол-во обвитий',1,0,'EntanglementMultiplicity',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocBirthEntanglementMultiplicity] 
	,['Где',1,0,'WhereEntanglement',Packages.ru.ecom.mis.ejb.domain.birth.voc.VocBirthWhereEntanglement]
	,['Отделение',1,0,'Department',Packages.ru.ecom.mis.ejb.domain.lpu.MisLpu]
	] ;
	if (aForm.getNewBornsInfo()!="") {
		var childs = aForm.getNewBornsInfo().split("#@#") ;
		for (var ind = 0 ; ind<childs.length; ind++) {
			
			var child = childs[ind].split("@#@") ;
			var newBorn = new Packages.ru.ecom.mis.ejb.domain.birth.NewBorn() ; 
			
			for (var j=0;j<theFld.length;j++) {
				var obj ;
				if (child[j]=="" && theFld[j][2]==1) throw "Не все обязательные поля по новорожденному заполнены!!!: "+ theFld[j][0];
				if (theFld[j][1]==1) {
					obj = child[j]==""?null:aCtx.manager.find(theFld[j][4],java.lang.Long(child[j]));
				} else if (theFld[j][1]==2) {
					obj = child[j]==""?null:java.lang.Integer.parseInt(child[j]) ;
				} else if (theFld[j][1]==5) {
					obj = child[j]==""?null:java.lang.Long.valueOf(child[j]) ;
				} else if (theFld[j][1]==3) {
					obj = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(child[j]) ;
					//throw ""+obj ;
				} else if (theFld[j][1]==4) {
					obj = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlTime(child[j]) ;
				} else {
					obj = child[j] ;
				}
				eval("newBorn.set"+theFld[j][3]+"(obj)") ;
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
			var pnb = aCtx.manager.createNativeQuery("select pnb.id from kinsman km left join patient pnb on pnb.id=km.person_id left join vockinsmanrole vkr on km.kinsmanrole_id=vkr.id where km.kinsman_id="+mother.id+" and vkr.omccode='1' and pnb.birthday=to_date('"+child[0]+"','dd.mm.yyyy') and pnb.newborn_id"+(vch!=null?"="+vch.id:" is null")).getResultList() ;
			//throw ""+pnb.size() ;
			var patient ;
			if (pnb.size()>0) {
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
				if (lPol.size()>0) {
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
				patient.firstname = (newBorn.sex!=null?(newBorn.sex.code=="1"?"У":"Х"):"Х") ;
				patient.middlename =  "Х";
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
			}
			newBorn.setPatient(patient) ;
			aCtx.manager.persist(newBorn) ;
		}
	}
	
}
