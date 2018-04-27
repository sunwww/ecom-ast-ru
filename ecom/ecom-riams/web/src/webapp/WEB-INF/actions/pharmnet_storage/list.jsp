<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="Patient">Склады:</msh:title>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/Pharmacy/Administration/Create" key="ALT+N" action="/entityPrepareCreate-pharmnet_storage" name="Добавить новый склад" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <ecom:webQuery name="storages" nativeSql="
        select ps.id, vb.branch, wf.groupname from pharmnetstorage ps
        left join vocbranch vb  on vb.branchid = ps.branchid
        left join workfunction wf on wf.id = ps.groupworkfuncid"/>
        <msh:tableNotEmpty name="storages" >
            <msh:table  name="storages" action="entityParentView-pharmnet_storage.do" idField="1" >
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Наименование в ФармНет" property="2" />
                <msh:tableColumn columnName="Рабочая функция" property="3" />
            </msh:table>
        </msh:tableNotEmpty>
    </tiles:put>
</tiles:insert>