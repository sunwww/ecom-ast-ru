<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <h1>Список ролей</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-secrole" name="Создать новую роль" />
        </msh:sideMenu>
        <msh:sideMenu title="Администрирование">
        	<msh:sideLink action="/serviceRole-exportEdit.do" roles="/Policy/Jaas/SecRole/Export" 
        		name="Экспорт списка ролей"/>
			<msh:sideLink action="/serviceRole-importEdit.do" roles="/Policy/Jaas/SecRole/Import" 
				name="Импорт списка ролей"/>
        </msh:sideMenu>
        <tags:menuJaas currentAction="roles"/>

    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="roleView.do" idField="id">
            <msh:tableColumn columnName="Название" property="name" />
            <msh:tableColumn columnName="Комментарий" property="comment" />
        </msh:table>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuRoles'), "selected") ;
        </script>
    </tiles:put>

    
</tiles:insert>