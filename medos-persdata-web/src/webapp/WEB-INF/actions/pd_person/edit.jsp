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
					<msh:textField property="lastname" label="Фамилия" size="20"/>
					<msh:textField property="firstname" label="Имя" size="20"/>
					<msh:textField property="patronymic" label="Отчетство" size="20"/>
				</msh:row>
				<msh:row>
					<msh:textField property="birthdate" label="Дата рождения"/>
					<msh:autoComplete property="sex" label="Пол" vocName="vocSex"/>
					<msh:textField property="snils" label="СНИЛС" size="20"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="nationality" vocName="vocNationality"/>
				</msh:row>
				<msh:row>
					<msh:textField property="email"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
				<msh:ifFormTypeIsView formName="pd_personForm">
				<msh:section createUrl="entityParentPrepareCreate-pd_identifier.do?id=${param.id}" title="Идентификаторы">
				<ecom:webQuery name="identifier" nativeSql="
				select i.id as iid,i.IdentificationNumber as identificationNumber
				, vis.name as visname, i.IsTransient as isTransient
				from Identifier i
				left join VocIdentificationSystem vis on vis.id=i.identificationSystem_id
				where i.person_id=${param.id}
				"/>
				<msh:table name="identifier" action="entityParentView-pd_identifier.do" idField="1">
					<msh:tableColumn property="2" columnName="Номер"/>
					<msh:tableColumn property="3" columnName="Система"/>
					<msh:tableColumn property="4" columnName="Временный?"/>
				</msh:table>				
				</msh:section>
			<msh:section createUrl="entityParentPrepareCreate-pd_address.do?id=${param.id}" title="Адреса">
				<ecom:webQuery name="address" nativeSql="
					select pa.id,va.name as vaname
					, case when vk.id is not null then vk.Province else a.Province end as aaProvince
					, case when vk.id is not null then vk.Region else a.Region end as aaRegion
					, case when vk.id is not null then coalesce(vk.city,'')||' '||coalesce(vk.Settlement,'') else a.Settlement end as aaSettlement
					, case when vk.id is not null then vk.street else a.Street end as aaStreet, pa.House, pa.building, pa.flat
					from PersonalAddress pa
					left join VocAddress va on va.id=pa.type_id
					left join Address a on a.id=pa.address_id
					left join VocKladr vk on vk.id=a.kladr_id
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
			<msh:section title="Документы" createUrl="entityParentPrepareCreate-pd_comingDocument.do?id=${param.id}">
				<ecom:webQuery name="comingDocument" nativeSql="
					select cd.id,vcd.name as vaname
					, cd.series
					, cd.documentNumber
					from ComingDocument cd
					left join VocComingDocument vcd on vcd.id=cd.type_id
					where cd.person_id=${param.id}
				"/>			
				<msh:table name="comingDocument" action="entityParentView-pd_comingDocument.do" idField="1">
					<msh:tableColumn property="2" columnName="Тип"/>
					<msh:tableColumn property="3" columnName="Серия"/>
					<msh:tableColumn property="4" columnName="Номер"/>
				</msh:table>
			</msh:section>
			<msh:section title="Телефон" createUrl="entityParentPrepareCreate-pd_phone.do?id=${param.id}">
				<ecom:webQuery name="phone" nativeSql="
					select p.id,vp.name as vaname
					, p.phoneNumber
					from Phone p
					left join vocPhone vp on vp.id=p.phoneType_id
					where p.person_id=${param.id}
				"/>			
				<msh:table name="phone" action="entityParentView-pd_phone.do" idField="1">
					<msh:tableColumn property="2" columnName="Тип"/>
					<msh:tableColumn property="3" columnName="Номер"/>
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
