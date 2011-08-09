package ru.ecom.mis.ejb.uc.privilege.service.rowwriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.mis.ejb.uc.privilege.service.ColumnMapping;
import ru.ecom.mis.ejb.uc.privilege.service.IRowWriter;
import ru.nuzmsh.dbf.DbfField;
import ru.nuzmsh.dbf.DbfWriter;

public class DbfRowWriter implements IRowWriter {

	public DbfRowWriter(List<ColumnMapping> aMapping, File aFile, int aRecordCount) {
		theMapping = aMapping ;
		theFile = aFile ;
		theCount = aRecordCount ;
	}
	
	public void open() {
		LinkedList<DbfField> fields = new LinkedList<DbfField>() ;
		for(ColumnMapping map:theMapping) {
			DbfField f = new DbfField(map.getField(), DbfField.CHAR, map.getLength()) ;
			fields.add(f);
		}
		theWriter = new DbfWriter(theCount, fields) ;
		try {
			theWriter.open(theFile) ;
			
		} catch (IOException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	public void write(List<String> aRow) {
		Iterator<ColumnMapping> it = theMapping.iterator() ;
		HashMap<String, Object> values = new HashMap<String, Object>() ; 
		for(String value : aRow) {
			if(it.hasNext()) {
				ColumnMapping map = it.next() ;
				values.put(map.getField(), value);
			} else {
				break; 
			}
		}
		try {
			theWriter.write(values);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		try {
			theWriter.close() ;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private DbfWriter theWriter = null;
	private final int theCount ;
	private final List<ColumnMapping> theMapping ;
	private final File theFile ;
}
