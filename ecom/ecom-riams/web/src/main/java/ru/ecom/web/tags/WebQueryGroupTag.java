package ru.ecom.web.tags;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class WebQueryGroupTag  extends AbstractGuidSimpleSupportTag {
	private static final Logger LOG = Logger.getLogger(WebQueryTag.class);

    public void doTag() throws JspException, IOException {
    	printIdeStart() ;
        //JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        try {
			IWebQueryService service = Injection.find(request).getService(IWebQueryService.class) ;
			Integer maxResult = null;
			if (theMaxResult!=null && !theMaxResult.equals("")) {
				maxResult = Integer.valueOf(theMaxResult) ;
			}
			Collection<WebQueryResult> result = new ArrayList<>();
			Collection<WebQueryResult> resultGroup = service.executeNativeSql(theGroupNativeSql,maxResult) ;
			
			for (WebQueryResult wqr:resultGroup) {
				result.add(wqr) ;
				String natSql ;//= theNativeSql.replaceAll(":group", ""+wqr.get2()) ;
				if (wqr.get2()==null) {
					natSql = theNativeSql.replaceAll("= :group", " is null") 
								.replaceAll("=:group", " is null") ;
				} else {
					natSql = theNativeSql.replaceAll(":group", "'"+wqr.get2()+"'") ; 
							
				}
				result.addAll(service.executeNativeSql(natSql,maxResult)) ;
			}
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

	/** Родной SQL для базы для группировки */
	@Comment("Родной SQL для базы для группировки")
	public String getGroupNativeSql() {return theGroupNativeSql;}
	public void setGroupNativeSql(String aGroupNativeSql) {theGroupNativeSql = aGroupNativeSql;}

	/** Родной SQL для базы для группировки */
	private String theGroupNativeSql;
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

	/** Поле группировки */
	@Comment("Поле группировки")
	public String getGroupFld() {
		return theGroupFld;
	}

	public void setGroupFld(String aGroupFld) {
		theGroupFld = aGroupFld;
	}

	/** nameGroupFldSql */
	@Comment("nameGroupFldSql")
	public String getNameGroupFldSql() {
		return theNameGroupFldSql;
	}

	public void setNameGroupFldSql(String aNameGroupFldSql) {
		theNameGroupFldSql = aNameGroupFldSql;
	}

	/** nameGroupFldSql */
	private String theNameGroupFldSql;
	/** Поле группировки */
	private String theGroupFld;
	/** Куда сохранять запрос */
	private String theNameFldSql;
	/** Куда выводить результат */
	private String theName;
	/** Запрос на HQL */
	private String theHql;
}
