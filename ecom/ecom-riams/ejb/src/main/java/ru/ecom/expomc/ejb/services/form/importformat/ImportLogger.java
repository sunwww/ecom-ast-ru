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
    public Writer getImportReportWriter() { return importReportWriter ; }
    public void setImportReportWriter(Writer aImportReportWriter) { importReportWriter = aImportReportWriter ; }

    public void log(String message) {
        Date dt = new Date();
        if (importReportWriter != null) {
            try {
                importReportWriter.write(FORMATLOG.format(dt)+": ");
                for (int i=0; i<inputReportLevel; i++) importReportWriter.write("\t");
                importReportWriter.write(message+"\n");
            } catch (IOException e) {
                LOG.error("can't write import report ");
                LOG.info(message);
            }
        }
    }
    public void inclev() { inputReportLevel++; }
    public void declev() { inputReportLevel--; }


    /** Выходной поток для отчета импорта */
    private Writer importReportWriter = null;
    private int inputReportLevel = 0;


}
