package ru.ecom.address.ejb.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.domain.address.AddressType;
import ru.ecom.address.ejb.domain.kladr.Kladr;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.services.sync.ISync;
import ru.ecom.expomc.ejb.services.sync.SyncContext;

/**
 * Синхронизация типов адреса из кладр
 */

public class AddressSync implements ISync {

    private final static Logger LOG = Logger.getLogger(AddressSync.class);
    private final static boolean CAN_TRACE = LOG.isDebugEnabled();



    public void sync(SyncContext aContext) throws Exception {

    	//String clause = " where kladrCode like '30%' and time = "+aContext.getImportTime().getId();
        String clause = " where time = "+aContext.getImportTime().getId();
        String queryString = " from Kladr" + clause;
        String countQueryString = "select count(*) from Kladr " + clause;

        theEntityManager = aContext.getEntityManager();
//        UserTransaction tx = aContext.getTransactionManager();
//        tx.begin();
        long results = (Long) theEntityManager.createQuery(countQueryString).getSingleResult();

        IMonitor monitor = aContext.getMonitorService().startMonitor(aContext.getMonitorId(), "Синхронизация ", results);

        int i = 0;
        Iterator<Kladr> iterator = QueryIteratorUtil.iterate(Kladr.class, theEntityManager.createQuery(queryString));
        while (iterator.hasNext()) {
            Kladr kladr = iterator.next();
            String kladrCode = kladr.getKladrCode();
            if (i % 10 == 0 && monitor.isCancelled()) break;
            Address address = findAddressByKladr(kladr.getKladrCode());
            if (address == null) {
                address = new Address();
                address.setKladr(kladrCode);
            }
            address.setDomen(getDomainForKladrCode(kladrCode));
            address.setName(kladr.getName());
            address.setPostIndex(kladr.getPostIndex());
            address.setType(findOrCreateType(kladr.getShortName()));
            address.setParent(findParentByKladrCode(kladrCode));

            theEntityManager.persist(address);

            if (++i % 100 == 0) {
                monitor.advice(100);
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(" ");
                sb.append(kladr != null ? kladr.getName() : "");
                sb.append(" ");
                sb.append(kladr != null ? kladr.getKladrCode() : "");
                monitor.setText(sb.toString());
//                tx.commit();
//                tx.begin();
            }
            theEntityManager.flush();
            theEntityManager.clear();
        }

        monitor.finish(aContext.getImportTime().getId() + "");
        theEntityManager = null;
//        if(monitor.isCancelled()) tx.rollback(); else tx.commit();
//        tx.commit() ;
    }

    @SuppressWarnings("unchecked")
	private AddressType findOrCreateType(String aShortName) {
        if (theHash.isEmpty()) {
            List<AddressType> list = theEntityManager.createQuery("from AddressType").getResultList();
            for (AddressType type : list) {
                theHash.put(type.getShortName(), type);
            }
        }
//        AddressType type = theHash.get(aShortName);
//        if (type == null) {
//            List<AddressType> list = theEntityManager.createQuery("from AddressType where shortName = :shortName")
//                    .setHint("org.hibernate.timeout",60)
//                    .setParameter("shortName", aShortName).getResultList();
//            type = list != null && list.size() > 0 ? list.iterator().next() : null;
//            if (type == null) {
//                type = new AddressType();
//                type.setName(aShortName);
//                type.setShortName(aShortName);
//                theEntityManager.persist(type);
//            }
//        }

        return theHash.get(aShortName);
    }

    @SuppressWarnings("unchecked")
	private Address findAddressByKladr(String aKladrCode) {
//        System.out.println("aKladrCode = " + aKladrCode);
        List<Address> list = theEntityManager.createQuery("from Address where kladr = :kladr")
                .setParameter("kladr", aKladrCode).getResultList();
        return list != null && list.size() > 0 ? list.iterator().next() : null;
    }


    public static int getDomainForKladrCode(String aKladrCode) {
        if (CAN_TRACE) LOG.debug("  getDomainForKladrCode: aKladrCode = " + aKladrCode);
        // 01 234 567 890 1234
        // СС РРР ГГГ ППП УУУУ
        int ret;
        if (isNonZero(aKladrCode, 11, 15)) {
            ret = 6;
        } else if (isNonZero(aKladrCode, 8, 11)) {
            ret = 5;
        } else if (isNonZero(aKladrCode, 5, 8)) {
            ret = 4;
        } else if (isNonZero(aKladrCode, 2, 5)) {
            ret = 3;
        } else if (isNonZero(aKladrCode, 0, 2)) {
            ret = 2;
        } else {
            ret = 1;
        }
        if (CAN_TRACE) LOG.debug("  getDomainForKladrCode: ret = " + ret);
        return ret;
    }

    private static boolean isNonZero(String aKladrCode, int aFrom, int aTo) {
        boolean ret;
        if (aKladrCode != null && aKladrCode.length() > aTo) {
            String code = aKladrCode.substring(aFrom, aTo);
            int len = code.length();
            ret = false;
            for (int i = 0; i < len; i++) {
                if (code.charAt(i) != '0') {
                    ret = true;
                    break;
                }
            }
        } else {
            ret = false;
        }
        return ret;
    }


    private Address findParentByKladrCode(String aKladrCode)  {
        if (CAN_TRACE) LOG.debug("  findParentByKladrCode: aKladrCode = " + aKladrCode);
        String parentCode = getParentKladrCode(aKladrCode);
        if (parentCode != null) {
            Collection<Address> parents = findByKladrCode(parentCode + "00");
            if (parents.isEmpty()) parents = findByKladrCode(parentCode + "99");
            if (parents.isEmpty()) parents = findByKladrCode(parentCode + "51");
            if (parents.isEmpty()) parents = findByKladrCode(parentCode + "01");
            if (parents.isEmpty()) parents = findByKladrCode(parentCode + "02");

            if (parents.isEmpty()) {
                return findParentByKladrCode(parentCode);
            } else {
                Address parent = parents.iterator().next();
                if (CAN_TRACE) LOG.debug("  findParentByKladrCode: parent.getKladrCode() = " + parent.getKladr()+" "+parent.getName());
                return parent ;
            }
        } else {
            return findOrCreateRussia() ; //theEntityManager.find(Address.class,1L) ;
        }
    }

    @SuppressWarnings("unchecked")
	private Address findOrCreateRussia() {
        List<Address> list = theEntityManager.createQuery("from Address where domen=1").setMaxResults(1).getResultList();
        Address ret ;
        if(list==null || list.isEmpty()) {
            ret = new Address();
            ret.setDomen(1);
            ret.setName("Россия");
            theEntityManager.persist(ret);
        } else {
            ret = list.iterator().next();
        }
        return ret ;
    }

    @SuppressWarnings("unchecked")
	private Collection<Address> findByKladrCode(String aCode)  {
        if (CAN_TRACE) LOG.debug("    findByKladrCode: aCode = " + aCode+" ...");
        Collection<Address> parents = theEntityManager.createQuery("from Address where kladr = :code")
                .setParameter("code",aCode).getResultList();  //theAddressHome.findByKladrCode(aCode) ;
        if (CAN_TRACE) LOG.debug("    findByKladrCode: parents = " + parents.size());
        return parents ;

    }

    public static String getParentKladrCode(String aKladrCode) {
        if (CAN_TRACE) LOG.debug("    getParentKladrCode: aKladrCode = " + aKladrCode);
        int domain = (int) getDomainForKladrCode(aKladrCode);
        String ret;
        switch (domain) {
            case 6:
                ret = extractParent(aKladrCode, 11);
                break;// улица -> пункт
            case 5:
                ret = extractParent(aKladrCode, 8);
                break;// пункт -> город
            case 4:
                ret = extractParent(aKladrCode, 5);
                break;// город -> райно
            case 3:
                ret = extractParent(aKladrCode, 2);
                break;// район -> субъект
            default:
                ret = null;
        }
        if (CAN_TRACE) LOG.debug("    getParentKladrCode: ret = " + ret);
        return ret;
    }

    private static String extractParent(String aCode, int aTo) {
        if (aTo < aCode.length()) {
            StringBuilder sb = new StringBuilder(aCode.substring(0, aTo));
            while (sb.length() < 11) {
                sb.append('0');
            }
            return sb.toString();
        } else {
            return aCode;
        }
    }


    private EntityManager theEntityManager;
    private HashMap<String, AddressType> theHash = new HashMap<String, AddressType>();
}
