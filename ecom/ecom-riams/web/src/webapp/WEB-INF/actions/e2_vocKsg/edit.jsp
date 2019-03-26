<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocKsgForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKsgForm">
        </msh:ifFormTypeIsView>
        <msh:form action="/entitySaveGoView-e2_vocKsg.do" defaultField="name" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
                <msh:row>
                    <msh:autoComplete property="group" vocName="vocKsgGroup" fieldColSpan="3" size="100" viewOnlyField="true"/>
                </msh:row>
                <msh:row>
                <msh:textField property="code"/>
                <msh:textField property="name" size="100"/>
            </msh:row>
                <msh:row>
                    <msh:textField property="KZ"/>
                    <msh:textField property="bedSubType" />
            </msh:row>

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />

            </msh:panel>
        </msh:form>

        <ecom:webQuery nameFldSql="kuksgListSql" name="kuksgList" nativeSql="
        select c.id, c.value, c.startdate, c.finishDate
        from E2KsgCoefficientHistory c where c.ksg_id=${param.id}
  order by c.startDate"/>
        <msh:section title='КУКСГ' createUrl="entityParentPrepareCreate-e2_vocKsgKuksg.do?id=${param.id}">

            <msh:table deleteUrl="entityDelete-e2_vocKsgKuksg.do" name="kuksgList" printToExcelButton="в excel" action="entityParentEdit-e2_vocKsgKuksg.do" idField="1" >
                <msh:tableColumn property="3" columnName="Дата начала действия" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                <msh:tableColumn property="4" columnName="Дата окончания действия" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                <msh:tableColumn property="2" columnName="Значение" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
            </msh:table>
        </msh:section>

    </tiles:put>


    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKsgForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocKsg" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityDelete-e2_vocKsg" name="Удалить" roles="/Policy/E2/View" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKsgForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                function addHistoryNumberToList() {

                }

            </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>