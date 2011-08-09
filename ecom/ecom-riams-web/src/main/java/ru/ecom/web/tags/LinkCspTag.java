package ru.ecom.web.tags;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.jaas.ejb.service.ISoftConfigService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;
import ru.nuzmsh.web.tags.ToolbarTag;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.IdeTagHelper;

public class LinkCspTag extends AbstractGuidSimpleSupportTag {

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

    /** Политики безопасности, разделенный ; */
    private String theRoles ;
    /** Struts action */
    private String theAction ;


    public void doTag() throws JspException, IOException {
    	printIdeStart("link");
    	System.out.println("----CSP LINK") ;
        JspWriter out = getJspContext().getOut() ;

        PageContext pageContext = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;
        String cspurl = "" ;
        
        IdeTagHelper ide = IdeTagHelper.getInstance() ;
        ide.printParentStart("Link", pageContext);
        ide.printMarker("Link", this, pageContext);
        
        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, request)) {
	    	try {
				ISoftConfigService service = Injection.find(request).getService(ISoftConfigService.class) ;
				System.out.println("contentPath="+request.getContextPath()) ;
				System.out.println("request"+request.toString()) ;
				System.out.println("localAddr="+request.getLocalAddr()) ;
				System.out.println("remoteAddr="+request.getRemoteAddr()) ;
				System.out.println("remoteHost"+request.getRemoteHost()) ;
				System.out.println("remotePort"+request.getRemotePort()) ;
				System.out.println("pathinfo="+request.getPathInfo());
				System.out.println(request.getPathTranslated());
				System.out.println("uri="+request.getRequestURI());
				System.out.println("url="+request.getRequestURL());
				System.out.println("scheme"+request.getScheme()) ;
				System.out.println("name"+request.getServerName()) ;
				System.out.println("port"+request.getServerPort()) ;
				StringBuilder url = new StringBuilder() ;
				url.append(request.getServerName()) ;
				if (request.getServerPort()>1000) {
					url.append(":").append(request.getServerPort()) ;
				}
				cspurl = service.getCspBaseUrl(url.toString()) ;
			} catch (Exception e) {
				// 
				out.println("<span class='error'>") ;
				out.println(e.getMessage()) ;
				out.println("</span>") ;
			}

       
        
            //if(isInToolBar()) out.print("<li>") ;
        	System.out.println("url") ;
            StringBuilder url = new StringBuilder();
            url.append(request.getScheme()).append("://").append(cspurl).append("/") ;
            url.append(theAction.charAt(0)=='/' ? theAction.substring(1) : theAction) ;
            if(theAction.indexOf(".csp")<0) {
                url.append(".csp?user=").append(request.getUserPrincipal()) ;
            } else {
            	 if(theAction.indexOf("?")<0) {
            		 url.append("?user=") ;
            	 } else {
            		 url.append("&user=") ;
            	 }
            }
            LoginInfo loginInfo = LoginInfo.find(request.getSession()) ;
            url.append(loginInfo!=null ? loginInfo.getUsername() : "нет пользователя!") ;
            System.out.println(url) ;

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