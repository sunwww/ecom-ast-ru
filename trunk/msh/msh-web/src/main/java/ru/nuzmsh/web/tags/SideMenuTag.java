package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * 
 * Меню для боковой панели
 * 
 * @jsp.tag name="sideMenu" display-name="Side menu" body-content="scriptless"
 *          description="Меню для боковой панели"
 * 
 */
public class SideMenuTag extends AbstractGuidSimpleSupportTag {

	private static final String ALREADY_SHOWED = String
			.valueOf(SideMenuTag.class);

	public static final String MUST_HIDDED = SideMenuTag.class + ".MUST_HIDDED";

	/**
	 * Название панели
	 * 
	 * @jsp.attribute description="Название панели" required="false"
	 *                rtexprvalue="true"
	 */
	public String getTitle() {
		return theTitle;
	}

	public void setTitle(String aTitle) {
		theTitle = aTitle;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		JspContext ctx = getJspContext();
		StringBuilder cssClassName = new StringBuilder();
		String styleId = UUID.randomUUID().toString();
		ctx.setAttribute(MUST_HIDDED, styleId, PageContext.REQUEST_SCOPE);
		try {
			if (theTitle != null && theTitle.startsWith("Добавить")) {
				cssClassName.append("menuAdd");
			} else if (theTitle != null && theTitle.startsWith("Показать")) {
				cssClassName.append("menuShow");
			} else if (theTitle != null && theTitle.startsWith("Показать все")) {
				cssClassName.append("menuShow");
			} else if (theTitle != null && theTitle.startsWith("Перейти")) {
				cssClassName.append("menuGo");
			} else if (theTitle != null && theTitle.startsWith("Печать")) {
				cssClassName.append("menuPrint");
			}
			// boolean canShow = !StringUtil.isNullOrEmpty(theExistsForm) &&
			// ctx.getAttribute(theExistsForm)!=null;
			// if(canShow) {
			if (ctx.getAttribute(ALREADY_SHOWED) == null) {
				out.println("<ul class='" + cssClassName + " firstMenu' id='"
						+ styleId + "'>");
				ctx.setAttribute(ALREADY_SHOWED, "SHOWED");
			} else {
				out.println("<ul class='" + cssClassName + " menu' id='"
						+ styleId + "'>");
			}

			// IDE
			IdeTagHelper.getInstance().printMarker(this, getJspContext());

			out.print("<li class='title'>");
			if (theTitle != null) {
				out.print(theTitle);
			}
			out.println("</li>");

			if (getJspBody() != null)
				getJspBody().invoke(out);

			out.println("</ul>");

			// }
		} finally {
			Object mustHidded = ctx.getAttribute(MUST_HIDDED, PageContext.REQUEST_SCOPE);
			if (mustHidded != null) {
				removeMustHidden(ctx);
				JavaScriptContext js = JavaScriptContext.getContext(ctx, this);
				if(theTitle!=null) {
					js.println("// "+theTitle) ;
				}
				js.println("$('" + styleId + "').style.display = 'none';");
			}
		}
	}

	/** Название панели */
	private String theTitle;
	// /** Показывать если есть форма */
	// private String theExistsForm ;

	public static void removeMustHidden(JspContext aCtx) {
    	aCtx.removeAttribute(SideMenuTag.MUST_HIDDED, PageContext.REQUEST_SCOPE);
	}
}
