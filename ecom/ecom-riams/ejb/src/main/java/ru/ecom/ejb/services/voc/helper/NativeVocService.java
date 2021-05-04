package ru.ecom.ejb.services.voc.helper;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.voc.IVocContextService;
import ru.ecom.ejb.services.voc.IVocServiceManagement;
import ru.ecom.ejb.services.voc.VocContext;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.util.voc.VocValue;

import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class NativeVocService implements IVocContextService, IVocServiceManagement {

    private static final Logger LOG = Logger.getLogger(EntityVocService.class);

    private final String queriedFields;
    private final String select;

    public NativeVocService(String aFrom, String aNames, String aNameId, String aJoin, String aQueryAppend, String aQueried, String aParent, String aOrder
            , String aFieldsSplitCount, String aParentSplitCount, String aGroupBy
    ) {
        // select = aNames;
        splitQueriedCount = (aFieldsSplitCount != null && !aFieldsSplitCount.equals("")) ? Long.valueOf(aFieldsSplitCount) : null;
        splitParentCount = aParentSplitCount != null && !aParentSplitCount.equals("") ? Long.valueOf(aParentSplitCount) : null;
        /** Свойства с названиями */
        String[] names = getAsArray(aNames);
        StringBuilder sb = new StringBuilder();
        if (names.length > 0) {
            for (String name : names) {
                sb.append(name).append(",");
            }
            select = sb.substring(0, sb.length() - 1);
        } else {
            select = aNameId;
        }
        order = aOrder != null && !"".equals(aOrder) ? aOrder : null;
        nameId = aNameId;
        join = aJoin != null ? aJoin : "";
        groupBy = aGroupBy != null && aGroupBy.trim().equals("") ? null : aGroupBy;
        queryAppend = aQueryAppend;
        from = aFrom;
        queriedFields = aQueried;
        parentField = StringUtil.isNullOrEmpty(aParent) ? null : aParent;
    }

    public String getNameById(String aId, String aVocName, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if (StringUtil.isNullOrEmpty(aId)) return "";
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(nameId).append(", ").append(select).append(" from ").append(from)
                .append(" ").append(join).append(" where ")

                .append(nameId).append("='").append(aId).append("'");
        try {
            List<Object[]> obj = aContext.getEntityManager().createNativeQuery(sql.toString()).getResultList();
            if (!obj.isEmpty()) {

                if (obj.size() > 1 && parentField != null && aAdditional != null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
                    sql = new StringBuilder();
                    sql.append("select ").append(nameId).append(", ").append(select).append(" from ").append(from)
                            .append(" ").append(join).append(" where ")

                            .append(nameId).append("='").append(aId).append("'");
                    sql.append(" and ");
                    sql.append(getParent(parentField, aAdditional.getParentId(), splitParentCount));
                    List<Object[]> obj1 = aContext.getEntityManager().createNativeQuery(sql.toString()).getResultList();
                    if (!obj1.isEmpty()) {
                        return getNameFromEntity(obj1.get(0));
                    }
                }
                return getNameFromEntity(obj.get(0));
            }
        } catch (Exception e) {
            LOG.error("Ошибка выполнение voc-запроса(" + aVocName + "): " + sql, e);
        }
        return "";

    }

    public Collection<VocValue> findVocValueByQuery(String aVocName, String aQuery, int aCount, VocAdditional aAdditional, VocContext aContext) throws VocServiceException {
        if (StringUtil.isNullOrEmpty(aQuery)) {
            return findVocValueNext(aVocName, null, aCount, aAdditional, aContext);
        } else {
            // поиск по идентификатору
            StringBuilder sql = new StringBuilder();
            sql.append("select ").append(nameId).append(", ").append(select).append(" from ").append(from)
                    .append(" ").append(join);
            boolean appendIs = false;
            if (!StringUtil.isNullOrEmpty(queryAppend)) {
                sql.append(" where (").append(queryAppend).append(")");
                appendIs = true;
            }
            if (!StringUtil.isNullOrEmpty(queriedFields)) {
                if (appendIs) sql.append(" and ");
                else sql.append(" where ");
                sql.append(" (").append(queriedFields).append(")");
                appendIs = true;
            }

            if (parentField != null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
                if (appendIs) sql.append(" and ");
                else sql.append(" where ");
                sql.append(getParent(parentField, aAdditional.getParentId(), splitParentCount));
            }
            if (groupBy != null) sql.append(" group by ").append(groupBy);
            aQuery = aQuery.toUpperCase();
            Query query = aContext.getEntityManager().createNativeQuery(sql.toString());
            //LOG.debug(aQuery) ;
            if (splitQueriedCount != null && splitQueriedCount > 0) {
                //LOG.debug("splitQuery="+splitQueriedCount);
                String[] split = aQuery.split(" ");
                int cnt = splitQueriedCount.intValue();
                //LOG.debug("cnt="+cnt);
                for (int i = 0; i < cnt; i++) {
                    String querI = "quer" + (i + 1);
                    //LOG.debug("quer="+querI) ;
                    if (sql.toString().contains(querI)) {
                        if (i < split.length) {
                            //LOG.debug(split[i]) ;
                            query.setParameter(querI, split[i] + "%");
                        } else {
                            query.setParameter(querI, "%");
                        }
                    }
                }
            }
            if (sql.toString().contains("query")) {
                query.setParameter("query", "%" + aQuery + "%");
            }
            if (sql.toString().contains("querId")) {
                query.setParameter("querId", aQuery + "%");
            }
            if (sql.toString().contains("querInd")) {
                query.setParameter("querInd", aQuery);
            }

            List<Object[]> list = query.setMaxResults(aCount)
                    .getResultList();
            return createValues(list, 0);
        }

    }

    public Collection<VocValue> findVocValuePrevious(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        // уще не установлен родитель
        if (parentField != null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<>();
        }

        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(nameId).append(", ").append(select).append(" from ").append(from)
                .append(" ").append(join);
        boolean appendIs = false;
        if (parentField != null && StringUtil.isNotEmpty(aAdditional.getParentId())) {
            sql.append(" where ");
            sql.append(getParent(parentField, aAdditional.getParentId(), splitParentCount));
            appendIs = true;
        }
        if (StringUtil.isNotEmpty(queryAppend)) {
            if (appendIs) sql.append(" and ");
            else sql.append(" where ");
            sql.append(queryAppend);
            appendIs = true;
        }
        if (StringUtil.isNotEmpty(aId)) {
            if (appendIs) sql.append(" and ");
            else sql.append(" where ");
            sql.append(nameId).append("<='").append(aId).append("' ");
        }
        if (groupBy != null) sql.append(" group by ").append(groupBy);
        sql.append(" order by ").append(nameId).append(" desc");
        Query query = aContext.getEntityManager().createNativeQuery(sql.toString());
        List<Object[]> list = query.setMaxResults(aCount).getResultList();
        return createValues(list, 1);

    }

    public Collection<VocValue> findVocValueNext(String aVocName, String aId, int aCount, VocAdditional aAdditional, VocContext aContext) {
        if (parentField != null && StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            return new LinkedList<>();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(nameId).append(", ").append(select).append(" from ").append(from)
                .append(" ").append(join);
        boolean appendIs = false;
        if (parentField != null && !StringUtil.isNullOrEmpty(aAdditional.getParentId())) {
            sql.append(" where ");
            String param = getParent(parentField, aAdditional.getParentId(), splitParentCount);
            sql.append(param);
            appendIs = true;
        }
        if (!StringUtil.isNullOrEmpty(queryAppend)) {
            if (appendIs) sql.append(" and ");
            else sql.append(" where ");
            sql.append(queryAppend);
            appendIs = true;

        }
        if (!StringUtil.isNullOrEmpty(aId)) {
            if (appendIs) sql.append(" and ");
            else sql.append(" where ");
            sql.append(nameId).append(">='").append(aId).append("' ");
        }
        if (groupBy != null) sql.append(" group by ").append(groupBy);

        sql.append(" order by ").append(order != null ? order : nameId);
        Query query = aContext.getEntityManager().createNativeQuery(sql.toString());

        List<Object[]> list = null;
        try {
            list = query.setMaxResults(aCount).getResultList();
        } catch (Exception e) {
            LOG.error("Error execute sql: " + sql, e);
        }
        return createValues(list, 0);

    }

    private String getParent(String aParentField, String aReplace, Long aSplitCount) {

        String find;
        String result = aParentField;
        if (aSplitCount != null && aSplitCount > 0) {
            String[] val = aReplace.split("#");
            for (int i = 0; i < aSplitCount.intValue(); i++) {
                find = ":par" + (i + 1);

                String pr;
                if (val.length > i) {
                    pr = "'" + val[i] + "'";
                } else {
                    pr = "''";
                }
                result = result.replace(find, pr);
            }

        } else {
            find = ":parent";
            result = result.replace(find, "'" + aReplace + "'");
        }
        return result;
    }


    private Collection<VocValue> createValues(List<Object[]> aList, int aPrevIs) {
        LinkedList<VocValue> values = new LinkedList<>();
        if (aList != null && !aList.isEmpty()) {
            for (Object[] entity : aList) {
                if (aPrevIs > 0) {
                    values.add(0, createVocValue(entity));
                } else {
                    values.add(createVocValue(entity));
                }
            }
        }
        return values;
    }

    private String getNameFromEntity(Object[] aObj) {
        return aObj[1] + " ";
    }

    private VocValue createVocValue(Object[] aObj) {
        try {
            String id = aObj[0].toString();
            String name = getNameFromEntity(aObj);
            return new VocValue(id, name);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания VocValue", e);
        }
    }

    public void destroy() {
    }
    /** Менеджер */
    /**
     * Дополнительные параметры при выборе из справочника
     */
    private final String parentField;
    private final String join;
    private final Long splitQueriedCount;
    private final Long splitParentCount;
    private final String queryAppend;
    private final String order;
    private final String groupBy;
    private final String from;
    private final String nameId;

    private static String[] getAsArray(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr, ";");
        LinkedList<String> list = new LinkedList<>();
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        String[] ret = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}