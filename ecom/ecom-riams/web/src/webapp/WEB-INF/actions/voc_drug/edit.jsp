<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    
    <msh:sideMenu title="Лекарственное средство">
      <msh:ifFormTypeIsView formName="voc_drugForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-voc_drug" name="Изменить" roles="/Policy/Mis/Drug/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="voc_drugForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-voc_drug" name="Удалить" roles="/Policy/Mis/Drug/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>    
    <tags:voc_menu currentAction="drugTrade"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Классификатор лекарственных средств
    	  -->
    <msh:form action="/entitySaveGoView-voc_drug.do" defaultField="code">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel >
      	<msh:row>
      		<msh:textField property="code"/>
      	</msh:row>
        <msh:row>
          <msh:textField property="name" label="Наименование"  horizontalFill="true" fieldColSpan="3"/>
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
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="/Policy/Mis/Drug/Classificator/View">
      <msh:ifFormTypeIsView formName="voc_drugForm">
        <msh:section title="Категории, в которые входит данное лекарственное средство">
          <ecom:webQuery name="drugClas" nativeSql="select d.id,d.name from DrugClassificatorPosition dcp left join DrugClassificator d on d.id=dcp.drugClassificator_id where dcp.drug_id='${param.id}' "/>
          <msh:table name="drugClas" action="entityParentView-drug_classificator.do" idField="1">
            <msh:tableColumn property="2" columnName="Категория" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_drugForm" />
  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<msh:ifFormTypeIsCreate formName="voc_drugForm">
  	<script type="text/javascript">
  		$('name').value='${param.name}' ;
  	</script>
  	</msh:ifFormTypeIsCreate>
  </tiles:put>
  </tiles:insert>