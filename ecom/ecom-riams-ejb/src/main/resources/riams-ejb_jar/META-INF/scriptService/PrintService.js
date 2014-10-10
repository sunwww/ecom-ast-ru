var map = new java.util.HashMap() ;

function printNativeQuery_date(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
	var cntBegin = +aParams.get("cntBegin");
	var sqlColumn = aParams.get("sqlColumn");
	var beginDate = aParams.get("date1") ;
	var endDate = aParams.get("date2") ;
	if (cntBegin<1) cntBegin=1 ;
	var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	var ret = new java.util.ArrayList() ;
	var retAll = new java.util.ArrayList() ;
	var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var cal1 = java.util.Calendar.getInstance() ;
	cal1.setTime(Packages.ru.nuzmsh.util.format.DateFormat.parseDate(beginDate)) ;
	var cal2 = java.util.Calendar.getInstance() ;
	cal2.setTime(Packages.ru.nuzmsh.util.format.DateFormat.parseDate(endDate)) ;
	cal = java.util.Calendar.getInstance() ;
	var i=0;
	while (cal1.getTime().getTime()<=cal2.getTime().getTime()) {
		var obj,dt;
		if (list.size()>0 && i<list.size()) {
			obj = list.get(i) ;
			dt=""+obj[1] ;
		} else {
			dt=null ;
		}
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult() ;
		par.set1(""+cntBegin) ;
		++cntBegin ;
		if (dt!=null){cal.setTime(Packages.ru.nuzmsh.util.format.DateFormat.parseDate(dt)) ;}
		
		if (dt!=null && cal.getTime().getTime()==cal1.getTime().getTime()) {
			for (var j=2;j<=obj.length;j++) {
				var val = obj[j-1] ;
				eval("par.set"+(j)+"(val);") ;
				if (val==null) val=0 ;
				
				eval("var val1=parAll.get"+j+"()") ;
				if (val1==null) val1=0 ;
				var val11 = new java.math.BigDecimal(''+val1) ;
				var val12=  new java.math.BigDecimal(''+0) ;
				try {
					val12 =  new java.math.BigDecimal(''+val) ;
				} catch(e) {
					
				}
				var val=val12.add(val11) ;
				
				eval("parAll.set"+(j)+"(val);") ;
				
			}
			i++ ;
		} else {
			par.set2(FORMAT_2.format(cal1.getTime()));
			for (var j=3;j<=obj.length;j++) {
				eval("par.set"+(j)+"('');") ;
				
			}
		}
		ret.add(par) ;
		cal1.add(java.util.Calendar.DAY_OF_MONTH, 1) ;
	}
	retAll.add(parAll) ;
	map.put("list",ret) ;
	map.put("sqlInfo",sqlInfo) ;
	map.put("sqlColumn",sqlColumn) ;
	map.put("listAll",retAll) ;
	return map ;
}
function printNativeQuery(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
	var cntBegin = +aParams.get("cntBegin");
	var sqlColumn = aParams.get("sqlColumn");
	if (cntBegin<1) cntBegin=1 ;
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	var ret = new java.util.ArrayList() ;
	var retAll = new java.util.ArrayList() ;
	var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		
		par.set1(""+cntBegin) ;
		++cntBegin ;
		for (var j=2;j<=obj.length;j++) {
			var val = obj[j-1] ;
			eval("par.set"+(j)+"(val);") ;
			if (val==null) val=0 ;
			
				eval("var val1=parAll.get"+j+"()") ;
				if (val1==null) val1=0 ;
				var val11 = new java.math.BigDecimal(''+val1) ;
				var val12=  new java.math.BigDecimal(''+0) ;
				try {
				val12 =  new java.math.BigDecimal(''+val) ;
				} catch(e) {
					
				}
				var val=val12.add(val11) ;
				
				eval("parAll.set"+(j)+"(val);") ;
			
		}
		ret.add(par) ;
	}
	retAll.add(parAll) ;
	map.put("list",ret) ;
	map.put("sqlInfo",sqlInfo) ;
	map.put("sqlColumn",sqlColumn) ;
	map.put("listAll",retAll) ;
	return map ;
}
function printManyNativeQuery(aCtx,aParams) {
	var sqlCount = +aParams.get("sqlCount");
	var totalCount = +aParams.get("totalCount") ;
	var listRet ="";
	for (var jj=1;jj<=sqlCount;jj++) {listRet=listRet+";var listAll"+jj+"= new Packages.ru.ecom.ejb.services.query.WebQueryResult() ;"}
	eval(listRet) ;
	var maxCnt = 0 ;
	for (var jj=1;jj<=sqlCount;jj++) {
		var sqlText = aParams.get("sqlText"+jj);
		
		var sqlInfo = aParams.get("sqlInfo"+jj);
		
		var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
		var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		var ret = new java.util.ArrayList() ;
		
		for (var i=0; i < list.size(); i++) {
			var obj = list.get(i) ;
			var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			
			par.set1(""+(i+1)) ;
			if(maxCnt<obj.length) maxCnt=obj.length ;
			for (var j=2;j<=obj.length;j++) {
				var val = obj[j-1] ;
				eval("par.set"+(j)+"(val);") ;
				//if (val==null) val=0 ;
				if (val!=null && +val>0) {
					eval("var val1=listAll"+jj+".get"+j+"()") ;
					if (val1==null) val1=0 ;
					var val11 = new java.math.BigDecimal(''+val1) ;
					var val12 =  new java.math.BigDecimal(''+val) ;
					var val=val12.add(val11) ;
					eval("listAll"+jj+".set"+(j)+"(val);") ;
					
				}
			}
			ret.add(par) ;
		}
		map.put("list"+jj,ret) ;
		map.put("sqlInfo"+jj,sqlInfo) ;
		eval("map.put(\"listAll\"+jj,listAll"+jj+");") ;
		//eval("listAll"+jj+"=parAll") ;
	}
	//throw "maxCnt="+maxCnt ;
	for (var jj=1;jj<=totalCount;jj++) {
		var totalList = aParams.get("totalList"+jj);
		var obj = totalList.split(",") ;
		var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		for (var i=0; i < obj.length; i++) {
			var objTotal = obj[i] ;
			for (var j=2;j<=maxCnt;j++) {
				eval("var val=parAll.get"+j+"()");
				if (val==null) val=0 ;
				val=+val ;
				eval("val=+val+(+listAll"+objTotal+".get"+j+"())") ;
				if (+val>0) {
					/*var val_1=(val%1).toFixed(2).slice(2)
					var val_2=val|0 ;
					eval("parAll.set"+(j)+"("+val_1+"."+val_2+");") ;
					*/
					eval("parAll.set"+(j)+"(''+val);") ;
					
				}
					//if (j==4) throw "val="+val;
			}
		}
		map.put("listTotal"+jj,parAll) ;
	
	}
	return map ;
}