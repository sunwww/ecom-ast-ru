<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
 
 <%@ attribute name="name" required="true" description="название" %>
 <%@ attribute name="title" required="true" description="Заголовок" %>
 <%@ attribute name="action" required="true" description="название" %>
 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
 
 <style type="text/css">
    #${name}WorkCalendar  {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
 
 <msh:sideLink name="${title}" action=" javascript:show${name}WorkCalendar('.do') " />
 <form action="javascript:void(0)" >
 <div id='${name}WorkCalendar' class='dialog'>
    <h2>Выбор даты</h2>
    <div class='rootPane'>
    <input type="hidden" id="${name}WorkFunction" name="${name}WorkFunction" />
    <input type="hidden" id="${name}WorkCalendar" name="${name}WorkCalendar"/>
    <input type="hidden" id="${name}WorkCalendarDay" name="${name}WorkCalendarDay"/>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/ViewAll">
<msh:panel>
    	<msh:autoComplete size="120" property="${name}WorkFunction" label="Рабочая функция" vocName="workFunctionByDirect" horizontalFill="true"/>
 </msh:panel>    </msh:ifInRole>
    <div id="idManyCalendar"></div>
    
        <div style="float: left; margin-left: 1em; margin-bottom: 1em;" id="workCalendarMain${name}"></div>
        <div id="workCalendarComment${name}" style="margin-right: 1em; margin-bottom: 1em;"></div>
		<div  style="float: right;">
            <input type="button" onclick="javascript:save${name}WorkCalendar()" value='OK' id='${name}ButtonOk' name='${name}ButtonOk'/>
            <input type="button" value='Отменить' id='${name}ButtonCancel' name='${name}ButtonCancel' onclick='cancel${name}WorkCalendar()'/>
        </div>
      
 </div>
 </div>

    </form>
<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>

<script type="text/javascript">
     var theIs${name}Initialized = false ;
     var the${name}WorkCalendarDialog = new msh.widget.Dialog($('${name}WorkCalendar')) ;
     var the${name}WorkFunction;
     var the${name}WorkCalendar;
     var the${name}Calendar;
     var zam = 0 ;
     
     function getWorkCalendar() {
    		$('workCalendarMain${name}').innerHTML="" ;
    		$('workCalendarComment${name}').innerHTML="" ;
    		$('${name}ButtonOk').disabled=true ;
    			WorkCalendarService.getWorkCalendar(
    			          the${name}WorkFunction.value, 
    				    	   	{
    						 callback: function(aString1) {
    						                	     $('${name}WorkCalendar').value=aString1 ;
    						                	     var currentDay = 0 ;
    						                	     if (currentDay==0) {
    							                          var date=new Date();
    		  									          var y = date.getFullYear();
    												      var m = date.getMonth()+1;     // integer, 0..11
    												      var d = date.getDate();      // integer, 1..31
    													  the${name}Calendar = Calendar.setup(
    													    {
    													      flat         : "workCalendarMain${name}", // ID of the parent element
    													      flatCallback : ${name}DateChanged ,          // our callback function
    													      
    													    }
    													  );
    	  						                	      getInfoByDay(d,m,y) ;
    	  						                	       the${name}WorkCalendarDialog.hide() ;
    	  						                	       the${name}WorkCalendarDialog.show() ;
    						                	      }
    					       						} ,
    						errorHandler: function(aMessage) {
    								var ind = aMessage.indexOf(":") ;
    								aMessage = aMessage.substring(ind+1) ;
    											    $('workCalendarMain${name}').innerHTML = "<p class='error'>"+aMessage +"</p>";
    											    
    	 				                	       the${name}WorkCalendarDialog.hide() ;
    	 				                	       the${name}WorkCalendarDialog.show() ;
    											    
    										}
    						            }
    					         ) ;
    					         
    	}
  function ${name}DateChanged(calendar) {
  

    if (calendar.dateClicked) {
      // OK, a date was clicked, redirect to /yyyy/mm/dd/index.php
      var y = calendar.date.getFullYear();
      var m = calendar.date.getMonth()+1;     // integer, 0..11
      var d = calendar.date.getDate();      // integer, 1..31
      getInfoByDay(d,m,y) ;
    }
  };


 
  
  
  function getInfoByDay(d,m,y)  {
      if (+d<10)  d = "0"+d ;
      if (+m<10) m = "0"+m ;
      the${name}Date=d+"."+m+"."+y ;
       
       $('workCalendarComment${name}').innerHTML ="<i>Загрузка данных...</i>"
       $('${name}ButtonOk').disabled=true ;
       WorkCalendarService.getWorkCalendarDay(
			the${name}WorkCalendar.value,the${name}WorkFunction.value,the${name}Date
      , {
             callback: function(aString2) {
                $('${name}WorkCalendarDay').value=aString2 ;
                   var executed,planned,workCalendarDayId ;
                   var pos=aString2.indexOf("#") ;
                   var pos1=aString2.indexOf("#",pos+1) ;
                   var pos2=aString2.indexOf("#",pos1+1) ;
                  
                   workCalendarDayId = aString2.substr(0,pos);
                   
                   executed = aString2.substring(pos+1,pos1);
                   planned =aString2.substring(pos1+1,pos2);
                   prerecord =aString2.substring(pos2+1);
                   
                if (+workCalendarDayId==0) {
                   $('workCalendarComment${name}').innerHTML="На <b>"+ 
                      the${name}Date+"</b> нет данных<br><br>";
                	$('${name}ButtonOk').disabled=true ;
                } else {
                  info = 
                   "Дата:<b> "+the${name}Date+"</b><br>"
                      +"Кол-во  направленных пациентов:<b> "+planned+"</b><br>"
                   +"Кол-во принятых пациентов:<b> "+executed+"</b><br>"
                   +"Кол-во пред.направленных пациентов:<b>"+prerecord+"</b>"
                   //+"ИД<br>"+workCalendarDayId
                   ;
                	 $('workCalendarComment${name}').innerHTML=info ;
                	$('${name}ButtonOk').disabled=false ;
                }
                
             }
         	}
   		) ;
     
 }
	         


     function cancel${name}WorkCalendar() {
         the${name}WorkCalendarDialog.hide() ;
     }

     // Показать
     function show${name}WorkCalendar() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}Initialized) {
         	init${name}WorkCalendar() ;
          }
         the${name}WorkCalendarDialog.show() ;
         try {
        	 $('${name}WorkFunctionName').focus() ;
        	 
         } catch(e) {
        	 
         }

     }
     
     // Сохранение данных
     function save${name}WorkCalendar() {
        var saveIs = 1, error="";
            window.location = "${action}?id="+$('${name}WorkCalendarDay').value
	        	+"&tmp="+Math.random();
	     	the${name}WorkCalendarDialog.hide() ;
        
     }
     
     // инициализация диалогового окна
     function init${name}WorkCalendar() {
     		
     		
     		
     		the${name}WorkFunction = $('${name}WorkFunction');
     		the${name}WorkCalendar = $('${name}WorkCalendar');
     		the${name}WorkCalendarDay = $('${name}WorkCalendarDay');
     		
     		
            if  (zam) {
            	//getWorkCalendar() ;
            } else {
            	var isManyWorkCalendar = 0 ;
            	if (isManyWorkCalendar) {
            		
            	} else {
            		$('${name}WorkFunction').value=-1 ;
            		getWorkCalendar() ;
            		/*
    	    		WorkCalendarService.getWorkFunction(
   			     		{
		                   callback: function(aString) { 
		                   		$('${name}WorkFunction').value=aString ;
		                   		
		                   		getWorkCalendar() ;
		                   }
   			            }
		         	) ;*/
            	}
             }
	         if  (zam) {
	         	${name}WorkFunctionAutocomplete.addOnChangeCallback(function() {
	         		getWorkCalendar() ;
						}) ;     
				$('workCalendarMain${name}').innerHTML = "<p class='message'>Выберите рабочую функцию специалиста</p>";
			}
	        
	         theIs${name}Initialized = true ;
     
     }
     

</script>

<msh:ifInRole roles="/Policy/Mis/MedCase/Visit/ViewAll">
<script type="text/javascript">
 zam=1 ;
</script>
</msh:ifInRole>
    