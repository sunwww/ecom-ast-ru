package ru.ecom.mis.web.tags;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.mis.ejb.form.prescription.PrescriptionForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

/**
 * Shows content if PrescriptionForm.isHospitalMesCase() is true

 */


public class CanShowPrescriptionFulfilmentsTag extends AbstractGuidSimpleSupportTag {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
        PrescriptionForm form = (PrescriptionForm) request.getAttribute(theFormName);

        if (form == null) throw new IllegalStateException("No prescriptionForm has been found");

        try {

            IScriptService service = Injection.find(request).getService(IScriptService.class);
            boolean isHospital = (Boolean) service.invoke("PrescriptionFulfilmentService", "isPrescriptionFulfilmentHospitalMedCase", new Object[]{form.getId()});

            if (isHospital) {
                getJspBody().invoke(getJspContext().getOut());
            }
        }
        catch (NamingException e) {

            throw new IllegalStateException("Service ScriptService was not found: " + e.getMessage(), e);
        }
    }

    /** Form name */
    public String getFormName() {
        return theFormName ;
    }

    /** Form name */
    public void setFormName(String aFormName) {
        theFormName = aFormName ;
    }

    /** Form name */
    private String theFormName;
}
