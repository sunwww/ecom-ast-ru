package ru.ecom.web.tags;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.taglib.html.Constants;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

public class CheckGroupTag extends AbstractGuidSimpleSupportTag {
		
	    /** Пустая таблица */
		public boolean isEmpty() {return theIsEmpty;}

		/** Пустая таблица */
		private boolean theIsEmpty=true;
		public void doTag() throws JspException {
	    	printIdeStart("CheckBoxGroup") ;
	    	PageContext ctx = (PageContext) getJspContext() ;
	        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
            JspWriter out = ctx.getOut();	        
            ActionForm form = (ActionForm) ctx.getAttribute(Constants.BEAN_KEY, PageContext.REQUEST_SCOPE);
            
	    	try {
	    		String value=(String) PropertyUtil.getPropertyValue(form,  theProperty) ; 
	    		System.out.println("--cg---value="+value) ;
				IWebQueryService service = Injection.find(request).getService(IWebQueryService.class) ;
				String valueSql="" ;
				if (value!=null &&!value.trim().equals("")) {
					valueSql = ",case when "+theTableId+" in ("+value+") then 1 else null end as eqval" ;
				}
				Collection<WebQueryResult> col = service.executeNativeSql("select "+theTableId+" as id, "+theTableField+" as fld "+valueSql+" from "+theTableName);
				System.out.println("--cg---col="+col.size()) ;
				System.out.println("--cg---sql="+"select "+theTableId+" as id, "+theTableField+" as fld "+valueSql+" from "+theTableName) ;
		        boolean isEmpty=col.isEmpty();
		        boolean isFirst = true ;
		        if (col == null) {
		            throw new JspException("Нет Collection в request.getAttribute(" + theTableName + ")");
		        } else {
	
		        }
		        
		        if (!isEmpty) {
		        	out.println("<td class='label' title='"+(theLabel!=null?theLabel+":":"")+" ("+theProperty+")' colspan='1'>");
		        	out.println("<label id='"+theProperty+"Label'>"+(theLabel!=null?theLabel+":":"")+":</label>");
		        	out.println("<input type='hidden' id='"+theProperty+"' name='"+theProperty+"' value='"+value+"'/>") ;
		        	out.println("</td>");
		        	out.println("<td>");
		            out.println("<table>");
	
	
	                if (col != null) {
	                    if (col.size() == 0) {
	                        return ;
	                    }
	                   
	                    for (WebQueryResult row:col) {
	                    	Object currentId =  row.get1();
	                    	Object currentTitle = row.get2() ;
	                        boolean isChecked = row.get3()!=null?true:false ;
	                        
	                        out.print("<tr onclick='' class='") ;
	                        out.print(theTableName) ;
	                        out.print(' ') ;
	                        out.print(currentId) ;
	                        out.print(' ') ;
	                        
	                        out.print("'>");
	
	                        out.println("<td>") ;
	                        String typeId = theProperty+"_"+currentId ;
	                        out.println("<input id='"+theProperty+"Temp' name='"+theProperty+"Temp' value='"+currentId+"' type='checkbox' onclick=\"$('"+theProperty+"').value=getCheckedCheckBox(this.form,this.name,',')\"");
	                        if (isChecked) out.println(" checked='true'") ;
	                        out.println("/>") ;
	                        out.println("</td>") ;
	                        out.println("<td>") ;
	                        out.println(currentTitle) ;
	                        out.println("</td>") ;
	
	                        out.println("</tr>");
	                    }
	    	            out.println("</table");
	    	        	out.println("</td>");
	                }
		        }
	        } catch (Exception e) {
	                showException(e);
            } finally {
                
            }
	        
	        printIdeEnd();
	        return ;
		}
	    /**
	     * Название таблицы
	     *
	     * @jsp.attribute   description="Название таблицы"
	     *                  required="true"
	     *                  rtexprvalue="true"
	     */
	    public String getTableName() {return theTableName;}
	    public void setTableName(String aTableName) {theTableName = aTableName;}

	    /**
	     * Название поля id
	     *
	     * @jsp.attribute   description="Название поля id"
	     *                  required="true"
	     *                  rtexprvalue="true"
	     */
	    public String getTableId() {return theTableId;}
		public void setTableId(String aTableId) {theTableId = aTableId;}

	    /**
	     * Название поля название
	     *
	     * @jsp.attribute   description="Название поля название"
	     *                  required="true"
	     *                  rtexprvalue="true"
	     */	
		public String getTableField() {return theTableField;}
		public void setTableField(String aTableField) {theTableField = aTableField;}

	    /**
	     * Название поля название
	     *
	     * @jsp.attribute   description="Название свойства"
	     *                  required="true"
	     *                  rtexprvalue="true"
	     */	
		public String getProperty() {return theProperty;}
		public void setProperty(String aProperty) {theProperty = aProperty;}

		/** Заголовок */
		public String getLabel() {return theLabel;}
		public void setLabel(String aLabel) {theLabel = aLabel;}

		
		/** Заголовок */
		private String theLabel;
		/** Название свойства */
		private String theProperty;
		/** Описание значения */
		private String theTableField;
		/** Название поля Id */
		private String theTableId;
	    /** Таблица */
		private String theTableName;
}