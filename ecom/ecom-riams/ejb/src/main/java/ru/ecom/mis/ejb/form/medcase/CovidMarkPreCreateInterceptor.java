package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

import java.util.List;

public class CovidMarkPreCreateInterceptor implements IParentFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        List isExisted = aContext.getEntityManager().createNativeQuery("select id from CovidMark where medcase_id = " + aParentId).getResultList();
        //если нет созданной карты, надо проверять диагноз
        if (isExisted.isEmpty()) {
            Object val = aContext.getEntityManager().createNativeQuery(" select count(ds.id) from medcase mc" +
                    " left join diagnosis ds on ds.medcase_id = mc.id or ds.medcase_id = ANY(select id from medcase where parent_id = mc.id)" +
                    " left join medcase slo on slo.parent_id=mc.id and slo.id=ds.medcase_id" +
                    " left join vocidc10 idc on idc.id=ds.idc10_id" +
                    " where mc.dtype='HospitalMedCase' and idc.code like 'U%'" +
                    " and mc.id=" + aParentId).getSingleResult();
            if (val.toString().equals("0"))
                throw new IllegalArgumentException("Форму оценки тяжести заболевания COVID-19 можно создавать только для случаев с диагнозом U*!");
        }
    }
}