package ru.ecom.mis.web.action.medcase;

import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;

public class SSLTableDecorator implements ITableDecorator {

	public String getId(Object aRow) {
		HospitalMedCaseForm form = (HospitalMedCaseForm) aRow ;
		return String.valueOf(form.getId());
	}

	public String getRowCssClass(Object aRow) {
		StringBuffer style = new StringBuffer();
		HospitalMedCaseForm form = (HospitalMedCaseForm) aRow ;
		if(form.getIsDeniedHospitalizating()) style.append("deniedHospitalizating") ;
		//if (Long.valueOf(33545)==form.getId()) style.append( " current") ; 
		return style.toString() ;
		//return " current" ;
	}
	/*
    PriemJournalTableRow  row = (PriemJournalTableRow) aRow;
    StringBuffer style = new StringBuffer();
    if(row.isDeniedHospitalizating()) {
        style.append("deniedHospitalizating") ;
    }
    if(aSlsId==row.getSlsId()) {
        style.append( " current") ;
    }
    return  style.toString() ;
*/
}
