function onKeyPress(aEvent) {
    var evt = (aEvent) ? aEvent : window.event
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if(evt.ctrlKey && (charCode==13|| charCode==10)) { // CTRL+ENTER
        // 10 IE or 13 FF
        var button = $('defaultSaveButton') ;
        if(button) {
            button.click() ;
        }
    } else if(charCode==27 || ( (charCode==26 || charCode==122) && evt.ctrlKey)) { // ESC = 27
        // 26 IE or 122 FireFox
        var button = $('defaultCancelButton') ;
        if(button) {
            button.click() ;
        }
    }
}
document.onkeypress = onKeyPress ;
