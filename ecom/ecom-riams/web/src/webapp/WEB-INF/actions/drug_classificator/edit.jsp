<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    
    <msh:sideMenu title="Классификатор">
      <msh:ifFormTypeIsView formName="drug_classificatorForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-drug_classificator" name="Изменить" roles="/Policy/Mis/Drug/Classificator/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="drug_classificatorForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-drug_classificator" name="Удалить" roles="/Policy/Mis/Drug/Classificator/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="drug_classificatorForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-drug_classificator" name="Категорию" params="id" roles="/Policy/Mis/Drug/Classificator/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
    <tags:voc_menu currentAction="drugClassif"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Классификатор лекарственных средств
    	  -->
    <msh:form action="/entityParentSaveGoView-drug_classificator.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel >
        <msh:row>
          <msh:textField property="name" label="Наименование"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:ifFormTypeIsNotView formName="drug_classificatorForm">
	        <msh:row>
	        	<ecom:oneToManyOneAutocomplete label="Лек.средства" property="drugList" vocName="vocDrugClassify" colSpan="3"/>
	        </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:row>
         	<msh:textField property="username" label="Пользователь" viewOnlyField="true"/>
         	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="/Policy/Mis/Drug/Classificator">
      <msh:ifFormTypeIsView formName="drug_classificatorForm">
        <msh:section title="Категории">
          <ecom:parentEntityListAll attribute="children" formName="drug_classificatorForm" />
          <msh:table name="children" action="entityParentView-drug_classificator.do" idField="id">
            <msh:tableColumn property="name" columnName="Name" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/Drug/View">
      <msh:ifFormTypeIsView formName="drug_classificatorForm">
        <msh:section title="Прикрепленные лекарственные средства к группе">
          <ecom:webQuery name="drugs" nativeSql="select d.id,d.name from DrugClassificatorPosition dcp left join VocDrugClassify d on d.id=dcp.drug_id where dcp.drugClassificator_id='${param.id}' "/>
          <msh:table name="drugs" action="entityView-voc_drug.do" idField="1">
            <msh:tableColumn property="2" columnName="Лекарственное средство" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="drug_classificatorForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">

  
  </tiles:put>
</tiles:insert>

