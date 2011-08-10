package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

/**
 *
 * Вывод свойства
 * @jsp.tag  name="propertyItem"
 *          display-name="Вывод свойства"
 *          body-content="scriptless"
 *          description="Вывод свойства"
 *
 */
public class PropertyItemTag extends SimpleTagSupport {

    /**
     * Свойство
     * @jsp.attribute   description = "Свойство"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /**
     * Название
     * @jsp.attribute   description = "Название"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Название */
    private String theName ;
    /** Свойство */
    private String theProperty ;

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        if(StringUtil.isNullOrEmpty(theProperty)) {
            throw new IllegalArgumentException("Нет свойства property") ;
        } else {
            String evalled = evalProperty(theProperty, getJspContext()) ;
            if(!StringUtil.isNullOrEmpty(evalled) ) {
                out.print("<li><em>") ;
                out.print(theName) ;
                out.print(":</em> ") ;
                out.print(evalled) ;
                out.println("</li>") ;
            } else {
                out.println("<!-- propertyItem :"+theName+ " ("+theProperty + " )  = нет значения -->") ;
            }
        }
    }

    /**
     *
     * @param aProperty
     * @param aContext
     * @return null если нет значения
     * @throws JspException
     */
    public static String evalProperty(String aProperty, JspContext aContext) throws JspException {
        PageContext ctx = (PageContext) aContext ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;

        int pointPosition = aProperty.indexOf('.') ;
        Object value ;
        String keyToRead ;
        if(pointPosition>0) {
            String object = aProperty.substring(0, pointPosition) ;
            String property = aProperty.substring(pointPosition+1) ;
            try {
                value = PropertyUtil.getPropertyValue(request.getAttribute(object), property) ;
                keyToRead = object +"."+property ;
            } catch (Exception e) {
                throw new JspException("Ошибка получение значения: "+object+"."+property,e) ;
            }
        } else {
            value = request.getAttribute(aProperty) ;
            keyToRead = aProperty ;
        }

        return value!=null ? value.toString() : null ;
    }

}
