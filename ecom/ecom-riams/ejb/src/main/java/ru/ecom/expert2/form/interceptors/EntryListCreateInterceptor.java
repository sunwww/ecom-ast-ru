package ru.ecom.expert2.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expert2.form.E2EntryListForm;
import ru.ecom.expert2.service.IExpert2Service;

public class EntryListCreateInterceptor implements IFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {

        /**
         * СОздаем заполнение - находим список записей, расчитываем КСГ
         */
        E2EntryListForm form = (E2EntryListForm) aForm;
        if (form.getCreateEmptyEntryList()==null || !form.getCreateEmptyEntryList()) {
            E2ListEntry listEntry = (E2ListEntry) aEntity;

      /*      final long monitorId = EjbInjection.getInstance().getRemoteService(MonitorServiceBean.class).createMonitor();
            listEntry.setMonitorId(monitorId);
            aContext.getEntityManager().persist(listEntry);
                new Thread() {
                        public void run(){
                */    try {
                        EjbInjection.getInstance().getLocalService(IExpert2Service.class)
                            .fillListEntry(listEntry, form.getHistoryNumbers(),0L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                //}};


        }

    }
}
