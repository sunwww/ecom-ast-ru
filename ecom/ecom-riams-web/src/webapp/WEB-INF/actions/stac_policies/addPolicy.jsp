<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" title="Список полисов"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+1' params="id" action="/entityParentView-stac_ssl"
                          name="Текущий СЛС"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:section>
            <msh:sectionTitle>Полисы пациента</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiply" name="policies" action="entitySubclassView-mis_medPolicy.do" idField="id" noDataMessage="Нет полисов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:removePolicy()'>Удалить полис из случая</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="Информация о полисе" property="text"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

        <msh:section>
            <msh:sectionTitle>Список доступных полисов</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiple" name="toAdd" action="entitySubclassView-mis_medPolicy.do" idField="id" noDataMessage="Нет полисов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:addPolicy()'>Добавить полис в случай</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="Информация о полисе" property="text"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            //Element.addClassName($('mainMenuUsers'), "selected");

            function addPolicy() {
                var ids = theTableArrow.getInsertedIdsAsParams("id") ;
                if (ids) {
                    window.location = 'stac_policiesAdd.do?sslId=${param.id}&' + ids;
                } else {
                    alert("Нет выделенных полисов");
                }
            }

            function removePolicy() {
                var ids = theTableArrow.getInsertedIdsAsParams("id") ;
                if (ids) {
                    window.location = 'stac_policiesRemove.do?sslId=${param.id}&' + ids;
                } else {
                    alert("Нет выделенных полисов");
                }
            }

        </script>
    </tiles:put>


</tiles:insert>