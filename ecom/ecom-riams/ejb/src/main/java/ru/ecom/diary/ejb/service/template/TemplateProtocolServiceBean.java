package ru.ecom.diary.ejb.service.template;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.service.prescription.PrescriptionServiceBean;
import ru.ecom.poly.ejb.domain.protocol.Protocol;
import ru.nuzmsh.util.StringUtil;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

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
	private static final Logger LOG = Logger.getLogger(TemplateProtocolServiceBean.class);

	public String makePOSTRequest(String data, String address, String aMethod, Map<String, String> params) {
		//LOG.info("create connection, address = "+address+",method = "+aMethod+" , data="+data);
		if (address==null) {
			return "";
		}
		HttpURLConnection connection = null;
		try {
			URL url = new URL(address+"/"+aMethod);
			connection = (HttpURLConnection) url.openConnection();
			if (params!=null&&!params.isEmpty()) {
				for (Map.Entry<String,String> par: params.entrySet()) {
				//	LOG.info("send HTTP request. Key = "+par.getKey()+"<< value = "+par.getValue());
					connection.setRequestProperty(par.getKey(),par.getValue());
				}
			}
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStream writer = connection.getOutputStream();
			writer.write(data.getBytes("UTF-8"));
			writer.flush();
			writer.close();
			StringBuilder response = new StringBuilder();
			try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String s;
				while ((s = in.readLine()) != null) {
					response.append(s);
				}
			}
			connection.disconnect();
			return response.toString();

		} catch (ConnectException e) {
			LOG.error("Ошибка соединения с сервисом. "+e);
		} catch (Exception e) {
			if (connection!=null) {connection.disconnect();}
			LOG.error("in thread happens exception"+e);
			e.printStackTrace();
		}
		return "";
	}

	public static String saveParametersByProtocol(Long aSmoId,Protocol d, String aParams, String aUsername, EntityManager aManager) {
		//Добавить - если создать браслет - создаем браслет
		JSONObject obj = new JSONObject(aParams) ;
		Long wf = obj.getLong("workFunction");
		MedCase m = aManager.find(MedCase.class, aSmoId) ;
		if (m!=null && d!=null) {
			aManager.createNativeQuery("delete from FormInputProtocol where docProtocol_id=:protId").setParameter("protId",d.getId()).executeUpdate() ;
		}
		if (d == null) {
			d = new Protocol() ;
			d.setMedCase(m) ;
			d.setUsername(aUsername);
			aManager.persist(d) ;			
		}
		JSONArray params = obj.getJSONArray("params");
		if (d.getTemplateProtocol()!=null && d.getTemplateProtocol()>0L) {
			TemplateProtocol templateProtocol = aManager.find(TemplateProtocol.class,d.getTemplateProtocol());
			if (templateProtocol !=null && Boolean.TRUE.equals(templateProtocol.getCreateBracelet())) {
				//в случае создания/редактирования лаб. дневника - родитель у визита будет СЛС.
				MedCase medCase = d.getMedCase().getParent()!=null ? d.getMedCase().getParent() : d.getMedCase();
				new PrescriptionServiceBean().createBraceletByPrescription(templateProtocol, d.getId(), params, medCase, aUsername, aManager);
			}

		}

		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < params.length(); i++) {
			JSONObject param = params.getJSONObject(i);
			FormInputProtocol fip = new FormInputProtocol() ;
			fip.setDocProtocol(d) ;
			Parameter p = aManager.find(Parameter.class, ConvertSql.parseLong(param.get("id"))) ;
			fip.setParameter(p) ;
			fip.setPosition(i+1L);
			String type = param.getString("type");
			// 1-числовой
			// 4-числовой с плав точкой
			String value = param.getString("value");
			switch (type) {
				case "1":
				case "4":
					if (!StringUtil.isNullOrEmpty(value)) {
						fip.setValueBD(new BigDecimal(value));
						fip.setValueText(value);
						if (sb.length() > 0) sb.append("\n");
						sb.append(param.get("name")).append(": ");
						sb.append(value).append(" ");
						sb.append(param.get("unitname")).append(" ");
					}
					//пользовательский справочник
					break;
				case "2": {
					Long id = ConvertSql.parseLong(value);
					if (id != null && !id.equals(0L)) {
						UserValue uv = aManager.find(UserValue.class, id);
						fip.setValueVoc(uv);
						if (sb.length() > 0) sb.append("\n");
						sb.append(param.get("name")).append(": ");
						sb.append(param.get("valueVoc")).append(" ");
						sb.append(param.get("unitname")).append(" ");
					}
					//пользовательский справочник (множественный выбор)
					break;
				}
				case "6": {
					String id = param.getString("value");
					if (!id.trim().equals(",") && !id.trim().equals(",,") && !id.trim().equals("")) {
						fip.setValueText(param.getString("valueVoc"));
						fip.setListValues(id);
						if (sb.length() > 0) sb.append("\n");
						sb.append(param.get("name")).append(": ");
						sb.append(param.get("unitname")).append(" ");
						sb.append(fip.getValueText());
					}
					//пользовательский справочник (текст)
					break;
				}
				case "7": {
					Long id = ConvertSql.parseLong(value);
					if (id != null && !id.equals(0L)) {
						UserValue uv = aManager.find(UserValue.class, id);
						fip.setValueVoc(uv);
						fip.setValueText(param.getString("addValue"));
						if (sb.length() > 0) sb.append("\n");
						sb.append(param.get("name")).append(": ");
						sb.append(param.get("valueVoc")).append(" ");
						sb.append(param.get("unitname")).append(" ");
						sb.append(param.get("addValue")).append(" ");
					}
					//3-текстовый
					//5-текстовый с ограничением
					break;
				}
				case "3":
				case "5":
				case "8":   //дата
					if (!StringUtil.isNullOrEmpty(value)) {
						fip.setValueText(value);
						if (sb.length() > 0) sb.append("\n");
						sb.append(param.get("name")).append(": ");
						sb.append(value).append(" ");
						sb.append(param.get("unitname")).append(" ");
					}
					break;
			}
			aManager.persist(fip) ;
		}
		d.setRecord(sb.toString()) ;
		aManager.persist(d) ;
			if (m instanceof Visit)  {
				Visit vis = (Visit) m;
				if ( wf>0L) {
					vis.setWorkFunctionExecute(aManager.find(WorkFunction.class, wf)) ;
				} else {
					vis.setWorkFunctionExecute(vis.getWorkFunctionPlan()) ;
					aManager.persist(vis) ;
				}
			}
		aManager.persist(m) ;
		return ""+d.getId() ;
	}

	@Override
	public String getTextByProtocol(long aProtocolId) {
        Protocol tempprot = manager.find(Protocol.class, aProtocolId) ;
        if(tempprot==null) throw new IllegalArgumentException("Нет шаблона протокола с таким ИД "+aProtocolId) ;
        return tempprot.getRecord() ;		
	}
	@Override
    public String getTextTemplate(long aId) {
        TemplateProtocol tempprot = manager.find(TemplateProtocol.class, aId) ;
        if(tempprot==null) throw new IllegalArgumentException("Нет шаблона протокола с таким ИД "+aId) ;
        return tempprot.getText() ;
    }

	@EJB ILocalEntityFormService entityFormService ;
    @PersistenceContext
    EntityManager manager ;
	public Long getCountSymbolsInProtocol(long aVisit) {
		List<Protocol> list = manager.createQuery("from Protocol where medCase_id=:idv").setParameter("idv", aVisit).setMaxResults(1).getResultList() ;
		return list.isEmpty() ? Long.valueOf(0) : Long.valueOf(list.get(0).getRecord().length());
	}
}