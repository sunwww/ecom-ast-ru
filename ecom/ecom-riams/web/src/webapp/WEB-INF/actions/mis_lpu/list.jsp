<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="ru.ecom.web.util.ActionUtil" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ЛПУ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' roles="/Policy/Mis/MisLpu/Create" params="" action="/entityPrepareCreate-mis_lpu"
                          name="Добавить ЛПУ"/>

        </msh:sideMenu>
        <msh:sideMenu title="Перейти">
            <msh:sideLink key="ALT+1" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий"
                          roles="/Policy/Mis/WorkPlace/View"/>
            <msh:sideLink roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment"
                          action="/entityList-mis_copyingEquipment" name="Копировальное оборудование"
                          title="Показать сведения по копировальному оборудованию"/>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string'>
        <%
            String defaultLpuId = ActionUtil.getDefaultParameterByConfig("DEFAULT_LPU", "", request);
            if (defaultLpuId != null && !defaultLpuId.equals("")) {
                request.setAttribute("defaultLpuId", defaultLpuId);
        %>
        Текущее ЛПУ:
        <ecom:webQuery name="defaultLpu" nameFldSql="defaultLpu_sql"
                       nativeSql="select id, codef, name from mislpu where id=${defaultLpuId}"/>
        <msh:table name="defaultLpu" action="entityView-mis_lpu.do" idField="1">
            <msh:tableColumn columnName="Код" property="1"/>
            <msh:tableColumn columnName="Код ОМС" property="2"/>
            <msh:tableColumn columnName="Наименование ЛПУ" property="3"/>
        </msh:table>

        <%
            }
            String id = request.getParameter("id");
            if (id == null || id.equals("-1")) {
                request.setAttribute("idSql", " is null");
            } else {
                request.setAttribute("idSql", " =" + id);
            }
        %>
        <ecom:webQuery name="lpus" nameFldSql="lpus_sql"
                       nativeSql="select id, codef, name from mislpu where parent_id${idSql}"/>
        ЛПУ в системе
        <msh:table name="lpus" action="entityView-mis_lpu.do" idField="1">
            <msh:tableColumn columnName="Код" property="1"/>
            <msh:tableColumn columnName="Код ОМС" property="2"/>
            <msh:tableColumn columnName="Наименование ЛПУ" property="3"/>
        </msh:table>
    </tiles:put>
</tiles:insert>