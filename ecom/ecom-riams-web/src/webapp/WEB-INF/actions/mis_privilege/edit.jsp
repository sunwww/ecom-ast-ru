<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

     <tiles:put name='body' type='string'>
		<!-- Льгота -->
        <msh:form action="entityParentSaveGoParentView-mis_privilege.do" defaultField="beginDate">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="person"/>

             <msh:panel>

                 <msh:row>
                    <msh:textField property="beginDate" label="Дата включения" horizontalFill="false" />
                    <msh:textField property="endDate" label="Дата исключения" horizontalFill="false" />
                </msh:row>
                <msh:row>
                	<msh:checkBox property="active" label="Активность"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="category" label="Категория льготника" horizontalFill="true" fieldColSpan="3" vocName="vocPrivilegeCategory"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property='privilegeCode' label='Код льготы' horizontalFill="true" fieldColSpan="3" vocName="vocPrivilegeCode"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property='idc10' label='Диагноз' horizontalFill="true" fieldColSpan="3" vocName="vocIdc10"/>
                </msh:row>
                <msh:separator label="Льготный документ" colSpan="4"></msh:separator>

                <msh:row>
                    <msh:textField property="documentNumber" label="Номер" horizontalFill="false" size="20"/>
                    <msh:textField property="documentIssue" label="Дата выдачи"/>
                </msh:row>
                
                <msh:submitCancelButtonsRow colSpan="4"/>
          </msh:panel>
        </msh:form>
        
<!--  Лекарственное вещество  -->
<msh:ifFormTypeIsView formName="mis_privilegeForm">
	<msh:ifInRole roles="/Policy/Mis/Person/Privilege/DrugNeed/View">
		<ecom:parentEntityListAll formName="mis_drugNeedForm" attribute="needs"/>
		<msh:tableNotEmpty name="needs">
			<msh:section title='Лекарственные вещества'>
		            <msh:table  hideTitle='true' name="needs" action="entityParentView-mis_drugNeed.do" idField="id">
		                <msh:tableColumn property="info"/>
		            </msh:table>
			</msh:section>
		</msh:tableNotEmpty>
	</msh:ifInRole>
</msh:ifFormTypeIsView>

<!--  Рецепт -->
<msh:ifFormTypeIsView formName="mis_privilegeForm">
	<msh:ifInRole roles="/Policy/Mis/Person/Privilege/Recipe/View">
		<ecom:parentEntityListAll formName="mis_privilegeRecipeForm" attribute="recipes"/>
		<msh:tableNotEmpty name="recipes">
			<msh:section title='Рецепты'>
		            <msh:table  hideTitle='true' name="recipes" action="entityParentView-mis_privilegeRecipe.do" idField="id">
		                <msh:tableColumn columnName="Дата выписки" property="recipeDate"/>
		            </msh:table>
			</msh:section>
		</msh:tableNotEmpty>
	</msh:ifInRole>
</msh:ifFormTypeIsView>
        
        
  </tiles:put>

        <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_privilegeForm">
                <msh:sideLink key="ALT+2" params="id" roles="/Policy/Mis/Person/Privilege/Edit" action="/entityEdit-mis_privilege" name="Изменить"/>
                <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/Person/Privilege/DrugNeed/Create" action="/entityParentPrepareCreate-mis_drugNeed" name="Добавить лекарственный препарат"/>
                <msh:sideLink key="ALT+4" params="id" roles="/Policy/Mis/Person/Privilege/Recipe/Create" action="/entityParentPrepareCreate-mis_privilegeRecipe" name="Добавить рецепт"/>
	            <hr/>
                <msh:sideLink key='ALT+DEL' params="id" roles="/Policy/Mis/Person/Privilege/Delete" action="/entityParentDeleteGoParentView-mis_privilege" name="Удалить льготу" confirm="Удалить льготу?"/>
            </msh:ifFormTypeIsView>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_privilegeForm" />
    </tiles:put>

</tiles:insert>