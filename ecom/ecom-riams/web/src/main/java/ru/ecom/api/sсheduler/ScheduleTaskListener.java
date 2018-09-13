package ru.ecom.api.sсheduler;

import org.json.JSONException;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.web.util.Injection;

import javax.management.*;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/** Created by rkurbanov on 10.09.2018. */

@WebListener()
public class ScheduleTaskListener  implements ServletContextListener {

    private static Map<ScheduledExecutorService,Task> map = new HashMap<>();

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ScheduleTaskListener is start");

        try {
            IDisabilityService service1 = Injection.find(event,"riams")
                    .getService(IDisabilityService.class);
            String endpoint = service1.getSoftConfigValue("EndpointApi", "null");

            endpoint = "http://"+endpoint.split("/")[2];
            if(getEndPoints().contains(endpoint)) {
                System.out.println("This is TRUE server");

                IWebQueryService service = Injection.find(event, "riams")
                        .getService(IWebQueryService.class);
                Collection<WebQueryResult> list = service.executeNativeSql("select id,name,link, time from ScheduleTask");
                ScheduleTasks scheduleTasks = new ScheduleTasks();
                for (WebQueryResult wqr : list) {
                    Long id = Long.valueOf(wqr.get1().toString());
                    map = scheduleTasks.startThread(id, wqr.get2().toString(), wqr.get3().toString(), wqr.get4().toString());
                }
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

    private List<String> getEndPoints() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objs = mbs.queryNames( new ObjectName( "*:type=Connector,*" ),
                    Query.match( Query.attr( "protocol" ), Query.value( "HTTP/1.1" ) ) );
            String hostname = InetAddress.getLocalHost().getHostName();
            InetAddress[] addresses = InetAddress.getAllByName( hostname );
            ArrayList<String> endPoints = new ArrayList<>();
            for ( ObjectName obj : objs ) {
                String scheme = mbs.getAttribute( obj, "scheme" ).toString();
                String port = obj.getKeyProperty( "port" );
                for ( InetAddress addr : addresses ) {
                    String host = addr.getHostAddress();
                    String ep = scheme + "://" + host + ":" + port;
                    endPoints.add( ep );
                }
            }
            return endPoints;
        } catch ( MalformedObjectNameException | UnknownHostException | MBeanException | AttributeNotFoundException | InstanceNotFoundException | ReflectionException ex ) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
