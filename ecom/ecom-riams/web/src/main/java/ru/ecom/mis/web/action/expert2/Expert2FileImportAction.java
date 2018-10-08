package ru.ecom.mis.web.action.expert2;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jdom.input.SAXBuilder;
import ru.ecom.expert2.service.IExpert2ImportService;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Expert2FileImportAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
    	try {
			IExpert2ImportService expert2service = Injection.find(aRequest).getService(IExpert2ImportService.class);
    		
    		ExportPolicyForm form = aForm!=null?(ExportPolicyForm)aForm:null ;
    		FormFile ffile = form!=null?form.getFile():null;
    		if (ffile==null) {
				return aMapping.findForward("success") ;
			}
			String fileName=ffile.getFileName();
			System.out.println("filename = "+fileName);
			String action = form.getDirName();
			String xmlUploadDir = expert2service.getConfigValue("expert2.input.folder","/opt/jboss-4.0.4.GAi-postgres/server/default/data");
			if (action!=null && !action.equals("")) {
				if (action.equals("createEntry") && (fileName.endsWith(".mp") || fileName.endsWith(".MP"))) {
					saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
					System.out.println("import MP file");
					expert2service.createEntryByFondXml(fileName);
				}
			} else {
				if (fileName.startsWith("N5")&&fileName.endsWith(".xml")) { //Импортируем файл для проставления номеров направления фонда
					System.out.println("start import N5");
					Long entryListId = form.getObjectId();

					System.out.println(expert2service.importN5File(new SAXBuilder().build(ffile.getInputStream()),entryListId));
				} else { //Загружаем ответ фонда


					saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
					expert2service.importFondMPAnswer(fileName) ;
				}

			}

    		return aMapping.findForward("success") ;

    	} catch(Exception e) {
    		System.out.println(e);
    		e.printStackTrace();
    	}
    	return aMapping.findForward("success") ;
    }

	public void saveFile(InputStream aInputStream, String aFileName) throws IOException  {
		 int count ;
		System.out.println("filename="+aFileName);
		 File outputFile = new File(aFileName);
		 if (!outputFile.exists()) {outputFile.createNewFile();}
	        FileOutputStream out = new FileOutputStream(aFileName);
	        byte[] buf = new byte[8192] ;
	        while ( (count=aInputStream.read(buf)) > 0) {
	            out.write(buf, 0, count) ;
	        }
	        out.close() ;
	        aInputStream.close();
	}
}
