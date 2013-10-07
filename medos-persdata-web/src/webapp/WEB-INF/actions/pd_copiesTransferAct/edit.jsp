<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-pd_copiesTransferAct.do" defaultField="actNumber">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:panel>
			<msh:row>
				<msh:textField property="actNumber" label="Акт №"/>
			</msh:row>
			<msh:row>
				<msh:textField property="urgencyStartDate" label="Дата"/>
			</msh:row>
			<msh:row>
				<msh:textField property="copiesAmount" label="Кол-во копий"/>
			</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="pd_copiesTransferActForm">
				<msh:ifInRole roles="/Policy/PersData/Person/Identifier/View">
				<msh:section title="Идентификаторы" >
				<ecom:webQuery name="identifier" nativeSql="
				select i.id as iid,i.IdentificationNumber as identificationNumber
				, vis.name as visname, i.IsTransient as isTransient
				,vcd.name as vaname
					, cd.series
					, cd.documentNumber
					,p.lastname||' '||p.firstname||' '||coalesce(p.patronymic,'') as fio
					,p.birthdate
				from Identifier i
				left join Person p on p.id=i.person_id
				left join VocIdentificationSystem vis on vis.id=i.identificationSystem_id
				left join ComingDocument cd on cd.id=i.comingDocument_id
				left join VocComingDocument vcd on vcd.id=cd.type_id
				where i.transferAct_id=${param.id}
				"/>
				<msh:table name="identifier" action="entityParentView-pd_identifier.do" idField="1">
					<msh:tableColumn property="sn" columnName="#"/>
					<msh:tableColumn property="8" columnName="Фамилия имя отчество"/>
					<msh:tableColumn property="9" columnName="год рождения"/>
					<msh:tableColumn property="2" columnName="Номер"/>
					<msh:tableColumn property="3" columnName="Система"/>
					<msh:tableColumn property="4" columnName="Временный?"/>
					<msh:tableColumn property="5" columnName="Тип документа"/>
					<msh:tableColumn property="6" columnName="Серия"/>
					<msh:tableColumn property="7" columnName="Номер"/>					
				</msh:table>				
				</msh:section>
				</msh:ifInRole>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="pd_copiesTransferActForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="pd_copiesTransferActForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityEdit-pd_copiesTransferAct" name="Изменить" title="Изменить" roles="/Policy/PersData/Act/CopiesTransfer/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-pd_copiesTransferAct" name="Удалить" title="Удалить" roles="/Policy/PersData/Act/CopiesTransfer/Delete"/>
			</msh:sideMenu>

		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>