package ru.ecom.ejb.services.voc.helper;

import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 */
public class EntityVocAllValue implements IAllValue {

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;
    }
    public EntityVocAllValue(String aEntity, String aId, String aProperties, String aSortBy) {
        theId = aId ;
        theNameProperties = aProperties ;
        theEntity = aEntity ;
        theSortBy = aSortBy ;
    }

    public Collection<VocValue> listAll(AllValueContext aContext) {
        EntityManager manager = aContext.getEntityManager() ;
        List list = manager.createQuery("from "+theEntity+" c order by "+theSortBy).getResultList();
        LinkedList<VocValue> ret = new LinkedList<VocValue>();

        for (Object obj : list) {
            String id = getProperty(obj, theId) ;
            String name = getProperties(obj, theNameProperties) ;
            ret.add(new VocValue(id, name)) ;
        }
        return ret ;
    }

    private String getProperties(Object obj, String properties) {
        StringTokenizer st = new StringTokenizer(properties, ";, ");
        StringBuilder sb = new StringBuilder();
        while(st.hasMoreTokens()) {
            sb.append(getProperty(obj, st.nextToken())) ;
            sb.append(' ') ;
        }
        return sb.toString() ;
    }

    private String getProperty(Object obj, String theId)  {
        try {
            return PropertyUtil.getPropertyValue(obj, theId)+"" ;
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка получение значения свойства "+theId+" у объекта "+obj,e) ;
        }
    }

    public void destroy() {
    }

    private final String theEntity ;
    private final String theSortBy ;
    private final String theId ;
    private final String theNameProperties ;
	public Collection<VocValue> findVocValueByQuery(String aVocName,
			String aQuery, int aCount, VocAdditional aAdditional,
			AllValueContext aContext) throws VocServiceException {
        String query = aQuery.toUpperCase();
        String findedId = null;
        boolean finded=false;
        LinkedList<VocValue> ret = new LinkedList<VocValue>();
        if (!StringUtil.isNullOrEmpty(aQuery)) {
            for (VocValue value : listAll(aContext)) {
                String name = value.getName();
                String id = value.getId();
                if (name != null) {
                    if (name.toUpperCase().startsWith(query)) {
                        findedId = id;
                        finded=true;
                    }
                }
                if (findedId == null) {
                    if (id != null) {
                        if (id.toUpperCase().startsWith(query)) {
                            findedId = id;
                            finded=true;
                        }
                    }
                }
                if (name != null && name.toUpperCase().indexOf(query) > -1) {
                    findedId = id;
                    finded=true;
                }
                if (id != null && id.toUpperCase().indexOf(query) > -1) {
                    findedId = id;
                    finded=true;
                }
                if (finded) {
                    ret.add(value);
                    if (ret.size() > aCount) break;
                }
            }
        }
        return ret ;
	}
}
