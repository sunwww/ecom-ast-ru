package ru.ecom.web.tags;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.mis.ejb.service.birth.IPregnancyService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;
import ru.nuzmsh.web.tags.SideMenuTag;
import ru.nuzmsh.web.tags.ToolbarTag;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.IdeTagHelper;
import ru.nuzmsh.web.xhtml.Xa;
import ru.nuzmsh.web.xhtml.Xli;

public class SideLinkForWomanTag extends AbstractGuidSimpleSupportTag {

    private final static Log LOG = LogFactory.getLog(LinkCspTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;

    /**
     * Устанавливать фокус на элемент
     * @jsp.attribute   description = "Устанавливать фокус на элемент"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getRequestFocus() { return theRequestFocus ; }
    public void setRequestFocus(boolean aRequestFocus) { theRequestFocus = aRequestFocus ; }

    /** Устанавливать фокус на элемент */
    private boolean theRequestFocus ;
    /**
     * Struts action
     * @jsp.attribute   description="Struts action"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public String getAction() { return theAction ; }
    public void setAction(String aAction) { theAction = aAction ; }

    /**
     * Политики безопасности, разделенный ;
     * @jsp.attribute   description="Политики безопасности, разделенный ;"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getRoles() { return theRoles ; }
    public void setRoles(String aRoles) { theRoles = aRoles ; }

    /** Пациент */
	@Comment("Пациент")
	public String getId() {return theId;}
	public void setId(String aPatient) {theId = aPatient;}

	/** Класс объекта */
	@Comment("Класс объекта")
	public String getClassByObject() {return theClassByObject;}
	public void setClassByObject(String aClassByObject) {theClassByObject = aClassByObject;}

	/** Параметры */
	@Comment("Параметры")
	public String getParams() {return theParams;}
	public void setParams(String aParams) {theParams = aParams;}

	/** Горячие клавиши */
	@Comment("Горячие клавиши")
	public String getKey() {return theKey;}
	public void setKey(String aKey) {theKey = aKey;}

	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Стиль */
	@Comment("Стиль")
	public String getStyleId() {return theStyleId;}
	public void setStyleId(String aStyleId) {theStyleId = aStyleId;}

	/**  */
	@Comment("")
	public String getConfirm() {return theConfirm;}
	public void setConfirm(String aConfirm) {theConfirm = aConfirm;}

	/** Заголовок */
	@Comment("Заголовок")
	public String getTitle() {return theTitle;}
	public void setTitle(String aTitle) {theTitle = aTitle;}

	
	/** Заголовок */
	private String theTitle;
	/**  */
	private String theConfirm;
	/** Стиль */
	private String theStyleId;
	/** Название */
	private String theName;
	/** Горячие клавиши */
	private String theKey;
	/** Параметры */
	private String theParams;
	/** Класс объекта */
	private String theClassByObject;
	/** Пациент */
	private String theId;
    /** Политики безопасности, разделенный ; */
    private String theRoles ;
    /** Struts action */
    private String theAction ;
    private PageContext thePageContext;
    private HttpServletRequest theRequest ; 


    public void doTag() throws JspException, IOException {

        thePageContext = (PageContext) getJspContext() ;
        theRequest = (HttpServletRequest) thePageContext.getRequest() ;
        boolean womanIs = false ;
        JspWriter out = getJspContext().getOut() ;
        IdeTagHelper ide = IdeTagHelper.getInstance() ;
        ide.printParentStart("Link", thePageContext);
        ide.printMarker("Link", this, thePageContext);
        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, theRequest)) {
        	printIdeStart("link");
        	System.out.println("----PREGNANCY BY PATIENT LINK") ;
           
        
	    	try {
				IPregnancyService service = Injection.find(theRequest).getService(IPregnancyService.class) ;
				if (theClassByObject!=null && theClassByObject.equals("Patient")) 	womanIs = service.isWomanByPatient(Long.valueOf(theId)) ;
				if (theClassByObject!=null && theClassByObject.equals("MedCase")) 	womanIs = service.isWomanByMedCase(Long.valueOf(theId)) ;
			} catch (Exception e) {
				// 
				out.println("<span class='error'>") ;
				out.println(e.getMessage()) ;
				out.println("</span>") ;
			}

			if (womanIs) {
			
				SideMenuTag.removeMustHidden(thePageContext) ;
	        	
	            StringTokenizer st = new StringTokenizer(getParams(), ", ");
	            String action = getAction().substring(1) ;
	            StringBuffer sb = new StringBuffer(action) ;
	            if (action.indexOf("javascript")==-1) { 
	            	if(action.indexOf(".do")>=0) {
		                if(action.indexOf('?')>=0) {
		                    sb.append("&amp;") ;
		                }
		            } else {
		                sb.append(".do?") ;
		            }
	            }
	//        StringBuffer sb = new StringBuffer(getAction().substring(1) + ".do?");
	            boolean canPrint = true;
	            while (st.hasMoreTokens()) {
	                String param = st.nextToken();
	                String value = getParamValue(param);
	                if (value == null) {
	                    canPrint = false;
	                    break;
	                } else {
	                    if(value.equals("0")) {
	                        canPrint = false ;
	                        break ;
	                    }
	                    sb.append(param);
	                    sb.append('=');
	                    try {
	                        sb.append(URLEncoder.encode(value, "utf-8"));
	                    } catch (UnsupportedEncodingException e) {
	                        sb.append(value);
	                        e.printStackTrace();
	                    }
	                    sb.append("&amp;");
	                }
	            }
	
	            if (canPrint) {
	
	                Xa a = theKey!=null ? new Xa(sb.toString(), getName() +"<sup class='accessKey'>"+theKey+"</sup>")
	                        : new Xa(sb.toString(), getName()) ;
	
	                if(getStyleId()!=null) {
	                    a.setID(getStyleId()) ;
	                }
	                a.setTitle(getTitle()) ;
	                if (isSelected()) {
	                    a.setClass("selected");
	                }
	
	                StringBuilder onClickSb = new StringBuilder("return ");
	                if(!StringUtil.isNullOrEmpty(theConfirm)) {
	                    onClickSb.append(" confirm(\"")
	                            .append(theConfirm).append("\") && ") ;
	                }
	                onClickSb.append(" msh.util.FormData.getInstance().isChangedForLink() ;") ;
	                a.setOnClick(onClickSb.toString());
	
	                Xli li = new Xli(a);
	                if(theKey!=null) {
	                    printKeyAccessKey();
	                    if("ALT+DEL".equals(theKey)) {
	                        li.setClass("delete") ;
	                    }
	                }
	                try {
	                    out.println(li);
	                } catch (IOException ioe) {
	                    throw new JspException(ioe) ;
	                }
	
	            }
			} else {
				 out.print("<!--") ;
		         out.print("Ограничение по полу ") ;
		         out.print(theId) ;
		         out.print(" ") ;
		         out.print(theClassByObject) ;
		         out.println("-->") ;
			}

            //if(isInToolBar()) out.print("</li>") ;
        } else {
            out.print("<!--") ;
            out.print("Нет ролей: ") ;
            out.print(theRoles) ;
            out.println("-->") ;
        }

        
        printIdeEnd();

    }
    private String getParamValue(String aName) {
        Object param = thePageContext.getRequest().getAttribute(aName);
        if (param == null) {
            param = thePageContext.getRequest().getParameter(aName);
            if (param == null) {
                HttpSession session = thePageContext.getSession();
                if (session != null) {
                    param = session.getAttribute(aName);
                }
            }
        }
        String str;
        if (param != null) {
            if (param instanceof String) {
                str = (String) param;
            } else {
                str = param.toString();
            }
        } else {
            str = null;
        }
        return str;

    }

    private boolean isInToolBar() {
        return getParent() instanceof ToolbarTag ;
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
    private boolean isSelected() {
        try {
            return theRequest.getRequestURI().indexOf(getAction()) != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	private void printKeyAccessKey() {
        String key = getKey();
        String id = theStyleId == null ? key : theStyleId;
        JavaScriptContext.getContext(thePageContext, this)
                .println(
                 "     accesskeyutil.registerKey($('" + id + "'), accesskeyutil." + key + ") ;");
    }


}
