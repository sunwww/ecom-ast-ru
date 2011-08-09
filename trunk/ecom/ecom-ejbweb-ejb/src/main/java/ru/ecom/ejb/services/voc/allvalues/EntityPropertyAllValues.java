package ru.ecom.ejb.services.voc.allvalues;

import java.lang.reflect.Method;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;

public class EntityPropertyAllValues extends ArrayAllValue {

    public EntityPropertyAllValues(Class aClass) {
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if(name.startsWith("is") || name.startsWith("get")) {
                String propertyName = PropertyUtil.getPropertyName(method) ;
                Comment comment = method.getAnnotation(Comment.class) ;
                addValue(propertyName, comment!=null ? comment.value() : propertyName);
            }
        }
    }


}
