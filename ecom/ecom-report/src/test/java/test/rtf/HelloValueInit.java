package test.rtf;

import ru.ecom.report.replace.IValueInit;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.SetValueException;

import java.util.Map;

/**
 * @author esinev
 * Date: 11.10.2006
 * Time: 14:29:36
 */
public class HelloValueInit implements IValueInit {
    public void init(Map<String, String> aParams, IValueGetter aValueGetter) throws SetValueException {
        aValueGetter.set("hello", "hello");
    }
}
