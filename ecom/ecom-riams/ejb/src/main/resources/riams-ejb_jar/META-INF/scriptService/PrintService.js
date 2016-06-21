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
	current_info(aCtx) ;
	return map ;
}
function current_info(aCtx) {
    var FORMAT_2 = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
    var FORMAT_3 = new java.text.SimpleDateFormat("HH:mm") ;	
	var current = new java.util.Date() ;
	var curDate = new java.sql.Date(current.getTime()) ;
	var curTime = new java.sql.Time(current.getTime()) ;	
	map.put("current_date",FORMAT_2.format(curDate)) ;
	map.put("current_time",FORMAT_3.format(curTime)) ;
	map.put("current_username",aCtx.sessionContext.callerPrincipal.name ) ;
}
function printGroupColumnNativeQuery(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
	var printSql = aParams.get("printSql");
	var printId = +aParams.get("printId") ;
	var isupdate=false ;
	if (printSql!=null &&printSql!='') {
		isupdate=true ;
	}
	var cntBegin = 1;
	var sqlColumn = aParams.get("sqlColumn");
	var groupField = aParams.get("groupField");
	if (cntBegin<1) cntBegin=1 ;
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	var ret = new java.util.ArrayList() ;
	var retAll = new java.util.ArrayList() ;
	var groupList = new java.util.ArrayList() ;
	var groupColList = new java.util.ArrayList() ;
	//var group = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	
	var cntColumn = +aParams.get("cntColumn");
	if (cntColumn<1) cntColumn=1 ;
	//throw ""+cntColumn ;
	var iCol = 0 ;
	var parCol = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	
	var isColCreate = false ;
	var idOld="" ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		eval("var idNew = ''+obj["+groupField+"] ;") ; 
		if (idOld!=idNew) {
			if (idOld!='') {
				var r = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				r.set1(idOld) ;
				r.set2(groupList) ;
				r.set3(groupColList) ;
				ret.add(r) ;
			}
			cntBegin=1;
			idOld = idNew ;
			groupList = new java.util.ArrayList() ;
			groupColList = new java.util.ArrayList() ;
		}
		
		par.set1(""+cntBegin) ;
		++cntBegin ;
		if (isupdate) {
			var print_id = printSql.replace(":id",obj[printId-1]) ; 
			aCtx.manager.createNativeQuery(print_id).executeUpdate() ;
		}
		for (var j=2;j<=obj.length;j++) {
			var val = obj[j-1] ;
			eval("par.set"+(j)+"(val==null?'':val);") ;
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
		
		
		if (isColCreate && (iCol)%cntColumn==0) {
			groupColList.add(parCol) ;
			parCol = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			isColCreate = false;
			iCol=0;
			//throw "+++" ;
		} else {
			//throw "---" ;
			
			
		}
		iCol++ ;
		isColCreate = true ;
		eval("parCol.set"+iCol+"(par);") ;
		groupList.add(par) ;
		
	}
	if (idOld!='') {
		var r = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		groupColList.add(parCol) ;
		r.set1(idOld) ;
		r.set2(groupList) ;
		r.set3(groupColList) ;
		ret.add(r) ;
	}
	retAll.add(parAll) ;
	map.put("list",ret) ;
	map.put("sqlInfo",sqlInfo) ;
	map.put("sqlColumn",sqlColumn) ;
	map.put("listAll",retAll) ;
	current_info(aCtx) ;
	return map ;
}
function printGroupNativeQuery(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
	var cntBegin = 1;
	var sqlColumn = aParams.get("sqlColumn");
	var groupField = aParams.get("groupField");
	if (cntBegin<1) cntBegin=1 ;
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	var ret = new java.util.ArrayList() ;
	var retAll = new java.util.ArrayList() ;
	var groupList = new java.util.ArrayList() ;
	//var group = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var idOld="" ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		eval("var idNew = ''+obj["+groupField+"] ;") ; 
		if (idOld!=idNew) {
			if (idOld!='') {
				var r = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
				r.set1(idOld) ;
				r.set2(groupList) ;
				ret.add(r) ;
			}
			cntBegin=1;
			idOld = idNew ;
			groupList = new java.util.ArrayList() ;
		}
		
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
		groupList.add(par) ;
		
	}
	if (idOld!='') {
		var r = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		r.set1(idOld) ;
		r.set2(groupList) ;
		ret.add(r) ;
	}
	retAll.add(parAll) ;
	map.put("list",ret) ;
	map.put("sqlInfo",sqlInfo) ;
	map.put("sqlColumn",sqlColumn) ;
	map.put("listAll",retAll) ;
	current_info(aCtx) ;
	return map ;
}
// Формируется массив с группировкой по 
function printGroup3NativeQuery(aCtx,aParams) {
    var sqlText = aParams.get("sqlText");
    var sqlInfo = aParams.get("sqlInfo");
    var cntBegin = 1;
    var cntBegin2 = 1;
    var cntBegin3 = 1;
    var printSql = aParams.get("printSql");
    var printId = +aParams.get("printId") ;
    var isupdate=false ;
    if (printSql!=null &&printSql!='') {
            isupdate=true ;
    }
    var sqlColumn = aParams.get("sqlColumn");
    var groupFieldAr = aParams.get("groupField");
    //throw ""+groupFieldAr;
    var groupField = groupFieldAr.split(",") ;
    //throw ""+sqlText ;
    var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
    var retAll = new java.util.ArrayList() ;
    var retAll1 = new java.util.ArrayList() ;
    var cmd="" ;
    //throw "length."+groupField.length+"----groupFieldAr="+groupFieldAr ;
    for (var ind=0;ind<groupField.length;ind++) {
            cmd+="var idOld"+ind+"='' ;" ;
            cmd+="var cntBegin"+ind+"=1 ;" ;
            //cmd+="var groupList"+ind+" = new java.util.ArrayList() ;" ;
            cmd+="var retG"+ind+" = new java.util.ArrayList() ;" ;
            cmd+="var ret"+ind+" = new java.util.ArrayList() ; var r"+ind+"=null;";
    }
    eval(cmd) ;
    var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
    var par ;
    for (var i=0; i < list.size(); i++) {
            var obj = list.get(i) ;
            par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;

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
            retAll1.add(par);
            for (var ind=0;ind<groupField.length;ind++) {
                    eval("idNew"+ind+"=''+obj["+groupField[ind]+"-1] ;") ;
            }




            if (idOld0!=idNew0) {
                r0 = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
                r0.set1(idOld0) ;
                r0.set2(par) ;
                r0.set3(java.lang.Long.valueOf(cntBegin0++)) ;
                retG0 = new java.util.ArrayList() ;
                r0.set4(retG0) ;
                ret0.add(r0) ;

                if (idOld0='') {
                	eval("cntBegin0=1 ;") ;
                	eval("try{cntBegin1=1 ;}catch(e){}") ;
                } else {
                	eval("try{cntBegin1=1 ;}catch(e){}") ;
                }
                eval("idOld0=idNew0 ;") ;
            }

            par.set1(""+cntBegin) ;
            ++cntBegin ;
           if (isupdate) {
                    var print_id = printSql.replace(":id",obj[printId-1]) ;
                    aCtx.manager.createNativeQuery(print_id).executeUpdate() ;
            }
            
            var isEquals = false ;
            for (var ind=1;ind<groupField.length;ind++) {
                var cmd = "if (idOld"+ind+"!=idNew"+ind+"||isEquals) {"
                +"retG"+ind+"=new java.util.ArrayList() ;try{cntBegin"+(ind+1)+"=1 ;}catch(e){}"
                +"var par"+ind+" = par ;"
                +"par"+ind+".set1(cntBegin"+(ind-1)+");"
                +"r"+ind+"=new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;"
                +"r"+ind+".set1(idOld"+ind+") ;"
                +"r"+ind+".set2(par"+ind+") ;"
                +"r"+ind+".set3(java.lang.Long.valueOf(cntBegin"+(ind)+")) ;"
                +"r"+ind+".set4(retG"+ind+") ;"
                +"retG"+(ind-1)+".add(r"+ind+") ;"
                +"idOld"+ind+"=idNew"+ind+" ;"
                +"isEquals=true;cntBegin"+(ind)+"+=1; "
                +"}" ;
                eval(cmd) ;
            }
            

    }
   // throw cntBegin1 ;
    map.put("list",ret0) ;
    map.put("sqlInfo",sqlInfo) ;
    map.put("sqlColumn",sqlColumn) ;
    map.put("listAll",retAll) ;
    map.put("listAll1",retAll1) ;
    current_info(aCtx) ;
    return map ;
}

function printNativeQuery(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var clazz = aParams.get("clazz");
	var sqlInfo = aParams.get("sqlInfo");
	var fieldIdUniq = +aParams.get("fieldIdUniq");
	var cntBegin = +aParams.get("cntBegin");
	var sqlColumn = aParams.get("sqlColumn");
	if (clazz!=null && !clazz.equals("")) {
		//throw clazz1+"---"+clazz2;
		var clazz1=clazz.split(":") ;
		eval("var cl1="+clazz1[0]) ;
		map.put("clazz",aCtx.manager.find(cl1,java.lang.Long.valueOf(clazz1[1]))) ;
	}
	if (cntBegin<1) cntBegin=1 ;
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	
	var ret = new java.util.ArrayList() ;
	var retAll = new java.util.ArrayList() ;
	var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	var oldFieldIdUniq="" ;
	var isUniqSn = fieldIdUniq<1?false:true ;
	for (var i=0; i < list.size(); i++) {
		var obj = list.get(i) ;
		var par = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
		if (isUniqSn) {
		//	throw "=== "+ret.size()+" true";
			var newFieldIdUniq=obj[fieldIdUniq-1] ;
			//throw oldFieldIdUniq+"--"+newFieldIdUniq ;
			if (oldFieldIdUniq!=newFieldIdUniq) {
				par.set1(""+cntBegin) ;
				++cntBegin ;
				oldFieldIdUniq=""+newFieldIdUniq;
			} else {
				par.set1("") ;
			}
		} else {
			par.set1(""+cntBegin) ;
			++cntBegin ;
		}
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
	current_info(aCtx) ;
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
	current_info(aCtx) ;
	return map ;
}
function printColumn(aCtx,aParams) {
	var sqlText = aParams.get("sqlText");
	var sqlInfo = aParams.get("sqlInfo");
	var cntBegin = +aParams.get("cntBegin");
	
	var sqlColumn = aParams.get("sqlColumn");
	if (cntBegin<1) cntBegin=1 ;
	
	var cntColumn = +aParams.get("cntColumn");
	if (cntColumn<1) cntColumn=1 ;
	var retCol = new java.util.ArrayList() ;
	var parCol = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	
	var list = aCtx.manager.createNativeQuery(sqlText).getResultList() ;
	var ret = new java.util.ArrayList() ;
	var retAll = new java.util.ArrayList() ;
	
	var parAll = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
	
	var iColumn = 1 ; var isColCreate = false ; 
	for (var i=0; i < list.size(); i++) {
		if (isColCreate && iColumn%cntColumn==0) {
			retCol.add(parCol) ;
			parCol = new Packages.ru.ecom.ejb.services.query.WebQueryResult()  ;
			isColCreate = false;
			iColumn++ ;
		} else {
			isColCreate = true ;
			iColumn=1;
		}
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
		eval("parCol.set"+iColumn+"(ret);") ;
		
	}
	if (isColCreate) {
		retCol.add(parCol) ;
	}
	retAll.add(parAll) ;
	map.put("list",ret) ;
	map.put("listCol",retCol) ;
	map.put("sqlInfo",sqlInfo) ;
	map.put("sqlColumn",sqlColumn) ;
	map.put("listAll",retAll) ;
	current_info(aCtx) ;
	return map ;
}