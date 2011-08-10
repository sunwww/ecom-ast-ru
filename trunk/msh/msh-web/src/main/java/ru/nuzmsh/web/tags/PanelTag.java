package ru.nuzmsh.web.tags;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.util.BaseValidatorFormUtil;
import ru.nuzmsh.web.util.IdeTagHelper;

/**
 * @jsp.tag name="panel"
 *          display-name="Panel"
 *          body-content="scriptless"
 *          description="Panel"
 */
public class PanelTag extends AbstractGuidSimpleSupportTag {

    /**
     * Название панели
     * @jsp.attribute   description="Название панели"
     *                  required="false"
     *                  rtexprvalue="true"
     *
     * */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;

        PageContext pageContext = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest() ;

        // IDE
        printIdeStart("Panel") ;
        
        out.print("<table") ;
        StringBuilder sb = new StringBuilder() ;
        if(BaseValidatorFormUtil.isViewOnly(pageContext)) {
        	sb.append("viewOnly") ;
        }
        
        if(request.getParameter("debug")!=null || theIdeTagHelper.isInIdeMode(getJspContext())) {
        	sb.append(" debug") ;
            out.print(" border='1'") ;
        }
        if(sb.length()>0) {
        	out.print(" class='") ;
        	out.print(sb);
        	out.print('\'');
        }
        if(!StringUtil.isNullOrEmpty(theStyleId)) {
            out.print(" id='") ;
            out.print(theStyleId) ;
            out.println("'");
        }        
        out.print(">") ;
        if(getJspBody()!=null) getJspBody().invoke(out);
        out.println("</table>") ;

        // IDE
        printIdeEnd() ;
    }

    
    /**
     * Ширина колонок
     * @jsp.attribute   description="Ширина колонок"
     *                  required="false"
     *                  rtexprvalue="true"
     */
	public String getColsWidth() {
		return theColsWidth;
	}

	protected Collection<String> getColsWidthArray() {
		return theColsWidthArray ;
	}
	
	public void setColsWidth(String aColsWidth) {
		theColsWidth = aColsWidth;
		if(!StringUtil.isNullOrEmpty(aColsWidth)) {
			 StringTokenizer st = new StringTokenizer(aColsWidth, " ,;.") ;
			 theColsWidthArray = new LinkedList<String>() ;
			 while(st.hasMoreTokens()) {
				 theColsWidthArray.add(0, st.nextToken()) ;
			 }
		}
	}

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
	
	/**
	 * GUID
	 * @jsp.attribute   description = "GUID"
	 *                     required = "false"
	 *                  rtexprvalue = "true"
	 */
	public String getGuid() {
	        return theGuid;
	}
	public void setGuid(String aGuid) {
	        theGuid = aGuid;
	}
	/** GUID */
	private String theGuid;
	

	/** Идентификатор элемента */
	private String theStyleId;	
	private List<String> theColsWidthArray ;
	
	/** Ширина колонок */
	private String theColsWidth;
    /** Название панели */
    private String theTitle ;
    
    private final IdeTagHelper theIdeTagHelper = IdeTagHelper.getInstance(); 
}
