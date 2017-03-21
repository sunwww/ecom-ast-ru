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
      
      	<!-- <msh:sideLink confirm="Точно хотите удалть?" name="Удалить" action=".javascript:edit()" roles="/Policy/Mis/Calc/Calculation/Edit"/>
    	<msh:sideLink name="Изменить" action=".javascript:delete()" roles="/Policy/Mis/Calc/Calculation/Edit"/>
    	-->
    	<msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-calc_calculationsResult" name="Изменить" roles="/Policy/Mis/Calc/Calculation/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-calc_calculationsResult" name="Удалить" roles="/Policy/Mis/Calc/Calculation/Edit" />
    	</msh:sideMenu>
</msh:ifFormTypeAreViewOrEdit>
</tiles:put>
   
  
<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-calc_calculationsResult.do" defaultField="name">
		
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
			<msh:hidden property="departmentMedCase" />
			<msh:hidden property="calculator"/>

   <b>  Расчет клубочковой фильтрации:</b>
   <div class="formula"> </div>
			<msh:panel>
				<br>
				<tr>
				<td colspan="1" class="label"><label>Пол:</label></td>
				<td colspan="2">
<label><input disabled name="sex" id="sex2" value="2" type="radio">муж </label>
<label><input disabled name="sex" id="sex1" value="1" type="radio">жен</label>
</td>
</tr>
				<tr>
					<td colspan="1" class="label"><label>Возраст:</label></td>
					<td colspan="3"><input disabled id="age" size="10" type="text"></td>
				</tr>
				
				<msh:ifFormTypeIsNotView formName="calc_calculationsResultForm"> 
				<tr>
					<td colspan="1" class="label"><label>Креатинин(с плав зап):</label></td>
					<td colspan="3"><input id="createnin" size="10" type="text"></td>
				</tr>

				<tr>
					<td colspan="1" class="label"><label>Масса тела(кг):</label></td>
					<td colspan="3"><input id="mass" size="10" type="text"></td>
				</tr>
				</msh:ifFormTypeIsNotView>
				<msh:row>
					<msh:textField viewOnlyField="true" property="result" label="Результат"
						horizontalFill="true" fieldColSpan="3" />
				</msh:row>
				
			</msh:panel>
			
<msh:ifFormTypeIsNotView formName="calc_calculationsResultForm"> 
<tr> 
<td> 
<input value="Отмена" onclick="goBack();" type="button">
</td>
<td> 
<input value="Рассчитать" onclick="calculate();" type="button">
</td>
<msh:ifFormTypeAreViewOrEdit formName="calc_calculationsResultForm"> 
<td> 
<input value="Рассчитать и сохранить" onclick="EditRes();" type="button">
</td>
</msh:ifFormTypeAreViewOrEdit> 
<msh:ifFormTypeIsCreate formName="calc_calculationsResultForm"> 
<td> 
<input value="Рассчитать и создать" onclick="saveRes();" type="button">
</td>
</msh:ifFormTypeIsCreate>
</tr>
</msh:ifFormTypeIsNotView>


</msh:form>
</tiles:put>






<!-- Scripts -->
<tiles:put name="javascript" type="string">
<script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
<script type="text/javascript">

 
var DepartmentId= $('departmentMedCase');
var aId= $('id');
var result = document.querySelector('#resultReadOnly');
var createnin = document.querySelector('#createnin');
var mass = document.querySelector('#mass');
var formula = document.querySelector('.formula');
var empty=true;
var ages="";
var genders="";

getGenderAndAge();


function EditRes() {
	 calculate();
	 if(!checkEmpty()){
	 CalculateService.SetCalculateResultEdit(aId.value, result.value, {
			callback : function(aResult) {
				//alert("Сохранено!");
				goBack();
				}
	 });
	 }}
	 
 function saveRes() {
	 calculate();
	 if(!checkEmpty()){
	 CalculateService.SetCalculateResultCreate(DepartmentId.value, result.value, {
			callback : function(aResult) {
				//alert("Сохранено!");
				goBack();
				}
	 });
	 }}
	
function getGenderAndAge() {
	CalculateService.GetGenderAndAge(DepartmentId.value, {
		callback : function(aResult) {
		
   var age = document.querySelector('#age');
   var radio1 = document.querySelector('#sex1');
   var radio2 = document.querySelector('#sex2');
			
			var i = 0, p = 0;
			for (i; i < aResult.length; i++) {
				if (aResult[i] == "|") {
					p = 1;
					i++;
				}
				if (p == 0) {
					ages += aResult[i];
				}
				if (p == 1) {
					genders += aResult[i];
				}
			}
			age.value = ages;
			if(genders==1) radio1.checked=true;  //жен
			if(genders==2) radio2.checked=true; //муж
		}});}

		
		
		function calculate() {

			createnin.value = createnin.value.replace(".",",");
			result.className = "horizontalFill";
			
			if(!checkEmpty()){
			if (genders == "2") {
				result.value = ((1.23 * (140 - ages) * mass.value) / createnin.value);
				formula.innerHTML = ' по формуле: <b>1,23 х (140 - возраст) х масса / креатинин</b>';
			}
			if (genders == "1") {//1
				result.value = ((1.04 * (140 - ages) * mass.value) / createnin.value);
				formula.innerHTML = ' по формуле: <b>1,04 х (140 - возраст) х масса / креатинин</b>';
			}
			}
			else {formula.innerHTML = '  <font color="red"><b>Заполните все поля!</b></font>';}
			result.className = "viewOnly horizontalFill";
		}
		
		
		 function goBack() // возврат на родительскую страницу.
		 {
			 location.href= "entityParentView-stac_slo.do?id="+DepartmentId.value;
		 }
		 
		 function checkEmpty() //проверка полей на пустоту
		 {
			 if(createnin.value =="" || mass.value=="") return true;
				else return false;
		 }
		
	</script>
 </tiles:put>
</tiles:insert>

