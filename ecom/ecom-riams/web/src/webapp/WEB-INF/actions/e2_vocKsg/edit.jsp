<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocKsgForm" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKsgForm">
        </msh:ifFormTypeIsView>
        <msh:form action="/entitySaveGoView-e2_vocKsg.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="year" />
            <msh:hidden property="profile" />
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
                    <msh:checkBox property="longKsg"/>
                    <msh:checkBox property="isOperation" />
            </msh:row>
                <msh:row>
                    <msh:checkBox property="isFullPayment"/>
            </msh:row>
                <msh:row>
                    <msh:textField property="KZ"/>
                    <msh:textField property="bedSubType" />
            </msh:row>
            <msh:row>

                <msh:checkBox property="doNotUseCusmo"/>
                <msh:checkBox property="isCovid19"/>
            </msh:row>

                <msh:submitCancelButtonsRow colSpan="4" />

            </msh:panel>
        </msh:form>

        <ecom:webQuery nameFldSql="kuksgListSql" name="kuksgList" nativeSql="
        select c.id, c.value, c.startdate, c.finishDate
        from E2KsgCoefficientHistory c where c.ksg_id=${param.id}
  order by c.startDate"/>
        <msh:section title='КУКСГ' createUrl="entityParentPrepareCreate-e2_vocKsgKuksg.do?id=${param.id}">

            <msh:table deleteUrl="entityDelete-e2_vocKsgKuksg.do" name="kuksgList" printToExcelButton="в excel" action="entityParentEdit-e2_vocKsgKuksg.do" idField="1" >
                <msh:tableColumn property="3" columnName="Дата начала действия" />
                <msh:tableColumn property="4" columnName="Дата окончания действия" />
                <msh:tableColumn property="2" columnName="Значение" />
            </msh:table>
        </msh:section>

    </tiles:put>


    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKsgForm">
            <msh:sideMenu>
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