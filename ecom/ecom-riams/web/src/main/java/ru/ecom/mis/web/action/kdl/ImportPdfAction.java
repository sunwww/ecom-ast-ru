package ru.ecom.mis.web.action.kdl;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

public class ImportPdfAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		theService = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		try{
		 //theService.checkPdf();
		 theService.checkXmlFiles();
		} catch (Exception e){}
		return aMapping.findForward("success");
	}
	
	
	IPrescriptionService theService; 
    String theDirName;
    String theArcDirName;
    String theErrorDirName;
    String theWorkDirName;
    File theWorkArcDir;
    File theWorkErrorDir;

}
