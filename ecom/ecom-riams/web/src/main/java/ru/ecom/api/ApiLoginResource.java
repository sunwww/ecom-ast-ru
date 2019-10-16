package ru.ecom.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Path("login")
public class ApiLoginResource {

    @GET
    @Path("getTimurBiohim")
    @Produces(MediaType.APPLICATION_JSON)
    public String makeTimurBiohim(@Context HttpServletRequest aRequest, @QueryParam("medcaseId") Long aMedcaseId, @QueryParam("token") String token) throws NamingException, SQLException {
        JSONObject ret = new JSONObject();
        ApiUtil.login(token,aRequest);
        String sql = "select t.id as id" +
                " ,max(t.p247) as Bldl\n" +
                ",max(t.p248) as Bbelok\n" +
                ",max(t.p243) as Bchol\n" +
                ",max(t.p244) as Bglu\n" +
                ",max(t.p276) as KaBLD\n" +
                ",max(t.p279) as HbA1c\n" +
                ",max(t.p257) as Burea\n" +
                ",max(t.p277) as Na\n" +
                ",max(t.p252) as Balt\n" +
                ",max(t.p246) as Bbilt\n" +
                ",max(t.p399) as Bmncit\n" +
                ",max(t.p397) as Beos\n" +
                ",max(t.p396) as Bsegneiro\n" +
                ",max(t.p382) as Bhgb\n" +
                ",max(t.p383) as Brbc\n" +
                ",max(t.p385) as Bhct\n" +
                ",max(t.p387) as Bptl\n" +
                ",max(t.p388) as Bwbc\n" +
                ",max(t.p400) as Blym\n" +
                ",max(t.p258) as Bcrea\n" +
                ",max(t.p316) as Msw\n" +
                ",max(t.p502) as Mmucs \n" +
                ",max(t.p524) as Mglu\n" +
                ",max(t.p789) as Mbil\n" +
                ",max(t.p481) as MrpH\n" +
                ",max(t.p480) as Mpro \n" +
                ",max(t.p487) as Mbact\n" +
                ",max(t.p549) as Mket\n" +
                ",max(t.p486) as Muro\n" +
                ",max(t.p781) as Mrbc\n" +
                ",max(t.p784) as Mleu\n" +
                ",max(t.p113) as aptt\n" +
                ",max(t.p114) as PT\n" +
                ",max(t.p126) as UFB\n" +
                ",max(t.p116) as tbt\n" +
                ",max(t.p702) as Bmalb\n" +
                ",max(t.p253) as Bast \n" +
                ",max(t.p256) as Btrig\n" +
                ",max(t.p254) as Bhdl\n" +
                ",max(t.p38) as ESR\n" +
                ",max(t.p249) as Balb\n" +
                "from (\n" +
                "select pat.id as id, pat.id as fio" +
                " ,case when par.id=247 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p247\n" +
                ",case when par.id=248 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p248\n" +
                ",case when par.id=243 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p243\n" +
                ",case when par.id=244 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p244\n" +
                ",case when par.id=276 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p276\n" +
                ",case when par.id=280 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p280\n" +
                ",case when par.id=756 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p756\n" +
                ",case when par.id=279 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p279\n" +
                ",case when par.id=257 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p257\n" +
                ",case when par.id=277 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p277\n" +
                ",case when par.id=252 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p252\n" +
                ",case when par.id=246 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p246\n" +
                ",case when par.id=408 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p408\n" +
                ",case when par.id=405 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p405\n" +
                ",case when par.id=406 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p406\n" +
                ",case when par.id=409 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p409\n" +
                ",case when par.id=393 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p393\n" +
                ",case when par.id=395 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p395\n" +
                ",case when par.id=394 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p394\n" +
                ",case when par.id=407 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p407\n" +
                ",case when par.id=399 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p399\n" +
                ",case when par.id=397 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p397\n" +
                ",case when par.id=396 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p396\n" +
                ",case when par.id=382 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p382\n" +
                ",case when par.id=383 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p383\n" +
                ",case when par.id=384 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p384\n" +
                ",case when par.id=385 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p385\n" +
                ",case when par.id=386 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p386\n" +
                ",case when par.id=387 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p387\n" +
                ",case when par.id=388 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p388\n" +
                ",case when par.id=400 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p400\n" +
                ",case when par.id=398 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p398\n" +
                ",case when par.id=410 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p410\n" +
                ",case when par.id=411 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p411\n" +
                ",case when par.id=401 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p401\n" +
                ",case when par.id=403 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p403\n" +
                ",case when par.id=402 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p402\n" +
                ",case when par.id=412 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p412\n" +
                ",case when par.id=413 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p413\n" +
                ",case when par.id=836 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p836\n" +
                ",case when par.id=258 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p258\n" +
                ",case when par.id=501 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p501\n" +
                ",case when par.id=117 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p117\n" +
                ",case when par.id=120 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p120\n" +
                ",case when par.id=178 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p178\n" +
                ",case when par.id=316 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p316\n" +
                ",case when par.id=502 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p502\n" +
                ",case when par.id=524 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p524\n" +
                ",case when par.id=789 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p789\n" +
                ",case when par.id=478 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p478\n" +
                ",case when par.id=479 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p479\n" +
                ",case when par.id=777 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p777\n" +
                ",case when par.id=779 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p779\n" +
                ",case when par.id=780 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p780\n" +
                ",case when par.id=481 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p481\n" +
                ",case when par.id=480 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p480\n" +
                ",case when par.id=487 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p487\n" +
                ",case when par.id=549 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p549\n" +
                ",case when par.id=486 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p486\n" +
                ",case when par.id=19 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p19\n" +
                ",case when par.id=26 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p26\n" +
                ",case when par.id=11 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p11\n" +
                ",case when par.id=521 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p521\n" +
                ",case when par.id=27 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p27\n" +
                ",case when par.id=781 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p781\n" +
                ",case when par.id=784 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p784\n" +
                ",case when par.id=113 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p113\n" +
                ",case when par.id=114 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p114\n" +
                ",case when par.id=126 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p126\n" +
                ",case when par.id=699 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p699\n" +
                ",case when par.id=700 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p700\n" +
                ",case when par.id=116 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p116\n" +
                ",case when par.id=702 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p702\n" +
                ",case when par.id=253 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p253\n" +
                ",case when par.id=171 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p171\n" +
                ",case when par.id=173 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p173\n" +
                ",case when par.id=172 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p172\n" +
                ",case when par.id=488 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p488\n" +
                ",case when par.id=256 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p256\n" +
                ",case when par.id=254 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p254\n" +
                ",case when par.id=38 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p38\n" +
                ",case when par.id=249 then\n" +
                "list(case when fipr.valuebd is not null then round(fipr.valuebd,2)::varchar else fipr.valuetext end)\n" +
                "else null end as p249\n" +
                "from medcase slsinner\n" +
                "left join medcase slo on slo.parent_id=slsinner.id and slo.dtype='DepartmentMedCase'\n" +
                "left join prescriptionlist pl on pl.medcase_id=slsinner.id or pl.medcase_id=slo.id\n" +
                "left join prescription p on p.prescriptionlist_id=pl.id\n" +
                "left join medcase msmc on msmc.id=p.medcase_id\n" +
                "left join diary d on msmc.id=d.medcase_id \n" +
                "left join diagnosis ds on ds.medcase_id=slsinner.id or ds.medcase_id=slo.id\n" +
                "left join vocidc10 idc on idc.id=ds.idc10_id\n" +
                "left join medservice ms on ms.id=p.medservice_id \n" +
                "left join forminputprotocol fipr on fipr.docprotocol_id=d.id\n" +
                "left join parameter par on par.id=fipr.parameter_id\n" +
                "left join patient pat on pat.id=slo.patient_id\n" +
                "left join templateprotocol tmpl on tmpl.medservice_id=ms.id\n" +
                "where slsinner.id="+aMedcaseId+" and slsinner.datefinish is not null \n" +
                "and slsinner.dtype='HospitalMedCase' and idc.code like 'E11%'\n" +
                "and d.dateregistration is not null\n" +
                "and (ms.code like 'A09.05.023%' or ms.code like 'A09.05.026' or ms.code like 'A09.05.017'\n" +
                "or ms.code like 'A09.05.020' or ms.code like 'A09.05.021' or ms.code like 'A09.05.011'\n" +
                "or ms.code like 'A09.05.041' or ms.code like 'A09.05.004' or ms.code like 'B03.016.003'\n" +
                "or ms.code like 'A09.05.083' or ms.code like 'A12.05.001' or ms.code like 'A09.05.042'\n" +
                "or ms.code like 'A09.05.028' or ms.code like 'A09.05.031' or ms.code like 'B03.016.006'\n" +
                "or ms.code like 'A09.05.010' or ms.code like 'A09.05.025' or ms.code like 'B03.005.006'\n" +
                "or ms.code like 'A09.05.030' or ms.code like 'B03.016.014' or ms.code like 'A09.28.003.001')\n" +
                " group by pat.id,par.id,d.dateregistration\n" +
                " order by d.dateregistration) as t\n" +
                " group by t.id,t.fio\n";

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        JSONArray array = new JSONArray(service.executeSqlGetJson(sql,100));
        ret.put("medcaseId", aMedcaseId).put("data",array);
        sql = "select p.code as code," +
                " uv.cntball as value" +
                " from medcase sls" +
                " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase'" +
                " left join assessmentCard ac on ac.medcase_id = slo.id" +
                " left join AssessmentCardTemplate tmpl on tmpl.id=ac.template" +
                " left join forminputprotocol fip on fip.assessmentCard=ac.id" +
                " left join parameter p on p.id=fip.parameter_id" +
                " left join uservalue uv on uv.id=valuevoc_id" +
                " where sls.id="+aMedcaseId+" and tmpl.code in ('checkDiabetes2','checkDiabetes')";
        array = new JSONArray(service.executeSqlGetJson(sql,100));
        ret.put("parameters", array);
        return ret.toString();
        //return ret.toString();
    }

    @GET
    @Path("makeTimurHappy")
    @Produces(MediaType.APPLICATION_JSON)
    /*Найдем информация для Тимура. ТЕСТ*/
    public String makeTimurHappy(@Context HttpServletRequest aRequest, @QueryParam("patientId") Long aPatientId, @QueryParam("token") String token) throws NamingException {
        ApiUtil.login(token,aRequest);
        String sql = "select pat.lastname , pat.birthday, sex.name , ss.weight , ss.height , ss.imt , sls.datestart,  sls.id" +
                " from patient pat " +
                " left join vocsex sex on sex.id=pat.sex_id " +
                " left join medcase sls on sls.patient_id = pat.id and sls.dtype='HospitalMedCase' and sls.deniedhospitalizating_id is null" +
                " left join statisticstub ss on ss.medcase_id = sls.id" +
                " where pat.id="+aPatientId+" and sls.id is not null" +
                " order by sls.datestart desc";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> patientInfo = service.executeNativeSql(sql,1);
        JSONObject ret = new JSONObject();
        if (!patientInfo.isEmpty()) {
            WebQueryResult wqr = patientInfo.iterator().next();
            ret.put("lastname", toS(wqr.get1()));
            ret.put("sex", toS(wqr.get3()));
            ret.put("birthday",toS(wqr.get2()));
            ret.put("weight",toS(wqr.get4()));
            ret.put("height",toS(wqr.get5()));
            ret.put("imt",toS(wqr.get6()));
            String medcaseId = toS(wqr.get8());
            sql = "select mc.id, list(ms.code) as cde, list(ms.name) as nme , to_char(mc.datestart,'dd.MM.yyyy') as f4_dat " +
                    " ,(select json_array(list('value#'||fip.valuebd||'#name#'||par.name||'#code#'||coalesce(par.externalcode, par.code)),'#')) as f5_cd" +
                    " , to_char(mc.timeexecute, 'HH24:MI') as f6_startTime" +
                    " from medcase sls " +
                    " left join medcase slo on slo.parent_id = sls.id and slo.dtype='DepartmentMedCase'" +
                    " left join prescriptionList pl on pl.medcase_id = slo.id or pl.medcase_id = sls.id" +
                    " left join prescription p on p.prescriptionlist_id = pl.id " +
                    " left join medservice ms on ms.id=p.medservice_id" +
                    " left join vocservicetype vst on vst.id=ms.servicetype_id" +
                    " left join medcase mc on mc.id=p.medcase_id" +
                    " left join diary d on d.medcase_id=mc.id" +
                    " left join FormInputProtocol fip on fip.docprotocol_id = d.id" +
                    " left join parameter par on par.id=fip.parameter_id" +
                    " where sls.id = "+medcaseId+" and sls.dtype='HospitalMedCase' and ms.id is not null and mc.datestart is not null" +
                    " group by mc.id" +
                    " order by mc.datestart desc";
            Collection<WebQueryResult> surveyInfo = service.executeNativeSql(sql);
           // ArrayList<String> servicesCode = new ArrayList<>();
            JSONArray ser = new JSONArray();
            for (WebQueryResult r : surveyInfo) {
                String sCode = toS(r.get2());
            //    if (!servicesCode.contains(sCode)) {
                    JSONObject s = new JSONObject();
                    s.put("serviceCode", sCode);
                    s.put("serviceName", toS(r.get3()));
                    s.put("serviceDate", toS(r.get4()));
                    s.put("serviceTime", toS(r.get6()));
                    s.put("result", new JSONArray(toS(r.get5())));
                //    servicesCode.add(sCode);
                    ser.put(s);
            //    }
            }
            ret.put("servicesArray", ser);
            ret.put("status", "ok");
        } else {
            ret.put("status", "error");
            ret.put("errorName", "Не найдено госпитализаций по пациенту или типа того");
        }


        return ret.toString();

    }

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
        if (arr.length()>0) {
            for (int i=0;i<arr.length();i++) {
                JSONObject obj = arr.getJSONObject(i);
                ret.append("newBornSize=").append(arr.length()).append(";birthDate=").append(obj.getString("date")).append(";birthMale=").append(obj.getString("male")).append(";birthFeMale=").append(obj.getString("female")).append("\r");
            }
        }
        else
            ret.append("newBornSize=").append("1").append(";birthDate=").append(tmpDate).append(";birthMale=").append("0").append(";birthFeMale=").append("0").append("\r");
        return ret.toString();
    }

    private String toS(Object o ){
        return o!=null ? o.toString() : "";
    }
}
