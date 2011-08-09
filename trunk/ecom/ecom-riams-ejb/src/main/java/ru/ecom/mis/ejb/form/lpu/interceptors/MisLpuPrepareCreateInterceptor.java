package ru.ecom.mis.ejb.form.lpu.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;

/**
 *
 */
public class MisLpuPrepareCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {

        MisLpuForm form = (MisLpuForm) aForm ;
        //form.setName("hello");
        // проверка: Если есть участки, то нельзя добавить подразделение
//        MisLpu parentLpu = manager.find(MisLpu.class, aParentId) ;
//        if(parentLpu!=null) {
//            if(parentLpu.getAreas()!=null && !parentLpu.getAreas().isEmpty() ) {
//                throw new IllegalStateException("У ЛПУ уже есть участки. Добавить подразделение нельзя.") ;
//            }
//        }
    }
}
