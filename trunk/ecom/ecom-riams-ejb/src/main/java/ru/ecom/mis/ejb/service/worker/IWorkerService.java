package ru.ecom.mis.ejb.service.worker;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ru.ecom.mis.ejb.domain.worker.Worker;

/**
 * 
 */
public interface IWorkerService {

	public String getWorkFunctionInfoById(Long aWorkFunctionId) ;
    void onUpdate(Worker aWorker) ;
    public String getUsernameByWorkFunction(Long aWorkFunction) ;
    public String getWorkFunctions(Long aWorkFunction) ;
    /**
     * В каком отделение работает врач
     * @return department
     */
    public Long getWorkingLpu() ; 
    public String getWorkingLpuInfo(Long aLpu) ; 

    public Long getSecUser() ;
	public Long getWorkFunction(Long SecUser) ;
	public Long getWorkFunction() ;
	public String getWorkFunctionInfo(Long aWorker) ;
	public List<TableTimeBySpecialists> getTableByDayAndFunction(Date aDateStart, Date aDateFinish, Long aVocWorkFunctionId) ;
	public List<TableSpetialistByDay> getTableSpetialistByDay(Date aDate, Long aWorkCalendarDay) ;
	public String getCalendarTimeId(Long aCalendarDay, Time aCalendarTime, Long aMinIs) ;
	public String getDayBySpec(Long aWorkFunction) ;
}
