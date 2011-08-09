<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Шаблон листа назначений
    	  -->
    <msh:form action="/entitySaveGoView-pres_template.do" defaultField="name" guid="ea411ae6-6822-4cbd-a7f3-b8f6cfa1beba">
      <msh:hidden property="id" guid="ba8ca3c4-0044-44ab-bb12-a75e3441fae2" />
      <msh:hidden property="saveType" guid="efb8a9d9-e3c6-4f03-87bc-f0cccb820e89" />
      <msh:panel guid="8f2d40f7-6118-4da3-b209-3529333490ec">
        <msh:row>
        	<msh:textField property="name" label="Название"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:row guid="203a1bdd-8e88-4683-ad11-34692e44b66d">
          <msh:autoComplete property="workFunction" label="Владелец" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="b28cb409-df33-4406-bef7-9a79f93b49dd">
          <ecom:oneToManyOneAutocomplete label="Категории шаблона" property="categories" vocName="vocTemplateCategory" colSpan="4" />
        </msh:row>
        <msh:row guid="fdabbac6-97a9-4171-af88-2506106b38a3">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="d4f27875-9c0e-4b2d-807a-e4b1d002ec30" />
          <msh:textField property="date" label="Дата создания" viewOnlyField="true" guid="4f0598d7-0dfd-414a-acef-274fb76dda94" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="pres_templateForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <tags:templatePrescription record="1" parentId="${param.id}" name="new" />
      <msh:ifInRole roles="/Policy/Mis/Prescription/Template/DrugPrescription/View" guid="bf331972-44d3-4b35-9f3e-627a9be109e8">
        <msh:section title="Назначения" guid="c33b9308-477d-4d6b-8d7b-e290e7de2530">
          <ecom:parentEntityListAll formName="pres_prescriptionForm" attribute="prescription" guid="f9491526-b5e6-4a92-ba34-0d9892326de7" />
          <msh:table name="prescription" action="entitySubclassView-pres_template_prescription.do" idField="id" guid="95f63378-5b89-4f15-ad12-ba3f87976c52">
            <msh:tableColumn columnName="#" property="id" guid="24ec4672-8e09-4ec3-80bf-c2e7cb489806" />
            <msh:tableColumn columnName="Описание назначения" property="descriptionInfo" guid="6a4b783f-f665-4f8a-aff0-3e918f97914d" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="pres_templateForm" guid="af3d88cd-60b5-4bba-a85d-ac2334f43161" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_templateForm" guid="d4c560e9-6ddb-4cf2-9375-4caf7f0d3fb8">
      <msh:sideMenu title="Шаблон" guid="2742309d-41bf-4fbe-9238-2f895b5f79a9">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-pres_template" name="Изменить" roles="/Policy/Mis/Prescription/Template/Edit" guid="0f0781cd-81dd-4da2-aba5-67eab700b161" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-pres_template" name="Удалить" roles="/Policy/Mis/Prescription/Template/Delete" guid="99bf20e3-4292-4554-bd60-051aa4338ee1" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="9825ef2b-1d4b-4070-b035-b6707a878e5c">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-pres_template_drugPrescription" name="Лекарственное средство" roles="/Policy/Mis/Prescription/Template/DrugPrescription/View" guid="f5549341-6246-4cc4-8369-6f7b04931f2a" />
        <msh:sideLink action=" javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="Назначения на основе существующего шаблона" title="Создать шаблон лист назначения на основе существующего шаблона" guid="c6e48b9d-d1cf-4731-af04-3f8fe356717e" />
      </msh:sideMenu>
      <tags:template_menu currentAction="prescriptions"/>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

