<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >



  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Сводный лист назначений" />
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="сводный лист назначений" guid="29345263-7743-4455-879e-130b73690294" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" guid="a47dfc0b-97d1-4cb5-b904-4ff717e612a7" />
  </tiles:put>
  <tiles:put name="body" type="string">

    <msh:section title="Список листов назначений">
    
    <msh:sectionContent>
	    <msh:table name="list" action="entityParentView-pres_prescriptList.do" idField="id" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543">
	      <msh:tableColumn columnName="Назначил" property="workFunctionInfo" guid="44482100-2200-4c8b-9df5-4f5cc0e3fe68" />
	      <msh:tableColumn columnName="Комментарии" property="comments" guid="5c893448-9084-4b1a-b301-d7aca8f6307c" />
	      <msh:tableColumn columnName="Дата создания" property="createDate" guid="dbe4fc52-03f7-42af-9555-a4bee397a800" />
	      <msh:tableColumn columnName="Период актуальности" property="periodActual"/>
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <tags:templatePrescription record="2" parentId="${param.id}" name="new" />
    <tags:pres_prescriptByList field="pl.medCase_id='${param.id}' or pl.medCase_id=ANY(select id from medcase where parent_id='${param.id}') or pl.medCase_id=ANY(select id from medcase where parent_id=(select parent_id from medcase where id='${param.id}'))" />
  </tiles:put>
</tiles:insert>

