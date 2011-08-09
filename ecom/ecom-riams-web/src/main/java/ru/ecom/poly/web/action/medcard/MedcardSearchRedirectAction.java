package ru.ecom.poly.web.action.medcard;

import ru.nuzmsh.web.struts.BaseAction;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 25.01.2007
 * Time: 21:11:30
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class MedcardSearchRedirectAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        HttpSession session = aRequest.getSession(true);

        Object o = session.getAttribute("medcardSearchResult");
        if (o == null)
            o = new LinkedList();

        aRequest.setAttribute("list", o);

        return aMapping.findForward("success");
    }
}
