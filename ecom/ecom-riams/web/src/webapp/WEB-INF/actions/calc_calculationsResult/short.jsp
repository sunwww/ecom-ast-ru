<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


	


<!-- Styler -->

		<style>
			.calc {
				display: inline-block;
				padding: 3px;
				border: 1px solid rgba(255, 0, 0, 0);
				transition: 3s;
				border-color: rgb(59, 92, 105);
				position: relative;
				padding-top: 0.4em;
				min-height: 0em;
			}
		</style>

	
	<!-- Body -->

	<msh:form action="/entitySaveGoView-calc_calculationsResult.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="departmentMedCase" />
			<msh:hidden property="calculator" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete vocName="vocCalculator" property="calculator" label="Выбор калькулятора" fieldColSpan="4" size="30" />
				</msh:row>
			</msh:panel>
			
			
		</msh:form>

	




