function onCreate(aForm, aEntity, aCtx) {
	 save_interval(aForm,aEntity,aCtx)
}

/**
 * При просмотре
 */
function onView(aForm, aEntity, aCtx) {
	var list = aCtx.manager.createNativeQuery("select cni.MedServiceGroup_id,list(distinct cni.fromMedServiceCode||'-'||cni.toMedServiceCode) from MedServiceInterval cni where cni.MedServiceGroup_id="+aEntity.id+"  group by cni.MedServiceGroup_id").getResultList() ;
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
	var list = aCtx.manager.createNativeQuery("delete from MedServiceInterval cni where cni.MedServiceGroup_id="+aEntity.id+" ").executeUpdate() ;
	throw "" +aForm;
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
	    			var st = "Z99.999.999.999" ;
	    			fTo = fFrom+st.substring(st,fFrom.length(),st.length()-fFrom.legth()) ;
	    			throw fTo ;
	    			/*
	    			if (fFrom.length()<2) {
	    				fTo = fFrom+"99.999.999.999" ;
	    			} else if (fFrom.length()<3) {
	    				fTo = fFrom+"9.999.999.999" ;
	    			} else if (fFrom.length()<4) {
	    				fTo = fFrom+".999.999.999" ;
	    			} else if (fFrom.length()<5) {
	    				fTo = fFrom+"999.999.999" ;
	    			} else if (fFrom.length()<6) {
	    				fTo = fFrom+"99.999.999" ;
	    			} else if (fFrom.length()<7) {
	    				fTo = fFrom+"9.999.999" ;
	    			} else if (fFrom.length()<8) {
	    				fTo = fFrom+".999.999" ;
	    			} else if (fFrom.length()<9) {
	    				fTo = fFrom+"999.999" ;
	    			} else if (fFrom.length()<10) {
	    				fTo = fFrom+"99.999" ;
	    			} else if (fFrom.length()<11) {
	    				fTo = fFrom+"9.999" ;
	    			} else if (fFrom.length()<12) {
	    				fTo = fFrom+".999" ;
	    			} else if (fFrom.length()<13) {
	    				fTo = fFrom+"999" ;
	    			} else if (fFrom.length()<14) {
	    				fTo = fFrom+"99" ;
	    			} else if (fFrom.length()<15) {
	    				fTo = fFrom+"9" ;
	    			} else {
	    				fTo = fFrom ;
	    			}*/
	    		}
	    		var nosInterval = new Packages.ru.ecom.mis.ejb.domain.contract.MedServiceInterval() ;
	    		nosInterval.setMedServiceGroup(aEntity) ;
	    		nosInterval.setFromMedServiceCode(fFrom) ;
	    		nosInterval.setToMedServiceCode(fTo) ;
	    		aCtx.manager.persist(nosInterval) ;
			
		}
	} 
}