package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;

public class CovidMarkPreCreateInterceptor implements IParentFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        Object val = aContext.getEntityManager().createNativeQuery("select count(ds.id) from diagnosis ds" +
                " left join medcase sls on sls.id=ds.medcase_id" +
                " left join medcase slo on slo.id=ds.medcase_id and slo.parent_id=sls.id" +
                " left join vocidc10 idc on idc.id=ds.idc10_id" +
                " where sls.dtype='HospitalMedCase' and idc.code like 'U%'" +
                " and sls.id=" + aParentId).getSingleResult();
        if (val.toString().equals("0"))
            throw new IllegalArgumentException("Форму оценки тяжести заболевания COVID-19 можно создавать только для случаев с диагнозом U*!");
    }
}
