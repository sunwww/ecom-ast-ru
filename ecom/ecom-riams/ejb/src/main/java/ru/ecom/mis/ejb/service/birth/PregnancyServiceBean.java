package ru.ecom.mis.ejb.service.birth;

import ru.ecom.document.ejb.domain.certificate.ConfinementCertificate;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.domain.birth.ConfinedExchangeCard;
import ru.ecom.mis.ejb.domain.birth.NewBorn;
import ru.ecom.mis.ejb.domain.birth.PregnanExchangeCard;
import ru.ecom.mis.ejb.domain.birth.PregnancyHistory;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(IPregnancyService.class)
public class PregnancyServiceBean implements IPregnancyService {
	@SuppressWarnings("unchecked")
	public Long getPregnanExchangeCard(Long aPregId) {
		List<PregnanExchangeCard> list = theManager.createQuery("from PregnanExchangeCard where pregnancy_id=:pregId")
			.setParameter("pregId",aPregId).getResultList() ;
		return list.isEmpty() ? null : list.iterator().next().getId();
	}
	@SuppressWarnings("unchecked")
	public Long getConfinedExchangeCard(Long aPregId) {
		List<ConfinedExchangeCard> list = theManager.createQuery("from ConfinedExchangeCard where pregnancy_id=:pregId")
		.setParameter("pregId",aPregId).getResultList() ;
		return list.isEmpty() ? null : list.iterator().next().getId();
	}
	
	@SuppressWarnings("unchecked")
	public Long getPregHistoryByMedCase(Long aMedCaseId) {
		List<PregnancyHistory> list = theManager.createQuery("from PregnancyHistory where medCase_id=:medCaseId")
		.setParameter("medCaseId",aMedCaseId).getResultList() ;
		return list.isEmpty() ? null : list.iterator().next().getId();
	}
	@SuppressWarnings("unchecked")
	public Long getNewBornHistoryByMedCase(Long aMedCaseId) {
		List<NewBorn> list = theManager.createQuery("from NewBorn where medCase_id=:medCaseId")
		.setParameter("medCaseId",aMedCaseId).getResultList() ;
	return list.isEmpty() ? null : list.iterator().next().getId();
	}
	@SuppressWarnings("unchecked")
	public Long getConfinementCertificate(Long aPregId) {
		List<ConfinementCertificate> list = theManager.createQuery("from Certificate where DTYPE='ConfinementCertificate' and pregnancy_id=:pregId")
		.setParameter("pregId",aPregId).getResultList() ;
		return list.isEmpty() ? null : list.iterator().next().getId();
	}
	public boolean isWomanByPatient(Long aPatient) {
		List<Object[]> res = theManager.createNativeQuery(" select vs.omcCode,vs.id from patient p left join vocsex vs on vs.id=p.sex_id where p.id='"+aPatient+"' and vs.omcCode='2'").setMaxResults(1).getResultList() ;
		return !res.isEmpty() ;
	}
	
	public boolean isWomanByMedCase(Long aMedCase) {
		//MedCase medCase = theManager.find(MedCase.class, aMedCase) ;
		//Patient pat = medCase.getPatient() ;
		//if (pat==null && medCase.getParent()!=null) pat = medCase.getParent().getPatient() ;
		if (aMedCase==null) aMedCase=0L ;
		List<Object[]> res = theManager.createNativeQuery(" select vs.omcCode,vs.id from MedCase m left join patient p on p.id=m.patient_id left join vocsex vs on vs.id=p.sex_id where m.id="+aMedCase+" and vs.omcCode='2'").setMaxResults(1).getResultList() ;

		return !res.isEmpty();
	}

	public Long calcApgarEstimation(Long aMuscleTone, Long aPalpitation
  			, Long aReflexes, Long aRespiration
  			, Long aSkinColor)  {
		String sql = "select" +
				"     (select case when max(ball) is not null then max(ball) else 0 end from VocApgarSkinColor where id=:skinColor)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocApgarRespiration where id=:respiration)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocApgarReflexes where id=:reflexes)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocApgarPalpitation where id=:palpitation)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocApgarMuscleTone where id=:muscleTone)" +
				" 	,id	 from VocSex";
		List<Object[]> row = theManager.createNativeQuery(sql)
			.setParameter("skinColor",aSkinColor)
			.setParameter("respiration",aRespiration)
			.setParameter("reflexes",aReflexes)
			.setParameter("palpitation",aPalpitation)
			.setParameter("muscleTone",aMuscleTone)
			.setMaxResults(1)
			.getResultList() ;
		Long value = 0L ;
		if(!row.isEmpty()) {
			value = ConvertSql.parseLong(row.get(0)[0]) ;
			if (value==null) value=0L ;
		} 
		return value ;
	
	}
	
	public String calcDownesEstimation(Long aRespirationRate, Long aCyanosis
  			, Long aIntercostalRetraction, Long aDifficultExhalation
  			, Long aAuscultation) {
		String sql = "select" +
				"     (select case when max(ball) is not null then max(ball) else 0 end from VocDownesRespirationRate where id=:respirationRate)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocDownesCyanosis where id=:cyanosis)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocDownesIntercostalRet where id=:intercostalRetraction)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocDownesDifExhalation where id=:difficultExhalation)+" +
				" 		(select case when max(ball) is not null then max(ball) else 0 end from VocDownesAuscultation where id=:auscultation)" +
				" 	,id	 from VocSex";
		List<Object[]> row = theManager.createNativeQuery(sql)
			.setParameter("respirationRate",aRespirationRate)
			.setParameter("cyanosis",aCyanosis)
			.setParameter("intercostalRetraction",aIntercostalRetraction)
			.setParameter("difficultExhalation",aDifficultExhalation)
			.setParameter("auscultation",aAuscultation)
			.setMaxResults(1)
			.getResultList() ;
		
		Long value = 0L ;
		if(!row.isEmpty()) {
			value = ConvertSql.parseLong(row.get(0)[0]) ;
		}
		StringBuilder ret = new StringBuilder() ;
		ret.append(value).append("#") ;
		row = theManager.createNativeQuery("select id,name,code from VocCommonMask where DTYPE='VocDownesCommonMark' and :valueball between minBall and maxBall")
			.setParameter("valueball", value)
			.getResultList() ;
		if(!row.isEmpty()) {
			
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
		.append("     (select case when max(ball) is not null then max(ball) else 0 end from VocInfRiskWaterless where id=:waterlessDuration)+")
		.append(" 		(select case when max(ball) is not null then max(ball) else 0 end from VocInfRiskMotherTemperature where id=:motherTemperature)+")
		.append(" 		(select case when max(ball) is not null then max(ball) else 0 end from VocInfRiskWaterNature where id=:waterNature)+")
		.append(" 		(select case when max(ball) is not null then max(ball) else 0 end from VocInfRiskApgar where id=:apgar)+")
		.append(" 		(select case when max(ball) is not null then max(ball) else 0 end from VocInfRiskNewBornWeight where id=:newBornWeight)+")
		.append(" 		(select case when max(ball) is not null then max(ball) else 0 end from VocInfRiskMotherDiseases where id=:motherInfectiousDiseases)")
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
		
		Long value = 0L ;
		if(!row.isEmpty()) {
			value = ConvertSql.parseLong(row.get(0)[0]) ;
		}
		StringBuilder ret = new StringBuilder() ;
		ret.append(value).append("#") ;
		sql = new StringBuilder() ;
		sql.append("select id,name,code from VocCommonMask where DTYPE='VocInfRiskMark' and :valueball between minBall and maxBall");
		row = theManager.createNativeQuery(sql.toString())
			.setParameter("valueball", value)
			.getResultList() ;
		if(!row.isEmpty()) {
			
			ret.append(row.get(0)[0]) ;
			ret.append("#").append(row.get(0)[1]);
			ret.append("#").append(row.get(0)[2]);
			
		}
		return ret.toString() ;
	}
	
	@PersistenceContext EntityManager theManager ;
	
}
