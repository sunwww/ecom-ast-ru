package ru.ecom.report.excel;

import jxl.Cell;
import jxl.CellType;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.log4j.Logger;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;
import ru.nuzmsh.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Создание отчета
 */
public class ReportEngine {

	/**
	 * Создание отчета по шаблону
	 * 
	 * @param aTemplateFile
	 *            шаблон
	 * @param aOutputFile
	 *            выходной файл
	 * @param aGetter
	 *            данные
	 */
	public void make(File aTemplateFile, File aOutputFile,
					 IValueGetter aGetter) throws ReportEngineMakeException {
		try {
			ReplaceHelper replaceHelper = new ReplaceHelper();
			Workbook template = Workbook.getWorkbook(aTemplateFile);
			WritableWorkbook workbook = Workbook.createWorkbook(aOutputFile,
					template);
			WritableSheet sheet = workbook.getSheet(0);

			final int rowCount = 100;

			for (int row = 1; row < rowCount; row++) {
				Cell cell = sheet.getCell(0, row);

				boolean skip = false;
				if (cell.getType() == CellType.LABEL) {
					Label label = (Label) cell;
					String str = label.getString();
					if (str != null && str.startsWith("$$FOR")) {

						int adding = iterateFor(workbook, sheet, row, str,
								aGetter, replaceHelper);
						row += adding;
						skip = true;
					}
				}
				if (!skip) {
					fillRow(sheet, row, replaceHelper, aGetter);
				}

			}

			workbook.write();
			workbook.close();
		} catch (IOException | SetValueException | BiffException | WriteException e) {
			throw new ReportEngineMakeException("Ошибка создание отчета", e);
		}

	}

	public static String appendRowsToPrintArea(String aPrintArea, int aRows) {
		if (StringUtil.isNullOrEmpty(aPrintArea)){
			return aPrintArea;
		}
		int last = aPrintArea.lastIndexOf('$');
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
			ReplaceHelper aReplaceHelper) throws SetValueException, WriteException {

		int forRow = aRow;
		int valueRow = aRow + 1;
		int styleRow = aRow + 2;
		int endForRow = aRow + 3;

		// 1. взять коллекцию и засунуть ее в Getter
		StringTokenizer st = new StringTokenizer(aStr, " \t:");
		st.nextToken();
		String name = st.nextToken();
		String expression = st.nextToken();
		Collection col = (Collection) aGetter.getValue(expression);
		aGetter.set(name, col);

		// 2. массив со стилями для ячеек из нижней строки

		final int MAX_COLS = getMaxCols(aSheet);

		// 3. масси для expression
		String[] expressions = new String[MAX_COLS];
		for (int i = 0; i < expressions.length; i++) {
			String str = aSheet.getCell(i, valueRow).getContents();

			if (StringUtil.isNullOrEmpty(str)) {
				expressions[i] = null;
			} else {
				expressions[i] = str;
			}

		}
		// 4. спррятать 3 строки FIXME
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

		// 6. выводим значения
		int fromRow = aRow + 4;
		Iterator iterator = col.iterator();
		for (int i = fromRow; i < fromRow + count; i++) {
			aGetter.set(name, iterator.next());
			for (int c = 0; c < MAX_COLS; c++) {
				Label label = new Label(c, i, expressions[c]);
				aSheet.addCell(label);
				fillRow(aSheet, i, aReplaceHelper, aGetter);
			}
		}
		return 4 + col.size();
	}

	private static int getMaxCols(WritableSheet aSheet) {
		int max = aSheet.getColumns() + 1;
		return Math.min(max, 255);
	}

	private static void fillRow(WritableSheet aSheet, int aRow,
			ReplaceHelper aReplaceHelper, IValueGetter aGetter)
			throws SetValueException, WriteException {
		final int colCount = getMaxCols(aSheet);
		for (short col = 0; col < colCount; col++) {
			Cell cell = aSheet.getCell(col, aRow);
			if (cell.getType() == CellType.LABEL) {
				Label labelCell = (Label) cell;
				String str = labelCell.getString();
				Object obj = aReplaceHelper.replaceWithValues(str, aGetter);
				if (obj != null && !StringUtil.isNullOrEmpty(obj.toString())) {
					if (obj instanceof BigDecimal) {
						BigDecimal value = (BigDecimal) obj;
						jxl.write.Number n = new jxl.write.Number(col, aRow,
								value.doubleValue());
						aSheet.addCell(n);
					} else if (obj instanceof Integer) {
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
