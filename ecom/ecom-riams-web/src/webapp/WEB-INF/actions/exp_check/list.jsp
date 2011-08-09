<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importdocumentForm"  title="Список проверок"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityEdit-exp_importdocument" name="⇧ К документу" />
            
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-exp_check" name="Создать новую проверку" />
        </msh:sideMenu>
        <msh:sideMenu title="Легенда">
        	<li class='checkType1'>Сигнальное</li>
        	<li class='checkType2'>Изменение<li>
        	<li class='checkType3'>Критическое<li>
        	<li class='disabled'>Отключено<li>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityParentView-exp_check.do" decorator="ru.ecom.expomc.web.actions.check.CheckTableDecorator">
            <msh:tableColumn columnName="№" property="sn" />
            <msh:tableColumn columnName="Название" property="name" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
            <msh:tableColumn columnName="Дата действия с" property="actualDateFrom" />
            <msh:tableColumn columnName="Дата действия по" property="actualDateTo" />
            <msh:tableColumn columnName="Отключен" property="disabled" />
            <msh:tableColumn columnName="Свойства" property="properties" />
        </msh:table>
    </tiles:put>
	
<tiles:put name="style" type="string">
    <style type="text/css">
        .checkType2 {
        	color: green ;
        }
        .checkType3 {
        	color: red ;
        }
        .disabled {
        	color: gray ;
        }
    	.properties {
    		white-space: pre;
    		font-family: monospace;
    		font-size: 130% ;
    		background-color: white ;
    		color: black ;
    	}
    </style>
</tiles:put>
	
</tiles:insert>