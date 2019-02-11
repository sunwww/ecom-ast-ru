package ru.nuzmsh.util.zip;

import java.io.File;
import java.io.IOException;

public class JarUtil {


	private static void check(File aFile, File aDir) {
		if(aFile==null) throw new IllegalArgumentException("aFile==null") ;
		if(aDir==null) throw new IllegalArgumentException("aDir==null") ;
		if(aFile.isDirectory()) throw new IllegalArgumentException("Параметр aFile должен быть файлом, а не каталогом") ;
		if(!aDir.isDirectory()) throw new IllegalArgumentException("Параметр aDir должен быть каталогом") ;
	}
	
	public static void makeArchive(File aFile, File aDir) {
    	check(aFile, aDir) ;
		sun.tools.jar.Main m = new sun.tools.jar.Main(System.out, System.err, "jar");
        //LOG.info("jar "+"cfM "+zipFile.getAbsolutePath()+ " -C "+xmlFile.getParentFile().getCanonicalPath()+" "+ xmlFile.getName());
        String[] cmd = {"cfM",aFile.getAbsolutePath(),"-C",aDir.getAbsolutePath(), "."} ;
        if(!m.run(cmd)) throw new IllegalStateException("Ошибка создания архива "+aFile.getAbsolutePath() +" [dir="+aDir.getAbsolutePath()+"]");
	}
	
    public static void extract(File aFile, File aDir) throws IOException {
		check(aFile, aDir) ;
		ExtractJar jar = new ExtractJar() ;
		jar.extract(aDir, aFile) ;
    }
	
	  
}
