package ru.ecom.expomc.web.actions.importdata;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspWriter;

import ru.ecom.expomc.ejb.domain.impdoc.IUrlEditable;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;

/**
 * 
 */
public class ImportDataViewHelper {

	public static void printTableHeader(JspWriter out) throws IOException {
        out.println("<table border='1' class='tabview sel tableArrow'>");
        out.println("<tr><th>Поле</th><th>Комментарий</th><th>Значение</th></tr>");
	}
	
    public static void print(Object aObj, JspWriter out) throws IOException, IllegalAccessException, InvocationTargetException {
        Class clazz = aObj.getClass();
        printTableHeader(out) ;
        for (Method method : clazz.getMethods()) {
            if(accept(method)) {
            	print(aObj, out, method) ;
            }
        }
        out.println("</table>");
    }
    
    public static void print(Object aObj, JspWriter out, Method method) throws IOException, IllegalAccessException, InvocationTargetException {
        out.println("<tr>");
        out.println("<td>") ;
        out.println(PropertyUtil.getPropertyName(method)) ;
        out.println("</td>") ;
        out.println("<td>") ;
        out.println(getComment(method)) ;
        out.println("</td>") ;
        out.println("<td>") ;
        Object value = method.invoke(aObj) ;

        out.println(value!=null ? value : "&nbsp;") ;
        out.println("</td>") ;
        out.println("</tr>");
    }    


    public static void printUrlEdit(Object aObj, JspWriter out) throws IOException {
    	if(aObj instanceof IUrlEditable) {
    		IUrlEditable urlEditable = (IUrlEditable) aObj ;
    		out.print("<li><a href='") ;
    		out.print(urlEditable.getUrlEdit()) ;
    		out.println("'>Изменить исходные данных</a></li>");
    	}
    }
    
    private static boolean accept(Method aMethod) {
        return aMethod.getAnnotation(Comment.class)!=null ;
    }

    private static String getComment(Method aMethod) {
    	Comment comment = aMethod.getAnnotation(Comment.class) ;
    	return comment!=null?comment.value() : aMethod.getName() ;
    }

}
