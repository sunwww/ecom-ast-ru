package ru.ecom.mis.ejb.uc.privilege.service.rowwriter;

import java.util.List;

import ru.ecom.mis.ejb.uc.privilege.service.IRowWriter;

public class CountRowWriter implements IRowWriter {

	
	public void clearCount() {
		theCount = 0 ;
	}
	
	public void write(List<String> aRow) {
		theCount++ ;
	}
	
	public long getCount() {
		return theCount ;
	}
	
	private long theCount ;
}
