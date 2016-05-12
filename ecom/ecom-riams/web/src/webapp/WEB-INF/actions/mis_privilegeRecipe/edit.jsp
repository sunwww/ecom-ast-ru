<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name='body' type='string'>
		<!-- 
		   -	Рецепт
		  -->
		<msh:form action="entityParentSaveGoView-mis_privilegeRecipe.do"
				defaultField="recipeNumber">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="privilege" />

			<msh:panel>

				<msh:row>
					<msh:textField property='recipeNumber' label='Номер рецепта' />
					<msh:textField property='recipeDate' label='Дата выписки'  />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="drugClassify"
						label="Лекарственный препарат" horizontalFill="true"
						fieldColSpan="3" vocName="vocDrugClassify" size='50' />
				</msh:row>
				<msh:row>
					<msh:autoComplete property='doctor' label='Код врача'
						horizontalFill="true" vocName="vocDloDoctor" fieldColSpan="3" />
				</msh:row>

				<msh:row>
					<msh:autoComplete vocName='vocDrugForm' property='drugForm'
						label='Лекарственная форма' horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName='vocIdc10' property='idc10' label='МКБ10'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property='kekDesicion' label='Решение КЭК' />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName='mainLpu' property='lpu'
						label='ЛПУ, которое выписало рецепт'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property='payPercent' label='Процент оплаты' />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName='vocPrivilegeCategory'
						property='privilegeCategory' label='Тип льготы'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property='recFinSource'
						label='Источник финансирования по рецепту' labelColSpan="2"  horizontalFill="true" fieldColSpan="2"/>
				</msh:row>
				<msh:row>
					<msh:textField property='recFirstname' label='Имя по рецепту'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property='recLastname' label='Фамилия по рецепту'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property='recMiddlename' label='Отчество по рецепту'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property='recSnils' label='СНИЛС по рецепту'  horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property='regDrugDoze'
						label='Дозировка ЛС' />
				</msh:row>
				<msh:row>
					<msh:textField property='regDrugQuantity'
						label='Кол-во ЛС по рецепту' />
				</msh:row>
				<msh:row>
					<msh:textField property='regDrugTradeName'
						label='Торговое наименование лекарственного средства по рецепту' labelColSpan="2" fieldColSpan="2" horizontalFill="true"/>
				</msh:row>

				<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>


	</tiles:put>

	<tiles:put name='side' type='string'>
		<msh:sideMenu>
			<msh:ifFormTypeIsView formName="mis_privilegeRecipeForm">
				<msh:sideLink key="ALT+2" params="id"
					roles="/Policy/Mis/Person/Privilege/Recipe/Edit"
					action="/entityEdit-mis_privilegeRecipe" name="Изменить" />
			</msh:ifFormTypeIsView>
			<hr />
			<msh:ifFormTypeAreViewOrEdit formName="mis_privilegeRecipeForm">
				<msh:sideLink key='ALT+DEL' params="id"
					roles="/Policy/Mis/Person/Privilege/Recipe/Delete"
					action="/entityParentDeleteGoParentView-mis_privilegeRecipe"
					name="Удалить рецепт"
					confirm="Удалить рецепт?" />
			</msh:ifFormTypeAreViewOrEdit>
		</msh:sideMenu>
	</tiles:put>

	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Patient" beginForm="mis_privilegeRecipeForm" />
	</tiles:put>

</tiles:insert>