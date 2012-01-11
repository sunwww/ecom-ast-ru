package test.replace;

import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.SetValueException;

/**
 *
 */
public class TestReplaceHelper {
    public static void main(String[] args) throws SetValueException {
        String kladr = "300000010000000" ;
        String lastKl = kladr.substring(kladr.length()-1, kladr.length()) ;
		while (lastKl!=null&&lastKl.equals("0")) {
			kladr = kladr.substring(0,kladr.length()-1) ;
			lastKl = kladr.substring(kladr.length()-1, kladr.length()) ;
		}
		System.out.println("kladr = " +kladr);
    	/*ReplaceHelper rh = new ReplaceHelper();
        String test = "hello ${df} ${dddd}" ;
        Object result = rh.replaceWithValues(test, new IValueGetter() {
            public Object getValue(String aExpression) throws SetValueException {
                return "замена" ;
            }

            public void set(String aKey, Object aValue) throws SetValueException {

            }
        }) ;
        System.out.println("result = " + result);*/
    }



}
