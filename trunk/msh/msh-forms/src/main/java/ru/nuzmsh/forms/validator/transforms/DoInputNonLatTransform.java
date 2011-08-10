package ru.nuzmsh.forms.validator.transforms;

import ru.nuzmsh.forms.validator.ITransform;

import java.util.HashMap;

/**
 * @author esinev
 * Date: 20.02.2006
 * Time: 9:30:19
 */
public class DoInputNonLatTransform implements ITransform {
    public Object transform(Object aValue) {
        String str ;
        if(aValue!=null) {
            if(aValue instanceof String) {
                str = (String) aValue ;
            } else {
                str = aValue.toString() ;
            }
            int len = str.length() ;
            StringBuilder sb = new StringBuilder(str);
            for(int i=0; i<len; i++) {
                char ch = sb.charAt(i) ;
                Character repChar = theHash.get(ch) ;
                if(repChar!=null) {
                    sb.setCharAt(i, repChar);
                }
            }
            return sb.toString() ;
        }
        return null ;
    }

    private final HashMap<Character,Character> theHash = new HashMap<Character, Character>();

    public DoInputNonLatTransform() {
        theHash.put('A','А') ;
        theHash.put('a','а') ;

        theHash.put('B','В') ;

        theHash.put('H','Н') ;

        theHash.put('P','Р') ;

        theHash.put('O','О') ;
        theHash.put('o','о') ;

        theHash.put('E','Е') ;
        theHash.put('e','е') ;

        theHash.put('T','Т') ;

        theHash.put('Y','У') ;
        theHash.put('y','у') ;

        theHash.put('K','К') ;
        theHash.put('k','к') ;

        theHash.put('X','Х') ;
        theHash.put('x','х') ;

        theHash.put('C','С') ;
        theHash.put('c','с') ;

        theHash.put('M','М') ;


    }

}
