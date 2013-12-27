<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_lpuAttachedByDepartment.do" defaultField="attachedTypeName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>

            <msh:panel>
            	<msh:row>
            		<msh:autoComplete property="attachedType" label="Тип прикрепления" fieldColSpan="3" horizontalFill="true" vocName="vocAttachedType"/>
            	</msh:row>
				<msh:row styleId='rowLpu'>
		            <msh:autoComplete fieldColSpan="3" property="lpu" label="ЛПУ" horizontalFill="true"
		                              vocName="lpu" size="50"/>
		        </msh:row>
		        <msh:row styleId='rowLpuArea'>
		            <msh:autoComplete fieldColSpan="3" property="area" label="Участок" horizontalFill="true"
		                              parentAutocomplete="lpu" vocName="lpuAreaWithParent"/>
		        </msh:row>		
		        <msh:row>
		        	<msh:textField property="dateFrom" label="Дата прикрепления"/>
		        	<msh:textField property="dateTo" label="Дата открепления"/>
		        </msh:row>
                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
            
            

        </msh:form>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="mis_lpuAttachedByDepartmentForm">
                <msh:sideLink roles="/Policy/Mis/Patient/AttachedByDepartment/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_lpuAttachedByDepartment" name="Изменить"/>

	            <hr/>
                <msh:sideLink roles="/Policy/Mis/Patient/AttachedByDepartment/Delete" key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_lpuAttachedByDepartment" name="Удалить"
                              confirm="Удалить прикрепление?"/>
            </msh:ifFormTypeIsView>

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_lpuAttachedByDepartmentForm"/>
    </tiles:put>


</tiles:insert>