
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <!-- Upper -->
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="pharmnet_complect" beginForm="pharmnet_complectForm"/>
    </tiles:put>


    <!-- Sider -->
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="pharmnet_complectForm">
            <msh:sideMenu title="Управление">
                <msh:sideLink key="ALT+1" params="id"
                              action="/entityEdit-pharmnet_complect" name="Изменить"
                              roles="/Policy/Mis/Pharmacy/Administration/Edit" />

                <msh:sideLink key="ALT+2" params="id"
                              action="/entityPrepareCreate-pharmnet_complectRow" name="Добавить товары"
                              roles="/Policy/Mis/Pharmacy/Administration/Create" />

                <msh:sideLink styleId="viewShort" params="id"
                              action="/entityList-pharmnet_complect.do" name="К списку комплектов"
                              roles="/Policy/Mis/Pharmacy/Administration/Create" />

                <msh:sideLink key="ALT+DEL" confirm="Удалить?"
                              params="id"
                              action="/entityParentDeleteGoParentView-pharmnet_complect" name="Удалить"
                              roles="/Policy/Mis/Pharmacy/Administration/Edit" />


            </msh:sideMenu>


        </msh:ifFormTypeIsView>
    </tiles:put>


    <!-- Body -->
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-pharmnet_complect.do" defaultField="name">
                <msh:hidden property="id" />
                <msh:hidden property="saveType" />
                <msh:row>
                    <msh:textField property="name" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>

                <msh:row>
                    <msh:autoComplete  property="medService_id" label="Медицинская услуга" vocName="medService" fieldColSpan="3" horizontalFill="true" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
        </msh:form>

        <msh:ifFormTypeIsView formName="pharmnet_complectForm">
            <ecom:webQuery name="complects" nativeSql="
            select pcr.id, vg.drug||'('||vg.form||')' as dr ,cast(pcr.count as numeric) from pharmnetcomplectrow pcr
            left join vocgoods vg on vg.regid = pcr.regid
            left join pharmnetcomplect pc on pc.id = pcr.complectid
            where pcr.complectid=${param.id}"/>
            <msh:tableNotEmpty name="complects" >
                <msh:table  name="complects" action="entityView-pharmnet_complectRow.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Наименование" property="2" />
                    <msh:tableColumn columnName="Кол-во" property="3" />
                </msh:table>
            </msh:tableNotEmpty>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <!-- Scripts -->
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>
        <script type="text/javascript">

        </script>
    </tiles:put>
</tiles:insert>

