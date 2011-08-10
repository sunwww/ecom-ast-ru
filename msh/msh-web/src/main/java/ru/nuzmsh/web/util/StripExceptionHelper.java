package ru.nuzmsh.web.util;

import java.util.HashSet;

/**
 * Отображает только ошибку в одну строку
 */
public class StripExceptionHelper {

    public String strip(Exception e) {
        StringBuilder sb = new StringBuilder("ОШИБКА: ");
        strip(sb, e) ;
        String s = sb.toString() ;
        theHash.clear() ;
        return s ;
    }

    private void strip(StringBuilder sb, Throwable e) {
        if(e==null) return ;
        String message = e.getMessage() ;
        if(message!=null) message = message.replace('\n', ' ') ;
        else message = e.toString() ;
        message = dressOff(message) ;
        //message = message.trim() ;
        
        // убираем дублирование сообщений
        System.out.println(message +" = "+ theHash);
        if(!theHash.contains(message)) {
            sb.append(message) ;
            theHash.add(message) ;
            if(e.getCause()!=null) sb.append(" :: ") ;
        }
        
        if(e.getCause()!=null) {
            strip(sb, e.getCause()) ;
        }
        
    }
    
    private String dressOff(String s) {
        s = s.replace("java.rmi.ServerException:", "");
        s = s.replace("EJBException:; nested exception is:", "");
        s = s.replace("javax.ejb.EJBException:", "");
        s = s.replace("javax.naming.NameNotFoundException:", "");
        s = s.trim();
        s = s.replace('\'', ' ') ;
        s = s.replace('"', ' ') ;
        return s ;
    }
    private final HashSet<String> theHash = new HashSet<String>() ; 
}
