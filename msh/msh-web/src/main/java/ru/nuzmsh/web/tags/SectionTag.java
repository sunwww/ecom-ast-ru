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
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 *
 * Секция
 * @jsp.tag  name="section"
 *          display-name="Секция"
 *          body-content="scriptless"
 *          description="Секция"
 *
 */
public class SectionTag extends AbstractGuidSimpleSupportTag {

	private final static Log LOG = LogFactory.getLog(SectionTag.class);
	private final static boolean CAN_TRACE = LOG.isTraceEnabled();

    /**
     * Название секции
     * @jsp.attribute   description = "Название секции"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }
    
    /** 
     * Url добавить запись 
     * @jsp.attribute   description = "URL добадления новой записи"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
	public String getCreateUrl() {return theCreateUrl;}
	public void setCreateUrl(String aCreateUrl) {theCreateUrl = aCreateUrl;}

	/** 
	 * Url short просмотра списка 
     * @jsp.attribute   description = "URL short просмотра списка"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
	public String getShortList() {return theShortList;}
	public void setShortList(String aShortList) {theShortList = aShortList;}

	/** Url short просмотра списка  */
	private String theShortList;
	/** 
	 * Url перейти к списку
     * @jsp.attribute   description = "URL перехода к списку"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
	public String getListUrl() {return theListUrl;}
	public void setListUrl(String aListUrl) {theListUrl = aListUrl;}
	
	/** 
	 * Роли для создания 
     * @jsp.attribute   description = "Роли для создания"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
	public String getCreateRoles() {return theCreateRoles;}
	public void setCreateRoles(String aCreateRoles) {theCreateRoles = aCreateRoles;}

	/** 
	 * Роли для просмотра 
     * @jsp.attribute   description = "Роли для просмотра"
     *                     required = "false"
     *                  rtexprvalue = "true"
	 */
	public String getViewRoles() {return theViewRoles;}
	public void setViewRoles(String aViewRoles) {theViewRoles = aViewRoles;}

	/** Роли для просмотра */
	private String theViewRoles;
	/** Роли для создания */
	private String theCreateRoles;

	/** Url перейти к списку */
	private String theListUrl;
	/** Url добавить запись */
	private String theCreateUrl;
	

    private String shortViewFunctionName(String aAction) {
    	return new StringBuilder().append(" onclick='getDefinition(\"").append(aAction).append("\",event); '").toString();
    }
	
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        
        PageContext pageContext = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;
        
        out.println("<div class='section'>") ;
        
        // IDE
        IdeTagHelper.getInstance().printMarker("Section", this, getJspContext());
        
        if(!StringUtil.isNullOrEmpty(theTitle)) {
            out.print("<h2 class='section'>") ;
            out.print(theTitle) ;
            out.print(" ") ;
            if(theCreateUrl!=null && (StringUtil.isNullOrEmpty(theCreateRoles) || RolesHelper.checkRoles(theCreateRoles, request))) {
            	out.print("<a href='") ;
            	out.print(theCreateUrl);
            	out.print("'>") ;
            	out.print("<img src='/skin/images/main/plus.png' alt='Добавить запись' title='Добавить запись' height='14' width='14'/>") ;
            	out.print("Добавить") ;
            	out.print("</a>&nbsp;");
            }
            if((theShortList!=null || theListUrl!=null) &&(StringUtil.isNullOrEmpty(theViewRoles) || RolesHelper.checkRoles(theViewRoles, request))) {
	            if (theShortList!=null) {
	            	out.print("<a href='javascript:void(0);' "+shortViewFunctionName(theShortList)+">") ;
	            	out.print("<img src='/skin/images/main/view1.png' alt='Просмотр списка' title='Просмотр списка' height='14' width='14'/>") ;
	            	out.print("Просмотр списка") ;
	            	out.print("</a>&nbsp;");
	            }
	            if (theListUrl!=null) {
	            	out.print("<a href='") ;
	            	out.print(theListUrl);
	            	out.print("'>") ;
	            	out.print("<img src='/skin/images/main/list.png' alt='Переход к списку' title='Переход к списку' height='14' width='14'/>") ;
	            	out.print("Перейти к списку") ;
	            	out.print("</a>&nbsp;");
	            }
            }
            out.println("</h2>") ;
            out.println("<div class='sectionContent'>") ;
        }
        try {
        	if(getJspBody()!=null) getJspBody().invoke(out);
        } catch (Exception e) {
        	showException(e);
//        	LOG.error("Ошибка вызова содержимого sectionTag: "+e.getMessage(), e) ;
//            //e.printStackTrace() ;
//            throw new IllegalStateException(e) ;
        }
        if(!StringUtil.isNullOrEmpty(theTitle)) {
            out.println("</div>")  ;
        }
        out.println("</div>")  ;
    }
    /** Название секции */
    private String theTitle = null ;
}
