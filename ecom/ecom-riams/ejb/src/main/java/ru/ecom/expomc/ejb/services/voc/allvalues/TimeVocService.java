package ru.ecom.expomc.ejb.services.voc.allvalues;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.voc.IVocConfigXmlService;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.impdoc.ImportTime;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.Query;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 */
public class TimeVocService implements IVocContextService, IVocServiceManagement, IVocConfigXmlService {

    private static final Logger LOG = Logger.getLogger(TimeVocService.class) ;

    private enum QueryConvertType {NONE, LOWER_CASE, UPPER_CASE}

    public TimeVocService() {
    }

    private Class findEntityClassByName(String aEntityName) throws IOException, JDOMException, ClassNotFoundException {

        Class ret = null ;
        try (InputStream in = getClass().getResourceAsStream("/META-INF/persistence.xml")) {
            Document doc = new SAXBuilder().build(in);
            Element rootElement = doc.getRootElement();
            List<Element> persistenceUnits = rootElement.getChildren("persistence-unit");
            for (Element persistenceUnit : persistenceUnits) {
                List<Element> classes = persistenceUnit.getChildren("class");
                for (Element classElement : classes) {
                    Class clazz = classLoaderHelper.loadClass(classElement.getTextTrim());
                    if (aEntityName.equals(entityHelper.getEntityName(clazz))) {
                        ret = clazz;
                        break;
                    }
                }
            }
        }
        if(ret==null) throw new IllegalStateException("Не найден класс для Entity "+aEntityName) ;
        return ret ;
    }

    public void config(Element aElement) {
        Element timeElement = aElement.getChild("timeVoc") ;
        if(timeElement==null) throw new IllegalArgumentException("Нет элемента timeVoc в элементе "+aElement);
        setEntityName(getText(timeElement, "entity",null));
        setNameFields(getArray(timeElement, "names",new String[]{"name"}));
        setCodeField(getText(timeElement,"code","code"));
        setQueriedFields(getArray(timeElement,"queried",getNameFields()));
        setQueryConvertType(getText(timeElement,"queryConvertType","NONE"));
        try {
            entityClass = findEntityClassByName(getEntityName()) ;
        } catch (Exception e) {
            throw new IllegalStateException(e) ;
        }
    }


    private void setQueryConvertType(String aText) {
        if(aText.equals("NONE")) {
        } else if(aText.equals("UPPER_CASE")) {
            setQueryConvertType(QueryConvertType.UPPER_CASE);
        } else if(aText.equals("LOWER_CASE")) {
            setQueryConvertType(QueryConvertType.LOWER_CASE);
        } else {
            throw new IllegalArgumentException("Нет зарегестрированного типа конвертера для "+aText) ;
        }
    }


    private static String[] getArray(Element aParentElement, String aElementName, String[] aDefaultArray) {
        String text = getText(aParentElement,aElementName,createFromArray(aDefaultArray)) ;
        StringTokenizer st = new StringTokenizer(text,", ;:");
        LinkedList<String> list = new LinkedList<>();
        while(st.hasMoreTokens()) {
            String token = st.nextToken() ;
            list.add(token) ;
        }
        String[] ret = new String[list.size()];
        for(int i=0; i<list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret ;
    }

    private static String createFromArray(String[] aArray) {
        StringBuilder sb = new StringBuilder();
        boolean firstPassed = false  ;
        for (String s : aArray) {
            if(firstPassed) {
                sb.append(',') ;
            } else {
                firstPassed = true ;
            }
            sb.append(s) ;
        }
        return sb.toString();
    }
    private static String getText(Element aParentElement, String aElementName, String aDefaultValue) {
        String elm = aParentElement.getAttributeValue(aElementName) ;
        if(elm==null && aDefaultValue==null) throw new IllegalArgumentException("Нет аттрибута "+aElementName) ;
        if(elm==null) return aDefaultValue ;
        else return elm ;
    }


    /** Поля для названия */
    public String[] getNameFields() { return nameFields ; }
    public void setNameFields(String[] aNameFields) { nameFields = aNameFields ; }

    /** Поля для названия */
    private String[] nameFields ;
    /** Поля для поиска */
    public String[] getQueriedFields() { return queriedFields ; }
    public void setQueriedFields(String[] aQueriedFields) { queriedFields = aQueriedFields ; }

    /** Поля для поиска */
    private String[] queriedFields ;
    /** Код */
    public String getCodeField() { return codeField ; }
    public void setCodeField(String aCodeField) { codeField = aCodeField ; }

    /** Код */
    private String codeField ="code" ;
    /** Тип конвертации запроса */
    public QueryConvertType getQueryConvertType() { return queryConvertType ; }
    public void setQueryConvertType(QueryConvertType aQueryConvertType) { queryConvertType = aQueryConvertType ; }

    /** Тип конвертации запроса */
    private QueryConvertType queryConvertType = QueryConvertType.NONE ;
//    /** Поле с названием */
//    public String getNameField() { return nameField ; }
//    public void setNameField(String aNameField) { nameField = aNameField ; }
//
//    /** Поле с названием */
//    private String nameField ;

    /** Entity */
    public String getEntityName() { return entityName ; }
    public void setEntityName(String aEntityName) { entityName = aEntityName ; }

    /** Entity */
    private String entityName ;

    private long findActualImport(VocContext aContext) {
        List<ImportDocument> list = aContext.getEntityManager().createQuery("from ImportDocument where entityClassName = :clazz order by id")
                .setParameter("clazz", entityClass.getName())
                .getResultList() ;
        if(list.isEmpty()) throw new IllegalStateException("Нет документа с типом "+entityClass) ;
        ImportDocument doc = list.iterator().next() ;
        Collection<ImportTime> times = doc.getTimes() ;
        if(times.isEmpty()) throw new IllegalArgumentException("Нет импортированных данных для документа "+doc.getComment()) ;
        long ret = -1 ;
        for (ImportTime time : times) {
            ret = time.getId() ;
        }
        return ret ;
    }
    private long getIdByCode(String aCode, VocContext aContext) {
        List list = aContext.getEntityManager().createQuery("from "+entityName+" where time = :time and "+codeField+" = :code")
                .setParameter("time", findActualImport(aContext))
                .setParameter("code", aCode)
                .getResultList() ;
        long id ;
        if(list!=null && !list.isEmpty()) {
            Object obj = list.iterator().next() ;
            try {
                String strId = PropertyUtil.getPropertyValue(obj, "id").toString() ;
                id = Long.parseLong(strId)  ;
            } catch (Exception e) {
                throw new IllegalStateException("Ошибка получения свойства id с кодом "+aCode,e) ;
            }
        } else {
            throw new IllegalStateException("Нет идентификатора с кодом "+(aCode==null ? "$NULL$" : aCode)) ;
        }
        return id ;
    }

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if(StringUtil.isNullOrEmpty(aId)) return "" ;
        // todo изменить на manager.find(...)
        List list = aContext.getEntityManager().createQuery("from "+entityName+" where id= :id")
                .setParameter("id", getIdByCode(aId, aContext)).getResultList();
        String name ;
        if(list!=null  && !list.isEmpty()) {
            Object firstObject = list.iterator().next() ;
            try {
                name = getNameFromEntity(firstObject) ; //PropertyUtil.getPropertyValue(firstObject, nameField) ;
            } catch (Exception e) {
                throw new VocServiceException("Ошибка получения имени по индексу "+aId);
            }
        } else {
            name = "Нет такого объекта с ИД "+aId ;
        }
        return name ;
    }

    private String getNameFromEntity(Object aEntity) throws IllegalAccessException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        boolean firstPassed = false ;
        for (String field : nameFields) {
            if(firstPassed) {
                sb.append(" ") ;
            } else {
                firstPassed = true ;
            }
            sb.append(PropertyUtil.getPropertyValue(aEntity, field)) ;
        }
        return sb.toString();
    }


    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if(StringUtil.isNullOrEmpty(aQuery)) {
            return findVocValueNext(aVocName, null, aCount, aAdditional, aContext);
        } else {
            switch(queryConvertType) {
                case LOWER_CASE: aQuery = aQuery.toLowerCase() ; break ;
                case UPPER_CASE: aQuery = aQuery.toUpperCase() ; break ;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("from ").append(entityName) ;
            sb.append(" where time = :time and ( ") ;
            boolean firstPassed = false ;
            for (String field : queriedFields) {
                if(firstPassed) sb.append(" or ") ; else firstPassed = true ;
                sb.append(field).append( "  like :query") ;
            }
            sb.append(" ) order by id") ;

            LOG.info(sb) ;

            // ("from "+entityName+" where time = :time and "+theNameField+" like :query order by id")

            Query query  = aContext.getEntityManager().createQuery
                    (sb.toString())
                    .setParameter("time", findActualImport(aContext))
                    .setParameter("query", "%" + aQuery + "%") ;

            List list = query.setMaxResults(aCount)
                    .getResultList();
            return createValues(list);
        }
    }

    private VocValue createVocValue(Object aEntity) {
        try {
            String id = PropertyUtil.getPropertyValue(aEntity, codeField).toString() ;
            String name = getNameFromEntity(aEntity) ; //PropertyUtil.getPropertyValue(aEntity, nameField).toString() ;
            return new VocValue(id, name) ;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания VocValue",e);
        }
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(entityName) ;
        sb.append(" where time = :time ") ;
        if(!StringUtil.isNullOrEmpty(aId)) {
            sb.append("and id <= ").append(getIdByCode(aId, aContext)) ;
        }
        sb.append("order by id desc") ;
        List list = aContext.getEntityManager().createQuery(sb.toString())
                .setParameter("time", findActualImport(aContext))
                .setMaxResults(aCount)
                .getResultList();
        Object last = null ;
        for (Object obj : list) {
            last = obj ;
        }
        if(last!=null) {
            try {
                String id = PropertyUtil.getPropertyValue(last, codeField).toString() ;
                return findVocValueNext(aVocName, id, aCount, aAdditional, aContext) ;
            } catch (Exception e) {
                throw new VocServiceException("Ошибка получения идентификатора",e);
            }
        } else {
            LinkedList<VocValue> values = new LinkedList<>();
            values.add(new VocValue("", "Не найдено")) ;
            return values;
        }
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        StringBuilder sb = new StringBuilder();
        sb.append("from ").append(entityName) ;
        sb.append(" where time = :time ") ;
        if(!StringUtil.isNullOrEmpty(aId)) {
            sb.append("and id >= ").append(getIdByCode(aId, aContext)) ;
        }
        sb.append("order by id") ;
        List list = aContext.getEntityManager().createQuery(sb.toString())
                .setParameter("time", findActualImport(aContext))
                .setMaxResults(aCount)
                .getResultList();
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

    public void destroy() {
        entityClass = null ;
        classLoaderHelper = null ;
        entityHelper = null ;
    }

    private ClassLoaderHelper classLoaderHelper = ClassLoaderHelper.getInstance();
    private Class entityClass ;
    private EntityHelper entityHelper = EntityHelper.getInstance();
}
