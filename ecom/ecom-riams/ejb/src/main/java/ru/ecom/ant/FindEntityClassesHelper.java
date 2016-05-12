package ru.ecom.ant;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;

public class FindEntityClassesHelper {

	private File theRootDir = null ;
	public Set<String> findClasses(String aDir) {
		TreeSet<String> ret = new TreeSet<String>() ;
		File file = new File(aDir); 
		theRootDir = file ;
		addToSet(file, ret) ;
		return ret ;
	}

	private void addToSet(File aFile, TreeSet<String> aSet) {
		if(aFile==null) return ;
		if(aFile.isDirectory()) {
			File[] files = aFile.listFiles() ;
			for(File file : files) {
				addToSet(file, aSet);
			}
		} else {
			String classname = getClassname(aFile);
			if(classname!=null) {
				aSet.add(classname);
			}
		}
	}

	private String getClassname(File file) {
		String filename = file.getAbsolutePath() ;
		if(filename.endsWith(".class")) {
			String rootDirname = theRootDir.getAbsolutePath() ;
			String name = filename.substring(rootDirname.length()+1);
			name = name.replace(File.separatorChar, '.');
			name = name.substring(0, name.length()-6);
			return isEntityClass(name) ? name : null ;
		}
		return null;
	}
	
	private boolean isEntityClass(String aClassname) {
		try {
			Class clazz = getClass().forName(aClassname);
			boolean ret = clazz.isAnnotationPresent(Entity.class) ;
			if(ret) {
				trace("Adding "+aClassname);
			}
			return ret ;
		} catch (Exception e) {
			System.out.println("Error loading class: "+aClassname);
			return false ;
			//throw new IllegalStateException(e.getMessage(),e);
		}
	}
	
	private void trace(Object aObj) {
		System.out.println(aObj);
	}
	
	public static void main(String args[]) {
		FindEntityClassesHelper h = new FindEntityClassesHelper() ;
		Set<String> classes = h.findClasses("./target/classes");
		for(String cl : classes) {
			System.out.println(cl);
		}
	}
}
