package ru.ecom.mis.web.action.synclpufond;

import java.io.File;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class HospitalDirectFondImportFromDirAction  extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
    	theService = Injection.find(aRequest).getService(IKdlDiaryService.class) ;
		final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	theHospService.startMonitor(monitorId) ;
            	getFiles(monitorId);
            	theHospService.finishMonitor(monitorId) ;
            }
        }.start() ;
		return aMapping.findForward("success");
	}
	public void getFiles(long aMonitorId) {
		theDirName = getOrder263Dir();
		theArcDirName = getOrder263ArcDir();
		theErrorDirName = getOrder263ErrorDir();
		parseDir(theDirName, theArcDirName, false,aMonitorId);
	}

	public void parseDir(String dirName, String arcDirName, Boolean aRootDir,long aMonitorId){
		File dir = new File(dirName);
		theWorkDirName = dir.getName();
		theWorkArcDir = new File(theArcDirName, theWorkDirName);
		theWorkErrorDir = new File(theErrorDirName, theWorkDirName);
		setPermissions(dir);
		
		parseDir(dir, aRootDir, aMonitorId);
	}
	public void parseDir(File aDir, Boolean aRootDir, long aMonitorId){

		File targetDir = null;

		File[] files=aDir.listFiles();
		if (files == null) {
		} else {
		    for (File file:files) {
		    	if(file.isDirectory()==false){
		    		targetDir = theWorkArcDir;
		    		try {
			    		theHospService.importFileDataFond(aMonitorId,file.getAbsolutePath());
			    	} catch (Exception e) {
			    		targetDir = theWorkErrorDir;
			    	}
		    		moveFileTo(file, targetDir);
		    	} else {
		    			theWorkDirName = file.getName();
		    			theWorkArcDir = new File(theArcDirName, theWorkDirName);
		    			theWorkErrorDir = new File(theErrorDirName, theWorkDirName);
		    			//parseDir(file, false,aMonitorId);
		    		}
			    }
		}
	    if (!aRootDir)	
	    {
	    	try {
	    	//aDir.delete();
	    	}catch (Exception e) 
	    	{}
	    }
	}
	public static String getOutFolderBy263(HttpServletRequest aRequest) throws NamingException {
		IKdlDiaryService service = Injection.find(aRequest).getService(IKdlDiaryService.class) ;
		return service.getDir("data.dir.order263.out", null); 
	}
	public static String deleteSlsInDirectFond(HttpServletRequest aRequest,Long aIdDirectFond) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		if (aIdDirectFond!=null)service.executeUpdateNativeSql("update hospitaldatafond set hospitalmedcase_id=null where id="+aIdDirectFond) ;
		return ""; 
	}
	public String getOrder263Dir() {
		String workDir =theService.getDir("data.dir.order263.in", null);
		return workDir!=null?new StringBuilder().append(workDir).append("/in").toString():null ;
	}
	public String getOrder263ArcDir() {
		String workDir =theService.getDir("data.dir.order263.arc", null);
		return workDir!=null?new StringBuilder().append(workDir).append("/arc").toString():null ;
	}
	public String getOrder263ErrorDir() {
		String workDir = theService.getDir("data.dir.order263.err", null);
		return workDir!=null?new StringBuilder().append(workDir).append("/err").toString():null ;
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
	IKdlDiaryService theService; 
	IHospitalMedCaseService theHospService; 
    String theDirName;
    String theArcDirName;
    String theErrorDirName;
    String theWorkDirName;
    File theWorkArcDir;
    File theWorkErrorDir;

}
