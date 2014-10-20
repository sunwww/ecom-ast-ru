<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySave-mis_copyingEquipment.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="lpu"/>
			<msh:panel colsWidth="10%,10%,10%">
				<msh:row>
					<msh:textField property="name" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="accountNumber"/>
					<msh:textField property="ipaddress"/>
				</msh:row>
				<msh:row>
					<msh:textField property="model"/>
					<msh:textField property="serialNumber"/>
				</msh:row>
				<msh:checkBox property="isTxtFile"/>
				<msh:row>
					<msh:textArea property="comment" fieldColSpan="3"  />
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="mis_copyingEquipmentForm">
		<msh:section createUrl="entityParentPrepareCreate-mis_copyingEquipmentMaskFiles.do?id=${param.id}" title="Список дополнительных настроек по шаблонам">
		<ecom:webQuery name="child" nativeSql="select id,maskFiles,name,isTxtFile from copyingEquipment where parent_id=${param.id}"/>
		<msh:table name="child" action="entityView-mis_copyingEquipmentMaskFiles.do" idField="1">
			<msh:tableColumn property="sn" columnName="№"/>
			<msh:tableColumn property="2" columnName="Маска"/>
			<msh:tableColumn property="3" columnName="Наименование принтера"/>
			<msh:tableColumn property="4" columnName="Приоритет текстового файла"/>
		</msh:table>
		</msh:section>
		<msh:section title="Список отделений, использующие принтер">
        <ecom:webQuery name="child" nativeSql="select id,name from mislpu where copyingEquipmentDefault_id=${param.id}"/>
        <msh:table name="child" action="entityView-mis_lpu.do" idField="1">
            <msh:tableColumn property="sn" columnName="в„–"/>
            <msh:tableColumn property="2" columnName="РќР°РёРјРµРЅРѕРІР°РЅРёРµ РїСЂРёРЅС‚РµСЂР°"/>
        </msh:table>
        </msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<msh:title mainMenu="Lpu" title="Копировального оборудования"></msh:title>
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="mis_copyingEquipmentForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_copyingEquipment" name="Изменить" title="Изменить" 
				roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-mis_copyingEquipment" name="Удалить" 
				title="Удалить" roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Delete"/>
			</msh:sideMenu>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment/Template/Create" params="id" action="/entityParentPrepareCreate-mis_copyingEquipmentMaskFiles" title="Добавить шаблон файла" name="Шаблон файла" />
		</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
