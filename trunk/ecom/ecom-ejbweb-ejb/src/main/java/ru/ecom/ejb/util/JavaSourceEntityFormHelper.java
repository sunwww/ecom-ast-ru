package ru.ecom.ejb.util;

import java.lang.reflect.Method;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Выводит поля для формы
 */
public class JavaSourceEntityFormHelper {

    public String getSource(Class aClass) {
        StringBuilder sb = new StringBuilder();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            Comment comment = method.getAnnotation(Comment.class) ;
            if(comment!=null) {

            }
        }
        return null ;
    }



}
