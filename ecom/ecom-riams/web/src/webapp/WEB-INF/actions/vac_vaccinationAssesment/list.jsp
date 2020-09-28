<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" title="Оценка">Оценка</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Vaccination/VaccinationAssesment/Create" key="ALT+N" action="/entityPrepareCreate-vac_vaccinationAssesment" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-vac_vaccinationAssesment.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Проба" property="assesmentDate" />
    </msh:table>
  </tiles:put>
</tiles:insert>

