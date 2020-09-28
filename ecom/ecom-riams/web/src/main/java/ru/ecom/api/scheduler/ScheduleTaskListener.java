package ru.ecom.api.scheduler;

import org.apache.log4j.Logger;
import org.json.JSONException;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/** Created by rkurbanov on 10.09.2018. */

@WebListener()
public class ScheduleTaskListener  implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ScheduleTaskListener.class);
    private static Map<ScheduledExecutorService,Task> map = new HashMap<>();
    public void contextInitialized(ServletContextEvent event) {
        LOG.info("ScheduleTaskListener is start");
        try {
            setScheduler(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setScheduler(ServletContextEvent event) {
        try {
            IDisabilityService service1 = Injection.find(event, "riams").getService(IDisabilityService.class);
            String endpoint = service1.getSoftConfigValue("EndpointApi", null);
            if (endpoint != null) {
                endpoint = (endpoint.split("/")[2]).split(":")[0];
                List<String> endpoints = getEndPoints();
                for (String endp : endpoints) {
                    if (endp.equals(endpoint)) {
                        LOG.info(endp + " This is TRUE server");
                        IWebQueryService service = Injection.find(event, "riams").getService(IWebQueryService.class);
                        Collection<WebQueryResult> list = service.executeNativeSql("select id,name,link, time from ScheduleTask");
                        ScheduleTasks scheduleTasks = new ScheduleTasks();
                        for (WebQueryResult wqr : list) {
                            Long id = Long.valueOf(wqr.get1().toString());
                            map = scheduleTasks.startThread(id, wqr.get2().toString(), wqr.get3().toString(), wqr.get4().toString());
                        }
                        break;
                    }
                }
            }
        } catch (NamingException | JSONException e) {
            LOG.error("JBOSS is DOWN , попробуем через 10 минут: "+e.getMessage());
            new Thread(() -> {
                try {
                    Thread.sleep(600000); //ждём 10 минут
                    setScheduler(event); //вторая попытка
                } catch (Exception e1) {
                    e1.printStackTrace();LOG.error(e1.getMessage(),e1);
                }
            }).start();
        }
    }
    public void contextDestroyed(ServletContextEvent event) {
        for (Map.Entry entry : map.entrySet()) {
                ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();
                scheduledExecutorService.shutdownNow();
                scheduledExecutorService.shutdown();
        }
        LOG.info("ScheduleTaskListener is destroyed");
    }

    private List<String> getEndPoints() {
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> endPoints = new ArrayList<>();
            while(e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    endPoints.add(i.getHostAddress());
                }
            }
            return endPoints;
        } catch (SocketException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
