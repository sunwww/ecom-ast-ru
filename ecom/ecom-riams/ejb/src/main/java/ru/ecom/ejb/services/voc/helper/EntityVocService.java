package ru.ecom.ejb.services.voc.helper;

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

import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *
 */
public class EntityVocService implements IVocContextService, IVocServiceManagement {

    private static final Logger LOG = Logger.getLogger(EntityVocService.class) ;
    private static final boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    private enum QueryConvertType {NONE, LOWER_CASE, UPPER_CASE, FIRST_UPPER, FIRST_LAT_UPPER}

    public Class getEntityClass() {
    	return entityClass ;
    }
    public EntityVocService(String aEntityClass, String[] aNames, String[] aQueriedFields
        , String aParent, String aQueryAppend, String aConvertType) throws ClassNotFoundException {
//        entityClassName = aEntityClass;
        names = aNames;
        entityClass = classLoaderHelper.loadClass(aEntityClass);
        queriedFields = aQueriedFields ;
        entityName = entityHelper.getEntityName(entityClass) ;
        parentField = StringUtil.isNullOrEmpty(aParent) ? null : aParent ;
        appendQuery = StringUtil.isNullOrEmpty(aQueryAppend) ? null : aQueryAppend ;
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
    public QueryConvertType getQueryConvertType() { return queryConvertType ; }
    public void setQueryConvertType(QueryConvertType aQueryConvertType) { queryConvertType = aQueryConvertType ; }

    /** Тип конвертации запроса */
    private QueryConvertType queryConvertType = QueryConvertType.NONE ;

    private final String entityName ;
    /** Поля для запроса */
    public String[] getQueriedFields() { return queriedFields ; }

    /** Поля для запроса */
    private final String[] queriedFields ;
    /** Свойства с названиями */
    public String[] getNames() { return names ; }

    /** Свойства с названиями */
    private final String[] names ;

    /** Название Entity Bean */
    private final Class entityClass ;


    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if(StringUtil.isNullOrEmpty(aId)) return "" ; //throw new VocServiceException("Нет идентификатора ");
        Object obj = aContext.getEntityManager().find(entityClass, entityHelper.convertId(entityClass, aId)) ;
        try {
            return getNameFromEntity(obj) ;
        } catch (Exception e) {
            throw new VocServiceException("Ошибка при получении наименования у объекта "+entityClass+" "+aId);
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
            		BaseEntity entity =  (BaseEntity) aContext.getEntityManager().find(entityClass, id);
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
            if (queryConvertType == QueryConvertType.FIRST_LAT_UPPER) {
                queryDop = aQuery;
                queryDop = queryDop.toUpperCase();
                char c1 = queryDop.charAt(0);
                queryDop = queryDop.replaceFirst("Ю", ".");
                queryDop = queryDop.replaceFirst(",", ".");
                aQuery = getLat(c1) + (queryDop.length() > 1 ? queryDop.substring(1) : "");
            }
            
        	aQuery = aQuery.toUpperCase() ;
            StringBuilder sb = new StringBuilder();
            sb.append("from ").append(entityName) ;
            sb.append(" where ( ") ;
            boolean firstPassed = false ;
            for (String field : queriedFields) {
                if(firstPassed) sb.append(" or ") ; else firstPassed = true ;
                sb.append(" (upper(").append(field).append( ")  like :query) ") ;
                if (queryDop!=null) sb.append(" or (upper(").append(field).append( ")  like '%").append(queryDop).append("%')") ;
            }
            sb.append(" ) ") ;
            if(appendQuery!=null) {
                sb.append(" and ") ;
                sb.append(appendQuery) ;
            }
            if(parentField!=null) {
                sb.append(" and ") ;
                sb.append(parentField).append('=').append(aAdditional.getParentId()) ;
            }
            sb.append(" order by id") ;
            if (CAN_DEBUG) LOG.debug("findVocValueByQuery: query = " + sb);

            Query query  = aContext.getEntityManager().createQuery
                    (sb.toString())
                    .setParameter("query", "%" + aQuery + "%") ;
            List list = query.setMaxResults(aCount)
                    .getResultList();
            return createValues(list);
        }
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        // уще не установлен родитель
        if(parentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<>() ;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(entityName) ;
        Object id = null ;
        boolean whereAppended = false ;
        if(!StringUtil.isNullOrEmpty(aId)) {
            sb.append(" where id <= :id") ;
            whereAppended = true ;
            id = entityHelper.convertId(entityClass, aId) ;
        }

        if(appendQuery!=null) {
            if(!StringUtil.isNullOrEmpty(aId)) sb.append(" and ") ;
            else {
                whereAppended = true ;
                sb.append(" where ") ;
            }
            sb.append(appendQuery) ;
        }
        // parentProperty
        if(parentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            if(whereAppended) sb.append(" and ") ;
            else sb.append(" where ") ;
            sb.append(parentField).append('=').append(aAdditional.getParentId()) ;

        }
        sb.append(" order by id desc") ;
        if (CAN_DEBUG) LOG.debug("findVocValuePrevious: query = " + sb);
        Query query = aContext.getEntityManager().createQuery(sb.toString()) ;
        if(id!=null) {
            query.setParameter("id", id) ;
            if (CAN_DEBUG) LOG.debug("findVocValuePrevious: id = " + id);

        }
        List list = query.setMaxResults(aCount).getResultList();
        Collection<VocValue> values = createValues(list) ;
        ArrayList<VocValue> ret = new ArrayList<>();
        for (VocValue value : values) {
            ret.add(0, value);
        }
        return ret ;
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        // уще не установлен родитель
        if(parentField!=null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<>() ;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(entityName) ;
        Object id = null ;
        boolean whereAppended = false ;
        if(!StringUtil.isNullOrEmpty(aId)) {
            sb.append(" where id >= :id") ;
            whereAppended = true ;
            id = entityHelper.convertId(entityClass, aId) ;
        }

        if(appendQuery!=null) {
            if(!StringUtil.isNullOrEmpty(aId)) sb.append(" and ") ;
            else {
                whereAppended = true ;
                sb.append(" where ") ;
            }
            sb.append(appendQuery) ;
        }
        // parentProperty
        if(parentField!=null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            if(whereAppended) sb.append(" and ") ;
            else sb.append(" where ") ;
            sb.append(parentField).append('=').append(aAdditional.getParentId()) ;
        }
        sb.append(" order by id") ;
       // LOG.info(sb) ;
        Query query = aContext.getEntityManager().createQuery(sb.toString()) ;
        if(id!=null) {
            query.setParameter("id", id) ;
        }
        List list = query.setMaxResults(aCount).getResultList();
        return createValues(list) ;
    }



    private Collection<VocValue> createValues(List aEntities)  {
        LinkedList<VocValue> values = new LinkedList<>();
        if(aEntities!=null && !aEntities.isEmpty()) {
            for (Object entity : aEntities) {
                values.add(createVocValue(entity)) ;
            }
        }
        return values;
    }

    private String getNameFromEntity(Object aEntity) throws IllegalAccessException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        for (String field : names) {
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
        classLoaderHelper = null ;
        entityHelper = null ;
        map = null ;
    }

    public Map<String,String> enrusCreate() {
    	Map <String,String> enRusMap = new HashMap<>();
    	enRusMap.put("Й", "Q" ) ;
    	enRusMap.put("Ц", "W" ) ;
    	enRusMap.put("У","E"  ) ;
    	enRusMap.put( "К", "R" ) ;
    	enRusMap.put("Е", "T"  ) ;
    	enRusMap.put( "Ф","A" ) ;
    	enRusMap.put( "Ы", "S") ;
    	enRusMap.put("В", "D"  ) ;
    	enRusMap.put("А","F" ) ;
    	enRusMap.put("П","G"  ) ;
    	enRusMap.put("Я","Z"  ) ;
    	enRusMap.put("Ч","X"  ) ;
    	enRusMap.put("С","C"  ) ;
    	enRusMap.put( "М", "V" ) ;
    	enRusMap.put("И", "B" ) ;
    	enRusMap.put("Н", "Y"  ) ;
    	enRusMap.put("Г", "U"  ) ;
    	enRusMap.put("Ш", "I"  ) ;
    	enRusMap.put("Щ", "O"  ) ;
    	enRusMap.put("З","P" ) ;
    	enRusMap.put( "Р","H" ) ;
    	enRusMap.put("О", "J"  ) ;
    	enRusMap.put("Л","K"  ) ;
    	enRusMap.put("Д", "L" ) ;
    	enRusMap.put("Т","N" ) ;
    	enRusMap.put( "Ь","M" ) ;
    	enRusMap.put( "Ю","." ) ;
    	return enRusMap ;
    }
    private String getLat(char aChar) {
    	String ret =map.get(String.valueOf(aChar)) ;
    	return ret !=null ? ret: String.valueOf(aChar) ;
    }
    

    Map<String, String> map = enrusCreate();
    private ClassLoaderHelper classLoaderHelper = ClassLoaderHelper.getInstance();
    private EntityHelper entityHelper = EntityHelper.getInstance();
    private final String parentField ;
    private final String appendQuery ;


}
