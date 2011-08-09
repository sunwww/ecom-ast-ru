package ru.ecom.ejb.services.file;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Информация о том где создан файл и его идентификатор
 */
class JbossFileInfoLocal {

    private final static Logger LOG = Logger.getLogger(JbossFileInfoLocal.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;



    public JbossFileInfoLocal(long aId
            , String aExportDir, String aUniquePath
    ) {
        theId = aId ;
        theExportDir = aExportDir ;
        theUniquePath = aUniquePath ;
    }



    protected File createFile(String aFilename) {
        if(theFile!=null) throw new IllegalStateException("Файл уже создан") ;
        StringBuilder sb = new StringBuilder(theExportDir);
        sb.append('/') ;
        sb.append(theUniquePath) ;
        sb.append('/') ;
        sb.append(aFilename) ;

        theFile = new File(sb.toString()) ;
        if (CAN_DEBUG) LOG.debug("File to write j: " + theFile.getAbsolutePath());
        new File(theFile.getParent()).mkdirs() ;

        theRelativeFilename = theUniquePath+"/"+aFilename ;
        return theFile  ;
    }

    /** Относительный путь */
    public String getRelativeFilename() { return theRelativeFilename ; }

    /** Относительный путь */
    private String theRelativeFilename ;

    /** Идентификатор */
    public long getId() { return theId ; }

    /** Идентификатор */
    private final long theId ;
    private final String theUniquePath ;
    private final String theExportDir ;
    private File theFile = null ;
}
