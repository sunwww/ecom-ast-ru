package ru.nuzmsh.web.tags.helper;

import javax.servlet.jsp.PageContext;

/**
 * @author esinev
 * Date: 13.03.2006
 * Time: 11:47:11
 */
public class JavaScriptContextLast extends JavaScriptContext {

    protected static final String ATTRIBUTE_KEY = String.valueOf(JavaScriptContextLast.class)  ;

    public static JavaScriptContext getContext(PageContext aPageContext, Object aOwner) {
        return getContext(aPageContext, aOwner, ATTRIBUTE_KEY) ;
    }

}
