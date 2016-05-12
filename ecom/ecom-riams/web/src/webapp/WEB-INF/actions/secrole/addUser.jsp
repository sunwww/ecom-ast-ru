<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <h1>Список пользователей с ролью ${roleInfo }</h1>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+1' params="id" action="/roleView"
                          name="Роль"/>
        </msh:sideMenu>
        <tags:menuJaas currentAction="roles"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:section>
            <msh:sectionTitle>Пользователи с ролью ${roleInfo}</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiply" name="users" action="userView.do" idField="id" noDataMessage="Нет пользователей">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='4'>
                                <msh:toolbar>
                                    <a href='javascript:removeUser()'>Удалить роль у пользователя</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>

                    <msh:tableColumn columnName="Логин" property="login"/>
                    <msh:tableColumn columnName="Полное имя" property="fullname"/>
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

        <msh:section>
            <msh:sectionTitle>Список доступных пользователей для добавления роли</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table selection="multiple" name="toAdd" action="userView.do" idField="id" noDataMessage="Нет пользователей">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='4'>
                                <msh:toolbar>
                                    <a href='javascript:addUser()'>Добавить пользователя</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>

                    <msh:tableColumn columnName="Логин" property="login"/>
                    <msh:tableColumn columnName="Полное имя" property="fullname"/>
                    <msh:tableColumn columnName="Комментарий" property="comment"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuRoles'), "selected");

            function addUser() {
                var ids = theTableArrow.getInsertedIdsAsParams("id","toAdd") ;
                if (ids) {
                    window.location = 'roleUserAdd.do?roleId=${param.id}&' + ids;
                } else {
                    alert("Нет выделенных пользователей");
                }
            }

            function removeUser() {
                var ids = theTableArrow.getInsertedIdsAsParams("id","users") ;
                if (ids) {
                    window.location = 'roleUserRemove.do?roleId=${param.id}&' + ids;
                } else {
                    alert("Нет выделенных пользователей");
                }
            }

        </script>
    </tiles:put>


</tiles:insert>