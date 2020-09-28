package ru.ecom.template.web.dwr;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 24.01.2007
 * Time: 13:52:18
 * To change this template use File | Settings | File Templates.
 */
public class CategoryTreeServiceJs {

	//lastrelease milamesher 21.03.2018 #31 учитываие prescriptType (aViewButton)
    public String getCategoryMedService(String aName,String aFunction, String aTable, Long aParent,int aLevel, int aAddParam,String aViewButton,String aCheckId, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	String table , fldId, fldView, fldParent , fldOrderBy , fldIsChild,join="",whereAdd="" ;
    	String isOnceViewFld ="0>0";
    	int level=aLevel+1;
    	String[] checkId = (aCheckId!=null?aCheckId.split(","):"".split(""));
    	String viewButton = "" ;
    	if (aTable.toUpperCase().equals("PRICEMEDSERVICE")) {
    		table="PricePosition pp" ;fldId="case when pp.dtype='PriceGroup' then pp.id else pms.id end";
    				fldView="case when pp.dtype='PriceGroup' then '<b>'||pp.code||'</b> '||replace(pp.name,'\"','') else '<b>'||pp.code||'</b> '||' '||replace(pp.name,'\"','')||' ('||pp.cost||')' end" 
    				;fldParent="pp.parent_id";fldOrderBy="case when pp.dtype='PriceGroup' then 1 else 0 end,pp.code";
    				join=" left join pricemedservice pms on pms.priceposition_id=pp.id left join medservice ms on ms.id=pms.medservice_id";
    				whereAdd=" and (pp.dtype='PriceGroup' or pms.id is not null)" ;
    				isOnceViewFld=" pp.dtype='PriceGroup' and pp.isOnceView='1'" ;
    				whereAdd=" and pp.priceList_id='"+aAddParam+"'  and pp.dateTo is null and pms.dateto is null "+whereAdd ;
    				
    		fldIsChild = "(select count(*) from "+table+"1 where pp1.parent_id=pp.id)";
    	} else if (aTable.toUpperCase().equals("PRICEPOSITION")) {
    		table="PricePosition pp" ;fldId="pp.id";fldView="pp.code||' '||pp.name" 
    				;fldParent="pp.parent_id";fldOrderBy="pp.code";
    				//whereAdd=" and pp.dateTo is null "+whereAdd;
    		fldIsChild = "(select count(*) from "+table+"1 where pp1.parent_id="+fldId +")";
    	} else if (aTable.toUpperCase().equals("MEDSERVICE")) {
    		if (aViewButton==null) {
				if (aParent != null && aParent.equals(aParent.valueOf(0))) {
					aParent = 4056L;
				} //Только лабораторные исследования!
				/*if (aViewButton != null) {
					viewButton = "case when ms.id not in (select mss.id from medservice mss  left join workfunctionservice wfss on wfss.medservice_id=mss.id  left join vocprescripttype vpts on vpts.id=wfss.prescripttype_id  left join vocservicetype vsts on vsts.id=mss.servicetype_id where vpts.id= '" + aViewButton + "'   and mss.dtype='MedService'  and vsts.code='LABSURVEY'  ) then null else ms.id end as checkbut";
				}*/
				table = "MedService ms";
				fldId = "ms.id";
				fldView = "case when ms.dtype='MedServiceGroup' then '<b>'||ms.code||'</b> '||replace(ms.name,'\"','') else '<b>'||ms.code||'</b> '||' '||replace(ms.name,'\"','') end"
				;
				fldParent = "parent_id";
				fldOrderBy = "case when ms.dtype='MedServiceGroup' then 1 else 0 end,ms.code";
				whereAdd = "and (ms.dtype='MedServiceGroup' or ms.parent_id is not null) and ms.finishDate is null ";
				fldIsChild = "(select count(*) from " + table + "1 where ms1.parent_id=ms.id)";
			}
			else {
				join=join+"left join workfunctionservice wfss on wfss.medservice_id=ms.id   and ms.dtype='MedService'\n" +
						" left join vocprescripttype vpts on vpts.id=wfss.prescripttype_id and vpts.id='"+aViewButton+"'\n" +
						" left join vocservicetype vsts on vsts.id=ms.servicetype_id \n" +
						" and case when ms.dtype='MedServiceGroup' then vsts.code='LABSURVEY' and ms.dtype='MedService' else 1=1 end\n";
				if (aParent!=null&&aParent.equals(aParent.valueOf(0))) {
					aParent=4056L;
				} //Только лабораторные исследования!
				//if (aViewButton!=null) {
				viewButton = "case when ms.id not in (select mss.id from medservice mss  left join workfunctionservice wfss on wfss.medservice_id=mss.id  left join vocprescripttype vpts on vpts.id=wfss.prescripttype_id  left join vocservicetype vsts on vsts.id=mss.servicetype_id where vpts.id= '"+aViewButton+"'   and mss.dtype='MedService'  and vsts.code='LABSURVEY'  ) then null else ms.id end as checkbut";
				//}
				table="MedService ms" ;fldId="ms.id";
				fldView="case when ms.dtype='MedServiceGroup' then '<b>'||ms.code||'</b> '||replace(ms.name,'\"','') else '<b>'||ms.code||'</b> '||' '||replace(ms.name,'\"','') end"
				;fldParent="parent_id";fldOrderBy="case when ms.dtype='MedServiceGroup' then 1 else 0 end,ms.code";
				whereAdd="and (vsts.code='LABSURVEY' and ms.dtype='MedService' or ms.dtype='MedServiceGroup') and (ms.dtype='MedServiceGroup' or ms.parent_id is not null) and ms.finishDate is null " ;
				whereAdd+="and (ms.dtype='MedService' and ms.id not in \n" +
						"(select mss.id from medservice mss  \n" +
						"left join workfunctionservice wfss on wfss.medservice_id=mss.id \n" +
						"left join vocprescripttype vpts on vpts.id=wfss.prescripttype_id  \n" +
						"left join vocservicetype vsts on vsts.id=mss.servicetype_id  \n" +
						"where mss.dtype='MedService'  and vsts.code='LABSURVEY' and vpts.id='"+aViewButton+"') or ms.dtype='MedServiceGroup')";
				fldIsChild = "(select count(*) from "+table+"1 where ms1.parent_id=ms.id)";
			}
}
    	else {
    		return "" ;
    	}
    	
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(fldId).append(" as fldId, ").append(fldView).append(" as fldView")
    	.append(", case when ").append(fldIsChild).append(">0 then 1 else null end as ascntChild")
    		.append(", case when ").append(isOnceViewFld).append(" then 1 else null end as asView") ;
    		if (aViewButton!=null) {
    			sql.append(",").append(viewButton) ;
    		}
    		sql.append(" from ").append(table)
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
        		rs.append("<div class='dir").append(aLevel).append("Div' id='").append(aName).append(wqr.get1()).append("' onclick='").append(aFunction)
    			.append("(").append("\"").append(aName).append(wqr.get1()).append("Dir\",\"").append(wqr.get1()).append("\",").append(level).append(")").append("'>") ;
	    		for (int i=0;i<aLevel;i++) {
	    			rs.append("<span class='dirN'>&nbsp;|&nbsp;</span>") ;
	    		}
	    		if (wqr.get4()!=null) {
	    			rs.append("<span class='dirN' id='").append(aName).append(wqr.get1()).append("DirV'> - </span><span class='dirV'>") ;
		    		rs.append(wqr.get2()).append("</span></div><div id='").append(aName).append(wqr.get1()).append("Dir'>") ;
		    		rs.append(getCategoryMedService(aName,aFunction, aTable, ConvertSql.parseLong(wqr.get1()),level, aAddParam,aViewButton,aCheckId, aRequest));
		    		rs.append("</div>") ;
	    		} else {
	    			rs.append("<span class='dirN' id='").append(aName).append(wqr.get1()).append("DirV'> + </span><span class='dirV'>") ;
		    		rs.append(wqr.get2()).append("</span></div><div id='").append(aName).append(wqr.get1()).append("Dir'></div>") ;
	    		}
	    		
    		} else {
    			rs.append("<div id='").append(aName).append(wqr.get1()).append("m' ondblclick='").append(aFunction)
    			.append("Add(").append("\"").append(wqr.get1()).append("\",\"").append(wqr.get2()).append("\")").append("'");
    			if (checkId(checkId,""+wqr.get1())) {
    				rs.append(" style='color: blue;'");
    			}
    			rs.append(">") ;
	    		for (int i=0;i<aLevel;i++) {
	    			rs.append("<span class='ygtvdepthcell'>&nbsp;|&nbsp;</span>") ;
	    		}
	    		if (wqr.get5()==null) {
		    		rs.append("<input type='button' value='Д' onclick='").append(aFunction)
	    				.append("Add(").append("\"").append(wqr.get1()).append("\",\"").append(wqr.get2()).append("\")").append("'>");
	    		}
	    		rs.append(wqr.get2()).append("</div>");
	    		//rs.append("<div id='").append(aName).append(wqr.get1()).append("Dir'></div>") ;
    		}
    	}
    	
    	return rs.toString() ;
    }
    public boolean checkId(String[] aArr, String aId) {
    	for (String val:aArr) {
    		if (val.equals(aId)) return true ;
    	}
    	return false ;
    }
    
}
