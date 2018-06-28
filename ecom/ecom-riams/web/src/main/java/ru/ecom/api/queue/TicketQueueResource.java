package ru.ecom.api.queue;

import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.queue.domain.QueueTicket;
import ru.ecom.queue.service.IQueueService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/queue/ticket")

public class TicketQueueResource {
    @GET
    @Path("getQueues")
    @Produces(MediaType.APPLICATION_JSON)
    /* Получаем все возможные виды очередей*/
    public String getQueues(@Context HttpServletRequest aRequest, @QueryParam("token") String token, @QueryParam("terminal") String aTerminalCode) throws NamingException {
        String sql = "select id, name from VocQueue where isArchival='0'";
        //TODO Сделать возможность возвращать разные очереди для разных терминалов
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeNativeSqlGetJSON(new String[] {"queueId", "queueName"},sql,null);
    }

    @GET
    @Path("getNewTicket")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNewTicket(@Context HttpServletRequest aRequest, @QueryParam("token") String token, @QueryParam("queue") Long aQueueId) throws NamingException {
        //IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        IQueueService queueService = Injection.find(aRequest).getService(IQueueService.class);
        QueueTicket ticket = queueService.getNewTicket(aQueueId);
        JSONObject test = new JSONObject();
        try {
            test.put("number", ticket.getNumber());
            test.put("fullNumber", ticket.getFullNumber());
            test.put("queueName", ticket.getQueue().getType().getName());
            test.put("queueDate", ticket.getQueue().getDate());
            test.put("ticketDate", ticket.getCreateDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test.toString();


    }
}
