var map = new java.util.HashMap() ;
function printArea(aCtx, aParams) {
	//var check = (aParams.get("id")+"").split(":") ;
	var typeDiag = +aParams.get("typeDiag");// +check[0] ;
	var typeAddress = +aParams.get("typeAddress");// +check[0] ;
	var typeDate = +aParams.get("typeDate");// +check[0] ;
	var typeInv = +aParams.get("typeInv");//+check[1] ;
	var typeSuicide =+aParams.get("typeSuicide");// +check[2] ;
	var typeDateSuicide =+aParams.get("typeDateSuicide");// +check[2] ;
	var typeAge = +aParams.get("typeAge");//+check[3] ;
	var ambulatoryCare = +aParams.get("ambulatoryCare");
	var typeCare = +aParams.get("typeCare");
	var reasonBegin = +aParams.get("reasonBegin") ;
	var reasonTransfer = +aParams.get("reasonTransfer") ;
	var reasonEnd = +aParams.get("reasonEnd") ;
	var sex = +aParams.get("sex");
	var lpuArea = +aParams.get("lpuArea");
	var ageFrom = +aParams.get("ageFrom");
	var ageTo= +aParams.get("ageTo");
	var natureSuicide = +aParams.get("natureSuicide");
	var dateEnd = aParams.get("dateEnd");
	var dateBegin = aParams.get("dateBegin");
	var compTreatment = +aParams.get("compTreatment");
	var groupInv = +aParams.get("groupInv");
	var group = +aParams.get("group");
	
	var ambulatoryCare = +aParams.get("ambulatoryCare");
	if (ambulatoryCare!=null && ambulatoryCare>0
			&& lpuArea!=null && lpuArea>0) {
	} else {
		throw "Указаны не все параметры" ;
	}
	var addressAdd="" ;
	if (typeAddress==2) {
		addressAdd="real";
	}

	var lpuAreaO =aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.lpu.LpuArea
			, new java.lang.Long(lpuArea)) ;
	var lpuAreaInfo = lpuAreaO.name ;
	var sexSql=""; sexInfo="" ;
	if (sex!=null && +sex>0) {
		sexSql=" and p.sex_id='"+sex+"'" ;
		var vs=aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.voc.VocSex
				, new java.lang.Long(sex)) ;
		sexInfo=" по полу:"+(vs!=null?vs.name:"?") ;
	}
	var ageToSql="",ageFromSql="",ageInfo="" ;
	if (typeAge==1) {
		var format = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
		if (ageTo==0&& ageTo<ageFrom) ageTo = null ; 
		if (ageFrom!=null) {
			var cal = java.util.Calendar.getInstance() ;
			cal.add(java.util.Calendar.YEAR, ageFrom*(-1)) ;
			ageFromSql= " and p.birthday < to_date('"+format.format(cal.getTime())+"','dd.mm.yyyy') " ;
		}
		if (ageTo!=null) {
			var cal = java.util.Calendar.getInstance() ;
			cal.add(java.util.Calendar.YEAR, (ageTo*(-1)-1)) ;
			ageToSql = " and p.birthday >=to_date('"+format.format(cal.getTime())+"','dd.mm.yyyy') " ;
		}
		var ageInfo = "возраст: "+(ageFrom!=null?ageFrom:"?")+"-"+(ageTo!=null?ageTo:"?" ) ;
	}
	

	
	if (dateEnd==null || dateEnd=="") {
		dateEnd = dateBegin ;
	}
	var periodInfo = dateBegin+"-"+dateEnd ;
	dateEnd = "to_date('"+dateEnd+"','dd.mm.yyyy')" ;
	dateBegin = "to_date('"+dateBegin+"','dd.mm.yyyy')" ;

	// АПЛ+АДН (список)
	var careDate = null ;
	var careInfo = null ;
	if (group==0
				&&compTreatment==0) {} 
	else if (typeCare==2) {
		careDate = " and  fldDateStart <="
				+dateEnd					
				+" and (fldDateFinish is null or fldDateFinish > "
				+dateEnd
				+" )" ;
		careInfo=" (состоящие)"; 
	} else if (typeCare==3) {
		careDate = " and  fldDateStart between "
				+dateBegin+" and "
				+dateEnd+" " ;
		careInfo=" (взятые)" ;
	} else if (typeCare==4) {
		careDate = " and  fldDateFinish between "
				+dateBegin+" and "
				+dateEnd+" " ;
		careInfo=" (снятые)" ;
	}
	var suicideSql="",suicideInfo="",suicideAddField=addField("suicide",0) ; ;
	if (typeSuicide==2) {
		var sui = " and "+((+typeDateSuicide==1)?"sui.suicideDate":"sui.regDate")+" between "+dateBegin+" and "+dateEnd+" " ;
		if (natureSuicide>0) {
			sui=sui+" and sui.type_id='"+natureSuicide+"'" ;
			var vs = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocSuicideMesType, new java.lang.Long(sex)) ;
			suicideInfo= " вид суицида: "+(vs!=null?vs.name:"?") ;
		} else {
			suicideInfo= " суицид " ;
		}
		suicideSql= sui;
		suicideAddField=addField("suicide",1) ;
	} else if (typeSuicide==3) {
		var sui = " and "+((+typeDateSuicide==1)?"sui.suicideDate":"sui.regDate")+" between "+dateBegin+" and "+dateEnd+" and sui.isFinished='1' " ;
		if (natureSuicide>0) {
			sui=sui+" and sui.type_id='"+natureSuicide+"'" ;
			var vs = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocSuicideMesType, new java.lang.Long(natureSuicide)) ;
			suicideInfo= " вид суицида завершенного: "+(vs!=null?vs.name:"?") ;
		} else {
			suicideInfo= " суицид завершенный " ;
		}
		suicideSql= sui;
		suicideAddField=addField("suicide",1) ;
	}
	var dateSql="",dateInfo="" ;
	if (typeDate==1) {
		dateSql=dateSql+" and  area.startDate<="+dateEnd					
				+" and (area.finishDate is null or area.finishDate > "
				+dateEnd+" ) and (area.transferDate is null or area.transferDate > "
				+dateEnd+" )" ;
		dateInfo= " (состоящие)" ;
	} else if (typeDate==2) {
		dateSql=dateSql+" and (area.finishDate between "+dateBegin+" and "+dateEnd
			+" or area.transferDate between "+dateBegin+" and "+dateEnd+") ";
		dateInfo= "(снятые и переведенные)" ;
	} else if (typeDate==3) {
		dateSql=dateSql+" and area.startDate between "+dateBegin+" and "+dateEnd+"" ;
		if (reasonBegin>0) {
			dateSql=dateSql+" and area.observationReason_id='"+reasonBegin+"'" ;
			var vpor = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychObservationReason, new java.lang.Long(reasonBegin)) ;
			dateInfo= "(причина взятия: "+vpor.name+")" ;
		} else {
			dateInfo= "(взятые)" ;
		}
	} else if (typeDate==4) {
		dateSql=dateSql+" and area.startDate between "+dateBegin+" and "+dateEnd+" and vpor.isPrimary='1' " ;
		dateInfo= "(взятые по первичным причинам)" ;
	} else if (typeDate==5) {
		dateSql=" and (area.finishDate between "+dateBegin+" and "+dateEnd
				+" or area.transferDate between "+dateBegin+" and "+dateEnd
				+") and area.stikeOffReason_id is null ";

		if (reasonTransfer>0) {
			dateSql=dateSql+" and area.transferReason_id='"
				+reasonTransfer+"'" ;
			var vpor = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychTransferReason, new java.lang.Long(reasonTransfer)) ;
			dateInfo= "(причина перевода: "+vpor.name+")" ;
		} else {
			dateInfo="(переведенные)" ;
		}
	} else if (typeDate==6) {
		
		dateSql=dateSql+" and (area.finishDate between "
				+dateBegin+" and "+dateEnd
				+" or area.transferDate between "
				+dateBegin+" and "+dateEnd+") and area.transferReason_id is null ";

		if (reasonEnd>0) {
			dateSql=dateSql+" and area.stikeOffReason_id='"
				+reasonEnd+"'" ;
			var vpor = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychStrikeOffReason, new java.lang.Long(reasonEnd)) ;
			dateInfo= "(причина снятия: "+vpor.name+")" ;
		} else {
			dateInfo="(снятые)" ;
		}
	} else if (typeDate==7) {
		dateSql=dateSql+" and (" ;
		dateSql=dateSql+" ( (area.finishDate between ";
		dateSql=dateSql+dateBegin+" and "+dateEnd;
		dateSql=dateSql+" or area.transferDate between ";
		dateSql=dateSql+dateBegin+" and "+dateEnd+") and area.transferReason_id is null )";
		dateSql=dateSql+" or ";
		dateSql=dateSql+" ( (area.finishDate between ";
		dateSql=dateSql+dateBegin+" and "+dateEnd;
		dateSql=dateSql+" or area.transferDate between ";
		dateSql=dateSql+dateBegin+" and "+dateEnd+") and area.stikeOffReason_id is null )";
		dateSql=dateSql+" or ";
		dateSql=dateSql+" ( area.startDate between ";
		dateSql=dateSql+dateBegin+" and ";
		dateSql=dateSql+dateEnd+")" ;
		dateSql=dateSql+" or ";
		dateSql=dateSql+" (  area.startDate<=";
		dateSql=dateSql+dateEnd;
		dateSql=dateSql+" and (area.finishDate is null or area.finishDate >= ";
		dateSql=dateSql+dateEnd;
		dateSql=dateSql+" ) and (area.transferDate is null or area.transferDate >= ";
		dateSql=dateSql+dateEnd;
		dateSql=dateSql+" ))";
		dateSql=dateSql+")" ;
		dateInfo="(взятые, состоящие, снятые, перевед.)" ;
	}
	
	// ИНВАЛИДНОСТЬ
	var invSql = "", invInfo="", invAddField=addField("invalidity",0);
	if (typeInv==1) {
		invInfo=" " ;
	} else if (typeInv==2) {
		typeSql=" and p.incapable='1' " ;
		invInfo=" недееспособные " ;
		invAddField=addField("invalidity",0);
	} else {
		invAddField=addField("invalidity",1);
		var str1="",str="" ;
		str=str+" and inv.dateFrom<="+dateEnd+" and (inv.isOtherInvalidity is null or inv.isOtherInvalidity='0')" ;
		if (groupInv>0) {
			str=str+" and inv.group_id="+groupInv ;
			var invGr = 
				aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.disability.voc.VocInvalidity, new java.lang.Long(groupInv)) ;
			str1 = " группа " +invGr.name ;
		}
		if (typeInv==4) {				
			str=str+" and inv.initial='1' and inv.dateFrom between "
				+dateBegin+" and "+dateEnd ;
			str1=str1+" первичные" ;
		} 
		if (typeInv==5) {
			str=str+" and inv.incapable='1' " ;
			str1 = str1+"недееспособные" ;
		}
		if (typeInv==6) {
			str=str+" and inv.childhoodInvalid='1' " ;
			str1 = str1+"с детства" ;
		}
		
		if (typeInv==4) {
			invSql = " "+str+" " ;
		} else {
			invSql = " and (inv.withoutExam='1' or inv.nextRevisionDate>"+dateEnd+" or inv.dateTo>"+dateEnd+") "+str+" " ;
		}
		invInfo=" инвалиды "+str1 ;
	}
	// ПРИНУДИТЕЛЬНОЕ ЛЕЧЕНИЕ (ПРИНУДКА)
	var compTreatInfo= "",compTreatSql="",comTreatAddField=addField("compulsory",0) ;
	if (compTreatment>0&& careDate!=null) {
		//compTreatSql=" and (ct.decisionDate <=coalesce(area.finishDate,"+dateEnd+")  and (ct.dateReplace is null or ct.dateReplace>="+dateBegin+" and ct.dateReplace >= coalesce(area.finishDate,"+dateEnd+"))) and ct.kind_id='"+compTreatment+"'" ;
		compTreatSql=careDate.replace(new RegExp("fldDateStart",'g'), "ct.decisionDate").replace(new RegExp("fldDateFinish",'g'), "ct.dateReplace")+" and ct.kind_id='"+compTreatment+"'" ;
		
		if (typeCare==3) {
			// взятые
			compTreatSql=compTreatSql+" and (select to_char(min(ct.decisionDate),'dd.mm.yyyy') from CompulsoryTreatment ct1 where pcc.id=ct1.careCard_id and ct1.orderNumber=ct.orderNumber)>="+dateBegin ;
		} else if (typeCare==4) {
			//снятые
			compTreatSql=compTreatSql+" and  (ct.courtDecisionReplace_id=2 or ct.courtDecisionReplace_id=4) ";
		}
		var compTreatmentO = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychCompulsoryTreatment, new java.lang.Long(compTreatment)) ;
		//compTreatInfo= "прин. лечение: "+compTreatmentO.name +careInfo;
		compTreatInfo= " "+compTreatmentO.name +careInfo;
		comTreatAddField=addField("compulsory",1) ;
	}
	
	var groupSql = "" , groupInfo = "" , groupDopJoin = "" , groupDopSql = "" ,groupAddField=addField("group",0);
	if (ambulatoryCare>0){
		//aRequest.setAttribute("lpo", "left join PsychiaticObservation po on po.careCard_id=pcc.id") ;
		groupSql=groupSql+" and po.startDate between (cast(area.startDate as integer)-1) and ifnull(area.finishDate,cast(CURRENT_DATE as integer),(cast(area.finishDate as integer)-1))  " ;
		groupSql=groupSql+" and po.ambulatoryCare_id = "+ambulatoryCare ;
		var ambCareO = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychAmbulatoryCare, new java.lang.Long(ambulatoryCare)) ;
		groupInfo = ""+ambCareO.name ;
		if (group>0 && careDate!=null) {
			var grO = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychDispensaryGroup, new java.lang.Long(group)) ;
			groupInfo = groupInfo +" группа: "+grO.name +careInfo;
			groupDopJoin =" left join PsychiaticObservation po1 on po1.careCard_id=pcc.id"; 
			groupDopJoin = groupDopJoin + " left join VocCriminalCodeArticle vccaAdn on vccaAdn.id=po1.criminalCodeArticle_id";
			groupDopSql=" and po1.lpuAreaPsychCareCard_id is null" ;
			groupSql=groupSql+" and po1.dispensaryGroup_id = "+group;
			groupSql=groupSql+careDate.replace(new RegExp("fldDateStart",'g'), "po1.startDate").replace(new RegExp("fldDateFinish",'g'), "po1.finishDate") ;
			groupAddField=addField("group",1) ;
			} else if(typeDate==3) {
			}
	}
	var sql = "" ;
	sql = sql + " select distinct pcc.id as pccid" ;
	sql = sql + " , (to_char(CURRENT_DATE,'yyyy')-to_char(p.birthday,'yyyy') ";
	sql = sql + " +case when (to_char(CURRENT_DATE, 'mm')-to_char(p.birthday, 'mm')) <0 then -1 when (to_char(CURRENT_DATE,'dd') - to_char(p.birthday,'dd')<0) and ((to_char(CURRENT_DATE, 'mm')-to_char(p.birthday, 'mm')-1)<0)  then -1 else 0 end" ;
	sql = sql + " ) ||' '||coalesce(vs1.name,'-') as age";
	sql = sql + " ,coalesce(p.lastname,'-')||' '||coalesce(p.firstname,'-')|| ' '||coalesce(p.middlename,'-') as lfm";
	sql = sql + " ,to_char(p.birthday,'dd.mm.yyyy') as pbirthday " ;
	sql = sql + " ,coalesce(to_char(area.startDate,'dd.mm.yyyy'),'-') as areastartdate";
	sql = sql + " ,vpor.name as vporname" ;
	sql = sql + " ,coalesce(to_char(area.finishDate,'dd.mm.yyyy'),to_char(area.transferDate,'dd.mm.yyyy'),'') as areafinishdate";
	sql = sql + " ,coalesce(vptr.name,vpsor.name,'') as vptrname" ;
	sql = sql + " ,coalesce(to_char(pcc.deathDate,'dd.mm.yyyy'),'')||vpdr.name as deathinfo" ;
	sql = sql + ", case when p."+addressAdd+"address_addressId is not null "
	+"          then coalesce(a.fullname,a.name) || "
	+"               case when p."+addressAdd+"houseNumber is not null and p."+addressAdd+"houseNumber!='' then ' д.'||p."+addressAdd+"houseNumber else '' end" 
	+		" ||case when p."+addressAdd+"houseBuilding is not null and p."+addressAdd+"houseBuilding!='' then ' корп.'|| p."+addressAdd+"houseBuilding else '' end" 
	+		"||case when p."+addressAdd+"flatNumber is not null and p."+addressAdd+"flatNumber!='' then ' кв. '|| p."+addressAdd+"flatNumber else '' end"
	+"       when p.territoryRegistrationNonresident_id is not null"
	+"          then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident"
	+"               ||' '||ost.name||' '||p.StreetNonresident||"
	+"               case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end" 
	+" ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end" 
	+"||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end"
	+"   else '' "
	+"  end as address" ;
	sql = sql + " ,' '||$$getDiagnosis^ZPsychUtil(p.id,isnull(area.finishDate,CURRENT_DATE),"+typeDiag+") as diag" ;
	sql = sql + invAddField + suicideAddField + comTreatAddField + groupAddField;
	sql = sql + " from PsychiatricCareCard pcc " ;
	sql = sql + " left join VocPsychDeathReason vpdr on vpdr.id=pcc.deathReason_id";
	sql = sql + " left join Patient p on p.id=pcc.patient_id" ; 
	sql = sql + " left join VocSex vs1 on vs1.id=p.sex_id" ; 
	sql = sql + " left join LpuAreaPsychCareCard area on pcc.id=area.careCard_id" ; 
	sql = sql + " left join CompulsoryTreatment ct on pcc.id=ct.careCard_id" ;
	sql = sql + " left join VocLawCourt vlc on vlc.id=ct.lawCourt_id" ;
	sql = sql + " left join VocCriminalCodeArticle vcca on vcca.id=ct.crimainalCodeArticle_id";
	sql = sql + " left join PsychiaticObservation po on po.lpuAreaPsychCareCard_id=area.id" ;
	sql = sql + groupDopJoin ;
	sql = sql + " left join SuicideMessage sui on sui.patient_id=p.id" ;
	sql = sql + " left join VocSuicideMesType vpsn on vpsn.id=sui.type_id";
	sql = sql + " left join VocPsychObservationReason vpor on vpor.id=area.observationReason_id" ;
	sql = sql + " left join VocPsychTransferReason vptr on vptr.id=area.transferReason_id" ;
	sql = sql + " left join VocPsychStrikeOffReason vpsor on vpsor.id=area.stikeOffReason_id" ;
	sql = sql + " left join Invalidity inv on inv.patient_id=p.id" ;
	sql = sql + " left join VocInvalidity vi on vi.id=inv.group_id" ;
	sql = sql + " left join VocInvalidityVitalRestriction vivr on vivr.id=inv.vitalRestriction_id";
	sql = sql + " left join VocInvalidityHealthViolation vihr on vihr.id=inv.healthViolation_id";
	sql = sql + " left join VocLawCourt invvlc on invvlc.id=inv.lawCourt_id" ;
	sql = sql + " left join Address2 a on a.addressId=p."+addressAdd+"address_addressId" ;
	sql = sql + " left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id";
	sql = sql + " left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id";
	sql = sql + " left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id" ;

	sql = sql + " where  area.lpuArea_id="+lpuArea ;
	sql = sql + " "+dateSql+" "+groupDopSql+" "+invSql+" "+compTreatSql ;
	sql = sql + " "+suicideSql+" " ;
	sql = sql + " "+groupSql+" "+sexSql+" "+ageFromSql+" "+ageToSql ;
	sql = sql + " group by area.id " ;
	sql = sql + " order by p.lastname,p.firstname,p.middlename" ;
	var list = aCtx.manager.createNativeQuery(sql).getResultList() ;
	
	var ret = new java.util.ArrayList() ;
	
	for (var i=0;i<list.size();i++) {
		var wq = Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		var obj = list.get(i) ;
		wq.setSn(i+1) ;
		for (var j=1;j<obj.length;j++) {
			eval("wq.set"+j+"(obj["+(j)+"])") ;
		}
		ret.add(wq) ;
	}
	var map = new java.util.HashMap() ;
	map.put("list",ret) ;
	map.put("name",lpuAreaInfo+ " "+dateInfo+" "+invInfo+" "+compTreatInfo
			+" "+suicideInfo+" "
			+" "+groupInfo+" "+sexInfo+" "+ageInfo+" период: "+periodInfo) ;
	return map ;
}
function addField(aType,aInt) {
	if (aType=="suicide") {
		if (aInt==0) {
			return ",'s1' as s1,'s2' as s2,'s3' as s3" ;
			//return ",' ' as s1,' ' as s2,' ' as s3" ;
		} else {
			return ", to_char(sui.suicideDate,'dd.mm.yyyy') as suifulfilmentdate, case when sui.isFinished='1' then 'Да' else 'Нет' end as suiisfinished, vpsn.name as vpsn " ;
		}
	} else if (aType=="group") {
		if (aInt==0) {
			return ",'g1' as g1,'g2' as g2,'g3' as g3, 'g4' as g4" ;
			//return ",' ' as g1,' ' as g2,' ' as g3" ;
		} else {
			return " ,to_char(po1.startDate,'dd.mm.yyyy') as pop1startDate,to_char(po1.finishDate,'dd.mm.yyyy') as po1finishDate, coalesce(po1.finishDate,current_date)-po1.startDate as po1cntDays,vccaAdn.code as vccaAdnname";
		}
	} else if (aType=="compulsory") {
		if (aInt==0) {
			return ",'c1' as c1,'c2' as c2,'c3' as c3,'c4' as c4" ;
			//return ",' ' as c1,' ' as c2,' ' as c3,' ' as c4" ;
		} else {
			return ",to_char(ct.decisionDate,'dd.mm.yyyy')||'/'||(select to_char(min(ct1.decisionDate),'dd.mm.yyyy') from CompulsoryTreatment ct1 where pcc.id=ct1.careCard_id and ct1.orderNumber=ct.orderNumber) as ctdecisionDate, vlc.name as ctvlcname, vcca.code as ctvccaname,coalesce(ct.dateReplace,current_date)- ct.decisionDate as ctcntdays";
		}
	} else if (aType=="invalidity") {
		if (aInt==0) {
			return ",'i1' as i1,'i2' as i2,'i3' as i3,'i4' as i4,'i5' as i5,'i6' as i6" ;
			//return ",' ' as i1,' ' as i2,' ' as i3,' ' as i4" ;
		} else {
			return ",case when inv.incapable='1' then 'Недеесп. '||coalesce(to_char(inv.lawCourtDate,'dd.mm.yyyy'),'-')||' '||invvlc.name else '' end as invincapable, to_char(inv.dateFrom,'dd.mm.yyyy') as invdatefrom,case when inv.withoutExam='1' then 'Без переосвид.' else to_char(inv.nextRevisionDate,'dd.mm.yyyy') end as invwithoutexam, vi.name as viname, vivr.code as vivrid, vihr.code as vihrid " ;
		}
	}
	return "" ;
}