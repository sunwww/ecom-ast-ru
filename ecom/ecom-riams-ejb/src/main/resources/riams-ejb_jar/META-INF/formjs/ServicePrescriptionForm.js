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
function onSave(aForm, aEntity, aCtx) {
	
}

/*function Remove_Load(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	var isEmergency = 0;
	var isLess2Hours = 0;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	
	var sdf =java.text.SimpleDateFormat("yyyy-MM-dd-hh:mm:ss") ;
	var prescriptList = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.prescription.PrescriptList,aForm.prescriptionList) ;
	var sqlquery = "select mcs.datestart || '-' || mcs.entrancetime as datetime, mc.emergency, mc.dtype from prescriptionList pl " +
			"left join medcase mc on pl.medcase_id = mc.id " +
			"left join medcase mcs on mcs.id = mc.parent_id " +
			"where pl.id ='"+prescriptList.id+"' and mcs.dtype='HospitalMedCase' " ;

	var list = aCtx.manager.createNativeQuery(sqlquery).getResultList() ;
	if (list.size()>0) {
		var obj = list.get(0);
		var date = sdf.parse(obj[0].toString());

		var check = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThenHour(date,2);
		if (obj[1]=="true") { // Если экстренно
			isEmergency=1;
		}
		if (check) { //Если меньше 2х часов
			isLess2Hours = 1;
		}	
	}

	aEntity.setIsEmergency(isEmergency) ;
	aEntity.setIsLess2Hours(isLess2Hours) ;
}*/
