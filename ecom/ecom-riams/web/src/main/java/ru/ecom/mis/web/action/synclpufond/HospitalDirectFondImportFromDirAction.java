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
		theHospService = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
		final long monitorId = monitorService.createMonitor() ;
        new Thread() {
            public void run() {
            	
            	theHospService.startMonitor(monitorId) ;
            	try {
					getFiles(monitorId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	theHospService.finishMonitor(monitorId) ;
            }
        }.start() ;
		return aMapping.findForward("success");
	}
	public void getFiles(long aMonitorId) throws Exception {
		theDirName = getOrder263InDir();
		theArcDirName = getOrder263ArcDir();
		theErrorDirName = getOrder263ErrorDir();
		System.out.println(theDirName) ;
		parseDir(theDirName, theArcDirName, false,aMonitorId);
	}

	public void parseDir(String dirName, String arcDirName, Boolean aRootDir,long aMonitorId) throws Exception{
		File dir = new File(dirName);
		theWorkDirName = dir.getName();
		theWorkArcDir = new File(theArcDirName, theWorkDirName);
		theWorkErrorDir = new File(theErrorDirName, theWorkDirName);
		setPermissions(dir);
		System.out.println("1 этап") ;
		parseDir(dir, aRootDir, aMonitorId);
	}
	public void parseDir(File aDir, Boolean aRootDir, long aMonitorId) throws Exception{

		File targetDir = null;

		File[] files=aDir.listFiles();
		System.out.println(aDir) ;
		if (files == null) {
		} else {
		    for (File file:files) {
		    	if(file.isDirectory()==false){
		    		System.out.println(file.getAbsolutePath()) ;
		    		targetDir = theWorkArcDir;
		    		System.out.println(targetDir) ;
		    		try {
			    		theHospService.importFileDataFond(aMonitorId,file.getAbsolutePath());
			    	} catch (Exception e) {
			    		e.printStackTrace();
			    		targetDir = theWorkErrorDir;
			    	}
		    		moveFileTo(file, targetDir);
		    	} else {
		    			theWorkDirName = file.getName();
		    			theWorkArcDir = new File(theArcDirName, theWorkDirName);
		    			theWorkErrorDir = new File(theErrorDirName, theWorkDirName);
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
	public String getOrder263InDir() {
		String workDir =theService.getDir("data.dir.order263.in", null);
		return workDir!=null?new StringBuilder().append(workDir).toString():null ;
	}
	public String getOrder263ArcDir() {
		String workDir =theService.getDir("data.dir.order263.arc", null);
		return workDir!=null?new StringBuilder().append(workDir).toString():null ;
	}
	public String getOrder263ErrorDir() {
		String workDir = theService.getDir("data.dir.order263.err", null);
		return workDir!=null?new StringBuilder().append(workDir).toString():null ;
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
		} catch (Exception e){
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
