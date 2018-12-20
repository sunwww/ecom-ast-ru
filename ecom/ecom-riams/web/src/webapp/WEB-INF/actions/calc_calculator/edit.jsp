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
					<msh:checkBox property="createDiary" label="Создавать дневник при вычислении?" horizontalFill="true" fieldColSpan="3" />
				</msh:row>
				<msh:row>
				<msh:label property="username" label="пол-ль"/>
				<msh:submitCancelButtonsRow colSpan="1000"  />
				</msh:row>
			</msh:panel>
			
			
			<msh:ifFormTypeIsView formName="calc_calculatorForm">
			 <ecom:webQuery name="calcs" nativeSql="select c.id,c.value,c.comment, vtc.name, c.note from calculations c left join voctypecalc  vtc on vtc.id= c.type_id
													where calculator_id=${param.id} order by orderus"/>
      	<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityParentPrepareCreate-calc_calculations.do?id=${param.id}" title="Функционал">
      		<msh:sectionContent>
		      	<msh:table name="calcs" action="entityParentView-calc_calculations.do" idField="1">
		      		<msh:tableColumn property="sn" columnName="##"/>
		      		<msh:tableColumn property="2" columnName="Значение" />
		      		<msh:tableColumn property="3" columnName="Назначение/Комментарий"/>
		      		<msh:tableColumn property="4" columnName="Тип"/>
                    <msh:tableColumn property="5" columnName="Примечание"/>
		      	</msh:table>
      		</msh:sectionContent>
      	</msh:section>
				<ecom:webQuery name="ints" nativeSql="select id,value,result from interpretation where calculator_id=${param.id}"/>
				<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityParentPrepareCreate-calc_interpretation.do?id=${param.id}" title="Интерпретации результата">
					<msh:sectionContent>
						<msh:table name="ints" action="entityParentView-calc_interpretation.do" idField="1">
							<msh:tableColumn property="sn" columnName="##"/>
							<msh:tableColumn property="2" columnName="Значение" />
							<msh:tableColumn property="3" columnName="Интерпретация результата"/>
						</msh:table>
					</msh:sectionContent>
				</msh:section>
				<ecom:webQuery name="prescs" nativeSql="select p.id,p.prescvalue,r.riskvalue,r.lowscore,r.upscore from presccalc p left join calcrisk r on r.id=p.calcrisk_id where p.calculator_id=${param.id}"/>
				<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityParentPrepareCreate-calc_presc.do?id=${param.id}" title="Назначения">
					<msh:sectionContent>
						<msh:table name="prescs" action="entityParentView-calc_presc.do" idField="1">
							<msh:tableColumn property="sn" columnName="##"/>
							<msh:tableColumn columnName="Назначение" property="2"/>
							<msh:tableColumn columnName="Риск" property="3"/>
							<msh:tableColumn columnName="Баллы риска от" property="4"/>
							<msh:tableColumn columnName="До" property="5"/>
						</msh:table>
					</msh:sectionContent>
				</msh:section>
				<ecom:webQuery name="contras" nativeSql="select id,contravalue from contracalc where calculator_id=${param.id}"/>
				<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityParentPrepareCreate-calc_contra.do?id=${param.id}" title="Противопоказания">
					<msh:sectionContent>
						<msh:table name="contras" action="entityParentView-calc_contra.do" idField="1">
							<msh:tableColumn property="sn" columnName="##"/>
							<msh:tableColumn columnName="Противопоказание" property="2"/>
						</msh:table>
					</msh:sectionContent>
				</msh:section>
				<ecom:webQuery name="risks" nativeSql="select r.id,r.riskvalue,r.lowscore,r.upscore from calcrisk r where r.calculator_id=${param.id}"/>
				<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityParentPrepareCreate-calc_risk.do?id=${param.id}" title="Риски">
					<msh:sectionContent>
						<msh:table name="risks" action="entityParentView-calc_risk.do" idField="1">
							<msh:tableColumn property="sn" columnName="##"/>
							<msh:tableColumn columnName="Риск" property="2"/>
							<msh:tableColumn columnName="Баллы риска от" property="3"/>
							<msh:tableColumn columnName="До" property="4"/>
						</msh:table>
					</msh:sectionContent>
				</msh:section>
      	</msh:ifFormTypeIsView>
      	
      	
		</msh:form>
	</tiles:put>
	
	
	<tiles:put name="side" type="string">
	<msh:ifFormTypeIsView formName="calc_calculatorForm">
		<msh:sideMenu title="Добавить">
			<msh:sideLink params="id" action="/entityParentPrepareCreate-calc_calculations.do" name="функционал" title="функционал"/>
			<msh:sideLink params="id" action="/entityParentPrepareCreate-calc_interpretation.do" name="интерпретацию результата" title="интерпретацию результата"/>
			<msh:sideLink params="id" action="/entityParentPrepareCreate-calc_presc.do" name="назначение" title="назначение"/>
			<msh:sideLink params="id" action="/entityParentPrepareCreate-calc_contra.do" name="противопоказание" title="противопоказание"/>
			<msh:sideLink params="id" action="/entityParentPrepareCreate-calc_risk.do" name="риск" title="риск"/>
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