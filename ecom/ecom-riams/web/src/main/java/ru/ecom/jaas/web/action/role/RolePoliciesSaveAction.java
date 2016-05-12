package ru.ecom.jaas.web.action.role;

import ru.nuzmsh.web.struts.BaseAction;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.ecom.web.util.Injection;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.ejb.service.CheckNode;
import ru.ecom.jaas.web.action.service.ServiceExportJbossAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Сохранение политик у роли
 */
public class RolePoliciesSaveAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecRoleService service = (ISecRoleService) Injection.find(aRequest).getService("SecRoleService");

        long roleId = getLongId(aRequest, "Идентификатор роли");

        service.saveRolePolicies(roleId
                , getLongs(aRequest.getParameterValues("a"))
                , getLongs(aRequest.getParameterValues("r")));
        ServiceExportJbossAction.export(aRequest);

        new InfoMessage(aRequest, "Политики роли сохранены и экспортированы") ;

        return new ActionForward(aMapping.findForward("success").getPath() + "?id=" + roleId, true);
    }


    public static long[] getLongs(String aStr[]) {
    	try {
    		long[] ar = new long[aStr.length];
    		for (int i = 0; i < aStr.length; i++) {
    			String s = aStr[i];
    			ar[i] = Long.parseLong(s);
    		}
    		return ar;
    	} catch (Exception e) {
    		return new long[0] ;
    	}
    }
    public static ArrayList<Long>getLongsArray(String aStr[]) {
        try {
            ArrayList<Long> ar = new ArrayList<Long>();
            for (int i = 0; i < aStr.length; i++) {
                String s = aStr[i];
                ar.add(Long.parseLong(s));
            }
            return ar;
        } catch (Exception e) {
            return new ArrayList<Long>() ;
        }
    }


}
