/**
* @author stkacheva
*/
function onPreCreate(aForm, aCtx) {
	var lpu_id = aForm.getLpu() ;
	var serviceStream_id = aForm.getServiceStream() ;
	//java.lang.System.out.println("service stream id = "+serviceStream_id) ;
	var bedType_id = aForm.getBedType() ;
	var bedSubType_id = aForm.getBedSubType() ;
	var reductionType_id = aForm.getReductionType() ;
	var dateStart = aForm.getDateStart();
    var dateStartD = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(dateStart);
	var dateFinish = aForm.getDateFinish();
	var list ;
	var noFood = "noFood='1'" ;
	var rehab = "isRehab='1'" ;
	if (aForm.getNoFood()==null || aForm.getNoFood()==false) {
		noFood = "(noFood is null or noFood='0')"
	}
	if (aForm.getIsRehab()==null || aForm.getIsRehab()==false) {
		rehab = "(isRehab is null or isRehab='0')"
	}
	if (dateFinish!=null && !dateFinish.equals("")) {		
	    var dateFinishD = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(dateFinish);
        list = aCtx.manager.createQuery("from BedFund where"
        	+" lpu_id = :lpu"
        	+" and serviceStream_id = :serviceStream"
        	+" and bedType_id = :bedType"
        	+" and bedSubType_id = :bedSubType"
        	+" and reductionType.code = '1'"
        	+" and "+noFood
        	+" and "+rehab
        	+" and ((:dateStart between dateStart and coalesce(dateFinish,:dateStart)) or (:dateFinish between dateStart and coalesce(dateFinish,:dateFinish)) or (dateStart between :dateStart and :dateFinish))"
        	)
        	.setParameter("lpu",lpu_id)
        	.setParameter("serviceStream",serviceStream_id)
        	.setParameter("bedType",bedType_id)
        	.setParameter("bedSubType",bedSubType_id)
        	.setParameter("dateStart",dateStartD)
        	.setParameter("dateFinish",dateFinishD)
        	.getResultList() ;
	} else {
        list = aCtx.manager.createQuery("from BedFund where lpu_id = :lpu"
        	+" and serviceStream_id = :serviceStream"
        	+" and bedType_id = :bedType"
        	+" and bedSubType_id = :bedSubType "
        	+" and "+noFood
        	+" and "+rehab
        	+" and reductionType.code = '1'"
        	+" and :dateStart <=  coalesce(dateFinish,CURRENT_DATE)"
        	)
        	.setParameter("bedType",bedType_id)
        	.setParameter("bedSubType",bedSubType_id)
        	.setParameter("dateStart",dateStartD)
           	.setParameter("lpu",lpu_id)
        	.setParameter("serviceStream",serviceStream_id)
        	.getResultList() ;
		
	}
	errorThrow(list) ;
	
	var listVBST=aCtx.manager.createQuery("from VocBedSubType where id=:id")
		.setParameter("id",bedSubType_id)
		.getResultList() ;
	var code=listVBST.size()>0?listVBST.get(0).code:"" ;
	if (code!="" && +code==2) {
		aForm.addCaseDuration=true ;
	}
	
}

function onPreSave(aForm,aEntity , aCtx) {
	var lpu_id = aForm.getLpu() ;
	var serviceStream_id = aForm.getServiceStream() ;
	//Packages.java.lang.System.out.println("service stream id = "+serviceStream_id) ;
	var bedType_id = aForm.getBedType() ;
	var bedSubType_id = aForm.getBedSubType() ;
	var reductionType_id = aForm.getReductionType() ;
	var dateStart = aForm.getDateStart();
    var dateStartD = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(dateStart);
	var dateFinish = aForm.getDateFinish();
	var thisid = aForm.getId() ;
	var list ;
	var noFood = "noFood='1'" ; var rehab = "isRehab='1'" ;
	if (aForm.getNoFood()==null || aForm.getNoFood()==false) {
		noFood = "(noFood is null or noFood='0')"
	}
	if (aForm.getIsRehab()==null || aForm.getIsRehab()==false) {
		rehab = "(isRehab is null or isRehab='0')"
	}
	if (dateFinish!=null && !dateFinish.equals("")) {		
	    var dateFinishD = Packages.ru.nuzmsh.util.format.DateFormat.parseDate(dateFinish);
        list = aCtx.manager.createQuery("from BedFund where lpu_id = :lpu"
        	+" and serviceStream_id = :serviceStream"
        	+" and bedType_id = :bedType"
        	+" and bedSubType_id = :bedSubType"
        	+" and reductionType.code = '1'"
        	+" and "+noFood
        	+" and "+rehab
        	+" and ("
        	+"(:dateStart between dateStart and coalesce(dateFinish,:dateStart))"
        	+" or "
        	+"(:dateFinish between dateStart and coalesce(dateFinish,:dateFinish))"
        	+" or "
        	+"(dateStart between :dateStart and :dateFinish)"
        	+")"
        	+" and id != '"+thisid+"'"
        	)
        	.setParameter("lpu",lpu_id)
        	.setParameter("serviceStream",serviceStream_id)
        	.setParameter("bedType",bedType_id)
        	.setParameter("bedSubType",bedSubType_id)
        	.setParameter("dateStart",dateStartD)
        	.setParameter("dateFinish",dateFinishD)
        	.getResultList() ;
	} else {
        list = aCtx.manager.createQuery("from BedFund where lpu_id = :lpu"
        	+" and serviceStream_id = :serviceStream"
        	+" and bedType_id = :bedType"
        	+" and bedSubType_id = :bedSubType"
        	+" and "+noFood
        	+" and "+rehab
        	+" and reductionType.code = '1'"
        	+" and :dateStart <=  coalesce(dateFinish,:dateStart)"
        	+" and id != '"+thisid+"'"
        	)
        	.setParameter("bedType",bedType_id)
        	.setParameter("bedSubType",bedSubType_id)
        	.setParameter("dateStart",dateStartD)
           	.setParameter("lpu",lpu_id)
        	.setParameter("serviceStream",serviceStream_id)
        	.getResultList() ;
	}
	errorThrow(list) ;
	var listVBST=aCtx.manager.createQuery("from VocBedSubType where id=:id")
		.setParameter("id",bedSubType_id)
		.getResultList() ;
	var code=listVBST.size()>0?listVBST.get(0).code:"" ;
	if (code!="" && +code==2) {
		aForm.addCaseDuration=true ;
	}
	
}

function errorThrow(aList) {
	if (aList.size()>0) {
		var error ="";
		for(var i=0; i<aList.size(); i++) {
			var bedFund = aList.get(i) ;
			var period = Packages.ru.ecom.ejb.util.DurationUtil.getDurationFull(bedFund.dateStart, bedFund.dateFinish)
			error = error+" ИД = "+bedFund.id+" ПЕРИОД: "+period + "; " ;
		}
		throw "Недопустимое пересечение периодов!!!" + error ;
	}
}