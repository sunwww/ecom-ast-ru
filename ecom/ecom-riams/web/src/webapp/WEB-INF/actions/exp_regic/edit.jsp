<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Документ</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="id" action="/entityList-exp_regic" name="Список страховых компаний" />
            <msh:sideLink params="id" action="/entityDelete-exp_regic" confirm="Удалить страховую компанию?" name="Удалить" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="entitySave-exp_regic.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />

            <msh:panel>
                <msh:row>
                    <msh:textField property="name" label="Наименование страховой компании" size='50'/>
                </msh:row>
                <msh:row>
                    <msh:textField size="20" property="omcCode" label="Код в ОМС"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
    </tiles:put>


</tiles:insert>