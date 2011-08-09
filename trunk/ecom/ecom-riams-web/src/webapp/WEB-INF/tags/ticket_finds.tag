<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>

<style type="text/css">
a#${currentAction}, #side ul li a#${currentAction}, #side ul li a#${currentAction}  {
    color: black ;
    background-color: rgb(195,217,255) ;
    cursor: text ;
    border: none ;
    text-decoration: none ;
    background-image: url("/skin/images/main/sideSelected.gif");
    background-repeat: no-repeat;
    background-position: center left; ;
	font-weight: bold ;
    -moz-border-radius-topleft: 4px ;
    -moz-border-radius-bottomleft: 4px ;
}
</style>
<msh:sideMenu></msh:sideMenu>
<msh:sideMenu title="Поиск талонов" guid="fsdfsdf-dfsf">
	<msh:sideLink name="По номеру" action="/poly_tickets.do" key="ALT+7" 
		title="По талону" roles="/Policy/Poly/Ticket/View"
		styleId="tickets"/>
	<msh:sideLink name="По специалисту" action="/poly_ticketspec.do" key="ALT+8" 
		title="По специалисту" roles="/Policy/Poly/Ticket/View"
		styleId="ticketspec"/>
	<msh:sideLink name="Открытые" action="/poly_ticketsopen.do" key="ALT+9" 
		title="По специалисту" roles="/Policy/Poly/Ticket/View"
		styleId="ticketopen"/>
	<msh:sideLink name="Дубли" action="/poly_doubleTickets_list.do" 
		title="Дубли талонов по специалисту" roles="/Policy/Poly/Ticket/View"
		styleId="ticketdouble"/>
	<msh:sideLink name="Проблемы с полисами" action="/poly_problemTickets_list.do" 
		title="Проблемы с полисами по талонам" roles="/Policy/Poly/Ticket/View"
		styleId="ticketsByProblemWithMpolicy"/>
</msh:sideMenu>
<msh:sideMenu title="Статистика">
	<msh:sideLink name="по пользователю" action="/poly_ticketsByUserList" params="" 
		roles="/Policy/Poly/Ticket/StatByUser" styleId="ticketsByUser"
	/>
	<msh:sideLink name="по типу населению" action="/poly_ticketsByNonredidentPatientList" params="" 
		roles="/Policy/Poly/Ticket/StatByPatient" styleId="ticketsByResident"
	/>
	<msh:sideLink name="по специалистам" action="/poly_ticketsBySpecialistList" params="" 
		roles="/Policy/Poly/Ticket/StatBySpecialist" styleId="ticketsBySpec"
	/>
	<msh:sideLink name="учет посещений" action="/poly_journalRegisterVisit_list" params="" 
		roles="/Policy/Poly/Ticket/RegistrationVisit" styleId="journalRegisterVisit"
	/>
</msh:sideMenu>
    <msh:sideMenu title="Поиск">
      <msh:sideLink key="CTRL+7" params="" action="/poly_medcards" name="⇧ Мед.карты" 
      	styleId="medcard"
      	guid="e9346501-00ea-4b6b-bd25-7fdab4803413" />
      <msh:sideLink key="CTRL+8" params="" action="/mis_patients" name="⇧ Персоны" guid="e9346501-00ea-4b6b-bd25-7fdab4803413" />
    </msh:sideMenu>
