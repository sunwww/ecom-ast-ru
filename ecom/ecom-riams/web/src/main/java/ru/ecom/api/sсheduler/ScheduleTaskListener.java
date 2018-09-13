package ru.ecom.api.sсheduler;

import org.json.JSONException;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/** Created by rkurbanov on 10.09.2018. */

@WebListener()
public class ScheduleTaskListener  implements ServletContextListener {

    private static Map<ScheduledExecutorService,Task> map = new HashMap<>();

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ScheduleTaskListener is start");

        try {
            IWebQueryService service = Injection.find(event, "riams", "66405d38-a173-4cb7-a1b6-3ada51c16ac5").getService(IWebQueryService.class);
            Collection<WebQueryResult> list = service.executeNativeSql("select id,name,link, time from ScheduleTask");

            ScheduleTasks scheduleTasks  = new ScheduleTasks();

            for (WebQueryResult wqr : list){
                Long id = Long.valueOf(wqr.get1().toString());
                map = scheduleTasks.startThread(id,wqr.get2().toString(),wqr.get3().toString(),wqr.get4().toString());
            }

        }catch (NamingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("ScheduleTaskListener is destroyed");
        for (Map.Entry entry : map.entrySet()) {
                ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();
                scheduledExecutorService.shutdownNow();
                scheduledExecutorService.shutdown();
        }
    }
}
