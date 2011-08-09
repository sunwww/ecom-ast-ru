package ru.ecom.report.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 *  Работа с ячейками
 */
public class PoiCellUtil {

    private final static Log LOG = LogFactory.getLog(PoiCellUtil.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public static Object getCellValue(HSSFCell aCell) {
        if(aCell==null) return null ;
        int type = aCell.getCellType();
        switch (type) {
            case HSSFCell.CELL_TYPE_ERROR:
            case HSSFCell.CELL_TYPE_FORMULA:
            case HSSFCell.CELL_TYPE_BLANK:
                return null;

            case HSSFCell.CELL_TYPE_NUMERIC:
                return aCell.getNumericCellValue();
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return aCell.getBooleanCellValue();
            case HSSFCell.CELL_TYPE_STRING:
                return aCell.getStringCellValue();

        }
        return "unknown: " + type;
    }


    public static Date getDate(HSSFRow aRow, int aCol) {
        HSSFCell cell = aRow.getCell((short) aCol);
        if (cell != null) {
            try {
                return cell.getDateCellValue();
            } catch (Exception e) {
                LOG.warn("While getting date from column "+aCol+": "+e.getMessage(), e);
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getString(HSSFRow aRow, int aCol) {
        HSSFCell cell = aRow.getCell((short) aCol);
        Object obj = getCellValue(cell) ;
        return obj!=null ? obj.toString() : null ;
//        if (cell != null) {
//            return cell.getStringCellValue();
//        } else {
//            return null;
//        }
    }

    public static void setCellValue(HSSFRow aRow, short aColumn, Object aValue) {
        setCellValue(aRow,aColumn, aValue, null) ;
    }

    public static void setCellValue(HSSFRow aRow, short aColumn, Object aValue, HSSFCellStyle aStyle) {

        HSSFCell cell = aRow.getCell(aColumn);

        if (cell == null) cell = aRow.createCell(aColumn);

        if (aStyle != null) {
            cell.setCellStyle(aStyle);
        }
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);

        if (aValue != null) {
            if (aValue instanceof Date) {
                cell.setCellValue((Date) aValue);
            } else if (aValue instanceof Number) {
                Number number = (Number) aValue;
                cell.setCellValue(number.doubleValue());
            } else {
                cell.setCellValue(aValue.toString());
            }
        } else {
//            aRow.removeCell(cell);
            cell.setCellValue((String) null);
        }
    }
}
