var map = new java.util.HashMap() ;	
function printOmcBill(aCtx, params) {
	map.put("billNumber",params.get("billNumber"));
	map.put("billDate",params.get("billDate"));
	var cost = params.get("billCost");
	map.put("billCost",cost);
	map.put("billPeriod",params.get("billPeriod"));
	map.put("billCostString",params.get("billPeriod"));
	var value = new java.math.BigDecimal(cost) ;
	var kop =(+cost % 1).toFixed(2).slice(2) ;
	map.put("billCostString",Packages.ru.ecom.ejb.services.util.ConvertSql.toWords(value)+" руб. "+ kop +" коп.") ;
	return map;
}
