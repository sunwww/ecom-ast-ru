<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_templateForm" title="Назначение лекарственного средства"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Prescription/Template/DrugPrescription/Create" key="ALT+N" action="/entityPrepareCreate-pres_drugPrescription" name="Добавить лекарство" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-pres_template_drugPrescription.do" idField="id">

      <msh:tableColumn columnName="Лекарственное средство" property="drug"/>
      <msh:tableColumn columnName="Способ введения" property="planStartDate" />
      <msh:tableColumn columnName="Частота" property="planEndDate"/>
      <msh:tableColumn columnName="Тип приема" property="prescriptorInfo"/>
      <msh:tableColumn columnName="Время" property="cancelDate"/>
      <msh:tableColumn columnName="Продолжительность" property="cancelWorkerInfo"/>
      <msh:tableColumn columnName="Количество" property="fulfilmentState"/>
    </msh:table>
  </tiles:put>
</tiles:insert>

