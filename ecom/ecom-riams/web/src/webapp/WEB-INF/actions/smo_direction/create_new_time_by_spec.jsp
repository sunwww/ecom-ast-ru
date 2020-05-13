<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Patient">Создание нового времени по специалисту</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecord" name="Пред. запись" action="/js-smo_direction-preRecorded.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecordMany" name="Пред. запись неск-ко специалистов" action="/js-smo_direction-preRecordedMany.do"/>
    	<msh:sideLink roles="/Policy/Mis/MedCase/Direction/Journal" name="Журнал направленных" action="/visit_journal_direction.do"/>
        </msh:sideMenu>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/work_create_timeBySpecialist.do" defaultField="specialistName" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    <msh:row>
    	<msh:separator label="Выбор режима работы" colSpan="7"/>
    </msh:row>
    <msh:row>
    <td onclick="this.childNodes[1].checked='checked';showCreateTime()"> <input class="radio" name="rdMode" id="rdMode" value="1" type="radio" >создание доп.времени</td>
    <td onclick="this.childNodes[1].checked='checked';showMoveDay()"> <input class="radio" name="rdMode" id="rdMode" value="1" type="radio" >перенос рабочего дня</td>
    <td onclick="this.childNodes[1].checked='checked';showMoveSpec()"> <input class="radio" name="rdMode" id="rdMode" value="1" type="radio" >замена врача </td>
    <td onclick="this.childNodes[1].checked='checked';showClearDayBySpec()"> <input class="radio" name="rdMode" id="rdMode" value="1" type="radio" >очистить своб.времена</td>
    
    </msh:row>
      <msh:row>
        <msh:separator label="Параметры" colSpan="7" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete fieldColSpan="5" vocName="workFunctionByDirect"
        	 property="specialist" label="Специалист" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete fieldColSpan="5" vocName="vocServiceReserveType"
        	 property="reserveType" label="Резерв" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete fieldColSpan="5" vocName="workFunctionByDirect"
        	 property="moveSpecialist" label="Замена на спец-та" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="date" label="Дата" />
        	<msh:textField property="moveDate" label="Перенести на дату" />
        </msh:row>
        <msh:row>
	        <msh:textField property="timeFrom" label="Начиная со времени"/>
	        <msh:textField property="timeTo" label="Начиная со времени"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="countVisits" label="Кол-во"/>
        	<td colspan="3">
            	<input type="submit" onclick="find()" value="Найти" />
        	</td>
        </msh:row>
        <msh:row>
        	<msh:textArea property="times" label="Времена" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<td colspan="6" align="center">
        		<input type="button" id="btnGenerate" name="btnGenerate" onclick="generate()" value="Добавить времена" />
        		<input type="button" id="btnMoveSpec" name="btnMoveSpec" onclick="moveDays()" value="Перенести пациентов с одного дня на другой" />
        	<%--	<input type="button" onclick="combineSpec()" value="Совместить пациентов одного специалиста с другого" />  --%>
        		<input type="button" id="btnMoveDays" name="btnMoveDays" onclick="moveSpec()" value="Перенести пациентов с одного специалиста на другого" />
        		<input type="button" id="btnShowClearDayBySpec" name="btnShowClearDayBySpec" onclick="clearTime()" value="Очистить незанятые времена" />
        	</td>
        </msh:row>
    </msh:panel>
    </msh:form>

  </tiles:put>
  <tiles:put type="string" name="javascript">
    <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
    <script type='text/javascript'>
    document.forms[0].action='javascript:getTimeBySpecAndDate()' ;
    function find() {
    	document.forms[0].action='javascript:getTimeBySpecAndDate()' ;
    }
    specialistAutocomplete.addOnChangeCallback(function(){
			updateDate() ;
		}) ;
  	eventutil.addEventListener($('date'), "change", 
  		  	function() {
  				updateDate() ;
  		  	}) ;
  	eventutil.addEventListener($('date'), "blur", 
  		  	function() {
  				updateDate() ;
  		  	}) ;
    function updateDate() {
    	WorkCalendarService.getIntervalBySpecAndDate($('date').value
    			, $('specialist').value
    			, {
    	  	 callback: function(aResult) {
    				 	//alert(aCount) ;
    				 	if (aResult!=null && aResult!="") {
    				 		var s = aResult.split("-") ;
    				 		$('timeFrom').value=s[0] ;
    				 		$('timeTo').value=s[1] ;
    				 		
    				 	}
    				    
    				    
    				},
    	    		errorHandler: function(aMessage) {
    	        		
    	    		}}) ;
    }
    function getTimeBySpecAndDate() {
    	WorkCalendarService.getTimesBySpecAndDate($('date').value
    			, $('specialist').value, $('countVisits').value
    			, $('timeFrom').value, $('timeTo').value
    			
    			, {
    	  	 callback: function(aResult) {
    				 	//alert(aCount) ;
    				    $('times').value = aResult ;
    				    
    				},
    	    		errorHandler: function(aMessage) {
    	        		
    	    		}}) ;
    }
    function generate() {
    	var dt = $('moveDate').value ;
    	if (dt!='' && !confirm("Вы хотите времена добавить в каждый день за период "+$('date').value+"-"+dt+"?")) dt="" ;
    	WorkCalendarService.getCreateNewTimesBySpecAndDate($('date').value
    			, dt ,$('reserveType').value, $('specialist').value, $('times').value
    			, {
    	  	 callback: function(aResult) {
    				 	alert(aResult) ;
    				},
    		errorHandler: function(aMessage) {
    		
    		}
    	}) ;
    }
    function moveSpec() {
    	WorkCalendarService.moveSpecialistPlanByDate($('date').value
    			, $('moveDate').value , $('specialist').value, $('moveSpecialist').value
    			, {
    	  	 callback: function(aResult) {
    				 	alert(aResult) ;
    				},
    		errorHandler: function(aMessage) {
    			alert("Не удалось перенести! "+aMessage) ;
    		}
    	}) ;    
    }
    function moveDays() {
    	WorkCalendarService.moveDatePlanBySpec($('date').value
    			, $('moveDate').value, $('specialist').value
    			, {
    	  	 callback: function(aResult) {
    				 	alert(aResult) ;
    				},
    		errorHandler: function(aMessage) {
    			alert("Не удалось перенести! "+aMessage) ;
    		}
    	}) ;    
    }
    function clearTime() {
    	WorkCalendarService.deleteEmptyCalendarDays($('date').value
    			, $('moveDate').value, $('specialist').value
    			, {
    	  	 callback: function(aResult) {
    				 	alert(aResult) ;
    				},
    		errorHandler: function(aMessage) {
    			alert("Не удалось очистить времена! "+aMessage) ;
    		}
    	}) ;    
    }
    function showCreateTime() {
    	$('btnGenerate').style.display='';
    	$('btnMoveSpec').style.display='none';
    	$('btnMoveDays').style.display='none';
    	$('btnShowClearDayBySpec').style.display='none';
    	$('specialistName').className = 'autocomplete horizontalFill required' ;
    	$('moveSpecialistName').className = 'autocomplete horizontalFill' ;
    	$('reserveTypeName').className = 'autocomplete horizontalFill required' ;
    	$('date').className = 'required' ;
    	$('moveDate').className = '' ;
    	$('timeTo').className = 'required' ;
    	$('timeFrom').className = 'required' ;
    	$('countVisits').className = 'required' ;
    	$('times').className = ' horizontalFill required' ;
    }
    function showMoveDay() {
    	$('btnGenerate').style.display='none';
    	$('btnMoveSpec').style.display='';
    	$('btnMoveDays').style.display='none';
    	$('btnShowClearDayBySpec').style.display='none';
    	$('specialistName').className = 'autocomplete horizontalFill required' ;
    	$('moveSpecialistName').className = 'autocomplete horizontalFill' ;
    	$('reserveTypeName').className = 'autocomplete horizontalFill' ;
    	$('date').className = 'required' ;
    	$('moveDate').className = 'required' ;
    	$('timeTo').className = '' ;
    	$('timeFrom').className = '' ;
    	$('countVisits').className = '' ;
    	$('times').className = ' horizontalFill' ;
    }
    function showMoveSpec() {
    	$('btnGenerate').style.display='none';
    	$('btnShowClearDayBySpec').style.display='none';
    	$('btnMoveSpec').style.display='none';
    	$('btnMoveDays').style.display='';
    	$('specialistName').className = 'autocomplete horizontalFill required' ;
    	$('moveSpecialistName').className = 'autocomplete horizontalFill required' ;
    	$('reserveTypeName').className = 'autocomplete horizontalFill' ;
    	$('date').className = 'required' ;
    	$('moveDate').className = 'required' ;
    	$('timeTo').className = '' ;
    	$('timeFrom').className = '' ;
    	$('countVisits').className = '' ;
    	$('times').className = ' horizontalFill' ;
    }
    function showClearDayBySpec() {
    	$('btnGenerate').style.display='none';
    	$('btnShowClearDayBySpec').style.display='';
    	$('btnMoveSpec').style.display='none';
    	$('btnMoveDays').style.display='none';
    	$('specialistName').className = 'autocomplete horizontalFill required' ;
    	$('moveSpecialistName').className = 'autocomplete horizontalFill' ;
    	$('reserveTypeName').className = 'autocomplete horizontalFill' ;
    	$('date').className = 'required' ;
    	$('moveDate').className = 'required' ;
    	$('timeTo').className = '' ;
    	$('timeFrom').className = '' ;
    	$('countVisits').className = '' ;
    	$('times').className = ' horizontalFill'     	
    }
    showCreateTime() ;
    document.forms[0].rdMode[0].checked=true ;
    
    </script>  
  </tiles:put>
</tiles:insert>

