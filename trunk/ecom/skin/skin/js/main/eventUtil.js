var eventutil = {
    Version: '1.0'
    , EVENT_BLUR: 'blur'
    , EVENT_KEY_PRESS: 'keypress'
    , EVENT_KEY_UP: 'keyup'
    , EVENT_KEY_DOWN: 'keydown'
    , EVENT_CLICK: 'click'
    , EVENT_MOUSE_OVER: 'mouseover'
    , EVENT_MOUSE_OUT: 'mouseout'

    , VK_BACKSPACE: 8
    , VK_TAB: 9
    , VK_ENTER: 13
    , VK_INSERT: 45
    , VK_BACK_QUOTE: 192
    , VK_SPACE: 32
    , VK_ESCAPE: 27
    , VK_PAGE_DOWN: 34
    , VK_PAGE_UP: 33
    , VK_HOME: 36
    , VK_LEFT: 37
    , VK_UP: 38
    , VK_RIGHT: 39
    , VK_DOWN: 40
    , VK_DEL: 46
    , VK_0: 48
    , VK_1: 49
    , VK_2: 50
    , VK_3: 51
    , VK_4: 52
    , VK_5: 53
    , VK_6: 54
    , VK_7: 55
    , VK_8: 56
    , VK_9: 57

    , VK_A: 65
    , VK_B: 66
    , VK_C: 67
    , VK_D: 68

    , VK_N: 78

    , VK_F12: 123

}

EnterSupporter = function(aCurField, aNextElement) {

    this.select = function(aEvent) {
        var keyCode = aEvent.keyCode ;
//        alert(keyCode) ;
        if(aEvent.shiftKey && eventutil.isKey(aEvent, eventutil.VK_ESCAPE)) {
        //if(aEvent.ctrlKey && eventutil.isKey(aEvent, eventutil.VK_BACK_QUOTE)) {
            $('cancelButton').click() ;
        } else if(aEvent.ctrlKey && eventutil.isKey(aEvent, eventutil.VK_ENTER)){
            $('submitButton').click() ;
        } else if(eventutil.isKey(aEvent, eventutil.VK_ENTER)) {
            aCurField.blur() ;
            try {
                aNextElement.focus() ;
                aNextElement.select() ;
                return false ;
            } catch (e) {}

        }
    }
}

eventutil.addEventListener = function(aElement, aEventName, aListenerFunction) {
    try {
        aElement.attachEvent('on' + aEventName, aListenerFunction);
    } catch (e) {
        try {
            aElement.addEventListener(aEventName, aListenerFunction, false);
        } catch (e) {
            //alert(e);
        }
    }
}

eventutil.removeEventListener = function(aElement, aEventName, aListenerFunction) {
    try {
        aElement.detachEvent('on' + aEventName, aListenerFunction);
    } catch (e) {
        try {
            //alert("removing .."+aEventName) ;
            alert(aListenerFunction) ;
            aElement.removeEventListener(aEventName, aListenerFunction, true);
        } catch (e) {
            alert(e);
        }
    }
}

eventutil.addObserveListener = function(aElement, aEventName, aListenerFunction) {
    Event.observe(aElement, aEventName, aListenerFunction, false);
}

eventutil.addEnterSupport = function(aCurrentElementName, aNextElementName) {
	try {
    var curElm = $(aCurrentElementName) ;
    var nextElm = $(aNextElementName) ;
    if(curElm!=null && nextElm!=null) {
        eventutil.addEventListener(curElm, "keyup", new EnterSupporter(curElm, nextElm).select) ;
        curElm.setAttribute("autocomplete", "off");
        nextElm.setAttribute("autocomplete", "off");
    }
    } catch(e) {
    }
}

eventutil.isKey = function(aEvent, aKeyVk) {
    return aEvent.keyCode == aKeyVk ;
}
