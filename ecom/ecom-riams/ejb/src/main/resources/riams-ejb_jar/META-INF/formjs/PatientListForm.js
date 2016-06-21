function onPreDelete(aEntityId, aContext) {
	aContext.manager.createNativeQuery("delete from patientListRecord where patientList="+aEntityId).executeUpdate() ;
}