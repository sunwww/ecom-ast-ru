package ru.ecom.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

import ru.ecom.mis.ejb.service.birth.IPregnancyService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class IfPatientIsWoman extends AbstractGuidSimpleSupportTag {

    /**
     * Роли
     * @jsp.attribute   description = "Роли"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getIdObject() { return theIdObject ; }
    public void setIdObject(String aIdObject) { theIdObject = aIdObject ; }
	/** Класс объекта */
	@Comment("Класс объекта")
	public String getClassByObject() {return theClassByObject;}
	public void setClassByObject(String aClassByObject) {theClassByObject = aClassByObject;}
	
    /**
     * Политики безопасности, разделенный ;
     * @jsp.attribute   description="Политики безопасности, разделенный ;"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getRoles() { return theRoles ; }
    public void setRoles(String aRoles) { theRoles = aRoles ; }
    /**
     * GUID
     * @jsp.attribute   description = "GUID"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getGuid() {return theGuid;}
    public void setGuid(String aGuid) {theGuid = aGuid;}

    public void doTag() throws JspException, IOException {
    	printIdeStart("ifPatientIsWoman");
    	boolean womanIs = false ;
        JspWriter out = getJspContext().getOut() ;
        PageContext pageContext = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;
        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, request)) {
	    	try {
				IPregnancyService service = Injection.find(request).getService(IPregnancyService.class) ;
				String id = getObjectIdValue(pageContext) ;
				if (theClassByObject!=null && theClassByObject.equals("Patient")) 	womanIs = service.isWomanByPatient(Long.valueOf(id)) ;
				if (theClassByObject!=null && theClassByObject.equals("MedCase")) 	womanIs = service.isWomanByMedCase(Long.valueOf(id)) ;
			} catch (Exception e) {
				System.out.println("<span class='error'>") ;
				System.out.println(e.getMessage()) ;
				System.out.println("</span>") ;
			}
			System.out.println("womanIs="+womanIs) ;
			if (womanIs) {
				getJspBody().invoke(out);
			}
        }
    	printIdeEnd();
    }
private String getObjectIdValue(PageContext aPageContext)  {
    	
    	
        HttpServletRequest request = (HttpServletRequest) aPageContext.getRequest() ;
    	String value = request.getParameter(theIdObject) ;
    	if(value!=null) return value ;
    	
    	Object obj = aPageContext.findAttribute(theIdObject) ;
    	if(obj!=null) {
    		return obj.toString() ;
    	} else {
        	int pos = theIdObject.indexOf('.') ;
        	if(pos>0) {
        		String formName = theIdObject.substring(0,pos) ;
        		Object form = aPageContext.findAttribute(formName) ;
        		String property = theIdObject.substring(pos+1) ;
        		try {
            		obj = PropertyUtils.getProperty(form, property) ;
        		} catch(Exception e) {
        			obj = e.getMessage() ;
        			//LOG.error("Ошибка получение parentId "+theIdObject,e) ;
        		}
        	}
    	}
    	return obj!=null ? obj.toString() : theIdObject ;
    }

    /** Политики безопасности, разделенный ; */
    private String theRoles ;
    /** Класс объекта */
	private String theClassByObject;
	/** Роли */
    private String theIdObject ;
    /** GUID */
    private String theGuid;    
}