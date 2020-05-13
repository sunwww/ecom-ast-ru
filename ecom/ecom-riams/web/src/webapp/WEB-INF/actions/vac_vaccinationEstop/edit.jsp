<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-vac_vaccinationEstop.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="vaccination" />
      <msh:panel>
        <msh:row>
          <msh:row>
            <msh:checkBox property="noActuality" label="Недействительность" />
          </msh:row>
          <msh:textField property="estopDate" label="Дата" />
          <msh:textField property="dateFinish" label="Дата окончания" />
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entityView-vac_vaccinationEstop.do" vocName="vocVaccinationEstopType" property="estopType" fieldColSpan="3" label="Тип" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea hideLabel="false" property="comments" viewOnlyField="false" label="Комментарий" fieldColSpan="3" rows="5" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="vac_vaccinationEstopForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="vac_vaccinationEstopForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-vac_vaccinationEstop" name="Изменить" roles="/Policy/Mis/Vaccination/VaccinationEstop/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-vac_vaccinationEstop" name="Удалить" roles="/Policy/Mis/Vaccination/VaccinationEstop/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

