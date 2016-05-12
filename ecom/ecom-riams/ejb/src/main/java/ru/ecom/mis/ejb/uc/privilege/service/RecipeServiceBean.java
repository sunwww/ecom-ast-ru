package ru.ecom.mis.ejb.uc.privilege.service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import ru.ecom.mis.ejb.uc.privilege.service.cellfunctions.SplitFunction;
import ru.ecom.mis.ejb.uc.privilege.service.rowwriter.CountRowWriter;
import ru.ecom.mis.ejb.uc.privilege.service.rowwriter.DbfRowWriter;
import ru.nuzmsh.util.StringUtil;

public class RecipeServiceBean {

	public void convertExcelToDbf(GroupExcelConfig aConfig, File aExcelFile, IRowWriter aRowWriter) {
		try {
			Workbook workbook = Workbook.getWorkbook(aExcelFile);
			Sheet sheet = workbook.getSheet(0);
			int rowsCount = sheet.getRows();
			LinkedList<String> group = new LinkedList<String>() ;
			LinkedList<String> detail = new LinkedList<String>() ;
			for(int row=0;row<rowsCount;row++) {
				if(isMatched(row, sheet, aConfig.getGroupColumns())) {
					group.clear() ;
					addMapping(row, sheet, group, aConfig.getGroupColumns());
					//System.out.println(group) ;
					continue ;
				}
				
				boolean detailOk = isMatched(row, sheet, aConfig.getDetailsColumns()) ;
//				System.out.println("detail ok = "+detailOk) ;
//				System.out.println("  cols = "+aConfig.getDetailsColumns());
				if(!group.isEmpty() && detailOk) {
					addMapping(row, sheet, detail, aConfig.getDetailsColumns());
					append(detail, group) ;
					//System.out.println("   "+detail) ;
					aRowWriter.write(detail);
					detail.clear() ;
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e) ;
		}
	}
	
	private void append(List aContainer, List aAppendable) {
		for(Object o : aAppendable) {
			aContainer.add(o);
		}
	}
	private void addMapping(int aRow, Sheet aSheet, List<String> aList, List<ColumnMapping> aMapping) {
		for(ColumnMapping map: aMapping) {
			String value = aSheet.getCell(map.getColumn(), aRow).getContents() ;
			if(map.getCellFunction()!=null) {
				value = map.getCellFunction().invoke(value);
			}
			aList.add(value) ;
		}
	}
	
	private boolean isMatched(int aRow, Sheet aSheet, List<ColumnMapping> aMapping) {
		boolean groupOk = false ;
		boolean mathedPassed = false ;
		for(ColumnMapping map : aMapping) {
			if(!StringUtil.isNullOrEmpty(map.getMatched())) {
				mathedPassed = true ;
				String value = aSheet.getCell(map.getColumn(), aRow).getContents() ;
				
				if(value!=null && value.startsWith(map.getMatched())) {
					groupOk = true ;
					break ;
				}
			}
		}
		if(!mathedPassed) groupOk = true ;
		
		boolean requiredOk = true ;
		for(ColumnMapping map : aMapping) {
			if(map.getRequired()) {
				if(isEmpty(aSheet, map.getColumn(), aRow)) {
					requiredOk = false ;
					break ;
				}
			}
		}	
		
		return groupOk && requiredOk ;
		
	}
	
	private boolean isEmpty(Sheet aSheet, int aCol, int aRow) {
		return StringUtil.isNullOrEmpty(aSheet.getCell(aCol, aRow).getContents());
	}
	private void write(LinkedList<String> aGroup, int i, Sheet sheet) {
		
		
	}

	
	public static void main(String args[]) {
		GroupExcelConfig c = new GroupExcelConfig() ;
		c.getGroupColumns().add(ColumnMapping.createGroupColumn(0, true, "SKIP_1", "Идент", 10));
		c.getGroupColumns().add(ColumnMapping.createGroupColumn(3, true, "DOC_ID", null, 30));
		c.getGroupColumns().add(ColumnMapping.createGroupColumn(6, true, "DOC_F", null, new SplitFunction(" \t",1),40));
		c.getGroupColumns().add(ColumnMapping.createGroupColumn(6, false, "DOC_I", null, new SplitFunction(" \t",2),40));
		c.getGroupColumns().add(ColumnMapping.createGroupColumn(6, false, "DOC_O", null, new SplitFunction(" \t",3),40));
		
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(1, true, "REC_ID",40)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(2, "REC_DATE",10)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(3, "PAT_F", new SplitFunction(" \t",1),40)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(3, "PAT_I", new SplitFunction(" \t",2),40)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(3, "PAT_O", new SplitFunction(" \t",3),40)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(4, "PAT_SNILS",20)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(5, "PRIVILEG", new SplitFunction("/",1),6)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(5, "FINANCE", new SplitFunction("/",2),1)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(6, "DRUGCODE",10)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(7, "DRUGNAME",255)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(8, "DRUGFORM",10)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(9, "DOZE",255)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(10, "DCOUNT",20)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(11, "IDC10",10)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(12, "PAY",4)) ;
		c.getDetailsColumns().add(ColumnMapping.createDetailsColumn(13, "KEK",1)) ;
		
		File file = new File("/home/esinev/download/foms2/dloReport2.xls") ;
		CountRowWriter countRow = new CountRowWriter() ;
		RecipeServiceBean service = new RecipeServiceBean() ;
		service.convertExcelToDbf(c, file, countRow) ;
		System.out.println("count = "+countRow.getCount());
		
		LinkedList<ColumnMapping> all = new LinkedList<ColumnMapping>() ;
		service.append(all, c.getDetailsColumns()) ;
		service.append(all, c.getGroupColumns()) ;
		
		DbfRowWriter dbfRowWriter = new DbfRowWriter(all
					, new File("out.dbf")
			, (int) countRow.getCount()) ;
		dbfRowWriter.open() ;
		service.convertExcelToDbf(c, file, dbfRowWriter) ;
		dbfRowWriter.close();
	}
	
}
