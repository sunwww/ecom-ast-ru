/**
 * Выбор даты поступления
 */
function findByDate(aForm, aCtx) {
	
	return aCtx.createForward("/WEB-INF/actions/stac_slo/listByDate.jsp") ;
}

function list_protocols(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_slo/list_protocols.jsp") ;
}

function list_edit_protocol(aForm, aCtx) {
	return aCtx.createForward("/WEB-INF/actions/stac_slo/list_edit_protocol.jsp") ;
}
function gotoChildBirthOrMisbirthOrRobson(aForm, aCtx) {
	var type = aCtx.request.getParameter("type");
	var chbId = aCtx.request.getParameter("chbId");
    var rbId = aCtx.request.getParameter("rbId");
    var mbId = aCtx.request.getParameter("mbId");
    var mcId = aCtx.request.getParameter("mcId");
    if (type=='chb' &&  chbId!="0") return aCtx.createForward("/entityParentView-preg_childBirth.do?id="+chbId) ;
    else if (type=='rb' && rbId!="0") return aCtx.createForward("/entityParentView-preg_robsonClass.do?id="+rbId) ;
    else if (type=='mb' && mbId!="0") return aCtx.createForward("/entityParentView-preg_misbirth.do?id="+mbId) ;
    else if (type=='chb') return aCtx.createForward("/entityParentPrepareCreate-preg_childBirth.do?id="+mcId) ;
    else if (type=='rb') return aCtx.createForward("/entityParentPrepareCreate-preg_robsonClass.do?id="+mcId) ;
    else if (type=='mb') return aCtx.createForward("/entityParentPrepareCreate-preg_misbirth.do?id="+mcId) ;
    else return aCtx.createForward("/entityParentView-stac_slo.do?id="+mcId) ;
}