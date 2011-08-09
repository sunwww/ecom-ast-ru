<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page import="ru.ecom.diary.web.action.protocol.template.TemplateEditAction" %>
<%@ page import="ru.ecom.diary.ejb.service.protocol.tree.CheckNode" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Config" beginForm="diary_templateForm" guid="4399c99f-8801-4a73-b168-c25c23f8b0ba" />
    <h1>Параметры шаблона </h1>
</tiles:put>


<tiles:put name='body' type='string'>

    <div id="treeDiv1"></div>

    <div id="logDiv"
         style="font-family:Arial, Helvetica, sans-serif;position:relative;overflow:auto;text-align:left;z-index:998;font-size:84%;width:100%;top:0px; height:280px;"></div>

</tiles:put>

<tiles:put name='side' type='string'>
	<tags:style_currentMenu currentAction="mis_medService" />
	<msh:sideMenu title="Параметры шаблона">
		<msh:sideLink name="Сохранить" action=".javascript:save('.do')"/>
		<msh:sideLink name="Не сохранять" action=".javascript:cancel('.do')"/>
		<msh:sideLink name="Раскрыть всех" action=".javascript:tree.expandAll('.do')"/>
		<msh:sideLink name="Свернуть" action=".javascript:tree.collapseAll('.do')"/>
	</msh:sideMenu>
	<msh:sideMenu title="Дополнительно">
      <tags:diary_additionMenu />
	</msh:sideMenu>

</tiles:put>

<tiles:put name="javascript" type="string">
 <link rel="stylesheet" type="text/css" href="css/folders/tree.css">
 
<script type="text/javascript" src="js/YAHOO.js" ></script>
<script type="text/javascript" src="js/log.js"></script>
<script type="text/javascript" src="js/event.js"></script>
<script type="text/javascript" src="js/connect.js"></script>
<script type="text/javascript" src="js/animation.js"></script>
<script type="text/javascript" src="js/dom.js"></script>


<script type="text/javascript" src="js/treeview.js" ></script>
<script type="text/javascript" src="js/TaskNode.js"></script>
<script type="text/javascript" src="js/CheckTaskNode.js"></script>
<msh:javascriptSrc src='./dwr/interface/TemplateProtocolService.js' />

    <script type="text/javascript">
        //Element.addClassName($('mainMenuRoles'), "selected");

        var gLogger;
        var tree;
        var nodes = new Array();
        var nodeIndex;

        function cancel() {
            window.location = "diary_templateView.do?id=${param.id}";
        }

        function save() {
            var node = tree.getRoot() ;
            var added = new Array() ;
            var removed = new Array() ;

            processSave(node, added, removed);
	    
		    TemplateProtocolService.saveParametersByMedService(${param.id}, added, removed, 
			    { 
					callback: function() {
					    var url = "diary_templateView.do?id=${param.id}" ;
					    window.location = url;
					}, errorHandler: function(aMessage) {
					    alert(aMessage) ;
					}
			    }
		    ) ;
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
            var node = new YAHOO.widget.CheckTaskNode(aName, aParent, aVisible, aChecked, aId);
            return node ;
        }
        
        function createNodeByGroup(aParent, aName, aChecked, aVisible, aId) {
                return new YAHOO.widget.TextNode(aName, aParent, aVisible) ; //, aChecked, aId);
        }
        

        function buildRandomTextNodeTree() {
            tree = new YAHOO.widget.TreeView("treeDiv1");

            var root = tree.getRoot() ;
        <%
        TemplateEditAction.printNode(out, (CheckNode) request.getAttribute("params"));
        %>

            tree.draw();
        }

        var callback = null;

        treeInit();
    </script>
</tiles:put>

</tiles:insert>