package ru.ecom.diary.ejb.service.protocol;

import ru.ecom.diary.ejb.form.protocol.parameter.ParameterReferenceValueForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

import java.util.List;

public class ParameterReferenceValueSaveInterceptor implements IFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        ParameterReferenceValueForm form = (ParameterReferenceValueForm) aForm;
        Double min = Double.parseDouble(form.getNormaMin());
        Double max = Double.parseDouble(form.getNormaMax());

        if (min >= max) {
            throw new IllegalStateException("Минимальный реф. значение больше максимального!");
        }
        if (form.getSuperMin() >= form.getSuperMax()) {
            throw new IllegalStateException("Минимальное возможное значение больше максимального!");
        }

        List<Object> doubleList = aContext.getEntityManager().createNativeQuery("" +
                "select ageFrom||' - '||prv.ageTo from parameterReferenceValue prv " +
                " where prv.parameter_id=:parId" +
                " and not ( least(prv.agefrom,:ageFrom) < greatest(prv.ageto,:ageTo) and least(prv.ageto,:ageTo) < greatest(prv.agefrom,:ageFrom) )" +
                (form.getSex()>0L ? " and (sex_id is null or sex_id="+form.getSex()+")" : "") +
                (form.getId()>0L ? " and prv.id !="+form.getId() : "")).setParameter("parId", form.getParameter()).setParameter("ageFrom", form.getAgeFrom())
                .setParameter("ageTo", form.getAgeTo()).setMaxResults(1).getResultList();
        if (!doubleList.isEmpty()) {
            throw new IllegalStateException("Пересечение с возрастом: "+doubleList.get(0).toString());
        }
    }
}