package ru.ecom.expomc.ejb.services.form.importformat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Отчет импортирования
 * @author ikouzmin 16.03.2007 12:05:24
 */
public class ImportLogger {
    private static final Logger LOG = Logger.getLogger(ImportLogger.class) ;

    private SimpleDateFormat FORMATLOG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");


    /** Выходной поток для отчета импорта */
    public Writer getImportReportWriter() { return theImportReportWriter ; }
    public void setImportReportWriter(Writer aImportReportWriter) { theImportReportWriter = aImportReportWriter ; }

    public void log(String message) {
        Date dt = new Date();
        if (theImportReportWriter != null) {
            try {
                theImportReportWriter.write(FORMATLOG.format(dt)+": ");
                for (int i=0; i<theInputReportLevel; i++) theImportReportWriter.write("\t");
                theImportReportWriter.write(message+"\n");
            } catch (IOException e) {
                LOG.error("can't write import report ");
                LOG.info(message);
            }
        }
    }
    public void inclev() { theInputReportLevel++; }
    public void declev() { theInputReportLevel--; }


    /** Выходной поток для отчета импорта */
    private Writer theImportReportWriter = null;
    private int theInputReportLevel = 0;


}
