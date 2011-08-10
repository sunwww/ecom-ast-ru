package ru.nuzmsh.util.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 *
 */
public class SourcesFileFilter implements FileFilter {
    public SourcesFileFilter () {
        theExludeFileTypes.add(".iml") ;
        theExludeFileTypes.add(".ipr") ;
        theExludeFileTypes.add(".iws") ;
        theExludeFileTypes.add(".png") ;
        theExludeFileTypes.add(".jpg") ;
        theExludeFileTypes.add(".gif") ;
        theExludeFileTypes.add(".awf") ;
    }

    public boolean accept(File aFile) {
        // .svn
        boolean ret = true ;
        String name = aFile.getName() ;
        if(aFile.isDirectory()) {
            ret = !name.equals(".svn") ;
        } else {
            for (String type : theExludeFileTypes) {
                if(name.endsWith(type)) {
                    ret = false ;
                    break ;
                }
            }
        }
        return ret ;
    }


    private ArrayList<String> theExludeFileTypes = new ArrayList<String>();
}
