package ru.ecom.expomc.ejb.services.importservice;

import org.apache.log4j.Logger;

import javax.annotation.EJB;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Запуск импорта
 */
//@MessageDriven(activationConfig = {
//@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/expomc/import")
//        })
public class ImportMDB implements MessageListener {

    private static final Logger LOG = Logger.getLogger(ImportMDB.class) ;
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    public void onMessage(Message aMessage) {
        try {
            long monitorId = aMessage.getLongProperty("monitorId") ;
            String filename = aMessage.getStringProperty("filename") ;
            ImportFileForm fileForm = new ImportFileForm();

            fileForm.setActualDateFrom(aMessage.getStringProperty("ImportFileForm_actualDateFrom"));
            fileForm.setActualDateTo(aMessage.getStringProperty("ImportFileForm_actualDateTo"));
            fileForm.setComment(aMessage.getStringProperty("ImportFileForm_comment"));
            fileForm.setImportFormat(aMessage.getLongProperty("ImportFileForm_importFormat"));

            ImportFileResult result = theImportService.importFile(filename,monitorId, filename, fileForm) ;
            LOG.info(result.getMessages()) ;
        } catch (Exception e) {
            LOG.error(e,e);
        }
    }

    @EJB IImportService theImportService ;
}
