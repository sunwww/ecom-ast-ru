<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ОФОРМЛЕНИЕ ПРЕДВАРИТЕЛЬНОЙ ЗАПИСИ. Шаг 1. Ввод данных пациента</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
<div>
	   <table>
	   <tr>
	   <td onclick="rowDefault('lastname','characterKeyboard');" id="lastnameLabel" name="lastnameLabel" class="label selected"><b>Фамилия:</b></td>
	   <td onclick="rowDefault('lastname','characterKeyboard');"><input value="${param.lastname}" type="text" name="lastname" id="lastname" style="width: 100%"/></td>
	   <td rowspan="4">	    
	    <div class="divRecord button" onclick="clearValue(1);">
	    	<p class="label" style="color: black;">Очистить все</p>
	    </div>    
	    <br/>
	    <div class="divRecord button" onclick="clearValue(0);">
	    	<p class="label" style="color: black;">Очистить</p>
	    </div>    
	    <br/>
	   <div class="divRecord button fb" onclick="goNextStep()">
	    	<p class="label">Далее</p>
	    </div> 
		</td>
	   </tr>
	   <tr onclick="rowDefault('firstname','characterKeyboard');">
	   <td id="firstnameLabel" name="firstnameLabel" class="label"><b>Имя:</b></td>
	   <td><input type="text" value="${param.firstname}" name="firstname" id="firstname" style="width: 100%"/></td>
	   </tr>
	   <tr onclick="rowDefault('middlename','characterKeyboard');">
	   <td id="middlenameLabel" name="middlenameLabel" class="label"><b>Отчество:</b></td>
	   <td><input type="text" value="${param.middlename}" name="middlename" id="middlename" style="width: 100%"/></td>
	   </tr>
	   <tr onclick="rowDefault('birthday','numericKeyboard');">
	   <td id="birthdayLabel" name="birthdayLabel" class="label"><b>Дата рождения:</b></td>
	   <td><input type="text" value="${param.birthday}" name="birthday" id="birthday" style="width: 100%"/></td>
	   </tr>
	   </table>
</div>
<div align="center">
	   <table  style="margin: 20px;padding:20px;font-size: 1em;display:none;" id='numericKeyboard'>
	       		<tr>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>7</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>8</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>9</td>
	       		</tr>
	       		<tr>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>4</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>5</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>6</td>
	       		</tr>
	       		<tr>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>1</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>2</td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>3</td>
	       		</tr>
	       		<tr>
	       		<td></td>
	       		<td class='kbButton' onclick='pressVirtualKey(this)'>0</td>
	       		<td class='kbButton' onclick='pressVirtualOtherKey(this)'>←</td>
	       		</tr>
	       		
	       		</table>

	       		<table  style="margin: 20px;padding:20px;font-size: 1em;dispaly:none ;"  id='characterKeyboard'>
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
	       	
	       	var elements=["lastname","firstname","middlename","birthday"] ;
	       	function pressVirtualKey(elem) {
	       		$(fieldVirtualKey).value=$(fieldVirtualKey).value+elem.innerHTML ;
	       		liveCheck(0) ;
	       	}
	       	function pressVirtualOtherKey(elem) {
	       		$(fieldVirtualKey).value=$(fieldVirtualKey).value.substring(0,$(fieldVirtualKey).value.length-1) ;
	       		liveCheck(1) ;
	       	}
	       	function liveCheck(aBackspace) {
	       		if (fieldVirtualKey=='birthday') {
	       			if (aBackspace<1) {
	       	            var size = $(fieldVirtualKey).value.length ;
	       	            if(size==2 || size==5) {
	       	            	$(fieldVirtualKey).value = $(fieldVirtualKey).value + "." ;
	       	            } else if(size>10) {
	       	            	$(fieldVirtualKey).value = $(fieldVirtualKey).value.substring(0,10) ;
	       	            }
	       			} else {
	       	            var size = $(fieldVirtualKey).value.length ;
	       	            if(size==2 || size==5) {
	       	            	pressVirtualOtherKey('') ;
	       	            }
	       			}
       	        }
       	    }
	       	function rowDefault(aField,aKeypad) {
	       		
	       		for (var i=0;i<elements.length;i++){
	       			var fld = $(elements[i]+"Label") ;
	       			Element.removeClassName(fld, "selected") ;
	       		}
	       		Element.addClassName($(aField+"Label"), "selected") ;
	       		fieldVirtualKey=aField ;
	       		if (aKeypad=="characterKeyboard") {
	       			$('numericKeyboard').style.display='none' ;
	       		} else {
	       			$('characterKeyboard').style.display='none' ;
	       		}
	       		$(aKeypad).style.display='table' ;
	       	}
	       	function clearValue(aAll) {
	       		if (aAll>0) {
	       			for (var i=0;i<elements.length;i++){
	       				$(elements[i]).value="" ;
	       			}
	       		} else {
	       			$(fieldVirtualKey).value="" ;
	       		}
	       	}
	       	function goNextStep() {
	       		var isNext = true ;
	       		var info="" ;
	       		for (var j=0;j<elements.length;j++){
	       			var fld = elements[j] ;
	       			var keypad='characterKeyboard' ;
	       			if (j>2) {
	       				keypad='numericKeyboard' ;
	       				try
		       	        {
		       	            theFieldError = errorutil.HideError($(fld)) ;
		       	            var oldDate = $(fld).value ;
		       	            if (!(oldDate == null || oldDate == ""))
		       	            {
		       	                var date = parseDate(oldDate);
		       	                if(oldDate!=date)
		       	                {
		       	                	$(fld).value = date ;
		       	                }
//		       	            theFieldError = errorutil.HideError(theElement) ;
		       	            }
		       	        }
		       	        catch (e)
		       	        {
		       	            //dump(e) ;
		       	            theFieldError = errorutil.ShowFieldError($(fld), e.message) ;
		       	         	rowDefault(fld,keypad) ;
	       					isNext = false ;
		       				break ;
		       				
		       	        }
	       				
	       			}
	       			info = info+"&"+fld+"="+$(fld).value ;
	       			if ($(fld).value=="") {
	       				rowDefault(fld,keypad) ;
	       				isNext = false ;
	       				break ;
	       			} 
	       		}
	       		if (isNext) window.location="step_record_1.do?"+info.substring(1)+"${addParam}" ;
	       	}
	       	function goNext(aParam) {
	       		var lastname='${param.lastname}' ;
	       		var firstname='${param.firstname}' ;
	       		var middlename='${param.middlename}' ;
	       		var vocWorkCalendar = '${param.vocWorkCalendar}';
	       		var workCalendar ='${param.workCalendar}'
	       		window.location="step_record_info.do" ;
	       		
	       	}
	       	
	       	
	       	
	       	
	       	
	       	
	       	var theTimeout = null ;

	       	/*
	       	 * new dateutil.DateField(element) ;
	       	 */
	       	var dateutil =
	       	{
	       	    Version: '1.0'
	       	}

	       	dateutil.DateField = function(aElement) {

	       		//var calA = document.createElement("SPAN") ;
	       		//calA.innerHTML="<a href=\"javascript:void(0)\" onclick=\"if(self.gfPop2){gfPop2.fPopCalendar($('"+aElement.id+"'));}return false;\">...</a>" ;
	       	    //	calA.id = 'spanA'+aElement.id;
	       	    	
	       	    //aElement.parentNode.appendChild(calA) ;
	       	   // aElement.parentNode.innerHTML = aElement.parentNode.innerHTML 
	       	    eventutil.addEventListener(aElement, "dblclick", function() {if(self.gfPop2){gfPop2.fPopCalendar($(aElement.id));}}) ;	 
	       	    aElement.onblur = check ;
	       	    var theElement  = aElement;

	       	    var theFieldError = null ;

	       	    function liveCheck(aEvent) {
	       	        var keyCode = aEvent.keyCode ;
	       	        if(keyCode == eventutil.VK_BACKSPACE) {

	       	        } else {
	       	            var size = theElement.value.length ;
	       	            if(size==2 || size==5) {
	       	                theElement.value = theElement.value + "." ;
	       	            } else if(size>10) {
	       	                theElement.value = theElement.value.substring(0,10) ;
	       	            }
	       	        }
	       	    }
	       	    

	       	    eventutil.addEventListener(aElement, "keydown", liveCheck) ;
	       	   // eventutil.addEventListener(aElement, "click", liveCalendar) ;
	       	    eventutil.addEventListener(aElement, "keyup", liveCheck) ;


	       	    function check(aEvent)
	       	    {
	       	        try
	       	        {
	       	            theFieldError = errorutil.HideError(theElement) ;
	       	            var oldDate = theElement.value ;
	       	            if (!(oldDate == null || oldDate == ""))
	       	            {
	       	                var date = parseDate(oldDate);
	       	                if(oldDate!=date)
	       	                {
	       	                    theElement.value = date ;
	       	                }
//	       	            theFieldError = errorutil.HideError(theElement) ;
	       	            }
	       	        }
	       	        catch (e)
	       	        {
	       	            //dump(e) ;
	       	            theFieldError = errorutil.ShowFieldError(theElement, e.message) ;
	       	            return false ;
	       	        }
	       	    }

	       	}

	       	dateutil.PeriodFields = function(aElement, aElementLinker, aPlusYear, aPlusMonth, aPlusDate) {
	       	    try
	       	    {
	       	        aElement.attachEvent('onblur',onLinker);
	       	    } catch (e)
	       	    {
	       	        try { aElement.addEventListener("blur",onLinker,false); } catch (e) {}
	       	    }
	       	    var theElement = aElement ;
	       	    var theLastText = theElement.value ;
	       	    var theElementLinker = aElementLinker ;
	       	    var thePlusYear = parseInt(aPlusYear,10) ;
	       	    var thePlusMonth = parseInt(aPlusMonth,10) ;
	       	    var thePlusDate = parseInt(aPlusDate,10)

	       	    // Методы
	       	    function onLinker(aEvent) {
	       	        try {
//	       	            if(theElementLinker.value=='') {
	       	                var dateString = parseDate(theElement.value);

	       	                var yyyy = parseInt(dateString.substring(6,dateString.length),10) ;
	       	                var dd = parseInt(dateString.substring(0,2),10) ;
	       	                var mm = parseInt(dateString.substring(3,5),10) ;
	       	            var date = new Date(yyyy, mm-1, dd) ;
	       	            theElement.value = monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+ date.getYear()

	       	            if (thePlusDate!=null && thePlusDate>0) {
	       	                date.setDate(dd+thePlusDate) ;
	       	            }


	       	            if (thePlusMonth!=null && thePlusMonth>0) {
	       	                date.setMonth(mm + thePlusMonth) ;
	       	            }

	       	            if (thePlusYear!=null && thePlusYear>0) {
	       	                    date.setYear(yyyy + thePlusYear) ;
	       	            }
	       	                theElementLinker.value = monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+ date.getYear() ;
//	       	            } else {
	       	//
//	       	            }
	       	        } catch(e) {

	       	        }
	       	    }



	       	}
	       	dateutil.YearLinkAction = function(aElement, aLinkedElement) {
	       	    // конструктор
	       	    try
	       	    {
	       	        aElement.attachEvent('onblur',onLinker);
	       	    } catch (e)
	       	    {
	       	        try { aElement.addEventListener("blur",onLinker,false); } catch (e) {}
	       	    }
	       	    var theElement  = aElement;
	       	    var theLastText = theElement.value;

	       	    // методы
	       	    function onLinker(aEvent) {
	       	        try {
	       	            if(aLinkedElement.value=='') {

	       	                var dateString = parseDate(theElement.value);
	       	                var yyyy = parseInt(dateString.substring(6,dateString.length),10) ;
	       	                yyyy+=1 ;

	       	                aLinkedElement.value = dateString.substring(0,6)+yyyy ;
	       	            }
	       	        } catch(e) {

	       	        }
	       	    }

	       	}
	       	////////////////////////////////////////////////////////////////////////////
//	       	                         PRIVATE FUNCTION                               //
	       	////////////////////////////////////////////////////////////////////////////

	       	////////////////////////////////////////////////////////////////////////////
//	       	                  ADDITION PRIVATE FUNCTION                             //
	       	////////////////////////////////////////////////////////////////////////////

	       	// проверка корректности введенной даты
	       	function parseDate(inputStr) {
	       	    while (inputStr.indexOf("-") != -1)
	       	    {
	       	        inputStr = replaceString(inputStr,"-","/") ;
	       	    }
	       	    while (inputStr.indexOf(",") != -1)
	       	    {
	       	        inputStr = replaceString(inputStr,",","/") ;
	       	    }
	       	    while (inputStr.indexOf(".") != -1)
	       	    {
	       	        inputStr = replaceString(inputStr,".","/") ;
	       	    }
	       	    var delim1 = inputStr.indexOf("/") ;
	       	    var delim2 = inputStr.lastIndexOf("/") ;
	       	    if (delim1 != -1 && delim1 == delim2)
	       	    {
	       	        throw errorutil.SetErrorObj("Форма ввода даты: ДД.ММ.ГГГГ. Например: 31.12.2004") ;
	       	        //throw setErrorObj("Форма ввода даты: ddmmyyyy, dd/mm/yyyy, or dd-mm-yyyy,  dd.mm.yyyy.") ;
	       	    }
	       	    if (delim1 != -1)
	       	    {
	       	        var dd = parseInt(inputStr.substring(0,delim1),10) ;
	       	        var mm = parseInt(inputStr.substring(delim1 + 1,delim2),10) ;
	       	        var yyyy = parseInt(inputStr.substring(delim2 + 1, inputStr.length),10) ;
	       	    } else
	       	    {
	       	        var dd = parseInt(inputStr.substring(0,2),10) ;
	       	        var mm = parseInt(inputStr.substring(2,4),10) ;
	       	        var yyyy = parseInt(inputStr.substring(4,inputStr.length),10) ;
	       	    }
	       	    if (isNaN(mm) || isNaN(dd) || isNaN(yyyy))
	       	    {
	       	        throw errorutil.SetErrorObj('Неправильно введена дата') ;
	       	    }
	       	    if (mm < 1 || mm > 12)
	       	    {
	       	        throw errorutil.SetErrorObj('Неправильно введен месяц') ;
	       	    }
	       	    if (dd < 1 || dd > 31)
	       	    {
	       	        throw errorutil.SetErrorObj('Неправильно введен день') ;
	       	    }
	       	    if (yyyy < 100)
	       	    {
	       	        if (yyyy >= 15) {
	       	            yyyy += 1900;
	       	        } else {
	       	            yyyy += 2000;
	       	        }
	       	    }
	       	    if (yyyy > 10000 || yyyy < 1000)
	       	    {
	       	        throw errorutil.SetErrorObj('Неправильно введен год') ;
	       	    }
	       	    return monthDayFormat(dd) + "." + monthDayFormat(mm) + "." + yyyy;
	       	}

	       	function onDateFld(aElementSt, aElementEnd)
	       	{
	       	    if (isDate(aElementSt))
	       	    {
	       	        var inputStr = aElementSt.value ;
	       	        var delimfirst=inputStr.indexOf(".")
	       	        var delimlast=inputStr.lastIndexOf(".");
	       	        var dd = parseInt(inputStr.substring(0,delimfirst),10) ;
	       	        var mm = parseInt(inputStr.substring(delimfirst + 1,delimlast),10) ;
	       	        var yyyy=parseInt(inputStr.substring(delimlast + 1, inputStr.length),10) ;
	       	        aElementEnd.value = dd + "." + mm + "." + (yyyy+1) ;
	       	    }
	       	}

	       	// Заменяет символ aSearch в строке aStr на символ aReplaceStr
	       	function replaceString(aStr,aSearchStr,aReplaceStr)
	       	{
	       	        var front = getFront(aStr,aSearchStr);
	       	        var end = getEnd(aStr,aSearchStr);
	       	        if (front != null && end != null)
	       	        {
	       	                return front + aReplaceStr + end;
	       	        }
	       	        return null;
	       	}

	       	function getFront(aStr,aSearchStr)
	       	{
	       	        var foundOffset = aStr.indexOf(aSearchStr);
	       	        if (foundOffset == -1)
	       	        {
	       	                return null;
	       	        }
	       	        return aStr.substring(0,foundOffset);
	       	}

	       	function getEnd(aStr,aSearchStr)
	       	{
	       	        var foundOffset = aStr.indexOf(aSearchStr);
	       	        if (foundOffset == -1)
	       	        {
	       	                return null;
	       	        }
	       	        return aStr.substring(foundOffset+aSearchStr.length,aStr.length);
	       	}

	       	function monthDayFormat(val)
	       	{
	       		if (isNaN(val) || val == 0)
	       	    {
	       			return "01";
	       		}
	       	    else if (val < 10)
	       	    {
	       			return "0" + val;
	       		}
	       		return "" + val;
	       	}
	       	new dateutil.DateField($('birthday')) ;
	       	</script>
</tiles:put>
</tiles:insert>