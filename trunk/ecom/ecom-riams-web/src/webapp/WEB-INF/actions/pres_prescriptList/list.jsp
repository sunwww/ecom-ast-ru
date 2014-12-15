<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Сводный лист назначений" />
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="сводный лист назначений" guid="29345263-7743-4455-879e-130b73690294" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" guid="a47dfc0b-97d1-4cb5-b904-4ff717e612a7" />
    <msh:sideMenu title="Добавить" guid="60616958-11ef-48b0-bec7-f6b1d0b8463f">
      <msh:sideLink roles="/Policy/Mis/Prescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_prescriptList" name="Сводный  лист назначений" guid="1faa5477-419b-4f77-8379-232e33a61922" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Create" key="ALT+4" action=".javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="ЛН из шаблона" guid="2a2c0ab6-4a46-41f7-8221-264de893815c" title="Добавить лист назначений из шаблона" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">

    <msh:section>
    <msh:sectionTitle>Список листов назначений</msh:sectionTitle>
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
    <tags:pres_prescriptByList field="pl.medCase_id='${param.id}'" />
  </tiles:put>
</tiles:insert>

