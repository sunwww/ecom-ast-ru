package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.voc.helper.AllValueContext;
import ru.ecom.ejb.services.voc.helper.IAllValue;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * Список свойств у документа
 */
public class ImportDocumentPropertiesAllValues implements IAllValue {
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
    protected Collection<VocValue> listByDocumentId(String aDocumentId, EntityManager aEntityManager) {
    	if(StringUtil.isNullOrEmpty(aDocumentId)) {
    		throw new IllegalArgumentException("Нет аргумента "+aDocumentId) ;
    	}
        LinkedList<VocValue> values = new LinkedList<VocValue>();

        if (!StringUtil.isNullOrEmpty(aDocumentId)) {
            ImportDocument document = aEntityManager.find(ImportDocument.class
                    , Long.parseLong(aDocumentId));
            try {
                Class clazz = ClassLoaderHelper.getInstance().loadClass(document.getEntityClassName());

                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    Comment annotation = method.getAnnotation(Comment.class);
                    if (annotation != null) {
                        String id = PropertyUtil.getPropertyName(method);
                        String name = annotation.value();
                        values.add(new VocValue(id, name));
                    }
                }
                return values;

            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Ошибка при выборе: " + e, e);
            }
        }
        return values;
    }

    public Collection<VocValue> listAll(AllValueContext aContext) {
        String parentId = aContext.getVocAdditional().getParentId();
        return listByDocumentId(parentId,aContext.getEntityManager()) ;
    }

    public void destroy() {
        
    }


}
