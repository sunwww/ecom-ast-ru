var map = new java.util.HashMap() ;

function printNativeQuery(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
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
	return map ;
}