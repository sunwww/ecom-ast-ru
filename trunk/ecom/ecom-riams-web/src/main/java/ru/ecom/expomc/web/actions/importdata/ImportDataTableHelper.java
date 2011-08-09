package ru.ecom.expomc.web.actions.importdata;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.jsp.JspWriter;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * 
 */
public class ImportDataTableHelper {


    public static void print(Collection aCollection, JspWriter out) throws IOException, IllegalAccessException, InvocationTargetException {
        if(aCollection==null) {
            out.println("Нет данных") ;
        } else {
            out.println("<table class='tabview sel tableArrow' border='1'>") ;
            boolean isFirst = true ;
            for (Object obj : aCollection) {
                if(isFirst) {
                    printHeader(obj,out);
                }
                printRow(obj, out) ;
                isFirst = false ;
            }
            out.println("</table>") ;
        }
    }

    private static void printRow(Object aObj, JspWriter out) throws IOException, IllegalAccessException, InvocationTargetException {
        Class clazz = aObj.getClass();
        out.println("<tr>");
        for (Method method : clazz.getMethods()) {
            if(accept(method)) {
                out.println("<td>") ;
                out.println(method.invoke(aObj)) ;
                out.println("</td>") ;
            }
        }
        out.println("</tr>");
    }


    private static void printHeader(Object aObj, JspWriter out) throws IOException {
        Class clazz = aObj.getClass();
        out.println("<tr>");
        for (Method method : clazz.getMethods()) {
            if(accept(method)) {
                out.println("<th>") ;
                out.println(method.getAnnotation(Comment.class).value()) ;
                out.println("</th>") ;
            }
        }
        out.println("</tr>");
    }

    private static boolean accept(Method aMethod) {
        return aMethod.getAnnotation(Comment.class)!=null ;
    }
}
