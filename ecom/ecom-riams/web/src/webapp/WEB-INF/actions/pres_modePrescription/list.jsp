<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Режим" guid="610fe86e-69f6-4ad0-a1dd-146453" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="Добавить">
      <msh:sideLink params="id" roles="/Policy/Mis/Prescription/ModePrescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_modePrescription" name="Назначение диеты" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" guid="b44659-2a4a-4e49-9700-31321f">
      <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentList-pres_prescription" name="К списку назначений" title="Список назначений" guid="3jg7-f85c-4e87-b447-419887e" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-pres_modePrescription.do" idField="id" guid="bhf1-1e0b-4ebd-9f58-b753b4">
      <msh:tableColumn columnName="День приема" property="planStartDate" guid="6ef-105f-43a0-be61-31590a86" />
      <msh:tableColumn columnName="Указания по приему" property="descriptionInfo" guid="d77-c64d-4748-bbee-30852b" />
      <msh:tableColumn columnName="Исполнитель" property="prescriptorInfo" guid="f31d2-3392-4978-b35931f" />
      <msh:tableColumn columnName="Комментарий" property="comments" guid="f31ds2-3392-4978-b31571f" />
    </msh:table>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	document.location.href = "entityParentList-pres_prescription.do?id=${param.id}" ;
  	</script>
  </tiles:put></tiles:insert>

