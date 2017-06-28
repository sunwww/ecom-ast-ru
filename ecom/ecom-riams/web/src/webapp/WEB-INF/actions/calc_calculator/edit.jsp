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
		<msh:form action="/entitySaveGoView-calc_calculator.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType"/>
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Значение"
						horizontalFill="true" fieldColSpan="3" />
				</msh:row>
				<msh:row>
					<msh:textArea rows="2" property="comment" label="Описание (для констант)"
						fieldColSpan="3" horizontalFill="true" />
				</msh:row>
			
	  
	  <msh:row>
	<msh:autoComplete  property="valueOfResult" label="Тип" vocName="vocMeasureUnit" size="100" fieldColSpan="3" />
</msh:row>
				<msh:row>
				<msh:label property="username" label="пол-ль"/>
				<msh:submitCancelButtonsRow colSpan="1000"  />
				</msh:row>
			</msh:panel>
			
			
			<msh:ifFormTypeIsView formName="calc_calculatorForm">
			 <ecom:webQuery name="calcs" nativeSql="select c.id,c.value,c.comment, vtc.name from calculations c left join voctypecalc  vtc on vtc.id= c.type_id
													where calculator_id=${param.id} order by orderus"/>
      	<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityParentPrepareCreate-calc_calculations.do?id=${param.id}" title="Функционал">
      		<msh:sectionContent>
		      	<msh:table name="calcs" action="entityParentView-calc_calculations.do" idField="1">
		      		<msh:tableColumn property="sn" columnName="##"/>
		      		<msh:tableColumn property="2" columnName="Значение" />
		      		<msh:tableColumn property="3" columnName="Назначение/Комментарий"/>
		      		<msh:tableColumn property="4" columnName="Тип"/>
		      	</msh:table>
      		</msh:sectionContent>
      	</msh:section>
      	</msh:ifFormTypeIsView>
      	
      	
		</msh:form>
	</tiles:put>
	
	
	<tiles:put name="side" type="string">
	<msh:ifFormTypeIsView formName="calc_calculatorForm">
		<msh:sideMenu title="Добавить">
			<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-calc_calculations.do" name="функционал" title="функционал"/>
		</msh:sideMenu>
	</msh:ifFormTypeIsView>
	</tiles:put>

<tiles:put name="javascript" type="string">
 <script type="text/javascript">
 function onPreCreate(aForm, aCtx) {
		aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	}	
 
 
 </script>
 </tiles:put>
</tiles:insert>