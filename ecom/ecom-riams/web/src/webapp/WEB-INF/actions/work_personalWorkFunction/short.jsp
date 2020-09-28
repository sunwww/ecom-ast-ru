<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Рабочая функция к специалистам
    	  -->
    <msh:form action="/entityParentSaveGoView-work_personalWorkFunction.do" defaultField="workFunctionName"
    title="<a href='entityView-work_personalWorkFunction.do?id=${param.id}'>Рабочая функция</a>">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpuRegister" />
      <msh:hidden property="worker" />
      <msh:panel>
        <msh:row>
          <msh:textField property="code" horizontalFill="true" fieldColSpan="3" label="Код"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-voc_workFunction.do" vocName="vocWorkFunction" property="workFunction" fieldColSpan="3" label="Название функции" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="groupWorkFunction" property="group" fieldColSpan="3" label="Групповая рабочая функция" size="50" viewAction="entityParentView-work_groupWorkFunction.do" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="userView.do" vocName="secUser" property="secUser" label="Вход в систему" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="registrationInterval" label="Интервал регистр." horizontalFill="true"/>
          <msh:checkBox property="archival" label="Архив" />
        </msh:row>
        <msh:ifFormTypeIsCreate formName="work_personalWorkFunctionForm">
        	<msh:row>
        		<msh:checkBox property="isCalendarCreate" label="Создавать календарь"/>
        	</msh:row>
<%--        	<msh:row>
        		<msh:separator label="Пользователь" colSpan="4"/>
        	</msh:row>
        	<msh:panel colsWidth="15%, 5%, 5%,-" styleId="tableUser">
        	<msh:row><msh:textField property="userForm.login" label="Пользователь"/></msh:row>
        <msh:row>
          <msh:textField property="userForm.fullname" label="Полное имя" size="50" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="userForm.password" label="Пароль" size="20" passwordEnabled="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textArea property="userForm.comment" label="Комментарий" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        </msh:panel>--%>
        </msh:ifFormTypeIsCreate>
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
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:sideLink action="/javascript:createSecUser('.do')"  name="Добавить пользователя" roles="/Policy/Jaas/SecUser/Create" styleId="createSecUser"/>
  	<msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
		<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
  		<script type="text/javascript">
  			function generationCalendar(){
  				WorkCalendarService.generateBySpecialist(
					//Long aCalendarDay, String aCalendarTime, Boolean aMinIs,
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
  	<msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
      <msh:section title="Календарь">
        <ecom:parentEntityListAll formName="cal_workCalendarForm" attribute="childs" />
        <msh:table viewUrl="entityShortView-cal_workCalendar.do" name="childs" action="entityParentView-cal_workCalendar.do" idField="id">
          <msh:tableColumn columnName="ИД" property="id" />
        </msh:table>
      </msh:section>
      
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/JournalPattern/View">
        <msh:section title="Журналы шаблонов">
          <ecom:webQuery name="journals" nativeSql="select 
          jpc.id,wcp.name,jpc.noActive,jpc.dateFrom,jpc.dateTo 
          from JournalPatternCalendar jpc
          left join WorkCalendarPattern wcp on wcp.id=jpc.pattern_id
          where jpc.workCalendar_id='${param.id}'
          " />
          <msh:table deleteUrl="entityParentDeleteGoParentView-work_journalPatternCalendar.do" 
          editUrl="entityParentEdit-work_journalPatternCalendar.do" 
          viewUrl="entityShortView-work_journalPatternCalendar.do" 
			name="journals" 
			action="entityParentView-work_journalPatternCalendar.do" idField="1">
            <msh:tableColumn columnName="ИД" property="1" />
            <msh:tableColumn columnName="Информация о шаблоне" property="2" />
            <msh:tableColumn columnName="Отключен" property="3" />
            <msh:tableColumn columnName="Действует с" property="4" />
            <msh:tableColumn columnName="Действует по" property="5" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="work_personalWorkFunctionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
      <msh:sideMenu title="Рабочая функция">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-work_personalWorkFunction" name="Изменить" roles="/Policy/Mis/Worker/WorkFunction/Create" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-work_personalWorkFunction" name="Удалить" roles="/Policy/Mis/Worker/WorkFunction/Delete" />
        <msh:sideLink action="/javascript:generationCalendar('.do')" name="Генерировать" roles="/Policy/Mis/Worker/WorkFunction/View" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_workCalendar" name="Календарь" title="Добавить календарь" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать" />
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsView formName="work_personalWorkFunctionForm">
		<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
  		<script type="text/javascript">
  			function generationCalendar(){
  				WorkCalendarService.generateBySpecialist(
					//Long aCalendarDay, String aCalendarTime, Boolean aMinIs,
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
  		</script>
  	</msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="work_personalWorkFunctionForm">
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
      	 }</script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

