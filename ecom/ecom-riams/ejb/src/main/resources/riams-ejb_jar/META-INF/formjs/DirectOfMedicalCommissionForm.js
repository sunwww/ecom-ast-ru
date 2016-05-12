/**
 * Перед сохранением
 */
function onPreSave(aForm,aEntity, aCtx) {
	if (aEntity.expertDate!=null) {
		throw "Нельзя изменять направление на ВК, по которому уже принято решение эксперта!!!" ;
	}
	var patient = aForm.getPatient() ;
	var orderDate = aForm.getOrderDate() ;
	var reasonDirect = aForm.getReasonDirect() ;
	var thisid = aForm.getId() ;
	var list ;
    list = aCtx.manager.createQuery("from ClinicExpertCard where "
    		+" patient_id = :patient "
    		+" and orderDate = to_date(:orderDate,'dd.mm.yyyy') and reasonDirect_id='"+reasonDirect+"'"
    		+" and id != '"+thisid+"'"
       	)
       	.setParameter("patient",patient)
       	.setParameter("orderDate",orderDate)
       	.getResultList() ;
	errorThrow(list,"В базе уже существует направление на ВК от "+orderDate+" по пациенту") ;
	var date = new java.util.Date();
	aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setEditTime(new java.sql.Time (date.getTime())) ;
	aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	checkOrderDate(orderDate,aEntity.createDate!=null?aEntity.createDate:new java.sql.Time (date.getTime()))
}

/**
 * При создании
 */
function onCreate(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setCreateDate(new java.sql.Date(date.getTime())) ;
	aEntity.setCreateTime(new java.sql.Time (date.getTime())) ;
	aEntity.setCreateUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;

}
/**
 * Перед созданием
 */
function onPreCreate(aForm, aContext) {
	//Проверка на дубли
	var patient = aForm.getPatient() ;
	var orderDate = aForm.getOrderDate() ;
	var reasonDirect = aForm.getReasonDirect() ;
    var list = aContext.manager.createQuery("from ClinicExpertCard where "
    		+" patient_id = :patient "
    		+" and orderDate = to_date('"+orderDate+"','dd.mm.yyyy') and reasonDirect_id='"+reasonDirect+"'"
       	)
       	.setParameter("patient",patient)
       	.getResultList() ;
    
	//throw "timeC="+timeC+"; timeO="+timeO ;
	errorThrow(list,"В базе уже существует направление на ВК от "+orderDate+" по пациенту") ;
	checkOrderDate(orderDate,new java.sql.Date(new java.util.Date().getTime()));
}
function checkOrderDate(aOrderDate,aCreateDate) {
    var orderDateCal = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aOrderDate);
    var cal = java.util.Calendar.getInstance() ;
    cal.setTime(aCreateDate) ;
    var timeC = cal.getTime().getTime();
	cal.setTime(orderDateCal) ;
	cal.add(java.util.Calendar.DAY_OF_MONTH,3) ;
	var timeO = cal.getTime().getTime();
	if (timeO<timeC) throw "У Вас стоит ограничение на дату направления!!!" ; 
}
function onPreDelete(aEntityId, aCtx) {
	var entity = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.expert.ClinicExpertCard,aEntityId) ;
	if (entity.expertDate!=null) {
		throw "Нельзя удалять направление на ВК, по которому уже принято решение эксперта!!!" ;
	}
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error = "";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" <a href='entityParentView-expert_ker_direct.do?id="+doc.id+"'>Направление на ВК №"+doc.id + "</a><br/>" ;
		}
		throw aError + error ;
	}
}