package ru.ecom.mis.ejb.service.synclpufond;


import java.sql.Date;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import ru.ecom.address.ejb.service.ILocalAddressService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.util.format.DateFormat;

/**
 * @author  azviagin
 */
@Stateless
@Remote(ISyncLpuFondService.class)
@Local(ISyncLpuFondService.class)
//@TransactionManagement(TransactionManagementType.BEAN)
public class SyncLpuFondServiceBean implements ISyncLpuFondService {

    private final static Logger LOG = Logger.getLogger(SyncLpuFondServiceBean.class) ;
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;


    public Long findPatientId(String aLastname, String aFirstname, String aMiddlename, Date aBirthday) {
        Patient patient = findPatient(aLastname, aFirstname, aMiddlename, aBirthday) ;
        return patient!=null ? patient.getId() : null ;
    }

    public Long findMedPolicyId(String aSeries, String aNumber) {
        MedPolicyOmc pol = findMedPolicy(aSeries, aNumber) ;
        return pol!=null ? pol.getId() : null ;
    }

    @SuppressWarnings("unchecked")
	private MedPolicyOmc findMedPolicy(String aSeries, String aNumber) {
        if (CAN_DEBUG) LOG.debug("Finding policy " + aSeries+" "+aNumber+"...");
        List<MedPolicyOmc> list = theManager.createQuery(
                "from MedPolicyOmc where series = :series and polNumber = :number"
        ).setParameter("series", aSeries)
                .setParameter("number", aNumber)
                .getResultList();
        if (CAN_DEBUG) LOG.debug("DONE");
        return list != null && list.size() == 1 ? list.iterator().next() : null;
    }


    @SuppressWarnings("unchecked")
	private Patient findPatient(String aLastname, String aFirstname, String aMiddlename, Date aBirthday) {
        if (CAN_DEBUG) LOG.debug("Finding patient " + aLastname+" "+aFirstname+" " +aMiddlename+" "+aBirthday+"...");
        List<Patient> list = theManager.createQuery(
                "from Patient where lastname = :lastname "
                        + " and firstname = :firstname"
                        + " and middlename = :middlename"
                        + " and birthday = :birthday"
        ).setParameter("lastname", aLastname)
                .setParameter("firstname", aFirstname)
                .setParameter("middlename", aMiddlename)
                .setParameter("birthday", aBirthday)
                .getResultList();
        if (CAN_DEBUG) LOG.debug("DONE");
        return list != null && list.size() == 1 ? list.iterator().next() : null;
    }

    



    


    @SuppressWarnings("unchecked")
	private <T> T findEntity(Class<T> aEntity, String aCodeField, String aValue) {
        String entityName = theEntityHelper.getEntityName(aEntity);
        List<T> list = theManager.createQuery(
                "from "+entityName+" where "+aCodeField+" = :code"
        ).setParameter("code",aValue).getResultList();
        return list!=null && list.size()==1 ? list.iterator().next() : null ;
    }




    @SuppressWarnings("unchecked")
	public void sync(long aMinitorId, long aTimeId) {
//        testAddress();
//        if(true) return ;
        IMonitor monitor = null;

        try {
//            theUserTransaction.begin();

            List<RegistryEntry> registry = theManager.createQuery(
                    "from RegistryEntry where time = :time"
            ).setParameter("time", aTimeId).getResultList();
            monitor = theMonitorService.startMonitor(aMinitorId, "Синхронизация услуг", registry.size());

            int i = 0;
            for (RegistryEntry entry : registry) {
                if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
                i++;
                MedPolicyOmc policy = findMedPolicy(entry.getSPolis(), entry.getNPolis());
                Patient patient;
                if (policy == null) {
                    patient = findPatient(entry.getLastname(), entry.getFirstname(), entry.getMiddlename(), entry.getBirthDate());
                } else {
                    patient = policy != null ? (Patient)policy.getPatient() : null;
                }
                
                if(i%10==0) monitor.setText(new StringBuilder().append("Импортируется: ").append(entry.getLastname()).append(" ").append(DateFormat.formatToDate(entry.getDischargeDate())).append("...").toString());
                monitor.advice(1);
                
//                    theUserTransaction.commit();
//                    theUserTransaction.begin();
                theManager.flush();
                theManager.clear();
                if (i % 300 == 0) {
//                    theUserTransaction.commit();
//                    theUserTransaction.begin();
                    monitor.setText("Импортировано " + i);
                }
            }
            monitor.finish("");
//            theUserTransaction.commit();
        } catch (Exception e) {
            if(monitor!=null) monitor.setText(e+"");
            throw new IllegalStateException(e) ;
        }
    }


    @EJB
    private ILocalMonitorService theMonitorService;
    @PersistenceContext
    private EntityManager theManager;
    private EntityHelper theEntityHelper = EntityHelper.getInstance();
//    @Resource UserTransaction theUserTransaction;
    @EJB ILocalAddressService theAddressService ;
//    @JndiInject(jndiName="ejb/stac/AddressService") AddressServiceHome theAddressServiceHome;
}
