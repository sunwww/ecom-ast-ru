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
import ru.nuzmsh.web.tags.helper.RolesHelper;

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
        String isReportBase = null ;
        if (theIsReportBase!=null &&theIsReportBase.equals(Boolean.TRUE)
        		&& RolesHelper.checkRoles("/Policy/Config/IsReportBase", ctx)) {
        	isReportBase = Injection.getWebName(request, null) ;
        	isReportBase = isReportBase.substring(0,1)+"rep"+isReportBase.substring(1) ;
        	//System.out.println(isReportBase+"==--- запроса: \n hql: "+theHql+", native: \n"+theNativeSql) ;
        }
        try {
        	
			IWebQueryService service = Injection.find(request,isReportBase).getService(IWebQueryService.class) ;
			Integer maxResult = null;
			if (theMaxResult!=null && !theMaxResult.equals("")) {
				maxResult = Integer.valueOf(theMaxResult) ;
			}
			Object result = StringUtil.isNullOrEmpty(theNativeSql) 
				? service.executeHql(theHql,maxResult)
				: service.executeNativeSql(theNativeSql,maxResult)		;
			request.setAttribute(theName, result) ;
		} catch (Exception e) {
			LOG.error("Ошибка выполнения запроса: \n hql: "+theHql+", native: \n"+theNativeSql,e);
			throw new IllegalStateException("Ошибка выполнения "+theHql+" "+theNativeSql+": "+e.getMessage(),e) ;
		}
        try {
        if (!StringUtil.isNullOrEmpty(theNameFldSql)) {
				request.setAttribute(theNameFldSql, (StringUtil.isNullOrEmpty(theNativeSql) 
						? theHql : theNativeSql).replaceAll("\n", " ")) ;
		}
        } catch(Exception e) {
        	throw new IllegalStateException("Ошибка сохранения запроса в свойство "+theNameFldSql+": "+e.getMessage(),e) ;
    		
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
	
	/** Кол-во строк */
	@Comment("Кол-во строк")
	public String getMaxResult() {
		return theMaxResult;
	}

	public void setMaxResult(String aMaxResult) {
		theMaxResult = aMaxResult;
	}

	/** Кол-во строк */
	private String theMaxResult;

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

	/** Куда сохранять запрос */
	@Comment("Куда сохранять запрос")
	public String getNameFldSql() {
		return theNameFldSql;
	}

	public void setNameFldSql(String aNameFldSql) {
		theNameFldSql = aNameFldSql;
	}
	
	/** Брать данные из отчетной базы */
	@Comment("Брать данные из отчетной базы")
	public Boolean getIsReportBase() {return theIsReportBase;}
	public void setIsReportBase(Boolean aIsReportBase) {theIsReportBase = aIsReportBase;}

	/** Брать данные из отчетной базы */
	private Boolean theIsReportBase;
	/** Куда сохранять запрос */
	private String theNameFldSql;
	/** Куда выводить результат */
	private String theName;
	/** Запрос на HQL */
	private String theHql;
}
