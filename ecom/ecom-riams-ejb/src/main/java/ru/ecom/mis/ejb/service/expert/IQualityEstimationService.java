package ru.ecom.mis.ejb.service.expert;

public interface IQualityEstimationService {
	public QualityEstimationRow getRow(Long aCardId, String aTypeSpecialist, boolean aView);
	public QualityEstimationRow getRow(Boolean aFullExpertCard, String aRequestParam,Long aCardId, String aTypeSpecialist, boolean aView) ;
	public Long getIdQualityEstimationByType(String aType, Long aIdCard);
	public String getCountRow(Long aCardId);
	public String getInfoBySmo(Long aSmo) ;
	public String getInfoByDep(Long aSmo, Long aDepartment) ;
	public String getInfoBySlo(Long aSmo, Long aSlo);
}
