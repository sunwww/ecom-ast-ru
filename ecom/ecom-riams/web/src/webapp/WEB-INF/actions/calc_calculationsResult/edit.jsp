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
	<%
		String shortS = request.getParameter("short") ;
		if (shortS==null) {

	%>
<tiles:put name="title" type="string">
<ecom:titleTrail mainMenu="Patient" beginForm="calc_calculationsResultForm"/>
</tiles:put>
	<% } %>

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
	<% 
		String shorts = (String)request.getParameter("short");
		System.out.println(shorts);%>
		<msh:form action="/entitySaveGoView-calc_calculationsResult.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="departmentMedCase" />
			<msh:hidden property="calculator" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete vocName="vocCalculator" property="calculator" label="Выбор калькулятора" fieldColSpan="4" size="90" />
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
						<input id="calculate" type="button" value="Рассчитать" onclick="calculating()"/>
						<input id="calcandtodiary" type="button" value="Рассчитать и сохранить" onclick="CreateRes(2)" />
						<td>
					</tr>

				</table>
			</div>
			
			</msh:ifFormTypeIsNotView>
			<tags:calculation name="calculation" roles="/Policy/Mis/Calc/Calculation/Create" field="record" title=""/>
			<tags:calculation_grace name="calculation_grace" roles="/Policy/Mis/Calc/Calculation/Create" field="record" title=""/>
            <tags:calculation_caprini name="calculation_caprini" roles="/Policy/Mis/Calc/Calculation/Create" field="record" title=""/>
			<tags:calculation_imt name="calculation_imt" roles="/Policy/Mis/Calc/Calculation/Create" field="record" title=""/>
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

		calculat = document.querySelector('#calculate');
		
		if(calculat!=null){
		calculat.disabled = false;
		}
		

		var tempo = document.querySelector('#calculatorName');

		div.removeChild(div.firstChild);
		global = 0;
		div.innerHTML = "Калькулятор: <b>" + tempo.value + "</b>";
		div.appendChild(table);
		var formul = "";

		var ifNeedNode = false;  //есть ли примечание
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
			
			if (result[i].Type_id == 3){
                if (result[i].Note =='') {
                    tr.innerHTML += "<td class=\"label'\"><label>" + result[i].Comment + ":</label></td>";
                    tr.innerHTML += "<td><input type=\"checkbox\"/ id=\"id" + global + "\" class=\"Ctxtbox\" value='" + result[i].Value + "'></td>";
                }
				else if (result[i].Note !='') {
                    tr.style="border-bottom: 1px solid black;border-top: 1px solid black";
                    tr.innerHTML += "<td width=\"20%\" class=\"label'\"><label>" + result[i].Comment + ":</label></td>";
                    tr.innerHTML += "<td width=\"5%\"><input type=\"checkbox\"/ id=\"id" + global + "\" class=\"Ctxtbox\" value='" + result[i].Value + "'></td>";
                    tr.innerHTML +="<td width=\"75%\" class=\"label'\"><label>"+ result[i].Note + "</label></td>";
                    ifNeedNode=true;
                }
				global++;
			}
		}
		
		var tr2 = document.createElement('tr');
		tr2.id = "res";
		table.appendChild(tr2);
		///////////////////---------------
		
					
				
		tr2.innerHTML += "<td class=\"label'\"><label>Результат:</label></td>";
        if (ifNeedNode) tr2.innerHTML += "<td class=\"label'\"><label></label></td>";
		tr2.innerHTML += "<td><input disabled id=\"result\" class=\"result\" size=\"160\" type=\"text\"></td>";

		
		var invisibleResult = document.querySelector('#result');
		var visibleResult = document.querySelector('#result.result');
		visibleResult.value = invisibleResult.value;
	}
	
	//Эвент для выадающего списка. При активации элемента получаем JSON
	calculatorAutocomplete.addOnChangeCallback(function() {
	    CalculateService.getCalcTagView(calculator.value, {
        callback : function(aResult) {
			if (aResult=='') GetAndParseJson();
            //если есть вьюшка, вывести её, имена должны совпадать
			else if (aResult=='calculation')
                    showcalculationNewCalculation($('departmentMedCase').value, 0);
                else if (aResult=='calculation_grace')
                    showcalculation_graceNewCalculation($('departmentMedCase').value,calculator.value, 0);
            else if (aResult=='calculation_caprini')
                showcalculation_capriniNewCalculation($('departmentMedCase').value,calculator.value, 0);
            else if (aResult=='calculation_imt')
                showcalculation_imtNewCalculation($('departmentMedCase').value, 0);
        }
    	});
	});

	//Вычисление
	function calculating(save) {
	    var formStringCheckBox='Параметр\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tБаллы\n';
		var T = "";
		for ( var i = 0; i < global; i++) {
			
			if(document.querySelector('#id' + i + '.Ctxtbox')==null)
				{
			var inputbox = document.querySelector('#id' + i + '.txtbox');
			T += "" + inputbox.value;
			
				}else{
					chkbox = document.querySelector('#id' + i + '.Ctxtbox');
					if (document.querySelector('#id' + i + '.Ctxtbox').parentNode!=null &&
						document.querySelector('#id' + i + '.Ctxtbox').parentNode.parentNode!=null &&
                        document.querySelector('#id' + i + '.Ctxtbox').parentNode.parentNode.childNodes.length>0)
                	formStringCheckBox+=document.querySelector('#id' + i + '.Ctxtbox').parentNode.parentNode.childNodes[0].innerText;
					if(chkbox.checked) {
						T += "+" + chkbox.value;
                        formStringCheckBox+="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t("+chkbox.value+")";
					}
					else formStringCheckBox+="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(0)";
                formStringCheckBox+="\n";
			}
		}

		var res = document.querySelector('#result.result');
		res.value = eval(T);
		resultofcalc = eval(T);
		//Добавить интерпретацию результата, если есть
		//Сохраняется в текущий дневник, если есть. И отдельным дневником вместе с самой формой - всегда
        if (!isEmpty()) {
            CalculateService.getInterpretation(calculator.value, res.value, {
                callback: function (aResult) {
                    if (aResult != '') {
                        if (res.value.indexOf(aResult) == -1) {
                            res.value = "Результат " + res.value + " " + aResult;
                            if (save != null) {
                                CalculateService.SetCalculateResultCreate(DepartmentId.value,
                                    res.value, calculator.value, formStringCheckBox, {
                                        callback: function () {
                                            if (window.parent.document.getElementById('record') != null) {
                                                window.parent.document.getElementById('record').value += res.value + "\n";
                                            }
                                            goBack();
                                        }
                                    });
                            }
                        }
                    }
                    else {
                        res.value = "Результат " + res.value;
                        if (save != null) {
                            CalculateService.SetCalculateResultCreate(DepartmentId.value,
                                res.value, calculator.value, formStringCheckBox,{
                                    callback: function () {
                                        if (window.parent.document.getElementById('record') != null) {
                                            window.parent.document.getElementById('record').value += res.value + "\n";
                                        }
                                        goBack();
                                    }
                                });
                        }
                    }
                }
            });
        }
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
	function CreateRes(way) {
        calculating(way);
    }
	//проверка полей на пустоту
	function isEmpty() {
		for ( var i = 0; i < global; i++) {
			var inputbox = document.querySelector('#id' + i + '.txtbox');
			if (typeof inputbox !== 'undefined' && inputbox !=null) {
                if (inputbox.value == "") {
                    alert("Заполнены не все поля! Заполните необходимые значения.");
                    return true;
                }
            }
            else {
				if (document.getElementById("result").value=='undefined') {
				    alert("Невозможно вычислить результат, т.к. не введены необходимые значения.");
				    return true;
				}
			}
		}
		return false;
	}
	
	 // возврат на родительскую страницу.
	function goBack() {
	    for (var i=0; i<100; i++) {
            if (window.parent.document.getElementById('allCalc')!=null) window.parent.document.getElementById('allCalc').hide();
            if (window.parent.document.getElementById('fadeEffect')!=null) window.parent.document.getElementById('fadeEffect').hide();
		}
		location.href = "entityParentView-stac_slo.do?id=" + DepartmentId.value;
	}
	 
	
	 function DelFromForm()
	 {
		 var d = document.querySelector('#calculate');
		    d.parentNode.removeChild(d);
		    d = document.querySelector('#cancel');
		    d.parentNode.removeChild(d);
	 }
</script>
 </tiles:put>
</tiles:insert>

