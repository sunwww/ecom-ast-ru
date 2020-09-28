<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Назначения" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Create" key="ALT+2" action="/entityParentPrepareCreate-pres_drugPrescription" name="Назначение на лекарство" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/DietPrescription/Create" key="ALT+3" action="/entityParentPrepareCreate-pres_dietPrescription" name="Назначение диеты" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Create" key="ALT+4" action="/entityParentPrepareCreate-pres_servicePrescription" name="Назначение на услугу" params="id" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать">
      <msh:sideLink params="id" action="/entityParentList-pres_drugPrescription" name="Список лекарственных назначений" key="ALT+5" />
      <msh:sideLink params="id" action="/entityParentList-pres_dietPrescription" name="Список назначенных диет" key="ALT+6" />
      <msh:sideLink params="id" action="/entityParentList-pres_servicePrescription" name="Список назначений на мед.услугу" key="ALT+7" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<tags:pres_prescriptByList field="pl.id='${param.id}'" />
  </tiles:put>
</tiles:insert>

