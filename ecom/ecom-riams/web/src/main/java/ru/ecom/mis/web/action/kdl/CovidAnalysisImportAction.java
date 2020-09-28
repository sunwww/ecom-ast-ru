package ru.ecom.mis.web.action.kdl;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.diary.ejb.service.protocol.IKdlDiaryService;
import ru.ecom.expert2.service.IExpert2ImportService;
import ru.ecom.expert2.web.form.ImportFileForm;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CovidAnalysisImportAction extends BaseAction {

    public static final Logger LOG = Logger.getLogger(BaseAction.class);

    @Override
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
                                   HttpServletRequest aRequest, HttpServletResponse aResponse)
            throws Exception {
          ImportFileForm form = (ImportFileForm)aForm;
      if (form.getFile()!=null && form.getFile().getFileSize()>0) {
            IKdlDiaryService service = Injection.find(aRequest).getService(IKdlDiaryService.class);
            IExpert2ImportService expert2service = Injection.find(aRequest).getService(IExpert2ImportService.class);
            String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
            String uploadDir = expert2service.getConfigValue("tomcat.data.dir","/opt/tomcat/webapps/rtf");
            String filename =uploadDir+"/"+ System.currentTimeMillis()+"#"+form.getFile().getFileName();
            saveFile(form.getFile().getInputStream(),filename);
            if (form.getWorkFunctionId()!=null && form.getWorkFunctionId()>0L) {
                service.importCovidAnalysis(filename, username, form.getWorkFunctionId(), form.getVisitResultId(),form.getVisitReasonId()
                ,form.getPrimaryId(), form.getMkbId(), form.getWorkPlaceId());
            } else {
                service.importCovidAnalysis(filename, username);
            }
        }
        return aMapping.findForward(SUCCESS);
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
