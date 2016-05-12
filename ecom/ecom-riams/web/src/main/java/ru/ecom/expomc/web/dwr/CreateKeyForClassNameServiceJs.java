package ru.ecom.expomc.web.dwr;

import ru.nuzmsh.util.StringUtil;

/**
 * Создает ключ по имени класса. Например. ru.hello.HelloString - > HELLO_STRING
 */
public class CreateKeyForClassNameServiceJs {

    public String createKey(String aClassName) {
        String name ;
        if(StringUtil.isNullOrEmpty(aClassName)) {
            throw new IllegalArgumentException("Нет названия класса") ;
        }
        int pos = aClassName.lastIndexOf('.') ;
        if(pos>=0 && pos+1<aClassName.length()-1) {
            name = aClassName.substring(pos+1) ;
        } else {
            name = aClassName;
        }
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<name.length(); i++) {
            char ch = name.charAt(i) ;
            boolean previousUpperCase = false ;
            if(i !=0) {
                previousUpperCase = Character.isUpperCase(name.charAt(i-1)) ;
            }

            if(Character.isUpperCase(ch) && !previousUpperCase) {
                sb.append("_") ;
            }
            sb.append(Character.toUpperCase(ch)) ;
        }
        if(sb.length()>0 && sb.charAt(0)=='_') {
            sb.deleteCharAt(0) ;
        }
        return sb.toString() ;
    }
}
