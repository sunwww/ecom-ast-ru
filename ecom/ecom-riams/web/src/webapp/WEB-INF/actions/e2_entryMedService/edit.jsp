<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_entryMedServiceForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <tags:E2ServiceAdd name="Diagnosis"/>
        <msh:form action="/entityParentSaveGoParentView-e2_entryMedService.do" defaultField="serviceDate">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="entry" />
            <msh:panel>
     <msh:separator colSpan="4" label="Общие"/>
                <msh:row>
                    <msh:textField property="serviceDate"  size="10"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="medService" vocName="vocMedService" size="50" horizontalFill="true" fieldColSpan="3"/>
                </msh:row><msh:row>
                    <msh:autoComplete property="mkb" vocName="vocIdc10" fieldColSpan="3"/>
                </msh:row>

    <msh:separator colSpan="4" label="Служебная информация"/>
            <msh:row>
                <msh:textField property="doctorSnils" size="20"  />
                <msh:textField property="cost" />
            </msh:row><msh:row>
                <msh:autoComplete property="doctorSpeciality" vocName="vocE2FondV021" fieldColSpan="3" size="50"/>
            </msh:row><msh:row>
                    <msh:textField property="comment" size="50" fieldColSpan="3" />
            </msh:row>
                <msh:submitCancelButtonsRow colSpan="1" />
            </msh:panel>
        </msh:form>
        <msh:section title="Мед. импланты">
            <ecom:webQuery name="medImplants" nativeSql="select mi.id, mi.serialNumber, mi.typeCode||' '||vmi.name
      from EntryMedServiceMedImplant mi
      left join VocSurgicalImplant vmi on vmi.code = mi.typeCode
      where mi.medService_id=${param.id} "/>
            <msh:tableNotEmpty name="medImplants">
                <msh:table idField="1" name="medImplants" styleRow="3" deleteUrl="entityParentDeleteGoParentView-e2_entryMedServiceMedImplant.do"
                           action="entityParentEdit-e2_entryMedServiceMedImplant.do">
                    <msh:tableColumn columnName="Серийный номер" property="2"/>
                    <msh:tableColumn columnName="Тип" property="3"/>
                </msh:table>
            </msh:tableNotEmpty>
        </msh:section>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_entryMedServiceForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <msh:ifFormTypeIsView formName="e2_entryMedServiceForm">

                <script type="text/javascript">

                    function deleteEntryFactor(factor) {
                        addOrDeleteEntryFactor(factor,true);
                    }


                </script>

        </msh:ifFormTypeIsView>

            <script type="text/javascript">

            </script>
        </msh:ifFormTypeAreViewOrEdit>

    </tiles:put>

    <tiles:put name="side" type="string">
<%--        <msh:ifFormTypeIsView formName="e2_entryMedServiceForm">--%>
            <msh:sideMenu>
                <msh:sideLink params="id" action="/entityParentEdit-e2_entryMedService" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-e2_entryMedService" name="Удалить" roles="/Policy/E2/Delete" />
                <msh:sideLink params="id" action="/entityParentPrepareCreate-e2_entryMedServiceMedImplant" name="Создать мед. имплант" roles="/Policy/E2/Create" />

            </msh:sideMenu>
<%--        </msh:ifFormTypeIsView>--%>
    </tiles:put>
</tiles:insert>

