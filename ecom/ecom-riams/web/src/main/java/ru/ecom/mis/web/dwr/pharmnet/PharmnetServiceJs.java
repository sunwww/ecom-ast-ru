package ru.ecom.mis.web.dwr.pharmnet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.pharmnetPharmacy.GoodsLeaveEntity;
import ru.ecom.mis.ejb.domain.pharmnetPharmacy.PharmnetComplectRowEntity;
import ru.ecom.mis.ejb.domain.pharmnetPharmacy.PharmnetStorageEntity;
import ru.ecom.mis.ejb.service.pharmacy.IPharmOperationService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Сервис Фармнет-аптеки
 * @author rkurbanov
 */
public class PharmnetServiceJs {

    public Integer createOutcome(String aJson, String medcase, String username, HttpServletRequest aRequest)
            throws JSONException, NamingException {
        JSONObject obj = new JSONObject(aJson);
        JSONArray params = obj.getJSONArray("array");

        StringBuilder sql = new StringBuilder();

        for (int i = 0; i < params.length(); i++) {
            JSONObject param = (JSONObject) params.get(i);
            IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
            String count = (String) param.get("count");
            String goodleaveId = (String) param.get("goodleaveId");
            sql = new StringBuilder();
            sql.append("select PharmnetOutcome ("+goodleaveId+","+medcase+",'"+username+"',"+count+")");
            service.executeNativeSql(sql.toString());
        }
        return 1;
    }


    public Integer createComplectRow(String regid, String complectId, String count, HttpServletRequest request)
            throws JSONException, NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into pharmnetcomplectrow  (regid,complectid_id,count) VALUES  ("+regid+","+complectId+","+count+")");
        //System.out.println(sql.toString());
        service.executeUpdateNativeSql(sql.toString());
        return 1;
    }

    public String viewComplect(String medserviceId,String count, HttpServletRequest aRequest) throws NamingException {

        if(count==null || count.equals("")) count="1";
        Collection<WebQueryResult> res = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select vg.drug||'('||vg.form||')' as dr, cast(pcr.count as text) as count from pharmnetcomplectrow pcr \n" +
                        "left join vocgoods vg on vg.regid = pcr.regid\n" +
                        "where complectid =(select id from pharmnetcomplect  where medservice_id = "+medserviceId+")");
        StringBuilder sql = new StringBuilder();

        sql.append("<table class='tabview sel tableArrow' border='1'><tbody><tr>");
        sql.append("<th class='idetag tagnameCol'>Наименование</th><th class='idetag tagnameCol'>Количество</th></tr>");
        for (WebQueryResult wqr: res) {

            sql.append("<tr><td>");
            sql.append(wqr.get1());
            sql.append("</td><td>");
            String s = (String) wqr.get2();
            sql.append( Float.valueOf(s) * Float.valueOf(count));
            sql.append("</td></tr>");
        }
        sql.append("</tbody></table>");
        return sql.toString();
    }

    public String createOutcomeByMedservice(String medserviceId, String count, String medcaseId, HttpServletRequest aRequest)
            throws NamingException {


        Collection<WebQueryResult> res = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select count, regid from pharmnetcomplectrow  where complectid =" +
                        "(select id from pharmnetcomplect  where medservice_id = "+medserviceId+")");

        List<PharmnetComplectRowEntity> pharmnetComplectRowEntityList = new ArrayList<PharmnetComplectRowEntity>();

        for (WebQueryResult wqr: res) {
            PharmnetComplectRowEntity pharmnetComplectRowEntity = new PharmnetComplectRowEntity();
            pharmnetComplectRowEntity.setCount((Float) wqr.get1());
            pharmnetComplectRowEntity.setRegid((Integer) wqr.get2());
            pharmnetComplectRowEntityList.add(pharmnetComplectRowEntity);
        }

        Collection<WebQueryResult> goodsl = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select id,qntost,regid from goodsleave  where storageid  =\n" +
                        "                (select id from pharmnetstorage where groupworkfuncid  =\n" +
                        "                (select group_id from workfunction where id=\n" +
                        "                (select workfunctionexecute_id from medcase where id= "+medcaseId+")))\n" +
                        "        and nextstate is null and (isend is null or isend = false)");


        List<GoodsLeaveEntity> goodsLeaveEntities = new ArrayList<GoodsLeaveEntity>();

        for (WebQueryResult wqr: goodsl) {
            GoodsLeaveEntity goodsLeaveEntity = new GoodsLeaveEntity();
            goodsLeaveEntity.setId((Long) wqr.get1());
            goodsLeaveEntity.setQntOst((Float) wqr.get1());
            goodsLeaveEntity.setRegId((Integer) wqr.get2());
            goodsLeaveEntities.add(goodsLeaveEntity);
        }

        Float qnt = Float.valueOf(count);
        for(PharmnetComplectRowEntity c:pharmnetComplectRowEntityList ) {
            for (GoodsLeaveEntity g: goodsLeaveEntities) {
                if(c.getRegid().equals(g.getRegId())){
                    if((c.getCount()*qnt) <= g.getQntOst()){

                        //списать (c.getCount()*qnt) с (g.getRegId())
                        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
                        StringBuilder sql = new StringBuilder();
                        //sql.append("insert into PharmnetOutcome  (regid,complectid_id,count) VALUES  ("+regid+","+complectId+","+count+")");
                        //System.out.println(sql.toString());
                        service.executeNativeSql(sql.toString());
                    }
                }
            }
        }
        return "";
    }
}
