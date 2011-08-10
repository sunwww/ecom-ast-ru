package ru.nuzmsh.util.filesystem;

import java.io.File;
import java.io.FileFilter;

/**
 * @author esinev
 * Date: 29.05.2006
 * Time: 13:52:33
 */
public class FileSystemIterate {

    public void iterate(File aDir, IFileListener aListener, FileFilter aFilter) {
        if(aDir==null) throw new IllegalArgumentException("Нет параметра aDir") ;
        if(aDir.isDirectory()) {
            File[] files = aDir.listFiles(aFilter);
            for (File file : files) {
                iterate(file, aListener, aFilter);
            }
        } else {
            aListener.processFile(aDir);
        }
    }
}
