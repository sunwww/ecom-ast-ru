/**
 * Список всех случаев нетрудоспособности по пациенту
 */
function listByPatient(aForm, aCtx) {
        var col = aCtx.invokeScript("DisabilityService", "findDisabilityCasesByPatient"
        	, aCtx.request.getParameter("id")) ;
        aCtx.request.setAttribute("list", col) ;
        return aCtx.createForward("/WEB-INF/actions/dis_disabilityCase/list.jsp") ;
}