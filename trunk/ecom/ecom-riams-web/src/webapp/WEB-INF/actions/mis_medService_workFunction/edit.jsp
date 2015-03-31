<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="mis_medService_workFunction" />
    <msh:sideMenu title="Прикрепление раб.функции">
      <msh:ifFormTypeIsView formName="mis_medService_workFunctionForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-mis_medService_workFunction" name="Изменить" roles="/Policy/Mis/MedService/VocWorkFunction/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_medService_workFunctionForm" >
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_medService_workFunction" name="Удалить" roles="/Policy/Mis/MedService/VocWorkFunction/Delete"/>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="mis_medService_workFunctionForm" >
      <msh:sideMenu title="Дополнительно" >
        <tags:diary_additionMenu />
      </msh:sideMenu>
      <tags:voc_menu currentAction="medService"/>
    </msh:ifFormTypeAreViewOrEdit>
    
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entityParentSaveGoParentView-mis_medService_workFunction.do" defaultField="vocMedService" >
      <msh:hidden property="id" />
      <msh:hidden property="medService" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="20% 30%">
        <msh:row>
          <msh:autoComplete vocName="vocWorkFunction" property="vocWorkFunction" label="Раб. функция" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="serviceWorkFunction" property="workFunction" label="Специалист (группа)" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="lpu" property="lpu" label="ЛПУ" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocBedSubType" property="bedSubType" label="Тип коек" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocRoomType" property="roomType" label="Уровень палат" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" vocName="vocPrescriptType" fieldColSpan="3" horizontalFill="true" label="Тип назначения"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="noActiveByPrescript" fieldColSpan="3" label="Не отображать в назначениях"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="listIdc10" label="Список диагнозов" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
      
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="mis_medService_workFunctionForm"/>
  </tiles:put>
</tiles:insert>

