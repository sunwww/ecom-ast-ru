<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Рабочая функция к специалистам
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoView-work_personalWorkFunction.do" defaultField="code">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="lpuRegister" guid="384a5a43-d9f9-464e-a36b-bcf6e2e8c6d4" />
      <msh:hidden property="worker" guid="caf14c3e-853a-4150-aeee-c85053c88d72" />
      <msh:panel guid="panel">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:textField property="code" horizontalFill="true" fieldColSpan="3" label="Код"/>
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete viewAction="entityView-voc_workFunction.do" vocName="vocWorkFunction" property="workFunction" guid="3a3e4d1b-8802-467d-b205-715fb379b018" fieldColSpan="3" label="Название функции" size="50" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocAcademicDegree" property="degrees" fieldColSpan="1" label="Учёная степень" size="20" />
          <msh:autoComplete vocName="vocCategory" property="category" fieldColSpan="1" label="Категория" size="20" />
        </msh:row>
        <msh:row guid="b5f4-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="groupWorkFunction" property="group" guid="3a3e4d1b-880-b205-715fb379b018" fieldColSpan="3" label="Групповая рабочая функция" size="50" viewAction="entityParentView-work_groupWorkFunction.do" />
        </msh:row>
        <msh:row guid="39f80ce0-5e25-41b9-a530-d406d84bfc00">
          <msh:autoComplete viewAction="userView.do" vocName="secUser" property="secUser" label="Вход в систему" guid="8754e635-11ce-4c73-b398-4479988fd60d" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="registrationInterval" label="Интервал регистр." horizontalFill="true"/>
          <msh:checkBox property="archival" label="Архив" />
        </msh:row>
        <msh:row>
           <msh:autoComplete vocName="vocAttorney" property="attorney" label="Использует доверенность" guid="8754e635-11ce-4c73-b398-4479988fd60d" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isSurgical" label="Оперирующий"/>
        	<msh:checkBox property="isInstrumentNurse" label="Опер.сестра"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isAdministrator" label="Начальник"/>
        	<msh:checkBox property="emergency" label="Экстр. пункт"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isRotation" label="Совместитель"/>
        	<msh:checkBox property="isNoP7Sync" label="Не синхронизировать с \"Парус-7\""/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isNoDirectSelf" label="Запрет на создание направление к самому себе" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
          <msh:row>
              <msh:checkBox property="isDirectionNoTime" label="Разрешить создавать направления без указания времени" fieldColSpan="3" horizontalFill="true"/>
          </msh:row>
        <msh:ifFormTypeIsCreate formName="work_personalWorkFunctionForm">
        	<msh:row>
        		<msh:checkBox property="isCalendarCreate" label="Создавать календарь"/>
        	</msh:row>
<%--        	<msh:row>
        		<msh:separator label="Пользователь" colSpan="4"/>
        	</msh:row>
        	<msh:panel colsWidth="15%, 5%, 5%,-" styleId="tableUser" guid="aada20f1-2321-43bf-a416-ddaf511e15a0">
        	<msh:row><msh:textField property="userForm.login" label="Пользователь"/></msh:row>
        <msh:row guid="50ea4530-3971-47e2-9595-485542f9bde5">
          <msh:textField property="userForm.fullname" label="Полное имя" size="50" horizontalFill="true" fieldColSpan="3" guid="fe5da194-9672-4c06-a8f2-be0d6a622b35" />
        </msh:row>
        <msh:row guid="71f70458-5150-4cdc-bf8d-a75ba0c26818">
          <msh:textField property="userForm.password" label="Пароль" size="20" passwordEnabled="true" fieldColSpan="3" guid="9c145321-5a15-4d06-be73-881ddde7cf84" />
        </msh:row>
        <msh:row guid="feafa852-b426-470a-baa1-46187ba71a45">
          <msh:textArea property="userForm.comment" label="Комментарий" horizontalFill="true" fieldColSpan="3" guid="d4fda195-9f04-4511-b5dc-a37a1691b735" />
        </msh:row>
        </msh:panel>--%>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:checkBox property="isNoViewRemoteUser"/>
        	<msh:autoComplete property="copyingEquipmentDefault" vocName="copyingEquipment" 
        	horizontalFill="true" label="Принтер"/>
        </msh:row>
          <msh:row>
            <msh:autoComplete property="kkmEquipmentDefault" vocName="kkmEquipment"
                              horizontalFill="true" label="ККМ"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="comment" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
          <msh:row>
              <msh:separator label="Электронная очередь" colSpan="4"/>
          </msh:row>
          <msh:row>
              <msh:autoComplete property="queue" vocName="vocTicketQueue" fieldColSpan="2" size="100" label="Тип очереди"/>
              <msh:textField property="windowNumber" label="Номер окна"/>
          </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="work_personalWorkFunctionForm">
     <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/View">
      <msh:section guid="sectionChilds" title="Календарь">
        <ecom:parentEntityListAll guid="parentEntityListChilds" formName="cal_workCalendarForm" attribute="childs" />
        <msh:table viewUrl="entityShortView-cal_workCalendar.do" guid="tableChilds" name="childs" action="entityParentView-cal_workCalendar.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" guid="23eed88f-9ea7-4b8f-a955-20ecf89ca86c" />
        </msh:table>
      </msh:section>
     </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="work_personalWorkFunctionForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="work_personalWorkFunctionForm">
      <msh:sideMenu guid="sideMenu-123" title="Рабочая функция">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-work_personalWorkFunction" name="Изменить" roles="/Policy/Mis/Worker/WorkFunction/Create" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-work_personalWorkFunction" name="Удалить" roles="/Policy/Mis/Worker/WorkFunction/Delete" />
        <msh:sideLink action="/javascript:generationCalendar('.do')"  name="Генерировать" roles="/Policy/Mis/Worker/WorkCalendar/View" />
        <msh:sideLink action="/javascript:createSecUser('.do')"  name="Добавить пользователя" roles="/Policy/Jaas/SecUser/Create" styleId="createSecUser"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="53f4a828-71f4-4c29-a2e8-fd61ff083187">
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь" title="Добавить календарь" guid="2f18fed4-7259-479a-97df-ff073fc4569d" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="5fgyr3df4a828-71f4-4c29-a2e8-fd61ff083187" />
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
		<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
  		<script type="text/javascript">
  			function generationCalendar(){
  				WorkCalendarService.generateBySpecialist(
					${param.id},
			     {
						callback: function(aTime) {
							alert(aTime) ;
     					}
     					, errorHandler: function(aMessage) {
					     alert(aMessage) ;
						}
     			 }	) ;
  			}
  	      	 function createSecUser() {
  	      		window.location = 'entityPrepareCreate-secuser.do?workFunction='+$('id').value+"&tmp="+Math.random() ;
  	      	 }
  	      	if (+$('secUser').value>0) $('createSecUser').style.display='none' ;
  		</script>
  	</msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="work_personalWorkFunctionForm" guid="eca3ffa2-a4a5-44ff-a97c-1d18b8cbe521">
      <script type="text/javascript">groupAutocomplete.setParentId(getWorkerAndFunction()) ;
      	workFunctionAutocomplete.addOnChangeCallback(function() {
      	 	updateGroup() ;
      	 });
      	
      	 function updateGroup() {
      	 	groupAutocomplete.setParentId(getWorkerAndFunction()) ;
      	 	groupAutocomplete.setVocId('');
      	 }
      	 function getWorkerAndFunction() {
      	 	return $('workFunction').value+"#"+$('worker').value ;
      	 }

      	 </script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

