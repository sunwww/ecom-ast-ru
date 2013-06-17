package ru.ecom.template.web.dwr;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import ru.nuzmsh.util.StringUtil;
import ru.ecom.diary.ejb.service.template.ICategoryTreeService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 24.01.2007
 * Time: 13:52:18
 * To change this template use File | Settings | File Templates.
 */
public class CategoryTreeServiceJs {
    public String getVocabulary(String aIdClassif, HttpServletRequest aRequest) throws NamingException {
        if (StringUtil.isNullOrEmpty(aIdClassif)) {
            return "" ;
        } else {
            ICategoryTreeService service = Injection.find(aRequest).getService(ICategoryTreeService.class) ;
            return service.getClazz(Long.valueOf(aIdClassif)) ;
//            return "Mkb" ;
        }
    }
    public String getCategoryMedService(String aName,String aFunction, String aTable, Long aParent, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String table , fldId, fldView, fldParent ;
    	if (aTable.toUpperCase().equals("MEDSERVICE")) {
    		table="MedService ms" ;fldId="ms.id";fldView="ms.code||' '||ms.name" ;fldParent="ms.parent";
    	} else if (aTable.toUpperCase().equals("PRICEPOSITION")) {
    		table="PricePosition pp" ;fldId="pp.id";fldView="pp.code||' '||pp.name" ;fldParent="pp.parent";
    	} else {
    		return "" ;
    	}
    	
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(fldId).append(" as fldId ").append(fldView).append(" as fldView")
    		.append(" from ").append(table)
    		.append(" where ").append(fldParent) ;
    	if (aParent!=null&&aParent>aParent.valueOf(0)) {
    		sql.append(" is null") ;
    	} else {
    		sql.append("=").append("'").append(aParent).append("'") ;
    	}
    	Collection<WebQueryResult> list=service.executeNativeSql(sql.toString()) ;
    	StringBuilder rs = new StringBuilder() ;
    	for (WebQueryResult wqr : list) {
    		rs.append("<div id='").append(aName).append(wqr.get1()).append("' onclick='").append(aFunction)
    			.append("(").append("\"").append(wqr.get1()).append("\"").append(")").append("'>")
    			.append(wqr.get2()).append("</div>") ;
    	}
    	
    	return rs.toString() ;
    }
    
}
