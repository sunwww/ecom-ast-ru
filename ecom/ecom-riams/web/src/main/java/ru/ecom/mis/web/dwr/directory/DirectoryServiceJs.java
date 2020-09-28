package ru.ecom.mis.web.dwr.directory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.directory.IDirectoryService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Сервис справочника
 * @author rkurbanov
 */
public class DirectoryServiceJs {


	private boolean isNotNullObj(JSONObject obj, String Alias){
		return obj.has(Alias);
	}


	private void SQLupdate(HttpServletRequest aRequest, String SQLUpdateString){
		try {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
			service.executeUpdateNativeSql(SQLUpdateString);
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
		String departid = "";

		Long buildingId,misLpuId,buildingLevelId,departmentId;
		if(isNotNullObj(obj,"buildingId") && isNotNullObj(obj,"buildingLevelId")
				&& isNotNullObj(obj,"misLpuId")){
			buildingId = Long.valueOf(String.valueOf(obj.get("buildingId")));
			misLpuId = Long.valueOf(String.valueOf(obj.get("misLpuId")));
			buildingLevelId = Long.valueOf(String.valueOf(obj.get("buildingLevelId")));
			//departmentId = Long.valueOf(String.valueOf(obj.get("departmentId")));
			//System.out.println("Mislpu>>>"+misLpuId+" Department>>>"+departmentId);
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
			StringBuilder sql = new StringBuilder();
			sql.append("select id from department").append(" where building_id =").append(buildingId).append(" and department_id = ").append(misLpuId).append(" and buildinglevel_id = ").append(buildingLevelId);

			Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());
			for (WebQueryResult wqr : res) {
				departid = wqr.get1().toString();
			}
			if(departid.equalsIgnoreCase("")){
				IDirectoryService service2 = Injection.find(aRequest).getService(IDirectoryService.class);
				departid = String.valueOf(service2.setDepartment(buildingId,buildingLevelId,misLpuId));
			}
			//System.out.println(">>>>ID:"+departid);
		}else return false;

		String entryId;
		if(isNotNullObj(obj,"comment") && isNotNullObj(obj,"internalNumber")){

			entryId = String.valueOf(obj.get("entryId"));
			String comment = obj.getString("comment");
			String internalNumber = obj.getString("internalNumber");
			String personId;
			if(isNotNullObj(obj,"personId")){
				personId =String.valueOf(obj.get("personId"));
			}else personId = "null";
			SQLupdate(aRequest,"update entry " +
					"set comment ='"+comment+"'" +
					" ,department_id = "+departid +
					" ,insidenumber ='"+internalNumber+"', " +
					"person_id="+personId+" where id="+entryId);

		}else return false;
		if(obj.has("delId")) params = obj.getJSONArray("delId");
		StringBuilder delIds= new StringBuilder();
		boolean tick=false;
		if (params!=null){
			for (int i = 0; i < params.length(); i++) {
				JSONObject param = params.getJSONObject(i);
			//	Long TelNumberIdDel = Long.valueOf((String) param.get("TelNumberIdDel"));
				if(tick) delIds.append(",");
				delIds.append(param.getString("TelNumberIdDel"));
				tick=true;
			}
		}
		if(!delIds.toString().equals("")) {
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
		StringBuilder JSON= new StringBuilder();
		sql.append("select d.building_id, vb.name as build, d.buildinglevel_id , vbl.name as buildlevel,e.comment, \n" + "e.insidenumber, e.person_id, \n" + "p.lastname||' '||p.firstname||' '||p.middlename as fio,m.id as misLpu,e.department_id depId\n" + ",m.name as depname from entry e \n" + "left join department d on d.id = e.department_id\n" + "left join mislpu m on m.id = d.department_id\n" + "left join vocbuilding vb on vb.id =d.building_id\n" + "left join vocbuildinglevel vbl on vbl.id =d.buildinglevel_id\n" + "left join workfunction wf on wf.id = e.person_id\n" + "left join worker w on w.id =wf.worker_id\n" + "left join patient p on p.id = w.person_id\n" + "where e.id = ").append(entryId);

		Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());

		for (WebQueryResult wqr : res) {

			JSON.append("{\"buildingId\":\"").append(wqr.get1()).append("\",");
			JSON.append("\"building\":\"").append(wqr.get2()).append("\",");
			JSON.append("\"buildingLevelId\":\"").append(wqr.get3()).append("\",");
			JSON.append("\"buildingLevel\":\"").append(wqr.get4()).append("\",");
			JSON.append("\"comment\":\"").append(wqr.get5()).append("\",");
			JSON.append("\"insidenumber\":\"").append(wqr.get6()).append("\",");
			JSON.append("\"personId\":\"").append(wqr.get7()).append("\",");
			JSON.append("\"person\":\"").append(wqr.get8()).append("\",");
			JSON.append("\"depId\":\"").append(wqr.get9()).append("\",");
			JSON.append("\"departmentId\":\"").append(wqr.get10()).append("\",");
			JSON.append("\"department\":\"").append(wqr.get11()).append("\",");
		}

		JSON.append("\"numbers\":[");
		sql = new StringBuilder();
		sql.append("select tn.telnumber, tn.typenumber_id, vtn.name, tn.id from telephonenumber tn\n" + "left join voctypenumber vtn on vtn.id = tn.typenumber_id \n" + "where tn.entry_id= ").append(entryId);
		res = service.executeNativeSql(sql.toString());
		int chk=0;
		for (WebQueryResult wqr : res) {
			if(chk>0) JSON.append(",");
			JSON.append("{\"telnumber\":\"").append(wqr.get1()).append("\",");
			JSON.append("\"typeId\":\"").append(wqr.get2()).append("\",");
			JSON.append("\"typeName\":\"").append(wqr.get3()).append("\",");
			JSON.append("\"telephonenumberId\":\"").append(wqr.get4()).append("\"}");
			chk++;
		}
		JSON.append("]}");
		return JSON.toString();
	}

	//Добавление полной записи (Отделение->Запись->Телефонные номера)
	public boolean setEntry(String aJson, HttpServletRequest aRequest) throws JSONException, NamingException{

		JSONObject obj = new JSONObject(aJson);

		Long buildingId,buildingLevelId,personId,departmentId;
		Long depRetId,entryId =null;

		String comment,internalNumber;
		IDirectoryService service = Injection.find(aRequest).getService(IDirectoryService.class);

		if(isNotNullObj(obj,"buildingId") && isNotNullObj(obj,"buildingLevelId")
				&& isNotNullObj(obj,"departmentId")) {
			buildingId = obj.getLong("buildingId");
			departmentId = obj.getLong("departmentId");
			buildingLevelId = obj.getLong("buildingLevelId");
			depRetId = service.setDepartment(buildingId,buildingLevelId,departmentId);
		} else {
			return false;
		}
		if(depRetId!=null){
			if(isNotNullObj(obj,"comment") && isNotNullObj(obj,"internalNumber")){

				comment = obj.getString("comment");
				internalNumber = obj.getString("internalNumber");

				if(isNotNullObj(obj,"personId")){
					personId = obj.getLong("personId");
					entryId = service.setEntry2(comment,internalNumber,depRetId,personId);
				}else {
					entryId = service.setEntry1(comment,internalNumber,depRetId);
				}
			}
		} else {
			return false;
		}

		if(entryId!=null){
			insertTelephoneNumbers(aRequest,obj, entryId);
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
	
	StringBuilder sql ;
	StringBuilder id =EntryExist(aRequest,department,insideNumber,person,comment);
	
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

	//System.out.println("=== ret ID: "+id);
	
	for (int i = 0; i < params.length(); i++) {
	    JSONObject param = (JSONObject) params.get(i);

	    String typeNumber = (String) param.get("typeNumber"), 
		    number = (String) param.get("number");
	    
	   sql = new StringBuilder();
	   sql.append("insert into telephonenumber (telnumber,entry_id,typenumber_id) ");
	   sql.append("values('").append(number).append("',").append(id).append(",").append(typeNumber).append(")");
	   service.executeUpdateNativeSql(sql.toString());
	}
	
    }
    
    private StringBuilder EntryExist(HttpServletRequest aRequest,
	    String department, String insideNumber, String person,
	    String comment) throws NamingException {

	IWebQueryService service = Injection.find(aRequest).getService(
		IWebQueryService.class);
	StringBuilder sql = new StringBuilder();

	sql.append("select id from entry where insidenumber='").append(insideNumber).append("' and department_id=").append(department);
	Collection<WebQueryResult> res = service.executeNativeSql(sql.toString());

	StringBuilder id = new StringBuilder();
	for (WebQueryResult wqr : res) {
	    id.append(wqr.get1());
	}

	if (id.toString().equals("") ) {

	    sql = new StringBuilder();
	    sql.append("INSERT INTO entry (comment,insidenumber,");
	    if(person==null || person.equals("")){
		sql.append("department_id)").append("VALUES ('").append(comment).append("','").append(insideNumber).append("',").append(department).append(") returning id");
		}else{
		    sql.append("department_id)").append("VALUES ('").append(comment).append("','").append(insideNumber).append("',").append(person).append(",").append(department).append(") returning id");
		}

	    res = service.executeNativeSql(sql.toString());

	    id = new StringBuilder();
	    for (WebQueryResult wqr : res) {
			id.append(wqr.get1());
	    }

	    return id;
	} else return id;
    } 
}