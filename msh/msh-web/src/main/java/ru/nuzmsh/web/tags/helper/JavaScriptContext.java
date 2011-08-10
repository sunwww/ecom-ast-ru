package ru.nuzmsh.web.tags.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author esinev
 * Date: 13.03.2006
 * Time: 11:47:11
 */
public class JavaScriptContext {

    private static final String ATTRIBUTE_KEY = String.valueOf(JavaScriptContext.class)  ;

    public static JavaScriptContext getContext(JspContext aPageContext, Object aOwner, String aKey) {
        //HttpServletRequest request = (HttpServletRequest) aPageContext.getRequest() ;
        JavaScriptContext ctx = (JavaScriptContext) aPageContext.getAttribute(aKey, PageContext.REQUEST_SCOPE) ;
        if(ctx==null) {
            ctx = new JavaScriptContext();
            aPageContext.setAttribute(aKey, ctx, PageContext.REQUEST_SCOPE) ;
        }
        ctx.println("") ;
        ctx.println("// "+aOwner.getClass());
        return ctx;
    }
    
    public static JavaScriptContext getContext(JspContext aPageContext, Object aOwner) {
        return getContext(aPageContext, aOwner, ATTRIBUTE_KEY) ;
    }
    

    public void print(CharSequence aCode) {
        theCode.append(aCode) ;
    }

    public void println(CharSequence aCode) {
        theCode.append(aCode) ;
        theCode.append('\n') ;
    }
    
    public void println(Object ...args) {
    	StringBuilder sb = new StringBuilder() ;
    	for(Object s: args) {
    		sb.append(s) ;
    	}
    	println(sb) ;
    }

    public void writeJavaScript(JspWriter aOut) throws IOException {
        aOut.println(theCode) ;
    }


    private final StringBuilder theCode = new StringBuilder();


}
