<%@ page import="ru.ecom.jaas.web.action.role.RolePoliciesEditAction" %>
<%@ page import="ru.ecom.jaas.ejb.service.CheckNode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='title' type='string'>
    <h1>Политики у группы </h1>
</tiles:put>


<tiles:put name='body' type='string'>


    <div id="treeDiv1"></div>

    <div id="logDiv"
         style="font-family:Arial, Helvetica, sans-serif;position:relative;overflow:auto;text-align:left;z-index:998;font-size:84%;width:100%;top:0px; height:280px;"></div>

</tiles:put>

<tiles:put name='side' type='string'>

        <ul class='firstMenu' style='position: fixed'>
            <li class='title'></li>

            <li>
                <a href='javascript:save()'>Сохранить</a>
            </li>

            <li>
                <a href='javascript:cancel()'>Не сохранять</a>
            </li>

            <li>
                <a href='javascript:tree.expandAll()'>Раскрыть всex</a>
            </li>

            <li>
                <a href='javascript:tree.collapseAll()'>Свернуть</a>
            </li>
        </ul>



        <%--<msh:sideLink key="ALT+1" params="" action="/entityList-secuser" name="⇧ Список пользователей" />--%>

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
<msh:javascriptSrc src='./dwr/interface/RolePoliciesService.js' />

    <script type="text/javascript">
        Element.addClassName($('mainMenuRoles'), "selected");

        var gLogger;
        var tree;
        var nodes = new Array();
        var nodeIndex;

        function cancel() {
            window.location = "roleView.do?id=<%=request.getParameter("id")%>";
        }

        function save() {
            //alert("Save") ;
            var node = tree.getRoot() ;
            var added = new Array() ;
            var removed = new Array() ;

            processSave(node, added, removed);

            //var url = "rolePoliciesSave.do?id=<%=request.getParameter("id")%>&a=" + added.join("&a=") + "&r=" + removed.join("&r=")  ;
	    
	    RolePoliciesService.savePolicies(<%=request.getParameter("id")%>, added, removed, { 
		callback: function() {
		    var url = "roleView.do?id=<%=request.getParameter("id")%>" ;
		    window.location = url;
		},
	        errorHandler: function(aMessage) {
		    alert(aMessage) ;
		}
	    }) ;
            //window.location = url;
        }

        function processSave(aNode, aAdded, aRemoved) {
            if (aNode.theId) {
                if (aNode.checkState == 2) {
                    aAdded[aAdded.length] = aNode.theId;
                } else {
                    aRemoved[aRemoved.length] = aNode.theId;
                }
            }
            for (var i = 0; i < aNode.children.length; i++) {
                processSave(aNode.children[i], aAdded, aRemoved);
            }
        }

        function treeInit() {
            if (typeof(ygLogger) != "undefined") {
                ygLogger.init(document.getElementById("logDiv"));
                //gLogger = new ygLogger("basic.php");
            }

            buildRandomTextNodeTree();
        }

        function createNode(aParent, aName, aChecked, aVisible, aId) {
            return new YAHOO.widget.CheckTaskNode(aName, aParent, aVisible, aChecked, aId);
        }

        function buildRandomTextNodeTree() {
            tree = new YAHOO.widget.TreeView("treeDiv1");

            var root = tree.getRoot() ;
        <%
        RolePoliciesEditAction.printNode(out, (CheckNode) request.getAttribute("policies"));
        %>

            tree.draw();
        }

        var callback = null;

        treeInit();
    </script>
</tiles:put>

</tiles:insert>