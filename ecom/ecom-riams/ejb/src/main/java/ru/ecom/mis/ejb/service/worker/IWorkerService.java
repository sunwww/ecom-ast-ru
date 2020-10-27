package ru.ecom.mis.ejb.service.worker;

import ru.ecom.mis.ejb.domain.worker.Worker;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * 
 */
public interface IWorkerService {

	String getWorkFunctionInfoById(Long aWorkFunctionId) ;
    void onUpdate(Worker aWorker) ;
    String getUsernameByWorkFunction(Long aWorkFunction) ;
    String getWorkFunctions(Long aWorkFunction) ;
    /**
     * В каком отделение работает врач
     * @return department
     */
    Long getWorkingLpu() ;
    String getWorkingLpuInfo(Long aLpu) ;

    Long getSecUser() ;
	Long getWorkFunction(Long SecUser) ;
	Long getWorkFunction() ;
	String getWorkFunctionInfo(Long aWorkFunction) ;
	
	String getVocServiceStreamByIdInfo(Long aId) ;
	String getVocWorkFunctionByIdInfo(Long aId) ;
	List<TableTimeBySpecialists> getTableByDayAndFunction(Date aDateStart, Date aDateFinish, Long aVocWorkFunctionId) ;
	String getCalendarTimeId(Long aCalendarDay, Time aCalendarTime, Long aMinIs) ;
	String getDayBySpec(Long aWorkFunction) ;
}
