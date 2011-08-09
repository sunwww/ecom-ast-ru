package ru.ecom.report.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Collection;
import java.util.Iterator;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.SetValueException;
import ru.nuzmsh.util.StringUtil;

/**
 * Создание отчета
 */
public class ReportEnginePoi {


    private final static Logger LOG = Logger.getLogger(ReportEnginePoi.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    /**
     * Создание отчета по шаблону
     * @param aTemplateFile шаблон
     * @param aOutputFile   выходной файл
     * @param aGetter       данные
     * @param aSheet        номер листа
     */
    public void make(File aTemplateFile, File aOutputFile, IValueGetter aGetter, int aSheet) throws ReportEngineMakeException {
        try {
            ReplaceHelper replaceHelper = new ReplaceHelper();
            HSSFWorkbook wb = PoiWorkbookUtil.openWorkbook(aTemplateFile);
            HSSFSheet sheet ;

            sheet = wb.getSheetAt(aSheet) ;
            final int rowCount = 100 ;
            int addedRowCount = 0 ;
            for(int row=1; row<rowCount; row++) {
                HSSFRow sheetRow = sheet.getRow(row);

                if(sheetRow!=null) {
                    String str = PoiCellUtil.getString(sheetRow, 0) ;
                    if(str!=null && str.startsWith("$$FOR")) {
                        int adding = iterateFor(wb, sheet, row, str, aGetter,replaceHelper);
                        row+=adding ;
                        addedRowCount+=adding ;
                    } else {
                        fillRow(sheetRow, replaceHelper, aGetter);
                    }
                }
            }
            // установка области печати
            String printArea = appendRowsToPrintArea(wb.getPrintArea(aSheet),addedRowCount) ;
            if(!StringUtil.isNullOrEmpty(printArea)) {
                try {
                    wb.setPrintArea(aSheet, appendRowsToPrintArea(wb.getPrintArea(aSheet),addedRowCount)) ;
                } catch (Exception e) {
                    LOG.warn("Ошибка при установке области печати: "+printArea,e);
                }
            }

            PoiWorkbookUtil.writeWorkbook(wb, aOutputFile);
        } catch (FileNotFoundException e) {
            throw new ReportEngineMakeException("Ошибка создание отчета",e);
        } catch (IOException e) {
            throw new ReportEngineMakeException("Ошибка создание отчета",e);
        } catch (SetValueException e) {
            throw new ReportEngineMakeException("Ошибка создание отчета",e);
        }

    }

    public static String appendRowsToPrintArea(String aPrintArea, int aRows) {
        if(StringUtil.isNullOrEmpty(aPrintArea)) return aPrintArea ;
        int last = aPrintArea.lastIndexOf("$") ;
        if(last!=-1) {
            String row = aPrintArea.substring(last+1) ;
            int rowInt = Integer.parseInt(row) ;
            rowInt+=aRows ;
            return aPrintArea.substring(0, last) +rowInt ;
        } else {
            return aPrintArea ;
        }
    }
    private int iterateFor(HSSFWorkbook aWorkbook, HSSFSheet aSheet, int aRow, String aStr, IValueGetter aGetter, ReplaceHelper aReplaceHelper) throws SetValueException {
        HSSFRow forRow = aSheet.getRow(aRow);
        HSSFRow valueRow = aSheet.getRow(aRow+1) ;
        HSSFRow styleRow = aSheet.getRow(aRow+2) ;
        HSSFRow endForRow = aSheet.getRow(aRow+3) ;


        // 1. взять коллекцию и засунуть ее в Getter
        StringTokenizer st = new StringTokenizer(aStr, " \t:");
        st.nextToken() ;
        String name = st.nextToken() ;
        String expression = st.nextToken() ;
        Collection col = (Collection) aGetter.getValue(expression) ;
        aGetter.set(name, col) ;
        // 2. массив со стилями для ячеек из нижней строки
        final int MAX_COLS = getMaxCols(valueRow);
        HSSFCellStyle styles[] = new HSSFCellStyle[MAX_COLS];
        for (short i = 0; i < styles.length; i++) {
            HSSFCell cell = styleRow.getCell(i);
            if(cell!=null && cell.getCellStyle()!=null) {
                styles[i] = cell.getCellStyle() ;
            } else {
                styles[i] = null ;
            }
        }
        // 3. масси для expression
        String expressions[] = new String[MAX_COLS];
        for (int i = 0; i < expressions.length; i++) {
            String str = PoiCellUtil.getString(valueRow, i);
            if(StringUtil.isNullOrEmpty(str)) {
                expressions[i] = null ;
            } else {
                expressions[i] = str ;
            }

        }
        // 4. спррятать 3 строки
        forRow.setHeight((short) 1);
        valueRow.setHeight((short) 1);
        styleRow.setHeight((short) 1);
        endForRow.setHeight((short) 1);

        int count = col.size() ;

        // 5. добавлять строки
        if(count>0) aSheet.shiftRows(aRow+4, 200,count) ;

        // 6. выводим значения
        int fromRow = aRow+4 ;
        Iterator iterator = col.iterator();
        for(int i=fromRow; i<fromRow+count; i++) {
            aGetter.set(name, iterator.next());
            HSSFRow row = aSheet.getRow(i) ;
            if(row==null) row = aSheet.createRow(i) ;
            for(int c=0; c<MAX_COLS; c++) {
                PoiCellUtil.setCellValue(row, (short) c, expressions[c],styles[c]) ;
                fillRow(row, aReplaceHelper, aGetter);
            }
        }
        return 4+col.size() ;
    }

    private static int getMaxCols(HSSFRow aRow) {
        int max = aRow.getLastCellNum()+1 ;
        return max > 255 ? 255 : max ;
    }

    private static void fillRow(HSSFRow aRow, ReplaceHelper aReplaceHelper, IValueGetter aGetter) throws SetValueException {
        final int colCount = getMaxCols(aRow) ;
         for(short col=0; col<colCount; col++) {
            HSSFCell cell = aRow.getCell(col) ;
            Object o = PoiCellUtil.getCellValue(cell) ;
            if(o instanceof String) {
                String str = (String) o ;
                Object obj = aReplaceHelper.replaceWithValues(str, aGetter) ;
                if (CAN_DEBUG) LOG.debug(str+"="+obj);

                PoiCellUtil.setCellValue(aRow, col, obj) ;
//                System.out.println("str = " + str);
            }
        }
    }

}
