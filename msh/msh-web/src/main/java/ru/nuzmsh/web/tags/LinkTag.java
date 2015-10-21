package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="link"
 *          display-name="A href element"
 *          body-content="scriptless"
 *          description="A href element"
 */
public class LinkTag extends AbstractGuidSimpleSupportTag {

    private final static Log LOG = LogFactory.getLog(LinkTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    /**
     * Устанавливать фокус на элемент
     * @jsp.attribute   description = "Устанавливать фокус на элемент"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public boolean getRequestFocus() { return theRequestFocus ; }
    public void setRequestFocus(boolean aRequestFocus) { theRequestFocus = aRequestFocus ; }

    /** OnClick 
     * @jsp.attribute   description = "Method onclick"
     *                     required = "false"
     *                  rtexprvalue = "true"
     * */
	public String getOnclick() {
		return theOnclick;
	}

	public void setOnclick(String aOnclick) {
		theOnclick = aOnclick;
	}
	/** Это отчет 
    * @jsp.attribute   description = "Ссылка на отчетную базу"
    *                     required = "false"
    *                  rtexprvalue = "true"
    */                  
	public boolean getIsReport() {
		return theIsReport;
	}

	public void setIsReport(boolean aIsReport) {
		theIsReport = aIsReport;
	}

	/** Это отчет */
	private boolean theIsReport;

	/** OnClick */
	private String theOnclick;
    
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

    /** Политики безопасности, разделенный ; */
    private String theRoles ;
    /** Struts action */
    private String theAction ;


    public void doTag() throws JspException, IOException {
    	printIdeStart("link");
    	
        JspWriter out = getJspContext().getOut() ;

        PageContext pageContext = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;

        IdeTagHelper ide = IdeTagHelper.getInstance() ;
        ide.printParentStart("Link", pageContext);
        ide.printMarker("Link", this, pageContext);
        
        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, request)) {
            //if(isInToolBar()) out.print("<li>") ;

            StringBuilder url = new StringBuilder();
            if (!theAction.startsWith("javascript:")) {
                String addSb ; 
                if (theIsReport) {
                	addSb = (String)pageContext.getSession().getAttribute("LOGININFO_URL_REPORT_BASE") ;
                } else {
                	addSb = (String)pageContext.getSession().getAttribute("LOGININFO_URL_MAIN_BASE") ;
                }
                if (theAction.indexOf("http")==-1) url.append(addSb!=null&&!addSb.equals("null")?addSb:"") ;

	            url.append(theAction.charAt(0)=='/' ? theAction.substring(1) : theAction) ;
	            
	            if(theAction.indexOf(".do")<0) {
	                url.append(".do") ;
	            }
            } else {
            	url.append(theAction) ;
            }

            if(isInToolBar()) {
                out.print("<button type='button' onclick=\"window.location='") ;
                out.print(url) ;
                out.print("'\"") ;
                if(theRequestFocus) {
                    out.println(" id='linkDefaultFocus'") ;
                }
                out.print(">") ;
                getJspBody().invoke(out);
                out.print("</button>") ;
            } else {
                out.print("<a href='") ;
                out.print(url) ;
                out.print("'") ;
//            if(isInToolBar()) out.print(" class='linkButton'") ;
                if (theOnclick!=null && !theOnclick.equals("")) {
                	out.print(" onclick=\"");
                	out.print(theOnclick) ;
                	out.print("\"") ;
                }
                out.print(">") ;
                getJspBody().invoke(out);
                out.println("</a>") ;
            }
            if(theRequestFocus) {
                JavaScriptContext js = JavaScriptContext.getContext(pageContext, this);
                js.println("$('linkDefaultFocus').focus();");

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
}

//private void printNoLink(JspWriter aOut, String aReason) throws IOException, JspException {
//    if(isInToolBar()) aOut.print("<li>") ;
//    aOut.print("<span class='nolink'>");
//    aOut.print(aReason) ;
//    aOut.println(" : ") ;
//    getJspBody().invoke(aOut);
//    aOut.println("</span>") ;
//    if(isInToolBar()) aOut.print("</li>") ;
//}
