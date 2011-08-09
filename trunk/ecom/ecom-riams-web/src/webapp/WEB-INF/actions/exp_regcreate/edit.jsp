<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Reg">Экспорт реестров ОМС</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="/exp_regCreateSave.do" defaultField="companyName">
            <msh:hidden property="saveType" />

            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="company" label="Страховая компания" size="50" fieldColSpan="3"/>
                </msh:row>
                
                <msh:row>
                    <msh:autoComplete property="timeImport" label="Реестр" size="50" parentId="exp_regCreateForm.documentId" fieldColSpan="3"/>
                </msh:row>
                
                <msh:row>
                    <msh:autoComplete property="template" label="Шаблон" size="50" fieldColSpan="3"/>
                </msh:row>

                <msh:row>
                    <msh:autoComplete property="exportType" label="Печать" size="50" fieldColSpan="3"/>
                </msh:row>
                
                <msh:row>
                    <msh:autoComplete property="format" label="Формат" size="50" parentId="exp_regCreateForm.documentId" fieldColSpan="3"/>
                </msh:row>
                
                <msh:row>
                    <msh:textField property="billDate" label="Дата счета" fieldColSpan="1" fieldColSpan="3"/>
                </msh:row>
                <msh:separator colSpan="4" label="Номера реестров/счетов" />
                <msh:row>
                    <msh:textField property="regWorking" label="Работающие: реестр" />
                    <msh:textField property="billWorking" label="счет" />
                </msh:row>
                <msh:row>
                    <msh:textField property="regPens" label="Пенсионеры: реестр" />
                    <msh:textField property="billPens" label="счет" />
                </msh:row>
                <msh:row>
                    <msh:textField property="regChild" label="Дети и подростки: реестр" />
                    <msh:textField property="billChild" label="счет" />
                </msh:row>
                <msh:row>
                    <msh:textField property="regOther" label="Прочие неработающие: реестр" />
                    <msh:textField property="billOther" label="счет" />
                </msh:row>
                
                <msh:separator label="Для детских учреждений" colSpan="4"/>
                <msh:row>
                    <msh:textField property="child55" label="Дети (55): реестр" />
                    <msh:textField property="billChild55" label="счет" />
                </msh:row>
                
                <msh:row>
                    <msh:textField property="child50" label="Подростки (50): реестр" />
                    <msh:textField property="billChild50" label="счет" />
                </msh:row>
                
                <msh:submitCancelButtonsRow labelSave="Напечатать реестр" labelSaving="Печать реестра..." colSpan="4" />
            </msh:panel>
        </msh:form>
    </tiles:put>

</tiles:insert>