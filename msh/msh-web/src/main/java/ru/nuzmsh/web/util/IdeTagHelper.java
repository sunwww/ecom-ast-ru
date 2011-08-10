package ru.nuzmsh.web.util;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.IGuidSupport;
import ru.nuzmsh.web.tags.helper.RolesHelper;

public class IdeTagHelper {

	public boolean isInIdeMode(JspContext aContext) {
		try {
			
			// не показываем, если есть cookie showTags = false ;
			PageContext pageContext = (PageContext) aContext ;
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;
			Cookie[] cookies = request.getCookies() ;
			if(cookies!=null) {
				for(Cookie c : cookies) {
					if("showTags".equals(c.getName())
							&& "false".equals(c.getValue())) {
						return false ;
					}
				}
			}
			
			return RolesHelper.checkRoles("/Policy/Config/IdeMode/Enabled"
					, aContext) ;
		} catch (Exception e) {
			return false ;
		}
	}

	public void printParentEnd(JspContext aJspContext) {
		if(isInIdeMode(aJspContext)) {
			try {
				aJspContext.getOut().println("</div>");
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public void printParentStart(String aParentName,JspContext aJspContext) {
		if(isInIdeMode(aJspContext)) {
			try {
				aJspContext.getOut().println("<div class='ideParent"+aParentName+"'>");
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public void printMarker(IGuidSupport aTag, JspContext aJspContext ) {
		printMarker(aTag.getClass().getSimpleName(), aTag, aJspContext);
	}	
	
	public void printMarker(String aTagName, IGuidSupport aTag, JspContext aJspContext ) {
			if(isInIdeMode(aJspContext)) {
				saveJspPath(aJspContext);
				String guid = aTag.getGuid() ;
				if(!StringUtil.isNullOrEmpty(guid)) {
					try {
						aJspContext.getOut().println("<div id='"+guid
								+"' class='idetag tagname"+aTagName+"'></div>");
					} catch (IOException e) {
						throw new IllegalStateException(e);
					}
				}
			}
	}
	
	private IdeTagHelper() {
	}
	
	public void saveJspPath(JspContext aJspContext) {
		if(aJspContext instanceof PageContext) {
			PageContext pageContext = (PageContext) aJspContext ;
	        if(pageContext.getRequest().getAttribute("ideMode_jspPath")==null) {
	        	pageContext.getRequest().setAttribute("ideMode_jspPath"
	        			, getJspPath(pageContext));
	        }
		}
	}
	
	private String getJspPath(PageContext aPageContext) {
		try {
			Method m = aPageContext.getClass().getMethod("getServlet") ;
			Object servletObject = m.invoke(aPageContext) ;
			String jspClassName = servletObject.getClass().getName() ;
			jspClassName = jspClassName.replace('.', '/');
			jspClassName = jspClassName.replace("_jsp", ".jsp");
			jspClassName = jspClassName.replace("_002d","-");
			jspClassName = jspClassName.replace("_005f","_");
			jspClassName = jspClassName.replace("org/apache/jsp/","/");
			return jspClassName ;
		} catch (Exception e) {
			e.printStackTrace() ;
			return "error_"+e.getMessage();
		}
		
	}
	
	public static IdeTagHelper getInstance() {
		return INSTANCE ;
	}
	
	private static final IdeTagHelper INSTANCE = new IdeTagHelper() ;

	public void showException(IGuidSupport aTag, Exception e, JspContext aJspContext) {
		e.printStackTrace();
		try {
			JspWriter out = aJspContext.getOut() ;
			out.println("<div class='tagError'>") ;
			printMarker(aTag, aJspContext) ;
			out.println("<span class='tagError'>"+e.getMessage()+"</span>");
			out.println("</div>") ;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


}
