package ru.ecom.expomc.web.tags;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.ecom.expomc.ejb.domain.impdoc.IUrlEditable;
import ru.ecom.expomc.ejb.services.check.ICheckService;
import ru.ecom.expomc.ejb.services.messages.ICheckMessageService;
import ru.ecom.expomc.web.actions.checkproperty.CheckPropertyListAction;
import ru.ecom.expomc.web.actions.importdata.ImportDataViewHelper;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;


public class MessageBadPropertiesTag extends SimpleTagSupport {
	
    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        
        Object aObj = request.getAttribute("entity") ;
        try {
            JspWriter out = ctx.getOut() ;
            if(aObj==null) {
            	out.println("Нет атрибута entity");
            	return ;
            }
            
			ICheckMessageService service = Injection.find(request).getService(ICheckMessageService.class) ;
			long messageId = Long.parseLong(request.getParameter("id")) ; 
			Collection<String> badProperties = service.getBadProperties(messageId) ;
			//System.out.println("badProperties="+badProperties) ;
			if(badProperties==null) return ;
			ImportDataViewHelper.printTableHeader(out) ;
			for (String prop : badProperties) {
				//System.out.println("prop="+prop) ;
				if(StringUtil.isNullOrEmpty(prop)) {
					out.print("<tr><td colspan='3'>Свойство равно null. <a href='entityParentView-exp_check.do?id=") ;
					out.print(service.getCheckByMessage(messageId)) ;
					out.println("'>Изменить проверку</a></td></tr>") ;
				} else {
					//String methodName = PropertyUtil.getGetterMethodNameForProperty(prop) ;
					Method method = PropertyUtil.getGetterMethod(aObj.getClass(), prop) ; //aObj.getClass().getMethod(methodName);
					ImportDataViewHelper.print(aObj, out, method) ;
				}
			}
			out.println("</table>");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e) ;
		}
        
    }
}
