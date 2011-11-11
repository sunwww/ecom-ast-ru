<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/poly" prefix="poly" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-poly_medcard.do" defaultField="number" guid="bc67abba-e254-42ec-93f5-05fc22642f08">
      <msh:hidden property="id" guid="ca6184cf-53af-4505-bcf1-e5968005c218" />
      <msh:hidden property="saveType" guid="dbe70ad4-8e55-4c4a-9d5c-c1f7c98baeab" />
      <msh:hidden property="person" guid="0dac6061-dfcf-492d-abd6-ff4863f05abf" />
      <msh:panel guid="493743b7-5c99-4908-8def-f46fc983c447">
      <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
            	<msh:row >
      				<td colspan="6"><div id='medPolicyInformation' style="display: none;" class="errorMessage"/></td>
      			</msh:row>
      </msh:ifFormTypeAreViewOrEdit>
        <msh:row>
        	<msh:autoComplete property="lpu" size="100" label="ЛПУ" vocName="lpu" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="f7e60994-c39c-4e57-a48d-2fbdf95717dc">
          <msh:textField property="number" label="Номер мед. карты" horizontalFill="true" size="10" guid="5c63b3f6-bad5-4ac0-8bab-16e93d194cb7" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="lpu" vocName="vocCardIndex" property="cardIndex" fieldColSpan="3" horizontalFill="true" label="Картотека"/>
        </msh:row>
        <msh:row guid="24ce0983-813b-4cc4-a329-f7487c49b66c">
          <msh:textField viewOnlyField="true" property="dateRegistration" label="Дата регистрации" horizontalFill="true" size="10" guid="e4905c10-14f2-471f-a07a-9d3119230614" />
          <msh:textField viewOnlyField="true" property="registrator" label="Регистратор" horizontalFill="true" size="10" guid="e4905c10-14f2-471f-a07a-9d3119230614" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" guid="38b40884-2beb-4370-b8c6-9f3e9b1b1183" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="poly_medcardForm" guid="d23e5168-1840-4e42-b681-7d8b84945666">
	  <msh:ifInRole roles="/Policy/Poly/Ticket/View">
	      <msh:section title="Открытые талоны" createRoles="/Policy/Poly/Ticket/Create" viewRoles="/Policy/Poly/Ticket/View" 
	      shortList="entityParentShortList-poly_ticket.do?id=${param.id}" 
	      createUrl="entityParentPrepareCreate-poly_ticket.do?id=${param.id}" 
	      listUrl="entityParentList-poly_ticket.do?id=${param.id}">
	        <msh:sectionContent guid="6963aae2-0581-4f08-8844-279f55ea6b45">
	        <poly:parentTicketListTag formName="poly_ticketForm" attribute="tickets"/>
	          <msh:table name="tickets" action="entityParentEdit-poly_ticket.do" idField="id" guid="2efa0c8f-b1ce-4046-90bc-2726273449b4">
	            <msh:tableColumn columnName="Дата выдачи" property="dateCreate" guid="2a999db1-fa0c-4e31-a4d2-3a143b2531bb" />
	            <msh:tableColumn columnName="Дата приема" property="date" guid="2a999db0c-4e31-a4d2-3a143b2531bb" />
	            <msh:tableColumn columnName="Номер" property="id" guid="2a9991-fa0c-4e31-a4d2-3a143b2531bb" />
	            <msh:tableColumn columnName="Специалист" property="workFunctionInfo" guid="f25d6806-aba9-45c9-b5f8-c85f94a5062b" />
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
      <msh:ifFormTypeIsView formName="poly_medcardForm" guid="67544104-2df3-4f80-a9a4-b119f24ce63c">
		<msh:sideLink name="Просмотр инф. о заключениях по медкарте" action="/js-poly_protocol-infoByMedcard.do" params="id"/>
      
        <msh:sideLink roles="/Policy/Poly/Medcard/Edit" key="ALT+2" params="id" action="/entityEdit-poly_medcard" name="Изменить" guid="6d095b79-2e4a-45a2-8811-96f9d293397b" />
      </msh:ifFormTypeIsView>
     
      <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm" guid="2a67d8c4-df82-47e4-a97e-5b3481b01a72">
        <msh:sideLink roles="/Policy/Poly/Medcard/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-poly_medcard" name="Удалить" confirm="Удалить медкарту?" guid="377c78a9-29eb-4d58-ab86-4a58431b71f1" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
	    <msh:sideMenu title="Добавить">
	        <msh:sideLink roles="/Policy/Poly/Ticket/Create" key="CTRL+1" params="id" action="/entityParentPrepareCreate-poly_ticket" name="Талон" title="Создать новый талон" guid="0b67da68-32d4-4ad5-8582-ba1faa76640c" />
	        <msh:sideLink roles="/Policy/Poly/ShortTicket/Create" key="CTRL+2" params="id" action="/entityParentPrepareCreate-poly_short_ticket" name="Талон на прием" title="Создать талон на прием" guid="0b67da68-32d4-4ad5-8582-ba1faa76640c" />
	    </msh:sideMenu>
    <msh:sideMenu title="Показать все">
        <msh:sideLink roles="/Policy/Poly/Ticket/Edit" key="ALT+4" params="id" action="/entityParentList-poly_ticket" name="Талоны" guid="661fe852-e096-410a-9fab-86d8e75db177" title="Все талоны по мед.карте" />
    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
    <tags:ticket_finds currentAction="medcard"/>    
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_medcardForm" guid="8d54b767-f785-4513-8a11-3dd5d2112e48" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeAreViewOrEdit formName="poly_medcardForm">
  <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
  	<script type="text/javascript">
  	PatientService.infoByPolicy($('person').value, {
    		callback: function(aResult) {
    			//alert(aResult) ;
    			if (aResult.length>0) {
    				$('medPolicyInformation').innerHTML = aResult ;
    				$('medPolicyInformation').style.display = 'block' ;
    			} else {
    			$('medPolicyInformation').style.display = 'none' ;
    			}
    		}
    	});
  	</script>
  </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
</tiles:insert>

