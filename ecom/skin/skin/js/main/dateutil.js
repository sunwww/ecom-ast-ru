var theTimeout = null ;

/*
 * new dateutil.DateField(element) ;
 */
var dateutil =
{
    Version: '1.0'
}

dateutil.DateField = function(aElement) {

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
//            theFieldError = errorutil.HideError(theElement) ;
            }
        }
        catch (e)
        {
            //dump(e) ;
            theFieldError = errorutil.ShowFieldError(theElement, e.message) ;
            return false ;
        }
    }

};

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
    var thePlusDate = parseInt(aPlusDate,10);

    // Методы
    function onLinker(aEvent) {
        try {
            var dateString = parseDate(theElement.value);

            var yyyy = parseInt(dateString.substring(6,dateString.length),10) ;
            var dd = parseInt(dateString.substring(0,2),10) ;
            var mm = parseInt(dateString.substring(3,5),10) ;
            var date = new Date(yyyy, mm-1, dd) ;
            theElement.value = monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+ date.getYear();
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

        } catch(e) {

        }
    }



};
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
//                         PRIVATE FUNCTION                               //
////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////
//                  ADDITION PRIVATE FUNCTION                             //
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
        if (yyyy >= 30) {
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
        var delimfirst=inputStr.indexOf(".");
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