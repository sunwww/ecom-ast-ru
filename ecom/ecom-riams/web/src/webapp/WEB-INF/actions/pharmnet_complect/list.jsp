<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="Patient">Комплекты</msh:title>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/Pharmacy/Administration/Create" key="ALT+N" action="/entityPrepareCreate-pharmnet_complect" name="Создать новый комплект" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="complects" nativeSql="
        select pc.id,pc.name as pc ,ms.name as medserv
        from pharmnetcomplect pc
        left join medservice ms on ms.id = pc.medservice_id"/>
        <msh:tableNotEmpty name="complects" >
            <msh:table  name="complects" action="entityParentView-pharmnet_complect.do" idField="1" >
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Наименование" property="2" />
                <msh:tableColumn columnName="Услуга" property="3" />
            </msh:table>
        </msh:tableNotEmpty>
    </tiles:put>
</tiles:insert>

