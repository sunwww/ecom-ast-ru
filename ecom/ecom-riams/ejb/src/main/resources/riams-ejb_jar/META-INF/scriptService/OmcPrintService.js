var map = new java.util.HashMap() ;	
function printOmcBill(aCtx, params) {
	var billNumber = params.get("billNumber");
	var billDate = params.get("billDate");
	map.put("billNumber",billNumber);
	map.put("billDate",billDate);
	var cost = params.get("billCost");
	map.put("billCost",cost);
	map.put("billPeriod",params.get("billPeriod"));
	map.put("billCostString",params.get("billPeriod"));
	var value = new java.math.BigDecimal(cost) ;
	var kop =(+cost % 1).toFixed(2).slice(2) ;
	map.put("billCostString",Packages.ru.ecom.ejb.services.util.ConvertSql.toWords(value)+" руб. "+ kop +" коп.") ;
	if (+params.get("entry")>0) { //добавим записи по счету
		map.put("entries",aCtx.manager.createNamedQuery("E2Entry.getAllByBillAndDate")
			.setParameter("billNumber",billNumber).setParameter("billDate"
				,Packages.ru.nuzmsh.util.format.DateFormat.parseSqlDate(billDate)).getResultList());
	}
	return map;
}
