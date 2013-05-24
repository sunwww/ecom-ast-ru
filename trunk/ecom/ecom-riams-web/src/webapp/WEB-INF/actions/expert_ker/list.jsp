<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" guid="helloItle-123" mainMenu="Lpu" title="КЭР" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Create" key="ALT+N"
       action="/entityParentPrepareCreate-expert_ker_direct" name="Создать направление на ВК" params="id" />
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/MedCase/ClinicExpertCard/Create" key="ALT+N"
       action="/entityParentPrepareCreate-expert_ker" name="Создать ВК" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section createRoles="/Policy/Mis/MedCase/ClinicExpertCard/Direct/Create" createUrl="entityParentPrepareCreate-expert_ker_direct.do?id=${param.id}" title="Направление на ВК">
    <msh:table name="list" action="entityParentView-expert_ker.do" idField="id" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
      <msh:tableColumn columnName="ИД" property="id" guid="0696a7-ed40-4ebf-a274-1e4" />
    </msh:table>
  </msh:section>
  </tiles:put>
</tiles:insert>

