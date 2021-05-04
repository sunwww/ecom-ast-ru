package ru.ecom.ejb.services.file;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Информация о том где создан файл и его идентификатор
 */
@Getter
class JbossFileInfoLocal {

    private static final Logger LOG = Logger.getLogger(JbossFileInfoLocal.class) ;
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled() ;



    public JbossFileInfoLocal(long aId
            , String aExportDir, String aUniquePath
    ) {
        id = aId ;
        exportDir = aExportDir ;
        uniquePath = aUniquePath ;
    }



    protected File createFile(String aFilename) {
        if(file!=null) throw new IllegalStateException("Файл уже создан") ;

        String sb = exportDir + '/' + uniquePath + '/' + aFilename;
        file = new File(sb) ;
        if (CAN_DEBUG) LOG.debug("File to write j: " + file.getAbsolutePath());
        new File(file.getParent()).mkdirs() ;

        relativeFilename = uniquePath+"/"+aFilename ;
        return file  ;
    }


    /** Относительный путь */
    private String relativeFilename ;

    /** Идентификатор */
    private final long id ;
    private final String uniquePath ;
    private final String exportDir ;
    private File file = null ;
}
