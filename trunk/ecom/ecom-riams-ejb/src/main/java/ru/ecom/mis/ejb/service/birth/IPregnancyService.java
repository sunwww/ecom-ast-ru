package ru.ecom.mis.ejb.service.birth;

/**
 * Сервис для работы с беременностью и тд
 * @author stkacheva
 *
 */
public interface IPregnancyService {
	public Long getPregnanExchangeCard(Long aPregId) ;
	public Long getConfinedExchangeCard(Long aPregId);
	public Long getConfinementCertificate(Long aPregId);
	public Long getPregHistoryByMedCase(Long aMedCaseId);
	public Long getNewBornHistoryByMedCase(Long aMedCaseId);
	public boolean isWomanByPatient(Long aPatient) ;
	public boolean isWomanByMedCase(Long aMedCase) ;
	public Long calcApgarEstimation(Long aMuscleTone, Long aPalpitation
  			, Long aReflexes, Long aRespiration
  			, Long aSkinColor) ;
	public String calcDownesEstimation(Long aRespirationRate, Long aCyanosis
  			, Long aIntercostalRetraction, Long aDifficultExhalation
  			, Long aAuscultation) ;
	public String calcInfRiskEstimation(Long aWaterlessDuration, Long aMotherTemperature
  			, Long aWaterNature, Long aApgar
  			, Long aNewBornWeight, Long aMotherInfectiousDiseases);
}
