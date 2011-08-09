<%@ page import="ru.ecom.jaas.web.action.role.RolePoliciesEditAction" %>
<%@ page import="ru.ecom.jaas.ejb.service.CheckNode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='title' type='string'>
    <msh:title mainMenu="Config" title="Экспорт политик"/>
</tiles:put>


<tiles:put name='body' type='string'>
<msh:section title="Выберите политики, которые нужно экспортировать">
    <div id="treeDiv1"></div>

</msh:section>
    <div id="logDiv"
         style="font-family:Arial, Helvetica, sans-serif;position:relative;overflow:auto;text-align:left;z-index:998;font-size:84%;width:100%;top:0px; height:280px;"></div>

        <form action="/servicePolicy-export" method="post" target="_blank" name="mainForm" id="mainForm">
            <input type="hidden" id="policies" name="policies"/>
        </form>
</tiles:put>

<tiles:put name='side' type='string'>
	<msh:sideMenu title="Экспорт">
		<msh:sideLink name="Экспортировать" action=".javascript:save('.do')"/>
		<msh:sideLink name="Отмена" action=".javascript:cancel('.do')"/>
		<msh:sideLink name="Раскрыть всех" action=".javascript:tree.expandAll('.do')"/>
		<msh:sideLink name="Свернуть" action=".javascript:tree.collapseAll('.do')"/>
	</msh:sideMenu>
	
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
        Element.addClassName($('mainMenuPolicies'), "selected");
        $('mainForm').action = "javascript:save()";
        var gLogger;
        var tree;
        var nodes = new Array();
        var nodeIndex;

        function cancel() {
            window.location = "entityParentList-secpolicy.do?id=1";
        }

        function save() {
            //alert("Save") ;
            var node = tree.getRoot() ;
            var added = new Array() ;
            var removed = new Array() ;

            processSave(node, added, removed);
            
            if (added.length==0) {
            	
            	alert("Выберите политики для экспорта") ;
            } else {
            	$('policies').value=added ;
            	$('mainForm').action="servicePolicy-export.do";
            	$('mainForm').target="_blank";
            	$('mainForm').submit() ;
            }
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