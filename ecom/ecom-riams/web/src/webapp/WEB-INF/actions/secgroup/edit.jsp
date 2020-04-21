<%@ page import="ru.ecom.jaas.ejb.form.SecRoleForm" %>
<%@ page import="ru.ecom.jaas.ejb.service.CheckNode"%>
<%@ page import="ru.ecom.jaas.web.action.role.RolePoliciesEditAction"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.jaas.ejb.service.ISecRoleService"%>
<%@page import="ru.ecom.web.util.Injection"%>
<%@page import="ru.ecom.jaas.web.action.role.CheckNodesUtil"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name='title' type='string'>
        <h1>Группы пользователей</h1>
    </tiles:put>
    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-secgroup.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:panel>
                <msh:row>
                    <msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true" size='50'/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
		        <msh:row>
		          <ecom:oneToManyOneAutocomplete viewAction="userView.do" vocName="secUser" colSpan="3" label="Пользователи" property="secUsers" />
		        </msh:row>
                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Создать">
            <msh:sideLink key='ALT+N' params="" action="/entityPrepareCreate-secgroup" name="Группу" />
        </msh:sideMenu>
        <msh:sideMenu title="Группа">
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-secgroup" name="Изменить" roles="/Policy/Jaas/SecGroup/Edit"/>
            <msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-secgroup" name="Удалить" roles="/Policy/Jaas/SecGroup/Delete"/>
        </msh:sideMenu>
        <tags:menuJaas currentAction="groups"/>
    </tiles:put>
</tiles:insert>