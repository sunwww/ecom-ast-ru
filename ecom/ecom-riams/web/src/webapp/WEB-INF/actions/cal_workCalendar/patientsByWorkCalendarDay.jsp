<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="" title="Список пациентов">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu />
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="list" nativeSql="select visit.id, patient.lastname, patient.firstname, patient.middlename, visit.timeExecute  from MedCase visit &#xA;inner join Patient patient on visit.patient_id = patient.id where visit.datePlan_id=${param.id}" />
    <msh:table name="list" action="entityView-smo_visit.do" idField="1">
      <msh:tableColumn columnName="Фамилия" property="2" />
      <msh:tableColumn columnName="Имя" identificator="false" property="3" />
      <msh:tableColumn columnName="Отчество" identificator="false" property="4" />
      <msh:tableColumn columnName="Время исполнения" identificator="false" property="5" />
    </msh:table>
  </tiles:put>
</tiles:insert>

