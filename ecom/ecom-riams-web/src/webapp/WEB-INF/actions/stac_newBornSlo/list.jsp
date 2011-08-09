<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="preg_newBornForm" mainMenu="Patient" title="Список всех СЛО по новорожденному" guid="c951c449-0ed2-489d-9163-da1263effaa3" />
  </tiles:put>
  <tiles:put name="side" type="string" >
	<msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
	</msh:sideMenu>  
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-stac_newBornSlo.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn columnName="Номер" property="id" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Отделения" property="departmentInfo" guid="b82b13ae-74fe-4614-966e-21f8d95e4418" />
      <msh:tableColumn property="transferDepartmentInfo" guid="f249b47b-67f9-4d31-ab89-e8984bd63cf5" columnName="Отделение перевода" />
      <msh:tableColumn columnName="Дата поступления" property="dateStart" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата перевода" property="transferDate" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="dateFinish" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Кол-во дней" property="daysCount" guid="dk29-5653-4920-bb78-168ha34"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_newBornSlo" name="Новый СЛО по новорожденному" title="Добавить случай стационарного лечения в отделении по новорожденному" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

