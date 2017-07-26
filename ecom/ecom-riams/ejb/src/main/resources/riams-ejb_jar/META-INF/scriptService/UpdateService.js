function update_postgres(aCtx, aParams) {
    default_id(aCtx,"ExportFSSLog") ;

	default_id(aCtx,"ChangeJournal") ;

	default_id(aCtx,"CustomMessage") ;
	default_id(aCtx,"ChildBirth") ;
	default_id(aCtx,"NewBorn") ;
	default_id(aCtx,"DiaryMessage") ;
	default_id(aCtx,"VocDefectDiary") ;
	default_id(aCtx,"prescriptionlist") ;
	default_id(aCtx,"HospitalDataFond") ;
	default_id(aCtx,"ContractPerson") ;
	default_id(aCtx,"LpuContractNosologyGroup") ;
	default_id(aCtx,"VocMkbAdc") ;
	default_id(aCtx,"AdminChangeJournal") ;
	default_id(aCtx,"VocQualityEstimationCrit") ;
	default_id(aCtx,"VocQualityEstimationMark") ;
	default_id(aCtx,"FlowDocument") ;
 	default_id(aCtx,"electronicdisabilitydocumentnumber") ;
  	default_id(aCtx,"exportfsslog") ;
	default_id(aCtx,"listwatch") ;
	default_id(aCtx,"patientwatch") ;

	drop_index(aCtx,"kladr","kladr_kladrcode") ;
	max_sequnce_default_id(aCtx,"Address2","Addressid") ;
	max_sequnce_default_id(aCtx,"Kladr","Id") ;
	default_fld(aCtx,"HospitalDataFond","voc_time='0'","case when voc_time>0 then '1' else null end is null") ;
	return "1" ;
}
function max_sequnce_default_id(aCtx,aTable,aFldId) {
	var l = aCtx.manager.createNativeQuery("select max("+aFldId+") as maxfld  from "+aTable).getResultList() ;
	if (l.size()>0) {
		aCtx.manager.createNativeQuery("alter sequence "+aTable.toLowerCase()+"_sequence restart with "+(+l.get(0)+1)).executeUpdate() ;
	} 
}
function default_id(aCtx,aTable) {
	aTable=aTable.toLowerCase() ;
	aCtx.manager.createNativeQuery("alter table "+aTable+" alter column id set default nextval('"+aTable+"_sequence')").executeUpdate() ;
	return "1" ;
}
function default_fld(aCtx,aTable,aFld,aWhere) {
	aTable=aTable.toLowerCase() ;
	aCtx.manager.createNativeQuery("update "+aTable+" set "+aFld+" where "+aWhere+"").executeUpdate() ;
	return "1" ;
}
function drop_index(aCtx,aTable,aIndex) {
	try {
		var li = aCtx.manager.createNativeQuery("select ct.relname from pg_index i join pg_class ci on ci.oid=i.indexrelid " +
				"join pg_class ct on ct.oid=i.indrelid where ct.relname='"
					+aTable+"' and ci.relname like '"+aIndex+"'").getResultList() ;
		if (li.size()>0) aCtx.manager.createNativeQuery("drop index "+aIndex+"").executeUpdate() ;
	return "1" ;
	} catch(e) {
		
	}
}

