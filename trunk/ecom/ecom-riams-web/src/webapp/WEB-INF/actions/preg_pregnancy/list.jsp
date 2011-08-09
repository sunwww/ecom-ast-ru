<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех беременностей" guid="c951c449-0ed2-489d-9163-da1263effaa3" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-preg_pregnancy.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn columnName="Информация о беременности" property="information" guid="dk29-5653-4920-bb78-168ha34"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_pregnancy" name="Новую беременность" title="Добавить беременность" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="241de050-e15f-4b4d-bc49-3c311ecd7c75">
      <msh:sideLink key="ALT+2" roles="/Policy/Mis/Patient/View" params="patient" action="/entityView-mis_patient" name="Пациент" guid="ac6f1e63-a6e4-4338-8fc3-d3b6c417b7f4" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

