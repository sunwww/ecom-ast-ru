package ru.ecom.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocValue;

/**
 * @author esinev 18.08.2006 1:54:06
 */
public class SimpleVocAutocompleteServlet extends AbstractAutocompleteServlet {

    private final static Log LOG = LogFactory.getLog(SimpleVocAutocompleteServlet.class);
    private final static boolean CAN_TRACE = LOG.isTraceEnabled();


    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }

    private String getVocName(HttpServletRequest aRequest) {
        return getNextDir(aRequest);
    }


    public void printFindByQuery(HttpServletRequest aRequest, String aQuery, int aCount, PrintWriter aOut) throws Exception {
        printResult(EntityInjection.find(aRequest).getVocService().findVocValueByQuery
                (getVocName(aRequest), aQuery, aCount, VocAdditionalUtil.create(aRequest)), aOut);
    }

    public void printNext(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception {

        if (CAN_TRACE) LOG.trace("printNext(): aId = " + aId);
        if (CAN_TRACE) LOG.trace("  printNext(): aCount = " + aCount);

        Collection<VocValue> vocs = EntityInjection.find(aRequest).getVocService().findVocValueNext
                (getVocName(aRequest), aId, aCount, VocAdditionalUtil.create(aRequest));

        if (CAN_TRACE) LOG.trace("  vocs = " + vocs);
        if (CAN_TRACE) LOG.trace("  vocs.size() = " + vocs.size());

        if (!StringUtil.isNullOrEmpty(aId)) {
            String lastId = null;
            if (vocs != null && !vocs.isEmpty()) {
                for (VocValue vocValue : vocs) {
                    lastId = vocValue.getId();
                }
            }
            if (CAN_TRACE) LOG.trace("  lastId = " + lastId);
            if (vocs.size() < aCount && !StringUtil.isNullOrEmpty(lastId)) {
                Collection<VocValue> addVocs = EntityInjection.find(aRequest).getVocService().findVocValuePrevious
                        (getVocName(aRequest), lastId, aCount - vocs.size(), VocAdditionalUtil.create(aRequest));

                if (CAN_TRACE) LOG.trace("  addVocs = " + addVocs);
                HashMap<String, VocValue> hash = new HashMap<String, VocValue>();
                for (VocValue vocValue : addVocs) {
                    hash.put(vocValue.getId(), vocValue) ;
                }

                if (CAN_TRACE) LOG.trace("  hash = " + hash);

                LinkedList<VocValue> ret = new LinkedList<VocValue>();
                for (VocValue value : addVocs) {
                    ret.add(value);
                }
                if (CAN_TRACE) LOG.trace("  ret = " + ret);
//                if (CAN_TRACE) LOG.trace("  ret = " + ret);
//                if (ret.size() > 0) ret.remove(ret.size() - 1);
//                if (CAN_TRACE) LOG.trace("  after delete ret = " + ret);
                for (VocValue vocValue : vocs) {
                    String id = vocValue.getId() ;
                    if(id!=null && hash.get(id)==null) {
                        hash.put(id, vocValue) ;
                        ret.add(vocValue) ;
                    }
//                    ret.add(vocValue);
                }
                if (CAN_TRACE) LOG.trace("  after add ret = " + ret);
                printResult(ret, aOut);
            } else {
                printResult(vocs, aOut);
            }
        } else {
            printResult(vocs, aOut);
        }
    }

    public void printPrevious(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception {

        Collection<VocValue> vocs = EntityInjection.find(aRequest).getVocService().findVocValuePrevious
                (getVocName(aRequest), aId, aCount, VocAdditionalUtil.create(aRequest));

        if (vocs.size() < aCount && !StringUtil.isNullOrEmpty(aId)) {
            VocValue lastVoc = null;
            for (VocValue vocValue : vocs) {
                lastVoc = vocValue;
            }
            if (lastVoc != null) {
                Collection<VocValue> addVocs = EntityInjection.find(aRequest).getVocService().findVocValueNext
                        (getVocName(aRequest), aId, aCount - vocs.size(), VocAdditionalUtil.create(aRequest));
                LinkedList<VocValue> ret = new LinkedList<VocValue>();
                for (VocValue value : vocs) {
                    ret.add(value);
                }
                boolean firstPassed = false;
                for (VocValue value : addVocs) {
                    if (firstPassed) {
                        ret.add(value);
                    } else {
                        firstPassed = true;
                    }
                }
                printResult(ret, aOut);
            } else {
                printResult(vocs, aOut);
            }
        } else {
            printResult(vocs, aOut);
        }
    }


    private void printResult(Collection aCol, PrintWriter aOut) {
        int COUNT = 10;
        int i = 0;
        for (Object o : aCol) {
            VocValue value = (VocValue) o;
            p(aOut, value.getId(), value.getName());
            if (i++ > COUNT) break;
        }
    }
}
