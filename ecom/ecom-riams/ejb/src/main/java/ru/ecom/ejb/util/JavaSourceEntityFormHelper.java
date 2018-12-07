package ru.ecom.ejb.util;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.lang.reflect.Method;

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
