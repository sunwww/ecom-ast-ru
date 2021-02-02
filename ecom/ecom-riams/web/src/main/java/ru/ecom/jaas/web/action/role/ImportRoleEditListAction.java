package ru.ecom.jaas.web.action.role;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import ru.ecom.jaas.ejb.service.ISecService;
import ru.ecom.jaas.ejb.service.PolicyForm;
import ru.ecom.jaas.web.action.policy.ExportPolicyForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ImportRoleEditListAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        ISecService service = (ISecService) Injection.find(aRequest).getService("SecService");
        List<PolicyForm> list = new LinkedList<>();
        try {
            if (aForm != null) {
                ExportPolicyForm form = (ExportPolicyForm) aForm;
                try (InputStream in = form.getFile().getInputStream()) {
                    Document doc = new SAXBuilder().build(in);
                    Element parConfigElement = doc.getRootElement();
                    long i = 0L;
                    for (Object o : parConfigElement.getChildren()) {
                        Element parElement = (Element) o;
                        if ("role".equals(parElement.getName())) {

                            String key = parElement.getAttributeValue("key");
                            String name = parElement.getAttributeValue("name");
                            String comment = parElement.getAttributeValue("comment");
                            PolicyForm role = new PolicyForm(key, name, comment);
                            role.setIsExist(service.findRole(role) != null);
                            role.setIdInFile(++i);
                            list.add(role);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }


        } catch (Exception e) {
            System.out.println(e);
        }
        aRequest.setAttribute("roles", list);
        return aMapping.findForward(SUCCESS);
    }
}