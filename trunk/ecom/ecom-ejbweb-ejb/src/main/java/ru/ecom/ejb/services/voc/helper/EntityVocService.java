package ru.ecom.ejb.services.voc.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

/**
 *
 */
public class EntityVocService implements IVocContextService, IVocServiceManagement {

    private final static Logger LOG = Logger.getLogger(EntityVocService.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private enum QueryConvertType {NONE, LOWER_CASE, UPPER_CASE, FIRST_UPPER, FIRST_LAT_UPPER}

    public Class getEntityClass() {
    	return theEntityClass ;
    }
    public EntityVocService(String aEntityClass, String aNames[], String aQueriedFields[]
        , String aParent, String aQueryAppend, String aConvertType) throws ClassNotFoundException {
//        theEntityClassName = aEntityClass;
        theNames = aNames;
        theEntityClass = theClassLoaderHelper.loadClass(aEntityClass);
        theQueriedFields = aQueriedFields ;
        theEntityName = theEntityHelper.getEntityName(theEntityClass) ;
        theParentField = StringUtil.isNullOrEmpty(aParent) ? null : aParent ;
        theAppendQuery = StringUtil.isNullOrEmpty(aQueryAppend) ? null : aQueryAppend ;
        setQueryConvertType(aConvertType);
    }

    private void setQueryConvertType(String aConvertType) {
        if(StringUtil.isNullOrEmpty(aConvertType)) {
            setQueryConvertType(QueryConvertType.NONE) ;
        } else if(aConvertType.equals("UPPER_CASE")) {
            setQueryConvertType(QueryConvertType.UPPER_CASE) ;
        } else if(aConvertType.equals("LOWER_CASE")) {
            setQueryConvertType(QueryConvertType.LOWER_CASE) ;
        } else if(aConvertType.equals("FIRST_UPPER")) {
        	setQueryConvertType(QueryConvertType.FIRST_UPPER) ;
        } else if(aConvertType.equals("FIRST_LAT_UPPER")) {
            setQueryConvertType(QueryConvertType.FIRST_LAT_UPPER) ;
        }else {
            throw new IllegalArgumentException("Нет конвертера "+aConvertType) ;
        }
    }

    /** Тип конвертации запроса */
    public QueryConvertType getQueryConvertType() { return theQueryConvertType ; }
    public void setQueryConvertType(QueryConvertType aQueryConvertType) { theQueryConvertType = aQueryConvertType ; }

    /** Тип конвертации запроса */
    private QueryConvertType theQueryConvertType = QueryConvertType.NONE ;

    private final String theEntityName ;
    /** Поля для запроса */
    public String[] getQueriedFields() { return theQueriedFields ; }

    /** Поля для запроса */
    private final String[] theQueriedFields ;
    /** Свойства с названиями */
    public String[] getNames() { return theNames ; }

    /** Свойства с названиями */
    private final String[] theNames ;

    /** Название Entity Bean */
//    private final String theEntityClassName ;
    private final Class theEntityClass ;


    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if(StringUtil.isNullOrEmpty(aId)) return "" ; //throw new VocServiceException("Нет идентификатора ");
        Object obj = aContext.getEntityManager().find(theEntityClass, theEntityHelper.convertId(theEntityClass, aId)) ;
        try {
            return getNameFromEntity(obj) ;
        } catch (Exception e) {
            throw new VocServiceException("Ошибка при получении наименования у объекта "+theEntityClass+" "+aId);
        }
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if(StringUtil.isNullOrEmpty(aQuery)) {
            return findVocValueNext(aVocName, null, aCount, aAdditional, aContext);
        } else {
        	// попытка поиска по идентификатору
        	try {
        		try {
            		Long id = Long.parseLong(aQuery);
            		BaseEntity entity =  (BaseEntity) aContext.getEntityManager().find(theEntityClass, id);
            		if(entity!=null) {
            			String firstId = String.valueOf(entity.getId()) ;
            			return findVocValueNext(aVocName, firstId, aCount, aAdditional, aContext) ;
            		}
        		} catch (NumberFormatException e) {
        			
        		}
        	} catch (Exception e) {
        		LOG.warn("Ошибка при поиске по идентификатору: "+e.getMessage(), e);
        	}
        	String queryDop = null ;
            switch(theQueryConvertType) {
            /*
                case LOWER_CASE: aQuery = aQuery.toLowerCase() ; break ;
                case UPPER_CASE: aQuery = aQuery.toUpperCase() ; break ;
                case FIRST_UPPER: {
                	char c = aQuery.charAt(0) ;
                	aQuery = Character.toUpperCase(c) + (aQuery.length()>1 ? aQuery.substring(1).toLowerCase() : "") ;
                	break ;
                }
                */
            	
                case FIRST_LAT_UPPER: {
                    queryDop = aQuery ;
                    queryDop = queryDop.toUpperCase() ;
                	char c1 = queryDop.charAt(0) ;
                	queryDop = queryDop.replaceFirst("Ю", ".") ;
                    aQuery = getLat(c1) + (queryDop.length()>1 ? queryDop.substring(1) : "") ;
                    break ;
                }
            }
            
        	aQuery = aQuery.toUpperCase() ;
            StringBuilder sb = new StringBuilder();
            sb.append("from ").append(theEntityName) ;
            sb.append(" where ( ") ;
            boolean firstPassed = false ;
            for (String field : theQueriedFields) {
                if(firstPassed) sb.append(" or ") ; else firstPassed = true ;
                sb.append(" (upper(").append(field).append( ")  like :query) ") ;
                if (queryDop!=null) sb.append(" or (upper(").append(field).append( ")  like '%").append(queryDop).append("%')") ;
            }
            sb.append(" ) ") ;
            System.out.println(sb) ;
            if(theAppendQuery!=null) {
                sb.append(" and ") ;
                sb.append(theAppendQuery) ;
            }
            if(theParentField!=null) {
                sb.append(" and ") ;
                sb.append(theParentField).append('=').append(aAdditional.getParentId()) ;
            }
            sb.append(" order by id") ;
            //if (CAN_DEBUG)
				LOG.debug("findVocValueByQuery: query = " + sb); 

            // ("from "+theEntityName+" where time = :time and "+theNameField+" like :query order by id")

            Query query  = aContext.getEntityManager().createQuery
                    (sb.toString())
                    .setParameter("query", new StringBuilder().append("%").append(aQuery).append("%").toString()) ;
            List list = query.setMaxResults(aCount)
                    .getResultList();
            return createValues(list);
        }
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        // уще не установлен родитель
        if(theParentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<VocValue>() ;
        }
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
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        // уще не установлен родитель
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
    }



    private Collection<VocValue> createValues(List aEntities)  {
        LinkedList<VocValue> values = new LinkedList<VocValue>();
        if(aEntities!=null && !aEntities.isEmpty()) {
            for (Object entity : aEntities) {
                values.add(createVocValue(entity)) ;
            }
        }
        return values;
    }

    private String getNameFromEntity(Object aEntity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        for (String field : theNames) {
            sb.append(PropertyUtil.getPropertyValue(aEntity, field)) ;
            sb.append(" ") ;
        }
        return sb.toString();
    }

    private VocValue createVocValue(Object aEntity) throws RuntimeException {
        try {
            String id = PropertyUtil.getPropertyValue(aEntity, "id").toString() ;
            String name = getNameFromEntity(aEntity) ;
            return new VocValue(id, name) ;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания VocValue",e);
        }
    }

    public void destroy() {
        theClassLoaderHelper = null ;
        theEntityHelper = null ;
        theMap = null ;
    }

    public Map<String,String> enrusCreate() {
    	Map <String,String> map = new HashMap<String, String>();
    	map.put("Й", "Q" ) ;
    	map.put("Ц", "W" ) ;
    	map.put("У","E"  ) ;
    	map.put( "К", "R" ) ;
    	map.put("Е", "T"  ) ;
    	map.put( "Ф","A" ) ;
    	map.put( "Ы", "S") ;
    	map.put("В", "D"  ) ;
    	map.put("А","F" ) ;
    	map.put("П","G"  ) ;
    	map.put("Я","Z"  ) ;
    	map.put("Ч","X"  ) ;
    	map.put("С","C"  ) ;
    	map.put( "М", "V" ) ;
    	map.put("И", "B" ) ;
    	map.put("Н", "Y"  ) ;
    	map.put("Г", "U"  ) ;
    	map.put("Ш", "I"  ) ;
    	map.put("Щ", "O"  ) ;
    	map.put("З","P" ) ;
    	map.put( "Р","H" ) ;
    	map.put("О", "J"  ) ;
    	map.put("Л","K"  ) ;
    	map.put("Д", "L" ) ;
    	map.put("Т","N" ) ;
    	map.put( "Ь","M" ) ;
    	map.put( "Ю","." ) ;
    	return map ;
    }
    private String getLat(char aChar) {
    	String ret =theMap.get(String.valueOf(aChar)) ;
    	return ret !=null ? ret: String.valueOf(aChar) ;
    }
    

    Map<String, String> theMap = enrusCreate();
    private ClassLoaderHelper theClassLoaderHelper = ClassLoaderHelper.getInstance();
    private  EntityHelper theEntityHelper = EntityHelper.getInstance();
    private final String theParentField ;
    private final String theAppendQuery ;


}
