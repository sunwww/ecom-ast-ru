package ru.ecom.web.util;

import org.apache.struts.upload.FormFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Загрузка файла
 */
public class FileUploadUtil {

    public static String writeFile(FormFile aFormFile) throws IOException {
        File tempFile = File.createTempFile("import","dbf") ;
        writeFile(aFormFile.getInputStream(), tempFile.getAbsolutePath());
        return tempFile.getAbsolutePath() ;
    }

    private static void writeFile(InputStream aInputStream, String aFilename) {
        int count ;
        try (FileOutputStream out = new FileOutputStream(aFilename)){
            byte[] buf = new byte[8192] ;
            while ( (count=aInputStream.read(buf)) > 0) {
                out.write(buf, 0, count) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}