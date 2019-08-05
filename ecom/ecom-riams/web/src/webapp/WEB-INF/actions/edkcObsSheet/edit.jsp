<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name="side" type="string">
        <tags:vocObservRes name="vocObservRes"/>
        <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Действия">
            <msh:sideLink key="ALT+C" action="/javascript:showvocObservResJs()" name="Закрыть ЛН" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Edit" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-edkcObsSheet.do" defaultField="id" guid="b55hb-b971-441e-9a90-5155c07"
                  fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="patient" />
            <msh:panel>
                <msh:row>
                    <msh:textField viewOnlyField="true" property="startDate" label="Дата открытия" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                    <msh:textField viewOnlyField="true" property="createUsername" label="Логин" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="specialistStart" label="Открыл" size="100" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
                </msh:row>
                <msh:row>
                    <msh:textField viewOnlyField="true" property="finishDate" label="Дата закрытия" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                    <msh:textField viewOnlyField="true" property="editUsername" label="Логин" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                    <msh:autoComplete property="observResult" label="Результат наблюдения" size="100" vocName="vocObservationResult" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true"  />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="specialistFin" label="Закрыл" size="100" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
                </msh:row>
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="edkcObsSheetForm"/>
    </tiles:put>
    <tiles:put name='javascript' type='string'>
        <script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
        <script type="text/javascript">
            function showvocObservResJs() {
                if ($('patient').value!='')
                    showvocObservRes($('patient').value,window.location.href);
                else
                    showToastMessage('Необходимо выбрать пациента!',null,true);
            }
        </script>
    </tiles:put>
</tiles:insert>