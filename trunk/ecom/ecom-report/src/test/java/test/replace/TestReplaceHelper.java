package test.replace;

import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.SetValueException;

/**
 *
 */
public class TestReplaceHelper {
    public static void main(String[] args) throws SetValueException {
        ReplaceHelper rh = new ReplaceHelper();
        String test = "hello ${df} ${dddd}" ;
        Object result = rh.replaceWithValues(test, new IValueGetter() {
            public Object getValue(String aExpression) throws SetValueException {
                return "замена" ;
            }

            public void set(String aKey, Object aValue) throws SetValueException {

            }
        }) ;
        System.out.println("result = " + result);
    }



}
