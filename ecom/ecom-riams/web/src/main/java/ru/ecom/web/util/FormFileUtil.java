package ru.ecom.web.util;

import org.apache.struts.upload.FormFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class FormFileUtil {

    public static String writeFile(FormFile aFormFile) throws IOException {
        return writeFile(aFormFile.getInputStream()).getAbsolutePath() ;
    }

    public static File writeFile(InputStream aInputStream) throws IOException {
        File file = File.createTempFile("formfile", "web") ;
        int count;
        FileOutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[8192] ;
        while ( (count=aInputStream.read(buf)) > 0) {
            out.write(buf, 0, count) ;
        }
        out.close() ;
        return file ;

    }

}
