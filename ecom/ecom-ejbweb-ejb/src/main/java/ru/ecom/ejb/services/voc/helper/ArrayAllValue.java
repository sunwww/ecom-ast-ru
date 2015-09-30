package ru.ecom.ejb.services.voc.helper;

import java.util.Collection;
import java.util.LinkedList;

import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev 18.08.2006 1:25:33
 */
public class ArrayAllValue implements IAllValue {

    public ArrayAllValue() {

    }
    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, AllValueContext aContext) throws VocServiceException {
    	String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;
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
