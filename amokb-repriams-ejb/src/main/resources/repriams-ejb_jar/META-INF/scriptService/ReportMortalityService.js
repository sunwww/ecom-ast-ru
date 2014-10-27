function load(aCtx, aId) {
	var form = new Packages.ru.ecom.mis.ejb.form.mortality.MortalityReportRowForm() ;
	form.menAge0="123";
	form.setId(new java.lang.Long(aId)) ;
	//throw form.menAge0+"";
	return form ;
}

function getLpuIdByMortalityReportDate(aCtx, aId) {
	var d = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.mortality.MortalityReportDate
		, new java.lang.Long(aId)) ;
	return d.getLpu().getId() ;	
	
}

function save(aCtx, aForm) {
	var d = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.mortality.MortalityReportDate
		, new java.lang.Long(aId)) ;

	var list = d.getReportRows() ;
	for(var i = 0 ;i<list.size();i++) {
		//aForm.
	}
	throw "hello"+aForm;
}