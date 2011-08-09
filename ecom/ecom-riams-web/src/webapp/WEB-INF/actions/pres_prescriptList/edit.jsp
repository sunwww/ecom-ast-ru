<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" guid="29345263-7743-4455-879e-130b73690294" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  -  лист назначений
    	  -->
    <msh:form action="/entityParentSaveGoView-pres_prescriptList.do" defaultField="name" guid="ea411ae6-6822-4cbd-a7f3-b8f6cfa1beba">
      <msh:hidden property="id" guid="ba8ca3c4-0044-44ab-bb12-a75e3441fae2" />
      <msh:hidden property="saveType" guid="efb8a9d9-e3c6-4f03-87bc-f0cccb820e89" />
      <msh:hidden property="medCase" guid="ac31e2ce-8059-482b-b138-b441c42e4472" />
      <msh:panel guid="8f2d40f7-6118-4da3-b209-3529333490ec">
        <msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="workFunction" label="Назначил" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="fdabbac6-97a9-4171-af88-2506106b38a3">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="d4f27875-9c0e-4b2d-807a-e4b1d002ec30" />
          <msh:textField property="date" label="Дата создания" viewOnlyField="true" guid="4f0598d7-0dfd-414a-acef-274fb76dda94" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="pres_prescriptListForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:ifInRole roles="/Policy/Mis/Prescription/DrugPrescription/View" guid="bf331972-44d3-4b35-9f3e-627a9be109e8">
    	<tags:pres_prescriptByList field="pl.id='${param.id}'" />
      </msh:ifInRole>
      <tags:templatePrescription record="1" parentId="${param.id}" name="add" />
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_prescriptListForm" guid="d4c560e9-6ddb-4cf2-9375-4caf7f0d3fb8">
      <msh:sideMenu title="Лист назначений" guid="2742309d-41bf-4fbe-9238-2f895b5f79a9">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-pres_prescriptList" name="Изменить" roles="/Policy/Mis/Prescription/Template/Edit" guid="0f0781cd-81dd-4da2-aba5-67eab700b161" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescriptList" name="Удалить" roles="/Policy/Mis/Prescription/Template/Delete" guid="99bf20e3-4292-4554-bd60-051aa4338ee1" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="9825ef2b-1d4b-4070-b035-b6707a878e5c">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-pres_drugPrescription" name="Лекарственное средство" roles="/Policy/Mis/Prescription/DrugPrescription/View" guid="f5549341-6246-4cc4-8369-6f7b04931f2a" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-pres_dietPrescription" name="Диету" guid="71dca8ec-ccdf-4f2a-88c7-750cbc00b045" roles="/Policy/Mis/Prescription/DietPrescription/View" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_servicePrescription" name="Медицинскую услугу" guid="3bb119f6-39d0-4bf4-9198-48f90e56f944" />
        <msh:sideLink action=" javascript:showaddTemplatePrescription(1,&quot;.do&quot;)" name="Назначения из шаблона" guid="a2f380f2-f499-49bf-b205-cdeba65f4e12" title="Добавить назначения из шаблона" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="4943cb98-adb2-4c2d-9668-e973ee0ed67f">
        <msh:sideLink key="ALT+9" action="/entityParentListRedirect-pres_prescriptList" name="⇧К списку сводных листов назначений" guid="07f2bb72-26b3-4609-a19a-7dffebdd0171" params="id" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="9793b3d9-bf76-4b96-b4c5-300b97c01753">
        <msh:sideLink action="/js-pres_prescriptList-print" name="Листа назначений" params="id" guid="503861b9-a4ed-4098-97aa-4c89b8a977bb" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

