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

    /**Создание списания*/
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

    /**Создание комплекта*/
    public Integer createComplectRow(String regid, String complectId, String count, HttpServletRequest request)
            throws JSONException, NamingException {
        IWebQueryService service = Injection.find(request).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into pharmnetcomplectrow  (regid,complectid_id,count) VALUES  ("+regid+","+complectId+","+count+")");
        //System.out.println(sql.toString());
        service.executeUpdateNativeSql(sql.toString());
        return 1;
    }

    private List<GoodsLeaveEntity> getGoods(String medcaseId,HttpServletRequest aRequest) throws NamingException {

        Collection<WebQueryResult> goodsl = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select cast(qntost as text),regid from goodsleave  where storageid  =\n" +
                " (select id from pharmnetstorage where groupworkfuncid  =\n" +
                " (select group_id from workfunction where id=\n" +
                " (select workfunctionexecute_id from medcase where id= "+medcaseId+")))\n" +
                " and nextstate is null and (isend is null or isend = false)");


        List<GoodsLeaveEntity> goodsLeaveEntities = new ArrayList<GoodsLeaveEntity>();
        for (WebQueryResult wqr: goodsl) {
            GoodsLeaveEntity goodsLeaveEntity = new GoodsLeaveEntity();
            String temp = (String) wqr.get1();
            goodsLeaveEntity.setQntOst(Float.valueOf(temp));
            goodsLeaveEntity.setRegId((Integer) wqr.get2());
            goodsLeaveEntities.add(goodsLeaveEntity);
        }

        return goodsLeaveEntities;
    }
    /**Предварительный просмотр комплекта, что будет списано по услуге*/
    public String viewComplect(String medserviceId,String count,String medcaseId, HttpServletRequest aRequest) throws NamingException {

        List<GoodsLeaveEntity> goodsLeaves =getGoods(medcaseId,aRequest);

        if(count==null || count.equals("")) count="1";
        Collection<WebQueryResult> res = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select vg.drug||'('||vg.form||')' as dr, cast(pcr.count as text) as count, pcr.regid from pharmnetcomplectrow pcr \n" +
                        "left join vocgoods vg on vg.regid = pcr.regid\n" +
                        "where complectid =(select id from pharmnetcomplect  where medservice_id = "+medserviceId+")");
        StringBuilder sql = new StringBuilder();

        List<GoodsLeaveEntity> goodsOuts = new ArrayList<GoodsLeaveEntity>();

        for (WebQueryResult wqr: res) {
            GoodsLeaveEntity goodsOut = new GoodsLeaveEntity();
            goodsOut.setSeria(String.valueOf(wqr.get1()));
            String s = (String) wqr.get2();
            goodsOut.setQntOst(Float.valueOf(s) * Float.valueOf(count));
            goodsOut.setRegId((Integer) wqr.get3());
            goodsOuts.add(goodsOut);
        }

        sql.append("<table class='tabview sel tableArrow' border='1'><tbody><tr>");
        sql.append("<th class='idetag tagnameCol'>Наименование</th><th class='idetag tagnameCol'>Количество</th>" +
                "<th class='idetag tagnameCol'>Кол-во на скаладе</th><th class='idetag tagnameCol'>Списание</th></tr>");
        for(GoodsLeaveEntity gout: goodsOuts) {

            sql.append("<tr><td>");
            sql.append(gout.getSeria());
            sql.append("</td><td>");
            sql.append(gout.getQntOst());
            sql.append("</td><td>");
            boolean a = false;
            Float c= null;
            for (GoodsLeaveEntity gleave : goodsLeaves) {
                if(gout.getRegId().equals(gleave.getRegId())){
                    a=true;
                    c=gleave.getQntOst();
                    break;
                }else {
                    a=false;
                }
            }

            if(a) sql.append(c+"</td><td>+");
            else sql.append("-</td><td>-");
        }
        sql.append("</td></tr>");
        sql.append("</tbody></table>");
        return sql.toString();
    }

    public String createOutcomeByMedservice(String medserviceId, String amountMedserv, String medcaseId, String username, HttpServletRequest aRequest)
            throws NamingException {


        Collection<WebQueryResult> res = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select cast(count as text), regid from pharmnetcomplectrow  where complectid =" +
                        "(select id from pharmnetcomplect  where medservice_id = "+medserviceId+")");

        List<PharmnetComplectRowEntity> pharmnetComplectRowEntityList = new ArrayList<PharmnetComplectRowEntity>();

        for (WebQueryResult wqr: res) {
            PharmnetComplectRowEntity pharmnetComplectRowEntity = new PharmnetComplectRowEntity();
            String temp = (String) wqr.get1();
            pharmnetComplectRowEntity.setCount(Float.valueOf(temp));
            pharmnetComplectRowEntity.setRegid((Integer) wqr.get2());
            pharmnetComplectRowEntityList.add(pharmnetComplectRowEntity);
        }

        Collection<WebQueryResult> goodsl = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select id, cast(qntost as text),regid from goodsleave  where storageid  =\n" +
                        "                (select id from pharmnetstorage where groupworkfuncid  =\n" +
                        "                (select group_id from workfunction where id=\n" +
                        "                (select workfunctionexecute_id from medcase where id= "+medcaseId+")))\n" +
                        "        and nextstate is null and (isend is null or isend = false)");


        List<GoodsLeaveEntity> goodsLeaveEntities = new ArrayList<GoodsLeaveEntity>();

        for (WebQueryResult wqr: goodsl) {
            GoodsLeaveEntity goodsLeaveEntity = new GoodsLeaveEntity();
            Integer tmp = (Integer) wqr.get1();
            goodsLeaveEntity.setId(tmp);
            String t = (String) wqr.get2();
            goodsLeaveEntity.setQntOst(Float.valueOf(t));
            goodsLeaveEntity.setRegId((Integer) wqr.get3());
            goodsLeaveEntities.add(goodsLeaveEntity);
        }

        //StringBuilder result =new StringBuilder();
        Float qnt = Float.valueOf(amountMedserv);
        for(PharmnetComplectRowEntity c:pharmnetComplectRowEntityList ) {
            for (GoodsLeaveEntity g: goodsLeaveEntities) {
                if(c.getRegid().equals(g.getRegId())){

                    Float countOut = c.getCount()*qnt;
                    if((countOut) <= g.getQntOst()){

                        //result.append(c.get)
                        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
                        StringBuilder sql = new StringBuilder();
                        sql.append("select PharmnetOutcomebyMedservice ("+g.getId()+","+medcaseId+",'"+username+"',"+countOut+","+medserviceId+")");
                        service.executeNativeSql(sql.toString());
                    }
                }
            }
        }
        return "";
    }

    public String getBalance(String storId,HttpServletRequest aRequest)
            throws NamingException {
        Collection<WebQueryResult> goodsl = Injection.find(aRequest)
                .getService(IWebQueryService.class)
                .executeNativeSql("select gl.regid, vg.drug,vg.form, cast(gl.qntost as text),cast(gl.uqntost as text),to_char(gl.srokg,'dd.MM.yyyy') as srok from goodsleave gl\n" +
                        "left join vocgoods vg on vg.regid = gl.regid\n" +
                        "where gl.storageid = "+storId);

        StringBuilder sql = new StringBuilder();

        sql.append("<table class='tabview sel tableArrow' border='1'><tbody><tr>");
        sql.append("<th class='idetag tagnameCol'>Id</th>" +
                "<th class='idetag tagnameCol'>Наименование</th>" +
                "<th class='idetag tagnameCol'>Форма</th>" +
                "<th class='idetag tagnameCol'>Количество</th>" +
                "<th class='idetag tagnameCol'>Кол-во упаковок</th>" +
                "<th class='idetag tagnameCol'>Срок годности</th>" +
                "</tr>");

        for (WebQueryResult wqr: goodsl) {

            sql.append("<tr><td>");
            sql.append(wqr.get1()+"</td><td>");
            sql.append(wqr.get2()+"</td><td>");
            sql.append(wqr.get3()+"</td><td>");
            sql.append(wqr.get4()+"</td><td>");
            sql.append(wqr.get5()+"</td><td>");
            sql.append(wqr.get6()+"</td>");
            sql.append("</td></tr>");
        }

        sql.append("</tbody></table>");
        return sql.toString();
    }
}
