package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="row"
 *          display-name="row"
 *          body-content="scriptless"
 *          description="Row"
 */
public class RowTag extends AbstractGuidSimpleSupportTag {

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        
        JspTag parent = getParent() ;
        //System.out.println("panel parent = "+parent) ;
        if(parent instanceof PanelTag) {
        	PanelTag panel = (PanelTag) parent ;
        	if(panel.getColsWidthArray()!=null) {
        		theStack = new Stack<String>() ;
        		for(String s:panel.getColsWidthArray()) {
        			theStack.add(s);
        		}
    			this.getJspContext().setAttribute("colsWidthStack", theStack) ;
        		//System.out.println("theStack = "+theStack);
        	}
        }
        if(StringUtil.isNullOrEmpty(theStyleId)) {
            out.println(" <tr>") ;
        } else {
            out.print(" <tr id='") ;
            out.print(theStyleId) ;
            out.println("'>");
        }
        if(getJspBody()!=null) getJspBody().invoke(out);
        
		this.getJspContext().removeAttribute("colsWidthStack") ;
		
		// ide mode
        if(theIdeTagHelper.isInIdeMode(getJspContext())) {
        	out.println("<td>");
    		theIdeTagHelper.printMarker("Row", this, getJspContext());
        	out.println("</td>");
        }
        
        out.println(" </tr>") ;
    }
    
    private final IdeTagHelper theIdeTagHelper = IdeTagHelper.getInstance() ;

    /** 
	 * Идентификатор элемента
	 * @jsp.attribute   description = "Идентификатор элемента"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getStyleId() {
		return theStyleId;
	}

	public void setStyleId(String aStyleId) {
		theStyleId = aStyleId;
	}

	/** Идентификатор элемента */
	private String theStyleId;
    protected Stack<String> getColsWidthStack() {
    	return theStack ;
    }

    private Stack<String> theStack ;
}
