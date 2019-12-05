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
}

function onCreate(aForm,aEntity,aCtx){
	aEntity.setCreateDate(new java.sql.Date(new java.util.Date().getTime()));
	aEntity.setCreateTime(new java.sql.Time(new java.util.Date().getTime()));
}

function onPreSave(aForm,aEntity , aCtx) {
	
	onPreCreate(aForm,aCtx);
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
	checkExport(aEntity.getId(),aCtx);
}

function onSave(aForm,aEntity,aCtx) {
	onCreate(aForm,aEntity,aCtx);
}

function onPreDelete(aEntityId, aContext) {
	if (aContext.manager.createNativeQuery("select id from electronicdisabilitydocumentnumber where disabilitydocument_id="+aEntityId))
	checkExport(aEntityId, aContext);
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

function checkExport(aEntityId, aCtx) {
    var disRec = aCtx.manager.createNativeQuery("select createdate,createtime,disabilitydocument_id from disabilityrecord where id="+aEntityId).getResultList();
	var number = aCtx.manager.createNativeQuery("select number,id from disabilitydocument  where id ="+ disRec.get(0)[2]).getResultList();
	var electronic = aCtx.manager.createNativeQuery("select exportdate,exporttime from ElectronicDisabilityDocumentNumber where number='"+ number.get(0)[0] + "'").getResultList();
	if (electronic!=null&&electronic.size()>0) {
    	var createDate = disRec.get(0)[0];
    	if (createDate != null)  createDate = createDate.getTime();  else return;
    	var createTime = disRec.get(0)[1];
   		 if (createTime != null)  createTime = createTime.getTime();   else return;
    	var exportDate = electronic.get(0)[0];
   		var exportTime = electronic.get(0)[1];
    	if (exportDate != null) {
        	exportDate = exportDate.getTime();
        	if (exportDate > createDate) {
           	 throw "Нельзя изменять период! Данный период уже выгружен!";
        	} else if (exportDate == createDate) {
            // проверка по времени, если даты равны
            	if (exportTime != null) {
                	exportTime = exportTime.getTime();
                	if (exportTime >= createTime) {
                  	  throw "Нельзя изменять период! Данный период уже выгружен!";
               		}
            	}
       		}
    	}
	}

}