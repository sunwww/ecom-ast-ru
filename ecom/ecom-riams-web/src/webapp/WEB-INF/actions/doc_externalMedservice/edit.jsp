<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-doc_externalMedservice.do" defaultField="patientLastname">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="patient" />
			<msh:panel>
			<msh:row>
				<msh:textField property="patientLastname"/>
				<msh:textField property="patientFirstname"/>
			</msh:row>
			<msh:row>
				<msh:textField property="patientMiddlename"/>
				<msh:textField property="patientBirthday"/>
			</msh:row>
			<msh:row>
				<msh:textField property="orderLpu"/>
				<msh:textField property="orderer"/>
			</msh:row>
			<msh:row>
				<msh:textField property="orderDate"/>
				<msh:textField property="orderTime"/>
			</msh:row>
			<msh:row>
				<msh:textField property="createDate"/>
				<msh:textField property="createTime"/>
			</msh:row>
			<msh:row>
				<msh:textArea property="comment" fieldColSpan="3" horizontalFill="true" rows="10"/>
			</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="doc_externalMedserviceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="doc_externalMedserviceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="doc_externalMedserviceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_externalMedservice" name="Изменить" title="Изменить" roles="/Policy/Mis/MedCase/Document/External/Medservice/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-doc_externalMedservice" name="Удалить" title="Удалить" roles="/Policy/Mis/MedCase/Document/External/Medservice/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
