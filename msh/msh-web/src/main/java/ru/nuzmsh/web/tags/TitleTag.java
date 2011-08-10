package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 *
 * Название страницы
 * @jsp.tag  name="title"
 *          display-name="Title"
 *          body-content="scriptless"
 *          description="Название страницы"
 *
 */
public class TitleTag extends AbstractGuidSimpleSupportTag {


    /**
     * Название страницы
     * @jsp.attribute   description="Название страницы"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /**
     * Основное меню
     * @jsp.attribute   description = "Основное меню"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getMainMenu() { return theMainMenu ; }
    public void setMainMenu(String aMainMenu) { theMainMenu = aMainMenu ; }

    /**
     * Свойство
     * @jsp.attribute   description = "Свойство"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }


    public void doTag() throws JspException, IOException {
//    	printIdeStart();
//    	printIdeEnd();
        JspWriter out = getJspContext().getOut() ;
        out.print("<h1>") ;
        IdeTagHelper.getInstance().printMarker(this, getJspContext());
        // IDE
        
        if(!StringUtil.isNullOrEmpty(theTitle)) {
        	out.print(theTitle);
        } else {
            if(getJspBody()!=null) getJspBody().invoke(out);
        }
        
        if(!StringUtil.isNullOrEmpty(theProperty)) {
            String value = PropertyItemTag.evalProperty(theProperty, getJspContext());
            if(value!=null) {
                out.print(": <span>") ;
                out.print(value) ;
                out.print("</span>") ;
            }
        }
        out.println("</h1>")  ;
        if(!StringUtil.isNullOrEmpty(theMainMenu)) {
            JavaScriptContext js = JavaScriptContext.getContext((PageContext) getJspContext(), this);
            js.println("Element.addClassName($('mainMenu"+theMainMenu+"'), 'selected');");
        }
        
    }

    /** Свойство */
    private String theProperty ;
    /** Основное меню */
    private String theMainMenu ;

    /** Название панели */
    private String theTitle ;
}
