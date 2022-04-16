package ru.ecom.mis.ejb.form.disability.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.disability.DisabilityDocument;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.disability.DisabilityDocumentByPatientForm;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Создание формы по документу нетрудоспособности
 *
 * @author stkacheva
 */
public class DocumentByPatientPreCreate implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        EntityManager manager = aContext.getEntityManager();
        DisabilityDocumentByPatientForm form = (DisabilityDocumentByPatientForm) aForm;
        Patient pat = manager.find(Patient.class, aParentId);
        if (pat != null) {
            // Обязательно, чтобы все даты окончания были заполнены
            //(Новый период нетрудоспособности не должен заводиться если у последнего
            //не проставлена да окончания)
            //Новый период нетрудоспособности должен заводиться с датой последняя дата окончания + 1

            List<DisabilityDocument> list = manager.createQuery("from DisabilityDocument where patient_id = :pat and closeReason_id is null  and (noActuality is null or cast(noActuality as integer)=0)").setParameter("pat", pat.getId()).getResultList();
            if (!list.isEmpty()) {
                StringBuilder err = new StringBuilder();
                err.append("Невозможно добавить новый случай нетрудоспособности. <br/>В базе имеются не закрытые документы по предыдущему случаю:");
                for (DisabilityDocument doc : list) {
                    err.append("<br/><a href='entityParentView-dis_document.do?id=")
                            .append(doc.getId()).append("'>")
                            .append(doc.getInfo())
                            .append("</a>");
                }
                throw new IllegalStateException(err.toString());
            }
            form.setJob(pat.getWorks());
        } else {
            throw new IllegalStateException("Невозможно добавить случай. Сначала надо определить пациента");
        }
    }

}