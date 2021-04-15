package ru.ecom.expert2.service;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.ecom.api.webclient.IWebClientService;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.dto.Hosp;
import ru.ecom.expert2.dto.HospLeave;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@Stateless
@Local(IExpert2AlkonaService.class)
@Remote(IExpert2AlkonaService.class)
public class Expert2AlkonaServiceBean implements IExpert2AlkonaService {
    private static final Logger LOG = Logger.getLogger(Expert2AlkonaServiceBean.class);
    private static final String OMC_SERVICE_STREAM = "OBLIGATORYINSURANCE";
    private static final String ALKONA_URL = "http://127.0.0.1:8082/medcase";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private @EJB
    IWebClientService clientService;
    private @PersistenceContext
    EntityManager manager;

    @Override
    public void exportHospLeaveToAlkona(Long entryListId) {
        LOG.info("start send to alkona");
        List<E2Entry> entries = manager.createNamedQuery("E2Entry.getAllByListEntryIdAndServiceStream")
                .setParameter("listEntryId", entryListId).setParameter("serviceStream", OMC_SERVICE_STREAM).getResultList();
        int i = 0;
        LOG.info("found " + entries.size() + " entries");
        for (E2Entry entry : entries) {
            try {
                i++;
                if (i % 100 == 0) LOG.info("Отправлено в алькону " + i + " записей");
                LOG.info("id:" + entry.getId() + " Response: " + clientService.makePOSTRequest(toString(mapEntryHospLeave(entry)), ALKONA_URL, "postDischarge", Collections.emptyMap()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void exportHospToAlkona(Long entryListId, Boolean isEmergency) {
        LOG.info("start send to alkona");
        List<E2Entry> entries = manager.createNamedQuery("E2Entry.getAllByListEntryIdAndServiceStream")
                .setParameter("listEntryId", entryListId).setParameter("serviceStream", OMC_SERVICE_STREAM).getResultList();
        int i = 0;
        LOG.info("found " + entries.size() + " entries");
        for (E2Entry entry : entries) {
            if (isEmergency == null || isEmergency.equals(entry.getIsEmergency())) {
                try {
                    i++;
                    if (i % 100 == 0) LOG.info("Отправлено в алькону " + i + " записей");
                    LOG.info("id:" + entry.getId() + " Response: " + clientService.makePOSTRequest(toString(mapEntryHosp(entry)), ALKONA_URL, "postHosp", Collections.emptyMap()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //выписка
    private HospLeave mapEntryHospLeave(E2Entry entry) {
        HospLeave leave = new HospLeave();
        leave.setExternalId(entry.getExternalId() + "");
        leave.setDirectNumber(entry.getTicket263Number());
        leave.setDirectDate(toLocalDate(entry.getDirectDate()));
        leave.setServiceKind(Boolean.TRUE.equals(entry.getIsEmergency()) ? "2" : "1");
        leave.setLpuCode(entry.getLpuCode());
        leave.setHospStartDate(toLocalDate(entry.getStartDate()));
        leave.setHospFinishDate(toLocalDate(entry.getFinishDate()));
        leave.setMedHelpProfile(entry.getMedHelpProfile().getCode());
        leave.setDepartmentName(entry.getDepartmentName());
        leave.setStatisticStub(entry.getHistoryNumber());
        leave.setPatientLastname(entry.getLastname());
        leave.setPatientFirstname(entry.getFirstname());
        leave.setPatientMiddlename(entry.getMiddlename());
        leave.setMedTerms(mapMedTerms(entry.getMedHelpUsl().getCode()));

        return leave;
    }


    private Hosp mapEntryHosp(E2Entry entry) {
        Hosp hosp = new Hosp();
        hosp.setExternalId(entry.getExternalId() + "");
        hosp.setIsEmergency(Boolean.TRUE.equals(entry.getIsEmergency()) ? 1 : 0);
        hosp.setDirectNumber(entry.getTicket263Number());
        hosp.setDirectDate(toLocalDate(entry.getDirectDate()));
        hosp.setServiceKind(Boolean.TRUE.equals(entry.getIsEmergency()) ? 2 : 1);
        hosp.setDirectLpu(entry.getDirectLpu());
//        hosp.setDirectLpuDepartment(entry.getDirectLpu());
        hosp.setLpuCode(entry.getLpuCode());
        hosp.setDepartmentName(entry.getDepartmentName());
        hosp.setHospStartDate(toLocalDate(entry.getStartDate()));
        hosp.setHospStartTime(entry.getStartTime().toLocalTime());
        hosp.setMedPolicyType(Integer.parseInt(entry.getMedPolicyType()));
        hosp.setMedPolicySeries(entry.getMedPolicySeries());
        hosp.setMedPolicyNumber(entry.getMedPolicyNumber());
        hosp.setInsuranceCompanyCode(entry.getInsuranceCompanyCode());
        hosp.setRegionOkato("12700");
        hosp.setPatientLastname(entry.getLastname());
        hosp.setPatientFirstname(entry.getFirstname());
        hosp.setPatientMiddlename(entry.getMiddlename());
        hosp.setSex("1".equals(entry.getSex()) ? 1 : 0); //палка - мальчик, дырка - девочка!
        hosp.setBirthDate(toLocalDate(entry.getBirthDate()));
        hosp.setMedHelpProfile(entry.getMedHelpProfile().getCode());
        hosp.setHospitalBranch(entry.getDepartmentName());
        hosp.setStatisticStub(entry.getHistoryNumber());
        hosp.setMedTerms(mapMedTerms(entry.getMedHelpUsl().getCode()));
        return hosp;
    }

    private String mapMedTerms(String medHelpUsl) {
        switch (medHelpUsl) {
            case "3":
                return "ДП";
            case "2":
                return "ДС";
            case "1":
                return "КР";
            default:
                throw new IllegalStateException("HZ");
        }
    }

    private String toLocalDate(Date date) {
        return date == null ? null : format.format(date);

    }

    public String toString(Object o) {
        Gson gson = new Gson();
        String str = gson.toJson(o);
        return str;
    }

}
