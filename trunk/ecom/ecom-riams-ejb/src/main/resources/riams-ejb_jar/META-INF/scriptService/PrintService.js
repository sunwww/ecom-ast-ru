var map = new java.util.HashMap() ;

function printNativeQuery(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
	var sqlColumn = aParams.get("sqlColumn");
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	var ret = new java.util.ArrayList() ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		par.set1(""+(i+1)) ;
		for (var j=2;j<=obj.length;j++) {
			eval("par.set"+(j)+"(obj[j-1]);") ;
		}
		ret.add(par) ;
	}
	map.put("list",ret) ;
	map.put("sqlInfo",sqlInfo) ;
	map.put("sqlColumn",sqlColumn) ;
	return map ;
}
function printManyNativeQuery(aCtx,aParams) {
	var sqlCount = +aParams.get("sqlCount");
	for (var jj=1;jj<=sqlCount;jj++) {
		var sqlText = aParams.get("sqlText"+jj);
		
		var sqlInfo = aParams.get("sqlInfo"+jj);
		
		var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
		var ret = new java.util.ArrayList() ;
		for (var i=0; i < list.size(); i++) {
			var obj = list.get(i) ;
			var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			par.set1(""+(i+1)) ;
			for (var j=2;j<=obj.length;j++) {
				eval("par.set"+(j)+"(obj[j-1]);") ;
			}
			ret.add(par) ;
		}
		map.put("list"+jj,ret) ;
		map.put("sqlInfo"+jj,sqlInfo) ;
	}
	return map ;
}