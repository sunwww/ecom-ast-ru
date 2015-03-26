<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>

<style type="text/css">
    #${name}263Dialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}263Dialog' class='dialog'>
    <h2 id='${name}Title'></h2>
    <div class='rootPane' id="${name}RootDiv"></div>
</div>

<script type="text/javascript"><!--
     var theIs${name}263DialogInitialized = false ;
     var the${name}263Dialog = new msh.widget.Dialog($('${name}263Dialog')) ;
     // Показать 
     function show${name}263napr(aSls,aPreDateHosp,aLastname,aFirstname,aMiddlename,aBirthday,aMode) {
    	 $('${name}Title').innerHTML = "ВЫБОР НАПРАВЛЕНИЯ ДЛЯ ГОСПИТАЛИЗАЦИИ от "+aPreDateHosp+" на "+aLastname+" "+aFirstname+" "+aMiddlename+" "+aBirthday ;
    	 $('${name}RootDiv').innerHTML ="<i>Загрузка данных...</i>" ;
    	 the${name}263Dialog.show() ;
    	 HospitalMedCaseService.viewTable263narp(aSls,aPreDateHosp,aLastname,aFirstname,aMiddlename,aBirthday,aMode,{
 			callback: function(aResult) {
 				var change ="\"show${name}263napr('"+aSls+"','"+aPreDateHosp+"','"+aLastname+"','"+aFirstname+"','"+aMiddlename+"','"+aBirthday+"',this.value)\"";
 		    	 $('${name}RootDiv').innerHTML ="<input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}263()\">"
 					+"<form name='frm${name}NaprView' id='frm${name}NaprView' action='javascript:void(0)'><table><tr>"
 		    		 +'<td class="label" title="Список  (typeView1)" colspan="1"><label for="typeView1Name" id="typeView1Label">Совпадание:</label></td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +' <input name="type${name}NaprView" value="1" type="radio" onchange='+change+'>  ФИО+ДР' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="2" type="radio" onchange='+change+'>  Фамилия' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="3" type="radio" onchange='+change+'>  Имя' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="4" type="radio" onchange='+change+'>  Пред.дата госп.' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="5" type="radio" onchange='+change+'>  Все' 
 		    		 +'</td>'
 		    		 +'</tr></table></form>'
 				+aResult+"<input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}263()\">" ;
 		    	try{document.forms['frm${name}NaprView'].type${name}NaprView[aMode-1].checked='checked' ;}catch(e){}
 	    	 	the${name}263Dialog.show() ;
 	    	 }
 		}) ;
    	 
     }
     function show${name}263sls(aSls,aPreDateHosp,aLastname,aFirstname,aMiddlename
    		 	,aBirthday,aMode,aDenied) {
    	 $('${name}Title').innerHTML = "ВЫБОР ГОСПИТАЛИЗАЦИИ ДЛЯ НАПРАВЛЕНИЯ от "+aPreDateHosp+" на "+aLastname+" "+aFirstname+" "+aMiddlename+" "+aBirthday ;
    	 $('${name}RootDiv').innerHTML ="<i>Загрузка данных...</i>" ;
    	 the${name}263Dialog.show() ;
    	 HospitalMedCaseService.viewTable263sls(aSls,aPreDateHosp,aLastname,aFirstname,aMiddlename,aBirthday,aMode,aDenied,{
 			callback: function(aResult) {
 				var change ="\"show${name}263sls('"+aSls+"','"+aPreDateHosp+"','"+aLastname+"','"+aFirstname+"','"+aMiddlename+"','"+aBirthday+"',this.value,'"+aDenied+"')\"";
 				var changeD ="\"show${name}263sls('"+aSls+"','"+aPreDateHosp+"','"+aLastname+"','"+aFirstname+"','"+aMiddlename+"','"+aBirthday+"','"+aMode+"',this.value)\"";
 		    	 $('${name}RootDiv').innerHTML ="<input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}263()\">"
 		    	    +"<input type=\"button\" value=\"Оформить отказ\" onclick=\"show${name}263denied("+aSls+",0)\">"
 					+"<form name='frm${name}NaprView' id='frm${name}NaprView' action='javascript:void(0)'><table><tr>"
 		    		 +'<td class="label" title="Список  (typeView1)" colspan="1"><label for="typeView1Name" id="typeView1Label">Совпадание:</label></td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +' <input name="type${name}NaprView" value="1" type="radio" onchange='+change+'>  ФИО+ДР' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="2" type="radio" onchange='+change+'>  Фамилия' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="3" type="radio" onchange='+change+'>  Имя' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="4" type="radio" onchange='+change+'>  Пред.дата госп.' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}NaprView" value="5" type="radio" onchange='+change+'>  Все' 
 		    		 +'</td>'
 		    		 
 		    		 +'</tr><tr>'
 		    		 +'<td class="label" title="Отказы  (typeView1)" colspan="1"><label for="typeView1Name" id="typeView1Label">Тип:</label></td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +' <input name="type${name}DeniedView" value="1" type="radio" onchange='+changeD+'> гопитализации' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}DeniedView" value="2" type="radio" onchange='+changeD+'>  отказы' 
 		    		 +'</td>'
 		    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
 		    		 +'	<input name="type${name}DeniedView" value="3" type="radio" onchange='+changeD+'>  все' 
 		    		 +'</td>'

 		    		 +'</tr></table></form>'

 		    		 +aResult+"<input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}263()\">" 
  		    	    //+"<input type=\"button\" value=\"Оформить отказ\" onclick=\"show${name}263denied("+aSls+",0)\">"
  		    	    show${name}263denied(aSls,0,1) ;
					;
 		    	try{document.forms['frm${name}NaprView'].type${name}NaprView[aMode-1].checked='checked' ;}catch(e){}
 		    	try{document.forms['frm${name}NaprView'].type${name}DeniedView[aDenied-1].checked='checked' ;}catch(e){}
 	    	 	the${name}263Dialog.show() ;
 			}
 			});
     }
     function show${name}263denied(aHDFid,aDenied,aNext) {
    	 var change ="\"setDeniedByHDF('"+aHDFid+"','frm${name}DeniedQ','type${name}Denied');cancel${name}263()\"";
    	 if (aNext) {
    		 $('${name}RootDiv').innerHTML = $('${name}RootDiv').innerHTML+"<br/>" ;
    	 } else {
    		 $('${name}RootDiv').innerHTML = "" ;
    		 $('${name}Title').innerHTML = "ВЫБОР ПРИЧИНЫ ОТКАЗА" ;
    	 }
    	 $('${name}RootDiv').innerHTML = $('${name}RootDiv').innerHTML+"<form name='frm${name}DeniedQ' id='frm${name}DeniedQ' action='javascript:void(0)'><table><tr>"
    		 +'<td class="label" title="Список  (typeView1)" colspan="1"><label for="typeView1Name" id="typeView1Label">Список отказов:</label></td>'
    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
    		 +' <input name="type${name}Denied" value="1" type="radio" onchange='+change+'>  Неявка пациента на госпитализацию' 
    		 +'</td>'
    		 +'</tr>'
    		 +'<tr>'
    		 +'<td></td>'
    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
    		 +'	<input name="type${name}Denied" value="2" type="radio" onchange='+change+'>  Непредоставление необходимого пакета документов' 
    		 +'</td>'
    		 +'</tr>'
    		 +'<tr>'
    		 +'<td></td>'
    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
    		 +'	<input name="type${name}Denied" value="3" type="radio" onchange='+change+'>  Инициативный отказ от госпитализации пациентом' 
    		 +'</td>'
    		 +'</tr>'
    		 +'<tr>'
    		 +'<td></td>'
    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="4">'
    		 +'	<input name="type${name}Denied" value="4" type="radio" onchange='+change+'>  Смерть' 
    		 +'</td>'
    		 +'</tr>'
    		 +'<tr>'
    		 +'<td></td>'
    		 +'<td onclick="this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()" colspan="8">'
    		 +'	<input name="type${name}Denied" value="5" type="radio" onchange='+change+'>  Прочие' 
    		 +'</td>'
    		 +'</tr></table></form>'
    		 //+"<input type=\"button\" value=\"ОК\" onclick="+change+">" 
    		 +"<input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}263()\">" ;
    	 	try{document.forms['frm${name}DeniedQ'].type${name}Denied[aDenied-1].checked='checked' ;}catch(e){}
    	 	
    	 the${name}263Dialog.hide() ;
    	 	the${name}263Dialog.show() ;
     }
     
     // Отмена 
     function cancel${name}263() {
    	the${name}263Dialog.hide() ;
    	msh.effect.FadeEffect.pushFadeAll();
     }
     
	
     
</script>