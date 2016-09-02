<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_assessmentCardForm" guid="helloItle-123" mainMenu="Patient" title="Пациент" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/AssessmentCard/Create" key="ALT+N" action="/entityParentPrepareCreate-mis_assessmentCard" name="Создать новую карту" params="id" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="cardList" nameFldSql="cardList_sql" nativeSql="select ac.id, act.name, to_char(ac.datestart,'dd.MM.yyyy') as priemDate
  from accessmentCard ac 
  left join accessmentcardtemplate act on act.id=ac.type
  where ac.patient_id=${param.id} order by ac.datestart desc"/> 
    <msh:table name="cardList" action="entityParentView-mis_assessmentCard.do" idField="1" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
      
      <msh:tableColumn columnName="Название" property="2" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Дата приема" property="3" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
  </tiles:put>
</tiles:insert>
