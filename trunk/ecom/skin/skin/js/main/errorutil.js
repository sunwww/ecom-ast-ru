

var errorutil = {
    Version: '1.0'
}

errorutil.ShowFieldError = function (aElement, aErrorMessage) {
    var theElement  = aElement ;
    var theErrorMessage = aErrorMessage ;
    aElement.title = aErrorMessage ;
//    aElement.focus() ;
//    aElement.select();

    var errorDiv = document.createElement("DIV") ;
    errorDiv.innerHTML = aErrorMessage ;
    errorDiv.id = 'errorDiv';
    errorDiv.style.borderColor = "#F06" ;
    errorDiv.style.color = '#F06' ;
    aElement.parentNode.style.border = '2px solid #F06' ;
    aElement.parentNode.appendChild(errorDiv) ;

}

errorutil.HideError = function (aElement) {
    var theElement  = aElement ;
    aElement.parentNode.style.border = ''
    aElement.title = "";
    try {
        var aChild=aElement.parentNode.lastChild ;
        if (aChild.tagName == "DIV" && aChild.id == "errorDiv") aElement.parentNode.removeChild(aChild);
    } catch (e){
    }


}
// обработка исключительных ситуаций
errorutil.SetErrorObj = function (msg) {
    var err = new Error(msg) ;
    if (!err.message) {
        err.message = msg ;
    }
    return err ;

}

