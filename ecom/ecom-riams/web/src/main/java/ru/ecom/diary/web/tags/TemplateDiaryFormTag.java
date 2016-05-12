package ru.ecom.diary.web.tags;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.mis.ejb.form.prescription.PrescriptionForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

public class TemplateDiaryFormTag extends AbstractGuidSimpleSupportTag {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        String id = request.getParameter("id") ;
        System.out.println("diary id="+id) ;
        //PrescriptionForm form = (PrescriptionForm) request.getAttribute(theFormName);

        //if (form == null) throw new IllegalStateException("No prescriptionForm has been found");
    }
}