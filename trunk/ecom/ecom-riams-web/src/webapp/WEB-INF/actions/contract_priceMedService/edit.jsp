<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_priceMedService.do" defaultField="medServiceName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="pricePosition" />
			<msh:panel colsWidth="1%,1%,1%">
				<msh:row>
					<msh:textField property="medService"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete fieldColSpan="3" property="medService" label="Медицинская услуга" 
					vocName="medServiceAll" horizontalFill="true" 
					size="150" viewAction="entityView-mis_medService.do"
					shortViewAction="entityView-mis_medService.do?short=Short"
					/>
				</msh:row>
				<msh:ifFormTypeIsNotView formName="contract_priceMedServiceForm">
					<msh:row>
						<msh:checkBox property="medServiceIsCreate" label="Добавить мед.услугу" fieldColSpan="3" horizontalFill="true"/>
					</msh:row>
					<msh:row styleId="styleCateg">
						<msh:autoComplete label="Категория" property="medServiceForm.parent" vocName="medService" 
							fieldColSpan="3" horizontalFill="true"/>
					</msh:row>
					<msh:row styleId="styleCateg">
						<msh:autoComplete label="Услуга по спр. МЗ" property="medServiceForm.vocMedService" vocName="vocMedService" 
							fieldColSpan="3" horizontalFill="true"/>
					</msh:row>
					<msh:row styleId="styleCateg">
						<msh:autoComplete label="Тип услуги" property="medServiceForm.serviceType" vocName="vocServiceType" 
							fieldColSpan="3" horizontalFill="true"/>
					</msh:row>
					<msh:row styleId="styleCateg">
						<msh:textField property="medServiceForm.code"/>
						<msh:textField property="medServiceForm.complexity"/>
					</msh:row>
					<msh:row styleId="styleCateg">
						<msh:textField property="medServiceForm.name" fieldColSpan="3" 
							horizontalFill="true"/>
					</msh:row>
			        <msh:row styleId="styleCateg">
			        	<msh:checkBox property="medServiceForm.isHospital" label="В стационаре"/>
			        	<msh:checkBox property="medServiceForm.isDayHospital" label="В дн.стационаре"/>
			        </msh:row>
			        <msh:row styleId="styleCateg">
			        	<msh:checkBox property="medServiceForm.isPoliclinic" label="В поликлинике"/>
			        </msh:row>
				</msh:ifFormTypeIsNotView>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала"/>
					<msh:textField property="dateTo" label="Дата окончания"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_priceMedServiceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_priceMedServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_priceMedService" name="Изменить" title="Изменить" roles=""/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_priceMedService" name="Удалить" title="Удалить" roles=""/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="price"/>
	</tiles:put>
</tiles:insert>
