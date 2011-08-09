package ru.ecom.web.tags;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

/**
 * Выполнить запрос
 */
public class WebQueryTag extends AbstractGuidSimpleSupportTag {
	private final static Logger LOG = Logger.getLogger(WebQueryTag.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    public void doTag() throws JspException, IOException {
    	printIdeStart() ;
        //JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        try {
			IWebQueryService service = Injection.find(request).getService(IWebQueryService.class) ;
			Object result = StringUtil.isNullOrEmpty(theNativeSql) 
				? service.executeHql(theHql)
				: service.executeNativeSql(theNativeSql)		;
			request.setAttribute(theName, result) ;
		} catch (Exception e) {
			LOG.error("Ошибка выполнения запроса: \n hql: "+theHql+", native: \n"+theNativeSql,e);
			throw new IllegalStateException("Ошибка выполнения "+theHql+" "+theNativeSql+": "+e.getMessage(),e) ;
		}
    	printIdeEnd() ;
    }
    
    /** Родной SQL для базы */
	@Comment("Родной SQL для базы")
	public String getNativeSql() {
		return theNativeSql;
	}

	public void setNativeSql(String aNativeSql) {
		theNativeSql = aNativeSql;
	}

	/** Родной SQL для базы */
	private String theNativeSql;
	
    /** Запрос на HQL */
	public String getHql() {
		return theHql;
	}

	public void setHql(String aHql) {
		theHql = aHql;
	}

	/** Куда выводить результат */
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Куда выводить результат */
	private String theName;
	/** Запрос на HQL */
	private String theHql;
}
