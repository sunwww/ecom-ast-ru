<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Disability" title="КЭР" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Create" key="ALT+N"
       action="/entityParentPrepareCreate-expert_ker_direct" name="Создать направление на ВК" params="id" />
      <msh:sideLink roles="/Policy/Mis/MedCase/ClinicExpertCard/Create" key="ALT+N"
       action="/entityParentPrepareCreate-expert_ker" name="Создать ВК" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/ClinicExpertCard/Create">
  <msh:section createRoles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Create" createUrl="entityParentPrepareCreate-expert_ker_direct.do?id=${param.id}" title="Направление на ВК">
    <msh:table name="list" action="entityParentView-expert_ker_direct.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Дата направления" property="orderDate" />
    </msh:table>
  </msh:section>
  </msh:ifNotInRole>
  <msh:ifInRole roles="/Policy/Mis/MedCase/ClinicExpertCard/Create">
  <msh:section createRoles="/Policy/Mis/MedCase/ClinicExpertCard/Create" createUrl="entityParentPrepareCreate-expert_ker.do?id=${param.id}" title="Направление на ВК">
    <msh:table name="list" action="entityParentView-expert_ker.do" idField="id">
      <msh:tableColumn columnName="ИД" property="id" />
      <msh:tableColumn columnName="Дата направления" property="orderDate" />
    </msh:table>
  </msh:section>
  </msh:ifInRole>
  </tiles:put>
</tiles:insert>

