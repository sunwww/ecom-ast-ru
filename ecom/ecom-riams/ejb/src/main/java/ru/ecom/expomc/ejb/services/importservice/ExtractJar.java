package ru.ecom.expomc.ejb.services.importservice;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class ExtractJar {

    private final static Log LOG = LogFactory.getLog(ExtractJar.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    public ExtractJar(boolean aSupportsJarDir) {
        theSupportsJarDir = aSupportsJarDir ;
    }

    public ExtractJar() {
        theSupportsJarDir = true ;
    }

    private static final int ADVICE = 100;
    public static final int BUF_SIZE = 2048;
    public static final String JAR_DIR_SUFFIX = "_JARDIR";

    public void extract(File aDirToExtract, File aArchive) throws IOException {

//        if (CAN_TRACE) LOG.trace("aArchive = " + aArchive);
        JarFile jarFile = new JarFile(aArchive, true, JarFile.OPEN_READ);
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            int count = 0;
            while (entries.hasMoreElements()) {
                entries.nextElement();
                count++;
            }

            entries = jarFile.entries();
            int i = 0;
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
//                if (CAN_TRACE) LOG.trace("entry.getName() = " + entry.getName());
                extract(aDirToExtract, entry, jarFile);
            }
        } finally {
            jarFile.close();
        }

    }

    private void createDir(File aDirToExtract, JarEntry aEntry) {
        String name = append(aDirToExtract, aEntry).getAbsolutePath() ;
        if (aEntry.isDirectory()) {
            new File(name).mkdirs();
        } else {
            File dir = new File(name).getParentFile();
            if (dir != null) {
                dir.mkdirs();
            } else {
                int lastSlash = name.lastIndexOf("/");
                if (lastSlash != -1) {
                    String dirname = name.substring(0, lastSlash);
                    new File(dirname).mkdirs();
                }
            }
        }
    }

    private void extract(File aDirToExtract, JarEntry aEntry, JarFile aFile) throws IOException {
        createDir(aDirToExtract, aEntry);
        if(!aEntry.isDirectory()) {
            InputStream in = aFile.getInputStream(aEntry);
            FileOutputStream out = new FileOutputStream(append(aDirToExtract, aEntry.getName()));
            try {
                byte[] buf = new byte[BUF_SIZE];
                int count;
                while ((count = in.read(buf)) > 0) {
                    out.write(buf, 0, count);
                }
            } finally {
                try {
                    in.close();
                } finally {
                    out.close();
                }
            }
        }
        if(theSupportsJarDir && aEntry.getName().endsWith(".jar")) {
            File dir = new File(aDirToExtract,aEntry.getName()+JAR_DIR_SUFFIX);
            File jarFile = new File(aDirToExtract, aEntry.getName()) ;
            extract(dir, jarFile) ;

            //FileUtil.delete(jarFile) ;
        }
    }

    private File append(File aDirToExtract, String aName) {
        File f = new File(aDirToExtract.getAbsolutePath() + "/" + aName);
        return f;
    }

    private File append(File aDirToExtract, JarEntry aEntry) {
        File f = new File(aDirToExtract.getAbsolutePath() + "/" + aEntry.getName());
        return f;
    }

  

    private final boolean theSupportsJarDir ;

}
