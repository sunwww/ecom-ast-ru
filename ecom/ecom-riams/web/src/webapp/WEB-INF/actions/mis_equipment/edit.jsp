<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSave-mis_equipment.do" defaultField="typeEquipName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>

            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="typeEquip" label='Оборудование' vocName="vocTypeEquip" horizontalFill="true" fieldColSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="marka" label='Марка' vocName="vocMarka" horizontalFill="true" fieldColSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="createYear" label="Год выпуска"/>
                    <msh:textField property="stayYear" label="Год установки"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="creater" label="Производитель" vocName="vocCreater"/>
                    <msh:autoComplete property="provider" label="Поставщик" vocName="vocProvider"/>
                </msh:row>
                <msh:row>
                   <msh:textArea property="info" label="Примечание" fieldColSpan="3"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_equipmentForm">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_equipment" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_equipmentForm">
                <msh:sideLink key='ALT+DEL' params="id" action="entityParentDeleteGoParentView-mis_equipment" name="Удалить" confirm="Удалить оборудование?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_equipmentForm" />
    </tiles:put>


</tiles:insert>
