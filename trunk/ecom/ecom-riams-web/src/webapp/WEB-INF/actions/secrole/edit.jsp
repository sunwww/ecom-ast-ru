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
        <h1>Роль </h1>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-secrole.do" defaultField="key">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>

            <msh:panel>
                <msh:row>
                    <msh:textField property="key" label="Ключ" fieldColSpan="1" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true" size='50'/>
                </msh:row>
		        <msh:row>
		            <msh:ifInRole roles="/Policy/Jaas/SecRole/EditSystem" >
		              <msh:checkBox property="isSystems" label="Системный" />
		            </msh:ifInRole>
		            <msh:ifNotInRole roles="/Policy/Jaas/SecRole/EditSystem">
		              <msh:hidden property="isSystems" />
		            </msh:ifNotInRole>
		        </msh:row>
                <msh:row>
                    <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
                </msh:row>
        <msh:row guid="ad5ec-5754-4cbd-bcb5-a592">
          <ecom:oneToManyOneAutocomplete viewAction="entityView-secrole.do" vocName="role" colSpan="3" label="Дочерние роли" property="children" guid="652c9b95-2724-4086-87f5-aefd67b01e8c" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                
                        <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

        <%
            SecRoleForm form = (SecRoleForm) request.getAttribute("secroleForm");
            if (form.isViewOnly()) {
            	ISecRoleService service = (ISecRoleService) Injection.find(request).getService("SecRoleService") ;
                CheckNode root = service.loadPoliciesByRole(Long.parseLong(request.getParameter("id"))) ;
                CheckNodesUtil.removeUnchecked(root);
                request.setAttribute("policies", root);
        %>
        <msh:section>
            <msh:sectionTitle>Список политик у роли</msh:sectionTitle>
            <msh:sectionContent>
                <div id="treeDiv1"></div>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-secrole" name="Изменить" roles="/Policy/Jaas/SecRole/Edit"/>
            <msh:sideLink key="ALT+3" params="id" action="/rolePolicies" name="Изменить политики у групп" roles="/Policy/Jaas/SecRole/EditPolicies"/>
            <msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-secrole" name="Удалить роль" roles="/Policy/Jaas/SecRole/Delete"/>

        </msh:sideMenu>
        <msh:sideMenu title="Администрирование">
        	<msh:sideLink action="/serviceRole-exportEdit.do" roles="/Policy/Jaas/SecRole/Export" 
        		name="Экспорт списка ролей" key="ALT+5"/>
			<msh:sideLink action="/serviceRole-importEdit.do" roles="/Policy/Jaas/SecRole/Import" 
				name="Импорт списка ролей" key="ALT+6"/>
			      <msh:sideLink key="ALT+7" params="id" action="/roleUserEdit" name="Изменить пользователей у роли" roles="/Policy/Jaas/SecUser/EditRoles" guid="c81c676b-39ab-4ad3-b55d-946012bf258d" />

        </msh:sideMenu>
        <tags:menuJaas currentAction="roles"/>

    </tiles:put>

    <tiles:put name="javascript" type="string">
     <link rel="stylesheet" type="text/css" href="css/check/tree.css">
<script type="text/javascript" src="js/YAHOO.js" ></script>
<script type="text/javascript" src="js/log.js"></script>
<script type="text/javascript" src="js/event.js"></script>
<script type="text/javascript" src="js/connect.js"></script>
<script type="text/javascript" src="js/animation.js"></script>

<script type="text/javascript" src="js/dom.js"></script>


<script type="text/javascript" src="js/treeview.js" ></script>
<script type="text/javascript" src="js/TaskNode.js"></script>
<script type="text/javascript" src="js/CheckTaskNode.js"></script>
    
        <script type="text/javascript">
            Element.addClassName($('mainMenuRoles'), "selected");

        <%
            SecRoleForm form3 = (SecRoleForm) request.getAttribute("secroleForm");
            if (form3.isViewOnly()) {
        %>
            var gLogger;
            var tree;
            var nodes = new Array();
            var nodeIndex;


            function treeInit() {
                if (typeof(ygLogger) != "undefined") {
                    ygLogger.init(document.getElementById("logDiv"));
                    //gLogger = new ygLogger("basic.php");
                }

                buildRandomTextNodeTree();
            }

            function createNode(aParent, aName, aChecked, aVisible, aId) {
                return new YAHOO.widget.TextNode(aName, aParent, aVisible) ; //, aChecked, aId);
            }

            function buildRandomTextNodeTree() {
                tree = new YAHOO.widget.TreeView("treeDiv1");

                var root = tree.getRoot() ;
    <%
                RolePoliciesEditAction.printNode(out, (CheckNode) request.getAttribute("policies"));
    %>

                tree.draw();
                tree.expandAll() ;
            }

            var callback = null;

                         treeInit() ;
            <%
            }
            %>

        </script>
    </tiles:put>

</tiles:insert>