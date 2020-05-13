<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Карта обратившегося за психиатрической помощью по участку
    	  -->
    <msh:form action="/entityParentSaveGoView-psych_lpuAreaPsychCareCard.do" defaultField="startDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="startDate" label="Дата взятия"/>
        	<msh:autoComplete size="100" vocName="vocPsychObservationReason" property="observationReason" label="Причина наблюдения"/>
        </msh:row>

        <msh:row>
        	<msh:autoComplete property="lpuArea" horizontalFill="true" fieldColSpan="3" label="Участок ЛПУ" vocName="lpuArea"/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="psych_lpuAreaPsychCareCardForm">
        	<msh:row>
        		<msh:autoComplete property="ambulatoryCare" vocName="vocPsychAmbulatoryCare" label="Вид наблюдения" fieldColSpan="3" horizontalFill="true"/>
        	</msh:row>
        	<msh:row>
        		<msh:autoComplete parentAutocomplete="ambulatoryCare" property="dispensaryGroup" vocName="vocPsychDispensaryGroup" label="Группа" fieldColSpan="3" horizontalFill="true"/>
        	</msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:textField property="transferDate" label="Дата перевода"/>
        	<msh:autoComplete size="100" vocName="vocPsychTransferReason" property="transferReason" label="Причина перевода"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="finishDate" label="Дата снятия"/>
        	<msh:autoComplete size="100" vocName="vocPsychStrikeOffReason" property="stikeOffReason" label="Причина снятия"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete label="Переведен в интернат" fieldColSpan="3" property="otherPlaceTransfer" vocName="vocPsychOtherPlaceTransfer" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
            <msh:label property="createUsername" label="Пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="psych_lpuAreaPsychCareCardForm">
      <msh:ifInRole roles="/Policy/Mis/Psychiatry/CareCard/PsychiaticObservation/View">
        <msh:section>
          <msh:sectionTitle>
            Динамика наблюдений по участку.
            <a href="entityParentPrepareCreate-psych_observation.do?id=${param.id}">Добавить</a>
            <a href="entityParentList-psych_observation.do?id=${param.id}">Перейти к списку по пациенту</a>
          </msh:sectionTitle>
          <msh:sectionContent>
            <ecom:webQuery name="observations" nativeSql="select o.id,o.startdate
            ,vac.name as vacname,vdg.name as vdgname 
            from PsychiaticObservation o 
            left join VocPsychDispensaryGroup vdg on vdg.id=o.DispensaryGroup_id 
            left join VocPsychAmbulatoryCare vac on vac.id=o.AmbulatoryCare_id  
            where o.lpuAreaPsychCareCard_id='${param.id}' 
             
            order by o.startDate" />
            <msh:table name="observations" idField="1" action="entityParentView-psych_observation.do">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="2" columnName="Дата начала наблюдения"/>
              <msh:tableColumn property="3" columnName="Вид амбулаторного наблюдения"/>
              <msh:tableColumn property="4" columnName="Диспансерная группа"/>
            </msh:table>
          </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_lpuAreaPsychCareCardForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeAreViewOrEdit formName="psych_lpuAreaPsychCareCardForm">
  
    <msh:sideMenu title="Карта обратившегося за психиатрической помощью по участку">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_lpuAreaPsychCareCard" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_lpuAreaPsychCareCard" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Delete"  />
    </msh:sideMenu>
        <msh:sideMenu title="Дополнительно">
     <msh:sideLink roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Create"  key="ALT+2"
    	name="Перевод &larr;"   action="/javascript:goTransfer('.do')"  
    	 title='Перевод'  />
     <msh:sideLink roles="/Policy/Mis/Psychiatry/CareCard/LpuAreaPsychCareCard/Create"  key="ALT+2"
    	name="Изменить карта псих. пациент &larr;"   action="/javascript:goEditCareCard('.do')"  
    	 title='Изменить псих. карту пациента'  />
    
    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
      <tiles:put name="javascript" type="string">
        <script type="text/javascript">//var theBedFund = $('bedFund').value;

      	function goTransfer() {
      		window.location = 'entityParentPrepareCreate-psych_lpuAreaPsychCareCard.do?id='+$('careCard').value+"&tmp="+Math.random() ;
      	}
      	function goEditCareCard() {
      		window.location = 'entityParentEdit-psych_careCard.do?id='+$('careCard').value+"&tmp="+Math.random() ;
      	}
      	</script>
  </tiles:put>
</tiles:insert>