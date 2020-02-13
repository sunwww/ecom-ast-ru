var map = new java.util.HashMap() ;	
function printOmcBill(aCtx, params) {
	var bill = aCtx.manager.find(Packages.ru.ecom.expert2.domain.E2Bill
		, new java.lang.Long(params.get("billId"))) ;
	var billNumber = bill.billNumber;
	var billDate = bill.billDate;
	map.put("billNumber",billNumber);
	map.put("billDate",billDate);
	var cost = bill.sum;
	map.put("billCost",cost);
	map.put("billPeriod",params.get("billPeriod"));
	map.put("billProperty",bill.getBillProperty()!=null ? bill.getBillProperty() : "");
	var value = new java.math.BigDecimal(cost+"") ;
	var kop =(+cost % 1).toFixed(2).slice(2) ;
	map.put("billCostString",Packages.ru.ecom.ejb.services.util.ConvertSql.toWords(value)+" руб. "+ kop +" коп.") ;
	if (+params.get("entry")>0) { //добавим записи по счету
		map.put("entries",aCtx.manager.createNamedQuery("E2Entry.getAllByBillAndDate")
			.setParameter("billNumber",billNumber).setParameter("billDate",billDate).getResultList());
	}
	return map;
}
