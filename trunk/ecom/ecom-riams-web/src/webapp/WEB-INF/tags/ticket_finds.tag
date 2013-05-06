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
	<msh:sideLink name="По специалисту" action="/smo_ticketspec.do" key="ALT+8" 
		title="По специалисту" roles="/Policy/Poly/Ticket/View"
		styleId="ticketspec"/>
	<msh:sideLink name="Открытые" action="/smo_ticketsopen.do" key="ALT+9" 
		title="По специалисту" roles="/Policy/Poly/Ticket/View"
		styleId="ticketopen"/>
	<msh:sideLink name="Дубли" action="/smo_doubleTickets.do" 
		title="Дубли талонов по специалисту" roles="/Policy/Poly/Ticket/View"
		styleId="ticketdouble"/>

</msh:sideMenu>
<msh:sideMenu title="Статистика">
	<msh:sideLink name="по пользователю" action="/smo_ticketsByUser" params="" 
		roles="/Policy/Poly/Ticket/StatByUser" styleId="ticketsByUser"
	/>
	<msh:sideLink name="по типу населению" action="/smo_journal_nonredidentPatient" params="" 
		roles="/Policy/Poly/Ticket/StatByPatient" styleId="ticketsByResident"
	/>
	<msh:sideLink name="по специалистам" action="/smo_ticketsBySpecialistList" params="" 
		roles="/Policy/Poly/Ticket/StatBySpecialist" styleId="ticketsBySpec"
	/>
	<msh:sideLink name="учет посещений" action="/smo_journalRegisterVisit_list" params="" 
		roles="/Policy/Poly/Ticket/RegistrationVisit" styleId="journalRegisterVisit"
	/>
	<msh:sideLink name="Форма 039У-02" action="/smo_f039.do" params="" 
		roles="/Policy/Poly/Ticket/Report039" styleId="report039"
	/>
	<msh:sideLink action="/psych_listByArea" 
	name="Список пациентов по участкам" roles="/Policy/Poly/Ticket/Psych" styleId="psychDriveArea"/>

</msh:sideMenu>
    <msh:sideMenu title="Поиск">
      <msh:sideLink key="CTRL+7" params="" action="/poly_medcards" name="⇧ Мед.карты" 
      	styleId="medcardfind"
      	guid="e9346501-00ea-4b6b-bd25-7fdab4803413" />
      <msh:sideLink key="CTRL+8" params="" action="/mis_patients" name="⇧ Персоны" guid="e9346501-00ea-4b6b-bd25-7fdab4803413" />
    </msh:sideMenu>
