package ru.ecom.mis.ejb.service.expert;

import java.util.ArrayList;

public interface IQualityEstimationService {
	public QualityEstimationRow getRow(Long aCardId, String aTypeSpecialist, boolean aView);
	public QualityEstimationRow getRow(Boolean aFullExpertCard, String aRequestParam,Long aCardId, String aTypeSpecialist, boolean aView) ;
	public Long getIdQualityEstimationByType(String aType, Long aIdCard);
	public String getCountRow(Long aCardId);
	public String getInfoByDep(Long aSmo, Long aDepartment) ;
	public Long createDraftEK(Long aMcaseId, String aCode);
	public ArrayList<String> getAllServicesByMedCase(Long aMedcaseId);
}