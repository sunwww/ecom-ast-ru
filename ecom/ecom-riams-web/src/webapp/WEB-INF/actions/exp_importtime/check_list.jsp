<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

<%--     <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importdocumentForm" title="Импортировано по дате"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityEdit-exp_importdocument" name="⇧ К документу" />

            <msh:sideLink key="ALT+2" params="${param.parent }" action="/exp_importTimeUploadEdit" name="Загрузить данные" />
        </msh:sideMenu>
    </tiles:put>
 --%>
    <tiles:put name='body' type='string' >
        <ecom:webQuery name="list" nativeSql="select fir.id, fir.importresult from fondimport fi 
left join fondimportreestr fir on fir.importtype_id=fi.id
where fi.id=${param.id}
order by fir.id"/>
        <msh:table name="list" action="javascript:void()" idField="1">
            <msh:tableColumn columnName="Результат" property="2" />${param.parent}
        </msh:table>
    </tiles:put>


</tiles:insert>