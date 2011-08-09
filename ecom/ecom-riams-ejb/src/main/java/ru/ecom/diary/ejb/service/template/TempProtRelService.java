package ru.ecom.diary.ejb.service.template;

import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;

import org.jdom.Element;

import ru.ecom.ejb.services.voc.IVocConfigXmlService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;

/**
 * FIXME Интересно, для чего это?
 * Шаблоны протоколов их связка с классификатором и категориями + определяются шаблоны общие и личные (подается код врача)
 */
public class TempProtRelService implements IVocServiceManagement, IVocConfigXmlService {

    private enum QueryConvertType {NONE, LOWER_CASE, UPPER_CASE, FIRST_UPPER}

    public TempProtRelService() {

    }


    public void setEntityManager(EntityManager aManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void config(Element aElement) {
        Element tempElement = aElement.getChild("TempVocService") ;
        if(tempElement==null) throw new IllegalArgumentException("Нет элемента TempVocService в элементе "+aElement);
        setEntityName(getText(tempElement, "entity", null));
        setParent(getText(tempElement, "parent", null));
        setNameFields(getArray(tempElement, "names", new String[]{"name"}));
        setQueriedFields(getArray(tempElement, "queried", getNameFields()));
        setQueryConvertType(getText(tempElement,"queryConvertType","NONE"));
        try {
            setEntityClass(findEntityClassByName(getEntityName()));
        } catch (Exception e) {
            throw new IllegalStateException(e) ;
        }

    }

    // --- поиск класса Entity
    private Class findEntityClassByName(String aText) {
        return null ;
    }

    // ---
    private static String[] getArray(Element aParentElement, String aElementName, String[] aDefaultArray) {
        String text = getText(aParentElement,aElementName,createFromArray(aDefaultArray)) ;
        StringTokenizer st = new StringTokenizer(text,", ;:");
        LinkedList<String> list = new LinkedList<String>();
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

    // ---
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

    // ---
    private static String getText(Element aParentElement, String aElementName, String aDefaultValue) {
        String elm = aParentElement.getAttributeValue(aElementName) ;
        if(elm==null && aDefaultValue==null) throw new IllegalArgumentException("Нет аттрибута "+aElementName) ;
        if(elm==null) return aDefaultValue ;
        else return elm ;
    }


    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //                          ------ Поля запроса ------

    /** Entity */
    public String getEntityName() { return theEntityName ; }
    public void setEntityName(String aEntityName) { theEntityName = aEntityName ; }

    /** Поля для запроса */
    public String[] getQueriedFields() { return theQueriedFields ; }
    public void setQueriedFields(String[] aQueriedFields) { theQueriedFields = aQueriedFields ; }

    /** Поля для названия */
    public String[] getNameFields() { return theNameFields ; }
    public void setNameFields(String[] aNameFields) { theNameFields = aNameFields ; }

    /** Тип конвертации запроса */
    public QueryConvertType getQueryConvertType() { return theQueryConvertType ; }
    public void setQueryConvertType(String aText) {
        if (aText.equals("NONE")) {
            setQueryConvertType(QueryConvertType.NONE);
        } else if (aText.equals("FIRST_UPPER")) {
            setQueryConvertType(QueryConvertType.FIRST_UPPER);
        } else if (aText.equals("LOWER_CASE")) {
            setQueryConvertType(QueryConvertType.LOWER_CASE);
        } else if (aText.equals("UPPER_CASE")) {
            setQueryConvertType(QueryConvertType.UPPER_CASE);
        } else {
            throw new IllegalArgumentException("Нет зарегестрированного типа конвертера для "+aText) ;
        }
    }

    public void setQueryConvertType(QueryConvertType aQueryConvertType) {
        theQueryConvertType = aQueryConvertType ;
    }

    /** Родитель */
    public String getParent() { return theParent ; }
    public void setParent(String aParent) {
        theParent = aParent ;
//        String[] param = getArray()

    }

    /** Дополнение к запросу */
    public String getQueryAppend() { return theQueryAppend ; }
    public void setQueryAppend(String aQueryAppend) { theQueryAppend = aQueryAppend ; }

    /** Категория классификатора */
    public Long getAdditionCategory() { return theAdditionCategory ; }
    public void setAdditionCategory(Long aAdditionCategory) { theAdditionCategory = aAdditionCategory ; }

    /** Название классификатора */
    public String getAdditionClassif() { return theAdditionClassif ; }
    public void setAdditionClassif(String aAdditionClassif) { theAdditionClassif = aAdditionClassif ; }

    /** Id категории классификатора */
    public Long getCategoryId() { return theCategoryId ; }
    public void setCategoryId(Long aCategoryId) { theCategoryId = aCategoryId ; }

    /** Название классификатора */
    public String getClassifName() { return theClassifName ; }
    public void setClassifName(String aClassifName) { theClassifName = aClassifName ; }

    /** Класс Entity классификатора */
    public Class getClassifClass() { return theClassifClass ; }
    public void setClassifClass(Class aClassifClass) { theClassifClass = aClassifClass ; }

    /** Класс Entity */
    public Class getEntityClass() { return theEntityClass ; }
    public void setEntityClass(Class aEntityClass) { theEntityClass = aEntityClass ; }

    /** Класс Entity */
    private Class theEntityClass ;

    /** Класс Entity классификатора */
    private Class theClassifClass ;

    /** Название классификатора */
    private String theClassifName ;

    /** Id категории классификатора */
    private Long theCategoryId ;

    /** Название классификатора */
    private String theAdditionClassif ;

    /** Категория классификатора */
    private Long theAdditionCategory ;

    /** Дополнение к запросу */
    private String theQueryAppend ;

    /** Родитель */
    private String theParent ;

    /** Тип конвертации запроса */
    private QueryConvertType theQueryConvertType = QueryConvertType.NONE;

    /** Поля для названия */
    private String[] theNameFields ;

    /** Поля для запроса */
    private String[] theQueriedFields ;

    /** Entity */
    private String theEntityName ;

}
