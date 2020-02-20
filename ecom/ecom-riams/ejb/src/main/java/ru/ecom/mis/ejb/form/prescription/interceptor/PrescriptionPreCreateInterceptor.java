package ru.ecom.mis.ejb.form.prescription.interceptor;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.contract.ContractGuarantee;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.form.prescription.PrescriptionForm;
import ru.ecom.mis.ejb.service.contract.ContractServiceBean;
import ru.nuzmsh.forms.response.FormMessage;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

public class PrescriptionPreCreateInterceptor implements IParentFormInterceptor {
    private static final Logger LOG = Logger.getLogger(PrescriptionPreCreateInterceptor.class);

    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        //Проставим поток обслуживания родительского случая
        EntityManager manager = aContext.getEntityManager();
        PrescriptList pl = manager.find(PrescriptList.class,aParentId);
        if (pl==null || pl.getMedCase() == null ) return;
        PrescriptionForm form = (PrescriptionForm) aForm;
        VocServiceStream serviceStream = pl.getServiceStream();
        MedCase medCase = pl.getMedCase();
        if (serviceStream!=null) {
            form.setServiceStream(serviceStream.getId());
        } else {
            throw new IllegalStateException("Необходимо <a href='entityEdit-stac_sslAdmission.do?id="
                    +medCase.getId()+"' target='_blank'>проставить поток обслуживания!</a>");
        }

        form.setMedcaseType(medCase instanceof HospitalMedCase ? "HOSPITAL" : "POLYCLINIC");
        form.setMedcaseId(medCase.getId());
        form.setAllowOnlyPaid(Boolean.TRUE.equals(serviceStream.getIsPaidConfirmation()));
        form.setPatient(medCase.getPatient().getId());
        if (Boolean.TRUE.equals(serviceStream.getIsCalcDogovor()) ) { // находим остаток по г. письму
            ContractGuarantee guarantee = medCase.getGuarantee()!=null ? medCase.getGuarantee() : medCase.getParent().getGuarantee();
            if (guarantee!=null) {
                form.setGuaranteeId(guarantee.getId());
                try {
                    JSONObject guar = new JSONObject(new ContractServiceBean().getGuaranteeLimit(guarantee.getId(), manager));
                    if (guar.has("number")) {
                        if (guar.getBoolean("isNoLimit")) {
                            form.addMessage(new FormMessage("Гарантийное письмо №"+guar.getString("number")+" без лимита", true));
                        } else {
                            form.addMessage(new FormMessage("Остаток по гарантийному письму №"+guar.getString("number")+" составляет: "+guar.get("ostatok")+" руб.", false));
                        }
                    }
                } catch (NamingException e) {
                    LOG.error(e);
                }
            } else {
                LOG.warn("Не найдено гар. письмо перед созданием назначения"+pl.getId());
            }
        }
    }
}