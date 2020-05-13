<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" title="Список медотводов">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Vaccination/VaccinationEstop/Create" key="ALT+N" action="/entityParentPrepareCreate-vac_vaccinationEstop" name="Создать новое" params="id"/>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-vac_vaccinationEstop.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Недействительность" property="noActuality" />
      <msh:tableColumn columnName="Дата начала" property="estopDate" />
      <msh:tableColumn columnName="Дата окончания" property="dateFinish" />
    </msh:table>
  </tiles:put>
</tiles:insert>

