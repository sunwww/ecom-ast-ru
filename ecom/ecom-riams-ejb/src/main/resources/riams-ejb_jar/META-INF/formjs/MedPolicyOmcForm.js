/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	/*
	var number = aForm.polNumber ;
	var series = aForm.series ;
	var smo = aForm.company ;
	
	*/
	checkPeriod(aForm) ;
	checkNumSerSmo(aCtx,aForm,"") ;
	//throw "select top 1 CheckOMIPolicy('"
    //	+series+"','"+number+"','"+smo+"',0,'"+sgr+"','"+rayon+"') from MedPolicy" ;

	
}

function onPreSave(aForm, aEntity, aCtx) {
	var add =" and id!="+aForm.id ;
	checkPeriod(aForm) ;
	checkNumSerSmo(aCtx,aForm,add) ;
}

function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error =":";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			if (doc.patient!=null) {
				error = error+" <br/><a href='entitySubclassView-mis_medPolicy.do?id="+doc.id+"'>"
				error=error+" ПЕРСОНА:"+doc.patient.lastname+" "+doc.patient.firstname ;
				error=error+" ПОЛИС: " +doc.text +"</a><br/>" ;
			} else {
				error = error+" <br/><a href='entitySubclassView-mis_medPolicy.do?id="+doc.id+"'>"+"ОШИБКА !!! НЕ УКАЗАНА ПЕРСОНА!!! УДАЛИТЕ ЭТОТ ПОЛИС И ЗАНОВО СОЗДАЙТЕ ЕГО"+"</a><br/>"
			}
		}
		throw aError + error ;
	}
}

function checkPeriod(aForm) {
}
function checkNumSerSmo(aCtx,aForm,aSqlAdd) {
	var number = aForm.polNumber ;
	var series = aForm.series ;
	var smo = aForm.company ;
	var dateFrom, dateTo ;
	try {
		dateFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateFrom) ;
	} catch(e) {
		throw "Неправильно введена дата начала" ;
	}
	try {
		dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateTo) ;
	} catch(e) {
		dateTo=null;
		//throw "Неправильно введена дата начала или окончания" ;
	}
	if ( dateFrom==null)	throw "Поле даты начала действия полиса является обязательным" ;
	if (dateTo != null && (dateTo.getTime() < dateFrom.getTime())) throw "Дата окончания должна быть больше, чем дата начала";
	
	var policies ;
	policies = aCtx.manager.createQuery("from MedPolicy where"
		+" series=:series and polnumber=:number and company_id=:company"+ aSqlAdd
		)
		.setParameter("series",series)
		.setParameter("number",number)
		.setParameter("company",smo)
		.getResultList() ;
	errorThrow(policies, "В базе уже существует полис с такой серией, номером и страховой компанией") ;	
	/*
	var company = aCtx.manager.find(Packages.ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany,smo) ;
	var comomc = company!=null?company.omcCode:"" ;
	var patient = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.patient.Patient,aForm.patient) ;
	var sgr = patient.socialStatus!=null?patient.socialStatus.omcCode:"" ;
	var rayon = patient.rayon!=null?patient.rayon.code:"" ;
	var err=null ;
	err = aCtx.manager.createNativeQuery("select top 1 $$CheckOMC^ZMedPolicy('"+series+"','"+number+"','"+comomc+"',0,'"+sgr+"','"+rayon+"',cast('"+dateTo+"' as date)),id from VocSex"
       	).getSingleResult() ;

    if (err!=null && err[0]!=null) throw ""+err[0] ;
    */
}