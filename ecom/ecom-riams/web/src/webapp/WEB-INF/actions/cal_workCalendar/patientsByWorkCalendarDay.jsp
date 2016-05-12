<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="" title="Список пациентов">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select visit.id, patient.lastname, patient.firstname, patient.middlename, visit.timeExecute  from MedCase visit &#xA;inner join Patient patient on visit.patient_id = patient.id where visit.datePlan_id=${param.id}" guid="b460dfg0306-4982-4258-9ef0-6954b8c6df79" />
    <msh:table name="list" action="entityView-smo_visit.do" idField="1" guid="b6fgs21e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Фамилия" property="2" guid="06oebg94f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Имя" identificator="false" property="3" guid="4210jhf5a62-1ab4-4130-914d-fa19f7e948de" />
      <msh:tableColumn columnName="Отчество" identificator="false" property="4" guid="42105a62-1ab4-4130-914d-fa19f7e948de" />
      <msh:tableColumn columnName="Время исполнения" identificator="false" property="5" guid="43e73fc9-27d8-44d0-8e10-155612ee801b" />
    </msh:table>
  </tiles:put>
</tiles:insert>

