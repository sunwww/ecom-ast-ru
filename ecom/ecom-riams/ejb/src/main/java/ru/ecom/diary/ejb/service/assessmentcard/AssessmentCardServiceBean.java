package ru.ecom.diary.ejb.service.assessmentcard;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.diary.ejb.domain.protocol.parameter.FormInputProtocol;
import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.user.UserValue;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.assessmentcard.AssessmentCard;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.util.StringUtil;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
@Remote(IAssessmentCardService.class)
public class AssessmentCardServiceBean implements IAssessmentCardService{

	public static String saveParametersByCard(Long aPatientId, AssessmentCard ac, String aParams, EntityManager aManager) {
		
			JSONObject obj = new JSONObject(aParams) ;
			Patient pat = aManager.find(Patient.class, aPatientId) ;
			if (pat!=null) {			
				if (ac!=null) {
					aManager.createNativeQuery("delete from FormInputProtocol where assessmentCard="+ac.getId()).executeUpdate() ;
				} else {
				ac = new AssessmentCard() ;
				ac.setPatient(pat.getId()) ;				
				aManager.persist(ac) ;			
			}
			
			JSONArray params = obj.getJSONArray("params");
		//	StringBuilder sb = new StringBuilder() ;
			for (int i = 0; i < params.length(); i++) {
				JSONObject param = (JSONObject) params.get(i);
				FormInputProtocol fip = new FormInputProtocol() ;
				fip.setAssessmentCard(ac.getId());
				Parameter p = aManager.find(Parameter.class, ConvertSql.parseLong(param.get("id"))) ;
				fip.setParameter(p) ;
				fip.setPosition(i+1L) ;
				String type = String.valueOf(param.get("type"));
				// 1-числовой
				// 4-числовой с плав точкой
				String value = String.valueOf(param.get("value"));
				if (type.equals("1")||type.equals("4")) {
					if (!StringUtil.isNullOrEmpty(value)) {
						fip.setValueBD(new BigDecimal(value)) ;
			/*			if (sb.length()>0) sb.append("\n") ;
						sb.append(param.get("name")).append(": ") ;
						sb.append(value).append(" ") ;
						sb.append(param.get("unitname")).append(" ") ;*/
					}
					//пользовательский справочник
				} else if (type.equals("2") && !"".equals(value)) {
					Long id = ConvertSql.parseLong(value) ;
					if (id!=null && !id.equals(0L)) {
						UserValue uv = aManager.find(UserValue.class, id) ;
						fip.setValueVoc(uv) ;
				/*		if (sb.length()>0) sb.append("\n") ;
						sb.append(param.get("name")).append(": ") ;
						sb.append(param.get("valueVoc")).append(" ") ;
						sb.append(param.get("unitname")).append(" ") ;*/
					}
					//3-текстовый
					//5-текстовый с ограничением
				} else if (type.equals("3")||type.equals("5")) {
					if (!StringUtil.isNullOrEmpty(value)) {
						fip.setValueText(value) ;
				/*		if (sb.length()>0) sb.append("\n") ;
						sb.append(param.get("name")).append(": ") ;
						sb.append(value).append(" ") ;
						sb.append(param.get("unitname")).append(" ") ;*/
					}
				}
				aManager.persist(fip) ;
			}
				
			return ""+ac.getId() ;
			}
			return null;
		}
		
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;	
}
