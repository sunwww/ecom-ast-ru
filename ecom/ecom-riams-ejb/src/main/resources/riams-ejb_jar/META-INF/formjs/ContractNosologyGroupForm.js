function onCreate(aForm, aEntity, aCtx) {
	 save_interval(aForm,aEntity,aCtx)
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aCtx) {
	var list = aCtx.manager.createNativeQuery("select cni.nosologyGroup_id,list(distinct cni.fromidc10Code||'-'||cni.toidc10Code) from NosologyInterval cni where cni.nosologyGroup_id="+aEntity.id+"  group by cni.nosologyGroup_id").getResultList() ;
	if (list.size()>0) {
		aForm.setRangeMkb(list.get(0)[1]) ;
	}
}

/**
 * При сохранении
 */
function onSave(aForm, aEntity, aCtx) {
	 save_interval(aForm,aEntity,aCtx)
}


function save_interval(aForm,aEntity,aCtx) {
	var list = aCtx.manager.createNativeQuery("delete from NosologyInterval cni where cni.nosologyGroup_id="+aEntity.id+" ").executeUpdate() ;
	
	var rangeMkb = aForm.rangeMkb ;
	rangeMkb = rangeMkb.toUpperCase() ;
	var fs1=rangeMkb.split(",") ;
	for (var i=0;i<fs1.length;i++) {
		var fFrom ,fTo ;
			var filt1 = fs1[i].trim() ;
			var fs=filt1.split("-") ;
			if (filt1.length()>0) {
    			if (fs.length>1) {
	    			fFrom=fs[0].trim();fTo=fs[1].trim();
	    		} else {
	    			fFrom=filt1 ;
	    			var st = "Z99.999" ;
	    			fTo = fFrom+st.substring(fFrom.length()) ;
	    			
	    		}
	    		var nosInterval = new Packages.ru.ecom.mis.ejb.domain.contract.NosologyInterval() ;
	    		nosInterval.setNosologyGroup(aEntity) ;
	    		nosInterval.setFromIdc10Code(fFrom) ;
	    		nosInterval.setToIdc10Code(fTo) ;
	    		aCtx.manager.persist(nosInterval) ;
			
		}
	} 
}