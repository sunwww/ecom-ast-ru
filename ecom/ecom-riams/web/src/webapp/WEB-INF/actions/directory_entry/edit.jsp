<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp"
	flush="true">
		
		<tiles:put name="style" type="string">
	 <style>
           .border {
               display: inline-block;
               padding: 3px;
               border: 1px solid rgba(255,0,0,0);
               transition: 3s;
               border-color: rgb(59,92,105) ;
               position: relative;
			   padding-top: 0.4em;
			   min-height: 0em;
            }
            
            
   .colortext {
    background-color: red; /* Цвет фона */
    color: #ffe; /* Цвет текста */
    width: 97%;
   }
   
        </style>
	</tiles:put>
	
		<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="directory_entryForm">
			<msh:sideMenu title="Управление">
			<msh:sideLink key="ALT+2" params="id"
					action="/entityPrepareCreate-directory_entry" name="Добавить"
					roles="/Policy/Mis/Directory/Department" />
				<msh:sideLink key="ALT+DEL" confirm="Удалить?"
					params="id"
					action="/entityParentDeleteGoParentView-calc_calculationsResult"
					name="Удалить" roles="/Policy/Mis/Directory/Department" />
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	
	<tiles:put name="body" type="string">
	<msh:form action="/entitySaveGoView-directory_entry.do"
			defaultField="name">
	<msh:panel>
		<msh:row>
		<msh:autoComplete vocName="vocDepartmentFull"
			property="department" label="Отделение" fieldColSpan="4" size="60" />
		</msh:row>
		<msh:row>
		<msh:autoComplete vocName="vocPersonShort" property="person"
			label="Персона" fieldColSpan="4" size="60" />
		</msh:row>
		<msh:row>
		<msh:textField property="insideNumber" label="Внутренний номер"
			horizontalFill="true" fieldColSpan="3" />
		</msh:row>
		<msh:row>
		<msh:textField property="comment" label="Комментарий"
			horizontalFill="true" fieldColSpan="3" />
		</msh:row>
		
	</msh:panel>

	<div id="copyFrom">
	<msh:panel>
		<msh:row>
		<msh:autoComplete vocName="vocTypeNumber"
			property="typeNumber" label="Тип номера" fieldColSpan="4" size="60" />
		</msh:row>
		<msh:row>
		<msh:textField property="number" label="Номер"
			horizontalFill="false" fieldColSpan="3" size="50" />
		</msh:row>
		</msh:panel>
		</div>
	</msh:form>
		<msh:row>
	 <button type="button"  onclick="Add()">Добавить</button> 
	 <button type="button"  onclick="Create()">Сохранить</button>
		</msh:row>
	<div id="copyTo">
	
	</div>
	</tiles:put>
	
	<tiles:put name="javascript" type="string">
	 <script type="text/javascript" src="./dwr/interface/DirectoryService.js"></script>
	<script type="text/javascript">
	var global=1;
	
	
	function delthis(id) {
		var intIdArr = id.split('id');
		var intId = parseInt(intIdArr[1]);
		var del = document.querySelector('#' + id);
		del.remove();
		global--;
	}
	function Add(){
		
		var clonedNode = document.querySelector("#copyFrom").cloneNode(true); // где копируем
		var content = document.querySelector('#copyTo');
		var span = document.createElement('span');
			span.className = "border";
			span.id = "id" + global;
			content.appendChild(span);
			span.appendChild(clonedNode);
		 
			var butn = document.createElement('div');
			butn.innerHTML = '<button type="button"  class="btn0" id="id'
					+ global
					+ '" onclick="delthis(this.id)">Удалить</button>';
			span.appendChild(butn);
			global++;
 
	}

	function Create() {

		var extJSON="{";
		 var department = document.querySelector('#department').value;
		 var person = document.querySelector('#person').value;
		 var insideNumber = document.querySelector('#insideNumber').value;
		 var comment = document.querySelector('#comment').value;

		 extJSON += "\"department\":\""+department+"\",\"person\":\""+person+"\"";
		extJSON += ",\"insideNumber\":\""+insideNumber+"\",\"comment\":\""+comment+"\"";

	
		if (global >= 2) {
				var span = document.querySelector('#id1.border').querySelector(
						'#copyFrom');
				var i = 1;
				var numbers = new Array();
				for (i; i < global; i++) {
					var span = document.querySelector('#id' + i + '.border')
							.querySelector('#copyFrom');
					numbers[i] = {
						typeNumber : span.querySelector('#typeNumber').value,
						number : span.querySelector('#number').value,
					};
				}
				extJSON += ",\"numbers\":" + JSON.stringify(numbers);
			} else
				extJSON += ",\"numbers\":[]";

			extJSON += "}";

			DirectoryService.setEntryAndNumber(JSON, {
				callback : function(aResult) {
				}
			});
		}

		function  save() {
            DirectoryService.setEntryAndNumber(extJSON, {
                callback : function(aResult) {
                }
            });
        }
	</script>
	</tiles:put>
	</tiles:insert>