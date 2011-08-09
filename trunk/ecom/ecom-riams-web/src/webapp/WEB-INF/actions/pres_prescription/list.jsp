<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Назначения" guid="59b3091a-95ac-4bf2-af2f-5c57219a5b24" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="e82777b5-387b-4e7b-bfee-3409aab78307">
      <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Create" key="ALT+2" action="/entityParentPrepareCreate-pres_drugPrescription" name="Назначение на лекарство" guid="6f237b21-4999-4113-aeff-8cd0c886da0f" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/DietPrescription/Create" key="ALT+3" action="/entityParentPrepareCreate-pres_dietPrescription" name="Назначение диеты" guid="c78a5cd9-345f-442a-9450-31bac14872ce" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Create" key="ALT+4" action="/entityParentPrepareCreate-pres_servicePrescription" name="Назначение на услугу" guid="b8f9cf37-df27-4508-b7db-8bb11f48b77f" params="id" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать" guid="b44g659-2a4a-4e49-9700-31b3a7eb150f">
      <msh:sideLink params="id" action="/entityParentList-pres_drugPrescription" name="Список лекарственных назначений" guid="fb51b8dc-0106-4ed3-85e9-710fd25ed587" key="ALT+5" />
      <msh:sideLink params="id" action="/entityParentList-pres_dietPrescription" name="Список назначенных диет" guid="f1b8dc-0106-4ed3-85e9-710fd25ed587" key="ALT+6" />
      <msh:sideLink params="id" action="/entityParentList-pres_servicePrescription" name="Список назначений на мед.услугу" guid="fb5dc-0106-4ed3-85e9-710fd25ed587" key="ALT+7" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<tags:pres_prescriptByList field="pl.id='${param.id}'" />
  </tiles:put>
</tiles:insert>

