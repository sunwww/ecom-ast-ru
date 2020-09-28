package ru.ecom.mis.ejb.form.medcase.interceptor;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.poly.VisitMedCaseForm;
import ru.ecom.mis.ejb.service.contract.ContractServiceBean;
import ru.ecom.mis.ejb.service.contract.IContractService;

import javax.persistence.EntityManager;
import java.util.List;

public class VisitSaveInterceptor implements IFormInterceptor {
    private static final Logger LOG = Logger.getLogger(VisitSaveInterceptor.class);
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {

        Visit visit = (Visit) aEntity;
        if (Boolean.TRUE.equals(visit.getServiceStream().getIsPaidConfirmation())) {
            EntityManager manager = aContext.getEntityManager();
            VisitMedCaseForm form = (VisitMedCaseForm) aForm;
            JSONArray servicesList = new JSONObject(form.getMedServices()).getJSONArray("childs");
            List<Object[]> paidServices =EjbInjection.getInstance().getRemoteService(IContractService.class).getPaidMedServicesByPatient(visit.getPatient().getId()
                    ,null,visit.getId(), null, null, null);
            StringBuilder errorList = new StringBuilder();
            for (int i=0;i<servicesList.length();i++) {
                JSONObject child = servicesList.getJSONObject(i);
                try {
                    MedService ms = manager.find(MedService.class, child.getLong("value"));
                    String code = ms.getCode();
                    boolean ispaid= false;
                    for (Object[] o :paidServices) {
                        String pCode = o[0]!=null ? o[0].toString() : "";
                        if (pCode.equals(code)) {
                            ContractAccountOperationByService caos = manager.find(ContractAccountOperationByService.class, Long.valueOf(o[1].toString()));
                            caos.setMedcase(visit);
                            manager.persist(caos);
                            ispaid = true;
                            break;
                        }
                    }
                    if (!ispaid) {
                        errorList.append("Услуга ").append(code).append(" ").append(ms.getName()).append(" не входит в список оплаченных\n\r");

                    }
                } catch (Exception e) {
                 LOG.error(e.getMessage(), e);
                }
            }
            if (errorList.length()>0 && !aContext.getSessionContext().isCallerInRole("/Policy/Mis/Contract/MakeUnpaidServices")) {
                throw new IllegalStateException(errorList.toString());
            }
        } else if (visit.getGuarantee()!=null) {
            EntityManager manager = aContext.getEntityManager();
            List<ServiceMedCase> serviceList = manager.createQuery("from ServiceMedCase where parent=:par").setParameter("par",visit).getResultList();
            for (ServiceMedCase smc :serviceList) {
                new ContractServiceBean().addMedServiceAccount("MEDCASE",smc.getId(),smc.getMedService().getCode()
                        ,visit.getPatient().getId(), visit.getGuarantee(), manager);
            }
        }

    }
}
