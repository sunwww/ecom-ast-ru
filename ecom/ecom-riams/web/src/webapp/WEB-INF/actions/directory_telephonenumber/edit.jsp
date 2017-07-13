<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp"
	flush="true">
	<tiles:put name="body" type="string">
	
	
		 	<msh:form action="/entitySaveGoView-directory_telephonenumber.do"
			defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete vocName="vocEntry" property="entry"
						label="Запись" fieldColSpan="4" size="60" />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocTypeNumber"
						property="typeNumber" label="Тип номера" fieldColSpan="4"
						size="60" />
				</msh:row>
				<msh:row>
					<msh:textField property="telNumber" label="номер телефона"
						horizontalFill="true" fieldColSpan="3" size="60" />
				</msh:row>
			</msh:panel>
			
			<msh:submitCancelButtonsRow colSpan="1000" />
			</msh:form>
		</tiles:put>
</tiles:insert>