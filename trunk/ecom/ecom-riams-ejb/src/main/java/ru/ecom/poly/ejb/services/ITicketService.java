package ru.ecom.poly.ejb.services;

import java.text.ParseException;
import java.util.List;

import ru.ecom.poly.ejb.form.TicketForm;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 28.01.2007
 * Time: 13:09:34
 * To change this template use File | Settings | File Templates.
 */
public interface ITicketService {
	// Получить список в json мед. услуг по специалисту и дате оказания услуги
	public String getMedServiceBySpec(Long aSpec, String aDate) throws ParseException;
	// Поиск дублей по специалисту и дате оказания услуги
	public String findDoubleBySpecAndDate(Long aId, Long aMedcard, Long aSpecialist, String aDate);
	// Поиск Id#name профосмотра из справочника целей посещения
	public String findProvReason() ;
	// Поиск незакрытых талонов по медкарте
    List<TicketForm> findActiveMedcardTickets(Long aMedcard);
    // Поиск всех талонов по медкарте
    List<TicketForm> findAllMedcardTickets(Long aMedcard);
    List<TicketForm> findAllWorkerTickets(Long aSpecialist, String aDate, int aStatus);
    //Поиск талонов по специалисту
    List<TicketForm> findAllSpecialistTickets(Long aSpecialist);
    // Поиск талонов по специалисту и дате
    List<TicketForm> findAllSpecialistTickets(Long aSpecialist, String aDate, int aStatu);
    // Поиск талона по номеру
    List<TicketForm> findTicketsByNumber(String aNumber) ;
    // Поиск
    List<GroupByDate> findOpenTicketGroupByDate() ;
    List<TicketForm> findOpenTicketByDate(String aDate) ;
    List<TicketForm> findStatTicketByDateAndUsername(String aDateInfo, String aDate,String aUsername) ;

    
    //public List<TicketForm> findTicketByNonresident(String aTypePat, String aDate,String aDateTo);
    //public List<TicketForm> findTicketByNonresidentByDate(String aTypePat, String aDate);
    
    public List<TicketForm> findTicketBySpecialistByDate(String aTypePat, String aDate, String aSpecialist) ;
    void closeTicket(Long aTicketForm);
	void printTicket(long aMonitorId, long aTicketId, long aFileId) ;
	
}
