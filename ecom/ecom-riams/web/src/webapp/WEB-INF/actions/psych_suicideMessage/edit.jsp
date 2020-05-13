<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Суицид
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_suicideMessage.do" defaultField="regOtherLpuDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      
      <msh:panel>
      	<msh:row>
      		<msh:textField property="regOtherLpuDate" label="Дата заполнения"/>
      		<msh:textField property="regOtherLpuTime" label="время"/>
      	</msh:row>
      
        <msh:row>
        	<msh:autoComplete property="regOtherLpu" label="ЛПУ" horizontalFill="true" fieldColSpan="3" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="regDate" label="Дата заполнения в ОКПБ"/>
        	<msh:textField property="regTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="suicideDate" label="Дата суицида"/>
        	<msh:textField property="phone" label="Телефон" />
        </msh:row>
        <msh:row>
        	<msh:textField property="kinsman" label="Представитель" size="100" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="socialStatus" label="Социальный статус" vocName="vocSocialStatus"  fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="type" label="Вид" vocName="vocSuicideMesType"  fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="otherType" label="Другие виды" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="place" label="Место совершения" vocName="vocSuicideMesPlace"  fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="otherPlace" label="Другое место" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete labelColSpan="2" property="otherPeople"  label="Присутствовали др. люди при суициде" vocName="vocYesNo" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete labelColSpan="2" property="intoxication" label="Наличие нарк/алкогольного опьянения" vocName="vocYesNo" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderLpu" label="Направлен" vocName="mainLpu" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="postedLpu" label="Доставлен" vocName="mainLpu" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isFinished" label="Завершен"/>
        </msh:row>
        </msh:panel>
			<msh:panel>
		 <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>   
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView formName="psych_suicideMessageForm">
    <msh:section
     title="Осмотр после суицида"
     createRoles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Suicide/Create" createUrl="entityParentPrepareCreate-psych_suicide.do?id=${param.id}">
    <msh:sectionContent>
      	<ecom:webQuery name="listd" nativeSql="select s.id,pcc.cardNumber
  	,s.registrationDate
  	from Suicide s
  	left join PsychiatricCareCard pcc on pcc.id=s.careCard_id   
  	where s.suiMessage_id=${param.id}
  		"/>
    <msh:table name="listd" action="entityParentView-psych_suicide.do" idField="1">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="1" columnName="ИД"/>
              <msh:tableColumn property="2" columnName="№карты"/>
              <msh:tableColumn property="3" columnName="Дата осмотра"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_suicideMessageForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Суицид">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_suicideMessage" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_suicideMessage" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Delete"  />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>