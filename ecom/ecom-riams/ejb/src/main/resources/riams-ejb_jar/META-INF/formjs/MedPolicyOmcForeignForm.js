/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	if (((+aForm.type==3)||(+aForm.type==4)) && aForm.commonNumber.trim()=="") {
		throw "При заполнение нового полиса поле ЕДИНЫЙ НОМЕР (RZ) является ОБЯЗАТЕЛЬНЫМ!!!" ; 
	}
	checkPeriod(aForm) ;
	
}

function onPreSave(aForm, aEntity, aCtx) {
	if (((+aForm.type==3)||(+aForm.type==4)) && aForm.commonNumber.trim()=="") {
		throw "При заполнение нового полиса поле ЕДИНЫЙ НОМЕР (RZ) является ОБЯЗАТЕЛЬНЫМ!!!" ; 
	}
	checkPeriod(aForm) ;
}
function checkPeriod(aForm) {
	if (+aForm.insuranceCompanyCode==0 && (+aForm.company==0)) {
		throw "Необходимо заполнить поле либо ОГРН СМО, либо Страховая компания" ;
	}
	if (+aForm.insuranceCompanyCode==0 && aForm.insuranceCompanyCity==""){
		throw "Необходимо заполнить поле Город СМО" ;
	} 
	var dateFrom, dateTo ;
	try {
		dateFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateFrom) ;
		dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.actualDateTo) ;
	} catch(e) {
		throw "Неправильно введена дата начала или окончания" ;
	}
	if (dateTo!=null && (dateTo.getTime() < dateFrom.getTime())) throw "Дата окончания должна быть больше, чем дата начала";
}
function onPreDelete(aEntityId, aCtx) {
	var list = aCtx.manager.createNativeQuery("select patient_id,dtype from medpolicy where id='"+aEntityId+"'").setMaxResults(1).getResultList() ;
	if (list.size()>0) {
		aCtx.manager.createNativeQuery("update Patient set attachedOmcPolicy_id=null where id="+list.get(0)[0]).executeUpdate() ;
	}
}
function onSave(aForm, aEntity, aCtx) {
	if (aEntity.commonNumber!=null&&aEntity.commonNumber!='') {
		aEntity.patient.setCommonNumber(aEntity.commonNumber) ;
	}
}
function onCreate(aForm, aEntity, aCtx) {
	if (aEntity.commonNumber!=null&&aEntity.commonNumber!='') {
		aEntity.patient.setCommonNumber(aEntity.commonNumber) ;
	}
}