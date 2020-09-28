package ru.ecom.jaas.web.action.role;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.jaas.ejb.service.ISecRoleService;
import ru.ecom.jaas.web.action.service.ServiceExportJbossAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        return new ActionForward(aMapping.findForward(SUCCESS).getPath() + "?id=" + roleId, true);
    }


    public static long[] getLongs(String[] aStr) {
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
    public static ArrayList<Long>getLongsArray(String[] aStr) {
        try {
            ArrayList<Long> ar = new ArrayList<>();
            for (String s : aStr) {
                ar.add(Long.parseLong(s));
            }
            return ar;
        } catch (Exception e) {
            return new ArrayList<>() ;
        }
    }


}
