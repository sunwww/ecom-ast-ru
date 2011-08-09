<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="StacJournal" title="Список всех хир.операций в ССЛ" guid="d65a8cc3-3360-43fb-bac7-0d7dde1057ae" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-stac_surOperation.do" idField="id" guid="9b3ab5f4-4e7c-45ab-8a40-25664427af5c">
      <msh:tableColumn columnName="Дата" property="operationDate" guid="f0ef0a0a-4690-4a70-a7df-5811b6813952" />
      <msh:tableColumn columnName="Время" property="operationTime" guid="6cbe7939-e697-4a44-9277-70f9b2a9f8a4" />
      <msh:tableColumn columnName="Операция" property="operationInfo" guid="42cd2d67-593d-4525-8203-6eb91a63a969" />
      <msh:tableColumn columnName="Хирург" property="surgeonInfo" guid="805b92e3-4d45-4918-9b8a-3a629591e030" />
      <msh:tableColumn columnName="Ассистенты" property="surgeonsInfo" guid="8045-4918-9b8a-3a629591e030" />
      <msh:tableColumn columnName="Основная" property="base" guid="f79525b3-b84f-4a3c-b625-969175f79494" />
      <msh:tableColumn columnName="Анестезия" property="anesthesiaInfo" guid="9e13f15a-244b-44fc-afc4-4eff79114103" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="73fe6c01-daa2-49fb-af12-20402ea5695b">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_surOperation" name="Хир. операцию" title="Добавить хирургическую операцию в случай стационарного лечения" guid="1eb84508-a862-4de8-b2a9-c447c2cf7cd1" />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

