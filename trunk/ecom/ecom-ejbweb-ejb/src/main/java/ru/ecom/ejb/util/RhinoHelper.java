package ru.ecom.ejb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import ru.ecom.ejb.services.util.JBossConfigUtil;

public class RhinoHelper {

    private RhinoHelper() {}
    
	public void loadLibraryFiles(Context aContext, Scriptable aScope) {
		try {
			File file = JBossConfigUtil.getDataFile("filltime.js");
			InputStreamReader in = new InputStreamReader(new FileInputStream(file),"utf-8") ;
			try {
				Script script = aContext.compileReader(in, file.getName(), 0, null);
				script.exec(aContext, aScope);
			} finally {
				in.close();
			}		
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public void addSource(Context aContext, Scriptable aScope, String aSource, String aSourceName) {
		Script script = aContext.compileString(aSource, aSourceName, 0, null);
		script.exec(aContext, aScope) ;
	}
	
	public static RhinoHelper getInstance() {
		return new RhinoHelper() ;
	}
	
}
