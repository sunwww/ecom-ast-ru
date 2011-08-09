package ru.ecom.ejb.services.entityform;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import ru.nuzmsh.util.StringUtil;

public class PersistList {
	public static void saveArrayJson(String aTableName, Long aIdEntity, String aJson, String aFieldParent, String aFieldChildren, EntityManager aManager) {
		try {
			JSONObject obj = new JSONObject(aJson) ;
			JSONArray ar = obj.getJSONArray("childs");
			StringBuilder ids = new StringBuilder() ;
			for (int i = 0; i < ar.length(); i++) {
				JSONObject child = (JSONObject) ar.get(i);
				String jsonId = String.valueOf(child.get("value"));
				if (!StringUtil.isNullOrEmpty(jsonId) || "0".equals(jsonId)) {
					//System.out.println("    id="+jsonId) ;
					ids.append(",").append(jsonId) ;
					StringBuilder sql = new StringBuilder() ;
					sql.append("select count(*) from ").append(aTableName).append(" where ").append(aFieldParent).append("='")
							.append(aIdEntity).append("' and ").append(aFieldChildren).append("='").append(jsonId).append("'") ;
					//System.out.println(sql) ;
					Object count = aManager.createNativeQuery(sql.toString()).getSingleResult() ;
					if (parseLong(count)<1) {
						sql = new StringBuilder() ;
						sql.append("insert into ").append(aTableName).append(" set ").append(aFieldChildren).append("='")
							.append(jsonId).append("',").append(aFieldParent).append("='").append(aIdEntity).append("'") ;
						aManager.createNativeQuery(sql.toString()).executeUpdate() ;
					}
				}
			}
			StringBuilder sql = new StringBuilder () ;
			if (ids.length()>0) {
				
				sql.append("delete from ").append(aTableName)
					.append(" where ").append(aFieldParent).append("='").append(aIdEntity).append("' and ").append(aFieldChildren).append(" not in (").append(ids.substring(1)+")") ;
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
				StringBuilder sql = new StringBuilder() ;
				sql.append("select ").append(aFieldChildren).append(",").append(aFieldParent).append(" from ").append(aTableName).append(" where ").append(aFieldParent).append("='").append(aIdEntity).append("'") ;
				//System.out.println(sql) ;
				List<Object[]> list = aManager.createNativeQuery(sql.toString()).getResultList(); 
				for (Object child[] : list) {
					j.object().key("value").value(parseLong(child[0]));
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
	
	public static Long parseLong(Object aValue) {
		Long ret =null;
		if (aValue==null) return ret ;
		if (aValue instanceof Integer) {
			
			return Long.valueOf((Integer) aValue) ;
		}
		if(aValue instanceof BigInteger) {
			BigInteger bigint = (BigInteger) aValue ;
			
			return bigint!=null?bigint.longValue() : null;
		} 
		if (aValue instanceof Number) {
			Number number = (Number) aValue ;
			return number!=null?number.longValue() : null ;
		}
		if (aValue instanceof String) {
			return Long.valueOf((String) aValue);
		}
		return ret ;
	}
}
