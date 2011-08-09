package ru.ecom.report.replace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.nuzmsh.util.PropertyUtil;

/**
 */
public class ReplaceHelper {

    private final static Log LOG = LogFactory.getLog(ReplaceHelper.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;

    /** Режим RTF */
    public boolean getRtfMode() { return theRtfMode ; }
    public void setRtfMode(boolean aRtfMode) { theRtfMode = aRtfMode ; }

    /** Режим RTF */
    private boolean theRtfMode = false ;

    public Object replaceWithValues(String aLine, IValueGetter aValueGetter) throws SetValueException {
        if (aLine.startsWith("${") && aLine.indexOf("${",3)==-1&& aLine.endsWith("}")) {
        	//System.out.println("<--"+aLine) ;
        	try {
        		Object o = aValueGetter.getValue(aLine.substring(2, aLine.length() - 1)) ;
        		return o ;
        	}catch (Exception e) {
        		System.out.println(e.getCause()) ;
        		throw new SetValueException(e.getMessage(),e.getCause());
        	}
            
        } else {
        	//System.out.println("-->"+aLine) ;
            if (aLine.indexOf("$\\{") >= 0 || aLine.indexOf("${") >= 0) {
                StringBuilder sb = new StringBuilder(aLine);
                int result = 0;

                while ((result = replace(sb, result, aValueGetter, theRtfMode)) >= 0) {

                }
                return sb.toString();
            } else {
                return aLine;
            }
        }
    }


    private static int replace(StringBuilder aSb, int aFromIndex, IValueGetter aValueGetter, boolean aRtfMode) throws SetValueException {
        int from = aSb.indexOf(aRtfMode? "$\\{" : "${" , aFromIndex);
        //System.out.println("from = " + from);
//        if (from == -1) from = aSb.indexOf("${", aFromIndex);
        if (from > -1) {
            int to = aSb.indexOf(aRtfMode? "\\}" : "}",from+1);
            //System.out.println("to = " + to);
//            if (to == -1) to = aSb.indexOf("}");
            if (to > -1 && to>from) {
                String key = aSb.substring(aRtfMode?from+1:from, to);
                //System.out.println("key="+key) ;
                key = stripKey(key);
                //System.out.println("key1="+key) ;
                Object value = aValueGetter.getValue(key);
                String strValue ;
                if(value==null) {
                    strValue = "" ;
                } else {
                    try {
                        System.out.println("value.getClass() = " + value.getClass() +"  "+value);
                        strValue = (String) PropertyUtil.convertValue(value.getClass(), String.class, value ) ;
                    } catch (Exception e) {
                        LOG.warn("Ошибка преобразования",e);
                        System.out.println(e) ;
                        strValue = value.toString() ;
                    }
                }
                aSb.delete(from, aRtfMode ? to +2 : to + 1);
                aSb.insert(from, strValue);
                return from + strValue.length();
            }
        }
        return -1;
    }

    private static String stripKey(String aStr) {
        if (aStr != null && aStr.length() > 3) {
            return aStr.substring(2);
        } else {
            return aStr;
        }
    }

}
