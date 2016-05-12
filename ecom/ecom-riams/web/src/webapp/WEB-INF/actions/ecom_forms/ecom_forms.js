/** Loading class */
function loadClass(aClassname) {
	var clazz = java.lang.Thread.currentThread().getContextClassLoader().loadClass(aClassname) ;
	return clazz ;
}


/** get comment form class aTypeName*/
function getComment(aTypeName) {
	try {
		var clazz = loadClass(aTypeName) ;
		var ann = clazz.getAnnotation(Packages.ru.nuzmsh.commons.formpersistence.annotation.Comment) ;
		return ann!=null ? ann.value() : "" ;
	} catch (e) {
		return "<div class='errorMessage'>"+e+"</div>" ;
	}	
}

/**
 * List all struts forms
 */
function listAllStrutsForms(aForm, aCtx) {
	var forms = aCtx.getMapping().getModuleConfig().findFormBeanConfigs() ;
	
	var	ret = java.lang.reflect.Array.newInstance(java.lang.Object, forms.length) ;
	for(var i=0; i<forms.length; i++) {
		var map = new java.util.HashMap(3) ;
		var f = forms[i] ;
		//map.put("sn",(i+1)) ;
		map.put("name", f.name) ;
		map.put("type", f.type) ;
		map.put("comment", getComment(f.type)) ;
		ret[i] = map ;
	}
	
	var sortBy = aCtx.request.getParameter("sort") || "name" ;
	//if(sortBy=="" || sortBy==null) sortBy = "name" ;
	//sortBy = "name" ;
	var cmp = new JavaAdapter(Packages.java.util.Comparator, {
		compare : function (a,b) {
			return a.get(sortBy).compareTo(b.get(sortBy)) ;
		}
	}) ;
	
	java.util.Arrays.sort(ret, cmp) ;
	aCtx.getRequest().setAttribute("list", ret) ;
	return aCtx.createForward("/WEB-INF/actions/ecom_forms/list.jsp") ;
}

function delJournal(aForm, aCtx) {

	return aCtx.createForward("/WEB-INF/actions/ecom_forms/deleteJournal.jsp") ;
}
/**
 * Redirect from id=helloForm to "/entityList-hello.do" action
 */
function redirectToForm(aForm, aCtx) {
	var id = aCtx.request.getParameter("id") ;
	id = id.replace("Form","") ;
	return aCtx.createForwardRedirect("/entityList-"+id+".do") ;
	
}
