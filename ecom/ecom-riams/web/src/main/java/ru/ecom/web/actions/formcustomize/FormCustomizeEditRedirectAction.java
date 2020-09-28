package ru.ecom.web.actions.formcustomize;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringTokenizer;

/**
 *
 */
public class FormCustomizeEditRedirectAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        IdStringForm form = (IdStringForm) aForm ;

        ActionForward fw = null ;

        StringTokenizer st = new StringTokenizer(form.getId()!=null ? form.getId():"", ".");
        if(st.hasMoreTokens()) {
            String formName = st.nextToken() ;
            if(st.hasMoreTokens()) {
                String elementName = st.nextToken() ;
                String url = "formCustomizeEdit.do?formName=" + formName +
                        "&elementName=" +elementName;
                fw = new ActionForward(url, true);
            } else {
                new InfoMessage(aRequest, "Нет названия поля") ;
            }
        } else {
            new InfoMessage(aRequest, "Нет названия формы") ;
        }
        return fw!=null ? fw : aMapping.findForward(SUCCESS) ;
    }

}
