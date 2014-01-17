package ru.ecom.jaas.web.action.vocabulary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.jaas.web.action.role.RolePoliciesSaveAction;
import ru.ecom.mis.ejb.service.vocabulary.IVocabularyService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

public class VocExtDispExportAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	IVocabularyService service = Injection.find(aRequest).getService(IVocabularyService.class) ;
    	String[] ids = aRequest.getParameterValues("id") ;
    	long[] idsL = RolePoliciesSaveAction.getLongs(ids) ;
        String filename = service.exportVocExtDisp(idsL);
        return new ActionForward("../rtf/"+filename,true) ;
    }
}
