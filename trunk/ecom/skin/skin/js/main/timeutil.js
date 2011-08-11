
/*
 * Ввод времени в формате 12.46
 *
 * new timeutil.TimeField(element) ;
 */
var timeutil =
{
    Version: '1.0'
}

timeutil.TimeField = function(aElement) {

    aElement.onblur = check ;
    var theElement  = aElement;
    eventutil.addEventListener(aElement, "dblclick", function() {if(self.gfPop1){gfPop1.fPopCalendar($(aElement.id));}}) ;
	//var calA = document.createElement("SPAN") ;
	//calA.innerHTML="<a href=\"javascript:void(0)\" onclick=\"if(self.gfPop1){gfPop1.fPopCalendar($('"+aElement.id+"'));}return false;\">...</a>" ;
    //	calA.id = 'spanA'+aElement.id;
    	
    //aElement.parentNode.appendChild(calA) ;
    var theFieldError = null ;

    function liveCheck(aEvent) {
        var keyCode = aEvent.keyCode ;
        if(keyCode == eventutil.VK_BACKSPACE) {

        } else {
            var size = theElement.value.length ;
            if(size==2) {
                theElement.value = theElement.value + ":" ;
            } else if(size>5) {
                theElement.value = theElement.value.substring(0,5) ;
            }
        }
    }

    eventutil.addEventListener(aElement, "keydown", liveCheck) ;
    eventutil.addEventListener(aElement, "keyup", liveCheck) ;

    function check(aEvent)  {

            theFieldError = errorutil.HideError(theElement) ;
            if(theElement.value=="") {
                return ;
            }
        
            var ar = theElement.value.split(":") ;
            if(ar.length<2) {
                errorutil.ShowFieldError(theElement, "Неправильно введено время") ;
            } else {
                var hour = 0 ;
                var minute = 0 ;
                try {
                    hour = parseInt(ar[0],10) ;

                    if(hour<0 || hour>23) {
                        errorutil.ShowFieldError(theElement, "Неправильно введены часы") ;
                    }
                    try {
                        minute = parseInt(ar[1],10) ;
                        if(minute<0 || minute>59) {
                            errorutil.ShowFieldError(theElement, "Неправильно введены минуты") ;
                        } else {
                            theElement.value = format(hour) +":"+format(minute) ;
                        }
                    } catch (e) {
                        errorutil.ShowFieldError(theElement, "Неправильно введены минуты") ;
                    }
                } catch (e) {
                    errorutil.ShowFieldError(theElement, "Неправильно введены часы") ;
                }
            }
    }

    function format(aNumber) {

        var str = (!isNaN(aNumber)?aNumber:"00")+"" ;
        for(var i=0; str.length<2;i++) {
            str = "0"+str ;
        }
        return str ;
    }

}