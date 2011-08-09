package ru.ecom.report.replace;

import bsh.Interpreter;
import bsh.EvalError;

import java.util.TreeSet;

/**
 *
 */
public class BshValueGetter implements IValueGetter {

    public void set(String aKey, Object aValue) throws SetValueException {
        try {
            theInterpreter.set(aKey, aValue);
        } catch (EvalError evalError) {
            throw new SetValueException("Ошибке установки "+aKey+" = "+aValue,evalError);
        }
        theKeys.add(aKey) ;
    }

    public Object getValue(String aExpression) throws SetValueException {
        if(aExpression!=null && (aExpression.indexOf('[')!=-1 || aExpression.indexOf(']')!=-1)) {
            aExpression = aExpression.replace('[','{') ;
            aExpression = aExpression.replace(']','}') ;
        }
        aExpression = aExpression.replace("&quot;","\"") ;
        aExpression = aExpression.replace("&lt;","<") ;
        aExpression = aExpression.replace("&gt;",">") ;
        try {
            return theInterpreter.eval(aExpression);
        } catch (EvalError evalError) {
            throw new SetValueException("Ошибка получения '"+aExpression+"'",evalError);
        }
    }

    public void clear() throws EvalError {
        for (String key : theKeys) {
            theInterpreter.unset(key);
        }
    }

    TreeSet<String> theKeys = new TreeSet<String>();
    Interpreter theInterpreter = new Interpreter();
}
