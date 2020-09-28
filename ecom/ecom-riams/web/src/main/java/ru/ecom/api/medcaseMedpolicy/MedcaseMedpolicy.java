package ru.ecom.api.medcaseMedpolicy;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import ru.ecom.api.fondCheck.FondCheckUtil;
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
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Created by rkurbanov on 20.08.2018.
 */

@Path("/medcaseMedpolicy")
public class MedcaseMedpolicy {
    private static final Logger LOG = Logger.getLogger(MedcaseMedpolicy.class);
    @GET
    @Path("syncMedcaseMedpolic")
    @Produces("application/json")
    /**Проверка прикрепленных полисов по госпитализациям*/
    public String syncMedcaseMedpolic(@Context HttpServletRequest aRequest, @WebParam(name="token") String aToken,
                                      @QueryParam("limit") Integer limit,
                                      @QueryParam("startDate") String startDate,
                                      @QueryParam("finishDate") String finishDate) throws NamingException, JSONException {

        ApiUtil.init(aRequest,aToken);
        String limitSql = limit!=null && limit>0 ? "limit "+limit : "";

        if (startDate==null || startDate.equals("")) {
            startDate = "DATE_TRUNC('day', CURRENT_DATE - INTERVAL '1  month') ";
        } else {
            startDate = "'"+startDate+"'";
        }

        if (finishDate==null || finishDate.equals("")) {
            finishDate= "current_date";
        } else {
            finishDate = "'"+finishDate+"'";
        }

        String sql = " select mcmp.id as mpId,mcmp.medcase_id as f2_hospId " +
                " ,coalesce(mp.series,'') as f3_policySeries ,coalesce(mp.polNumber,'') as f4_policyNumber" +
                " ,m.dateStart as f5_hospStart, coalesce(m.dateFinish,current_date) as f6_hospFinish" +
                " ,pat.lastname as f7_lastname, pat.firstname as f8_firstname, coalesce(pat.middlename,'') as f9_middlename , to_char(pat.birthday,'yyyy-MM-dd') as f10_bday" +
                ", mp.id as f11_medpolicyId" +
                " from medcase m" +
                " left join patient pat on pat.id=m.patient_id " +
                " left join medcase_medpolicy mcmp on mcmp.medcase_id = m.id " +
                " left join medpolicy mp on mp.id=mcmp.policies_id" +
                " left join vocservicestream vss on vss.id = m.servicestream_id " +
                " where m.datestart between  "+startDate+" and  "+finishDate+
                " and m.dtype='HospitalMedCase' " +
                " and vss.omccode='1' " +
                " and m.deniedhospitalizating_id is null and mp.id is not null and mcmp.datesync is null " +limitSql;
        LOG.info(sql);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list= service.executeNativeSql(sql);

        JSONArray arr = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (WebQueryResult wqr:list) {
            String lastname = wqr.get7().toString();
                boolean isActualPolicy = false;
                String polSeries = wqr.get3().toString();
                String polNumber = wqr.get4().toString();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", wqr.get1().toString()); // medcaseId
            try {
               JSONObject fondInfo = new JSONObject(FondCheckUtil.check(null,polSeries,polNumber,null,null,null,null,null));

               if (fondInfo.has("Polis")) { //полис найден
                   JSONArray policies = fondInfo.getJSONArray("Polis");
                   JSONObject policy = null;
                   for (int i=0;i<policies.length();i++) {
                       JSONObject pol = policies.getJSONObject(i);
                       if (!pol.has("isActual")) {
                           LOG.info(lastname+" no Actual!"+pol.toString());
                           continue;
                       }
                       if ("1".equals(pol.getString("isActual")) && pol.getString("seriesAndNumber").equals(polSeries+" "+polNumber)) {
                           policy = pol;
                           break;
                       }
                   }
                   if (policy == null ){
                       jsonObject.put("status","error_no_pol");
                       LOG.warn(lastname+" Нет полиса!!");
                   } else {
                       long hospStartDate = sdf.parse(wqr.get5().toString()).getTime();
                       long hospFinishDate = sdf.parse(wqr.get6().toString()).getTime();
                       long policyStartDate = sdf.parse(policy.getString("dateStart")).getTime();
                       long policyExpireDate = sdf.parse(policy.getString("dateEarlyEnd")).getTime();
                       if (policyStartDate<=hospFinishDate && policyExpireDate>=hospStartDate ) { //Полис действует на момент госпитализации, смотрим - сходятся ли данные пациента по полису с данными МЕДОСа
                           JSONObject patient = fondInfo.getJSONArray("Person").getJSONObject(0);
                           long birthday = sdf.parse(wqr.get10().toString()).getTime();
                           if (patient.getString("lastname").equalsIgnoreCase(lastname)
                                   && patient.getString("firstname").equalsIgnoreCase(wqr.get8().toString())
                                   && patient.getString("middlename").equalsIgnoreCase(wqr.get9().toString())
                                   && sdf.parse(patient.getString("birthday")).getTime()==birthday) {
                               LOG.info("УРА! пациент сошелся с данными ФОМС");
                               isActualPolicy = true;

                           } else {
                               LOG.warn("Пациент не сходится: "+
                                       patient.getString("lastname")+" : "+ lastname +" > " +
                                       patient.getString("firstname")+" : "+ wqr.get8().toString()+" > " +
                                       patient.getString("middlename")+" : "+wqr.get9().toString()+" > " +
                                       patient.getString("birthday")+" : "+wqr.get10().toString());
                           }
                       } else {
                        //   LOG.info("fondINfo = "+fondInfo);
                           LOG.warn("Дата действия полиса не попадает в госпитализацию: "+policy.getString("dateStart")+" "+policy.getString("dateEarlyEnd"));
                           jsonObject.put("status","bad_period");
                           //Установим дату окончания полиса согласно ТФОМС
                           service.executeUpdateNativeSql("update medpolicy set actualDateTo='"+sdf.format(new java.util.Date(policyExpireDate)) + "' where id = "+wqr.get11().toString());
                       }
                   }

               } else {
                   LOG.warn("Нет действующего полиса");
                   jsonObject.put("status","error_no_pol2");
               }
               // дата начала действия полиса ранее даты окончания госпитализации - хорошо
            } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
                LOG.error(e.getMessage(),e);
                //e.printStackTrace();
            }

                if (isActualPolicy) {
                    service.executeUpdateNativeSql("update medcase_medpolicy set datesync = current_date, isManualSync='0' where id=" + wqr.get1().toString());
                    jsonObject.put("status","ok");
                }

                jsonObject.put("result", isActualPolicy);
                arr.put(jsonObject);
        }

        JSONObject jsonObject = new JSONObject().put("arr",arr);
        return jsonObject.toString();
    }
}