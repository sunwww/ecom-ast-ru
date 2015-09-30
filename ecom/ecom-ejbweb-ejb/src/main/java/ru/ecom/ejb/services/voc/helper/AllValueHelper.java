package ru.ecom.ejb.services.voc.helper;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.IVocService;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev 18.08.2006 1:09:44
 */
public class AllValueHelper implements IVocContextService, IVocServiceManagement {

    private final static Log LOG = LogFactory.getLog(AllValueHelper.class);
    private final static boolean CAN_TRACE = LOG.isTraceEnabled();


    public AllValueHelper(IAllValue aAllValue) {
        theAllValue = aAllValue;
    }

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
    	AllValueContext ctx = new AllValueContext(
        		aAdditional, aContext.getEntityManager(), aContext.getSessionContext());
    	return theAllValue.getNameById(aId, aVocName, aAdditional, ctx) ;
        /*String ret = null;
        if (aId != null) {
            for (VocValue value : listAll(aAdditional, aContext)) {
                if (aId.equals(value.getId())) {
                    ret = value.getName();
                }
            }
        }
        return ret;*/
    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        String query = aQuery.toUpperCase();
        String findedId = null;
        if (!StringUtil.isNullOrEmpty(aQuery)) {
            for (VocValue value : listAll(aAdditional, aContext)) {
                String name = value.getName();
                String id = value.getId();
                if (name != null) {
                    if (name.toUpperCase().startsWith(query)) {
                        findedId = id;
                        break;
                    }
                }
                if (findedId == null) {
                    if (id != null) {
                        if (id.toUpperCase().startsWith(query)) {
                            findedId = id;
                            break;
                        }
                    }
                }
                if (name != null && name.toUpperCase().indexOf(query) > -1) {
                    findedId = id;
                    break;
                }
                if (id != null && id.toUpperCase().indexOf(query) > -1) {
                    findedId = id;
                    break;
                }
            }
        }
        return findVocValueNext(aVocName, findedId, aCount, aAdditional, aContext);
    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        LinkedList<VocValue> ret = new LinkedList<VocValue>();
        boolean finded = StringUtil.isNullOrEmpty(aId);
        LinkedList<VocValue> reverted = new LinkedList<VocValue>();
        for (VocValue value : listAll(aAdditional, aContext)) {
            reverted.add(0, value);
        }
        for (VocValue value : reverted) {
            if (CAN_TRACE) LOG.trace("valueProperty = " + value);
            if (!finded && value.getId().equals(aId)) {
                finded = true;
            }
            if (finded) {
                ret.add(0,value);
                if (ret.size() > aCount) break;
            }
        }
        return ret;
    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        LinkedList<VocValue> ret = new LinkedList<VocValue>();
        boolean finded = StringUtil.isNullOrEmpty(aId);
        for (VocValue value : listAll(aAdditional, aContext)) {
            if (CAN_TRACE) LOG.trace("valueProperty = " + value);
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

    private Collection<VocValue> listAll(VocAdditional aAdditional, VocContext aContext ) {
        AllValueContext ctx = new AllValueContext(
        		aAdditional, aContext.getEntityManager(), aContext.getSessionContext());
        return theAllValue.listAll(ctx);
    }

    public void destroy() {

    }

    

    private final IAllValue theAllValue;
}
