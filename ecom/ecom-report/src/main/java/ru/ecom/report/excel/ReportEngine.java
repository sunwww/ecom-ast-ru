package ru.ecom.report.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.StringTokenizer;
import java.util.Collection;
import java.util.Iterator;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;
import ru.nuzmsh.util.StringUtil;

/**
 * Создание отчета
 */
public class ReportEngine {

	private final static Logger LOG = Logger.getLogger(ReportEngine.class);

	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	/**
	 * Создание отчета по шаблону
	 * 
	 * @param aTemplateFile
	 *            шаблон
	 * @param aOutputFile
	 *            выходной файл
	 * @param aGetter
	 *            данные
	 * @param aSheet
	 *            номер листа
	 */
	public void make(File aTemplateFile, File aOutputFile,
			IValueGetter aGetter, int aSheet) throws ReportEngineMakeException {
		try {
			ReplaceHelper replaceHelper = new ReplaceHelper();
			Workbook template = Workbook.getWorkbook(aTemplateFile);
			WritableWorkbook workbook = Workbook.createWorkbook(aOutputFile,
					template);
			WritableSheet sheet = workbook.getSheet(0);

			final int rowCount = 100;
			int addedRowCount = 0;

			for (int row = 1; row < rowCount; row++) {
				// log("row = "+row) ;
				Cell cell = sheet.getCell(0, row);

				boolean skip = false;
				if (cell.getType() == CellType.LABEL) {
					Label label = (Label) cell;
					String str = label.getString();
					// System.out.println(row+" row = "+str);
					if (str != null && str.startsWith("$$FOR")) {
						System.out.println("for=" + str);

						int adding = iterateFor(workbook, sheet, row, str,
								aGetter, replaceHelper);
						row += adding;
						addedRowCount += adding;
						skip = true;
					}
				}
				if (!skip) {
					fillRow(sheet, row, replaceHelper, aGetter);
				}

			}

			// установка области печати
			/*
			 * String printArea =
			 * appendRowsToPrintArea(wb.getPrintArea(aSheet),addedRowCount) ;
			 * if(!StringUtil.isNullOrEmpty(printArea)) { try {
			 * wb.setPrintArea(aSheet,
			 * appendRowsToPrintArea(wb.getPrintArea(aSheet),addedRowCount)) ; }
			 * catch (Exception e) { LOG.warn("Ошибка при установке области
			 * печати: "+printArea,e); } }
			 */

			workbook.write();
			workbook.close();
			// PoiWorkbookUtil.writeWorkbook(wb, aOutputFile);
		} catch (FileNotFoundException e) {
			throw new ReportEngineMakeException("Ошибка создание отчета", e);
		} catch (IOException e) {
			throw new ReportEngineMakeException("Ошибка создание отчета", e);
		} catch (SetValueException e) {
			throw new ReportEngineMakeException("Ошибка создание отчета", e);
		} catch (BiffException e) {
			throw new ReportEngineMakeException("Ошибка создание отчета", e);
		} catch (WriteException e) {
			throw new ReportEngineMakeException("Ошибка создание отчета", e);
		}

	}

	public static String appendRowsToPrintArea(String aPrintArea, int aRows) {
		if (StringUtil.isNullOrEmpty(aPrintArea))
			return aPrintArea;
		int last = aPrintArea.lastIndexOf("$");
		if (last != -1) {
			String row = aPrintArea.substring(last + 1);
			int rowInt = Integer.parseInt(row);
			rowInt += aRows;
			return aPrintArea.substring(0, last) + rowInt;
		} else {
			return aPrintArea;
		}
	}

	private int iterateFor(WritableWorkbook aWorkbook, WritableSheet aSheet,
			int aRow, String aStr, IValueGetter aGetter,
			ReplaceHelper aReplaceHelper) throws SetValueException,
			RowsExceededException, WriteException {

		int forRow = aRow;
		int valueRow = aRow + 1;
		int styleRow = aRow + 2;
		int endForRow = aRow + 3;
		// HSSFRow forRow = aSheet.getRow(aRow);
		// HSSFRow valueRow = aSheet.getRow(aRow+1) ;
		// HSSFRow styleRow = aSheet.getRow(aRow+2) ;
		// HSSFRow endForRow = aSheet.getRow(aRow+3) ;

		// 1. взять коллекцию и засунуть ее в Getter
		StringTokenizer st = new StringTokenizer(aStr, " \t:");
		st.nextToken();
		String name = st.nextToken();
		String expression = st.nextToken();
		Collection col = (Collection) aGetter.getValue(expression);
		aGetter.set(name, col);

		// 2. массив со стилями для ячеек из нижней строки

		final int MAX_COLS = getMaxCols(valueRow, aSheet);
		HSSFCellStyle styles[] = new HSSFCellStyle[MAX_COLS];
		for (short i = 0; i < styles.length; i++) {
			Cell cell = aSheet.getCell(i, styleRow);
			// FIXME STYLES
			// if(cell!=null && cell.getCellStyle()!=null) {
			// styles[i] = cell.getCellStyle() ;
			// } else {
			// styles[i] = null ;
			// }
		}
		// 3. масси для expression
		String expressions[] = new String[MAX_COLS];
		for (int i = 0; i < expressions.length; i++) {
			String str = aSheet.getCell(i, valueRow).getContents(); // PoiCellUtil.getString(valueRow,
																	// i);
			if (StringUtil.isNullOrEmpty(str)) {
				expressions[i] = null;
			} else {
				expressions[i] = str;
			}

		}
		// 4. спррятать 3 строки FIXME
		// forRow.setHeight((short) 1);
		// valueRow.setHeight((short) 1);
		// styleRow.setHeight((short) 1);
		// endForRow.setHeight((short) 1);
		try {
			aSheet.setRowView(forRow, true);
			aSheet.setRowView(valueRow, true);
			aSheet.setRowView(styleRow, true);
			aSheet.setRowView(endForRow, true);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = col.size();

		// 5. добавлять строки
		for (int i = 0; i < count; i++) {
			aSheet.insertRow(aRow + 4);
		}
		// if(count>0) aSheet.shiftRows(aRow+4, 200,count) ;

		// 6. выводим значения
		int fromRow = aRow + 4;
		Iterator iterator = col.iterator();
		for (int i = fromRow; i < fromRow + count; i++) {
			aGetter.set(name, iterator.next());
			// HSSFRow row = aSheet.getRow(i) ;
			// if(row==null) row = aSheet.createRow(i) ;
			for (int c = 0; c < MAX_COLS; c++) {
				Label label = new Label(c, i, expressions[c]);
				aSheet.addCell(label);
				// PoiCellUtil.setCellValue(row, (short) c,
				// expressions[c],styles[c]) ; FIXME
				fillRow(aSheet, i, aReplaceHelper, aGetter);
			}
		}
		return 4 + col.size();
	}

	private static int getMaxCols(int aRow, WritableSheet aSheet) {
		int max = aSheet.getColumns() + 1;
		return max > 255 ? 255 : max;
	}

	private static void log(String aLog) {
		System.out.println(aLog);
	}

	private static void fillRow(WritableSheet aSheet, int aRow,
			ReplaceHelper aReplaceHelper, IValueGetter aGetter)
			throws SetValueException, RowsExceededException, WriteException {
		final int colCount = getMaxCols(aRow, aSheet);
		// log("fillRow [aRow="+aRow+"]") ;
		for (short col = 0; col < colCount; col++) {
			Cell cell = aSheet.getCell(col, aRow); // HSSFCell cell =
													// aRow.getCell(col) ;
			// log(" cell="+cell) ;
			if (cell.getType() == CellType.LABEL) {
				Label labelCell = (Label) cell;
				String str = labelCell.getString();
				Object obj = aReplaceHelper.replaceWithValues(str, aGetter);
				// log(" "+col+" = "+str+" -> "+obj) ;
				if (obj != null && !StringUtil.isNullOrEmpty(obj.toString())) {
					//log(obj.getClass().getName() + "=" + obj);
					if (obj instanceof BigDecimal) {
						BigDecimal value = (BigDecimal) obj;
						jxl.write.Number n = new jxl.write.Number(col, aRow,
								value.doubleValue());
						aSheet.addCell(n);
					} else if (obj instanceof Integer) {
						//log("number!!!!!!!") ;
						Integer value = (Integer) obj;
						jxl.write.Number n = new jxl.write.Number(col, aRow,
								value.doubleValue());
						aSheet.addCell(n);
					} else {
						
						labelCell.setString(obj.toString());
					}
				}
			}
		}
	}

}
