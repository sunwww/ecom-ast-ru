<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">${infoRecord} Шаг 1. Ввод данных пациента</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>

	   <table style="width: 100%">
	   <tr>
	   <td onclick="rowDefault('lastname');" id="lastnameLabel" name="lastnameLabel" class="label selected"><b>Фамилия:</b></td>
	   <td onclick="rowDefault('lastname');"><input value="${param.lastname}" type="text" name="lastname" id="lastname" style="width: 100%"/></td>
	   <td rowspan="3">	    
	   <div class="divRecord button fb" onclick="goNextStep()">
	    	<p class="label">Далее</p>
	    </div>    
		</td>
	   </tr>
	   <tr onclick="rowDefault('firstname');">
	   <td id="firstnameLabel" name="firstnameLabel" class="label"><b>Имя:</b></td>
	   <td><input type="text" value="${param.firstname}" name="firstname" id="firstname" style="width: 100%"/></td>
	   </tr>
	   <tr onclick="rowDefault('middlename');">
	   <td id="middlenameLabel" name="middlenameLabel" class="label"><b>Отчество:</b></td>
	   <td><input type="text" value="${param.middlename}" name="middlename" id="middlename" style="width: 100%"/></td>
	   </tr>
	   </table>
<div>
	       		<table  style="margin: 20px;padding:20px;font-size: 1em">
	       		<tr>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Й</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ц</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>У</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>К</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Е</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Н</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Г</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ш</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Щ</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>З</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Х</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ъ</td>
	       		</tr>
	       		<tr>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ф</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ы</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>В</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>А</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>П</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Р</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>О</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Л</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Д</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ж</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Э</td>
	       		<td class='kbButton'> </td>
	       		</tr>
	       		<tr>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Я</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ч</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>С</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>М</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>И</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Т</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ь</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Б</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>Ю</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>-</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>.</td>
	       		<td class='kbButton' onclick='pressVirtualOtherKey(this)'>←</td>
	       		</tr>
	       		
	       		</table>
	       	</div>	   
	       	<script type="text/javascript">
	       	var fieldVirtualKey = "lastname" ;
	       	var elements=["lastname","firstname","middlename"] ;
	       	function pressVirtualKey(elem) {
	       		$(fieldVirtualKey).value=$(fieldVirtualKey).value+elem.innerHTML ;
	       	}
	       	function pressVirtualOtherKey(elem) {
	       		$(fieldVirtualKey).value=$(fieldVirtualKey).value.substring(0,$(fieldVirtualKey).value.length-1) ;
	       	}
	       	function rowDefault(aField) {
	       		
	       		for (var i=0;i<elements.length;i++){
	       			var fld = $(elements[i]+"Label") ;
	       			Element.removeClassName(fld, "selected") ;
	       		}
	       		Element.addClassName($(aField+"Label"), "selected") ;
	       		fieldVirtualKey=aField ;
	       	}
	       	function goNextStep() {
	       		var isNext = true ;
	       		var info="" ;
	       		for (var j=0;j<elements.length;j++){
	       			var fld = elements[j] ;
	       			info = info+"&"+fld+"="+$(fld).value ;
	       			if ($(fld).value=="") {
	       				rowDefault(fld) ;
	       				isNext = false ;
	       				break ;
	       			} 
	       		}
	       		if (isNext) window.location="step_pre_record_1.do?"+info.substring(1)+"${addParam}" ;
	       	}
	       	function goNext(aParam) {
	       		var lastname='${param.lastname}' ;
	       		var firstname='${param.firstname}' ;
	       		var middlename='${param.middlename}' ;
	       		var vocWorkCalendar = '${param.vocWorkCalendar}';
	       		var workCalendar ='${param.workCalendar}'
	       		window.location="step_pre_record_info.do" ;
	       		
	       	}
	       	</script>
	       	<tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>