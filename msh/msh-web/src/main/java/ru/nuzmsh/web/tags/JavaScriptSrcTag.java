package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.filter.caching.CacheUniqueUtil;

/**
 * JavaScript src
 * @jsp.tag     name = "javascriptSrc"
 *      display-name = "JavaScript Src"
 *      body-content = "scriptless"
 *       description = "JavaScript Src"
 */
public class JavaScriptSrcTag extends SimpleTagSupport {

    public static final String ID = String.valueOf((int) (Math.random() * 1000.0))  ;
    /**
     * Путь к JavaScript
     * @jsp.attribute   description = "Путь к JavaScript"
     *                     required = "true"
     *                  rtexprvalue = "true"
     */
    public String getSrc() { return theSrc ; }
    public void setSrc(String aSrc) { theSrc = aSrc ; }

    /** Путь к JavaScript */
    private String theSrc ;

    public void doTag() throws JspException, IOException {

        JspWriter out = getJspContext().getOut() ;
        out.print("<script type='text/javascript' src='") ;
        String rewrited = theSrc.replace(".js", CacheUniqueUtil.getUniqueId()+".js") ;
        out.print(rewrited) ;
        //out.print(theSrc) ;
        //out.print("?tmp=") ;
        //out.print(ID) ;
        out.print("'></script>")  ;
    }
}
