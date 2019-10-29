package ru.ecom.ejb.services.voc.helper;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class NativeVocService implements IVocContextService, IVocServiceManagement {

    private static final Logger LOG = Logger.getLogger(EntityVocService.class) ;

    private final String theQueriedFields ;
    private final String theSelect ;

    public NativeVocService(String aFrom, String aNames,String aNameId, String aJoin, String aQueryAppend,String aQueried, String aParent, String aOrder
    		,String aFieldsSplitCount, String aParentSplitCount,String aGroupBy
    ) {
       // theSelect = aNames;
    	theSplitQueriedCount = (aFieldsSplitCount!=null && !aFieldsSplitCount.equals("")) ?Long.valueOf(aFieldsSplitCount):null ;
    	theSplitParentCount = aParentSplitCount!=null && !aParentSplitCount.equals("") ?Long.valueOf(aParentSplitCount):null ;
        theNames = getAsArray(aNames) ;
        StringBuilder sb = new StringBuilder () ;
        if (theNames.length>0) {
	        for (String name:theNames) {
	        	sb.append(name).append(",") ;
	        	
	        }
	        theSelect = sb.substring(0,sb.length()-1) ;
        } else {
        	theSelect = aNameId ;
        }
        theOrder = aOrder !=null && !"".equals(aOrder) ? aOrder : null;
        theNameId = aNameId ;
        theJoin = aJoin!=null?aJoin:"" ;
        theGroupBy = aGroupBy!=null&&aGroupBy.trim().equals("")?null:aGroupBy ;
        theQueryAppend = aQueryAppend ;
      //  theOrder = aOrder ;
        theFrom = aFrom ;
        theQueriedFields = aQueried ;
        theParentField = StringUtil.isNullOrEmpty(aParent) ? null : aParent ;
    }

    /** Свойства с названиями */
    private final String[] theNames ;

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
    	if(StringUtil.isNullOrEmpty(aId)) return "" ;  
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
		.append(" ").append(theJoin).append(" where ")
		
		.append(theNameId).append("='").append(aId).append("'") ;
        //throw new VocServiceException("Нет идентификатора ");
        
        //LOG.info("id="+aId) ;
    	//LOG.debug(sql) ;
        //Object obj = aContext.getEntityManager().find(theEntityClass, theEntityHelper.convertId(theEntityClass, aId)) ;
        try {
        	List<Object[]> obj =aContext.getEntityManager().createNativeQuery(sql.toString()).getResultList() ;
        	if (!obj.isEmpty()) {
        		
        		if (obj.size()>1 && theParentField!=null && aAdditional!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
        			sql = new StringBuilder() ;
        	    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
        			.append(" ").append(theJoin).append(" where ")
        			
        			.append(theNameId).append("='").append(aId).append("'") ;
        	    	sql.append(" and ");
        	    	sql.append(getParent(theParentField,aAdditional.getParentId(),theSplitParentCount)) ;
        	    	List<Object[]> obj1 =aContext.getEntityManager().createNativeQuery(sql.toString()).getResultList() ;
                	if (!obj1.isEmpty()) {
                		return getNameFromEntity(obj1.get(0)) ;
                	}
        		}
        		return getNameFromEntity(obj.get(0)) ;
        	}
        } catch (Exception e) {
        	LOG.error("Ошибка выполнение voc-запроса("+aVocName+"): "+sql.toString(),e);
        //	e.printStackTrace() ;
            //throw new VocServiceException("Ошибка при получении наименования у объекта "+theEntityClass+" "+aId);
        }
        return "" ;
        
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
    	//LOG.info("-------ЗАПРОС");
    	if (StringUtil.isNullOrEmpty(aQuery)) {
    		return findVocValueNext(aVocName, null, aCount, aAdditional, aContext) ;
    	} else {
    		// поиск по идентификатору
    		//try {
    			//Long id = Long.parseLong(aQuery) ;
    			StringBuilder sql = new StringBuilder() ;
    	    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
    			.append(" ").append(theJoin) ;
    	    	boolean appendIs = false ;
    	    	if (!StringUtil.isNullOrEmpty(theQueryAppend)) {
    	    		sql.append(" where (").append(theQueryAppend).append(")") ;
    	    		appendIs = true ;
    	    	}
    	    	if (!StringUtil.isNullOrEmpty(theQueriedFields)) {
    	    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    	    		sql.append(" (") .append(theQueriedFields).append(")");
    	    		//
    	    		appendIs = true ;
    	    	}
    	    	//sql.append(" where (").append(theQueryAppend).append(") and (") .append(theQueriedFields).append(")");
    	    	
    	    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    	    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    	    		sql.append(getParent(theParentField,aAdditional.getParentId(),theSplitParentCount)) ;
    	    	}
    	    	if (theGroupBy!=null) sql.append(" group by ").append(theGroupBy) ;
    	    	aQuery = aQuery.toUpperCase() ;
    	    	Query query  = aContext.getEntityManager().createNativeQuery(sql.toString()) ;
    	    	//LOG.debug(aQuery) ;
    	    	if (theSplitQueriedCount!=null && theSplitQueriedCount>0) {
    	    		//LOG.debug("splitQuery="+theSplitQueriedCount);
	    			String[] split = aQuery.split(" ") ;
	    			int cnt = theSplitQueriedCount.intValue() ;
	    			//LOG.debug("cnt="+cnt);
    				for (int i=0; i<cnt;i++) {
    					String querI = "quer"+(i+1) ;
    					//LOG.debug("quer="+querI) ;
    					if (sql.toString().contains(querI)) {
	    					if (i<split.length) {
	    						//LOG.debug(split[i]) ;
	    						query.setParameter(querI, split[i]+"%") ;
	    					} else {
	    						query.setParameter(querI, "%") ;
	    					}
    					}
    				}
    	    	}
    	    	if (sql.toString().contains("query")) {
        			query.setParameter("query", "%"+aQuery+"%") ;
    	    	}
    	    	if (sql.toString().contains("querId")) {
    	    		query.setParameter("querId", aQuery+"%") ;
    	    	}
    	    	if (sql.toString().contains("querInd")) {
    	    		query.setParameter("querInd", aQuery) ;
    	    	}
        		
    	    	List<Object[]> list = query.setMaxResults(aCount)
                		.getResultList();
    	    //	LOG.info("query done") ;
    	    	return createValues(list,0);
    		//} catch (Exception e) {
    			
    		//}
    	}
    	
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        // уще не установлен родитель
    	//LOG.info("-------ПРЕДЫДУЩИЙ СПИСОК");
        if(theParentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<>() ;
        }
        
        StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
		.append(" ").append(theJoin);
		boolean appendIs = false ;
		//LOG.info("-------sql1"+sql);
		//LOG.info("-------theParentField="+theParentField);
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		sql.append(" where ") ;
    		sql.append(getParent(theParentField,aAdditional.getParentId(),theSplitParentCount)) ;
    		appendIs=true ;
    		//LOG.info("-------aAdditional.getParentId()="+aAdditional.getParentId());
    	}
    	if (!StringUtil.isNullOrEmpty(theQueryAppend)) {
       		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
       		sql.append(theQueryAppend);
    		appendIs=true ;
    	}
    	if (!StringUtil.isNullOrEmpty(aId)) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(theNameId).append("<='").append(aId).append("' ");
    	//	appendIs=true ;
    	}
    	if (theGroupBy!=null) sql.append(" group by ").append(theGroupBy) ;
    	sql.append(" order by ").append(theNameId) .append(" desc");
    	Query query = aContext.getEntityManager().createNativeQuery(sql.toString()) ;
    	//if (!StringUtil.isNullOrEmpty(aId)){
    	//	query.setParameter("id", Long.valueOf(aId)) ;
    	//}
    	List<Object[]> list = query.setMaxResults(aCount).getResultList() ;
    	return createValues(list,1) ;
        
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
    	if (theParentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		return new LinkedList<>() ;
    	} 
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
		.append(" ").append(theJoin);
    	boolean appendIs = false ;
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		//if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(" where ") ;
    		String param=getParent(theParentField, aAdditional.getParentId(),theSplitParentCount) ;
    		sql.append(param) ;
    		appendIs = true ;
    	}
    	if (!StringUtil.isNullOrEmpty(theQueryAppend)) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(theQueryAppend);
    		appendIs = true ;
    		
    	}
    	//todo else
    	if (!StringUtil.isNullOrEmpty(aId)) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(theNameId).append(">='").append(aId).append("' ");
    	//	appendIs = true ;
    	}
    	if (theGroupBy!=null) sql.append(" group by ").append(theGroupBy) ;

    	sql.append(" order by ").append( theOrder!=null ? theOrder : theNameId) ;
    	Query query = aContext.getEntityManager().createNativeQuery(sql.toString()) ;
    	/*if (!StringUtil.isNullOrEmpty(aId)){
    		query.setParameter("id", Long.valueOf(aId)) ;
    	}*/
    	/*
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		query.setParameter("parent",aAdditional.getParentId()) ;
    	}*/

		List<Object[]> list = null;
		try {
			list = query.setMaxResults(aCount).getResultList();
		} catch (Exception e) {
			LOG.error("Error execute sql: "+sql,e);
		}
    	return createValues(list,0) ;
    		// уще не установлен родитель
    	/*
        if(theParentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<VocValue>() ;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(theEntityName) ;
        Object id = null ;
        boolean whereAppended = false ;
        if(!StringUtil.isNullOrEmpty(aId)) {
            sb.append(" where id >= :id") ;
            whereAppended = true ;
            id = theEntityHelper.convertId(theEntityClass, aId) ;
        }

        if(theAppendQuery!=null) {
            if(!StringUtil.isNullOrEmpty(aId)) sb.append(" and ") ;
            else {
                whereAppended = true ;
                sb.append(" where ") ;
            }
            sb.append(theAppendQuery) ;
        }
        // parentProperty
        if(theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            if(whereAppended) sb.append(" and ") ;
            else sb.append(" where ") ;
            sb.append(theParentField).append('=').append(aAdditional.getParentId()) ;
        }
        sb.append(" order by id") ;
        LOG.info(sb) ;
        Query query = aContext.getEntityManager().createQuery(sb.toString()) ;
        if(id!=null) {
            query.setParameter("id", id) ;
        }
        List list = query.setMaxResults(aCount).getResultList();
        return createValues(list) ;
        */
    	
    }
    
    private String getParent(String aParentField, String aReplace,Long aSplitCount) {
    	
    	String find  ;
    	String result = aParentField ;
    	if (aSplitCount!=null && aSplitCount>0) {
    		String[] val = aReplace.split("#") ;
    		for (int i=0;i<aSplitCount.intValue();i++) {
    			find = ":par"+(i+1) ;
    			
    			String pr ;
    			if (val.length>i) {
    				pr="'"+val[i]+"'" ;
    			} else {
    				pr="''" ;
    			}
    			result = result.replace(find, pr);
    		}
    		
    	} else {
    		find = ":parent" ;
	    	result = result.replace(find, "'"+aReplace+"'") ;
    	}
    	return result ;
    }
    



    private Collection<VocValue> createValues(List<Object[]> aList, int aPrevIs)  {
        LinkedList<VocValue> values = new LinkedList<>();
        if(aList!=null && !aList.isEmpty()) {
            for (Object[] entity : aList) {
            	if (aPrevIs>0) {
            		values.add(0,createVocValue(entity)) ;
            	} else {
            		values.add(createVocValue(entity)) ;
            	}
            }
        }
        return values;
    }

    private String getNameFromEntity(Object[] aObj) {
        return aObj[1]+" ";
    }

    private VocValue createVocValue(Object[] aObj) {
        try {
            //String id = PropertyUtil.getPropertyValue(aEntity, "id").toString() ;
        	String id = aObj[0].toString() ;
            String name = getNameFromEntity(aObj) ;
        	//String name = (String)aObj[2] ;
            return new VocValue(id, name) ;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания VocValue",e);
        }
    }

    public void destroy() {
        //theClassLoaderHelper = null ;
        //theEntityHelper = null ;
        //theMap = null ;
    }
    /** Менеджер */
    /** Дополнительные параметры при выборе из справочника */
    //private final VocAdditional theVocAdditional ;
    //private  EntityHelper theEntityHelper = EntityHelper.getInstance();
    private final String theParentField ;
    private final String theJoin ;
    private final Long theSplitQueriedCount ;
    private final Long theSplitParentCount ;
    private final String theQueryAppend ;
    private final String theOrder ;
  //  private final String theOrder ;
    private final String theGroupBy ;
    private final String theFrom ;
//    private StringBuilder theSql = new StringBuilder();
    private final String theNameId ;
    private static String[] getAsArray(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr, ";");
        LinkedList<String> list = new LinkedList<>();
        while(st.hasMoreTokens()) {
            list.add(st.nextToken()) ;
        }
        String[] ret = new String[list.size()];
        for(int i=0; i<list.size(); i++) {
            ret[i] = list.get(i) ;
        }
        return ret ;
    }
}