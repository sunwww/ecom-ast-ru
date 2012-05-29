package ru.ecom.mis.ejb.service.birth;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.document.ejb.domain.certificate.ConfinementCertificate;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.birth.ConfinedExchangeCard;
import ru.ecom.mis.ejb.domain.birth.NewBorn;
import ru.ecom.mis.ejb.domain.birth.PregnanExchangeCard;
import ru.ecom.mis.ejb.domain.birth.PregnancyHistory;
import ru.ecom.mis.ejb.domain.patient.Patient;

@Stateless
@Remote(IPregnancyService.class)
public class PregnancyServiceBean implements IPregnancyService {
	@SuppressWarnings("unchecked")
	public Long getPregnanExchangeCard(Long aPregId) {
		List<PregnanExchangeCard> list = theManager.createQuery("from PregnanExchangeCard where pregnancy_id=:pregId")
			.setParameter("pregId",aPregId).getResultList() ;
		return list.size()>0?list.iterator().next().getId(): null;
	}
	@SuppressWarnings("unchecked")
	public Long getConfinedExchangeCard(Long aPregId) {
		List<ConfinedExchangeCard> list = theManager.createQuery("from ConfinedExchangeCard where pregnancy_id=:pregId")
		.setParameter("pregId",aPregId).getResultList() ;
	return list.size()>0?list.iterator().next().getId(): null;
	}
	
	@SuppressWarnings("unchecked")
	public Long getPregHistoryByMedCase(Long aMedCaseId) {
		List<PregnancyHistory> list = theManager.createQuery("from PregnancyHistory where medCase_id=:medCaseId")
		.setParameter("medCaseId",aMedCaseId).getResultList() ;
	return list.size()>0?list.iterator().next().getId(): null;
	}
	@SuppressWarnings("unchecked")
	public Long getNewBornHistoryByMedCase(Long aMedCaseId) {
		List<NewBorn> list = theManager.createQuery("from NewBorn where medCase_id=:medCaseId")
		.setParameter("medCaseId",aMedCaseId).getResultList() ;
	return list.size()>0?list.iterator().next().getId(): null;
	}
	@SuppressWarnings("unchecked")
	public Long getConfinementCertificate(Long aPregId) {
		List<ConfinementCertificate> list = theManager.createQuery("from Certificate where DTYPE='ConfinementCertificate' and pregnancy_id=:pregId")
		.setParameter("pregId",aPregId).getResultList() ;
		return list.size()>0?list.iterator().next().getId(): null;
	}
	public boolean isWomanByPatient(Long aPatient) {
		List<Object[]> res = theManager.createNativeQuery(" select vs.omcCode,vs.id from patient p left join vocsex vs on vs.id=p.sex_id where p.id='"+aPatient+"' and vs.omcCode='2'").setMaxResults(1).getResultList() ;
		return res.size()>0?true:false ;
		//Patient pat = theManager.find(Patient.class, aPatient) ;
		//return isWomanByPatient(pat) ;
	}
	
	public boolean isWomanByMedCase(Long aMedCase) {
		//MedCase medCase = theManager.find(MedCase.class, aMedCase) ;
		//Patient pat = medCase.getPatient() ;
		//if (pat==null && medCase.getParent()!=null) pat = medCase.getParent().getPatient() ;
		if (aMedCase==null) aMedCase=Long.valueOf(0) ;
		List<Object[]> res = theManager.createNativeQuery(" select vs.omcCode,vs.id from MedCase m left join patient p on p.id=m.patient_id left join vocsex vs on vs.id=p.sex_id where m.id="+aMedCase+" and vs.omcCode='2'").setMaxResults(1).getResultList() ;

		return res.size()>0?true:false ;
	}

	private boolean isWomanByPatient(Object aOmcCode) {
		return (aOmcCode!=null && String.valueOf(aOmcCode).equals("2") )? true :false;
		
	}
	public Long calcApgarEstimation(Long aMuscleTone, Long aPalpitation
  			, Long aReflexes, Long aRespiration
  			, Long aSkinColor)  {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select") 
		.append("     (select isnull(max(ball),0) from VocApgarSkinColor where id=:skinColor)+")
		.append(" 		(select isnull(max(ball),0) from VocApgarRespiration where id=:respiration)+")
		.append(" 		(select isnull(max(ball),0) from VocApgarReflexes where id=:reflexes)+")
		.append(" 		(select isnull(max(ball),0) from VocApgarPalpitation where id=:palpitation)+")
		.append(" 		(select isnull(max(ball),0) from VocApgarMuscleTone where id=:muscleTone)")
		.append(" 	,id	 from VocSex") ;
		List<Object[]> row = theManager.createNativeQuery(sql.toString())
			.setParameter("skinColor",aSkinColor)
			.setParameter("respiration",aRespiration)
			.setParameter("reflexes",aReflexes)
			.setParameter("palpitation",aPalpitation)
			.setParameter("muscleTone",aMuscleTone)
			.setMaxResults(1)
			.getResultList() ;
		StringBuilder ret = new StringBuilder() ;
		Long value = Long.valueOf(0) ;
		if(row.size()>0) {
			ret = new StringBuilder() ;
			ret.append(row.get(0)[0]) ;
			value = ConvertSql.parseLong(row.get(0)[0]) ;
			if (value==null) value=Long.valueOf(0) ; 
		} 
		
		return value ;
	
	}
	
	public String calcDownesEstimation(Long aRespirationRate, Long aCyanosis
  			, Long aIntercostalRetraction, Long aDifficultExhalation
  			, Long aAuscultation) {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select") 
		.append("     (select isnull(max(ball),0) from VocDownesRespirationRate where id=:respirationRate)+")
		.append(" 		(select isnull(max(ball),0) from VocDownesCyanosis where id=:cyanosis)+")
		.append(" 		(select isnull(max(ball),0) from VocDownesIntercostalRet where id=:intercostalRetraction)+")
		.append(" 		(select isnull(max(ball),0) from VocDownesDifExhalation where id=:difficultExhalation)+")
		.append(" 		(select isnull(max(ball),0) from VocDownesAuscultation where id=:auscultation)")
		.append(" 	,id	 from VocSex") ;
		List<Object[]> row = theManager.createNativeQuery(sql.toString())
			.setParameter("respirationRate",aRespirationRate)
			.setParameter("cyanosis",aCyanosis)
			.setParameter("intercostalRetraction",aIntercostalRetraction)
			.setParameter("difficultExhalation",aDifficultExhalation)
			.setParameter("auscultation",aAuscultation)
			.setMaxResults(1)
			.getResultList() ;
		
		Long value = Long.valueOf(0) ;
		if(row.size()>0) {
			value = ConvertSql.parseLong(row.get(0)[0]) ;
		}
		StringBuilder ret = new StringBuilder() ;
		ret.append(value).append("#") ;
		sql = new StringBuilder() ;
		sql.append("select id,name,code from VocCommonMask where DTYPE='VocDownesCommonMark' and :valueball between minBall and maxBall");
		row = theManager.createNativeQuery(sql.toString())
			.setParameter("valueball", value)
			.getResultList() ;
		if(row.size()>0) {
			
			ret.append(row.get(0)[0]) ;
			ret.append("#").append(row.get(0)[1]);
			ret.append("#").append(row.get(0)[2]);
			
		}
		return ret.toString() ;
	}
	public String calcInfRiskEstimation(Long aWaterlessDuration, Long aMotherTemperature
  			, Long aWaterNature, Long aApgar
  			, Long aNewBornWeight, Long aMotherInfectiousDiseases){
		StringBuilder sql = new StringBuilder() ;
		sql.append("select") 
		.append("     (select isnull(max(ball),0) from VocInfRiskWaterless where id=:waterlessDuration)+")
		.append(" 		(select isnull(max(ball),0) from VocInfRiskMotherTemperature where id=:motherTemperature)+")
		.append(" 		(select isnull(max(ball),0) from VocInfRiskWaterNature where id=:waterNature)+")
		.append(" 		(select isnull(max(ball),0) from VocInfRiskApgar where id=:apgar)+")
		.append(" 		(select isnull(max(ball),0) from VocInfRiskNewBornWeight where id=:newBornWeight)+")
		.append(" 		(select isnull(max(ball),0) from VocInfRiskMotherDiseases where id=:motherInfectiousDiseases)")
		.append(" 	,id	 from VocSex") ;
		List<Object[]> row = theManager.createNativeQuery(sql.toString())
			.setParameter("waterlessDuration",aWaterlessDuration)
			.setParameter("motherTemperature",aMotherTemperature)
			.setParameter("waterNature",aWaterNature)
			.setParameter("apgar",aApgar)
			.setParameter("newBornWeight",aNewBornWeight)
			.setParameter("motherInfectiousDiseases",aMotherInfectiousDiseases)
			.setMaxResults(1)
			.getResultList() ;
		
		Long value = Long.valueOf(0) ;
		if(row.size()>0) {
			value = ConvertSql.parseLong(row.get(0)[0]) ;
		}
		StringBuilder ret = new StringBuilder() ;
		ret.append(value).append("#") ;
		sql = new StringBuilder() ;
		sql.append("select id,name,code from VocCommonMask where DTYPE='VocInfRiskMark' and :valueball between minBall and maxBall");
		row = theManager.createNativeQuery(sql.toString())
			.setParameter("valueball", value)
			.getResultList() ;
		if(row.size()>0) {
			
			ret.append(row.get(0)[0]) ;
			ret.append("#").append(row.get(0)[1]);
			ret.append("#").append(row.get(0)[2]);
			
		}
		return ret.toString() ;
	}
	
	@PersistenceContext EntityManager theManager ;
	
}
