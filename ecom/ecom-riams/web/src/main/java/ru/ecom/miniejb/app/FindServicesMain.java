package ru.ecom.miniejb.app;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class FindServicesMain {

    private void find(File aRootdir, File aDir, List<String> aList) {
        FileFilter filter = new ServiceClassFileFilter() ;
        File[] files = aDir.listFiles() ;
        for (File file : files) {
            if(file.isDirectory()) find(aRootdir, file, aList) ;
            else {
                if(filter.accept(file)) {
                    aList.add(createClassName(aRootdir, file)) ;
                }
            }
        }

    }

    private String createClassName(File aRootdir, File aFile) {
        String rootDirname = aRootdir.getAbsolutePath() ;
        String filename = aFile.getAbsolutePath();
        String classname = filename.substring(rootDirname.length()+1) ;
        classname = classname.replace('/','.') ;
        classname = classname.replace('\\','.') ;
        classname = classname.substring(0, classname.length()- ".class".length()) ;
        System.out.println("registering "+classname+"...");
        return classname ;
    }


    public void find(String aDir, String aFilename) throws IOException {
        LinkedList<String> list = new LinkedList<String>();
        File dir = new File(aDir);
        if(!dir.isDirectory()) throw new IllegalArgumentException(aDir+" не каталог") ;
        find(dir, dir, list) ;
        PrintWriter out = new PrintWriter(new FileWriter(aFilename)) ;
        for (String s : list) {
            out.println(s) ;
        }
        out.close() ;
    }

    public static void main(String[] args) throws IOException {
        if(args.length!=2) throw new IllegalArgumentException
                ("Нет параметров:\n 1. каталог, где искать классы\n 2. Куда сохранять файл с сервисами") ;
        new FindServicesMain().find(args[0], args[1]);
    }

    private static class ServiceClassFileFilter implements FileFilter {
        public boolean accept(File aFile) {
            String name = aFile.getName() ;
            return name.endsWith("ServiceBean.class") ;
        }
    }
}
