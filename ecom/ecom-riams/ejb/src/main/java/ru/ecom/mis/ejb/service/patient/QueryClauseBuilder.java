package ru.ecom.mis.ejb.service.patient;

import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ru.ecom.alg.common.IsPensioner;
import ru.nuzmsh.util.StringUtil;

/**
 *
 */
public class QueryClauseBuilder {

    private final static Logger LOG = Logger.getLogger(QueryClauseBuilder.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;


    public void addLike(String aParameterName, Object aValue) {
        thePameters.add(new QueryParameter(aParameterName, aValue, false, false, true)) ;
    }

    public void add(String aParameterName, Object aValue) {
    	thePameters.add(new QueryParameter(aParameterName, aValue, false)) ;
    }
    public void addParameter(String aParameterName, Object aValue) {
        thePameters.add(new QueryParameter(aParameterName, aValue)) ;
    }

    public void addIsNull(String aParameterName, Object aValue) {
        thePameters.add(new QueryParameter(aParameterName, aValue, true)) ;
    }
    public void addBetween(String aParameterName, Object aValue,Object aValueTo) {
        thePameters.add(new QueryParameter(aParameterName, aValue, aValueTo)) ;
    }
    public void addNot(String aParameterName, Object aValue) {
        thePameters.add(new QueryParameter(aParameterName, aValue, false, true)) ;
    }

    public Query build(EntityManager aManager, String aPrefix, String aSuffix) {
    	return build(aManager, aPrefix, aSuffix, false);
    }
    
    public Query buildNative(EntityManager aManager, String aPrefix, String aSuffix) {
    	return build(aManager, aPrefix, aSuffix, true);
    }
    
    private Query build(EntityManager aManager, String aPrefix, String aSuffix, boolean aNative) {
        StringBuilder sb = new StringBuilder(aPrefix.trim());
        for (QueryParameter parameter : thePameters) {
            appendClause(sb, parameter.name, parameter.value, parameter.isNull, parameter.isNot, parameter.isLike, parameter.isBetween,parameter.isParam);
        }
        String q = sb.toString().trim() ;
        if(q.endsWith("where")) {
            q = q.substring(0, q.length() - "where".length()) ;
        }
        sb.append(aSuffix) ;


        String q2 = new StringBuilder().append(q).append(" ").append(aSuffix).toString() ;
        if (CAN_DEBUG) LOG.debug("query = " + q2);

        Query query = aNative ? aManager.createNativeQuery(q2) : aManager.createQuery(q2);

        for (QueryParameter parameter : thePameters) {
        	setClause(query, parameter.name, parameter.value);
            if (parameter.valueTo!=null) setClause(query, parameter.name+"To", parameter.valueTo);
        }
        return query ;
    }

    private static void setClause(Query aQuery, String aParameterName, Object aValue) {
        if(!isNullOrZeroOrEmpty(aValue)) {
            aQuery.setParameter(aParameterName.replace(".", ""), aValue) ;
        }
    }
    
    private static void appendClause(StringBuilder aSb, String aParameterName, Object aValue, boolean aIsNullSupports, boolean isNot, boolean aIsLike, boolean aIsBetween,boolean aIsParam) {
        if (!aIsParam) {
    	if(!isNullOrZeroOrEmpty(aValue)) {
            if(!aSb.toString().endsWith("where")) aSb.append(" and ") ;
            aSb.append(' ') ;
            aSb.append(aParameterName) ;
            if(aIsLike) {
                aSb.append(" like :") ;
            } else {
                if(isNot) {
                    aSb.append(" <> :") ;
                } else {
                    aSb.append(" = :") ;
                }
            }
            aSb.append(aParameterName.replace(".", "")) ;// fx
            aSb.append(' ') ;
        } else if(aIsNullSupports) {
            if(!aSb.toString().endsWith("where")) aSb.append(" and ") ;
            aSb.append(' ') ;
            aSb.append(aParameterName) ;
            aSb.append(" is null ") ;
        } else if(aIsBetween) {
        	if(!aSb.toString().endsWith("where")) aSb.append(" and ") ;
            aSb.append(' ') ;
            aSb.append(aParameterName) ;
            aSb.append(" between ") ;
            aSb.append(":").append(aParameterName.replace(".", ""));
            aSb.append(" and ") ;
            aSb.append(":").append(aParameterName.replace(".", "")).append("To ");
        }
        }
    }

    private static boolean isNullOrZeroOrEmpty(Object aObject) {
        boolean ret ;
        if(aObject!=null) {
            if(aObject instanceof String) {
                ret = StringUtil.isNullOrEmpty((String) aObject) ;
            } else if(aObject instanceof Number) {
                Number number = (Number) aObject ;
                ret = number.intValue()==0 ;
            } else {
                ret = false ;
            }
        } else {
            ret = true ;
        }
        return ret ;
    }

    private static class QueryParameter {
        public QueryParameter(String aName, Object aValue, boolean aIsNullSupports, boolean aIsNot, boolean aIsLike) {
            name = aName ;
            value = aValue ;
            valueTo = null ;
            isNull = aIsNullSupports ;
            isNot = aIsNot ;
            isLike = aIsLike ;
            isBetween = false ;
            isParam = false ;
        }
        public QueryParameter(String aName, Object aValue,Object aValueTo) {
            name = aName ;
            value = aValue ;
            valueTo = aValueTo ;
            isNull = false ;
            isLike = false ;
            isNot = false ;
            isBetween = true ;
            isParam = false ;
        }        
        public QueryParameter(String aName, Object aValue, boolean aIsNullSupports, boolean aIsNot) {
            name = aName ;
            value = aValue ;
            valueTo = null ;
            isNull = aIsNullSupports ;
            isLike = false ;
            isNot = aIsNot ;
            isBetween = false ;
            isParam = false ;
        }
        public QueryParameter(String aName, Object aValue, boolean aIsNullSupports) {
        	name = aName ;
        	value = aValue ;
        	valueTo = null ;
        	isNull = aIsNullSupports ;
        	isNot = false ;
        	isLike = false ;
        	isBetween = false ;
        	isParam = false ;
        }
        
        public QueryParameter(String aName, Object aValue) {
            name = aName ;
            value = aValue ;
            valueTo = null ;
            isNull = false ;
            isNot = false ;
            isLike = false ;
            isBetween = false ;
            isParam = true ;
        }
        

        private final String name ;
        private final Object value ;
        private final Object valueTo ;
        private final boolean isNull ;
        private final boolean isNot ;
        private final boolean isLike ;
        private final boolean isBetween ;
        private final boolean isParam ;
    }
    LinkedList<QueryParameter> thePameters = new LinkedList<QueryParameter>();
//    Map<String, Object> theParameters = new TreeMap<String, Object>();
}
