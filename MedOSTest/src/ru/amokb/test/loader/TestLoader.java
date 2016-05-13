package ru.amokb.test.loader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class TestLoader extends ClassLoader {
	private String path;
	
	TestLoader(String p, ClassLoader prnt) { 
		super(prnt);
		path=p;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			byte[] b=getClassData(path+"\\"+name+".class");
			return defineClass(name, b, 0, b.length);
		} catch(Exception e) {
			return super.loadClass(name);
		}
	}
	
	private byte[] getClassData(String fileName) throws IOException {
		InputStream is=new FileInputStream(fileName);
		long size=new File(fileName).length();
		if(size<=0) {
			is.close();
			return null;
		}
		else if(size>Integer.MAX_VALUE) throw new IOException("Слишком большой размер класса! Too long class size!");
		
		byte[] buffer=new byte[(int) size];
		int offset=0, numRead=0;
		while((offset<buffer.length)&&((numRead=is.read(buffer, offset, buffer.length-offset))>=0)) offset+=numRead;
		
		if(offset<buffer.length) throw new IOException("Файл "+fileName+" считан не полностью! Could not completely read file "+fileName+"!");
		
		is.close();
		
		return buffer;
	}
}
