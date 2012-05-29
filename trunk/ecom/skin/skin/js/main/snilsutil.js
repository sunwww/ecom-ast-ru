var theTimeout = null ;

/*
 * new dateutil.DateField(element) ;
 */
var snilsutil =
{
    Version: '1.0'
}

snilsutil.SnilsField = function(aElement) {

    aElement.onblur = check ;
    var theElement  = aElement;

    var theFieldError = null ;

    function liveCheck(aEvent) {
        var keyCode = aEvent.keyCode ;
        if(keyCode == eventutil.VK_BACKSPACE) {

        } else {
            var size = theElement.value.length ;
            if(size==3 || size==7 ) {
                theElement.value = theElement.value + "-" ;
            } else if(size==11) {
            	theElement.value = theElement.value + " " ;
            } else if(size>14) {
                theElement.value = theElement.value.trim().substring(0,14) ;
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
            var oldSnils = theElement.value ;
            
            if (!(oldSnils == null || oldSnils == ""))
            {
                var snils = parseSnils(oldSnils);
                /*if(oldSnils!=snils)
                {
                    theElement.value = snils ;
                }*/
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

}

////////////////////////////////////////////////////////////////////////////
//                         PRIVATE FUNCTION                               //
////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////
//                  ADDITION PRIVATE FUNCTION                             //
////////////////////////////////////////////////////////////////////////////

// проверка корректности введенной даты
function parseSnils(inputStr) {
	var snilsReg = new RegExp("[0-9]{3}[-]{1}[0-9]{3}[-]{1}[0-9]{3}[ ]{1}[0-9]{2}") ;
    if (!snilsReg.test(inputStr))
    {
        throw errorutil.SetErrorObj("Форма ввода СНИЛС: NNN-NNN-NNN NN. Например: 111-111-111 11") ;
    }
    return "";
}
