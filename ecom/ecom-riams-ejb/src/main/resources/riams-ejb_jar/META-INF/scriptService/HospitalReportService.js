var map = new java.util.HashMap() ;
function printDeathList(aCtx,aParams) {
	var result = aParams.get("result") ;
	var department = +aParams.get("department") ;
	var dep="" ;
	var depNoOmc="" ;
	if (department>0) {
		dep=" and dmc.department_id='"+department+"'" ;
		depNoOmc=" and (dmc.department_id='"+department+"' or d.isNoOmc='1' and pdmc.department_id='"+department+"')" ;
	}
	var dateBegin = aParams.get("dateBegin") ;
	var dateEnd = aParams.get("dateEnd") ;
	if (dateEnd==null || dateEnd=="") {
		dateEnd=dateBegin ;
	}
	//Поиск по дате постуления или выписки
	var typeDate = +aParams.get("typeDate") ;
	var dateT="hmc.dateStart" ;
	if (typeDate==2) {
		dateT="hmc.dateFinish" ;
	}
	//Операции
	var typeOperation = +aParams.get("typeOperation") ;
	var addOperation="" ;
	if (typeOperation==1) {
		addOperation = " having (count(soHosp.id)>0 or count (soDep.id)>0) ";
	} else if (typeOperation==2) {
		addOperation = " having (count(soHosp.id)=0 and count (soDep.id)=0) ";
	}
	//Показания к поступлению планово или экстренно
	var typeEmergency = +aParams.get("typeEmergency") ;
	var addEmergency="" ;
	if (typeEmergency==1) {
		addEmergency=" and hmc.emergency='1' " ;
	} else if (typeEmergency==2) {
		addEmergency=" and (hmc.emergency is null or hmc.emergency='0')" ;
	}
	// Тип пациента
	var typePatient = +aParams.get("typePatient") ;
	var addPat = "";
	if (typePatient==1) {
		addPat=" and (ok.id is not null and ok.voc_code!='643') " ;
	} else if (typePatient==2) {
		addPat=" and (adr.addressId is not null and adr.kladr not like '30%') " ;
	}
	
	var sqlReestr = "select  "
		+"  d.name ||case when d.isNoOmc='1' then coalesce(' ('||pd.name||')','') else '' end as depname"
	    +" ,ss.code as sscode"
	    +" ,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'') as fio"
	    +" ,(hmc.dateFinish-pat.birthday)/365 as age"
	    +" ,case when hmc.emergency='1' then 'Э' else 'П' end"
	    
	    +" ,vpat.name as vpatname"
	    +" , case" 
	    +" 	when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1" 
	    +" 	when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1)"  
	    +" 	else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)"
	    +" 	end as countDays"
	    +" ,to_char(hmc.dateStart,'dd.mm.yyyy') as hmcdatestart,to_char(hmc.dateFinish,'dd.mm.yyyy') as hmcdatefinish"
	    +" , list(case when vpd.code='1' and vdrt.code='4' then mkb.code||' '||mkb.name else '' end) as diag"
	    +" ,count(soHosp.id)+count(soDep.id) as cntOper"
	    +" , list(to_char(soDep.operationDate,'dd.mm.yyyy')|| '-'||voDep.code|| ' '||voDep.name) ||' ' ||list(to_char(soHosp.operationDate,'dd.mm.yyyy')|| '-'||voHosp.code|| ' '||voHosp.name)"
	    //+" , d.name ||case when d.isNoOmc='1' then coalesce(' ('||pd.name||')','') else '' end as depname"
	    +" from MedCase as hmc"
	   	+" left join diagnosis diag on diag.medcase_id=hmc.id"
	   	+" left join VocIdc10 mkb on mkb.id=diag.idc10_id" 
	   	+" left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id"
	   	+" left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id"
	    +" left join statisticstub ss on hmc.statisticstub_id=ss.id"
	    +" left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id" 
	    +" left join MedCase as pdmc on pdmc.dtype='DepartmentMedCase' and pdmc.id=dmc.prevMedCase_id"
	    +" left join mislpu as pd on pd.id=pdmc.department_id  "
	    +" left join MedCase as admc on admc.dtype='DepartmentMedCase' and hmc.id=admc.parent_id" 
	    +" left join vocservicestream as vss on vss.id=hmc.servicestream_id"
	    +" left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id" 
	    +" left join mislpu as d on d.id=dmc.department_id" 
	    +" left join patient pat on pat.id=hmc.patient_id"
	    +" left join VocHospType vht on vht.id=hmc.hospType_id"
	    +" left join address2 adr on adr.addressId = pat.address_addressId"
	    +" left join vocHospitalizationResult vhr on vhr.id=hmc.result_id"
	    +" left join SurgicalOperation soHosp on soHosp.medCase_id=hmc.id"
	    +" left join VocOperation voHosp on soHosp.operation_id=voHosp.id"
	    +" left join SurgicalOperation soDep on soDep.medCase_id=admc.id"
	    +" left join VocOperation voDep on soDep.operation_id=voDep.id"
	    +" left join Omc_Oksm ok on pat.nationality_id=ok.id"
	    +" where hmc.DTYPE='HospitalMedCase' "
	    +" and "+dateT+" between to_date('"+dateBegin+"','dd.mm.yyyy')"  
	    +" 	and to_date('"+dateEnd+"','dd.mm.yyyy')" 
	    	
	    +" 	and dmc.dateFinish is not null"
	    +"	and hmc.result_id='"+result+"'"
	    +"	"+depNoOmc
	    	
	    +" "+addPat 
	    +" "+addEmergency
	    
	    +" group by hmc.id,pat.lastname,pat.firstname,pat.middlename,pat.birthday"
	    +" ,vpat.name,hmc.dateFinish,hmc.dateStart,hmc.emergency,vht.code,ss.code"
	    +" ,d.name,d.isNoOmc,pd.name"
	    
	    +" "+addOperation ;
	
	var sqlSwod = "    select  "
		+" d.name as depname"
		+" ,count(*)"
		+" ,count(case when hmc.emergency='1' then 1 else null end) as cntEmer"
		+" ,count(case when hmc.emergency='1' then null else 1 end) as cntPlan"
		+" ,count(case"
		+" 	when (select count(*) from SurgicalOperation so left join medcase m on m.id=so.medcase_id where m.id=hmc.id or m.parent_id=hmc.id)>0 then 1 else null"
		+" 	end) as cntoper"
    
		+" , sum(case "
		+" 	when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1" 
		+" 	when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1)" 
		+" 	else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)"
		+" 	end)/count(hmc.id) as spCountDays"
		+" from MedCase as hmc"
		+"  left join statisticstub ss on hmc.statisticstub_id=ss.id"
		+" left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id" 
		+" left join vocservicestream as vss on vss.id=hmc.servicestream_id"
		+" left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id" 
		+" left join mislpu as d on d.id=dmc.department_id "
		+" left join patient pat on pat.id=hmc.patient_id"
		+" left join VocHospType vht on vht.id=hmc.hospType_id"
		+" left join address2 adr on adr.addressId = pat.address_addressId"
		+" left join vocHospitalizationResult vhr on vhr.id=hmc.result_id"
		+" left join Omc_Oksm ok on pat.nationality_id=ok.id"
		+" where hmc.DTYPE='HospitalMedCase' "
	    +" and "+dateT+" between to_date('"+dateBegin+"','dd.mm.yyyy')"  
	    +" 	and to_date('"+dateEnd+"','dd.mm.yyyy')" 
    	
		+" 	and dmc.dateFinish is not null"
	    +"	and hmc.result_id='"+result+"'"
	    +"	"+dep
	    	
	    +" "+addPat 
    
    
	    +" group by "
	    +" d.name" ;
	var sqlSwodNoOmc = "    select  "
		+" case when d.isNoOmc='1' and pd.id is not null then pd.name else d.name end as depname"
		+" ,count(*)"
		+" ,count(case when hmc.emergency='1' then 1 else null end) as cntEmer"
		+" ,count(case when hmc.emergency='1' then null else 1 end) as cntPlan"
		+" ,count(case"
		+" 	when (select count(*) from SurgicalOperation so left join medcase m on m.id=so.medcase_id where m.id=hmc.id or m.parent_id=hmc.id)>0 then 1 else null"
		+" 	end) as cntoper"
    
		+" , sum(case "
		+" 	when (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)=0 then 1" 
		+" 	when vht.code='DAYTIMEHOSP' then ((coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)+1)" 
		+" 	else (coalesce(hmc.dateFinish,CURRENT_DATE)-hmc.dateStart)"
		+" 	end)/count(hmc.id) as spCountDays"
		+" from MedCase as hmc"
		+"  left join statisticstub ss on hmc.statisticstub_id=ss.id"
		+" left join MedCase as dmc on dmc.dtype='DepartmentMedCase' and hmc.id=dmc.parent_id" 
		+" left join vocservicestream as vss on vss.id=hmc.servicestream_id"
		+" left join VocPreAdmissionTime vpat on vpat.id=hmc.preAdmissionTime_id" 
		+" left join mislpu as d on d.id=dmc.department_id "
		+" left join patient pat on pat.id=hmc.patient_id"
		+" left join VocHospType vht on vht.id=hmc.hospType_id"
		+" left join address2 adr on adr.addressId = pat.address_addressId"
		+" left join vocHospitalizationResult vhr on vhr.id=hmc.result_id"
		+" left join Omc_Oksm ok on pat.nationality_id=ok.id"
		+" left join MedCase as pdmc on pdmc.dtype='DepartmentMedCase' and pdmc.id=dmc.prevMedCase_id" 
		+" left join mislpu as pd on pd.id=pdmc.department_id "
		+" where hmc.DTYPE='HospitalMedCase' "
	    +" and "+dateT+" between to_date('"+dateBegin+"','dd.mm.yyyy')"  
	    +" 	and to_date('"+dateEnd+"','dd.mm.yyyy')" 
    	
		+" 	and dmc.dateFinish is not null"
	    +"	and hmc.result_id='"+result+"'"
	    +"	"+depNoOmc
	    	
	    +" "+addPat 
    
    
	    +" group by case when d.isNoOmc='1' and pd.id is not null then pd.name else d.name end" ;
	    +" order by case when d.isNoOmc='1' and pd.id is not null then pd.name else d.name end" ;

	executeSql("listReestr",aCtx.manager,sqlReestr,12) ;
	executeSql("listSwod",aCtx.manager,sqlSwod,6) ;
	executeSql("listSwodNoOmc",aCtx.manager,sqlSwodNoOmc,6) ;
	return map ;
}
function executeSql(aName,aManager,aSql,aCntCols) {
	var listReestr = aManager.createNativeQuery(aSql).getResultList() ;
	var ret = new java.util.ArrayList() ;
	aCntCols++ ;
	var j=1 ;
	for (var ind=0 ; ind< listReestr.size() ; ind++) {
		var obj = listReestr.get(ind) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.set1(""+(j++)) ;
		for (var i=2;i<=aCntCols;i++) {
			var i1=i-2 ;
			eval("par.set"+i+"(obj["+i1+"]!=null?obj["+i1+']:"");') ;
		}
		ret.add(par) ;
	}
	map.put(aName,ret) ;
}
