<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

<!-- Upper -->
<tiles:put name="title" type="string">
<ecom:titleTrail mainMenu="Patient" beginForm="calc_calculationsResultForm"/>
</tiles:put>


	<!-- Sider -->
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="calc_calculationsResultForm">
			<msh:sideMenu title="Управление">
				<msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id"
					action="/entityEdit-calc_calculationsResult" name="Изменить"
					roles="/Policy/Mis/Calc/Calculation/Edit" />
				<msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?"
					params="id"
					action="/entityParentDeleteGoParentView-calc_calculationsResult"
					name="Удалить" roles="/Policy/Mis/Calc/Calculation/Edit" />
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>

	<!-- Styler -->
	<tiles:put name="style" type="string">
		<style>
			.calc {
				display: inline-block;
				padding: 3px;
				border: 1px solid rgba(255, 0, 0, 0);
				transition: 3s;
				border-color: rgb(59, 92, 105);
				position: relative;
				padding-top: 0.4em;
				min-height: 0em;
			}
		</style>
	</tiles:put>
	
	<!-- Body -->
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-calc_calculationsResult.do"
			defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="departmentMedCase" />
			<msh:hidden property="calculator" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete vocName="vocCalculator" property="calculator" label="Выбор калькулятора" fieldColSpan="4" size="30" />
				</msh:row>
			</msh:panel>
<msh:ifFormTypeIsView formName="calc_calculationsResultForm">
<input  hidden id="checker" class="check" size="60" type="text" value="1">
		<msh:row>					
			<msh:textField property="result"/>
		</msh:row>
</msh:ifFormTypeIsView>
			
			<msh:ifFormTypeIsNotView formName="calc_calculationsResultForm">
			<input  hidden id="checker" class="check" size="60" type="text" value="2">
			<msh:ifFormTypeAreViewOrEdit formName="calc_calculationsResultForm">
			<msh:row>					
			<msh:hidden property="result"/>
			</msh:row>
			</msh:ifFormTypeAreViewOrEdit>
			
			<br>
			<div class="calc">
				<table>
					<tr>
						<td style="padding-left: 20px; padding-top: 20px;">
							<div id="calculator" class="calc">Выберите калькулятор!</div>
						</td>
					</tr>
					<tr>
						<td style="padding-left: 20px; padding-top: 20px;"> 
						<input id="cancel" type="button" value="Отмена" onclick="goBack()"/> 
						<input disabled id="calculate" type="button" value="Рассчитать" onclick="calculating()"/> 
						<input disabled id="calcandsave" type="button" value="Рассчитать и сохранить" onclick="CreateRes()" />
						<td>
					</tr>
				</table>
			</div>
			</msh:ifFormTypeIsNotView>
		</msh:form>
	</tiles:put>
	<!-- Scripts -->
<tiles:put name="javascript" type="string">
<script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
<script type="text/javascript">
var DepartmentId= $('departmentMedCase');
var aId= $('id');
var calculator = document.querySelector('#calculator'); //id калькулятора.
var div = document.querySelector('#calculator.calc'); 
var global=0;
var result;
var resultofcalc;


	function GetAndParseJson() {
		CalculateService.GetSettingsById(calculator.value, {
			callback : function(aResult) {

				result = JSON.parse(aResult);
				
				var t= document.querySelector('#checker.check');
				if(t.value>"1"){createObjects();}
			}
		});}
	
	
	GetAndParseJson();
	
	//Cоздание элементов на основе json
	function createObjects() {

		var size = result.length;
		var table = document.createElement('table');

		document.querySelector('#calculate').disabled = false;
		document.querySelector('#calcandsave').disabled = false;

		var tempo = document.querySelector('#calculatorName');

		div.removeChild(div.firstChild);
		global = 0;
		div.innerHTML = "Калькулятор: <b>" + tempo.value + "</b>";
		div.appendChild(table);
		var formul = "";

		for ( var i = 0; i < size; i++) {
			var tr = document.createElement('tr');
			tr.id = "id" + global;
			table.appendChild(tr);
			if (result[i].Type_id == 1) {

				if (result[i].Value == "@gender") {
					getGender(global);
					tr.innerHTML += "<td class=\"label'\"><label id=\"gen"+global+"\" class=\"txtbox\" >"
							+ result[i].Comment + ":</label></td>";
					tr.innerHTML += "<td><input disabled id=\"id"+global+"\" class=\"txtbox\" size=\"60\" type=\"text\"></td>";
					global++;
				}

				if (result[i].Value == "@age") {
					getAge(global);
					tr.innerHTML += "<td class=\"label'\"><label>"
							+ result[i].Comment + ":</label></td>";
					tr.innerHTML += "<td><input disabled id=\"id"+global+"\" class=\"txtbox\" size=\"60\" type=\"text\"></td>";
					global++;
				}

				if (result[i].Value != "@age" && (result[i].Value != "@gender")) {
					tr.innerHTML += "<td class=\"label'\"><label>"
							+ result[i].Comment + ":</label></td>";
					tr.innerHTML += "<td><input id=\"id"+global+"\" class=\"txtbox\" size=\"60\" type=\"text\"></td>";
					global++;
				}
			}

			if (result[i].Type_id == 2) {
				tr.innerHTML += "<td class=\"label'\"><label hidden>-</label></td>";
				tr.innerHTML += "<td><input hidden disabled id=\"id"+global+"\" class=\"txtbox\" size=\"60\" type=\"text\" value=\""+result[i].Value+"\"></td>";
				global++;
			}
		}
		
		var tr2 = document.createElement('tr');
		tr2.id = "res";
		table.appendChild(tr2);
		///////////////////---------------
		
					
				
		tr2.innerHTML += "<td class=\"label'\"><label>Результат:</label></td>";
		tr2.innerHTML += "<td><input disabled id=\"result\" class=\"result\" size=\"60\" type=\"text\"></td>";
		
		
		var invisibleResult = document.querySelector('#result');
		var visibleResult = document.querySelector('#result.result');
		visibleResult.value = invisibleResult.value;
	}
	
	//Эвент для выадающего списка. При активации элемента получаем JSON
	calculatorAutocomplete.addOnChangeCallback(function() {
		GetAndParseJson();
	});

	//Вычисление
	function calculating() {
		var T = "";
		for ( var i = 0; i < global; i++) {
			var inputbox = document.querySelector('#id' + i + '.txtbox');
			T += "" + inputbox.value;
		}

		var res = document.querySelector('#result.result');
		res.value = eval(T);
		resultofcalc = eval(T);
	}



	//Получение пола по id случая
	function getGender(glob) {

		CalculateService.getGender(DepartmentId.value, {
			callback : function(aResult) {

				var genLab = document.querySelector('#gen' + glob + ".txtbox");
				var gentxt = document.querySelector('#id' + glob + ".txtbox");

				if (aResult == 2) {
					genLab.innerHTML = "Пол мужской"
					gentxt.value += "1.23";
				}

				if (aResult == 1) {
					genLab.innerHTML = "Пол женский"
					gentxt.value += "1.04";
				}
			}
		});
	}

	//получение возраста(полных лет) по id случая
	function getAge(glob) {
		CalculateService.getAge(DepartmentId.value, {
			callback : function(aResult) {
				var age = document.querySelector('#id' + glob + ".txtbox");
				age.value = aResult;

			}
		});
	}

	//Создание результата
	function CreateRes() {
		calculating();
		var res = document.querySelector('#result.result');
		if (!isEmpty()) {
			CalculateService.SetCalculateResultCreate(DepartmentId.value,
					res.value, calculator.value, {
						callback : function(aResult) {
							//alert("Сохранено!");
							goBack();
						}
					});
		}
	}

	//проверка полей на пустоту
	function isEmpty() {
		for ( var i = 0; i < global; i++) {
			var inputbox = document.querySelector('#id' + i + '.txtbox');
			if (inputbox.value == "") {
				alert("Заполнены не все поля!");
				return true;
			}
		}
		return false;
	}
	
	 // возврат на родительскую страницу.
	function goBack() {
		location.href = "entityParentView-stac_slo.do?id=" + DepartmentId.value;
	}
</script>
 </tiles:put>
</tiles:insert>

