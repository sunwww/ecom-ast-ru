package ru.ecom.expomc.ejb.services.voc.allvalues;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.ecom.expomc.ejb.services.check.checkers.*;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Список допустимыъ проверок
 */
public class AllowedChecksAllValues extends ArrayAllValue {
    public AllowedChecksAllValues() {
        // ДОБАВЛЯТЬ ТОЛЬКО В КОНЕЦ !!!
    	
        add(RequiredCheck.class) ;
        add(CheckInDomain.class) ;
        add(CheckNotInDomain.class) ;
        add(ChangeStringEquals.class) ;
        add(CheckMkbDiapazon.class) ;
        add(CheckDates.class) ;
        add(ChangeUpperCase.class) ;
        add(ChangeLowerCase.class) ;
        add(ChangeRussianToLatin.class) ;
        add(ChangeLatinToRussian.class) ;
        add(ChangeOToZero.class) ;
        add(ChangeZeroToLatinO.class) ;


        add(BeanShellCheck.class) ;

        add(ChangeIfEmpty.class) ;
        
        add(ChangeSubstring.class) ;
        

        // ДОБАВЛЯТЬ ТОЛЬКО В КОНЕЦ !!!
        
        
    }

    public Set<Map.Entry<Long,Class>> getChecksEntries() {
        return hash.entrySet() ;
    }

    public Collection<Class> getChecksClasses() {
        return hash.values();
    }

    public Class getCheckClassById(long aCheckId) {
        return hash.get(aCheckId);
    }

    private void add(Class aClass) {
        index++ ;
        Comment comment = (Comment) aClass.getAnnotation(Comment.class) ;
        StringBuilder sb = new StringBuilder();
        if(comment!=null) {
            sb.append(comment.value()) ;
        }
        sb.append(" (") ;
        sb.append(aClass.getSimpleName()) ;
        sb.append(")") ;
        addValue(String.valueOf(index), sb.toString());
        hash.put(index, aClass) ;

    }

    private TreeMap<Long, Class> hash = new TreeMap<>();
    private long index = 0 ;
}
