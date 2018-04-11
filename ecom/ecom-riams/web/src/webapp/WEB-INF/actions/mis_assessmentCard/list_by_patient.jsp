<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" guid="helloItle-123" mainMenu="Patient" title="Пациент" />
  </tiles:put>
  <!--tiles:put name="side" type="string"-->
    <!--msh:sideMenu guid="helloSideMenu-123"-->
      <!--msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/AssessmentCard/Create" key="ALT+N" action="/entityParentPrepareCreate-mis_assessmentCard" name="Создать новую карту" params="id" /-->
    <!--/msh:sideMenu-->
  <!--/tiles:put-->
  <tiles:put name="body" type="string">
  <ecom:webQuery name="cardList" nameFldSql="cardList_sql" nativeSql="select ac.id, act.name, to_char(ac.startDate,'dd.MM.yyyy') as priemDate
  ,ac.ballsum as f4_ballsum
  from assessmentCard ac 
  left join assessmentcardtemplate act on act.id=ac.template
  where ac.patient=${param.id} order by ac.startDate desc"/> 
  <msh:section createRoles="/Policy/Mis/AssessmentCard/Create" createUrl="entityParentPrepareCreate-mis_assessmentCard.do?id=${param.pat}"
		title="Карты оценки">
    <msh:table name="cardList" action="entityParentView-mis_assessmentCard.do" idField="1" guid="e699b892-d71e-4622-ae5e-eaec3ed85bb4">
      
      <msh:tableColumn columnName="Название" property="2" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Дата приема" property="3" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Сумма баллов" property="4" guid="f34e-392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
    </msh:section>
  </tiles:put>
</tiles:insert>

