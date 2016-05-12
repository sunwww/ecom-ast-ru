<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Случай смерти" guid="c951c449-0ed2-489d-9163-da1263effaa3" />
  </tiles:put>
  <tiles:put name="side" type="string" >
	<msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
	</msh:sideMenu>  
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:tableEmpty name="list">
  <msh:section createRoles="/Policy/Mis/MedCase/DeathCase/Create" createUrl="entityParentPrepareCreate-stac_deathCase.do?id=${param.id}" title="Случай смерти">
</msh:section>  
  </msh:tableEmpty>
  <msh:tableNotEmpty name="list">
   <msh:section title="Случай смерти">
    <msh:table name="list" action="entityParentView-stac_deathCase.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30"
    editUrl="entityParentEdit-stac_deathCase.do" >
      <msh:tableColumn columnName="Номер" property="id" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата смерти" property="deathDate" guid="34c6a-2b47-4feb-a3fa-5cd41d" />
      <msh:tableColumn columnName="Время смерти" property="deathTime" guid="c56a-2b47-4feb-a3fa-5cx6c41d" />
    </msh:table>
   </msh:section>
  </msh:tableNotEmpty>
 
  </tiles:put>
  <tiles:put name="side" type="string">
   
   
  </tiles:put>
</tiles:insert>

