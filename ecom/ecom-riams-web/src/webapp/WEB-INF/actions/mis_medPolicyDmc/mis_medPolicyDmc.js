function listExist(aForm, aCtx) {
		if (Packages.ru.nuzmsh.web.tags.helper.RolesHelper.checkRoles("/Policy/Mis/MedPolicy/Dmc/Exist",aCtx.request)) {
			return aCtx.createForward("/WEB-INF/actions/mis_medPolicyDmc/existDmc_list.jsp") ;
		} else {
			return aCtx.createForward("/entityParentPrepareCreate-mis_medPolicyDmc.do") ;
		}
    //    var col = aCtx.invokeScript("TemplateService", "findListPrescriptTemplate"
    //    	, aCtx.request.getParameter("id"), 25, aCtx.request.getParameter("next") ) ;
    //    aCtx.request.setAttribute("list", col) ;
    //   return aCtx.createForward("/WEB-INF/actions/pres_template/list.jsp") ;
}
function createExist(aForm,aCtx) {
	var pat=aCtx.request.getParameter("pat") ;
	aCtx.invokeScript("MedPolicyService","createDmcPolicy",aCtx.request.getParameter("id")
						,pat
	) ;
	return aCtx.createForward("/entityView-mis_patient.do?id="+pat) ;
}