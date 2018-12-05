package ru.ecom.mis.web.dwr.calculator;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

/**
 * Сервис калькулятора
 * @author rkurbanov
 */
public class CalculateServiceJs {
    
    
    // Создание. Создание функционала калькулятора.
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
		    orderus = (String) param.get("orderus"),
            note = (String) param.get("note");

	    sql = new StringBuilder();
	    sql.append("insert into calculations (value,comment,orderus,type_id,calculator_id,note) ");
	    sql.append("values('" + value + "','" + comment + "','" + orderus+ "','" + type + "','" + Calcid + "','" + note + "')");
	    service.executeUpdateNativeSql(sql.toString());
	}

    }
    //Milamesher 21112018 Создание интерпретаций результата
	public void SetInterpretationsSettings(String aJson, HttpServletRequest aRequest) throws JSONException, NamingException {
		JSONObject obj = new JSONObject(aJson);
		JSONArray params = obj.getJSONArray("params");
		String Calcid = String.valueOf(obj.get("calculatorId"));
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);


		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM interpretation where calculator_id='"+Calcid+"'");
		service.executeUpdateNativeSql(sql.toString());

		for (int i = 0; i < params.length(); i++) {
			JSONObject param = (JSONObject) params.get(i);

			String value = (String) param.get("value"),
					result = (String) param.get("result");

			sql = new StringBuilder();
			sql.append("insert into interpretation (value,result,calculator_id) ");
			sql.append("values('" + value + "','" + result + "','" + Calcid + "')");
			service.executeUpdateNativeSql(sql.toString());
		}

	}
    
    //Расчет. Получение функционала калькулятора для расчета.
    public String GetSettingsById(String aId, HttpServletRequest aRequest) throws NamingException, JSONException{

	StringBuilder sql = new StringBuilder();
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

	if (aId.equals("")) aId="0";

	sql.append("SELECT cs.value,cs.comment,cs.type_id,cs.note from calculations cs ");
	sql.append("left join calculator c on c.id = cs.calculator_id ");
	sql.append("where c.id = '"+aId+"' order by orderus ");
	
	Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
	
	JSONObject resultJson = new JSONObject();
	JSONArray arr = new JSONArray();

	for (WebQueryResult wqr : res) {
	    
	    resultJson = new JSONObject();
	    resultJson.put("Value",new String(wqr.get1().toString()));
	    resultJson.put("Comment",new String(wqr.get2().toString()));
	    resultJson.put("Type_id",new String(wqr.get3().toString()));
		resultJson.put("Note",new String(wqr.get4()!=null? wqr.get4().toString():""));

	    arr.put(resultJson);
	}
	return arr.toString();
    }
    
    
    //Расчеты. Сохранить результат редактирования.
    public void SetCalculateResultEdit(String aId,String aResult,HttpServletRequest aRequest) throws NamingException
    {
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	StringBuilder sql = new StringBuilder();
	sql.append("Update calculationsresult  set result='"+aResult+"' where id='"+aId+"'");
	//System.out.println("=== Debug: "+sql.toString());
	service.executeUpdateNativeSql(sql.toString()) ;
    }
    
    // Расчеты. Создать новый расчет.
    public void SetCalculateResultCreate(String aId, String aResult, String aCalcId, HttpServletRequest aRequest) throws NamingException {

		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sql = new StringBuilder();

		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
		//System.out.println(date.format(d)); //25.02.2013

		sql.append("INSERT INTO calculationsresult  (result,departmentmedcase_id,calculator_id,resdate)");
		sql.append("values ('" + aResult + "','" + aId + "','" + aCalcId + "','" + date.format(d).toString() + "')");
		//System.out.println("=== Debug: " + sql.toString());
		service.executeUpdateNativeSql(sql.toString());
		//Milamesher 22112018 создание дневника о вычислении
		sql = new StringBuilder();
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
		Collection<WebQueryResult> res = service.executeNativeSql
				("select name||' ('||comment||')', case when creatediary=true then '1' else '0' end from calculator where id=" + aCalcId);
		String result = "";
		Boolean checkCreateDiary = false;
		if (res.size() > 0) {
			result = (res.iterator().next().get1() != null) ? res.iterator().next().get1().toString() + ":\n" + aResult : aResult;
			checkCreateDiary = (res.iterator().next().get2().equals("1"));
		}
		if (checkCreateDiary) {
			String workFunction = service.executeNativeSql("select wf.id from secuser su left join workfunction wf on wf.secuser_id=su.id " +
					"where su.login='" + username + "'").iterator().next().get1().toString();
			sql.append("INSERT INTO diary(dtype,  \"time\", date, record, username, dateregistration, isdischarge, \n" +
					"timeregistration,medcase_id, specialist_id,journaltext, templateprotocol, disableedit)");
			sql.append("values ('Protocol', current_time, current_date,'" + result + "','" + username + "', current_date,false, current_time,"
					+ aId + "," + workFunction + ", '', 0, false)");
			service.executeUpdateNativeSql(sql.toString());
		}
	}
    
    
    //Получить пол
    public String getGender(Long aId, HttpServletRequest aRequest)
	    throws NamingException {
	
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
	StringBuilder sql = new StringBuilder();
	
	sql.append(" SELECT vs.id as sex ");
	sql.append(" from medcase smo ");
	sql.append(" left join patient p on p.id = smo.patient_id  ");
	sql.append(" left join vocsex vs on p.sex_id = vs.id ");
	sql.append(" where smo.id ='"+aId.toString()+"'");
	
	Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
	
	
	StringBuilder sb = new StringBuilder();
	for (WebQueryResult wqr : res) {
	    sb.append("" + wqr.get1());
	}
	
	return sb.toString();
	
    }

    //Получить возраст
    public String getAge(Long aId, HttpServletRequest aRequest)
	    throws NamingException {

	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) ");
	sql.append("+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) ");
	sql.append("+(case when (cast(to_char(smo.dateStart, 'dd') as int)-cast(to_char(p.birthday, 'dd') as int)<0) ");
	sql.append("then -1 else 0 end)<0) then -1 else 0 end)) as age ");
	sql.append("from medcase smo ");
	sql.append("left join patient p on p.id = smo.patient_id ");
	sql.append("where smo.id ='" + aId.toString()+"'");
	
	Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
	
	StringBuilder sb = new StringBuilder();
	for (WebQueryResult wqr : res) {
	    sb.append("" + wqr.get1());
	}
	return sb.toString();
    }
    
    public String getCountDiary(Long aId, HttpServletRequest aRequest) throws NamingException{
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
	StringBuilder sql = new StringBuilder();
	sql.append("select count(id) from diary where medcase_id='"+aId.toString()+"'");
	Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
	
	StringBuilder result = new StringBuilder();
	for (WebQueryResult wqr : res) {
	    result.append("" + wqr.get1());
	}
	
	return result.toString();
    }

    //Milamesher #127 получить интепретацию результата
	public String getInterpretation(Long cId, String val, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> res = service.executeNativeSql("select result from interpretation where value='"+val+"' and calculator_id='"+cId+"' limit 1");
		return (res.isEmpty())? "": res.iterator().next().get1().toString();
	}

	//Milamesher #127 есть ли тэг для калькулятора
	public String getCalcTagView(Long cId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		Collection<WebQueryResult> res = service.executeNativeSql("select tag from calculator where id="+cId);
		return (!res.isEmpty() && res.iterator().next().get1()!=null) ? res.iterator().next().get1().toString() : "";
	}
}