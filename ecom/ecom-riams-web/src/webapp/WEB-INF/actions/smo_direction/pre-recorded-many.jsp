<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="style" type="string">
  	<style type="text/css">

  	ul#listSpecialists li,ul#listDates li,ul#listTimes li,ul#listPatients li 
  	,ul#listFunctions li {
  		list-style:none outside none;
  	}
  	li#liTime:HOVER,ul#listPatients li:HOVER,ul#listSpecialists li:HOVER
  	,ul#listFunctions li:HOVER,.busyDay:HOVER,.selectedVisitDay:HOVER
  	,.selectedBusyDay:HOVER
  	,.visitDay:HOVER
  	 {
  		cursor: pointer;
  		
  	}
  	ul#listDirects li.liTimeDirect {
	background-color: #ff3333;
	border-top: 1px solid;
	
	}
	ul#listDirects li.liTimePre {
	background-color: #33cc66;
	border-top: 1px solid;
	
	}
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
  		padding: 2px;
  		margin: 2px;
  		border: 2px black outset;
  	}
  	.listDates td,.listDates th {
  		border: 2px black outset;
  	}
  	.listDates th {
  		/*font-size: medium;*/
  		font-weight: bolder;
  		text-align: center;
  		background-color: #BBCCFF;
  		
  	}
  	.spanNavigMonth {
  		/*font-size: medium;*/
  		font-weight: bolder;
  	}
  	.spanNavigMonth a{
  		/*font-size: medium;*/
  		font-weight: bolder;
  	}
  	.spanNavigMonth a:HOVER{
  		/*font-size: medium;*/
  		font-weight: bolder;
  		background-color: yellow;
  	}
  	
 ul li.title {
 font-weight: bolder;
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
	/*font-size: medium;*/
	} 
 ul.listTimes li ul.ulTime li#liTimeDirect{
	margin-left:  0;
	padding-left:0;
	list-style: none;
	/*font-size: medium;*/
	background-color: #ff3333;
	font-weight: bold;
	} 
li.liList{
	padding: 0;
}
 ul.listTimes li ul.ulTime li#liTimePre{
	margin-left:  0;
	padding-left: 0;
	list-style: none;
	/*font-size: medium;*/
	background-color: #33cc66;
	font-weight: bold;
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
	}

ul.listTimes li.first {
	margin-left: 0;
	border-left: none;
	list-style: none;
	display: inline;
	}
	/*
	input.radio {
	display: none ;}*/
  	</style>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Предварительная запись
    	  -->
    	  <table>
    	  <tr>
    	  <td valign="top" width="60%" style="padding-right: 2px">
    	  <table>
    	  	    	<msh:row styleId="step4title">
    		<msh:separator label="Выбор специалиста" colSpan="4"/>
    	</msh:row>
    	<msh:row styleId="step4">
    		<td colspan="4">
    			<div id="rowStep4WorkFunction"><i>Выберите рабочую функцию специалиста(ов)</i></div>
    		</td>
    	</msh:row>

    	  	
    	  </table>
    	  </td>
    	  <td valign="top">
    	  
    	  <table>
    	<msh:row styleId="step3title">
    		<msh:separator label="Параметры направления" colSpan="4"/>
    	</msh:row>
    	<msh:row styleId="step3_1">
    		<msh:autoComplete property="whomDirect" label="Кем направлен:" fieldColSpan="3" horizontalFill="true" vocName="lpu"/>
    	</msh:row>
    	<%--
    	<msh:row styleId="step3_2">
    		<td colspan="4">
    		
    	</msh:row> --%>
    <form id="frmStep1" name="frmStep1" action="javascript:findPatient()" >
    	<msh:row>
    		<msh:separator label="Поиск пациента" colSpan="6" />
    	</msh:row>	
    	<msh:row>
    		<msh:textField property="lastname" label="Фамилия" size="20"/>
    		<msh:textField property="firstname" label="Имя" size="20"/>
    	</msh:row>
    	<msh:row>
    	    <msh:textField property="middlename" label="Отчество" size="20"/>
    	    <msh:textField property="birthday" label="год рождения"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="series" label="Серия полиса" size="20"/>
    		<msh:textField property="number" label="Номер" size="20"/>
    	</msh:row>
    	<msh:row>
    		<msh:autoComplete property="rayon" label="Район" fieldColSpan="3" horizontalFill="true" vocName="vocRayon"/>
    	</msh:row>
    	<msh:row>
    		<td colspan="4" class="buttons" align="left">
				<input type="button" title="Поиск пациента для создания направления" onclick="step1_2();this.value=&quot;Поиск пациента &quot;;this.form.submit(); return true ;" value="    Поиск пациента" class="default" id="submitStep1Go2Button" autocomplete="off">
				<input type="button" title="Поиск направлений по пациенту" onclick="this.value=&quot;Поиск направлений &quot;;this.form.action='javascript:findDirectByPatient()'; this.form.submit(); return true ;" value="    Поиск направлений" class="default" id="submitDirectButton" autocomplete="off">
			</td>
    	</msh:row>
    	</form>
    	<msh:row styleId="step2title" >
    		<msh:separator label="Выбор пациента. Отображаются первые 20 пациентов" colSpan="4"/>
    	</msh:row>
    	<msh:row styleId="step2">
    		<td colspan="4">
    			<div id="rowStep2Patient"><i>Ждите идет поиск...</i></div>
    		</td>
    	</msh:row>
    	<msh:row styleId="findDirecttitle" >
    		<msh:separator label="Поиск направлений по пациенту" colSpan="4"/>
    	</msh:row>
    	<msh:row styleId="findDirect">
    		<td colspan="4">
    			<div id="rowDirectPatient"><i>Ждите идет поиск...</i></div>
    		</td>
    	</msh:row>
    	</table>
    	</td>
    	  </tr>
    	  <tr>
    	  <td colspan="2" width="100%">
    	  <table width="100%">
    
    	<msh:row styleId="step7title">
    		<msh:separator label="Подтверждение введенных данных" colSpan="4" />
    	</msh:row>
    	<msh:row styleId="step7">
    		<td colspan="4" width="100%">
    			<div id="rowStep7Total" />
    		</td>
    	</msh:row>
    </table>
    </td></tr></table>
    
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <msh:title>ПРЕДВАРИТЕЛЬНАЯ ЗАПИСЬ ПАЦИЕНТА НА ПРИЕМ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
   <msh:sideLink roles="/Policy/Mis/MedCase/Direction/PreRecord" name="Пред. запись на 1 спец-та" action="/js-smo_direction-preRecorded.do"/>
   <div id="rowStep3Functions"><i>Ждите идет поиск...</i></div>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
  
  	<script type="text/javascript">
  		var spTime = "" ; 
  		var spDay = "" ;
  		var spSpec = "" ;
  		var spFunc = "" ;
  		var spPat = "" ;
  		eventutil.addEventListener($('lastname'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
	  		findPatient(true) ; 
  		  	}
  		) ;
  		eventutil.addEventListener($('firstname'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
	  		findPatient(true) ; 
  		  	}
  		) ;
  		eventutil.addEventListener($('middlename'), eventutil.EVENT_KEY_UP, 
	  		  	function() {
	  		findPatient(true) ; 
	  		
  		  	}
  		) ;
  		rayonAutocomplete.addOnChangeCallback(function(){
    	  findPatient(true) ;
  		}) ;
  		$('lastname').focus();
  		$('lastname').select();
		showRow("step2title",false) ;
		showRow("step2",false) ;
		
		showRow("findDirecttitle",false) ;
		showRow("findDirect",false) ;
		goStep2() ;
		function uncheckedVocWorkFunction(aValue) {
			var checkBoxGrp = document.forms['frmFunctions'].elements['rdFunction'];
			if (checkBoxGrp) {
				if (checkBoxGrp.length) {
					for(i=0; i < checkBoxGrp.length; i++){
						
						  if (checkBoxGrp[i].value==aValue) checkBoxGrp[i].checked = false;
						}
				} else {
					if (checkBoxGrp.value==aValue) checkBoxGrp.checked = false;
				}
			}		
			step3() ;
		}
		function findDirectByPatient() {
  			showRow("findDirecttitle",true) ;
  			showRow("findDirect",true) ;
  			$('lastname').value = latRus($('lastname').value) ;
  			$('firstname').value = latRus($('firstname').value) ;
  			$('middlename').value = latRus($('middlename').value) ;
			WorkCalendarService.getDirectByPatient( 
				$('lastname').value,$('firstname').value,$('middlename').value
				,$('birthday').value,$('series').value,$('number').value,$('rayon').value
				,$('rayonName').value
				,{
						callback: function(aResult) {
							$('rowDirectPatient').style.display='block' ;
							$('rowDirectPatient').innerHTML=aResult;
	 					}
	 			 } 
			) ;
		}
  		function findPatient(aDirectIs) {
  			showRow("step2title",true) ;
  			showRow("step2",true) ;
  			$('lastname').value = latRus($('lastname').value) ;
  			$('firstname').value = latRus($('firstname').value) ;
  			$('middlename').value = latRus($('middlename').value) ;
  			
  			WorkCalendarService.getPatients(
					$('lastname').value,$('firstname').value,$('middlename').value
					,$('birthday').value,$('series').value,$('number').value,$('rayon').value
					
					,
			     {
						callback: function(aResult) {
							//alert(aResult) ;
							$('rowStep2Patient').style.display='block' ;
							$('rowStep2Patient').innerHTML=aResult;
							step6() ;
							if (aDirectIs) {
								findDirectByPatient() ;
							}
     					}
     			 }) ;
  		}
  		function showRow(aRowId, aCanShow, aField ) {
    		//alert(aRowId) ;
			try {
				//alert( aCanShow ? 'table-row' : 'none') ;
				$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
			} catch (e) {
				// for IE
				//alert(aCanShow ? 'block' : 'none') ;
				$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
			}	
		}
  		function goStep2() {
  			showRow("step3title",true) ;
  			showRow("step3_1",true) ;  		
  			//showRow("step3_2",true) ;  	
  			
  			if ($('rowStep3Functions').style.display=='block') {
  				
  			} else {
  	  			WorkCalendarService.getVocWorkFunction(true,
						
						
  					     {
  								callback: function(aResult) {
  									//alert(aResult) ;
  									$('rowStep3Functions').style.display='block' ;
  									$('rowStep3Functions').innerHTML=aResult;
  		     					}
  		     			 }) ;
  			}
  		}
  		function step3(func) {
  			
  			
  			//if (func==null|| func=="") 
  				func = getCheckedCheckBox($('frmFunctions'),"rdFunction",";") ;
  			//var fun = func.split("#") ;
  			if (func!='') {
  			WorkCalendarService.getSpecialistsByWorkFunctions(
					func,
					
			     {
						callback: function(aResult) {
							$('rowStep4WorkFunction').style.display='block' ;
							$('rowStep4WorkFunction').innerHTML=aResult;
     					}
     			 }) ;
  			} else {
				$('rowStep4WorkFunction').style.display='block' ;
				$('rowStep4WorkFunction').innerHTML='<i>Выберите рабочую функцию специалиста(ов)</i>';
  			}

  		}
  		function step1_1() {
  			document.frmStep1.action='javascript:goStep2()' ;
  		}
  		function step1_2() {

  			document.frmStep1.action='javascript:findPatient()' ;
  		}
  		function step4(aWorkCalendar,aMonth,aYear,aVocWorkFunction) {
  			
  			var frm=document.forms['frmSpecialist_'+aVocWorkFunction] ;
  			//alert(frm) ;
  			var spec ;
  			try {
  				spec = getCheckedRadio(frm,"rdSpecialist") ;
  				//$('rowStep4WorkFunction').style.display='none' ;
  			} catch (e) {
  				spec = "" ;
  			}
  			
  			if (spec!="") {
  				//$('rowStep4Total').style.display='block' ;
  	  			var sp = spec.split("#") ;
  	  			spSpec = spec ;
  	  			
  				//$('rowStep4Total').innerHTML='<b>'+sp[3]+' ('+sp[4]+')'+'</b>' ;
  	  			if (aWorkCalendar==null || +aWorkCalendar<1) aWorkCalendar = sp[0] ;
  	  			if (aMonth==null ||  +aMonth<1) aMonth = sp[1] ;
  	  			if (aYear==null || +aYear<1) aYear = sp[2] ;
  	  			//alert(aWorkCalendar+"-"+aMonth+"-"+aYear+"-"+aVocWorkFunction) ;
  	  			WorkCalendarService.getDatesBySpecialist(
  						aWorkCalendar,aMonth,aYear,aVocWorkFunction,
  						
  				     {
  							callback: function(aResult) {
  								//alert(aResult) ;
  								//$('rowStep5Date').style.display='block' ;
  								$('rowStep5Date_'+aVocWorkFunction).innerHTML=aResult;
  	     					}
  	     			 }) ;
  			}
  		}
  		function step6() {
  			
  		}
  		function step5(aElement,aSp0,aSp1,aSp2,aVocWorkFunction) {
  			var frm=document.forms['frmDate'] ;
  			//var date = getCheckedRadio(frm,"rdDate") ;
  			updateDayCell();
  			//updateDayCell("selectedBusyDay","busyDay");
  			
  			if (Element.hasClassName(aElement,"visitDay")) {
  				Element.removeClassName(aElement, "visitDay") ;
  				Element.addClassName(aElement, "selectedVisitDay") ;
  			} else if (Element.hasClassName(aElement,"busyDay")) {
  				Element.removeClassName(aElement, "busyDay") ;
  				Element.addClassName(aElement, "selectedBusyDay") ;
  			}
  			/*if (aSp0==null || +aSp0<1) aYear = sp[0] ;
  			if (aSp1==null || +aSp1<1) aYear = sp[1] ;
  			if (aSp2==null || +aSp2<1) aYear = sp[2] ;*/
  			//var sp = date.split("#") ;
  			//alert(aSp2) ;
  			
  			try {
  				//alert(aSp2) ;
  				
  	  			spDay = aSp0+"#"+aSp1+"#"+aSp2 ;
  	  			WorkCalendarService.getTimesByWorkCalendarDay(
  						aSp0,aSp1,aVocWorkFunction,
  						
  				     {
  							callback: function(aResult) {
  								$('rowStep6Time_'+aVocWorkFunction).innerHTML=
  									'за <b>'+aSp2+'</b>'
  								+aResult;
  	     					}
  	     			 }) ;
  			} catch(e) {
  				
  			}
  		}
  		
  		function latRus(aText) {
  			aText=aText.toUpperCase() ;
  			aText=replaceAll(aText,"Й", "Q" ) ;
  			aText=replaceAll(aText,"Ц", "W" ) ;
  	    	aText=replaceAll(aText,"У","E"  ) ;
  	    	aText=replaceAll(aText, "К", "R" ) ;
  	    	aText=replaceAll(aText,"Е", "T"  ) ;
  	    	aText=replaceAll(aText, "Ф","A" ) ;
  	    	aText=replaceAll(aText, "Ы", "S") ;
  	    	aText=replaceAll(aText,"В", "D"  ) ;
  	    	aText=replaceAll(aText,"А","F" ) ;
  	    	aText=replaceAll(aText,"П","G"  ) ;
  	    	aText=replaceAll(aText,"Я","Z"  ) ;
  	    	aText=replaceAll(aText,"Ч","X"  ) ;
  	    	aText=replaceAll(aText,"С","C"  ) ;
  	    	aText=replaceAll(aText, "М", "V" ) ;
  	    	aText=replaceAll(aText,"И", "B" ) ;
  	    	aText=replaceAll(aText,"Н", "Y"  ) ;
  	    	aText=replaceAll(aText,"Г", "U"  ) ;
  	    	aText=replaceAll(aText,"Ш", "I"  ) ;
  	    	aText=replaceAll(aText,"Щ", "O"  ) ;
  	    	aText=replaceAll(aText,"З","P" ) ;
  	    	aText=replaceAll(aText, "Р","H" ) ;
  	    	aText=replaceAll(aText,"О", "J"  ) ;
  	    	aText=replaceAll(aText,"Л","K"  ) ;
  	    	aText=replaceAll(aText,"Д", "L" ) ;
  	    	aText=replaceAll(aText,"Т","N" ) ;
  	    	aText=replaceAll(aText, "Ь","M" ) ;
  	    	aText=replaceAll(aText, "Ю","." ) ;
  	    	aText=replaceAll(aText, "Б","," ) ;
  	    	return aText ;
  		}
  		function replaceAll(aText,aSymbRep,aSymbIs) {
  			while (aText.indexOf(aSymbIs)>-1) {
  				aText = aText.replace(aSymbIs,aSymbRep) ;
  			}
  			return aText ;
  		}
  		function updateDayCell() {
  			for (var i=1;i<=31;i++ ) {
  				var cell ;
  				if (i<10) {
  					cell = $('tdDay0'+i) ;
  				} else {
  					cell = $('tdDay'+i) ;
  				}
  				if (cell) {
  	  				if(Element.hasClassName(cell, "selectedVisitDay")) {
  	  					Element.removeClassName(cell, "selectedVisitDay") ;
  	  					Element.addClassName(cell, "visitDay") ;
  	  					i=32 ;
  	  				} else if (Element.hasClassName(cell, "selectedBusyDay")) {
  	  					Element.removeClassName(cell, "selectedBusyDay") ;
	  					Element.addClassName(cell, "busyDay") ;
	  					i=32 ;
  	  				}
  				} else {
  					i=32 ;
  				}
  			}
  			
  		}
  		function deleteTime(aTime,aDirect) {
  			
  			WorkCalendarService.deletePreRecord(
  					aTime,
					
			     {
						callback: function(aResult) {
							//alert(aResult) ;
							if (+aDirect==1) {
								findDirectByPatient() ;
							} 
							if (aResult!='') {
							
								var spDay=aResult ;
								
								var d=spDay.split('#') ;
								var dd = d[2].split('.') ;
								step4(d[0],dd[1],dd[2],d[3]);
								
								step5($('tdDay'+dd[0]),d[0],d[1],d[2],d[3]) ;
								/*
								var d=spDay.split('#') ;
								var dd = d[2].split('.') ;
								step4(d[0],dd[1],dd[2]);
								step5($('tdDay'+dd[0]),d[0],d[1],d[2]) ;
								*/
							}
						}
     			 }) ;
  		}
  		function step6Finish(aTime) {
  			var patInfo = $('lastname').value+"#"
  			+$('firstname').value+"#"+$('middlename').value+"#"
			+$('birthday').value+"#"+$('series').value+"#"
			+$('number').value+"#"+$('rayonName').value ;
  			
  			var patInfo1 = ($('lastname').value+" "
  			+$('firstname').value+" "+$('middlename').value+" "
			+$('birthday').value+" "+$('series').value+" "
			+$('number').value+" "+$('rayonName').value) ;
  			var idPat ='';
  			try {
  				var frm = document.forms['frmPatient'] ;
  				spPat = getCheckedRadio(frm,"rdPatient") ;
  				if (spPat!='') {
  					idPat=spPat.split("#")[0] ;
  				} else {
  					if ($('lastname').value.trim()==''){
  						return ;
  					}
  				}
  			} catch (e) {
					return ;
  			}
  			//alert(2) ;
  			record(patInfo,idPat,aTime);
  		}
  		function record(aPatInfo,aPat,aTime) {
  			/*
  			var pat = 0;
  			if (aPat!='') {
  				var pat1 = aPat.split('#') ;
  				pat = pat1[0] ;
  			} */
  			//alert(aPatInfo+"#"+aPat+"#"+aTime);
  			WorkCalendarService.preRecordByTimeAndPatient(
  					aPatInfo,aPat,aTime,
					
			     {
						callback: function(aResult) {
							//alert(aResult) ;
							
							var spDay=aResult ;
							
							var d=spDay.split('#') ;
							var dd = d[2].split('.') ;
							step4(d[0],dd[1],dd[2],d[3]);
							
							step5($('tdDay'+dd[0]),d[0],d[1],d[2],d[3]) ;
						}
     			 }) ;
  			
  		}
  		
  		function patientCame(aTimeId, aPatientInfo, aPatientId) {
  			if (+aPatientId>0) {
  				window.location = 'entityParentPrepareCreate-smo_direction.do?id='
  						+aPatientId+"&time="+aTimeId ;
  			} else{
  				if (confirm('Перейти на поиск пациента?')) {
  					var pat = aPatientInfo.split(' ') ;
  					var lastname = "";
  					if (pat.length>0) {
  						lastname = pat[0] ;
  						if (pat.length>1) {
  							lastname = lastname+" "+pat[1];
  							if (pat.length>2) {
  								lastname = lastname+" "+pat[2];
  							}
  						}
  						var url = 'mis_patients.do?lastname='+lastname ;
  	  					window.location= url;
  					}
  				} else {
  					var pat = aPatientInfo.split(' ') ;
  					var lastname = "";
  					if (pat.length>0) {
  						lastname = pat[0] ;
  						if (pat.length>1) {
  							lastname = lastname+" "+pat[1];
  							if (pat.length>2) {
  								lastname = lastname+" "+pat[2];
  							}
  							
  						}
 	 					window.location = 'entityPrepareCreate-mis_patient.do?lastname='
 	 							+lastname ;
  					}
  					
  				}
  			}
  		}
  		Element.addClassName($('lastname'),"upperCase") ;
  		Element.addClassName($('firstname'),"upperCase") ;
  		Element.addClassName($('middlename'),"upperCase") ;
  		Element.addClassName($('series'),"upperCase") ;
  		Element.addClassName($('number'),"upperCase") ;
  		Element.addClassName($('lastname'),"required") ;
  		new dateutil.DateField($('birthday'));
  	</script>
  </tiles:put>

</tiles:insert>

