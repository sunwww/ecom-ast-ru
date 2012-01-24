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
        
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/work_create_timeBySpecialist.do" defaultField="specialistName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры " colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        	<msh:autoComplete fieldColSpan="3" vocName="workFunctionByDirect"
        	 property="specialist" label="Специалист" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="date" label="Дата" />
	        <msh:textField property="countVisits" label="Кол-во"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="timeFrom" label="Начиная со времени"/>
	        <msh:textField property="timeTo" label="Начиная со времени"/>
        </msh:row>
        <msh:row>
        	<td>
            	<input type="submit" onclick="find()" value="Найти" />
        	</td>
        </msh:row>
        <msh:row>
        	<msh:textArea property="times" label="Времена" fieldColSpan="3" horizontalFill="true"/>
        	<td>
        		<input type="button" onclick="generate()" value="Добавить" />
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
    				    
    				}}) ;
    }
    function generate() {
    	WorkCalendarService.getCreateNewTimesBySpecAndDate($('date').value
    			, $('specialist').value, $('times').value
    			, {
    	  	 callback: function(aResult) {
    				 	alert(aResult) ;
    				}}) ;
    }
    
    </script>  
  </tiles:put>
</tiles:insert>

