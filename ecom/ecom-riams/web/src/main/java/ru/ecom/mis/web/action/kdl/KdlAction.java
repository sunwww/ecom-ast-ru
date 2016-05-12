package ru.ecom.mis.web.action.kdl;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class KdlAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		theService = Injection.find(aRequest).getService(IKdlDiaryService.class) ;
		getFiles();
		return aMapping.findForward("success");
	}
	public void getFiles() {
		theDirName = getKdlDir();
		theArcDirName = getKdlArcDir();
		theErrorDirName = getKdlErrorDir();
		parseDir(theDirName, theArcDirName, true);
	}

	public void parseDir(String dirName, String arcDirName, Boolean aRootDir){
		File dir = new File(dirName);
		theWorkDirName = dir.getName();
		theWorkArcDir = new File(theArcDirName, theWorkDirName);
		theWorkErrorDir = new File(theErrorDirName, theWorkDirName);
		setPermissions(dir);
		parseDir(dir, aRootDir);
		}
	public void parseDir(File aDir, Boolean aRootDir){

		File targetDir = null;

		File[] files=aDir.listFiles();
		if (files == null) {
		} else {
		    for (File file:files) {
		    	if(file.isDirectory()==false){
		    		//printVariable("\nfile",file.getAbsolutePath()) ;
		    		targetDir = theWorkArcDir;
		    		try {
			    		theService.parseFile(file.getAbsolutePath());
			    	} catch (Exception e) {
			    		targetDir = theWorkErrorDir;
			    		printVariable("ParseFileException",e.toString());
			    		
			    	}
		    	moveFileTo(file, targetDir);
		    	} else {
		    			theWorkDirName = file.getName();
		    			theWorkArcDir = new File(theArcDirName, theWorkDirName);
		    			theWorkErrorDir = new File(theErrorDirName, theWorkDirName);
		    			parseDir(file, false);
		    		}
			    }
		}
	    if (!aRootDir)	aDir.delete();
	}
	public String getKdlDir() {
		//EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =theService.getDir("kdl.dir", "/opt/kdl/test");
		System.out.println(workDir) ;
		return workDir ;
	}
	public String getKdlArcDir() {
		//EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir =theService.getDir("kdl.arcdir", "/opt/kdl/testArc");
		System.out.println(workDir) ;
		return workDir ;
	}
	public String getKdlErrorDir() {
		//EjbEcomConfig config = EjbEcomConfig.getInstance() ;
		String workDir = theService.getDir("kdl.errordir", "/opt/kdl/testError");
		System.out.println(workDir) ;
		return workDir ;
	}
	public void setPermissions(File aFile){
		run("chmod -R 777 "+aFile.getAbsolutePath());
	}
	private void moveFileTo(File aSourceFile, File aTargetDir){
		if (!aTargetDir.exists()) {
			aTargetDir.mkdirs();
			setPermissions(aTargetDir);
		}
		File newFile = new File(aTargetDir, aSourceFile.getName());
    	aSourceFile.renameTo(newFile);
	}
	public String run(String Command){
		try{
		Runtime.getRuntime().exec(Command);
		return("0");
		}
		catch (Exception e){
		System.out.println("Error running command: " + Command +
		"\n" + e.getMessage());
		return(e.getMessage());
		}
	} 
	public static void println(String aString){
		System.out.println(aString);
	}	
	public void printVariable(String variable, String value) {
		printVariable(variable, value, "");
	}
	public static void printVariable(String variable, String value, String type )
	{
		try {
			String message ;
			message = variable+": "+(value==null?"":value);
			if (type == "D") {
				message = message + " ("+((java.util.Date) DateFormat.parseDate(value, "yyyy-MM-dd"))+")";
			}
			if (type == "T") {
				message = message + " ("+((java.sql.Time) DateFormat.parseSqlTime(value)+")");
			}

			println(message);
		} catch (Exception e) {}
	}
	IKdlDiaryService theService; 
    String theDirName;
    String theArcDirName;
    String theErrorDirName;
    String theWorkDirName;
    File theWorkArcDir;
    File theWorkErrorDir;

}
