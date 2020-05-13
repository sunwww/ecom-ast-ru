<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список переливаний за случай медицинского обслуживания" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySubclassView-trans_transfusion.do" idField="id">
      <msh:tableColumn columnName="Номер в журнале" property="journalNumber" />
      <msh:tableColumn columnName="Дата приготовления" property="preparationDate" />
      <msh:tableColumn columnName="Дата начала" property="startDate" />
      <msh:tableColumn columnName="Доза" property="doze" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
            <msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Blood/Create" params="id" action="/entityParentPrepareCreate-trans_blood" name="Переливание крови и её компонентов" title="Добавить донорской крови и её компонентов" />
      	<msh:sideLink roles="/Policy/Mis/MedCase/Transfusion/Other/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-trans_other" name="Переливание кровезамещающих растворов" title="Добавить переливание кровезамещающих растворов" />
	</msh:sideMenu>
  </tiles:put>
</tiles:insert>

