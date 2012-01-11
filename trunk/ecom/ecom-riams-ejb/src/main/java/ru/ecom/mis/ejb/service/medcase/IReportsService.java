package ru.ecom.mis.ejb.service.medcase;

import javax.persistence.EntityManager;

public interface IReportsService {
	public String getFilterInfo(boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream);
	public String getTextQueryBegin(boolean aIsTicket,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream) ;
	public String getTextQueryEnd(boolean aIsTicket,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream) ;
	public String getFilterId(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream) ;
	public String getFilterInfo(EntityManager aManager, boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream) ;
	public String getTitle(String aGroupBy) ;
	public String getFilter(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream) ;
}
