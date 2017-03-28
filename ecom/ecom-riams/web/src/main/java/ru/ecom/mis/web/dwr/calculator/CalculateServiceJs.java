package ru.ecom.mis.web.dwr.calculator;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

/**
 * Сервис калькулятора
 * @author rkurbanov
 */
public class CalculateServiceJs {
    
    
    
    
    // Создание. Создание настроек для калькулятора.
    public void SetCalculatorSettings(String aJson, HttpServletRequest aRequest)
	    throws JSONException, NamingException {
	JSONObject obj = new JSONObject(aJson);
	JSONArray params = obj.getJSONArray("params");
	String Calcid = String.valueOf(obj.get("calculatorId"));
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
	
	
	StringBuilder sql = new StringBuilder();
	sql.append("DELETE FROM calculations where calculator_id='"+Calcid+"'");
	service.executeUpdateNativeSql(sql.toString());
	
	for (int i = 0; i < params.length(); i++) {
	    JSONObject param = (JSONObject) params.get(i);

	    String type = (String) param.get("type"), 
		    value = (String) param.get("value"),
		    comment = (String) param.get("comment"),
		    orderus = (String) param.get("orderus");

	    sql = new StringBuilder();
	    sql.append("insert into calculations (value,comment,orderus,type_id,calculator_id) ");
	    sql.append("values('" + value + "','" + comment + "','" + orderus+ "','" + type + "','" + Calcid + "')");
	    service.executeUpdateNativeSql(sql.toString());
	}

    }
    
    //Расчеты. Сохранить результат редактирования.
    public void SetCalculateResultEdit(String aId,String aResult,HttpServletRequest aRequest) throws NamingException
    {
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	StringBuilder sql = new StringBuilder();
	sql.append("Update calculationsresult  set result='"+aResult+"' where id='"+aId+"'");
	System.out.println("=== Debug: "+sql.toString());
	service.executeUpdateNativeSql(sql.toString()) ;
	
    }
    
    // Расчеты. Создать новый расчет.
    public void SetCalculateResultCreate(String aId, String aResult,HttpServletRequest aRequest) throws NamingException {
	IWebQueryService service = Injection.find(aRequest).getService(
		IWebQueryService.class);
	StringBuilder sql = new StringBuilder();
	sql.append(
		"INSERT INTO calculationsresult  (result,departmentmedcase_id,calculator_id)")
		.append("values ('" + aResult + "','" + aId + "','1')");
	System.out.println("=== Debug: " + sql.toString());
	service.executeUpdateNativeSql(sql.toString());

    }
    
    
    public String GetGenderAndAge(Long aId, HttpServletRequest aRequest)
	    throws NamingException {

	IWebQueryService service = Injection.find(aRequest).getService(
		IWebQueryService.class);
	StringBuilder sb = new StringBuilder();
	String sql = "SELECT (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)"
		+ " +(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)"
		+ " +(case when (cast(to_char(smo.dateStart, 'dd') as int)-cast(to_char(p.birthday, 'dd') as int)<0)"
		+ " then -1 else 0 end)<0) then -1 else 0 end)) as age ,  vs.id as sex"
		+ " from medcase smo"
		+ " left join patient p on p.id = smo.patient_id "
		+ " left join vocsex vs on p.sex_id = vs.id "
		+ "  where smo.id =" + aId;
	Collection<WebQueryResult> res = service.executeNativeSql(sql);
	for (WebQueryResult wqr : res) {
	    sb.append("" + wqr.get1());
	    sb.append("|" + wqr.get2());
	}
	return sb.toString();
    }
    
}