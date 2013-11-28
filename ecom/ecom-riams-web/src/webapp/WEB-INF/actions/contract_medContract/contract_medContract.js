/**
 * Список всех случаев нетрудоспособности по пациенту
 */
function list_accrual_service(aForm, aCtx) {
        return aCtx.createForward("/WEB-INF/actions/contract_medContract/list_accrual_service.jsp") ;
}