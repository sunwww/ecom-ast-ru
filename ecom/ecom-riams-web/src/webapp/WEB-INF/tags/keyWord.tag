<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ attribute name="name" required="true" description="Наименование поля, к которому подключается обработчик ключевых слов" %>
<%@ attribute name="service" required="true" description="Сервис" %>
<%@ attribute name="methodService" required="true" description="Метод сервиса, к которому обращается тег"%>
<%@ attribute name="saveKeyWord" required="false" description="поле для сохранения ключевых слов"%>

<%--<msh:textArea property="record" label="Запись врача" horizontalFill="true" />--%>
<script type='text/javascript' src='./dwr/interface/KeyWordService.js'></script>
<script type="text/javascript">
    var texthiddenobr ;
//    var textkeyword = "record" ;
    var textkeyword = "${name}" ;
    var saveKeyWord = "${saveKeyWord}" ;

//    $(textkeyword).onkeypress = function (aEvent) {
    textkeywordonkeypress = function (aEvent) {
        var charCode ; // = aEvent.charCode
        if (navigator.appName=="Microsoft Internet Explorer") {
            charCode = aEvent.keyCode ;
        } else {
            charCode = aEvent.charCode ;
        }
        if (charCode==32) {
            texthiddenobr = $(textkeyword).value ;
        }
    }

//    $(textkeyword).onkeyup = function (aEvent) {
    textkeywordonkeyup = function (aEvent) {
        var keyCode = aEvent.keyCode ;
        if (keyCode==32) {
            var text = $(textkeyword).value ;
            var delim2 = getFocus($(textkeyword),text,texthiddenobr) ;
            var delim1 = text.substr(0,delim2-1).lastIndexOf(" ") ;
            var text1 = text.substring(0,delim1+1) ;
            var text2 = text.substring(delim1+1,delim2) ;
            var text3 = text.substring(delim2) ;
//            KeyWordService.getDecryption(text2, {
            ${service}.${methodService}(text2, {
                    callback: function(aString) {
                        if (aString!=null&&aString!="") {
                            var tempstring = text1+aString+" " ;
                            $(textkeyword).value = tempstring+text3 ;
                            if ($(saveKeyWord)!=null) {
                                if ($(saveKeyWord).value!="") $(saveKeyWord).value =  $(saveKeyWord).value + ", ";
                                $(saveKeyWord).value =  $(saveKeyWord).value + aString;

                            }
                            setFocus($(textkeyword),tempstring.length, 0)
                        }

                    }
            })

        }
    }
    function getFocus(aElement, aText1,text2) {
        try {
            if (navigator.appName=="Netscape")
                return aElement.selectionStart ;
            if (navigator.appName=="Microsoft Internet Explorer") {
                var tr = aElement.document.selection.createRange();
                var tr2 = tr.duplicate();
                tr2.moveToElementText(aElement);
                tr.setEndPoint('EndToStart', tr2);
                return tr.text.length;
            }
        } catch(e) {
            var delim1 = -1 ;
            var delim2 = -1 ;
            if (aText1.length>aText2.length) {
                while (delim1 == delim2){
                    delim1 = aText1.indexOf(" ", delim1+1) ;
                    delim2 = aText2.indexOf(" ", delim2+1) ;
                }
            }
            return delim1 ;
        }

    }
    function setFocus(aElement, aPosition, aLength) {
        try {
            if (navigator.appName=="Netscape")
                aElement.setSelectionRange(aPosition, aPosition + aLength);
            if (navigator.appName=="Microsoft Internet Explorer") {
                var tr = aElement.createTextRange();
                tr.collapse(true);
                tr.moveStart('character', aPosition);
                tr.moveEnd('character', aLength);
                tr.select();
            }
        } catch(e) {
        }
    }


    eventutil.addEventListener($(textkeyword), "keypress",textkeywordonkeypress )
    eventutil.addEventListener($(textkeyword), "keyup",textkeywordonkeyup )

</script>