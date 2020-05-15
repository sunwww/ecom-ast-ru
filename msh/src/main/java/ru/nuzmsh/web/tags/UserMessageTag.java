package ru.nuzmsh.web.tags;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.messages.ClaimMessage;
import ru.nuzmsh.web.messages.UserMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

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
			sql.append("<script type='text/javascript'>");

			sql.append("function showUserMessageTagOnce() {msh.effect.FadeEffect.putFade();}")
				.append("function closeUserMessageTagOnce() {msh.effect.FadeEffect.pushFade();}");
			for (UserMessage message:messages) {
				sql.append(" jQuery.toast({")
					.append(" position: 'top-center'")
					.append(",heading:'").append(toHtmlString(message.getTitle())).append("'")
					.append(",text:'"+toHtmlString(message.getMessage())+(!StringUtil.isNullOrEmpty(message.getUrl())?"<a href=\""+message.getUrl()+"\" target=\"_blank\"> Перейти</a>'":"'"))
					.append(",hideAfter: false")
						.append(",beforeShow:function(){showUserMessageTagOnce() ;checkUserMessage(").append(message.getId()).append(");}")
						.append(",afterHidden:function(){closeUserMessageTagOnce() ;}")
					.append(",stack:").append(messages.size())
					.append(",icon:'info'")
				.append("});");
	        }


			sql.append("function quickDelMessage(e){ ");
			sql.append("if(e.keyCode=='27'){" +
					"jQuery(\"div.jq-toast-wrap\").remove();" +
					"for (var i=0; i<2; i++) " +
					"if (document.getElementById(\"fadeEffect\")!=null) " +
					//при входе в систему и выводе одновременно планового и экстренного надо закрывать 2 эффекта
					"	document.getElementById(\"fadeEffect\").remove();}}");
			sql.append(" addEventListener(\"keydown\", quickDelMessage);");
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
