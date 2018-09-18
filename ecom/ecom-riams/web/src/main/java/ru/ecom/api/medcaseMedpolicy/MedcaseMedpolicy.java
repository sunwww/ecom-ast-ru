package ru.ecom.api.medcaseMedpolicy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.jws.WebParam;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by rkurbanov on 20.08.2018.
 */

@Path("/medcaseMedpolicy")
public class MedcaseMedpolicy {

    @GET
    @Path("syncMedcaseMedpolic")
    @Produces("application/json")
    public String syncMedcaseMedpolic(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken,
                                      @QueryParam("limit") String limit,
                                      @QueryParam("startDate") String startdate,
                                      @QueryParam("finishDate") String finishdate) throws SQLException, NamingException, JSONException {

        ApiUtil.init(aRequest,aToken);

        if(limit!=null){limit=" limit "+limit;}
        if(limit==null || limit.equals("")){limit="";}

        if(startdate==null || startdate.equals("")){
            startdate = "DATE_TRUNC('day', CURRENT_DATE - INTERVAL '1  month') ";
        }else startdate = "'"+startdate+"'";
        if(finishdate==null || finishdate.equals(""))
        {
            finishdate= "current_date";
        }else finishdate = "'"+finishdate+"'";


        String sql = " select mp.id,mp.medcase_id,mp.policies_id " +
                " from medcase m " +
                " left join medcase_medpolicy mp on mp.medcase_id = m.id " +
                " left join vocservicestream vss on vss.id = m.servicestream_id " +
                " where datestart  between  "+startdate+"' and  "+finishdate+
                " and m.dtype='HospitalMedCase' " +
                " and vss.omccode='1' " +
                " and m.deniedhospitalizating_id is null and mp.datesync is null " +limit;


        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list= service.executeNativeSql(sql);

        JSONArray arr = new JSONArray();

        for (WebQueryResult wqr:list) {
            if(wqr.get1()!=null){

                boolean mark = false;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", wqr.get1().toString());

                Date msd = null, med = null; //Начало и конец случая
                Date psd = null, ped = null; // Начало и конец полиса

                Collection<WebQueryResult> mcs = service.executeNativeSql("select datestart,datefinish from medcase where id=" + wqr.get2().toString());
                for (WebQueryResult mc : mcs) {
                    msd = (Date) mc.get1();
                    med = (Date) mc.get2();
                }

                Collection<WebQueryResult> pols = service.executeNativeSql(" select actualdatefrom, actualdateto from medpolicy where id =" + wqr.get3().toString());
                for (WebQueryResult pol : pols) {
                    psd = (Date) pol.get1();
                    ped = (Date) pol.get2();
                }

                if (med == null && ped == null) {
                    mark = true;
                }

                if (med == null && ped != null) {
                    int comp = ped.compareTo(msd);
                    if (comp == 1 || comp ==0) {
                        mark = true;
                    } else {
                        jsonObject.put("msg", "Кончился раньше, чем начался случай");
                    }
                }

                if (med != null && ped == null) {
                    int comp = psd.compareTo(med);
                    if (comp == -1 || comp == 0) {
                        mark = true;
                    } else {
                        jsonObject.put("msg", "Выдан уже после окончания случая");
                    }
                }

                if (med != null && ped != null) {
                    int comp = psd.compareTo(med);
                    int comp2 = ped.compareTo(msd);
                    if ((comp == -1 || comp == 0) && (comp2 == 1 || comp2 == 0)) {
                        mark = true;
                    } else {
                        if (comp == 1) {
                            jsonObject.put("msg", "Выдан уже после окончания случая");
                        }
                        if (comp2 == -1) {
                            jsonObject.put("msg", "Кончился раньше, чем начался случай");
                        }
                    }
                }

                if (mark == true) {
                    service.executeUpdateNativeSql("update medcase_medpolicy set datesync = current_date where id=" + wqr.get1().toString());
                }

                jsonObject.put("result", mark);
                arr.put(jsonObject);
            }
        }

        JSONObject jsonObject = new JSONObject().put("arr",arr);
        if(jsonObject==null) return "{\"error\":\"null\"}";
        return jsonObject.toString();
    }
}
