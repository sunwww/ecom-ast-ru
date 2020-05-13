<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_templateForm" title="Назначение лекарственного средства"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/Prescription/Template/DietPrescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_template_dietPrescription" name="Назначение диеты" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать">
      <msh:sideLink roles="/Policy/Mis/Prescription/Template/View" params="id" action="/entityParentList-pres_prescription" name="К списку назначений" title="Список назначений" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-pres_template_dietPrescription.do" idField="id">
      <msh:tableColumn columnName="День приема" property="planStartDate" />
      <msh:tableColumn columnName="Указания по приему" property="descriptionInfo" />
      <msh:tableColumn columnName="Исполнитель" property="prescriptorInfo" />
      <msh:tableColumn columnName="Комментарий" property="comments" />
    </msh:table>
  </tiles:put>
</tiles:insert>
