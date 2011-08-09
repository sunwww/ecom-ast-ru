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
import ru.nuzmsh.util.filesystem.IFileListener;

public class RtfPrintFileDriver implements IPrintFileDriver {

	private String theId ;
	private File theTemplateDir ;
	private String theKey ;
	private File theWorkDir ;
	
	public IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir) {
		RtfPrintFileDriver d = new RtfPrintFileDriver() ;
		d.theId = id ;
		d.theTemplateDir = templateDir ;
		d.theKey = key ;
		d.theWorkDir = new File(theWorkDir) ;
		return d;
	}

	public boolean isRtfReplaceMode() {
		return true ;
	}

	public void buildFile() {
	}
	public void buildFile(boolean aRemovedTempFile) {
	}

	public File getInputFile() {
		return new File(theTemplateDir, theKey+".rtf") ;
	}

	public File getOutputFile() {
		return new File(theWorkDir, getResultFilename()) ;
	}

	public String getResultFilename() {
		return theKey+"-"+theId+".rtf" ;
	}

	public boolean isAccept(File aDir, String aKeyName) {
		return new File(aDir, aKeyName+".rtf").exists() ;
	}


	public void prepare() {
	}

	public String getEncoding() {
		return "Cp1251" ;
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
            }
            String outStr = aReplaceHelper.replaceWithValues(sb.toString(), aValueGetter).toString() ;
            out.println(outStr.replace("\\par","\n\\par")) ;
        } catch (Exception e) {
            throw new RtfPrintException("Ошибка печати",e);
        } finally {
            if(out!=null) out.close() ;
            if(in!=null) try { in.close() ; } catch (Exception e) {
                throw new RtfPrintException("Ошибка закрытия файла: "+e.getMessage(),e) ;
            }
        }
    }
}
