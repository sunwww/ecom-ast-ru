package ru.ecom.mis.ejb.service.medcase;

import javax.persistence.EntityManager;

public interface IReportsService {
	public String getFilterInfo(boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType);
	public String getFilterInfoByOrder(Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF) ;
	public String getTextQueryBegin(boolean aIsTicket,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	public String getTextQueryEnd(boolean aIsTicket,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	public String getFilterId(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	public String getFilterInfo(EntityManager aManager, boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	public String getTitle(String aGroupBy) ;
	public String getFilter(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
}
