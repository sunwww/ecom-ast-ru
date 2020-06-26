package ru.ecom.mis.web.action.kdl;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public class KdlAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		theService = Injection.find(aRequest).getService(IKdlDiaryService.class) ;
		getFiles();
		return aMapping.findForward(SUCCESS);
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

		File targetDir;

		File[] files=aDir.listFiles();
		if (files != null) {
		    for (File file:files) {
		    	if(!file.isDirectory()){
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
	    if (!aRootDir) {
	    	try {
	    		aDir.delete();
	    	}catch (Exception e) 
	    	{}
	    }
	}
	public String getKdlDir() {
		String workDir =theService.getDir("kdl.dir", "/opt/kdl/test");
		return workDir ;
	}
	public String getKdlArcDir() {
		String workDir =theService.getDir("kdl.arcdir", "/opt/kdl/testArc");
		return workDir ;
	}
	public String getKdlErrorDir() {
		String workDir = theService.getDir("kdl.errordir", "/opt/kdl/testError");
		return workDir ;  //Ex?
	}
	public void setPermissions(File aFile){
		run("chmod -R 777 "+aFile.getAbsolutePath());
	}
	private void moveFileTo(File aSourceFile, File aTargetDir){
		if (!aTargetDir.exists()) {
			aTargetDir.mkdirs();  //Ex?
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
			String message = variable+": "+(value==null?"":value);
			if (type.equals("D")) {
				message = message + " ("+ DateFormat.parseDate(value, "yyyy-MM-dd") +")";
			}
			if (type.equals("T")) {
				message = message + " ("+(DateFormat.parseSqlTime(value) +")");
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
