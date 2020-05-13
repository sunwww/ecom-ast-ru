<%@page import="ru.ecom.diary.ejb.service.protocol.tree.CheckNode"%>
<%@page import="ru.ecom.diary.web.action.protocol.template.TemplateEditAction"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
<tiles:put name="style" type="string">
<style> 

h1, h3, h4 {
color: #1E586F;
margin: 25px 0 5px 5px;
}


table#parameterCheckTable, table#parameterCheckTable * {
-webkit-user-select: none;
-moz-user-select: none;
-o-user-select: none;
user-select: none;
border: 1px solid;
}
table#parameterCheckTable {
border-collapse: collapse;
width: 80%;
cursor: pointer;
background: #fff;
}
table#parameterCheckTable thead {
background: #437196;
color: #fff;
font-weight: bold;
}
table#parameterCheckTable td, table#parameterCheckTable th {
border: 1px solid #444;
padding: 5px;
}
.dragTr {
position: absolute;
z-index: 2;
width: 100%;
}
.plTr td {
border: 1px dashed #000;
background: #eee;
}
</style>

</tiles:put>
<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Config" beginForm="diary_templateForm" title="Изменение параметров шаблона"/>
    <h1>Параметры шаблона </h1>
</tiles:put>


<tiles:put name='body' type='string'>

    <div id="treeDiv1"></div>
    <div id="parameterCheckDiv">
   <br/>
   <br/>
   <h2>Порядок сортировки выбранных элементов <a href='javascript:void(0)' onclick='save()'>Сохранить изменения</a> <a href='javascript:void(0)' onclick='save()'>Отменить</a> </h2>
    <table id="parameterCheckTable">
    	<thead>
    	<tr>
    		<td>Параметр</td>
    		<td></td>
    	</tr>
    	</thead>
    	<tbody id="parameterCheckTbody">
    	</tbody>
    </table>
    </div>

    <div id="logDiv"
         style="font-family:Arial, Helvetica, sans-serif;position:relative;overflow:auto;text-align:left;z-index:998;font-size:84%;width:100%;top:0px; height:10px;"></div>

</tiles:put>

<tiles:put name='side' type='string'>
	
	<msh:sideMenu title="Параметры шаблона">
		<msh:sideLink name="Сохранить" action=".javascript:save('.do')"/>
		<msh:sideLink name="Не сохранять" action=".javascript:cancel('.do')"/>
		<msh:sideLink name="Раскрыть всех" action=".javascript:tree.expandAll('.do')"/>
		<msh:sideLink name="Свернуть" action=".javascript:tree.collapseAll('.do')"/>
	</msh:sideMenu>
	<msh:sideMenu title="Дополнительно">
      <tags:voc_menu currentAction="medService" />
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
<msh:javascriptSrc src='./dwr/interface/TemplateProtocolService.js' />

    <script type="text/javascript">
    var arrcheck = [];
    
    
    var dragTable = function(table, callbacks) {
            //перетаскиваемая строка
            var dragTr = false;
            
            //тело таблицы, в котором будет работать перетаскивание строк
            var tbody = false;
            
            //координата Y в момент нажатия кнопки мыши. Нужно для определения куда движется курсор от момент нажатия: вверх или вниз.
            var startY = false;
            
            //номер строки в момент перетаскивание
            var indexStart = false;
            
            //перетаскиваемая строка
            var trStart = false;

            //Метод, который привязывает события к элементам
            var init = function() {
                //Получаем tbody
                tbody = table.getElementsByTagName('tbody')[0];


                //Функция start выполняется в момент нажатия по таблице
                table.onmousedown = start;
                
                //Функция stop выполняется в момент отпуска кнопки мыши или выхода курсора за пределы таблицы
                table.onmouseleave = stop;
                table.onmouseup = stop;
                //Функция move выполняется, когда курсор перемещается относительно таблицы
                table.onmousemove = move;
            }

            //Выполняется в момент нажатия по таблице
            var start = function(e) {
                //Элемент, по которому нажали. Это должен быть TD
                var target = e.target || e.srcElement;
                //Получаем родителя TD - это TR
                var tr = target.parentNode;
                //Если родитель TR не TBODY, а например THEAD, то ничего не делаем
                if (tr.parentNode.nodeName !== 'TBODY') return false;

                //Если уже перетаскиваемая строка была определена, то ничего не делаем
                if (dragTr) return false;
                
                //Определяем две разные переменные одной и той же строкой
                trStart = tr;
                dragTr = tr;
                //Установим для перетаскиваемой строки класс
                dragTr.setAttribute('class', 'plTr');
                //Определим Y-координату курсора в момент нажатия
                startY = e.y || e.clientY;

                //Определим номер позиции строки в таблице
                indexStart = __getIndex(dragTr)
                //Если определена callback-функция start, то выполним ее
                if (callbacks && callbacks.start) callbacks.start(table, trStart, indexStart);
            }

            //Выполняется в момент отпуска кнопки мыши или выхода курсора за пределы таблицы
            var stop = function(e) {
                //Если перетаскиваемая таблица не определена, то ничего не делаем
                if (!dragTr) return false;
                
                //Удаляем аттрибут class
                dragTr.removeAttribute('class');
                startY = false; //Сбрасываем Y-координату курсора
                dragTr = false; //Сбрасываем перетаскиваем строку
                //Если определена callback-функция stop, то выполним ее
                if (callbacks && callbacks.stop) callbacks.stop(table, trStart, indexStart, __getIndex(trStart));
            }
            
            //Выполняется, когда курсор перемещается относительно таблицы
            var move = function(e) {
                //Если перетаскиваемая таблица не определена, то ничего не делаем
                if (!dragTr) return false;
                //Определяем элемент над курсором - это должен быть TD
                var target = e.target || e.srcElement;
                //Получаем родителя TD - это TR
                var currentTr = target.parentNode;
                //Убедимся что это строка над курсором не является перетаскиваемой строкой и все эти действия происходят относительно TBODY
                if (currentTr === dragTr || currentTr.nodeName !== 'TR' || currentTr.parentNode.nodeName !== 'TBODY') return false;

                //Определим Y-координату курсора
                var y = e.y || e.clientY;
                //Определим куда движется курсор
                var top = y < startY;
                //Переопределим Y-координату нажатия на таблицу
                startY = y;

                //Если движется вверх
                if (top) {
                    tbody.insertBefore(dragTr, currentTr);
                }
                //Если вниз
                else {
                    tbody.insertBefore(currentTr, dragTr);
                }
                
                //Если определена callback-функция dragging, то выполним ее
                if (callbacks && callbacks.dragging) callbacks.dragging(table, dragTr, currentTr, __getIndex(trStart))
            }

            //Определение номера позиции строки
            var __getIndex = function(tr) {
                //Получаем массив из строк
                var trs = tbody.getElementsByTagName('tr');
                //В цикле проходим все строки
                for (var i = 0, length = trs.length; i < length; i++) {
                    //Если строка из цикла равна указанной строки, то вернуть (индекс из массива + 1)
                    if (trs[i] === tr) return (i+1);
                }
                //Иначе вернуть 0
                return 0;
            }
            
            //Выполнить функцию, которая привяжет события к объекту
            init();
        } 
    
    
    
    //function check() {
    	
    	window.onload = function() {
    		
	    	        dragTable(document.getElementById('parameterCheckTable'), {
	    	            start: function(table, el, index) {
	    	                console.log('start');
	    	                console.log(table);
	    	                console.log(el);
	    	                console.log(index);
	    	                console.log('-----')
	    	            },
	    	            stop: function(table, el, indexBefore, index) {
	    	                console.log('stop');
	    	                console.log(table);
	    	                console.log(el);
	    	                console.log(indexBefore);
	    	                console.log(index);
	    	                console.log('-----')
	    	            }
	    	        });
	    	    } 
    //}
    
        //Element.addClassName($('mainMenuRoles'), "selected");
    function updateRow(aId,aTitle,aCheckState,aTreeId,aIndex) {
    	var index="_tr"+aId ;
    	var tbody = document.getElementById('parameterCheckTbody');
    	if (aCheckState==2) {
    		if (!document.getElementById(index)) {
				
			    var row = document.createElement("TR");
				row.id = index;
			    row.value=aId ;
			    tbody.appendChild(row);
			   	
			    // Создаем ячейки в вышесозданной строке 
			    // и добавляем тх 
			   // var td1 = document.createElement("TD");
			    var td2 = document.createElement("TD");
			    var td3 = document.createElement("TD");
			    //td1.align="right";
			    
			   	td2.innerHTML=aTitle ;
			   	td3.innerHTML="<a href='javascript:void(0)' onclick=\"YAHOO.widget.TreeView.getNode('"+aTreeId
			   			+"',"+aIndex+").checkClick()\"> Удалить из выбранных </a>" 
			   	+" <a target='_blank' href='diary_parameterView.do?id="+aId+"'> Перейти к параметру </a>" ;
			   	
			     //row.appendChild(td1);
				 row.appendChild(td2);
				 row.appendChild(td3);
			 
    		} else {
    			alert("Элемент уже существует))") ;
    		}
			
    	} else {
    		if (document.getElementById(index)) {
    			
    			var tr =document.getElementById(index) ;
    			tr.parentNode.removeChild(tr) ;
    		}
    	}
	    
	   	
	}
YAHOO.widget.CheckTaskNode = function(oData, oParent, expanded, checked, aId) {
    this.logger = new ygLogger("CheckTaskNode");

    if (oParent) {
        this.init(oData, oParent, expanded);
        this.setUpLabel(oData);
        this.checked = checked;
        this.checkState = checked?2:0;
        this.theId = aId ;
        this.checkClick= function(){
        	if (this.checkState === 0) {
        		this.check();
        	} else {
        		this.uncheck();
        	}
        	updateRow(this.theId.substring(1),this.label,this.checkState,this.tree.id,this.index) ;
        };
        this.getNodeHtml = function() { 
        	var sb = new Array();

        	sb[sb.length] = '<table border="0" cellpadding="0" cellspacing="0">';
        	sb[sb.length] = '<tr>';
        	
        	for (i=0;i<this.depth;++i) {
        		sb[sb.length] = '<td class="' + this.getDepthStyle(i) + '">&nbsp;</td>';
        	}

        	sb[sb.length] = '<td';
        	sb[sb.length] = ' id="' + this.getToggleElId() + '"';
        	sb[sb.length] = ' class="' + this.getStyle() + '"';
        	if (this.hasChildren(true)) {
        		sb[sb.length] = ' onmouseover="this.className=';
        		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
        		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getHoverStyle()"';
        		sb[sb.length] = ' onmouseout="this.className=';
        		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
        		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getStyle()"';
        	}
        	sb[sb.length] = ' onclick="javascript:' + this.getCheckLink() + '">&nbsp;';
        	sb[sb.length] = '</td>';

        	// check box
        	sb[sb.length] = '<td';
        	sb[sb.length] = ' id="' + this.getCheckElId() + '"';
        	sb[sb.length] = ' class="' + this.getCheckStyle() + '"';
        	sb[sb.length] = ' onclick="javascript:' + this.getCheckLink() + '">';
        	sb[sb.length] = '&nbsp;</td>';
        	

        	sb[sb.length] = '<td>';
        	sb[sb.length] = '<a';
        	sb[sb.length] = ' id="' + this.labelElId + '"';
        	sb[sb.length] = ' class="' + this.labelStyle + '"';
        	sb[sb.length] = ' href="javascript:' + this.getCheckLink() + '"';
        	sb[sb.length] = ' target="' + this.target + '"';
        	if (this.hasChildren(true)) {
        		sb[sb.length] = ' onmouseover="document.getElementById(\'';
        		sb[sb.length] = this.getToggleElId() + '\').className=';
        		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
        		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getHoverStyle()"';
        		sb[sb.length] = ' onmouseout="document.getElementById(\'';
        		sb[sb.length] = this.getToggleElId() + '\').className=';
        		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
        		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getStyle()"';
        	}
        	sb[sb.length] = ' >';
        	sb[sb.length] = this.label;
        	sb[sb.length] = '</a>';
        	sb[sb.length] = '</td>';
        	sb[sb.length] = '</tr>';
        	sb[sb.length] = '</table>';

        	return sb.join("");

        };
    }
};

YAHOO.widget.CheckTaskNode.prototype = new YAHOO.widget.TaskNode();

YAHOO.widget.CheckTaskNode.prototype.theId = -1 ;

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
	    	//alert("add="+added) ;
	    	var added = "" ;
	    	//alert("removed="+removed) ;
	    	var list = document.getElementById('parameterCheckTbody').childNodes;
	    	for (var i = 0; i < list.length; i++) {
	    		var t = list[i] ;
	    		
	    		if (typeof t.id != 'undefined') {
	    			if (added!='') added = added+"," ; 
	    			added = added+" "+t.id.substring(3);
	    			
	    		}
            }
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
            
            if (aChecked) {
            	arrcheck[arrcheck.length>0?arrcheck.length:0]=[node.theId.substring(1),node.tree.id,node.index] ;
        		//alert(arrcheck[arrcheck.length-1][0]) ;
            	//updateRow(node.theId.substring(1),node.label,'2',node.tree.id,node.index) ;
        	}
        	
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
        function findIndex(aId) {
        	for (var i=0;i<arrcheck.length;i++) {
        		
        		if (+arrcheck[i][0]==(+aId)) return i ;
        	}
        	return null ;
        }
        var ia = null;
        
        <%
        Collection l = (Collection)request.getAttribute("params_table") ;
        Iterator it = l.iterator();
        
        for(int i=0;i<l.size();i++) {
        	WebQueryResult wqr = (WebQueryResult)it.next() ;
        	out.println("ia = findIndex("+wqr.get1()+");");
        	out.println("if (ia!=null) {updateRow('"+wqr.get1()+"','"+wqr.get2()+"','2',arrcheck[ia][1],arrcheck[ia][2]); }") ;
        }
        %>
        
    </script>
</tiles:put>

</tiles:insert>