<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_statisticStubNewForm">
      <msh:sideMenu title="Стат.карты">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-stac_statisticStubNew" 
        	name="Изменить" 
        	roles="/Policy/Config/Hospital/StatisticStubNew/Edit" />
        <msh:sideLink key="ALT+2" confirm="Создать следующий номер стат. карты за новый год по ЛПУ?" 
        	params="" 
        	action="/entityPrepareCreate-stac_statisticStubNew" name="Создать" 
        	roles="/Policy/Config/Hospital/StatisticStubNew/Create" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" 
        	action="/entityDelete-stac_statisticStubNew" name="Удалить" 
        	roles="/Policy/Config/Hospital/StatisticStubNew/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink key="ALT+3" params="" action="/entityList-stac_statisticStubNew" name="⇧Cписок годов" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" title="Перейти к списку случаев лечения в отделении" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Следующий номер стат карты по году
    	  -->
    <msh:form action="/entitySave-stac_statisticStubNew.do" defaultField="transferDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel colsWidth="20% 30%">
        <msh:row>
          <msh:autoComplete vocName="lpu" property="lpu" label="Лечебное учреждение" fieldColSpan="6" 
          	horizontalFill="true" size="30" />
        </msh:row>
        
        <msh:row >
          <msh:autoComplete vocName="vocPigeonHole" property="pigeonHole" label="Приемник" fieldColSpan="6" 
          	horizontalFill="true" size="30" />
        </msh:row>
        
        <msh:row>
          <msh:textField property="year" label="Год" />
          <msh:textField property="code" label="Номер" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPlan"/>
        	<msh:checkBox property="isEmergency"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="stac_statisticStubNewForm" />
  </tiles:put>

</tiles:insert>

