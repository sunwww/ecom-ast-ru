<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_extDispPriceForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />

    </tiles:put>
    <tiles:put name="body" type="string">
       
        <msh:form action="/entitySaveGoView-e2_extDispPrice.do" defaultField="dispType" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>

                <msh:row>
                   <msh:autoComplete property="dispType" vocName="vocE2FondV016" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="sex" vocName="vocSex" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="cost" label="Цена полного случая" />
                </msh:row>
                <msh:row>
                    <msh:textField property="ages" label="Возраста (через запятую)" />
                    <msh:textField property="ageGroup"  />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateFrom" />
                    <msh:textField property="dateTo" />
                </msh:row>

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />

            </msh:panel>
        </msh:form>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_extDispPriceForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
        <ecom:webQuery name="isClosedList" nativeSql="select id from e2listentry where id=${param.id} and (isClosed is null or isClosed='0')"/>
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_extDispPrice" name="Изменить" roles="/Policy/E2/Edit" />

                <msh:sideLink action="/javascript:closeListEntry(false)" name="Открыть заполнение" roles="/Policy/E2/Admin" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_extDispPriceForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">

             </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

