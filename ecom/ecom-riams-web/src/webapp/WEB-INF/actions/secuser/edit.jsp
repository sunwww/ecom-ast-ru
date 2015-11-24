<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@page import="java.util.Collection"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title title="Пользователь" guid="2313862c-393b-4d86-b30e-0b3a6d6078da" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-secuser.do" defaultField="login" guid="f85bdd97-c59c-43c5-be7b-95fd11dc1344">
      <msh:hidden property="id" guid="f31c8b79-7309-4fd6-a1d7-da54e427baed" />
      <msh:hidden property="saveType" guid="6661bf96-cae4-4ae4-b216-4d9b622790d9" />
      <msh:ifFormTypeIsCreate formName="secuserForm">
      <msh:hidden property="workFunction"/>
      </msh:ifFormTypeIsCreate>
      <msh:panel guid="020e354f-560d-48a7-8a14-9e9699027a3e">
        <msh:row guid="eb8c8794-7b03-49fb-abf9-b9c86d184477">
        <msh:ifFormTypeIsCreate formName="secuserForm">
          <msh:textField property="login" label="Пользователь" guid="08315d12-8575-44d1-a6a1-408bf687b766" />
        
         </msh:ifFormTypeIsCreate>
         <msh:ifFormTypeAreViewOrEdit formName="secuserForm">
         <msh:textField property="login" label="Пользователь" viewOnlyField="true" guid="08315d12-8575-44d1-a6a1-408bf687b766" />
        
         </msh:ifFormTypeAreViewOrEdit>
          <msh:ifFormTypeIsView formName="secuserForm" guid="ac99c5fc-e76e-4720-92a4-b44cbcac9907">
            <msh:checkBox property="disable" label="Заблокирован" guid="c4d6e9dee-470f-b18f-5dcd5e38b404" />
          </msh:ifFormTypeIsView>
          <msh:ifFormTypeIsNotView formName="secuserForm" guid="a84170ee-8e8c-4fbb-ae9e-31e6d38b5369">
            <msh:ifInRole roles="/Policy/Jaas/SecUser/EditDisable" guid="323969f8-94f2-4b94-bf11-a3354b117e66">
              <msh:checkBox property="disable" label="Заблокирован" guid="c4d6e7f6-9dee-470f-b18f-5dcd5e38b404" />
            </msh:ifInRole>
          </msh:ifFormTypeIsNotView>
        </msh:row>
        <msh:row>
            <msh:ifInRole roles="/Policy/Jaas/SecUser/EditSystem" >
              <msh:checkBox property="isSystems" label="Системный" />
            </msh:ifInRole>
            <msh:ifNotInRole roles="/Policy/Jaas/SecUser/EditSystem">
              <msh:hidden property="isSystems" />
            </msh:ifNotInRole>
        </msh:row>
        <msh:row guid="50ea4530-3971-47e2-9595-485542f9bde5">
          <msh:textField property="fullname" label="Полное имя" size="50" horizontalFill="true" fieldColSpan="3" guid="fe5da194-9672-4c06-a8f2-be0d6a622b35" />
        </msh:row>
        <msh:ifFormTypeIsCreate formName="secuserForm">
        <msh:row>
        	<msh:autoComplete property="userCopy" vocName="secUser" label="Копировать роли у пользователя" fieldColSpan="2" labelColSpan="2" horizontalFill="true"/>
        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row guid="71f70458-5150-4cdc-bf8d-a75ba0c26818">
          <msh:textField property="password" label="Пароль" size="20" passwordEnabled="true" fieldColSpan="1" guid="9c145321-5a15-4d06-be73-881ddde7cf84" />
          <msh:checkBox property="isRemoteUser" label="Удаленный пол-ль"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="changePasswordAtLogin" label="Сменить пароль при входе в систему"/>
        </msh:row>
        <msh:row guid="feafa852-b426-470a-baa1-46187ba71a45">
          <msh:textArea property="comment" label="Комментарий" horizontalFill="true" fieldColSpan="3" guid="d4fda195-9f04-4511-b5dc-a37a1691b735" />
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
        	<msh:label property="passwordChangedDate" label="Дата изменения пароля"/>
        	
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
        <msh:submitCancelButtonsRow colSpan="4" guid="2e91f84b-e4f8-441d-8f8a-f8bacb7e0f17" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="secuserForm" guid="4640a7ef-b9d6-469c-b18b-07c7481c890d">
      <msh:ifInRole roles="/Policy/Jaas/SecRole/View" guid="b6fa1420-72fd-4dee-bdeb-12c58a5e51d7">
        <msh:section guid="b7c4f673-bfe8-4998-a756-97c1f8bff291">
          <msh:sectionTitle guid="e94873ec-c4d8-482d-bc05-687b8bd0611a">Роли пользователя</msh:sectionTitle>
          <msh:sectionContent guid="118c77ac-cb73-4f0e-bb5d-bbfdb42e1119">
          	<ecom:webQuery name="roles" nativeSql="select sr.id,sr.name,sr.comment from SecUser_SecRole ss left join secrole sr on sr.id=ss.roles_id where ss.secUser_id=${param.id}"/>
            <msh:table name="roles" action="roleView.do" idField="1" noDataMessage="Нет ролей" guid="bb014e96-9683-45da-b863-09e90a85fd45">
              <msh:tableColumn columnName="Название" property="2" guid="1815f52d-6cf1-4563-9d70-67594b7e06f2" />
              <msh:tableColumn columnName="Комментарий" property="3" guid="aa6a871c-93bc-4ce4-81ad-40940c3b1a91" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="secuserForm" guid="4640a7ef-b9d6-469c-b18b-07c74c890d">
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkFunction/View" guid="dfcf6e04-a278-46d3-b4b2-63b53eb4b5">
            <ecom:webQuery name="workFunction" hql="select id,COALESCE(workFunction.name,','),&#xA; worker.person.lastname || ' '|| worker.person.firstname || ' ' || worker.person.middlename&#xA;from WorkFunction where secUser.id=${param.id}" guid="e13952fe-a624-4b4d-b97f-e52768edf0db" />
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <% 
	    if (request.getAttribute("workFunction")!=null) {
	    	Collection list = (Collection)request.getAttribute("workFunction"); 
	    	if(list.size()>1) {
	    		out.println("<div class=\"errorMessage\" id='errorSecuser'>");
	    		out.println("<a href=\"#\" class=\"errorMessageClose\" title=\"Убрать сообщение об ошибке\" onclick='$(\"errorSecuser\").style.display=\"none\"'>Убрать</a>");
	    		out.println("ОШИБКА : Пользователь не должен иметь более одной рабочей фукции!!!");
	    		out.println("</div>");
	    		out.println("");
	    	}
	    }
	 %>
    <msh:ifFormTypeIsView formName="secuserForm" guid="4640a7ef-b9d6-469c-b18b-07c74c890d">
    
      <msh:ifInRole roles="/Policy/Mis/Worker/WorkFunction/View" guid="dfcf6e04-a278-46d3-b4b2-63b53eb4b5">
        <msh:section guid="ad607f37-2088-4ac7-bc01-e1fce16ff001">
          <msh:sectionTitle guid="abd1de25-407c-4669-91e1-5b9bf9dab61f">Рабочая функция</msh:sectionTitle>
          
          <msh:sectionContent guid="5075659a-5f2c-4c90-9644-b394f52fc2d1">
          
            
            <msh:table name="workFunction" action="entitySubclassView-work_workFunction.do" idField="1" guid="5fdef5ee-ac48-46eb-80ce-faca6933b9d2">
              <msh:tableColumn columnName="ФИО специалиста" identificator="false" property="3" guid="e601249e-b653-4c13-9621-524bd2bde28b" />
              <msh:tableColumn property="2" columnName="Рабочая функция" guid="e4542fb4-df71-4865-8420-f4b3510cb1d3" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="e7e68c90-9624-4f8a-9392-4eabfca9fd22">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-secuser" name="Редактировать" roles="/Policy/Jaas/SecUser/Edit" guid="aa2cd52f-8392-4edf-a2c9-80e2f309d72e" />
      <msh:sideLink key="ALT+3" params="id" action="/userRoleEdit" name="Изменить роли пользователя" roles="/Policy/Jaas/SecUser/EditRoles" guid="c81c676b-39ab-4ad3-b55d-946012bf258d" />
      <msh:sideLink action="/javascript:setDefaultPassword('.do')" name="Установить пароль по умолчанию" roles="/Policy/Jaas/SecUser/Edit" guid="c81c676b-39ab-4ad3-b55d-946012bf258d" />
    </msh:sideMenu>
    <msh:sideMenu title="Списки пользователей" guid="8a39450d-3f79-4741-9555-c379c457a830">
      <msh:sideLink action="/entityList-secuser.do?list=all" name="Все пользователи" guid="cdeac23a-ab60-49b9-a53f-d7fcbbd6bd86" styleId="listall" roles="/Policy/Jaas/SecUser/View" />
      <msh:sideLink action="/entityList-secuser.do?list=disable" name="Заблокированные" guid="58c9d4ef-772c-4370-81f4-5b8cf2f8947b" styleId="listdisable" roles="/Policy/Jaas/SecUser/View" />
      <msh:sideLink action="/entityList-secuser.do" name="Активные" title="Активные" guid="c78bd172-c888-410e-a775-a5917326cf3c" styleId="listactive" roles="/Policy/Jaas/SecUser/View" />
    </msh:sideMenu>
         <msh:sideMenu title="Безопасность">
        	<msh:sideLink styleId="selected" action="/entityList-secuser.do" roles="/Policy/Jaas/SecUser/View" name="Пользователи"/>
        	<msh:sideLink styleId="roles" action="/entityList-secrole.do" roles="/Policy/Jaas/SecRole/View" name="Роли"/>
        	<msh:sideLink styleId="policies" action="/entityParentList-secpolicy.do?id=1" roles="/Policy/Jaas/SecPolicy/View" name="Политики"/>
        	<msh:sideLink action="/serviceExport.do" roles="/Policy/Jaas/Activation" name="Активация" title="Активация"/>
        </msh:sideMenu>
 </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
    Element.addClassName($('mainMenuUsers'), "selected");
    </script>
    <msh:ifFormTypeAreViewOrEdit formName="secuserForm">
   
    <script type='text/javascript' src='./dwr/interface/RolePoliciesService.js'></script>
    
  <script type="text/javascript">
  function setDefaultPassword() {
  	RolePoliciesService.defaultPassword(
			
			$('login').value,
	     {
				callback: function(aResult) {
					if (+aResult.substring(0,1)>0) {
						alert(aResult.substring(1));
						window.location.reload() ;
					}
				}
	     });
  }
    </script>
    </msh:ifFormTypeAreViewOrEdit>
    <msh:ifFormTypeIsCreate formName="secuserForm">
    <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
    <script type="text/javascript">
    if ((+'${param.workFunction}'>0)&&($('workFunction'))) {
    	$('workFunction').value='${param.workFunction}' ;
    	
    	WorkCalendarService.getInfoByWorkFunction(
				//Long aCalendarDay, String aCalendarTime, Boolean aMinIs,
				$('workFunction').value,
		     {
					callback: function(aResult) {
						if (aResult!=null){
							var s = aResult.split("#") ;
							$('login').value = s[0] ;
							$('fullname').value = s[1] ;
							$('password').value = s[2] ;
							$('comment').value = s[3] ;
						}
 					}
 					, errorHandler: function(aMessage) {
				     alert(aMessage) ;
					}
 			 }	) ;
    }
    </script>
    </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>

