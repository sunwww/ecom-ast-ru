<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
 
 <%@ attribute name="name" required="true" description="название" %>
 <%@ attribute name="workFuncId" required="true" description="Рабочая функция"  %>
 <%@ attribute name="calenDayId" required="true" description="поле WorkCalendarDay"  %>
 <%@ attribute name="calenTimeId" required="true" description="поле WorkCalendarTime"  %>
 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
 
 <style type="text/css">
    #${name}WorkCalendar  {
        visibility: hidden ;
        display: none ;
        position: absolute ;
        width: 90% ;
    }
    .x-grid-hd-row td, .x-grid-row td{
	    line-height:13px;
	    white-space: nowrap;
		vertical-align: top;
		-moz-outline: none;
		-moz-user-focus: normal;
	}
	.x-grid-hd-row td div, .x-grid-row td div{ 
		padding-left: 5px;
		padding-right: 5 px;
		padding-bottom: 3px ;
		padding-top: 4px ;
	}
	.x-grid-hd-row td {
		line-height:14px;
	}
	.x-grid-hd {
		padding-right:1px;
	}
	.x-grid-header {
		background: #EBEADB url(/skin/ext/images/gray/grid/grid-hrow.gif) repeat-x scroll;
		display: block ;
		//overflow:hidden;
		position:relative;
	//	cursor:default;
	//	width:100%;
	}
	.x-grid-body{
		//overflow:hidden;
		position:relative;
//		cursor:default;
//		width:100%;
	}

	.x-grid-hd-text {
		color:#000000;
	}
    .x-grid-row-alt{
		background-color: #f1f1f1 ;
	}
	.x-grid-row-over td{
		background-color:#d9e8fb;
	}
	.x-grid-locked .x-grid-header table{
    border-right:1px solid transparent;
}
	.grid-hd-spec, .x-grid-hd-spec{
		width:350px ;
	}
	.grid-hd-date,  .x-grid-hd-date{
		width:90px ;
	}
	.x-grid-row {
		height:21px;
		color:#393939;
	}
	.x-grid-hd-row {
		height:22px;
	}

	.x-grid-hd-row td, .x-grid-row td {
		-moz-user-focus:normal;
		-x-system-font:none;
		font-size-adjust:none;
		font-stretch:normal;
		font-style:normal;
		font-variant:normal;
		font-weight:normal;
		outline-color:-moz-use-text-color;
		outline-style:none;
		outline-width:medium;
		vertical-align:top;
		white-space:nowrap;
	}

	.x-grid-split {
		background-image:url(/skin/ext/images/default/grid/grid-split.gif);
		background-position:center center;
		background-repeat:no-repeat;
		cursor:col-resize;
		display:block;
		//font-size:1px;
		height:16px;
		overflow:hidden;
		position:absolute;
		top:2px;
		width:6px;
		z-index:3;
	}
	.x-grid-col {
		border-bottom:1px solid #F1EFE2;
		border-right:1px solid #F1EFE2;
	}
	.x-grid-scroller {
		//overflow:hidden;
		width: 100% ;
	}
	.x-grid {
		overflow: auto;
	}
</style>
 <div id='${name}WorkCalendar' class='dialog'>
    <h2>Выбор даты</h2>
    <div class='rootPane'>
    <table width='100%'>
         <msh:row>
         	<msh:textField property="${name}DateStart" label="Период с"/>
         	<msh:textField property="${name}DateFinish" label="по"/>
         	<td width="100%"></td>
         </msh:row>
         <msh:row>
         	 <msh:autoComplete vocName="vocWorkFunction" property="${name}VocWorkFunction" label="Должность" horizontalFill="true" fieldColSpan="5"/>
         	         <td><input type="button" value='Поиск' id='${name}ButtonFind' name='${name}ButtonFind' onclick='load${name}Data()' title="Поиск"/></td>
         	 
        </msh:row>
    </table>
        <div style="width:  window.width">
        <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
        <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
        	<h3 id='${name}H3Title' style="margin-bottom: 5px;">Расписание врачей</h3>
            	<div class="x-grid-container xedit-grid" style="border: 1px solid rgb(153, 187, 232); overflow: hidden; height: window.height-100; position: static; width: screen.width;">
		           <!--  <div id="${name}load" style="border:1px solid #99bbe8;overflow: hidden; 
		            	 height: 600px;position:relative;left:0;top:0;"> -->
		            	<div id="ext-gen39" class="x-grid" hidefocus="false" style="width:100%; height: window.height-150">
							
							<div id="ext-gen46" class="x-grid-scroller" style="left: 0px; top: 0px; visibility: visible;">
								<div id="${name}DivHeader" class="x-grid-header"></div>
								
								<div id="${name}DivBody" class="x-grid-body" style="height: 542px;"></div>
							</div>
		            	</div>
		            <div></div>
            	</div>
        </div></div></div>
        <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
        </div>
		<div  style="">
<%--             <input type="button" value='OK' id='${name}ButtonOk' name='${name}ButtonOk' onclick='save${name}WorkCalendar()'/> --%>
            <input type="button" value='Отменить' id='${name}ButtonCancel' name='${name}ButtonCancel' onclick='cancel${name}WorkCalendar()'/>
        </div>
      
 	</div>
 </div>

<script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
<script type="text/javascript">
     var theIs${name}Initialized = false ;
     var the${name}WorkCalendarDialog = new msh.widget.Dialog($('${name}WorkCalendar')) ;
     var the${name}WorkFunction;
     var the${name}WorkCalendar;
     var the${name}SearchFunctionInfo;
     var the${name}SearchFunction;
     
  function ${name}DateChanged(calendar) {
  

    if (calendar.dateClicked) {
      // OK, a date was clicked, redirect to /yyyy/mm/dd/index.php
      var y = calendar.date.getFullYear();
      var m = calendar.date.getMonth()+1;     // integer, 0..11
      var d = calendar.date.getDate();      // integer, 1..31
      //var h = calendar.date.
      //var min = calendar.date.get
      loadDateByDayandFunction(d,m,y) ;
    }
  };


 
  
  
  function loadDateByDayAndFunction(d,m,y)  {
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
       		
     		$('${name}VocWorkFunctionName').focus() ;
     		$('${name}VocWorkFunctionName').select() ;
     }
     
     // Сохранение данных
     function save${name}WorkCalendar(aWorkFunctionId, aCalDayId, aTime, aWorkFunctionInfo, aCalDayInfo, aMinIs) {
        var saveIs = 1, error="";
        //${workFuncId}Autocomplete.setVocId(aWorkFunctionId);
        //alert(aWorkFunctionId+"-"+ aCalDay+"-"+ aTime) ;
        $('${workFuncId}').value=aWorkFunctionId;
        $('${workFuncId}Name').value=the${name}SearchFunctionInfo +""+aWorkFunctionInfo;
        $('${calenDayId}').value=aCalDayId ;
        $('${calenDayId}Name').value = aCalDayInfo;
        $('${calenTimeId}Name').value = aTime ;
        WorkCalendarService.getCalendarTimeId(
					//Long aCalendarDay, String aCalendarTime, Boolean aMinIs,
					aCalDayId,aTime,aMinIs,
			     {
						callback: function(aTime) {
							$('${calenTimeId}Name').value =  aTime.substring(aTime.indexOf("#")+1) ; ;
							$('${calenTimeId}').value = aTime.substring(0,aTime.indexOf("#")) ;
							getPreRecord() ;
     					}
     			 }) ;
		the${name}WorkCalendarDialog.hide() ;     
	}
     // инициализация диалогового окна
     function init${name}WorkCalendar() {
     		the${name}WorkFunction = $('${name}WorkFunction');
     		the${name}WorkCalendar = $('${name}WorkCalendar');
     		the${name}WorkCalendarDay = $('${name}WorkCalendarDay');
	        theIs${name}Initialized = true ;
          	//${name}VocWorkFunctionAutocomplete.addOnChangeCallback(function() {
          	//	load${name}Data() ;
             //}) ;     
             new dateutil.DateField($('${name}DateStart')) ;
             new dateutil.DateField($('${name}DateFinish')) ;
             current_date = new Date();
             
             $('${name}DateStart').value = formatDate(current_date);
             current_date.setDate(current_date.getDate()+7)  ;
             $('${name}DateFinish').value = formatDate(current_date);
              eventutil.addEnterSupport('${name}DateFinish', '${name}ButtonFind') ;
            // alert($('${name}VocWorkFunctionName')) ;

     }
     function formatDate(aDate) {
     	return monthDayFormat(aDate.getDate())+"."+monthDayFormat(aDate.getMonth()+1)+"."+(aDate.getYear()+1900) ;
     }
     function monthDayFormat(val)
	{
		if (isNaN(val) || val == 0)
	    {
			return "01" ;
		}
	    else if (val < 10)
	    {
			return "0" + val ;
		}
		return "" + val ;
	}
     function load${name}Data() {
		    $('${name}DivBody').innerHTML = "Загрузка данных" ;
		    $('${name}DivHeader').innerHTML = "Загрузка данных" ;
		    $('${name}H3Title').innerHTML = "Загрузка данных" ;
		    the${name}SearchFunctionInfo = $('${name}VocWorkFunctionName').value ;
		    the${name}SearchFunctionInfo = the${name}SearchFunctionInfo.substring(the${name}SearchFunctionInfo.indexOf(" ")+1) ;
		    the${name}SearchFunction = $('${name}VocWorkFunction').value ;
		    if ($('${name}DateStart').value=="" || $('${name}DateFinish').value =="" || the${name}SearchFunction=="") {
		    	alert("Введены не все данные для поиска!!!") ;
		    	return "";
		    }
			WorkCalendarService.getTableHeaderByDayAndFunction(
					$('${name}DateStart').value,$('${name}DateFinish').value,350,90,
			    	 {
						callback: function(aStrHeader) {
							$('${name}DivHeader').innerHTML = aStrHeader ;
							WorkCalendarService.getTableBodyByDayAndFunction(
		                		   $('${name}DateStart').value,$('${name}DateFinish').value,the${name}SearchFunction ,'save${name}WorkCalendar',
			    	               	{
										 callback: function(aStrBody) {
										 
					                	      $('${name}DivBody').innerHTML = "<table>"+aStrBody+"</table>";
					                	      cancel${name}WorkCalendar() ;
					                	      show${name}WorkCalendar() ;
				        		         }
					                }
				         ) ;}
				     }) ;       
				     $('${name}H3Title').innerHTML = the${name}SearchFunctionInfo +" за период " +$('${name}DateStart').value+"-"+$('${name}DateFinish').value ;

     }
</script>
    