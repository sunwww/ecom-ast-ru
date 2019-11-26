function allByPatient (aForm, aCtx) {
    return aCtx.createForward("/WEB-INF/actions/smo_planHospitalByVisit/list_by_patient.jsp") ;

}
function allByPatientOpht (aForm, aCtx) {
    return aCtx.createForward("/WEB-INF/actions/smo_planHospitalByVisit/list_by_patient_opht.jsp") ;

}
function allByPatientOphtVis (aForm, aCtx) {
    return aCtx.createForward("/WEB-INF/actions/smo_planHospitalByVisit/list_by_patient_ophtVis.jsp") ;

}