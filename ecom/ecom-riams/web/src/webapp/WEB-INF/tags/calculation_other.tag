<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">
<msh:sideLink name="${title}" action=" javascript:show${name}NewOtherCalculation('.do') " /> 


<style type="text/css">
    #${name}NewOtherCalculation {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}NewOtherCalculationDialog' class='dialog'>

<iframe id="iframe" src="/riams/entityParentPrepareCreate-calc_calculationsResult.do?id=${param.id}&short=ShortCreate" height="400" width="800"></iframe> 
<br>
<input value="Рассчитать" onclick="calc();" type="button">
<input value="Рассчитать и добавить к дневнику" onclick="save${name}NewCalculation();" type="button">
<input id="cancel" value="Закрыть" onclick="cancel${name}NewOtherCalculation()" type="button">
</div>



<script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
<script type="text/javascript">
var theIs${name}NewOtherCalculationDialogInitialized = false ;
var the${name}NewOtherCalculationDialog = new msh.widget.Dialog($('${name}NewOtherCalculationDialog')) ;




iframe.onload = function(){
    console.info('iframe загружен');
    var d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#calcandsave');
    d.parentNode.removeChild(d);
    d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#calculate');
    d.parentNode.removeChild(d);
    d = document.getElementById('iframe').contentDocument.documentElement.querySelector('#cancel');
    d.parentNode.removeChild(d);
}

// инициализация диалогового окна
function init${name}NewOtherCalculationDialog() {
	var iframe = document.getElementById('iframe').contentWindow;
	theIs${name}NewOtherCalculationDialogInitialized = true ;
}

// Показать
function show${name}NewOtherCalculation(create) {
   if (!theIs${name}NewOtherCalculationDialogInitialized) {
       init${name}NewOtherCalculationDialog() ;
   }
   the${name}NewOtherCalculationDialog.show() ;
   
   //
  /* if(create==0)
   	document.getElementById("cancel").style.display = "none";
   
   //if(create!=0)
   	document.getElementById("cancel").style.display ="";*/

}

// Отмена
function cancel${name}NewOtherCalculation() {
    the${name}NewOtherCalculationDialog.hide() ;
    
}
//сохран
function save${name}NewCalculation() {
	
	calc();  //var tempo = document.querySelector('#calculatorName');
	//alert('${param.id}')
	var result2 = document.getElementById('iframe').contentDocument.documentElement.querySelector('#calculatorName').value;
	//alert(result2);
	var result = document.getElementById('iframe').contentDocument.documentElement.querySelector('#result').value;
	
	if(result!=""){
	var prop ;
    if ("${property}"=="") {
        prop = "record" ;
    } else { prop = "${property}" ;}
    $(prop).value +=  result2+":"+result;
	the${name}NewOtherCalculationDialog.hide();
	}else alert("Заполните поля!")
}

function calc(){
	document.getElementById('iframe').contentWindow.calculating();
}

</script>
</msh:ifInRole>