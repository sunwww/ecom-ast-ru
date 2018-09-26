package ru.ecom.api.sсheduler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Created by rkurbanov on 04.09.2018. */

@Path("/scheduleTasks")
public class ScheduleTasks {

    static HashMap<ScheduledExecutorService,Task> map = new HashMap<>();

    @GET
    @Path("/startTask")
    @Produces("application/json;charset=UTF-8")
    public String startThread(@Context HttpServletRequest aRequest,
                              @WebParam(name="token") String aToken,
                              @QueryParam("id") String id,
                              @QueryParam("name") String name,
                              @QueryParam("link") String link,
                              @QueryParam("time") String time) throws InterruptedException, JSONException {

        ApiUtil.init(aRequest,aToken);
        return startThread(id,name,link,time);
    }

    @GET
    @Path("/showTasks")
    @Produces("application/json;charset=UTF-8")
    public String showTasks(@Context HttpServletRequest aRequest,
                                    @WebParam(name="token") String aToken) throws InterruptedException, JSONException {

        ApiUtil.init(aRequest,aToken);
        return showTasks();
    }

    @GET
    @Path("/shutdownTask")
    @Produces("application/json;charset=UTF-8")
    public String shutdown(@Context HttpServletRequest aRequest,
                           @WebParam(name="token") String aToken,
                           @QueryParam("id") String id) throws InterruptedException, JSONException {

        ApiUtil.init(aRequest,aToken);

        for (Map.Entry entry : map.entrySet()) {
            Task task = (Task) entry.getValue();
            if(id.equals(task.getId().toString())){
                ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();
                scheduledExecutorService.shutdownNow();
                scheduledExecutorService.shutdown();
            }
        }
        return new JSONObject().put(id,"isStopped").toString();
    }


    private String startThread(String id,String name,String link,String atTime) throws JSONException {

        String[] times = atTime.split(":");
        link = link.replace("*","&");

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Task task = new Task(Long.valueOf(id) ,name,link,atTime);
        scheduledExecutorService.scheduleAtFixedRate(task, timeInit(times[0],times[1],times[2]), 24*60*60, TimeUnit.SECONDS);
        map.put(scheduledExecutorService,task);

        return new JSONObject().put(name,"isStart").toString();
    }

    public HashMap<ScheduledExecutorService,Task> startThread(Long id,String name,String link,String atTime)
            throws JSONException {

        startThread(id.toString(),name,link,atTime);
        return map;
    }

    private Long timeInit(String hour,String  minute,String second){
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNext5 ;

        zonedNext5 = zonedNow
                .withHour(Integer.parseInt(hour))
                .withMinute(Integer.parseInt(minute))
                .withSecond(Integer.parseInt(second));

        if(zonedNow.compareTo(zonedNext5) > 0)
            zonedNext5 = zonedNext5.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNext5);
        return duration.getSeconds();
    }

    private String showTasks() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Map.Entry entry : map.entrySet()) {
            Task task = (Task)entry.getValue();
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();

            jsonArray.put(new JSONObject(task.getServiceInfo())
                    .put("isShutdawn",scheduledExecutorService.isShutdown()));
        }
        return new JSONObject().put("Array",jsonArray).toString();
    }
}
