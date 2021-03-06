<%@page import="ru.ecom.diary.web.action.protocol.template.TemplateViewAction"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ page import="ru.ecom.diary.ejb.service.protocol.tree.CheckNode"%>
<%@ page import="ru.ecom.diary.web.action.protocol.template.TemplateEditAction"%>
<%@ page import="ru.ecom.diary.ejb.form.protocol.TemplateProtocolForm" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-diary_template" defaultField="title" guid="34dc6e2d-dfa9-41a2-b8e1-3a0bdbb24d36">
      <msh:hidden property="id" guid="b13af088-2be6-4450-8c05-aaa9971111bf" />
      <msh:hidden property="saveType" guid="4f251d39-a1c1-46cc-b8a8-6c641aadad7d" />
      <msh:hidden property="medService" guid="ea9befb6-d884-4f43-b90e-f45eb4a310f4" />
      <msh:panel guid="4b44ca53-2bd6-4bdf-9e54-0ea38d8fb582">
        <msh:row>	
        	<msh:autoComplete property="type" vocName="templateProtocolType" label="Тип протокола" />
        </msh:row>
        <msh:row guid="d40cb3bf-d3e7-4544-a6ca-9db18a786f47">
          <msh:textField property="title" label="Заголовок шаблона" horizontalFill="true" guid="83e6d295-6ea7-4010-8845-c1f694f8fc2d" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="d40cb3bf-d3e7-4544-a6ca-9db18a786f47">
          <msh:checkBox property="createDiaryByDefault" label="Создавать заключения автоматически при приеме в лабораторию"/>
        </msh:row>
        <msh:row guid="fdcf0100-ab1c-4900-b7d6-cb08c77924b0">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="b3fd6145-7072-4065-accc-73fc37fb20ac" />
          <msh:textField property="date" label="Дата создания" viewOnlyField="true" guid="7162d626-b2a7-4928-ab70-adb244c07d5d" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="4a71ff37-bc99-4d73-a6a4-80c15f1c29e8" />
      </msh:panel>

    </msh:form>

    <msh:ifFormTypeIsView formName="diary_templateForm" guid="4a81e464-1352-415f-9286-596451caf264">
        <msh:section title="Список параметров у шаблона" viewRoles="/Policy/Diary/Template/Edit" listUrl="diary_templateParamsEdit.do?id=${param.id}">
            <msh:sectionContent>
            <table><tr><td>
                <div id="treeDiv1"></div>
                </td> <td style="vertical-align: top;">
                <ecom:webQuery name="list_par_temp" nativeSql="select p.parameter_id
,gr.name as grname
,par.name||' ('||case when par.type='1' then 'Числовой' 
when par.type='4' then 'Числовой с плавающей точкой зн.'||par.cntDecimal 
when par.type ='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') 
when par.type ='6' then 'Пользовательский справочник (множественный выбор): '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') 
when par.type ='7' then 'Пользовательский справочник (с текстовым полем): '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') 
when par.type='3' then 'Текстовое поле' when par.type='5' then 'Текстовое поле с ограничением'
 else 'неизвестный' end||') - '||coalesce(vmu.name,'')
,case when par.type='2' then coalesce(uv.name, '') else par.valueTextDefault end as f4_defaultValue
, coalesce(par.externalCode, '') as labCode 
from ParameterByForm p 
left join Parameter par on par.id=p.parameter_id 
left join ParameterGroup gr on gr.id=par.group_id
left join userDomain vd on vd.id=par.valueDomain_id left join vocMeasureUnit vmu on vmu.id=par.measureUnit_id
left join userValue uv on uv.domain_id=vd.id and uv.useByDefault='1'
where p.template_id=${param.id} order by p.position
                "/>
                    <msh:section title="Порядок">
   
    <msh:table name="list_par_temp" action="diary_parameterView.do" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Категория"/>
    	<msh:tableColumn property="3" columnName="Параметр"/>
    	<msh:tableColumn property="4" columnName="Значение по умолчанию"/>
    	<msh:tableColumn property="5" columnName="Код лаб. анализатора"/>
    	<msh:tableButton buttonFunction="editParameter" property="1" buttonShortName="Ред" buttonName="Редактировать параметр"/>
    </msh:table>
    </msh:section>
                </td>
            </tr></table>
            </msh:sectionContent>
        </msh:section>
        
    </msh:ifFormTypeIsView>
      <msh:ifFormTypeIsView formName="diary_templateForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:section title="Печатные формы шаблона" guid="712b744d-be86-4dc1-9d3a-0ab52eb1bed9">
        <ecom:webQuery name="protocolPrints" nativeSql="select ud.id ,ud.name,ud.fileName from userDocument ud where ud.template='${param.id}' order by ud.name" />
        <msh:table name="protocolPrints" action="entityParentView-temp_protocolPrint.do" idField="1" noDataMessage="Нет данных" guid="123c019a-f668-4454-af88-4897d27728ab">
          <msh:tableColumn property="2" columnName="Название шаблона для печати" guid="4d4c6566-75c9-4ef5-931c-723e88d4efbb" />
          <msh:tableColumn property="3" columnName="Название файла" guid="f6c2e5ba-4045-4dbe-b8fb-bb5d8386e9c4" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>   

  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction="mis_medService" />
    <msh:sideMenu title="Заключение" guid="5db0db09-9993-44cb-8477-a3fee5037b42">
      <msh:ifFormTypeIsView formName="diary_templateForm" guid="dd63e5e4-f81c-43f2-b50a-f12b1d8e026b">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-diary_template" name="Изменить" roles="/Policy/Diary/Template/Edit" guid="05503c33-989a-45dc-ab6f-8d1be735e97e" />
        <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-diary_template" name="Удалить" confirm="Удалить шаблон заключения?" guid="bc31c499-00cd-4cf8-94f0-fcdf1c9915ff" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <msh:sideMenu title="Работа с заключением">
        <msh:sideLink key="ALT+3" params="id" action="/diary_templateParamsEdit" name="Параметры" roles="/Policy/Diary/Template/Edit"/>
         <msh:sideLink key="ALT+4" action="/javascript:showParametersToPrint()" name="Показать параметры для печати" roles="/Policy/Diary/Template/View"/>
    </msh:sideMenu>
    <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Добавить">
      <msh:sideLink key="ALT+N" action="/entityParentPrepareCreate-temp_protocolPrint" params="id" name="Шаблон для печати" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Diary/Template/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
            <tags:voc_menu currentAction="medService" />
    </msh:sideMenu>
     
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_templateForm" guid="4399c99f-8801-4a73-b168-c25c23f8b0ba" />
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
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
    
        <script type="text/javascript">
        function editParameter(parId) {
        	window.location = "diary_parameterEdit.do?id="+parId;
        }
            Element.addClassName($('mainMenuRoles'), "selected");

        <%
        
        	TemplateViewAction.getParams(request);
            TemplateProtocolForm form3 = (TemplateProtocolForm) request.getAttribute("diary_templateForm");
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
                tree.expandAll() ;
            }

            var callback = null;

                         treeInit() ;
            <%
            }
            %>

        </script>
        <script type="text/javascript">
      //            $('buttonShowAddress').style.display = 'none';
      function showParametersToPrint() {
      TemplateProtocolService.getTemplateParametersList($('id').value,{
    	  callback: function (a) {
    		  if (a!=null&&a!=''){
    			  var rows = a.split("#");
    			  a='';
    			  for (var i=0;i<rows.length;i++) {
    				  a+=rows[i]+'\n';
    			  }
    			  
    			  alert (a);  
    		  } else {
    			  alert (a);
    		  }
    		  
    	  }
      });
      }
      
      </script>
    </tiles:put>

</tiles:insert>

