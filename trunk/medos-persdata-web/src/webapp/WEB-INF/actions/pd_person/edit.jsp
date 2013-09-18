<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-pd_person.do" defaultField="lastname">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
				<msh:row>
					<msh:textField property="lastname" label="Фамилия"/>
					<msh:textField property="firstname" label="Имя"/>
					<msh:textField property="patronymic" label="Отчетство"/>
				</msh:row>
				<msh:row>
					<msh:textField property="birthdate" label="Дата рождения"/>
					<msh:autoComplete property="sex" label="Пол" vocName="vocSex"/>
					<msh:textField property="snils" label="СНИЛС"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="nationality"/>
				</msh:row>
				<msh:row>
					<msh:textField property="email"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
				<msh:ifFormTypeIsView formName="pd_personForm">
			<msh:section createUrl="entityParentPrepareCreate-pd_address.do?id=${param.id}">
				<ecom:webQuery name="address" nativeSql="
					select pa.id,va.name as vaname, a.Province as aaProvince
					, a.District as aaDistrict, a.Settlement as aaSettlement
					, a.Street as aaStreet, pa.House, pa.building, pa.flat
					from PersonalAddress pa
					left join VocAddress va on va.id=pa.type_id
					left join Address a on a.id=pa.address_id
					where pa.person_id=${param.id}
				"/>
				<msh:table name="address" action="entityParentView-pd_address.do" idField="1">
					<msh:tableColumn property="2" columnName="Тип"/>
					<msh:tableColumn property="3" columnName="Регион"/>
					<msh:tableColumn property="4" columnName="Район"/>
					<msh:tableColumn property="5" columnName="Населенный пункт"/>
					<msh:tableColumn property="6" columnName="Улица"/>
					<msh:tableColumn property="7" columnName="Дом"/>
					<msh:tableColumn property="8" columnName="Корпус"/>
					<msh:tableColumn property="9" columnName="Квартира"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="pd_personForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="pd_personForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pd_person" name="Изменить" title="Изменить" roles=""/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-pd_person" name="Удалить" title="Удалить" roles=""/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
