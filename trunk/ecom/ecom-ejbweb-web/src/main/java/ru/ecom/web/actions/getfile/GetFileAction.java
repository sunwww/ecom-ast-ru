package ru.ecom.web.actions.getfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.file.IJbossGetFileService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Получение файла
 */
public class GetFileAction extends BaseAction {
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		long id = getLongId(aRequest, "Идентфикатор файла");
		IJbossGetFileService service = Injection.find(aRequest).getService(
				IJbossGetFileService.class);
		String relative = "../export/" + service.getRelativeFilename(id);
		aRequest.setAttribute("url", relative);
		return aMapping.findForward("success");
		// return new ActionForward(relative, true) ;
	}
}
