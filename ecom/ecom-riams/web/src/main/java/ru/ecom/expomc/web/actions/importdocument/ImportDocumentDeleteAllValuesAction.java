package ru.ecom.expomc.web.actions.importdocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.impdoc.IImportDocumentService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

public class ImportDocumentDeleteAllValuesAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		IImportDocumentService service = Injection.find(aRequest).getService(IImportDocumentService.class) ;
		long id = getLongId(aRequest, "Идентификатор документа") ;
		int count = service.deleteAllValues(id) ;
		new InfoMessage(aRequest, "Удалено "+count) ;
		return aMapping.findForward("success");
	}

}
