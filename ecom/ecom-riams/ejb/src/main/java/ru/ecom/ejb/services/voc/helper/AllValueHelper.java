package ru.ecom.ejb.services.voc.helper;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author esinev 18.08.2006 1:09:44
 */
public class AllValueHelper implements IVocContextService, IVocServiceManagement {

    private static final Logger LOG = Logger.getLogger(AllValueHelper.class);
    private static final boolean CAN_TRACE = LOG.isDebugEnabled();


    public AllValueHelper(IAllValue aAllValue) {
        theAllValue = aAllValue;
    }

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        AllValueContext ctx = new AllValueContext(
                aAdditional, aContext.getEntityManager(), aContext.getSessionContext());
        return theAllValue.getNameById(aId, aVocName, aAdditional, ctx);
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        String query = aQuery.toUpperCase();
        String findedId = null;
        boolean finded;
        LinkedList<VocValue> ret = new LinkedList<>();
        if (!StringUtil.isNullOrEmpty(aQuery)) {
            for (VocValue value : listAll(aAdditional, aContext)) {
                finded = false;
                String name = value.getName();
                String id = value.getId();

                if (findedId == null && id != null && id.toUpperCase().startsWith(query)) {
                    findedId = id;
                    finded = true;
                }
                if (name != null && name.toUpperCase().contains(query)) {
                    findedId = id;
                    finded = true;
                }
                if (id != null && id.toUpperCase().contains(query)) {
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

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        LinkedList<VocValue> ret = new LinkedList<>();
        boolean finded = StringUtil.isNullOrEmpty(aId);
        LinkedList<VocValue> reverted = new LinkedList<>();
        for (VocValue value : listAll(aAdditional, aContext)) {
            reverted.add(0, value);
        }
        for (VocValue value : reverted) {
            if (CAN_TRACE) LOG.info("valueProperty = " + value);
            if (!finded && value.getId().equals(aId)) {
                finded = true;
            }
            if (finded) {
                ret.add(0, value);
                if (ret.size() > aCount) break;
            }
        }
        return ret;
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        LinkedList<VocValue> ret = new LinkedList<>();
        boolean finded = StringUtil.isNullOrEmpty(aId);
        for (VocValue value : listAll(aAdditional, aContext)) {
            if (!finded && value.getId().equals(aId)) {
                finded = true;
            }
            if (finded) {
                ret.add(value);
                if (ret.size() > aCount) break;
            }
        }
        return ret;
    }

    private Collection<VocValue> listAll(VocAdditional aAdditional, VocContext aContext) {
        AllValueContext ctx = new AllValueContext(
                aAdditional, aContext.getEntityManager(), aContext.getSessionContext());
        return theAllValue.listAll(ctx);
    }

    public void destroy() {

    }


    private final IAllValue theAllValue;
}
