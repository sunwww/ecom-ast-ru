
PrivateAccessKeyEvent = function(aKeyCode, aIsCtrl, aIsAlt, aIsShift) {
    this.theKeyCode = aKeyCode ;
    this.theIsCtrl  = aIsCtrl  ;
    this.theIsShift = aIsShift ;
    this.theIsAlt   = aIsAlt   ;

//    this.getKeyCode = function() {
//        return theKeyCode ;
//    }
}

function createSingleKey(aKeyCode) {
    return new PrivateAccessKeyEvent(aKeyCode , false, false, false)
}

function createCtrl(aKeyCode) {
    return new PrivateAccessKeyEvent(aKeyCode , true, false, false)
}

function createCtrlShift(aKeyCode) {
    return new PrivateAccessKeyEvent(aKeyCode , true, false, true)
}
function createShift(aKeyCode) {
    return new PrivateAccessKeyEvent(aKeyCode , false, false, true)
}
function createAlt(aKeyCode) {
    return new PrivateAccessKeyEvent(aKeyCode , false, true, false)
}

var accesskeyutil = {
    Version: '1.0'
    , ALT_0 :  createAlt(eventutil.VK_0)
    , ALT_1 :  createAlt(eventutil.VK_1)
    , ALT_2 :  createAlt(eventutil.VK_2)
    , ALT_3 :  createAlt(eventutil.VK_3)
    , ALT_4 :  createAlt(eventutil.VK_4)
    , ALT_5 :  createAlt(eventutil.VK_5)
    , ALT_6 :  createAlt(eventutil.VK_6)
    , ALT_7 :  createAlt(eventutil.VK_7)
    , ALT_8 :  createAlt(eventutil.VK_8)
    , ALT_9 :  createAlt(eventutil.VK_9)
    , ALT_A :  createAlt(eventutil.VK_A)
    , ALT_B :  createAlt(eventutil.VK_B)
    , ALT_C :  createAlt(eventutil.VK_C)
    , ALT_D :  createAlt(eventutil.VK_D)
    , ALT_N :  createAlt(eventutil.VK_N)
    , ALT_DEL :  createAlt(eventutil.VK_DEL)

    , CTRL_0: createCtrl(eventutil.VK_0)
    , CTRL_1: createCtrl(eventutil.VK_1)
    , CTRL_2: createCtrl(eventutil.VK_2)
    , CTRL_3: createCtrl(eventutil.VK_3)
    , CTRL_4: createCtrl(eventutil.VK_4)
    , CTRL_5: createCtrl(eventutil.VK_5)
    , CTRL_6: createCtrl(eventutil.VK_6)
    , CTRL_7: createCtrl(eventutil.VK_7)
    , CTRL_8: createCtrl(eventutil.VK_8)
    , CTRL_9: createCtrl(eventutil.VK_9)
    , CTRL_A: createCtrl(eventutil.VK_A)
    , CTRL_B: createCtrl(eventutil.VK_B)
    , CTRL_C: createCtrl(eventutil.VK_C)
    , CTRL_D: createCtrl(eventutil.VK_D)
    , CTRL_N: createCtrl(eventutil.VK_N)
    , CTRL_DEL: createCtrl(eventutil.VK_DEL)

    , SHIFT_0: createShift(eventutil.VK_0)
    , SHIFT_1: createShift(eventutil.VK_1)
    , SHIFT_2: createShift(eventutil.VK_2)
    , SHIFT_3: createShift(eventutil.VK_3)
    , SHIFT_4: createShift(eventutil.VK_4)
    , SHIFT_5: createShift(eventutil.VK_5)
    , SHIFT_6: createShift(eventutil.VK_6)
    , SHIFT_7: createShift(eventutil.VK_7)
    , SHIFT_8: createShift(eventutil.VK_8)
    , SHIFT_9: createShift(eventutil.VK_9)
    , SHIFT_A: createShift(eventutil.VK_A)
    , SHIFT_B: createShift(eventutil.VK_B)
    , SHIFT_C: createShift(eventutil.VK_C)
    , SHIFT_D: createShift(eventutil.VK_D)
    , SHIFT_N: createShift(eventutil.VK_N)
    , SHIFT_DEL: createShift(eventutil.VK_DEL)

    , SHIFT_CTRL_0: createCtrlShift(eventutil.VK_0)
    , SHIFT_CTRL_1: createCtrlShift(eventutil.VK_1)
    , SHIFT_CTRL_2: createCtrlShift(eventutil.VK_2)
    , SHIFT_CTRL_3: createCtrlShift(eventutil.VK_3)
    , SHIFT_CTRL_4: createCtrlShift(eventutil.VK_4)
    , SHIFT_CTRL_5: createCtrlShift(eventutil.VK_5)
    , SHIFT_CTRL_6: createCtrlShift(eventutil.VK_6)
    , SHIFT_CTRL_7: createCtrlShift(eventutil.VK_7)
    , SHIFT_CTRL_8: createCtrlShift(eventutil.VK_8)
    , SHIFT_CTRL_9: createCtrlShift(eventutil.VK_9)
    , SHIFT_CTRL_A: createCtrlShift(eventutil.VK_A)
    , SHIFT_CTRL_B: createCtrlShift(eventutil.VK_B)
    , SHIFT_CTRL_C: createCtrlShift(eventutil.VK_C)
    , SHIFT_CTRL_D: createCtrlShift(eventutil.VK_D)
    , SHIFT_CTRL_N: createCtrlShift(eventutil.VK_N)
    , SHIFT_CTRL_DEL: createCtrlShift(eventutil.VK_DEL)

    , F12 :  createSingleKey(eventutil.VK_F12)
}




accesskeyutil.AccessKeyListener = function (theHrefElement, theAccessKeyEvent) {

    this.onKey = function(aEvent) {
        var keyCode = aEvent.keyCode ;

//        alert(keyCode) ;
        if(keyCode==theAccessKeyEvent.theKeyCode
                && aEvent.ctrlKey==theAccessKeyEvent.theIsCtrl
                && aEvent.shiftKey==theAccessKeyEvent.theIsShift
                && aEvent.altKey==theAccessKeyEvent.theIsAlt) {
            theHrefElement.focus() ;
            if(theHrefElement.onclick) {
                var ok = theHrefElement.onclick() ;
                if(ok) {
                    window.location = theHrefElement.href ;
                }
            } else {
                window.location = theHrefElement.href ;
            }
            return false ;
        }
    }
}

accesskeyutil.registerKey = function (aHrefElement, aAccessKeyEvent) {
    eventutil.addEventListener(document, "keydown"
            , new accesskeyutil.AccessKeyListener(aHrefElement, aAccessKeyEvent).onKey) ;
}
