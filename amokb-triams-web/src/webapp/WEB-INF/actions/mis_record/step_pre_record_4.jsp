<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">${infoRecord} Шаг 5. Выбор даты</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
      	.freeDay{
  		background-color: #DDD;
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center;
  	}
  	.busyDay{
  		background-color: #ff3333;
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center;  		
  	}
  	.selectedVisitDay {
  		background-color: navy;		
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center;  
  		color: white;	
  	}
  	.selectedVisitDay:HOVER{
  		background-color: #4D90FE;
  		/*font-size: medium;*/
  		color:black;
  		font-weight: bolder;
  		text-align: center;  		
  	}  	
  	.selectedBusyDay {
  		background-color: pink;		
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center;  
  		color: white;	
  	}
  	.selectedBusyDay:HOVER{
  		background-color: #4D90FE;
  		/*font-size: medium;*/
  		color:black;
  		font-weight: bolder;
  		text-align: center;  		
  	}  	
  	.visitDay {
  		background-color: #0066cc;
  		color:white;
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center;  		
  	}
  	.visitDay:HOVER{
  		background-color: #4D90FE;
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center; 
  		color:black; 		
  	}
  	.listDates {
  		border: 2px;
  		padding: 4px;
  		margin: 5px;
  		border: 2px black outset;
  		font: 1.5em Arial,helvetica,clean,sans-serif;
  	}
  	.listDates td,.listDates th {
  		border: 2px black outset;
  		padding: 4px;
  		margin: 5px;
    }
    ul.listTimes {
	margin-left: 0;
	padding-left: 0;
	} 
 	ul.listTimes li ul.ulTime {
	margin-left: 0;
	padding: 0;
	
	display: list-item;
	list-style: none;
	} 
	 ul.listTimes {
	margin: 0;
	padding: 0;
	display: inline;
	} 

 ul.listTimes li { 
	padding-bottom: 0px ;
	padding-top: 0px ;
	padding-left: 0px ;
	padding-right: 0px ;
	list-style: none;
	display: inline;
	font: 1.2em Arial,helvetica,clean,sans-serif;
	}
	
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	   <div style="display: inline; position: relative; float: left; margin-right: 40px;">${days}</div>
	   <div style="display: inline; position: relative; float: left;">
	       			<h2>ЛЕГЕНДА</h2>
    			<ul class="listTimes">
	    			<li  class="liList">
		    			<ul class="days" style="display: in">
		    				<li class="visitDay">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
		    				<li> - день, в котором <b>есть времена</b> для записи<br></li>
		    				<li class="busyDay">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
		    				<li> - день, в котором <b>нет времен</b> для записи<br></li>
		    				<li class="freeDay">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
		    				<li> - нет записи </li>
		    			</ul>
	    			</li>
    			</ul>
	   </div>
	    
    </tiles:put>
    <tiles:put name="javascript" type="string">
    	<script type="text/javascript">
    		function step5(aParam) {
    			window.location = "${path_rec}5.do?"+aParam ;
    		}
    		function step4(aParam) {
    			window.location = "${path_rec}4.do?"+aParam ;
    		}
    	</script>
    </tiles:put>
</tiles:insert>