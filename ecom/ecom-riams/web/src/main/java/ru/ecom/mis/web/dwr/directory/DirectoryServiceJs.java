package ru.ecom.mis.web.dwr.directory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.ecs.html.LI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.directory.Entry;
import ru.ecom.mis.ejb.domain.directory.TelephoneNumber;
import ru.ecom.mis.ejb.domain.directory.voc.VocTypeNumber;
import ru.ecom.mis.ejb.service.directory.IDirectoryService;
import ru.ecom.web.util.Injection;

/**
 * Сервис справочника
 * @author rkurbanov
 */
public class DirectoryServiceJs {


	private boolean isNotNullObj(JSONObject obj, String Alias){
		if(obj.has(Alias)){
            return true;
        }else return false;
	}


	private void SQLupdate(HttpServletRequest aRequest, String SQLUpdateString){
		try {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
			StringBuilder sql = new StringBuilder();
			sql.append(SQLUpdateString);
			service.executeUpdateNativeSql(sql.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void deleteEnryRecord(String EntryId,HttpServletRequest aRequest){
		SQLupdate(aRequest,"delete from telephonenumber where entry_id = "+EntryId);
		SQLupdate(aRequest,"delete from entry where id = "+EntryId);
		SQLupdate(aRequest,"delete from department   where id = (select department_id from entry  where id = "+EntryId+")");
	}
	public boolean updateEntryEdit(String aJson,HttpServletRequest aRequest) throws NamingException, JSONException {

		JSONObject obj = new JSONObject(aJson);
		JSONArray params= null;
		JSONArray params2= null;

		Long buildingId,misLpuId,buildingLevelId,departmentId;
		if(isNotNullObj(obj,"buildingId") && isNotNullObj(obj,"buildingLevelId")
				&& isNotNullObj(obj,"misLpuId")){
			buildingId = Long.valueOf(String.valueOf(obj.get("buildingId")));
			misLpuId = Long.valueOf(String.valueOf(obj.get("misLpuId")));
			buildingLevelId = Long.valueOf(String.valueOf(obj.get("buildingLevelId")));
			departmentId = Long.valueOf(String.valueOf(obj.get("departmentId")));

			SQLupdate(aRequest,"update department " +
			"set building_id ="+buildingId+", " +
			"department_id = "+misLpuId+", " +
			"buildinglevel_id="+buildingLevelId+" where id="+departmentId);
		}else return false;

		String entryId="";
		if(isNotNullObj(obj,"comment") && isNotNullObj(obj,"internalNumber")){

			entryId = String.valueOf(obj.get("entryId"));
			String comment = String.valueOf(obj.get("comment"));
			String internalNumber = String.valueOf(obj.get("internalNumber"));
			String personId;
			if(isNotNullObj(obj,"personId")){
				personId =String.valueOf(obj.get("personId"));
			}else personId = "null";
			SQLupdate(aRequest,"update entry " +
					"set comment ='"+comment+"', " +
					"insidenumber ='"+internalNumber+"', " +
					"person_id="+personId+" where id="+entryId);

		}else return false;
		if(obj.has("delId")) params = obj.getJSONArray("delId");
		String delIds="";boolean tick=false;
		for (int i = 0; i < params.length(); i++) {
			JSONObject param = (JSONObject) params.get(i);
			Long TelNumberIdDel = Long.valueOf((String) param.get("TelNumberIdDel"));
			if(tick==true)delIds+=",";
			delIds+=(String) param.get("TelNumberIdDel");
			tick=true;
		}
		if(delIds!=null && !delIds.equals("")) {
			SQLupdate(aRequest, "delete from telephonenumber where id in(" + delIds + ")");
		}
		insertTelephoneNumbers(aRequest,obj,Long.valueOf(entryId));
		return true;
	}

	private void insertTelephoneNumbers(HttpServletRequest aRequest,JSONObject obj,Long entryId){
		JSONArray params= null;
		try {
			IDirectoryService service = Injection.find(aRequest).getService(IDirectoryService.class);
			if(obj.has("numbers")) params = obj.getJSONArray("numbers");
			for (int i = 0; i < params.length(); i++) {
				JSONObject param = (JSONObject) params.get(i);
				Long typeNumber = Long.valueOf((String) param.get("typeNumber"));
				String number = (String) param.get("Number");
				service.setTelephoneNumber(number,entryId,typeNumber);
			}
		}catch (Exception e){e.printStackTrace();}
	}
	//Загрузка формы
	public String getEntryEdit(String entryId,HttpServletRequest aRequest) throws NamingException {

		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		StringBuilder sql = new StringBuilder();
		String JSON="";
		sql.append("select d.building_id, vb.name as build, d.buildinglevel_id , vbl.name as buildlevel,e.comment, \n" +
				"e.insidenumber, e.person_id, \n" +
				"p.lastname||' '||p.firstname||' '||p.middlename as fio,m.id as misLpu,e.department_id depId\n" +
				",m.name as depname from entry e \n" +
				"left join department d on d.id = e.department_id\n" +
				"left join mislpu m on m.id = d.department_id\n" +
				"left join vocbuilding vb on vb.id =d.building_id\n" +
				"left join vocbuildinglevel vbl on vbl.id =d.buildinglevel_id\n" +
				"left join workfunction wf on wf.id = e.person_id\n" +
				"left join worker w on w.id =wf.worker_id\n" +
				"left join patient p on p.id = w.person_id\n" +
				"where e.id = "+entryId);

		Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());

		for (WebQueryResult wqr : res) {

			JSON+="{\"buildingId\":\""+wqr.get1()+"\",";
			JSON+="\"building\":\""+wqr.get2()+"\",";
			JSON+="\"buildingLevelId\":\""+wqr.get3()+"\",";
			JSON+="\"buildingLevel\":\""+wqr.get4()+"\",";
			JSON+="\"comment\":\""+wqr.get5()+"\",";
			JSON+="\"insidenumber\":\""+wqr.get6()+"\",";
			JSON+="\"personId\":\""+wqr.get7()+"\",";
			JSON+="\"person\":\""+wqr.get8()+"\",";
			JSON+="\"depId\":\""+wqr.get9()+"\",";
			JSON+="\"departmentId\":\""+wqr.get10()+"\",";
			JSON+="\"department\":\""+wqr.get11()+"\",";
		}

		JSON+="\"numbers\":[";
		sql = new StringBuilder();
		sql.append("select tn.telnumber, tn.typenumber_id, vtn.name, tn.id from telephonenumber tn\n" +
				"left join voctypenumber vtn on vtn.id = tn.typenumber_id \n" +
				"where tn.entry_id= "+entryId);
		res = service.executeNativeSql(sql.toString());
		int chk=0;
		for (WebQueryResult wqr : res) {
			if(chk>0)JSON+=",";
			JSON+="{\"telnumber\":\""+wqr.get1()+"\",";
			JSON+="\"typeId\":\""+wqr.get2()+"\",";
			JSON+="\"typeName\":\""+wqr.get3()+"\",";
			JSON+="\"telephonenumberId\":\""+wqr.get4()+"\"}";
			chk++;
		}
		JSON+="]}";
		System.out.println(">>>>>>"+JSON);
		return JSON;
	}

	//Добавление полной записи (Отделение->Запись->Телефонные номера)
	public boolean setEntry(String aJson, HttpServletRequest aRequest) throws JSONException, NamingException{

		JSONObject obj = new JSONObject(aJson);
		JSONArray params= null;
		if(obj.has("numbers")) params = obj.getJSONArray("numbers");

		Long buildingId,buildingLevelId,personId,departmentId;
		Long depRetId,entryId =null;

		String comment,internalNumber;
		IDirectoryService service = Injection.find(aRequest).getService(IDirectoryService.class);

		if(isNotNullObj(obj,"buildingId") && isNotNullObj(obj,"buildingLevelId")
				&& isNotNullObj(obj,"departmentId"))
		{
			buildingId = Long.valueOf(String.valueOf(obj.get("buildingId")));
			departmentId = Long.valueOf(String.valueOf(obj.get("departmentId")));
			buildingLevelId = Long.valueOf(String.valueOf(obj.get("buildingLevelId")));
			depRetId = service.setDepartment(buildingId,buildingLevelId,departmentId);
			System.out.println(">>>>>DEP>>>:"+depRetId);
		}else return false;

		if(depRetId!=null){
			if(isNotNullObj(obj,"comment") && isNotNullObj(obj,"internalNumber")){

				comment = String.valueOf(obj.get("comment"));
				internalNumber = String.valueOf(obj.get("internalNumber"));

				if(isNotNullObj(obj,"personId")){
					personId = Long.valueOf(String.valueOf(obj.get("personId")));
					entryId = service.setEntry2(comment,internalNumber,depRetId,personId);
					System.out.println(">>>>>entryId>>>:"+entryId);
				}else {
					entryId = service.setEntry1(comment,internalNumber,depRetId);
					System.out.println(">>>>>entryId>>>:"+entryId);
				}
			}
		}else return false;

		if(entryId!=null){
			insertTelephoneNumbers(aRequest,obj,Long.valueOf(entryId));
			return true;
		}
		return false;
	}





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
	Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());

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