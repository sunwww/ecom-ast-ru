package ru.ecom.mis.web.action.expert2;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jdom.input.SAXBuilder;
import ru.ecom.expert2.service.IExpert2ImportService;
import ru.ecom.expert2.web.form.ImportFileForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Expert2FileImportAction extends BaseAction {
	private static final Logger log = Logger.getLogger(Expert2FileImportAction.class);

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
    	try {
			IExpert2ImportService expert2service = Injection.find(aRequest).getService(IExpert2ImportService.class);

			ImportFileForm form = aForm!=null?(ImportFileForm)aForm:null ;
    		FormFile ffile = form!=null?form.getFile():null;
    		if (ffile==null) {
				return aMapping.findForward("success") ;
			}
			String fileName=ffile.getFileName();
			log.info("filename = "+fileName);
			String action = form.getDirName();
			String result ;
			String xmlUploadDir = expert2service.getConfigValue("expert2.input.folder","/opt/jboss-4.0.4.GAi-postgres/server/default/data");
			switch (action) {
				case "createEntry":
					if (fileName.toUpperCase().endsWith(".MP")) {
						saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
						log.info("Создаем заполнение из файла");
						result = expert2service.createEntryByFondXml(fileName);
					} else {
						result="Создания заполнения возможно только из МР пакета!";
					}
					break;
				case "importN5":
					if (fileName.startsWith("N5") && fileName.toUpperCase().endsWith(".XML")) { //Импортируем файл для проставления номеров направления фонда
						log.info("start import N5");
						Long entryListId = form.getObjectId();
						result = expert2service.importN5File(new SAXBuilder().build(ffile.getInputStream()),entryListId);
					} else {
						result="Неверное имя файла для импорта N5 (xml файл должен начинаться с N5)!";
					}
					break;
				case "importFlk":
					saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
					result = expert2service.importFlkAnswer(fileName);
					break;
				case "importDefect":
					saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
					result = expert2service.importFondMPAnswer(fileName) ;
					break;
					default:
						result="Я не понимаю, чего вы от меня хотите!!!"+action;
			}

			log.info(result);
			aRequest.setAttribute("importResult",result);
    		return aMapping.findForward("success") ;

    	} catch(Exception e) {
    		log.error(e);
    		e.printStackTrace();
    	}
    	return aMapping.findForward("success") ;
    }

	public void saveFile(InputStream aInputStream, String aFileName) throws IOException  {
		int count ;
		log.info("filename="+aFileName);
		File outputFile = new File(aFileName);
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		try (FileOutputStream out = new FileOutputStream(aFileName)) {
			byte[] buf = new byte[8192] ;
			while ( (count=aInputStream.read(buf)) > 0) {
				out.write(buf, 0, count) ;
			}
			out.close() ;
			aInputStream.close();
		} catch (Exception e) {

		}

	}
}
