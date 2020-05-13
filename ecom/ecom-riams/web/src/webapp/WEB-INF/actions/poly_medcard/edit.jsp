<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/poly" prefix="poly" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-poly_medcard.do" defaultField="number">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="person" />
      <msh:panel>
      <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
            	<msh:row >
      				<td colspan="6"><div id='medPolicyInformation' style="display: none;" class="errorMessage"/></td>
      			</msh:row>
      </msh:ifFormTypeAreViewOrEdit>
        <msh:row>
        	<msh:autoComplete property="lpu" size="100" label="ЛПУ" vocName="lpu" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="number" label="Номер мед. карты" horizontalFill="true" size="10" />
        </msh:row>
        <msh:row>
          <msh:textField viewOnlyField="true" property="dateRegistration" label="Дата регистрации" horizontalFill="true" size="10" />
          <msh:textField viewOnlyField="true" property="registrator" label="Регистратор" horizontalFill="true" size="10" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="poly_medcardForm">
    	<ecom:webQuery name="lastVisit" nativeSql="select 
    	smc.id,smc.dateStart,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
    	from medcard m 
    	left join patient p on p.id=m.person_id
    	left join MedCase smc on p.id=smc.patient_id
    	
    	left join workfunction wf on wf.id=smc.workFunctionExecute_id
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
    	left join worker w on w.id=wf.worker_id
    	left join patient wp on wp.id=w.person_id
    	where UPPER(smc.dtype)='SHORTMEDCASE' and m.id=${param.id} and smc.dateStart is not null 
    	and smc.medcard_id=m.id
    	order by smc.dateStart desc
    	" maxResult="1" />
    <msh:tableNotEmpty name="lastVisit">
     <msh:section title="Последнее посещение">
    	<msh:table name="lastVisit" action="entityParentView-smo_ticket.do" idField="1">
	    	<msh:tableColumn property="2" columnName="Дата"/>
    		<msh:tableColumn property="3" columnName="Специалист"/>
    	</msh:table>
     </msh:section>
    </msh:tableNotEmpty>
	  <msh:ifInRole roles="/Policy/Poly/Ticket/View">
	      <msh:section title="
	      " createRoles="/Policy/Poly/Ticket/Create" viewRoles="/Policy/Poly/Ticket/View" 
	      shortList="js-smo_ticket-list.do?short=Short&id=${param.id}" 
	      createUrl="entityParentPrepareCreate-smo_ticket.do?id=${param.id}" 
	      listUrl="js-smo_ticket-list.do?id=${param.id}">
	      <msh:sectionTitle>
	      Открытые талоны
	       <msh:ifInRole roles="/Policy/Poly/Ticket/Create">
	       <a href='entityParentPrepareCreate-smo_ticket.do?id=${param.id}'><img src='/skin/images/main/plus.png' alt='Добавить запись' title='Добавить запись' height='14' width='14'>Добавить посещение</a>
	       <msh:ifInRole roles="/Policy/Poly/ShortTicket/Create">
	       <a href='entityParentPrepareCreate-smo_short_ticket.do?id=${param.id}'><img src='/skin/images/main/plus.png' alt='Добавить запись' title='Добавить запись' height='14' width='14'>Добавить талон на прием</a>
	       </msh:ifInRole>
	       <a href='entityParentPrepareCreate-smo_short_spo.do?id=${param.id}'><img src='/skin/images/main/plus.png' alt='Добавить запись' title='Добавить запись' height='14' width='14'>Добавить обращение</a>
	       </msh:ifInRole>
	       <a onclick="getDefinition(&quot;js-smo_ticket-list.do?short=Short&amp;id=${param.id}&quot;,event); " href="javascript:void(0);"><img width="14" height="14" title="Просмотр списка" alt="Просмотр списка" src="/skin/images/main/view1.png">Просмотр списка</a>
	       <a href="js-smo_ticket-list.do?id=${param.id}"><img width="14" height="14" title="Переход к списку" alt="Переход к списку" src="/skin/images/main/list.png">Перейти к списку</a>
	      </msh:sectionTitle>
	        <msh:sectionContent>
	        <ecom:webQuery name="tickets"
	        	nativeSql="select 
					smc.id as smcid,smc.createDate as smccreateDate,smc.orderDate as smcorderDate
					,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
					from MedCase smc
					left join Patient pat on pat.id=smc.patient_id
					left join Medcard card on card.person_id=pat.id 
			    	left join workfunction wf on wf.id=smc.workFunctionExecute_id
			    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
			    	left join worker w on w.id=wf.worker_id
			    	left join patient wp on wp.id=w.person_id
					where card.id='${param.id}' and UPPER(smc.dtype)='SHORTMEDCASE'
					and smc.dateStart is null 
					and smc.medCard_id='${param.id}'
					"
			/>
	          <msh:table name="tickets" printUrl="print-ticketshort.do?s=PrintTicketService&amp;m=printInfo&amp;next=entityParentView-poly_medcard.do__id=${param.id}&noView=1" action="entityParentEdit-smo_ticket.do" idField="1">
	            <msh:tableColumn columnName="Номер" property="1" />
	            <msh:tableColumn columnName="Дата выдачи" property="2" />
	            <msh:tableColumn columnName="Дата приема" property="3" />
	            <msh:tableColumn columnName="Специалист" property="4" />
	          </msh:table>
	        </msh:sectionContent>
	      </msh:section>
      </msh:ifInRole>
    	<msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/View">
    	<msh:section>
    		<msh:sectionTitle>Карта обратившегося за психиатрической помощью</msh:sectionTitle>
    		<msh:sectionContent>
	    		<ecom:webQuery name="careCard" nativeSql="select cc.id,cc.cardNumber from PsychiatricCareCard cc left join Medcard m on m.id=${param.id} where cc.patient_id=m.person_id"/>
	    		<msh:table name="careCard" action="entityParentView-psych_careCard.do" idField="1">
	    			<msh:tableColumn property="sn" columnName="#"/>
	    			<msh:tableColumn property="2" columnName="№"/>
	    		</msh:table>
    		</msh:sectionContent>
    	</msh:section>
    	</msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Мед.карта">
      <msh:ifFormTypeIsView formName="poly_medcardForm">
		<msh:sideLink name="Просмотр инф. о заключениях по медкарте" action="/js-poly_protocol-infoByMedcard.do" params="id" roles="/Policy/Poly/Ticket/View"/>
      
        <msh:sideLink roles="/Policy/Poly/Medcard/Edit" key="ALT+2" params="id" action="/entityEdit-poly_medcard" name="Изменить" />
      </msh:ifFormTypeIsView>
     
      <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
        <msh:sideLink roles="/Policy/Poly/Medcard/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-poly_medcard" name="Удалить" confirm="Удалить медкарту?" />
        <msh:sideLink roles="/Policy/Poly/Medcard/Delete" key="ALT+D" action="/javascript:deleteOpenTalons()" name="Удалить открытые талоны" confirm="Удалить все открытые талоны?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
	    <msh:sideMenu title="Добавить">
	        <msh:sideLink roles="/Policy/Poly/Ticket/Create" key="CTRL+1" params="id" action="/entityParentPrepareCreate-smo_ticket" name="Талон" title="Создать новый талон" />
	        <msh:sideLink roles="/Policy/Poly/ShortTicket/Create" key="CTRL+2" params="id" action="/entityParentPrepareCreate-smo_short_ticket" name="Талон на прием" title="Создать талон на прием" />
	    </msh:sideMenu>
    <msh:sideMenu title="Показать все">
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+4" params="id" action="/js-smo_ticket-list" name="Талоны" title="Все талоны по мед.карте" />
    </msh:sideMenu>
    <msh:sideMenu title="Печать">
    <msh:sideLink roles="/Policy/Mis/Patient/View"  key="CTRL+1"
    	name="Амб. карта"   action="/javascript:goPrint('.do')"  
    	 title='Амб.карты'  />
    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
    <tags:ticket_finds currentAction="medcard"/>    
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_medcardForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
  <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
  <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
  	<script type="text/javascript">
      	function goPrint() {
      		window.location = 'print-ambcard.do?s=PatientPrintService&m=printInfo&id='+$('person').value+"&tmp="+Math.random() ;
      	}
      	function deleteOpenTalons() {
      		TicketService.deleteTalons ($('id').value,'',{
      			callback: function (a) {
      				alert (a);
      				window.location.reload();
      			}
      		});
      	}
      	</script>
  </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
</tiles:insert>

