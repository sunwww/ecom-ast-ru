package ru.ecom.ejb.services.file;

/**
 * Получение пути к файлу, записанному на стороне JBoss'a
 */
public interface IJbossGetFileService {
    /** Получение относительного пути к файлу */
    public String getRelativeFilename(long aFileId) ;

    // IKO 070404 +++
    /** Получение абсолютного  пути к файлу */
    public String getAbsoluteFilename(long aFileId) ;

    // IKO 070404 ===


    /** Регистрация нового файла */
    public long register() ;
}
