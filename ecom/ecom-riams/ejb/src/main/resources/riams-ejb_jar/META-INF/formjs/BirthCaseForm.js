function onCreate(aForm, aBirthCase, aCtx) {
	aBirthCase.patient = aBirthCase.getMedCase().getPatient();
}