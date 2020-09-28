<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_templateForm" mainMenu="Patient" title="Назначения" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Template/Create" key="ALT+2" action="/entityParentPrepareCreate-pres_template_drugPrescription" name="Назначение на лекарство" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/DietPrescription/Template/Create" key="ALT+3" action="/entityParentPrepareCreate-pres_template_dietPrescription" name="Назначение диеты" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Template/Create" key="ALT+4" action="/entityParentPrepareCreate-pres_template_servicePrescription" name="Назначение на услугу" params="id" />
    </msh:sideMenu>
    <msh:sideMenu title="Показать">
      <msh:sideLink roles="/Policy/Mis/Prescription/DrugPrescription/Template/View" params="id" action="/entityParentList-pres_template_drugPrescription" name="Список лекарственных назначений" key="ALT+5" />
      <msh:sideLink roles="/Policy/Mis/Prescription/DietPrescription/Template/View" params="id" action="/entityParentList-pres_template_dietPrescription" name="Список назначенных диет" key="ALT+6" />
      <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Template/View" params="id" action="/entityParentList-pres_template_servicePrescription" name="Список назначений на мед.услугу" key="ALT+7" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-pres_template_prescription.do" idField="id">
      <msh:tableColumn columnName="Назначение" property="descriptionInfo" />
      <msh:tableColumn columnName="Дата начала" property="planStartDate" />
      <msh:tableColumn columnName="Дата окончания" property="planEndDate" />
      <msh:tableColumn columnName="Назначил" property="prescriptorInfo" />
      <msh:tableColumn columnName="Дата отмены" property="cancelDate" />
      <msh:tableColumn columnName="Отменил" property="cancelWorkerInfo" />
      <msh:tableColumn columnName="Состояние" property="fulfilmentState" />
      <msh:tableColumn columnName="Комментарий" property="comments" />
    </msh:table>
  </tiles:put>
</tiles:insert>

