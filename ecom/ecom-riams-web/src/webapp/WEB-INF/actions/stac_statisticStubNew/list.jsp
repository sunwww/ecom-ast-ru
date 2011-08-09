<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config">Список следующих новых номеров стат.карты по годам и ЛПУ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string" >
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-stac_statisticStubNew.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn columnName="Номер" property="id" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год" property="year" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Следующий номер" property="code" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="ЛПУ" property="lpuInfo" guid="b82b13ae-74fe-4614-966e-21f8d95e4418" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink params="" confirm="Создать следующий номер стат. карты за новый год?"
      	 	action="/entityPrepareCreate-stac_statisticStubNew" name="Создать" 
      	 	title="Создать следующий номер стат. карты за новый год по ЛПУ" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" 
      	 	roles="/Policy/Config/Hospital/StatisticStubNew/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

