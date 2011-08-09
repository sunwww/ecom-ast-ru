/**
 * Перед созданием записи
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
    //При заведении первого периода необходимо проверить пересечение с
    //другими документами по этому пациенту  (т.е. в этих случаях - запретить)
    var disDoc = aCtx.manager.find(
		Packages.ru.ecom.mis.ejb.domain.disability.DisabilityDocument
		,aForm.disabilityDocument) ;
	if (disDoc.anotherLpu==null) {
	 if (aForm.workFunction<1) throw "Рабочая функция является обязательным полем при создании нового документа нетрудоспособности" ;
	} else {
	 aForm.workFunction=0 ;
	}
	var dateTo ;
	var dateFrom ;
	if (aForm.dateTo != null && aForm.dateTo!="") {
		try {
			dateFrom = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.dateFrom) ;
			dateTo = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.dateTo) ;
		} catch(e) {
			throw "Неправильно введена дата начала или окончания" ;
		}
			if (dateTo.getTime() < dateFrom.getTime()) throw "Дата окончания продления должна быть больше, чем дата начала";
	}
    // если дата окончания не пуста, то она должна быть больше даты начала
	//errorThrow(list) ;
	
}
function onPreSave(aForm,aEntity , aCtx) {
	onPreCreate(aForm,aCtx) ;
	var dateToOld = aEntity.dateTo ;
	var dateFromOld = aEntity.dateFrom ;
	var row = aCtx.manager.createNativeQuery("select min(dateFrom), max(dateTo) from DisabilityRecord where disabilityDocument_id=:doc")
	       	.setParameter("doc",aForm.disabilityDocument)
			.getSingleResult() ;
	var dateMin = row[0] ;
	var dateMax = row[1] ;
	dateFromNew = Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(aForm.dateFrom) ;
		
	if (dateMin.getTime() != dateFromOld.getTime()) 
		if (dateFromNew.getTime() != dateFromOld.getTime())
			throw "Запрет на изменение дат продления, кроме даты начала 1 периода и даты окончания последнего периода!!!" ;
		
		
	// TODO доделать
	//throw "Запрет min="+dateMin.getTime()+ " old="+dateFromOld.getTime()+ " new=" + dateFromNew.getTime();
		
	if (dateToOld!=null && dateMax!=null && dateToOld.getTime() != dateMax.getTime()) {
		if (aForm.dateTo.equals("")) {
			throw "Запрет на изменение дат продления, кроме даты начала 1 периода и даты окончания последнего периода!!!" ;
		} else {
			dateToNew = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(aForm.dateTo) ;
			if (dateToNew.getTime() != dateToOld.getTime()) 
				throw "Запрет на изменение дат продления, кроме даты начала 1 периода и даты окончания последнего периода!!!" ;
			
		}
	}
		
		
	
	
	// если dateTo изменилась, если есть следующий период то ошибка
	//aEntity.dateTo
	// если dateFrom изменилась, если есть предыдущий период то ошибка
	//aEntity.dateFrom
    // если дата окончания не пуста, то она должна быть больше даты начала
}
function errorThrow(aList, aError) {
	if (aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var doc = aList.get(i) ;
			error = error+" ИД = "+doc.id+" ДОКУМЕНТ: "+ doc.series + " " +doc.number + "<br/>" ;
		}
		throw aError + error ;
	}
}