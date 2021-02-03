package ru.ecom.web.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author esinev 18.08.2006 1:54:06
 */
public class SimpleVocAutocompleteServlet extends AbstractAutocompleteServlet {

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }

    private String getVocName(HttpServletRequest aRequest) {
        return getNextDir(aRequest);
    }


    public void printFindByQuery(HttpServletRequest aRequest, String aQuery, int aCount, PrintWriter aOut) throws Exception {
        printResult(EntityInjection.find(aRequest).getVocService().findVocValueByQuery
                (getVocName(aRequest), aQuery, aCount, VocAdditionalUtil.create(aRequest)), aOut, "json".equalsIgnoreCase(aRequest.getParameter("type")));
    }

    public void printNext(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception {
        Collection<VocValue> vocs = EntityInjection.find(aRequest).getVocService().findVocValueNext
                (getVocName(aRequest), aId, aCount, VocAdditionalUtil.create(aRequest));

        boolean isJson = "json".equalsIgnoreCase(aRequest.getParameter("type"));
        if (!StringUtil.isNullOrEmpty(aId)) {
            String lastId = null;
            if (vocs != null && !vocs.isEmpty()) {
                for (VocValue vocValue : vocs) {
                    lastId = vocValue.getId();
                }
            }
            if (vocs != null && vocs.size() < aCount && !StringUtil.isNullOrEmpty(lastId)) {
                Collection<VocValue> addVocs = EntityInjection.find(aRequest).getVocService().findVocValuePrevious
                        (getVocName(aRequest), lastId, aCount - vocs.size(), VocAdditionalUtil.create(aRequest));

                HashMap<String, VocValue> hash = new HashMap<>();
                for (VocValue vocValue : addVocs) {
                    hash.put(vocValue.getId(), vocValue);
                }


                LinkedList<VocValue> ret = new LinkedList<>(addVocs);
                for (VocValue vocValue : vocs) {
                    String id = vocValue.getId();
                    if (id != null && hash.get(id) == null) {
                        hash.put(id, vocValue);
                        ret.add(vocValue);
                    }
                }
                printResult(ret, aOut, isJson);
            } else {
                printResult(vocs, aOut, isJson);
            }
        } else {
            printResult(vocs, aOut, isJson);
        }
    }

    public void printPrevious(HttpServletRequest aRequest, String aId, int aCount, PrintWriter aOut) throws Exception {

        Collection<VocValue> vocs = EntityInjection.find(aRequest).getVocService().findVocValuePrevious
                (getVocName(aRequest), aId, aCount, VocAdditionalUtil.create(aRequest));
        boolean isJson = "json".equalsIgnoreCase(aRequest.getParameter("type"));
        if (vocs.size() < aCount && !StringUtil.isNullOrEmpty(aId)) {
            VocValue lastVoc = null;
            for (VocValue vocValue : vocs) {
                lastVoc = vocValue;
            }
            if (lastVoc != null) {
                Collection<VocValue> addVocs = EntityInjection.find(aRequest).getVocService().findVocValueNext
                        (getVocName(aRequest), aId, aCount - vocs.size(), VocAdditionalUtil.create(aRequest));
                LinkedList<VocValue> ret = new LinkedList<>();
                ret.addAll(vocs);
                boolean firstPassed = false;
                for (VocValue value : addVocs) {
                    if (firstPassed) {
                        ret.add(value);
                    } else {
                        firstPassed = true;
                    }
                }
                printResult(ret, aOut, isJson);
            } else {
                printResult(vocs, aOut, isJson);
            }
        } else {
            printResult(vocs, aOut, isJson);
        }
    }


    private void printResult(Collection aCol, PrintWriter aOut, boolean isJson) {
        int COUNT = 10;
        int i = 0;
        if (isJson) {
            JSONArray arr = new JSONArray();

            for (Object o : aCol) {
                VocValue value = (VocValue) o;
                JSONObject v = new JSONObject();
                v.put("id", value.getId());
                v.put("text", value.getName());
                arr.put(v);
                if (i++ > COUNT) break;
            }
            aOut.print("\"results\":" + arr);
        } else {
            for (Object o : aCol) {
                VocValue value = (VocValue) o;
                p(aOut, value.getId(), value.getName());
                if (i++ > COUNT) break;
            }
        }
    }
}