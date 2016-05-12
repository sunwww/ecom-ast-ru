<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Лекарственное средство">
      <msh:ifFormTypeIsView formName="voc_drugForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-voc_drug" name="Изменить" roles="/Policy/Mis/Drug/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="voc_drugForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-voc_drug" name="Удалить" roles="/Policy/Mis/Drug/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>    
    <tags:voc_menu currentAction="drugTrade"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Классификатор лекарственных средств
    	  -->
    <msh:form action="/entitySaveGoView-voc_drug.do" defaultField="code" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" >
      	<msh:row>
      		<msh:textField property="code"/>
      	</msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="name" label="Наименование"  guid="b87e9cee-cf5d-43bc-b50d-1911d5e87e40" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="licensedName" label="Патент.название" vocName="vocDrugLicensedName" horizontalFill="true"/>
        	<msh:autoComplete property="drugUnlicensedName" label="Непатент.название" vocName="vocDrugUnlicensedName" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="dozage" label="Дозировка" />
        	<msh:textField property="packingAmount" label="Кол-во доз в упаковке" />
        </msh:row>
        <msh:row>
	        <msh:autoComplete property="drugForm" label="Лекарственная форма" horizontalFill="true" vocName="vocDrugForm"/>	
        </msh:row>
        <msh:ifFormTypeIsNotView formName="voc_drugForm">
	        <msh:row>
	        	<ecom:oneToManyOneAutocomplete label="Классификаторы" property="drugClassificatorPositions" vocName="drugClassificator" colSpan="3"/>
	        </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:row>
         	<msh:textField property="username" label="Пользователь" viewOnlyField="true"/>
         	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="/Policy/Mis/Drug/Classificator/View" guid="3a4d6eb2-8dac-420a-9dcf-4f47584d9d61">
      <msh:ifFormTypeIsView formName="voc_drugForm" guid="9f1dd6a7-43e7-b205-85730bba6968">
        <msh:section title="Категории, в которые входит данное лекарственное средство" guid="e681be03-d0185f156">
          <ecom:webQuery name="drugClas" nativeSql="select d.id,d.name from DrugClassificatorPosition dcp left join DrugClassificator d on d.id=dcp.drugClassificator_id where dcp.drug_id='${param.id}' "/>
          <msh:table name="drugClas" action="entityParentView-drug_classificator.do" idField="1" guid="16cdff9b--8997-eebc80ecc49c">
            <msh:tableColumn property="2" columnName="Категория" guid="2fd022ea-59b0-4cc9a3ddc91f" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_drugForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<msh:ifFormTypeIsCreate formName="voc_drugForm">
  	<script type="text/javascript">
  		$('name').value='${param.name}' ;
  	</script>
  	</msh:ifFormTypeIsCreate>
  </tiles:put>
  </tiles:insert>