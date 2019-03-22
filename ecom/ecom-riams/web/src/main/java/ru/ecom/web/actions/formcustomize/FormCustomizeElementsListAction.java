package ru.ecom.web.actions.formcustomize;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.struts.forms.customize.FormCustomizeServiceHolder;
import ru.nuzmsh.web.struts.forms.customize.FormElementInfo;
import ru.nuzmsh.web.struts.forms.customize.IFormCustomizeService;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class FormCustomizeElementsListAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IdStringForm form = (IdStringForm) aForm ;
        IFormCustomizeService service = FormCustomizeServiceHolder.getService() ;

        aRequest.setAttribute("list", service.listCustomizedElements(form.getId())) ;
        aRequest.setAttribute("decorator", new TableDecorator(form.getId())) ;
        aRequest.setAttribute("formName", form.getId());
        return aMapping.findForward(SUCCESS) ;
    }

    private static class TableDecorator implements ITableDecorator {
        public TableDecorator(String aFormName) {
            theFormName = aFormName ;
        }

        public String getRowCssClass(Object aRow) {
            return null;
        }

        public String getId(Object aRow) {
            FormElementInfo info = (FormElementInfo) aRow ;
            return info==null ? "" : theFormName +'.' +info.getName();
        }

        private final String theFormName ;
    }

}
