/**
 * Закрыть СПО
 */
function closeSpo(aContext, aVisitId) {
	/*var visit = aContext.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.Visit
		, java.lang.Long.valueOf(aVisitId)) ;
	if(visit.getDateStart()==null) throw "У визита нет даты приема" ;	
	// FIXME последний ли визит в СПО?
	var spo = visit.getParent() ;
	if(spo==null) throw "Визит не присоединен к СПО" ;
	if(spo.getDateFinish()!=null) throw "СПО уже закрыто" ;
	spo.setDateFinish(visit.getDateStart()) ;
	spo.setFinishWorker(visit.getWorkFunctionExecute().getWorker()) ;
	// FIXME диагноз
	return spo.getId();
	*/
}

/**
* Существует ли такой номер стат. карты за данный год?
* return true  - есть
*        false - нет
*/
function StatCardExist(aContext, String aStatCardNumber, int aYear) {
	var ret = false ;
	if (aStatCardNumber!=null && aStatCardNumber!="") {
		var list = aContext.manager.createQuery("from MedCase where statCardNumber=:number and DTYPE='HospitalMedCase'")
			.setParameter("number", aStatCardNumber).getResultList() ;
		if(!list.isEmpty()) {
			for(var i=0; i<list.size(); i++) {
            	Date admissionDate = list.get(i).dateStart;
                if (admissionDate != null) {
                	Calendar calendar = Calendar.getInstance();
                    calendar.setTime(admissionDate);
                    int year = calendar.get(Calendar.YEAR);
                    if (year == aYear) {
                     	ret = true;
                    	break;
                    }
                }
			}
		} else {
			ret = false ;
		}
	}
	return  ret;	
}