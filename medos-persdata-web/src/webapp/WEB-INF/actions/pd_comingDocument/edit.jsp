<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-pd_comingDocument.do" defaultField="typeName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="person" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="stateStructure" vocName="vocStateStructure" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="type" vocName="vocComingDocument" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="urgencyStartDate" label="Дата выдачи"/>
				</msh:row>
				<msh:row>
					<msh:textField property="series" label="Серия"/>
					<msh:textField property="documentNumber" label="Номер"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="pd_comingDocumentForm">
				<msh:ifInRole roles="/Policy/PersData/Person/Identifier/View">
				<msh:section createUrl="javascript:window.location='entityParentPrepareCreate-pd_identifier.do?comingDocument=${param.id}&id='+$('person').value" title="Идентификаторы" createRoles="/Policy/PersData/Person/Identifier/Create">
				<ecom:webQuery name="identifier" nativeSql="
				select i.id as iid,i.IdentificationNumber as identificationNumber
				, vis.name as visname, i.IsTransient as isTransient
				from Identifier i
				left join VocIdentificationSystem vis on vis.id=i.identificationSystem_id
				where i.comingDocument_id=${param.id}
				"/>
				<msh:table name="identifier" action="entityParentView-pd_identifier.do" idField="1">
					<msh:tableColumn property="2" columnName="Номер"/>
					<msh:tableColumn property="3" columnName="Система"/>
					<msh:tableColumn property="4" columnName="Временный?"/>
				</msh:table>				
				</msh:section>
				</msh:ifInRole>
      <msh:ifInRole roles="/Policy/PersData/ComingDocument/DocumentFile/View">
      <msh:section createRoles="/Policy/PersData/ComingDocument/DocumentFile/Create"
      createUrl="documentFile-preView.do?id=${param.id}" title="Прикрепленные файлы">
      <ecom:webQuery name="extDocument" nativeSql="
      	select d.id,to_char(d.createDate,'dd.mm.yyyy')
      	||coalesce(' '||cast(d.createTime as varchar(5)),''),d.referenceTo 
      	from DocumentFile d
      	where d.document_id='${param.id}'
      "/>
      	<msh:table name="extDocument"
      	viewUrl="entityParentView-pd_documentFile.do?short=Short" 
      	action="entityParentView-pd_documentFile.do" idField="1">
      		<msh:tableColumn property="2" columnName="Дата и время создания"/>
      		<msh:tableColumn property="3" columnName="Ссылка"/>
      	</msh:table>
      	</msh:section>
      </msh:ifInRole>
	</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="pd_comingDocumentForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="pd_comingDocumentForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pd_comingDocument" name="Изменить" title="Изменить" roles="/Policy/PersData/ComingDocument/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-pd_comingDocument" name="Удалить" title="Удалить" roles="/Policy/PersData/ComingDocument/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить">
				<msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-pd_documentFile" 
				name="Файл" title="Файл" roles="/Policy/PersData/ComingDocument/DocumentFile/Create"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
