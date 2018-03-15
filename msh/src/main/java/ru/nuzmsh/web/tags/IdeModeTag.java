package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.FormBeanConfig;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="ideMode"
 *              display-name="IDE MODE"
 *              body-content="empty"
 *              description="IDE MODE"
 * @author ESinev
 */
public class IdeModeTag extends TagSupport {

	
	@Override
    public int doStartTag() throws JspException {
		if(IdeTagHelper.getInstance().isInIdeMode(pageContext)) {
			try {
				JspWriter out = pageContext.getOut();
				out.println("<script type='text/javascript' src='/skin/js/js/main/idemode-CA11386e96365.js'></script>") ;
				
				out.println("<li class='infoDiv'><div class='ideMode-jspPath' id='ideMode_jspPath'>"
						+pageContext.getRequest().getAttribute("ideMode_jspPath")
						+"</div></li>");
				
				ActionMapping actionMapping = (ActionMapping) pageContext.getRequest().getAttribute(Globals.MAPPING_KEY);
				if(actionMapping!=null) {
					String formName = actionMapping.getName();
					printDiv(out, "ideMode_formName", "ideMode-formName", formName);
					
					FormBeanConfig cfg = actionMapping.getModuleConfig().findFormBeanConfig(formName) ;
					if(cfg!=null) {
						printDiv(out, "ideMode_formClass", "ideMode-formClass", cfg.getType());
					}
				}
				
				
				
			} catch (Exception e) {
				throw new JspException(e);
			}
		}
        return EVAL_BODY_INCLUDE;
	}
	
	private static void printDiv(JspWriter aOut, String aId, String aClass, String aValue) throws IOException {
		if(!StringUtil.isNullOrEmpty(aValue)) {
			aOut.println("<li class='infoDiv'><div class='"+aClass+"' id='"+aId+"'>"
					+aValue
					+"</div></li>");
		}
	}
	
//	out.println("<script type='text/javascript' src='/ext/adapter/yui/yui-utilities.js'></script>") ;
//	out.println("<script type='text/javascript' src='/ext/adapter/yui/ext-yui-adapter.js'></script>") ;
//	out.println("<script type='text/javascript' src='/ext/ext-all.js'></script>") ;
//	out.println("<script type='text/javascript' src='./dwr/interface/IdeModeService-CA11386e96365.js'></script>") ;
//	out.println("<script type='text/javascript'>") ;
//	out.println("   msh.idemode.init('"+pageContext.getRequest().getAttribute("ideMode_jspPath")+"') ;") ;
//	out.println("</script>") ;
//	out.println("<script language='javascript' src='ru.ecom.gwt.idemode.Main.nocache.js'></script>") ;

}
