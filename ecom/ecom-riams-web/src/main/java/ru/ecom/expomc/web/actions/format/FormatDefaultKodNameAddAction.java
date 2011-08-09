package ru.ecom.expomc.web.actions.format;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.expomc.ejb.domain.format.Field;
import ru.ecom.expomc.ejb.services.form.format.FieldForm;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.ForwardUtil;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * @author esinev
 * Date: 22.08.2006
 * Time: 11:43:54
 */
public class FormatDefaultKodNameAddAction extends BaseAction {

    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        //IFormatService service = Injection.find(aRequest).getFormatService();
        IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();

        long formatId = getLongId(aRequest, "Идентификатор формата");
        FieldForm form = new FieldForm();
        form.setFormat(formatId);
        form.setName("KOD");
        form.setComment("Код");
        form.setDbfSize(10);
        form.setDbfType(Field.TEXT);
        form.setDbfDecimal(0);
        form.setProperty("code");
        form.setSerialNumber(1);
        form.setRequired(true);
        form.setLinkedDocument(null);

        service.create(form) ;

        form.setName("NAME");
        form.setSerialNumber(2);
        form.setProperty("name");
        form.setComment("Наименование");
        form.setDbfSize(255);

        service.create(form) ;

        return ForwardUtil.createIdRedirectForward(aMapping.findForward("success"), aRequest) ;
    }
}
