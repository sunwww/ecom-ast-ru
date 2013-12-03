var map = new java.util.HashMap() ;

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
			if (+val>0) {
				eval("var val=+val+(+parAll.get"+j+"())") ;
				eval("parAll.set"+(j)+"(val);") ;
			}
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
				if (+val>0) {
					eval("var val=+val+(+listAll"+jj+".get"+j+"())") ;
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
				eval("var val=+parAll.get"+j+"()");
				eval("val=+val+(+listAll"+objTotal+".get"+j+"())") ;
				if (+val>0) {
					/*var val_1=(val%1).toFixed(2).slice(2)
					var val_2=val|0 ;
					eval("parAll.set"+(j)+"("+val_1+"."+val_2+");") ;
					*/
					eval("parAll.set"+(j)+"(val);") ;
					
				}
					//if (j==4) throw "val="+val;
			}
		}
		map.put("listTotal"+jj,parAll) ;
	
	}
	return map ;
}