package ru.ecom.expomc.web.actions.format;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.expomc.ejb.services.form.format.IFormatService;
import ru.ecom.web.util.ForwardUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * @author esinev
 * Date: 22.08.2006
 * Time: 11:43:54
 */
public class FormatImportFromWordSaveAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        FormatImportFromWordForm form = (FormatImportFromWordForm) aForm ;
        IFormatService service = Injection.find(aRequest).getService(IFormatService.class);

        service.addFieldFromWord(form.getId(), form.getText());
        
        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), aRequest) ;
    }
}
