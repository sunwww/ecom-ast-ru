package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.json.JSONObject;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.messages.ClaimMessage;
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
        List<ClaimMessage> claim_messages = ClaimMessage.findInRequest(ctx.getRequest()) ;
        if(claim_messages!=null) {
        	for (ClaimMessage message:claim_messages) {
	            JspWriter out = getJspContext().getOut() ;
	            out.println("<table id='claimMessageContainer"+message.getId()+"' class='claimMessageContainer' style='margin-left: 4em'><tr><td>");
	            out.println(" <div class='claimMessage'>") ;
	            out.println(" <a href='javascript:void(0)' class='claimMessageClose' title='' onclick='checkClaimMessage("+message.getId()+",1)'>Убрать (заявка выполнена)</a>") ;
	            out.println(message.getInfo()) ;
	            out.println("<br/>") ;
	            out.println("<u>"+message.getTitle()+"</u>") ;
	            out.println("<br/>") ;
	            out.println(message.getMessage()) ;
	            out.println("<br/>") ;
	            out.println(" <br>ЗАЯВКА:&nbsp;&nbsp; <a href='javascript:void(0)' class='claimMessageYes' title='' onclick='checkClaimMessage("+message.getId()+",1)'>ВЫПОЛНЕНА</a>&nbsp;") ;
	            out.println("&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' class='claimMessageNo' title='' onclick='checkClaimMessage("+message.getId()+",0)'>невыполнена</a>") ;
	            out.println(" </div>") ;
	            out.println("</td></tr></table>") ;
	        }
        }
        List<UserMessage> messages = UserMessage.findInRequest(ctx.getRequest()) ;
        if(messages!=null&&!messages.isEmpty()) {
			JspWriter out = getJspContext().getOut() ;
			StringBuilder sql = new StringBuilder();
			//out.println("<script type='text/javascript'>");
			sql.append("<script type='text/javascript'>");

			sql.append("function showUserMessageTagOnce() {msh.effect.FadeEffect.putFade();}")
				.append("function closeUserMessageTagOnce() {msh.effect.FadeEffect.pushFade();}");
			//System.out.println("user_messages size = "+messages.size());
			for (UserMessage message:messages) {
				 //sql = new StringBuilder();
				sql.append(" jQuery.toast({")
					.append(" position: 'top-center'")
					.append(",heading:'").append(toHtmlString(message.getTitle())).append("'")
					.append(",text:'"+toHtmlString(message.getMessage())+(!StringUtil.isNullOrEmpty(message.getUrl())?"<a href=\""+message.getUrl()+"\" target=\"_blank\"> Перейти</a>'":"'"))
					.append(",hideAfter: false")
						.append(",beforeShow:function(){showUserMessageTagOnce() ;checkUserMessage(").append(message.getId()).append(");}")
						.append(",afterHidden:function(){closeUserMessageTagOnce() ;}")
				//	.append(",bgColor: '#ff0000'")
					.append(",stack:").append(messages.size())
					.append(",icon:'info'")
				.append("});");

			//	out.println(sql.toString());
					//.append(",afterHidden:function(){checkEmergencyMessage(param.id);}")

	            /* //переделываем на jQuery.toast
	            out.println("<table id='userMessageContainer"+message.getId()+"' class='userMessageContainer' style='margin-left: 4em'><tr><td>");
	            out.println(" <div class='userMessage'>") ;
	            out.println(" <a href='javascript:void(0)' class='userMessageClose' title='Убрать сообщение' onclick='checkUserMessage("+message.getId()+")'>Убрать</a>") ;
	            out.println(" <a href='javascript:void(0)' class='userMessageCloseAll' title='Убрать все сообщения' onclick='checkAllMessages(\"userMessageContainer\")'>Убрать все сообщения</a>") ;
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
	            */
	            //viewEmergencyUserMessage(aJsonId)
	        }
			sql.append("</script>");
			out.println(sql.toString());
			UserMessage.setInRequest(ctx.getRequest(),null);
        }
        
    }
    private String toHtmlString(String aString) {
    	aString=aString.replaceAll("\n"," ");
    	aString=aString.replaceAll("\r"," ");
    	aString=aString.replaceAll("\t"," ");
    	aString=aString.replaceAll("\"","\\\"");
    	aString=aString.replaceAll("\'","\\\\'");


    	return aString;
	}
}
