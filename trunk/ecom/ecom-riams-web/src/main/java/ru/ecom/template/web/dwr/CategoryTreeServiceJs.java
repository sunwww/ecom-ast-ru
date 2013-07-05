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
    public String getCategoryMedService(String aName,String aFunction, String aTable, Long aParent,int aLevel, int aAddParam, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String table , fldId, fldView, fldParent , fldOrderBy , fldIsChild,join="",whereAdd="" ;
    	int level=aLevel+1;
    	if (aTable.toUpperCase().equals("PRICEMEDSERVICE")) {
    		table="PricePosition pp" ;fldId="case when pp.dtype='PriceGroup' then pp.id else pms.id end";
    				fldView="case when pp.dtype='PriceGroup' then pp.code||' '||pp.name else pp.code||' '||pp.name||' ('||pp.cost||')' end" 
    				;fldParent="pp.parent_id";fldOrderBy="pp.code";
    				join=" left join pricemedservice pms on pms.priceposition_id=pp.id ";
    				whereAdd=" and (pp.dtype='PriceGroup' or pms.id is not null)" ;
    				
    					whereAdd=" and pp.priceList_id='"+aAddParam+"' "+whereAdd ;
    				
    		fldIsChild = "(select count(*) from "+table+"1 where pp1.parent_id="+fldId +")";
    	} else if (aTable.toUpperCase().equals("PRICEPOSITION")) {
    		table="PricePosition pp" ;fldId="pp.id";fldView="pp.code||' '||pp.name" 
    				;fldParent="pp.parent_id";fldOrderBy="pp.code";
    		fldIsChild = "(select count(*) from "+table+"1 where pp1.parent_id="+fldId +")";
    	} else {
    		return "" ;
    	}
    	
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(fldId).append(" as fldId, ").append(fldView).append(" as fldView")
    		.append(", case when ").append(fldIsChild).append(">0 then 1 else null end as ascntChild")
    		.append(" from ").append(table)
    		.append(" ").append(join)
    		.append(" where ").append(fldParent) ;
    	if (aParent!=null&&aParent.equals(aParent.valueOf(0))) {
    		sql.append(" is null") ;
    	} else {
    		sql.append("=").append("'").append(aParent).append("'") ;
    	}
    	sql.append(" ").append(whereAdd).append(" ") ;
    	sql.append(" order by ").append(fldOrderBy) ;
    	Collection<WebQueryResult> list=service.executeNativeSql(sql.toString()) ;
    	StringBuilder rs = new StringBuilder() ;
    	for (WebQueryResult wqr : list) {
    		if (wqr.get3()!=null) {
        		rs.append("<div id='").append(aName).append(wqr.get1()).append("' onclick='").append(aFunction)
    			.append("(").append("\"").append(aName).append(wqr.get1()).append("Dir\",\"").append(wqr.get1()).append("\",").append(level).append(")").append("'>") ;
	    		for (int i=0;i<aLevel;i++) {
	    			rs.append("<span class='ygtvdepthcell'>&nbsp;|&nbsp;</span>") ;
	    		}
	    		rs.append("<span id='").append(aName).append(wqr.get1()).append("DirV'>+</span>") ;
	    		rs.append(wqr.get2()).append("</div><div id='").append(aName).append(wqr.get1()).append("Dir'></div>") ;
    		} else {
    			rs.append("<div id='").append(aName).append(wqr.get1()).append("' ondblclick='").append(aFunction)
    			.append("Add(").append("\"").append(wqr.get1()).append("\",\"").append(wqr.get2()).append("\")").append("'>") ;
	    		for (int i=0;i<aLevel;i++) {
	    			rs.append("<span class='ygtvdepthcell'>&nbsp;|&nbsp;</span>") ;
	    		}
	    		rs.append("<input type='button' value='Д' onclick='").append(aFunction)
    			.append("Add(").append("\"").append(wqr.get1()).append("\",\"").append(wqr.get2()).append("\")").append("'>");
	    		rs.append(wqr.get2()).append("</div><div id='").append(aName).append(wqr.get1()).append("Dir'></div>") ;
    		}
    	}
    	
    	return rs.toString() ;
    }
    
}
