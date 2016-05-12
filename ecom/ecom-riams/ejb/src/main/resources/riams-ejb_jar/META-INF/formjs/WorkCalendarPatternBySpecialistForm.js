function setWeekForm(aForm,aFrmAlgNum,aEntity,aContext) {
	eval("var frmAlg=aForm.weekAlgorithmForm"+aFrmAlgNum) ;
	var parity=aForm.weekAlgorithmForm0.parity
	var calParity=aForm.weekAlgorithmForm0.calendarParity
	var parObj=getParity(parity,aFrmAlgNum,aContext) ;
	var isNext=false ;var calPar;
	var isCreate=false ;
	if (+frmAlg.getWorkWeek()>0 && +frmAlg.getDayPattern()>0) isCreate=true ;
	if (isCreate) {
		var objWeek = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarWeekAlgorithm() ;
		if (parObj!=null) {
			var calParObj=getCalendarParity(calParity,aContext) ;
			objWeek.setCalendarParity(calParObj) ;
		}
		objWeek.setPattern(aEntity) ;
		objWeek.setParity(parObj) ;
		objWeek.setWorkWeek(getVoc("ru.ecom.mis.ejb.domain.workcalendar.voc.VocWorkWeek",frmAlg.getWorkWeek(),aContext));
		objWeek.setDayPattern(getVoc("ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern",frmAlg.getDayPattern(),aContext));
		aContext.manager.persist(objWeek) ;
	}
	if (+parity-1>aFrmAlgNum) isNext=true ;
	return isNext ;
}
function setWeekDaysForm(aForm,aFrmAlgNum,aEntity,aContext) {
	var propertyList=["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"] ;
	eval("var frmAlg=aForm.weekDaysAlgorithmForm"+aFrmAlgNum) ;
	var parity=aForm.weekDaysAlgorithmForm0.parity
	var calParity=aForm.weekDaysAlgorithmForm0.calendarParity
	var parObj=getParity(parity,aFrmAlgNum,aContext) ;
	var isNext=false ;var calPar;
	var isCreate=false ;
	for (var j=0;j<propertyList.length;j++) {
		eval("if (+frmAlg.get"+propertyList[j]+"()>0) isCreate=true;");
	}
	if (isCreate) {
		var objWeek = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarWeekDaysAlgorithm() ;
		if (parObj!=null) {
			var calParObj=getCalendarParity(calParity,aContext) ;
			objWeek.setCalendarParity(calParObj) ;
		}
		objWeek.setPattern(aEntity) ;
		objWeek.setParity(parObj) ;
		
		for (var j=0;j<propertyList.length;j++) {
			eval("objWeek.set"+propertyList[j]+"(getVoc(\"ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern\",frmAlg.get"+propertyList[j]+"(),aContext));");
		}
		aContext.manager.persist(objWeek) ;
	}
	if (+parity-1>aFrmAlgNum) isNext=true ;
	return isNext ;

}
function onCreate(aForm, aEntity, aContext) {
	if (aForm.isWeek!=null && aForm.isWeek.equals(java.lang.Boolean.TRUE)) {
		var isNext=true ;
		for (var i = 0;i<3;i++) {
			if (isNext) {
				isNext=setWeekForm(aForm,i,aEntity,aContext) ;
			}
			if (!isNext) break ;
		}
	}
	if (aForm.isWeekDays!=null && aForm.isWeekDays.equals(java.lang.Boolean.TRUE)) {
		var isNext=true ;
		for (var i = 0;i<3;i++) {
			if (isNext) {
				isNext=setWeekDaysForm(aForm,i,aEntity,aContext) ;
			}
			if (!isNext) break ;
		}
	}
	if (aForm.isDays!=null && aForm.isDays.equals(java.lang.Boolean.TRUE)) {
		//try {
		
			var frmAlg = aForm.datesAlgorithmForm ;
			var dateFrom=Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(frmAlg.dateFrom);
			var dateTo=Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(frmAlg.dateFrom);
			var obj = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDatesAlgorithm() ;
			obj.setDayPattern(getVoc("ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarDayPattern",frmAlg.getDayPattern(),aContext)) ;
			obj.setDateFrom(dateFrom) ;
			obj.setDateTo(dateTo) ;
			obj.setPattern(aEntity) ;
			aContext.manager.persist(obj);
	}
	if (aForm.isProfday!=null && aForm.isProfday.equals(java.lang.Boolean.TRUE)) {
		var frmAlg = aForm.prophDayAlgorithmForm ;
		var obj = new Packages.ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarProphDayAlgorithm() ;
		obj.setPattern(aEntity) ;
		if (+aForm.getProfType()==1) {
			obj.setMonthOrder(getVoc("ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekMonthOrder",frmAlg.getMonthOrder(),aContext)) ;
			obj.setWeekDay(getVoc("ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay",frmAlg.getWeekDay(),aContext));
		} else {
			obj.setMonthDay(frmAlg.getMonthDay()) ;
		}
		aContext.manager.persist(obj);
		
	}
}
function getVoc(aClazz,aId,aContext) {
	//throw "var obj = aContext.manager.find(Packages."+aClazz+",aId);";
	eval("var obj = aContext.manager.find(Packages."+aClazz+",aId);");
	return obj ;
}
function getCalendarParity(aCode,aContext) {
	var code="DAY";var name="ДЕНЬ";
	if (+aCode==2) {
		code="WEEK";name="НЕДЕЛЯ" ;
	} else if (+aCode==3) {
		code="MONTH";name="МЕСЯЦ" ;
	}
	return getVocByCode("ru.ecom.mis.ejb.domain.workcalendar.voc","VocWorkCalendarParity",code,name,aContext) ;
}
function getParity(aCode,aNumForm,aContext) {
	var code=null; var name ;
	var vocClass="VocDayParity";var vocPath = "ru.ecom.mis.ejb.domain.workcalendar.voc";
	if (+aCode==2) {
		if (+aNumForm==0) {
			//четная
			code="YES" ;name="ЧЕТНЫЙ" ;
		} else if (+aNumForm==1) {
			//нечетная
			code="NO" ;name="НЕЧЕТНЫЙ" ;
		}
	} else if (+aCode==3) {
		if (+aNumForm==0) {
			code="1-3";name ="1 из 3";
		} else if (+aNumForm==1) {
			code="2-3";name ="2 из 3";
		} else if (+aNumForm==2) {
			code=code="3-3";name ="3 из 3";
		}
	}
	if (code!=null) {
		return getVocByCode(vocPath,vocClass,code,name,aContext) ;
	}
	return null ;
}
function getVocByCode(aVocPath, aVocClass, aCode, aName, aContext) {
	var list = aContext.manager.createQuery("from "+aVocClass+" where code='"+aCode+"'").getResultList() ;
	if (list.isEmpty()) {
		var obj ;
		eval("obj = new Packages."+aVocPath+"."+aVocClass+"();" );
		obj.setName(aName) ;
		obj.setCode(aCode) ;
		aContext.manager.persist(obj) ;
		return obj ;
	} else {
		return list.get(0) ;
	}
}
