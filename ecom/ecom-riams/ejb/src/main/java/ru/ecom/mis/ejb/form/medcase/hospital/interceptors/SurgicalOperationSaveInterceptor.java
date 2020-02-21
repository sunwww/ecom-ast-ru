package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.SurgicalOperation;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.ecom.mis.ejb.service.contract.ContractServiceBean;
import ru.ecom.mis.ejb.service.contract.IContractService;

import javax.persistence.EntityManager;
import java.util.List;

public class SurgicalOperationSaveInterceptor implements IFormInterceptor {
	private static final Logger LOG = Logger.getLogger(DischargeMedCaseSaveInterceptor.class);

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		EntityManager manager = aContext.getEntityManager();
		SurgicalOperationForm form = (SurgicalOperationForm) aForm;
		MedCase parentMedCase = manager.find(MedCase.class, form.getMedCase());
		VocServiceStream serviceStream = parentMedCase.getServiceStream();
		boolean needPaidConfirmation = Boolean.TRUE.equals(serviceStream.getIsPaidConfirmation());
		boolean isCalcDogovor = Boolean.TRUE.equals(serviceStream.getIsCalcDogovor());
		if (needPaidConfirmation || isCalcDogovor) {
			SurgicalOperation oper = (SurgicalOperation) aEntity;
			MedService medService = manager.find(MedService.class, form.getMedService());
			String medServiceCode = medService.getCode();
			long patientId = parentMedCase.getPatient().getId();
			if (needPaidConfirmation) { //ищем подтверждение оплаты
				String operation = "OPERATION";
				List<Object[]> paidServiceList = EjbInjection.getInstance().getRemoteService(IContractService.class)
						.getPaidMedServicesByPatient(patientId, medServiceCode, parentMedCase.getId(), operation, form.getId(), null);
				if (paidServiceList.isEmpty()) {
					if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/Contract/MakeUnpaidServices")) {
						throw new IllegalStateException("Операция " + medServiceCode + " " + medService.getName() + " не оплачена пациентом!");
					}
				} else { //Нашли услугу, проставляем соответствие
					List<ContractAccountOperationByService> services = manager.createQuery("from ContractAccountOperationByService where serviceType=:serviceType and serviceId=:serviceId")
							.setParameter("serviceType", operation).setParameter("serviceId", oper.getId()).getResultList();
					ContractAccountOperationByService payCaos = manager.find(ContractAccountOperationByService.class, Long.valueOf(paidServiceList.get(0)[1].toString()));
					if (!services.isEmpty()) {
						ContractAccountOperationByService realCaos = services.get(0);
						if (realCaos.getId() != payCaos.getId()) { //при изменение услуги - удаляем информацию в caos о старой услуге
							realCaos.setServiceId(null);
							realCaos.setServiceType(null);
							manager.persist(realCaos);
						}
					}
					payCaos.setMedcase(parentMedCase);
					payCaos.setServiceType(operation);
					payCaos.setServiceId(oper.getId());
					manager.persist(payCaos);
				}
			} else { //ДМС
				LOG.info("Делаем манипуляции для ДМС");
				String typeService = "SURGICALOPERATION";

				ContractGuarantee letter = oper.getMedCase().getGuarantee() != null ? oper.getMedCase().getGuarantee() : oper.getMedCase().getParent().getGuarantee();
				if (letter == null) {
					LOG.error("Не указано гарантийное письмо для случая ДМС");
				} else {
					new ContractServiceBean().addMedServiceAccount(typeService, oper.getId(), medServiceCode, patientId, letter, manager);
				}
			}
		}
    }
}