package ru.nuzmsh.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @jsp.tag name="commentBox"
 *          display-name="Комментарий для форм"
 *          body-content="scriptless"
 *          description="Комментарий для форм"
 */
public class CommentBoxTag extends AbstractGuidSimpleSupportTag {

	
	// [start] Аттрибуты
	
	/** 
	 * Текст комментария
	 * @jsp.attribute   description = "Текст комментария"
	 *                     required = "true"
	 *                  rtexprvalue = "true"
	 */
	public String getText() {
		return theText;
	}

	public void setText(String aText) {
		theText = aText;
	}

	
	/** 
	 * Количество ячеек до элемента
	 * @jsp.attribute   description = "Количество ячеек до элемента"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public int getColSpanBefore() {
		return theColSpanBefore;
	}

	public void setColSpanBefore(int aColSpanBefore) {
		theColSpanBefore = aColSpanBefore;
	}

	/** 
	 * Количество ячеек, занятое комментарием
	 * @jsp.attribute   description = "Количество ячеек, занятое комментарием"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public int getColSpan() {
		return theColSpan;
	}

	public void setColSpan(int aColSpan) {
		theColSpan = aColSpan;
	}

	// [end]
	
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        try {
        	out.print("<td colspan='"+theColSpanBefore+"'>") ;
        	super.printMarker();
        	out.println("</td>");
        	String text = theText!=null ? theText.replace("&lt;", "<") : "no text!!!";
        	text = text.replace("&gt;", ">") ;
        	out.println("<td class='commentBox' colspan='"+theColSpan+"'>"+text+"</td>");
        } catch (Exception e) {
        	showException(e);
        }
    }

    // [start] FIELDS
	/** Количество ячеек до элемента */
	private int theColSpanBefore = 1;
	/** Количество ячеек, занятое комментарием */
	private int theColSpan = 1;
	/** Текст комментария */
	private String theText;
	// [end]

    
}
