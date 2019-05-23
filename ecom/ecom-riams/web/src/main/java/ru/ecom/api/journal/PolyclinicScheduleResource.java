package ru.ecom.api.journal;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Milamesher on 22.05.2019.
 */

@Path("/polySchedule")
public class PolyclinicScheduleResource {
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    /** Расписание специалистов на сегодняшний день*/
    public String getListOncoCase(@Context HttpServletRequest aRequest
    ) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String[] fields = {"num","wfname","fio","cab", "per"};
        return service.executeNativeSqlGetJSON(fields,"select * from selectSheduleForReport()",null);
    }

    @GET
    @Path("/getNumConfig")
    @Produces(MediaType.APPLICATION_JSON)
    /** Получить кол-во выводимых в отчёте строк*/
    public String getNumOfRowsInPolyScheduleReport(@Context HttpServletRequest aRequest
    ) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select keyvalue from softconfig where key='numOfRowsInPolyScheduleReport'");
        return !list.isEmpty()? list.iterator().next().get1().toString() : "";
    }
}