package ru.ecom.expert2.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.services.monitor.IRemoteMonitorService;
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

            final long monitorId = ((IRemoteMonitorService)EjbInjection.getInstance().getService("IMonitorService","remote")).createMonitor();
            listEntry.setMonitorId(monitorId);
      /*      System.out.println("monitor!!!"+monitorId);
            aContext.getEntityManager().persist(listEntry);
                new Thread() { //Именно так! Если слушать что предлагеет идея - jboss не запустится!
                        public void run(){
           */         try {
                        System.out.println("start!!!!");
            //            Thread.sleep(10000);
                        System.out.println("start!!!!2");
                       EjbInjection.getInstance().getLocalService(IExpert2Service.class)
                            .fillListEntry(listEntry, form.getHistoryNumbers(),monitorId);
                        System.out.println("finish!!!!");
                    } catch (Exception e) {
                        System.out.println("exception!!!!");
                        e.printStackTrace();
                    }
          /*      }}.start();
*/

        }

    }
}
