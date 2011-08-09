package ru.ecom.report.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class PoiWorkbookUtil {

    public static HSSFWorkbook openWorkbook(File aFile) throws IOException {
        HSSFWorkbook wb ;

        FileInputStream in = new FileInputStream(aFile);
        wb = new HSSFWorkbook(in);
        in.close() ;
        return wb ;
    }

    public static void writeWorkbook(HSSFWorkbook aWorkbook, File aFile) throws IOException {
        FileOutputStream out = new FileOutputStream(aFile);
        aWorkbook.write(out);
        out.close() ;
    }

}
