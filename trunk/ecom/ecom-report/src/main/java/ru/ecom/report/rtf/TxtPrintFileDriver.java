package ru.ecom.report.rtf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;

public class TxtPrintFileDriver implements IPrintFileDriver {

	private String theId ;
	private File theTemplateDir ;
	private String theKey ;
	private File theWorkDir ;
	
	public IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir) {
		TxtPrintFileDriver d = new TxtPrintFileDriver() ;
		d.theId = id ;
		d.theTemplateDir = templateDir ;
		d.theKey = key ;
		d.theWorkDir = new File(theWorkDir) ;
		return d;
	}

	public boolean isRtfReplaceMode() {
		return false ;
	}

	public void buildFile() {
	}
	public void buildFile(boolean aRemovedTempFile) {
	}

	public File getInputFile() {
		return new File(theTemplateDir, theKey+".txt") ;
	}

	public File getOutputFile() {
		return new File(theWorkDir, getResultFilename()) ;
	}

	public String getResultFilename() {
		return theKey+"-"+theId+".txt" ;
	}

	public boolean isAccept(File aDir, String aKeyName) {
		
		return new File(aDir, aKeyName+".txt").exists() ;
	}


	public void prepare() {
	}

	public String getEncoding() {
		return "Cp866" ;
	}
    public void print(ReplaceHelper aReplaceHelper,  IValueGetter aValueGetter) throws RtfPrintException {
    	//File aTemplateFile = getInputFile() ;
    	//File aOutputFile = getOutputFile() ;
    	//String aEncoding = getEncoding() ;
        LineNumberReader in = null ;
        PrintWriter out = null ;
        try {

            in = new LineNumberReader(new InputStreamReader(new FileInputStream(getInputFile()), getEncoding()));
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getOutputFile()), getEncoding()));
            String line ;
            StringBuilder sb = new StringBuilder();
            while ( (line=in.readLine())!=null) {
                sb.append(line) ;
                //System.out.println(line) ;
                String outStr = aReplaceHelper.replaceWithValues(line, aValueGetter).toString() ;
                getMultyLine(outStr, out) ;
               // System.out.println(outStr) ;
               
            }
            //String outStr = aReplaceHelper.replaceWithValues(sb.toString(), aValueGetter).toString() ;
            //out.print(outStr) ;
        } catch (Exception e) {
            throw new RtfPrintException("Ошибка печати",e);
        } finally {
            if(out!=null) out.close() ;
            if(in!=null) try { in.close() ; } catch (Exception e) {
                throw new RtfPrintException("Ошибка закрытия файла: "+e.getMessage(),e) ;
            }
        }
    }
    public void getMultyLine(String aStr, PrintWriter aOut) {
    	while (aStr.length()>77) {
    		aOut.println(aStr.substring(0,76)) ;
    		aStr = aStr.substring(76) ;
    	}
    	aOut.println(aStr) ;
    }
}
