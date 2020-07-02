package ru.ecom.poly.ejb.services;

import ru.ecom.poly.ejb.form.TicketForm;

import java.text.ParseException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 28.01.2007
 * Time: 13:09:34
 * To change this template use File | Settings | File Templates.
 */
public interface ITicketService {
	
	Long createMedcase (String aType) ;
	
	// Перенос визита (короткого талона) в другое СПО
	void moveVisitInOtherSpo(Long aVisit,Long aNewSpo) ;
	// Объединение СПО
	void unionSpos(Long aOldSpo,Long aNewSpo) ;
	// Получить список в json мед. услуг по специалисту и дате оказания услуги
	String getMedServiceBySpec(Long aSpec, String aDate) throws ParseException;
	// Поиск дублей по специалисту и дате оказания услуги
	String findDoubleBySpecAndDate(Long aId, Long aMedcard, Long aSpecialist, String aDate);
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

    void closeTicket(Long aTicketForm);
}
