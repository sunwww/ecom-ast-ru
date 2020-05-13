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
      <msh:sideLink roles="/Policy/Mis/MedCase/ClinicExpertCard/Create" key="ALT+N"
       action="/entityParentPrepareCreate-expert_ker_direct" name="Создать новое" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-expert_ker.do" viewUrl="entityParentView-expert_ker.do?short=Short" idField="id" >
      <msh:tableColumn columnName="ИД" property="id"  />
    </msh:table>
  </tiles:put>
</tiles:insert>

