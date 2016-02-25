function update_postgres(aCtx, aParams) {
	default_id(aCtx,"CustomMessage") ;
	default_id(aCtx,"DiaryMessage") ;
	default_id(aCtx,"VocDefectDiary") ;
	default_id(aCtx,"prescriptionlist") ;
	default_id(aCtx,"HospitalDataFond") ;
	default_id(aCtx,"ContractPerson") ;
	default_id(aCtx,"LpuContractNosologyGroup") ;
	default_id(aCtx,"VocMkbAdc") ;
	drop_index(aCtx,"kladr_kladrcode") ;
	max_sequnce_default_id(aCtx,"Address2","Addressid") ;
	return "1" ;
}
function max_sequnce_default_id(aCtx,aTable,aFldId) {
	var l = aCtx.manager.createNativeQuery("select max("+aFldId+") as maxfld,count(*) as cnt from "+aTable).getResultList() ;
	if (l.size()>0) {
		aCtx.manager.createNativeQuery("alter sequence "+aTable.toLowerCase()+"_sequence restart with "+(l.get(0)[0]+1)).executeUpdate() ;
	} 
	
}
function default_id(aCtx,aTable) {
	aTable=aTable.toLowerCase() ;
	aCtx.manager.createNativeQuery("alter table "+aTable+" alter column id set default nextval('"+aTable+"_sequence')").executeUpdate() ;
	return "1" ;
}
function drop_index(aCtx,aIndex) {
	try {
	aCtx.manager.createNativeQuery("drop index "+aIndex+"").executeUpdate() ;
	return "1" ;
	} catch(e) {}
}

