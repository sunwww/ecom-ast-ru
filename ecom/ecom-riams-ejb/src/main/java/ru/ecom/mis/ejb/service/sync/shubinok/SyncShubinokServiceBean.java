package ru.ecom.mis.ejb.service.sync.shubinok;

import java.util.Iterator;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ru.ecom.address.ejb.service.ILocalAddressService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.external.PatientInfoShubinok;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocOrg;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.patient.voc.VocSocialStatus;
import ru.ecom.mis.ejb.service.synclpufond.ISyncLpuFondService;

/**
 * Синхронизация с базой П.Г.Шибинка
 */

@Stateless
@Local(ISyncShubinokService.class)
public class SyncShubinokServiceBean implements ISyncShubinokService {

    private final static Logger LOG = Logger.getLogger(SyncShubinokServiceBean.class) ;
//    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;

    public void sync(long aMonitorId, long aTimeId) {
        IMonitor monitor = null;
        try {
            monitor = theMonitorService.startMonitor(aMonitorId, "Синхронизация персон", getCount(aTimeId));
            Query query = theManager.createQuery("from PatientInfoShubinok where time = :time")
                    .setParameter("time", aTimeId) ;
            Iterator<PatientInfoShubinok> pats = QueryIteratorUtil.iterate(PatientInfoShubinok.class, query);
            int i = 0;
            while (pats.hasNext()) {
                PatientInfoShubinok pat = pats.next();
                if (monitor.isCancelled()) {
                    throw new IllegalMonitorStateException("Прервано пользователем");
                }
                i++;
                Long medPolicyId = theSyncService.findMedPolicyId(pat.getPolicySeries(), pat.getPolicyNumber()) ;
                MedPolicyOmc medPolicy = medPolicyId!=null ? theManager.find(MedPolicyOmc.class, medPolicyId) : null ;

                Patient patient ;
                if(medPolicy==null) {
                    Long patientId = theSyncService.findPatientId(pat.getLastnameGood()
                            , pat.getFirstname(), pat.getMiddlename(), pat.getBirthdate()) ;
                    patient = patientId!=null ? theManager.find(Patient.class, patientId) : null ;
                } else {
                    patient = (Patient) medPolicy.getPatient() ;
                }

                if(i%100==0) {
                    monitor.advice(100);
                    monitor.setText(pat.getLastnameGood());
                }
                syncPatient(pat, patient, medPolicy) ;
            }
        } catch (IllegalMonitorStateException e) {
            throw e ;
        } catch (Exception e) {
            monitor.error("Ошибка синхронизации",e);
            LOG.error("Ошибка синхронизации",e);
        }
    }

    private void syncPatient(PatientInfoShubinok aEntry, Patient aPatient, MedPolicyOmc aMedPolicy) {
        if(aPatient==null) {
            aPatient = new Patient();
        }
        aPatient.setLastname(aEntry.getLastnameGood());
        aPatient.setFirstname(aEntry.getFirstname());
        aPatient.setMiddlename(aEntry.getMiddlename());
        aPatient.setBirthday(aEntry.getBirthdate());
        aPatient.setSnils(aEntry.getSnils());
        aPatient.setAddress(theAddressService.findAddressByKladr(aEntry.getKladr()));
        aPatient.setHouseNumber(aEntry.getHouseNumber());
        aPatient.setFlatNumber(aEntry.getFlatNumber());
        aPatient.setHouseBuilding(aEntry.getBuildingNumber());
        aPatient.setSex(findEntity(VocSex.class,"omcCode",aEntry.getSex()));
        aPatient.setSocialStatus(findEntity(VocSocialStatus.class,"omcCode",aEntry.getInsuranceType()));

        VocOrg org = findEntity(VocOrg.class, "fondNumber", aEntry.getOrgCodeNew()) ;
        aPatient.setWorks(org);

        if(aMedPolicy==null) {
            aMedPolicy = new MedPolicyOmc() ;
            aMedPolicy.setPatient(aPatient);
        }
        aMedPolicy.setSeries(aEntry.getPolicySeries());
        aMedPolicy.setPolNumber(aEntry.getPolicyNumber());
        aMedPolicy.setOrg(org);
        aMedPolicy.setCompany(findEntity(RegInsuranceCompany.class, "omcCode", aEntry.getInsuranceCompanyCode()));

        theManager.persist(aPatient);
        theManager.persist(aMedPolicy);
        theManager.flush();
        theManager.clear() ;
    }

    @SuppressWarnings("unchecked")
	private <T> T findEntity(Class<T> aEntity, String aCodeField, String aValue) {
        String entityName = theEntityHelper.getEntityName(aEntity);
        List<T> list = theManager.createQuery(
                "from "+entityName+" where "+aCodeField+" = :code"
        ).setParameter("code",aValue).getResultList();
        return list!=null && list.size()==1 ? list.iterator().next() : null ;
    }

    private Long getCount(long aTimeId) {
        return (Long) theManager.createQuery("select count(*) from PatientInfoShubinok where time = :time")
                .setParameter("time", aTimeId).getSingleResult() ;
    }


    private @EJB ILocalMonitorService theMonitorService;
    private @PersistenceContext EntityManager theManager;
    private @EJB ISyncLpuFondService theSyncService ;
    private @EJB ILocalAddressService theAddressService ;
    private EntityHelper theEntityHelper = EntityHelper.getInstance();

}
