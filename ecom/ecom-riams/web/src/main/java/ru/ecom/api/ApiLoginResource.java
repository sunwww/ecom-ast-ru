package ru.ecom.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("login")
public class ApiLoginResource {

    @GET
    @Path("login/{token}")
    public String login(@Context HttpServletRequest aRequest, @PathParam("token") String login) {
        ApiUtil.login(login, aRequest);
        return "404 :-)";
    }

    @GET
    @Path("logout")
    public String logout(@Context HttpServletRequest aRequest) {
        ApiUtil.logout(aRequest);
        return "404 :-(";

    }

    @GET
    @Path("getChildCount")
    public String getChildCount(@Context HttpServletRequest aRequest, @QueryParam("token") String token, @QueryParam("date") String aDate) throws NamingException, JSONException {
        ApiUtil.login(token,aRequest);
        String tmpDate;
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        if (aDate==null||aDate.equals("")) {
            tmpDate=df.format(new Date());
            aDate=" nb.birthdate = current_date";
        } else if (aDate.equals("-1")){
            tmpDate= df.format(new Date(System.currentTimeMillis()-24*60*60*1000));
            aDate=" nb.birthdate = current_date-1";
        } else if (aDate.split("-").length>1) {
            tmpDate= aDate.split("-")[0];
            aDate=" nb.birthdate between to_date('"+aDate.split("-")[0]+"','dd.MM.yyyy') and to_date('"+aDate.split("-")[1]+"','dd.MM.yyyy')";
        } else {
            tmpDate=aDate;
            aDate=" nb.birthdate = to_date('"+aDate+"','dd.MM.yyyy')";
        }
        String sql = "select to_char(nb.birthdate, 'dd.MM.yyyy') as bDate" +
                " ,count (case when vs.omccode='1' then nb.id else null end) as cntMale" +
                " ,count (case when vs.omccode='2' then nb.id else null end) as cntFeMale" +
                " from newBorn nb" +
                " left join vocSex vs on vs.id=nb.sex_id" +
                " left join vocLiveBorn vlb on vlb.id=nb.liveborn_id" +
                " where "+aDate+" and vlb.code='1'  " +
                " group by nb.birthdate" +
                " order by nb.birthdate ";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JSONArray arr= new JSONArray(service.executeNativeSqlGetJSON(new String[]{"date","male","female"},sql,10));
        StringBuilder ret = new StringBuilder();
        if (arr!=null&&arr.length()>0) {
            for (int i=0;i<arr.length();i++) {
                JSONObject obj = arr.getJSONObject(i);
                ret.append("newBornSize=").append(arr.length()).append(";birthDate=").append(obj.getString("date")).append(";birthMale=").append(obj.getString("male")).append(";birthFeMale=").append(obj.getString("female")).append("\n\r");
            }
        }
        else
            ret.append("newBornSize=").append("1").append(";birthDate=").append(tmpDate).append(";birthMale=").append("0").append(";birthFeMale=").append("0").append("\n\r");
        return ret.toString();
    }
}
