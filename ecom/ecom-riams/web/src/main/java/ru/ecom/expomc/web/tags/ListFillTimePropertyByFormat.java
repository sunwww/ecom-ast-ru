package ru.ecom.expomc.web.tags;

import org.apache.log4j.Logger;
import ru.ecom.expomc.ejb.uc.filltime.form.FillTimeForm;
import ru.ecom.expomc.ejb.uc.filltime.service.IFillTimeService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ListFillTimePropertyByFormat extends SimpleTagSupport {
	
	private static final Logger LOG = Logger.getLogger(ListFillTimePropertyByFormat.class);

	  /** Коллекция */
	@Comment("Коллекция")
	public String getCollection() {
		return theCollection;
	}

	public void setCollection(String aCollection) {
		theCollection = aCollection;
	}

	/** Коллекция */
	private String theCollection = "fillTimeProperties"; // FIXME
	
	   public void doTag() throws JspException, IOException {
	        PageContext ctx = (PageContext) getJspContext() ;
	        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
	        FillTimeForm form = (FillTimeForm) request.getAttribute("exp_fillTimeForm");
	        if(form!=null && form.getFormat()!=null && form.getFormat()!=0) {
		        try {
					IFillTimeService service = Injection.find(request).getService(IFillTimeService.class) ;
					request.setAttribute(theCollection, service.listByFormat(form.getId()));
				} catch (NamingException e) {
					LOG.error("form",e) ;
					throw new IllegalStateException(e);
				}
	        	
	        }
	      
	    }

}
