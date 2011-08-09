<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <h1>Список пользователей</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+1' params="id" action="/userView"
                          name="Пользователь"/>
        </msh:sideMenu>
        <tags:menuJaas currentAction="users"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:section>
            <msh:sectionTitle>Роли пользователя</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiply" name="roles" action="roleView.do" idField="id" noDataMessage="Нет ролей">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:removeRole()'>Удалить роль</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>

                    <msh:tableColumn columnName="Название" property="name"/>
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

        <msh:section>
            <msh:sectionTitle>Список доступных ролей</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiple" name="toAdd" action="roleView.do" idField="id" noDataMessage="Нет ролей">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='3'>
                                <msh:toolbar>
                                    <a href='javascript:addRole()'>Добавить роль</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>

                    <msh:tableColumn columnName="Название" property="name"/>
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuUsers'), "selected");

            function addRole() {
                var ids = theTableArrow.getInsertedIdsAsParams("id","toAdd") ;
                if (ids) {
                    window.location = 'userRoleAdd.do?userId=<%=request.getParameter("id")%>&' + ids;
                } else {
                    alert("Нет выделенных ролей");
                }
            }

            function removeRole() {
                var ids = theTableArrow.getInsertedIdsAsParams("id","roles") ;
                if (ids) {
                    window.location = 'userRoleRemove.do?userId=<%=request.getParameter("id")%>&' + ids;
                } else {
                    alert("Нет выделенных ролей");
                }
            }

        </script>
    </tiles:put>


</tiles:insert>