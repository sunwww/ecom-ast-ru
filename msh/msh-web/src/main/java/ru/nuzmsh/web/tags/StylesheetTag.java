package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.filter.caching.CacheUniqueUtil;

/**
 * JavaScript src
 * @jsp.tag     name = "stylesheet"
 *      display-name = "stylesheet"
 *      body-content = "scriptless"
 *       description = "stylesheet"
 */
public class StylesheetTag extends SimpleTagSupport {

    /**
     * Путь к css
     * @jsp.attribute   description = "Путь к css"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getSrc() { return theSrc ; }
    public void setSrc(String aSrc) { theSrc = aSrc ; }

    /** Путь к JavaScript */
    private String theSrc ;

    public void doTag() throws JspException, IOException {

    	
        JspWriter out = getJspContext().getOut() ;
        
        out.print("<link rel='stylesheet' type='text/css' href='") ;
        String rewrited = theSrc.replace(".css", CacheUniqueUtil.getUniqueId()+".css") ;
        out.print(rewrited) ;
        //out.print(theSrc) ;
        //out.print("?tmp=") ;
        //out.print(ID) ;
        out.print("' />") ;
    }
}
