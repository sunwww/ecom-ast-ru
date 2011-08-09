package ru.ecom.ejb.services.voc.helper;

import java.util.Collection;
import java.util.LinkedList;

import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev 18.08.2006 1:25:33
 */
public class ArrayAllValue implements IAllValue {

    public ArrayAllValue() {

    }

    public void addValue(String aId, String aName) {

        theValues.add(new VocValue(aId, aName)) ;
    }

    public Collection<VocValue> listAll(AllValueContext aContext) {
        return theValues;
    }

    public void destroy() {
        theValues.clear();
    }

    private final LinkedList<VocValue> theValues = new LinkedList<VocValue>();

}
