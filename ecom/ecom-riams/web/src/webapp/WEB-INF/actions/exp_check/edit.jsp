<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/exp" prefix="exp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >



    <tiles:put name='body' type='string' >
        <msh:form action="entityParentSaveGoView-exp_check.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="document" />

            <msh:panel>
                <msh:row>
                    <msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="sn" label="Порядковый номер" fieldColSpan="1"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="checkType" label="Тип сообщения" fieldColSpan="3" size='60' vocName="checkType"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="actualDateFrom" label="Дата действия с" />
                    <msh:textField property="actualDateTo" label="по" />
                </msh:row>
                <msh:row>
                    <msh:checkBox property="disabled" label="Отключен" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="checkId" label="Проверка" fieldColSpan="3" size='60' vocName="checks"/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
        
   <msh:ifFormTypeIsView formName="exp_checkForm">
            <msh:section title="Список настроек">
                <exp:checkPropertyList />
                <msh:table name="list" action="exp_checkPropertyEdit.do" idField="checkAndProperty">
                    <msh:tableColumn columnName="Настройка" property="property"/>
                    <msh:tableColumn columnName="Значение" property="value"/>
                </msh:table>
            </msh:section>
    </msh:ifFormTypeIsView>
    
        
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="id" action="/entityParentListRedirect-exp_check" name="⇧ Список проверок" />
			<msh:ifFormTypeIsView formName="exp_checkForm">
	            <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-exp_check" name="Изменить" />
	            <msh:sideLink key="ALT+3" params="id" action="/exp_checkPropertyList" name="Список настроек" />
    	        <msh:sideLink params="id" confirm="Удалить проверку?" action="/entityParentDelete-exp_check" name="Удалить" />
			</msh:ifFormTypeIsView>
			
        </msh:sideMenu>
    </tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
    	.value {
    		white-space: pre;
    		font-family: monospace;
    		font-size: 130% ;
    		background-color: white ;
    		color: black ;
    	}
    </style>
</tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_checkForm" />
    </tiles:put>


</tiles:insert>