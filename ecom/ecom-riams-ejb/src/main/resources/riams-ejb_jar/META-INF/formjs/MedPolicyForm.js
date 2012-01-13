function onPreDelete(aEntityId, aCtx) {
	var list = aCtx.manager.createNativeQuery("select patient_id,dtype from medpolicy where id='"+aEntityId+"'").setMaxResults(1).getResultList() ;
	if (list.size()>0) {
		aCtx.manager.createNativeQuery("update Patient set attachedOmcPolicy_id=null where id="+list.get(0)[0]).executeUpdate() ;
	}
}