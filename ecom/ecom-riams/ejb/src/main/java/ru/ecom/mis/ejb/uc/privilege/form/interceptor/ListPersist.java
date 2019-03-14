package ru.ecom.mis.ejb.uc.privilege.form.interceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.nuzmsh.util.StringUtil;

import javax.persistence.EntityManager;
import java.io.StringWriter;
import java.util.List;

public class ListPersist {
	public static void saveArrayJson(String aTableName, Long aIdEntity, String aJson, String aFieldParent, String aFieldChildren, EntityManager aManager) {
		try {
			JSONObject obj = new JSONObject(aJson) ;
			JSONArray ar = obj.getJSONArray("childs");
			StringBuilder ids = new StringBuilder() ;
			for (int i = 0; i < ar.length(); i++) {
				JSONObject child = (JSONObject) ar.get(i);
				String jsonId = String.valueOf(child.get("value"));
				if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
					ids.append(",").append(jsonId) ;
					StringBuilder sql = new StringBuilder() ;
					sql.append("select count(*) from ").append(aTableName).append(" where ").append(aFieldParent).append("='")
							.append(aIdEntity).append("' and ").append(aFieldChildren).append("='").append(jsonId).append("'") ;
					Object count = aManager.createNativeQuery(sql.toString()).getSingleResult() ;
					if (ConvertSql.parseLong(count)<1) {
						sql = new StringBuilder() ;
						sql.append("insert into ").append(aTableName).append(" ( ")
							.append(aFieldChildren).append(",").append(aFieldParent)
							.append(") values ('")
							.append(jsonId).append("',").append("'").append(aIdEntity).append("')") ;
						aManager.createNativeQuery(sql.toString()).executeUpdate() ;
					}
				}
			}
			StringBuilder sql = new StringBuilder () ;
			if (ids.length()>0) {
				
				sql.append("delete from ").append(aTableName)
                        .append(" where ").append(aFieldParent).append("='").append(aIdEntity).append("' and ").append(aFieldChildren).append(" not in (").append(ids.substring(1)).append(")");
				aManager.createNativeQuery(sql.toString()).executeUpdate();
			} else {
				sql.append("delete from ").append(aTableName)
					.append(" where ").append(aFieldParent).append("='").append(aIdEntity).append("'");
				aManager.createNativeQuery(sql.toString()).executeUpdate() ;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getArrayJson(String aTableName, String aFieldParent, Long aIdEntity, String aFieldChildren, EntityManager aManager) {
		StringWriter out = new StringWriter();
		JSONWriter j = new JSONWriter(out);
		try {
			j.object();

			j.key("childs").array();
			if (aIdEntity>0) {
				List<Object[]> list = aManager.createNativeQuery("select " + aFieldChildren + "," + aFieldParent + " from " + aTableName + " where " + aFieldParent + "='" + aIdEntity + "'").getResultList();
				for (Object[] child : list) {
					j.object().key("value").value(ConvertSql.parseLong(child[0]));
					j.endObject();
				}
			}
			j.endArray();
	
			j.endObject();
			return out.toString() ;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "" ;
	}

}
