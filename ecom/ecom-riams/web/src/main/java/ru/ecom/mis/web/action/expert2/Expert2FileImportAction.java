package ru.ecom.mis.web.action.expert2;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
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
	private static final Logger LOG = Logger.getLogger(Expert2FileImportAction.class);

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse) {
    	try {
			IExpert2ImportService expert2service = Injection.find(aRequest).getService(IExpert2ImportService.class);
			ImportFileForm form = (ImportFileForm)aForm;
    		FormFile ffile = form.getFile();
    		if (ffile==null) {
				return aMapping.findForward(SUCCESS) ;
			}
			String fileName=ffile.getFileName();
			LOG.info("filename = "+fileName);
			String action = form.getDirName();
			String xmlUploadDir = expert2service.getConfigValue("expert2.input.folder","/opt/jboss-4.0.4.GAi/server/default/data");
			Long entryListId = form.getObjectId();
			IRemoteMonitorService monitorService = (IRemoteMonitorService) Injection.find(aRequest).getService("MonitorService") ;
			final long monitorId = monitorService.createMonitor();

			switch (action) {
				case "createEntry":
					if (fileName.startsWith("ELMED")) { //импорт файлов с элмеда
						saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
						new Thread() {
							public void run() {
								try {
									expert2service.importElmed(monitorId,fileName);
								} catch (Exception e) {
									throw new IllegalStateException(e) ;
								}
							}
						}.start() ;


					} else if (fileName.toUpperCase().endsWith(".MP")) {
						saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
						LOG.info("Создаем заполнение из файла, monitor="+monitorId);
						new Thread() {
							public void run() {
								try {
									expert2service.createEntryByFondXml(monitorId, fileName);
								} catch (Exception e) {
									throw new IllegalStateException(e) ;
								}
							}
						}.start() ;
					} else {
						LOG.warn("Создания заполнения возможно только из МР пакета!");
					}
					break;
				/*case "importN5":
					LOG.warn("import N5 deprecated!");
					if ((fileName.startsWith("N2") || fileName.startsWith("N5")) && fileName.toUpperCase().endsWith(".XML")) { //Импортируем файл для проставления номеров направления фонда
						LOG.info("start import N5");
						 //expert2service.importN5File(new SAXBuilder().build(ffile.getInputStream()),entryListId);
					} else {
						result="Неверное имя файла для импорта N5 (xml файл должен начинаться с N5)!";
					}
					break; */
				case "importFlk":
					saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
					LOG.info(expert2service.importFlkAnswer(fileName, entryListId));
					break;
				case "importDefect":
					saveFile(ffile.getInputStream(), xmlUploadDir+"/"+fileName);
					new Thread() {
						public void run() {
							try {
								expert2service.importFondMPAnswer(monitorId, fileName) ;
							} catch (Exception e) {
								throw new IllegalStateException(e) ;
							}
						}
					}.start() ;


					break;
					default:
						LOG.warn("Я не понимаю, чего вы от меня хотите!!!"+action);
			}

			aRequest.setAttribute("monitorId",monitorId);
    		return aMapping.findForward(SUCCESS) ;

    	} catch(Exception e) {
			LOG.error("Ошибочка = ",e);
    	}
    	return aMapping.findForward(SUCCESS) ;
    }

	private void saveFile(InputStream aInputStream, String aFileName) throws IOException {
		File outputFile = new File(aFileName);
		if (outputFile.exists() || outputFile.createNewFile()) {
			int count;
			try (FileOutputStream out = new FileOutputStream(aFileName)) {
				byte[] buf = new byte[8192];
				while ((count = aInputStream.read(buf)) > 0) {
					out.write(buf, 0, count);
				}
				aInputStream.close();
			} catch (Exception e) {
				LOG.error("Невозможно записать файл: "+e.getMessage(),e);
			}
		} else {
			LOG.error("Невозможно открыть / создать файл: "+aFileName);
		}
	}
}