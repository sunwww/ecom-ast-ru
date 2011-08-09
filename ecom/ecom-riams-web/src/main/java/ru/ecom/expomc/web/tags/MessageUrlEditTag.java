package ru.ecom.expomc.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.ecom.expomc.ejb.domain.impdoc.IUrlEditable;
import ru.ecom.expomc.web.actions.checkproperty.CheckPropertyListAction;


public class MessageUrlEditTag extends SimpleTagSupport {
	
    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        
        Object aObj = request.getAttribute("entity") ;
        
        JspWriter out = ctx.getOut() ;
        if(aObj instanceof IUrlEditable) {
    		IUrlEditable urlEditable = (IUrlEditable) aObj ;
    		out.print("<li><a target='stac' href='") ;
    		out.print(urlEditable.getUrlEdit()) ;
    		out.println("'>Изменить исходные данных</a></li>");
    	}
    }
}
