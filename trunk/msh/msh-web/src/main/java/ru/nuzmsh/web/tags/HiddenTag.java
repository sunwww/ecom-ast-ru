package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="hidden" display-name="Hidden" body-content="JSP"
 *          description="Hidden"
 */
public class HiddenTag extends org.apache.struts.taglib.html.HiddenTag
		implements IGuidSupport {

	/**
	 * Название свойства
	 * 
	 * @jsp.attribute description="Название свойства" required="true"
	 *                rtexprvalue="true"
	 */
	public String getProperty() {
		return super.getProperty();
	}

	public void setProperty(String aProperty) {
		super.setProperty(aProperty);
		super.setStyleId(aProperty);
	}

	// ////////////////////////
	// IDE MODE

	@Override
	public int doStartTag() throws JspException {
		try {
			IdeTagHelper ide = IdeTagHelper.getInstance();
			if (ide.isInIdeMode(pageContext)) {
				ide.printParentStart("Hidden", pageContext);
				ide.printMarker("Hidden", this, pageContext);
				try {
					pageContext.getOut().println(getProperty());
				} catch (IOException e) {
					throw new JspException(e);
				}
			}
			return super.doStartTag();
		} catch (Exception e) {
			IdeTagHelper.getInstance().showException(this, e, pageContext);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		IdeTagHelper ide = IdeTagHelper.getInstance();
		ide.printParentEnd(pageContext);
		return super.doEndTag();
	}

	/**
	 * GUID
	 * 
	 * @jsp.attribute description = "GUID" required = "false" rtexprvalue =
	 *                "true"
	 */
	public String getGuid() {
		return theGuid;
	}

	public void setGuid(String aGuid) {
		theGuid = aGuid;
	}

	/** GUID */
	private String theGuid;

}
