<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_newBornInformationForm">
      <msh:sideMenu title="Беременность">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_newBornInformation" name="Изменить" roles="/Policy/Mis/Pregnancy/NewBornInformation/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_newBornInformation" name="Удалить" roles="/Policy/Mis/Pregnancy/NewBornInformation/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_newBornInformation.do" defaultField="fillingDate">
      <msh:hidden property="id" />
      <msh:hidden property="confinedExchangeCard" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:textField property="fillingDate" label="Дата" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthCondition" label="Состояние при рождении" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dischargeCondition" label="Состояние при выписке" horizontalFill="true" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthWeight" label="Вес при рождении" />
          <msh:textField property="birthHeight" label="Рост при рождении" />
        </msh:row>
        <msh:row>
          <msh:textField property="dischargeWeight" label="Вес при выписке" />
        </msh:row>
        <msh:row>
          <msh:separator label="Мероприятия, проведенные в отношении новорожденного" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="vcgVaccination" label="Противотуберкулезная вакцинация" />
          <msh:textField property="vcgEstop" label="Причины отказа" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea property="otherActions" label="Другие мероприятия" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textArea property="postNatalFeatures" label="Особенности течения послеродового периода" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textArea property="notes" label="Особые замечания" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_newBornInformationForm" />
  </tiles:put>
</tiles:insert>

