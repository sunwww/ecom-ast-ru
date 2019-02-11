package ru.nuzmsh.web.tags;

import org.apache.log4j.Logger;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @jsp.tag           name = "customizeFormModeLink"
 *            display-name = "Переход в режим редактирования формы"
 *            body-content = "scriptless"
 *             description = "Переход в режим редактирования формы"
 */
public class CustomizeFormModeLinkTag extends SimpleTagSupport {

    private static final Logger LOG = Logger.getLogger(CustomizeFormModeLinkTag.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;



    /**
     * Роли
     * @jsp.attribute   description = "Роли"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getRoles() { return theRoles ; }
    public void setRoles(String aRoles) { theRoles = aRoles ; }

    /** Роли */
    private String theRoles ;
    public void doTag() throws JspException, IOException {

        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, getJspContext())) {
            PageContext pageContext = (PageContext) getJspContext() ;
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;


            String custMode = request.getParameter("customizeMode") ;
            boolean isInCustomeMode = false ;
            try {
                if(custMode!=null) {
                    isInCustomeMode = Boolean.parseBoolean(custMode) ;
                }
            } catch (Exception e) {
                LOG.error("Ошибка преобразования строки в Boolean: "+custMode,e) ;
            }
            if (CAN_TRACE) LOG.info("isInCustomeMode = " + isInCustomeMode);
            JspWriter out = getJspContext().getOut() ;
            StringBuilder sb = new StringBuilder();
            sb.append(request.getRequestURI()) ;
            out.println("<script type='text/javascript'>") ;
            out.println(" var url= new String(window.location) ;") ;
            if(isInCustomeMode) {
                out.println("var replacedUrl = url.replace(/customizeMode\\=true/g, 'customizeMode=false') ;") ;
                out.println("document.write(\"<a href='\"+replacedUrl+\"'>Возврат в обычный режим</a>\") ;") ;
            } else {
                out.println("url=url.replace(/.customizeMode\\=true/g,'')") ;
                out.println("url=url.replace(/.customizeMode\\=false/g,'')") ;
                out.println(" var question=url.indexOf('?')>=0 ? '&' : '?' ; ") ;
                out.println("document.write(\"<a href='\"+url+question+\"customizeMode=true'>Изменить вид формы</a>\") ;") ;
            }
            out.println("</script>") ;
        }
    }

}
