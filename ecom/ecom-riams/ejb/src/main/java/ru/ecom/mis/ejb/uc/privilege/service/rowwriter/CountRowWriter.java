package ru.ecom.mis.ejb.uc.privilege.service.rowwriter;

import java.util.List;

import ru.ecom.mis.ejb.uc.privilege.service.IRowWriter;

public class CountRowWriter implements IRowWriter {

	
	public void clearCount() {
		count = 0 ;
	}
	
	public void write(List<String> aRow) {
		count++ ;
	}
	
	public long getCount() {
		return count ;
	}
	
	private long count ;
}
