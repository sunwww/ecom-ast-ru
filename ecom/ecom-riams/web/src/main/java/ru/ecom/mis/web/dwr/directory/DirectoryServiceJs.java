package ru.ecom.mis.web.dwr.directory;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.jboss.ejb3.entity.HibernateSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

/**
 * Сервис справочника
 * @author rkurbanov
 */
public class DirectoryServiceJs {
    
    // Добавление записи
    public void setEntryAndNumber(String aJson, HttpServletRequest aRequest) throws JSONException, NamingException{
	
	//System.out.println("=== Debug: "+aJson);
	JSONObject obj = new JSONObject(aJson);
	JSONArray params= null;
	if(obj.getJSONArray("numbers")!=null) params = obj.getJSONArray("numbers");
	
	String department = String.valueOf(obj.get("department"));
	String person = String.valueOf(obj.get("person"));
	String insideNumber = String.valueOf(obj.get("insideNumber"));
	String comment = String.valueOf(obj.get("comment"));
	
	StringBuilder sql = new StringBuilder();
	StringBuilder id =EntryExist(aRequest,department,insideNumber,person,comment);
	
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

	//System.out.println("=== ret ID: "+id);
	
	for (int i = 0; i < params.length(); i++) {
	    JSONObject param = (JSONObject) params.get(i);

	    String typeNumber = (String) param.get("typeNumber"), 
		    number = (String) param.get("number");
	    
	    System.out.println("=== Debug: "+number+" | "+typeNumber);
	   sql = new StringBuilder();
	   sql.append("insert into telephonenumber (telnumber,entry_id,typenumber_id) ");
	   sql.append("values('" + number + "'," + id + "," + typeNumber+ ")");
	   service.executeUpdateNativeSql(sql.toString());
	}
	
    }
    
    private StringBuilder EntryExist(HttpServletRequest aRequest,
	    String department, String insideNumber, String person,
	    String comment) throws NamingException {

	IWebQueryService service = Injection.find(aRequest).getService(
		IWebQueryService.class);
	StringBuilder sql = new StringBuilder();

	sql.append("select id from entry where insidenumber='"+insideNumber+"' and department_id=" + department);
	Collection<WebQueryResult> res = service.executeNativeSql(sql
		.toString());

	StringBuilder id = new StringBuilder();
	for (WebQueryResult wqr : res) {
	    id.append("" + wqr.get1());
	}

	if (id.equals("") || id.toString().equals("") || id == null) {

	    sql = new StringBuilder();
	    sql.append("INSERT INTO entry (comment,insidenumber,");
	    if(person.equals("") || person==null){
		sql.append("department_id)")
		.append("VALUES ('" + comment + "','" + insideNumber + "'," + department + ") returning id");
		}else{
		    sql.append("department_id)")
		    .append("VALUES ('" + comment + "','" + insideNumber + "',"+ person + "," + department + ") returning id");
		}

	    res = service.executeNativeSql(sql.toString());

	    id = new StringBuilder();
	    for (WebQueryResult wqr : res) {
		id.append("" + wqr.get1());
	    }

	    return id;
	} else return id;
    } 
}