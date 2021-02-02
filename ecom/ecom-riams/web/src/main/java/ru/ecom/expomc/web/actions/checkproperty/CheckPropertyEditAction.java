package ru.ecom.expomc.web.actions.checkproperty;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.expomc.ejb.services.check.ICheckService;
import ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringTokenizer;

/**
 * Редактирование свойства
 */
public class CheckPropertyEditAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ICheckService service = Injection.find(aRequest).getService(ICheckService.class);
        StringTokenizer st = new StringTokenizer(aRequest.getParameter("id"), ",");
        long checkId = Long.parseLong(st.nextToken());
        String property = st.nextToken();
        CheckPropertyForm form = service.loadForm(checkId, property);
        if (property.equals("property")) {
            form.setVocName("importDocumentPropertiesByCheck");
        } else if (property.equals("document")) {
            form.setVocName("importDocument");
        }
        BeanUtils.copyProperties(aForm, form);

        return aMapping.findForward(SUCCESS);
    }
}
