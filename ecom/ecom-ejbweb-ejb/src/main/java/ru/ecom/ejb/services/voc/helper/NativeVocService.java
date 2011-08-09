package ru.ecom.ejb.services.voc.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;

import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;
import sun.awt.windows.ThemeReader;

public class NativeVocService implements IVocContextService, IVocServiceManagement {

    private final static Logger LOG = Logger.getLogger(EntityVocService.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private final String theQueriedFields ;
    private final String theSelect ;

    public NativeVocService(String aFrom, String aNames,String aNameId, String aJoin, String aQueryAppend,String aQueried, String aParent, String aOrder
    		,String aFieldsSplitCount, String aParentSplitCount
    ) throws ClassNotFoundException {
       // theSelect = aNames;
    	theSplitQueriedCount = (aFieldsSplitCount!=null && !aFieldsSplitCount.equals("")) ?Long.valueOf(aFieldsSplitCount):null ;
    	LOG.debug("theSplitQueriedCount="+theSplitQueriedCount) ;
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
        theNameId = aNameId ;
        theJoin = aJoin ;
        theQueryAppend = aQueryAppend ;
        theOrder = aOrder ;
        theFrom = aFrom ;
        theQueriedFields = aQueried ;
        theParentField = StringUtil.isNullOrEmpty(aParent) ? null : aParent ;
    }

    /** Свойства с названиями */
    private final String[] theNames ;

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
    	 
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
		.append(" ").append(theJoin).append(" where ")
		
		.append(theNameId).append("=").append(aId) ;
        if(StringUtil.isNullOrEmpty(aId)) return "" ; //throw new VocServiceException("Нет идентификатора ");
        LOG.info("id="+aId) ;
    	LOG.info(sql) ;
        //Object obj = aContext.getEntityManager().find(theEntityClass, theEntityHelper.convertId(theEntityClass, aId)) ;
        try {
        	List<Object[]> obj =aContext.getEntityManager().createNativeQuery(sql.toString()).getResultList() ;
        	if (obj.size()>0) {
        		return getNameFromEntity(obj.get(0)) ;
        	} else {
        		
        	}
        } catch (Exception e) {
            //throw new VocServiceException("Ошибка при получении наименования у объекта "+theEntityClass+" "+aId);
        }
        return "" ;
        
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
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
    	    	
    	    	aQuery = aQuery.toUpperCase() ;
    	    	LOG.debug("query="+aQuery) ;
    	    	Query query  = aContext.getEntityManager().createNativeQuery
        		(sql.toString()) ;
    	    	LOG.debug(aQuery) ;
    	    	if (theSplitQueriedCount!=null && theSplitQueriedCount>0) {
    	    		LOG.debug("splitQuery="+theSplitQueriedCount);
	    			String[] split = aQuery.split(" ") ;
	    			int cnt = theSplitQueriedCount.intValue() ;
	    			LOG.debug("cnt="+cnt);
    				for (int i=0; i<cnt;i++) {
    					String querI = new StringBuilder().append("quer").append(i+1).toString() ;
    					LOG.debug("quer="+querI) ;
    					if (sql.toString().indexOf(querI)>0) {
	    					if (i<split.length) {
	    						LOG.debug(split[i]) ;
	    						query.setParameter(querI, split[i]+"%") ;
	    					} else {
	    						LOG.debug(null) ;
	    						query.setParameter(querI, "%") ;
	    					}
    					}
    				}
    	    	} else {
    	    		LOG.debug("splitQuery="+theSplitQueriedCount);
    	    	}
    	    	if (sql.toString().indexOf("query")>0) {
        			query.setParameter("query", new StringBuilder().append("%").append(aQuery).append("%").toString()) ;
    	    	}
    	    	if (sql.toString().indexOf("querId")>0) {
    	    		query.setParameter("querId", new StringBuilder().append("").append(aQuery).append("%").toString()) ;
    	    	}
        		//sql.append(" and ").append(theNameId).append("=").append(id) ;
    	    	/*
    	    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    	    		query.setParameter("parent", aAdditional.getParentId()) ;
    	    		LOG.info("parent="+aAdditional.getParentId());
    	    	}*/
    	    	
    	    	List<Object[]> list = query.setMaxResults(aCount)
                		.getResultList();
    	    	LOG.info("query done") ;
    	    	return createValues(list,0);
    		//} catch (Exception e) {
    			
    		//}
    	}
    	/*
        if(StringUtil.isNullOrEmpty(aQuery)) {
            return findVocValueNext(aVocName, null, aCount, aAdditional, aContext);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("from ").append(theEntityName) ;
            sb.append(" where ( ") ;
            boolean firstPassed = false ;
            for (String field : theQueriedFields) {
                if(firstPassed) sb.append(" or ") ; else firstPassed = true ;
                sb.append(field).append( "  like :query") ;
            }
            sb.append(" ) ") ;
            if(theAppendQuery!=null) {
                sb.append(" and ") ;
                sb.append(theAppendQuery) ;
            }
            if(theParentField!=null) {
                sb.append(" and ") ;
                sb.append(theParentField).append('=').append(aAdditional.getParentId()) ;
            }
            sb.append(" order by id") ;
            if (CAN_DEBUG)
				LOG.debug("findVocValueByQuery: query = " + sb); 

            // ("from "+theEntityName+" where time = :time and "+theNameField+" like :query order by id")

            Query query  = aContext.getEntityManager().createQuery
                    (sb.toString())
                    .setParameter("query", new StringBuilder().append("%").append(aQuery).append("%").toString()) ;
            List list = query.setMaxResults(aCount)
                    .getResultList();
            return createValues(list);
            
        }*/// return null ;
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        // уще не установлен родитель
    	
        if(theParentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<VocValue>() ;
        }
        StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
		.append(" ").append(theJoin);
		boolean appendIs = false ;
    	if (!StringUtil.isNullOrEmpty(theQueryAppend)) {
    		sql.append(" where ").append(theQueryAppend) ;
    		appendIs=true ;
    	}
		
    	if (!StringUtil.isNullOrEmpty(aId)) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(theNameId).append("<=:id ");
    		appendIs=true ;
    	}
    	
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(getParent(theParentField,aAdditional.getParentId(),theSplitParentCount)) ;
    		appendIs=true ;
    	}
    	sql.append(" order by ").append(theNameId) .append(" desc");
    	LOG.info(sql) ;
    	Query query = aContext.getEntityManager().createNativeQuery(sql.toString()) ;
    	if (!StringUtil.isNullOrEmpty(aId)){
    		query.setParameter("id", Long.valueOf(aId)) ;
    	}
    	/*
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		LOG.info("parent=="+aAdditional.getParentId()) ;
    		query.setParameter("parent", aAdditional.getParentId());
    	}*/
    	List<Object[]> list = query.setMaxResults(aCount).getResultList() ;
    	LOG.info("result cnt="+list.size()) ;
    	return createValues(list,1) ;
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(theEntityName) ;
        Object id = null ;
        boolean whereAppended = false ;
        if(!StringUtil.isNullOrEmpty(aId)) {
            sb.append(" where id <= :id") ;
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
        sb.append(" order by id desc") ;
        if (CAN_DEBUG)
			LOG.debug("findVocValuePrevious: query = " + sb); 
        Query query = aContext.getEntityManager().createQuery(sb.toString()) ;
        if(id!=null) {
            query.setParameter("id", id) ;
            if (CAN_DEBUG)
				LOG.debug("findVocValuePrevious: id = " + id); 

        }
        List list = query.setMaxResults(aCount).getResultList();
        Collection<VocValue> values = createValues(list) ;
        ArrayList<VocValue> ret = new ArrayList<VocValue>();
        for (VocValue value : values) {
            ret.add(0, value);
        }
        return ret ;
        */
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
    	if (theParentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		return new LinkedList<VocValue>() ;
    	} 
    	StringBuilder sql = new StringBuilder() ;
    	sql.append("select ").append(theNameId).append(", ").append(theSelect).append(" from ").append(theFrom)
		.append(" ").append(theJoin);
    	boolean appendIs = false ;
    	if (!StringUtil.isNullOrEmpty(theQueryAppend)) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(theQueryAppend);
    		appendIs = true ;
    		
    	}
    	if (!StringUtil.isNullOrEmpty(aId)) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		sql.append(theNameId).append(">=:id ");
    		appendIs = true ;
    	}
    	//todo else
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		if (appendIs) sql.append(" and "); else sql.append(" where ") ;
    		String param=getParent(theParentField, aAdditional.getParentId(),theSplitParentCount) ;
    		sql.append(param) ;
    		appendIs = true ;
    	}
    	sql.append(" order by ").append(theNameId) ;
    	LOG.info(sql) ;
    	LOG.info("id="+aId) ;
    	Query query = aContext.getEntityManager().createNativeQuery(sql.toString()) ;
    	if (!StringUtil.isNullOrEmpty(aId)){
    		query.setParameter("id", Long.valueOf(aId)) ;
    	}
    	/*
    	if (theParentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
    		query.setParameter("parent",aAdditional.getParentId()) ;
    		LOG.info("parent==="+aAdditional.getParentId());
    	}*/
    	List<Object[]> list = query.setMaxResults(aCount).getResultList() ;
    	LOG.info("query done");
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
    		System.out.println("count="+aSplitCount) ;
    		for (int i=0;i<aSplitCount.intValue();i++) {
    			find = ":par"+(i+1) ;
    			
    			String pr ;
    			if (val.length>i) {
    				pr="'"+val[i]+"'" ;
    			} else {
    				pr="''" ;
    			}
    			System.out.println("find="+find+"    "+"pr="+pr) ;
    			result = result.replace(find, pr);
    			System.out.println("----result="+result) ;
    		}
    		
    	} else {
    		find = ":parent" ;
	    	result = result.replace(find, "'"+aReplace+"'") ;
    	}
    	System.out.println("result="+result) ;
    	return result ;
    }
    



    private Collection<VocValue> createValues(List<Object[]> aList, int aPrevIs)  {
        LinkedList<VocValue> values = new LinkedList<VocValue>();
        if(aList!=null && !aList.isEmpty()) {
            for (Object[] entity : aList) {
            	if (aPrevIs>0) {
            		values.add(0,createVocValue(entity)) ;
            	} else {
            		values.add(createVocValue(entity)) ;
            	}
            }
            //System.out.println(aPrevIs+"  list size="+values.size()) ;
        }
        return values;
    }

    private String getNameFromEntity(Object[] aObj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        int i=0 ;
       // for (String field : theNames) {
        	//int column = Integer.valueOf(field.trim()) ;
            //sb.append(aObj[column]) ;
        	sb.append(aObj[++i]) ;
            sb.append(" ") ;
            
        //}
        return sb.toString();
    }

    private VocValue createVocValue(Object[] aObj) throws RuntimeException {
        try {
            //String id = PropertyUtil.getPropertyValue(aEntity, "id").toString() ;
        	//System.out.println("obj="+aObj) ;
        	String id = new StringBuilder ().append(aObj[0]).toString() ;
        	//System.out.println("id="+id) ;
            String name = getNameFromEntity(aObj) ;
            //System.out.println("name="+name) ;
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
    private final String theFrom ;
    private StringBuilder theSql = new StringBuilder();
    private final String theNameId ;
    private static String[] getAsArray(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr, ";");
        LinkedList<String> list = new LinkedList<String>();
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