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
                              @QueryParam("name") String name,
                              @QueryParam("everySeconds") Integer sec) throws InterruptedException, JSONException {

        ApiUtil.init(aRequest,aToken);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Task task = new Task();
        task.addService(new ServiceTasks(name));
        scheduledExecutorService.scheduleAtFixedRate(task, 0, sec, TimeUnit.SECONDS);
        map.put(scheduledExecutorService,task);
        return new JSONObject().put(name,"isStart").toString();

    }

    @GET
    @Path("/showActiveTasks")
    @Produces("application/json;charset=UTF-8")
    public String showActiveThreads(@Context HttpServletRequest aRequest,
                                    @WebParam(name="token") String aToken) throws InterruptedException, JSONException {

        ApiUtil.init(aRequest,aToken);
        JSONArray jsonArray = new JSONArray();

        for (Map.Entry entry : map.entrySet()) {
            Task workerThread = (Task)entry.getValue();
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();

            jsonArray.put(new JSONObject()
                    .put("name",workerThread.getService())
                    .put("isShutdawn",scheduledExecutorService.isShutdown()));
        }
        return new JSONObject().put("Array",jsonArray).toString();
    }

    @GET
    @Path("/shutdownTask")
    @Produces("application/json;charset=UTF-8")
    public String shtdwn(@Context HttpServletRequest aRequest,
                         @WebParam(name="token") String aToken,
                         @QueryParam("name") String name) throws InterruptedException, JSONException {

        ApiUtil.init(aRequest,aToken);

        for (Map.Entry entry : map.entrySet()) {
            Task workerThread = (Task) entry.getValue();
            if(name.equals(workerThread.getService())){
                ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService)entry.getKey();
                scheduledExecutorService.shutdownNow();
                scheduledExecutorService.shutdown();
            }
        }

        return new JSONObject().put(name,"isStopped").toString();
    }
}
