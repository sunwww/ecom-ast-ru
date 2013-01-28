package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.messages.ErrorMessage;
import ru.nuzmsh.web.messages.UserMessage;

/**
 * Печать сообщения об ошибке
 *
 * @jsp.tag     name = "userMessage"
 *      display-name = "Печать пользовательских сообщений"
 *      body-content = "scriptless"
 *       description = "Печать пользовательских сообщений"
 *
 */
public class UserMessageTag  extends SimpleTagSupport {

    public void doTag() throws JspException, IOException {
        PageContext ctx = (PageContext) getJspContext() ;
        List<UserMessage> messages = UserMessage.findInRequest(ctx.getRequest()) ;
        if(messages!=null) {
        	for (UserMessage message:messages) {
	            JspWriter out = getJspContext().getOut() ;
	            out.println("<table id='userMessageContainer"+message.getId()+"' style='margin-left: 4em'><tr><td>");
	            out.println(" <div class='userMessage'>") ;
	            out.println(" <a href='javascript:void(0)' class='userMessageClose' title='Убрать сообщение' onclick='checkUserMessage("+message.getId()+")'>Убрать</a>") ;
	            out.println(message.getInfo()) ;
	            out.println("<br/>") ;
	            out.println("<u>"+message.getTitle()+"</u>") ;
	            out.println("<br/>") ;
	            out.println(message.getMessage()) ;
	            if (message.getUrl()!=null) {
	            	out.println("<a href='javascript:void(0)' onclick='checkUserMessage("+message.getId()+");window.location=\""+message.getUrl()+"\";'>Подробно...</a>") ;
	            }
	            out.println(" </div>") ;
	            out.println("</td></tr></table>") ;
	        }
        }
    }
}
