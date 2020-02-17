package ru.nuzmsh.web.tags;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ActionConfig;
import ru.nuzmsh.forms.response.FormMessage;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.messages.InfoMessage;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.IdeTagHelper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.Iterator;

/**
 * Форма
 *
 * @jsp.tag name="form"
 *          display-name="Form"
 *          body-content="JSP"
 *          description="Form"
 */
public class FormTag extends org.apache.struts.taglib.html.FormTag implements IGuidSupport {


	private static final Logger LOG = Logger.getLogger(FormTag.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	/** 
	 * Название формы
	 * @jsp.attribute   description = "Название формы"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getTitle() {
		return theTitle;
	}

	public void setTitle(String aTitle) {
		theTitle = aTitle;
	}

	/** Название формы */
	private String theTitle;
	
	/** 
	 * Отключить подтверждение запроса при переходе по ссылке
	 * @jsp.attribute   description = "Отключить подтверждение запроса при переходе по ссылке"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public boolean getDisableFormDataConfirm() {
		return theDisableFormDataConfirm;
	}

	public void setDisableFormDataConfirm(boolean aDisableFormDataConfirm) {
		theDisableFormDataConfirm = aDisableFormDataConfirm;
	}

	/** Отключить подтверждение запроса при переходе по ссылке */
	private boolean theDisableFormDataConfirm = false;
    /**
     * Метод
     * @jsp.attribute   description = "Метод"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getMethod() { return theMethod ; }
    public void setMethod(String aMethod) { theMethod = aMethod ; }

    /** Метод */
    private String theMethod ;
    /**
     * Поддержка передачи файла
     * @jsp.attribute   description = "Поддержка передачи файла"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getFileTransferSupports() { return theFileTransferSupports ; }
    public void setFileTransferSupports(boolean aFileTransferSupports) { theFileTransferSupports = aFileTransferSupports ; }

    /**
     * Роли для сохранения
     * @jsp.attribute   description="Роли для сохранения"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getEditRoles() { return theEditRoles ; }
    public void setEditRoles(String aEditRoles) { theEditRoles = aEditRoles ; }


    /**
     * Роли для создания
     * @jsp.attribute   description="Роли для создания"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getCreateRoles() { return theCreateRoles ; }
    public void setCreateRoles(String aCreateRoles) { theCreateRoles = aCreateRoles ; }

    /**
     * Дейтсвие
     * @jsp.attribute   description="Дейтсвие"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public String getAction() { return super.getAction() ; }
    public void setAction(String aAction) { super.setAction(aAction); }


    /**
     * Поле по-умолчанию
     * @jsp.attribute   description="Поле по-умолчанию"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public String getDefaultField() { return theDefaultField ; }
    public void setDefaultField(String aDefaultField) { theDefaultField = aDefaultField ; }


    private String getFocusFieldName() {
        ActionMessages errors = (ActionMessages) pageContext.getRequest().getAttribute("org.apache.struts.action.ERROR");
        String ret = theDefaultField ;
        if(errors !=null && !errors.isEmpty()) {
            Iterator it = errors.properties() ;
            if(it.hasNext()) {
                //List list = (List) it.next() ;
//                ActionMessage msg = (ActionMessage) it.next() ;

                ret = (String) it.next() ;
                if(ret.endsWith("Pk")) {
                    ret += "Name" ; // TODO
                }
            }
        }
        if(ret==null || ret.equals("")) {
            ret = (String) pageContext.getAttribute(AbstractFieldTag.FIRST_DISPLAYED_FIELDNAME, PageContext.REQUEST_SCOPE) ;
        }

        return ret ;
    }

    public int doStartTag() throws JspException {
    	IdeTagHelper ide = IdeTagHelper.getInstance() ;
    	ide.printParentStart("Form", pageContext);
        ide.printMarker("Form", this, pageContext);
    	
        
        super.setStyleId("mainForm");
        if(!StringUtil.isNullOrEmpty(theMethod)) super.setMethod(theMethod);

        if(theFileTransferSupports) super.setEnctype("multipart/form-data");
        int ret = EVAL_BODY_INCLUDE ;
        JspWriter out = pageContext.getOut();
        try {
        	// out.println("<fieldset id='mainFormFieldset'><legend id='mainFormLegend'></legend>") ;
        	out.println("<div>") ;
        	out.println("<div class='x-box-tl'><div class='x-box-tr'><div class='x-box-tc'></div></div></div>") ;
            out.println("<div class='x-box-ml'><div class='x-box-mr'><div class='x-box-mc'>") ;
            out.print("<h2 id='mainFormLegend'>");
            if(!StringUtil.isNullOrEmpty(theTitle)) {
            	out.print(theTitle);
            }
            out.println("</h2>") ;
        	out.println("<div>") ;
        	ret = super.doStartTag();
        } catch (Exception e) {
        	ide.showException(this, e, pageContext);
        }

        
        ActionForm form = findForm() ; //(ActionForm) pageContext.getAttribute(Constants.BEAN_KEY, PageContext.REQUEST_SCOPE);
        if(form instanceof BaseValidatorForm) {
            BaseValidatorForm validatorForm = (BaseValidatorForm) form ;
            setFormPermissions(validatorForm, pageContext.getRequest()) ;

            if(!validatorForm.getFormMessages().isEmpty()) {
                for (FormMessage message : validatorForm.getFormMessages()) {
                    new InfoMessage(pageContext.getRequest(), message.getMessage(), message.getAutoHide());
                }
            }
        }
        

        return ret ;
    }

    private void setFormPermissions(BaseValidatorForm aForm, ServletRequest aRequest) throws JspException {
        if(aForm.getSaveType()==BaseValidatorForm.TYPE_SAVE
                && ( theEditRoles!=null && !RolesHelper.checkRoles(theEditRoles, (HttpServletRequest) aRequest))) {
            aForm.setTypeViewOnly();
        }

        if(aForm.getSaveType()==BaseValidatorForm.TYPE_CREATE
                && ( theCreateRoles!=null && !RolesHelper.checkRoles(theCreateRoles, (HttpServletRequest) aRequest))) {
            aForm.setTypeViewOnly();
        }
    }

    private ActionForm findForm() {
    	ActionConfig actionConfig = (ActionConfig) pageContext.getAttribute("org.apache.struts.action.mapping.instance", PageContext.REQUEST_SCOPE);
    	if(actionConfig!=null) {
    		Object obj = pageContext.getAttribute(actionConfig.getName(), PageContext.REQUEST_SCOPE);
    		if(obj!=null) {
    			return (ActionForm) obj ;
    		}
    	}
    	return null ;
    }
    
    private void printJavaScript(PageContext aPageContext) {
            JavaScriptContext js = JavaScriptContext.getContext(aPageContext, this);
            long unique = Math.round(Math.random() * 1000.0) ;

            ActionForm form = findForm() ; //(ActionForm) pageContext.getAttribute(Constants.BEAN_KEY, PageContext.REQUEST_SCOPE);
            if (CAN_DEBUG)
				LOG.debug("printJavaScript: form = " + form); 

            if(form instanceof BaseValidatorForm) {
                BaseValidatorForm validatorForm = (BaseValidatorForm) form ;
                if (CAN_DEBUG)
					LOG.debug("printJavaScript: validatorForm.isViewOnly() = " + validatorForm.isViewOnly()); 

                if(!validatorForm.isViewOnly()) {
                    js.println(String.format("   setFocusOnField('%1$s') ;", getFocusFieldName())) ;
                }
            }
            js.println("       Event.observe(window, 'load', _formInit_"+unique+", true);") ;
            js.println("       function _formInit_"+unique+"() {") ;
//        JavaScriptContext js = JavaScriptContextLast.getContext(pageContext, this) ;

//        js.println("msh.util.FormData.getInstance().init($('mainForm'))");


            // FIXME перенести код в AbstractFieldTag
            if(form instanceof BaseValidatorForm) {
                BaseValidatorForm baseForm = (BaseValidatorForm) form ;
                if(!baseForm.isViewOnly()) {
                    if(!theDisableFormDataConfirm) {
                    	js.println("msh.util.FormData.getInstance().init($('mainForm')) ;");
                    }
//                    Iterator it = ((BaseValidatorForm)form).getDisabledFieldsIterator() ;
//                    while (it.hasNext()) {
//                        String fieldName = (String) it.next();
//                        js.println(String.format("  if($('%1$s')!=null) $('%1$s').readOnly = true ;",fieldName)) ;
//                        js.println(String.format("  if($('%1$sName')!=null) $('%1$sName').readOnly = true ;",fieldName)) ;
//                        if(fieldName.endsWith("Pk")) {
//                            js.println(String.format("  $('%1$sName').readOnly = true ;",fieldName)) ; // todo
//                        }
//                    }
                } else {
                        js.println("Element.addClassName($('mainForm'), 'viewOnly');");
                }
            }

            js.println("}") ;
    }


    public int doEndTag() throws JspException {
        int ret = super.doEndTag();
        JspWriter out = pageContext.getOut();
        try {
        	//out.println("</fieldset>") ;
        	out.println("</div>");
        	out.println("</div></div></div>");
        	out.println("<div class='x-box-bl'><div class='x-box-br'><div class='x-box-bc'></div></div></div>");
        	out.println("</div>");        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        printJavaScript(pageContext);
    	IdeTagHelper.getInstance().printParentEnd(pageContext) ;
        return ret ;
    }

    /**
     * GUID
     * @jsp.attribute   description = "GUID"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getGuid() {
            return theGuid;
    }
    public void setGuid(String aGuid) {
            theGuid = aGuid;
    }
    /** GUID */
    private String theGuid;
    
    /** Поле по-умолчанию */
    private String theDefaultField ;
    /** Роли для сохранения */
    private String theEditRoles = null ;
    /** Роли для создания */
    private String theCreateRoles = null ;
    /** Поддержка передачи файла */
    private boolean theFileTransferSupports = false ;
}
