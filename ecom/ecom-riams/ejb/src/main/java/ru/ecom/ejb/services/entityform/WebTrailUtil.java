package ru.ecom.ejb.services.entityform;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 25.10.2006
 * Time: 22:40:23
 * To change this template use File | Settings | File Templates.
 */
public class WebTrailUtil {

    public static String getFirstPropertyName(Class aClass) {
        WebTrail trail = findWebTrail(aClass) ;
        String[] names = trail.nameProperties() ;
        if(names.length > 0) {
            return names[0] ;
        } else {
            throw new IllegalStateException("Нет свойства nameProperties у аннотации WebTrail в классе "+aClass) ;
        }
    }
    
    private static WebTrail findWebTrail(Class aClass) {
        WebTrail trail = (WebTrail) aClass.getAnnotation(WebTrail.class);
        if(trail ==null) {
            if(Void.class.equals(aClass)) {
                throw new IllegalArgumentException("В аннотации Parent не указан атрибут parentForm") ;
            } else {
                throw new IllegalArgumentException("У класса "+aClass+" нет аннотации WebTrail") ;
            }
        }
        return trail ;
    }
    public static String[] getPropertiesName(Class aClass) {
    	return findWebTrail(aClass).nameProperties() ;
    }
    
    public static String[] getListStyle(Class aClass) {
    	String[] res = findWebTrail(aClass).listStyle() ;
    	return res.length > 0 ? res : null;
    }

}
