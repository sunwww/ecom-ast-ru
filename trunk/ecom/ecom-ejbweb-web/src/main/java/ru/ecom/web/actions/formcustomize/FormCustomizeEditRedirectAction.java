package ru.ecom.web.actions.formcustomize;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

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
                StringBuilder url = new StringBuilder("formCustomizeEdit.do?formName=");
                url.append(formName) ;
                url.append("&elementName=") ;
                url.append(elementName) ;
                fw = new ActionForward(url.toString(), true);
            } else {
                new InfoMessage(aRequest, "Нет названия поля") ;
            }
        } else {
            new InfoMessage(aRequest, "Нет названия формы") ;
        }
        return fw!=null ? fw : aMapping.findForward(SUCCESS) ;
    }

}
