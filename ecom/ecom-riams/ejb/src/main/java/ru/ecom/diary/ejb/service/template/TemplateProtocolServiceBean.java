package ru.ecom.diary.ejb.service.template;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.ecom.poly.ejb.domain.protocol.RoughDraft;
import ru.nuzmsh.util.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:02
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Remote(ITemplateProtocolService.class)
public class TemplateProtocolServiceBean implements ITemplateProtocolService {
	
	public static String saveParametersByProtocol(Long aSmoId,Protocol d, String aParams, String aUsername, EntityManager aManager) throws JSONException {
		JSONObject obj = new JSONObject(aParams) ;
		String wf = String.valueOf(obj.get("workFunction"));
		System.out.print("workfunction================"+wf);
		StringBuilder sql = new StringBuilder() ;
		MedCase m = aManager.find(MedCase.class, aSmoId) ;
		if (m!=null) {
		List<Object> l = null;
		
		
		if (d!=null) {
			aManager.createNativeQuery("delete from FormInputProtocol where docProtocol_id="+d.getId()).executeUpdate() ;
		}
		} 
		if (d == null) {
			d = new Protocol() ;
			d.setMedCase(m) ;
			aManager.persist(d) ;			
		}
		
		JSONArray params = obj.getJSONArray("params");
		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < params.length(); i++) {
			JSONObject param = (JSONObject) params.get(i);
			FormInputProtocol fip = new FormInputProtocol() ;
			fip.setDocProtocol(d) ;
			Parameter p = aManager.find(Parameter.class, ConvertSql.parseLong(param.get("id"))) ;
			fip.setParameter(p) ;
			fip.setPosition(Long.valueOf(i+1)) ;
			String type = String.valueOf(param.get("type"));
			// 1-числовой
			// 4-числовой с плав точкой
			String value = String.valueOf(param.get("value"));
			if (type.equals("1")||type.equals("4")) {
				if (!StringUtil.isNullOrEmpty(value)) {
					fip.setValueBD(new BigDecimal(value)) ;
					fip.setValueText(value) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(value).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
				}
				//пользовательский справочник
			} else if (type.equals("2")) {
				Long id = ConvertSql.parseLong(value) ;
				if (id!=null && !id.equals(Long.valueOf(0))) {
					UserValue uv = aManager.find(UserValue.class, id) ;
					fip.setValueVoc(uv) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("valueVoc")).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
				}
				//пользовательский справочник (множественный выбор)
			} else if (type.equals("6")) {
				String id = String.valueOf(param.get("value")) ;
				if (id!=null && !id.trim().equals(",") && !id.trim().equals(",,") && !id.trim().equals("")) {
					fip.setValueText(String.valueOf(param.get("valueVoc"))) ;
					fip.setListValues(id) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("unitname")).append(" ") ;
					sb.append(fip.getValueText()) ;
				}
				//пользовательский справочник (текст)
			} else if (type.equals("7")) {
				Long id = ConvertSql.parseLong(value) ;
				if (id!=null && !id.equals(Long.valueOf(0))) {
					UserValue uv = aManager.find(UserValue.class, id) ;
					fip.setValueVoc(uv) ;
					fip.setValueText(String.valueOf(param.get("addValue"))) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(param.get("valueVoc")).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
					sb.append(param.get("addValue")).append(" ") ;
				}
				//3-текстовый
				//5-текстовый с ограничением
			} else if (type.equals("3")||type.equals("5")) {
				if (!StringUtil.isNullOrEmpty(value)) {
					fip.setValueText(value) ;
					if (sb.length()>0) sb.append("\n") ;
					sb.append(param.get("name")).append(": ") ;
					sb.append(value).append(" ") ;
					sb.append(param.get("unitname")).append(" ") ;
				}
			}
			aManager.persist(fip) ;
		}
		d.setRecord(sb.toString()) ;
		aManager.persist(d) ;
			if (m instanceof Visit)  {
				Visit vis = (Visit) m;
				if (wf!=null && !wf.equals("0")) {
					WorkFunction wfo = aManager.find(WorkFunction.class, Long.valueOf(wf)) ;
					vis.setWorkFunctionExecute(wfo) ;
				} else {
					vis.setWorkFunctionExecute(vis.getWorkFunctionPlan()) ;
					aManager.persist(vis) ;
				}
			}
		aManager.persist(m) ;
		return ""+d.getId() ;
	}
	
	public String getTextByProtocol(long aProtocolId) {
        Protocol tempprot = theManager.find(Protocol.class, aProtocolId) ;
        if(tempprot==null) throw new IllegalArgumentException("Нет шаблона протокола с таким ИД "+aProtocolId) ;
        return tempprot.getRecord() ;		
	}
    public String getTextTemplate(long aId) {
        TemplateProtocol tempprot = theManager.find(TemplateProtocol.class, aId) ;
        if(tempprot==null) throw new IllegalArgumentException("Нет шаблона протокола с таким ИД "+aId) ;
        return tempprot.getText() ;
    }

    public String getNameVoc(String aClassif, long aId) {
        return "123123" ;
    }

    @EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
	public Long getCountSymbolsInProtocol(long aVisit) {
		List<Protocol> list = theManager.createQuery("from Protocol where medCase_id=:idv").setParameter("idv", aVisit).setMaxResults(1).getResultList() ;
		if (list.size()>0) return Long.valueOf(list.get(0).getRecord().length()) ;
		return Long.valueOf(0);
	}}
