function by_slo_data(aForm, aCtx) {
	var request = aCtx.request ;
	var id=request.getParameter("id").split(":") ;
	//${mkbCode}||':${mkbLike}:${dep}:${servStream}:${fldDate}
	var mkbCode = "and mkb.code = '"+id[0]+"'"
	if (+id[1]>0) {
		
		mkbCode=" and mkb.code like '"+id[0]+"%'" ;
	}
	var filterAdd=id[10] ;
	
	if (filterAdd!=null) {
		var fs =filterAdd.split('-') ; 
		if (fs.length>1) {
			mkbCode=mkbCode+" and mkb.code between '"+fs[0]+"' and '"+fs[1]+"'" ;
		} else {
			mkbCode=mkbCode+" and mkb.code like '"+filterAdd+"%'" ;
		}
	} 
	request.setAttribute("mkbCode",mkbCode) ;
	request.setAttribute("dep",id[2]!=null?id[2]:"") ;
	request.setAttribute("servStream",id[3]!=null?id[3]:"") ;
	request.setAttribute("fldDate",id[4]) ;
	request.setAttribute("dateBegin",id[5]) ;
	request.setAttribute("dateEnd",id[6]) ;
	request.setAttribute("priority",id[7]!=null?id[7]:"") ;
	request.setAttribute("registrationType",id[8]!=null?id[8]:"") ;
	//request.setAttribute("prioritySql",id[9]!=null?id[9]:"") ;
	//request.setAttribute("regType",id[10]!=null?id[10]:"") ;
	request.setAttribute("bedSubTypeSql",id[9]!=null?id[9]:"") ;
	//prioritySql} ${regType} ${bedSubTypeSql
	return aCtx.createForward("/WEB-INF/actions/stac_diagnosis/list_by_slo_data.jsp") ;
}