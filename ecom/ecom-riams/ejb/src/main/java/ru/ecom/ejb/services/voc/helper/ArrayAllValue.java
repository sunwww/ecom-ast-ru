package ru.ecom.ejb.services.voc.helper;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author esinev 18.08.2006 1:25:33
 */
public class ArrayAllValue implements IAllValue {

    private final LinkedList<VocValue> theValues = new LinkedList<>();

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

        theValues.add(new VocValue(aId, aName));
    }

    public Collection<VocValue> listAll(AllValueContext aContext) {
        return theValues;
    }

    public void destroy() {
        theValues.clear();
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName,
                                                    String aQuery, int aCount, VocAdditional aAdditional,
                                                    AllValueContext aContext) {
        String query = aQuery.toUpperCase();
        String findedId = null;
        boolean finded = false;
        LinkedList<VocValue> ret = new LinkedList<>();
        if (!StringUtil.isNullOrEmpty(aQuery)) {
            for (VocValue value : listAll(aContext)) {
                String name = value.getName();
                String id = value.getId();
                if (name != null && name.toUpperCase().startsWith(query)) {
                    findedId = id;
                    finded = true;
                }
                if (findedId == null && id != null && id.toUpperCase().startsWith(query)) {
                    findedId = id;
                    finded = true;
                }
                if (name != null && name.toUpperCase().indexOf(query) > -1) {
                    findedId = id;
                    finded = true;
                }
                if (id != null && id.toUpperCase().indexOf(query) > -1) {
                    findedId = id;
                    finded = true;
                }
                if (finded) {
                    ret.add(value);
                    if (ret.size() > aCount) break;
                }
            }
        }
        return ret;
    }
}
