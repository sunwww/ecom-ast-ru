
var theMainFormDataInstance = null ;

msh.util.FormData = function() {
    var theInitValues = null ;
    var theForm = null ;

    this.init = function(aForm) {
        if(aForm==null) alert("Нет формы") ;
        if(theInitValues==null) {
            theInitValues = Form.serialize(aForm) ;
            theForm = aForm ;
        } else {
            alert("msh.util.FormData: Форма уже проинициализирована") ;
        }
    }

    this.isChanged = function() {
        return theInitValues!=Form.serialize(theForm) ;
    }

    this.isChangedForLink = function() {
        if(theForm==null) return true ;
        var changed = theInitValues!=Form.serialize(theForm) ;
        if(changed) {
            //alert(theInitValues+"\n"+Form.serialize(theForm)) ;
//            var w = window.open(""
//                    , ""
//                    , "height=500,width=600,scrollbars=yes") ;
//
//            w.document.write(theInitValues+"<br>"+Form.serialize(theForm));

            return confirm("Данные в форме изменились. Продолжить без сохранения данных?") ;
        }
        return !changed ;
    }
}

msh.util.FormData.getInstance = function() {
    if(theMainFormDataInstance==null) {
        theMainFormDataInstance = new msh.util.FormData() ;
    }
    return theMainFormDataInstance ;
}

