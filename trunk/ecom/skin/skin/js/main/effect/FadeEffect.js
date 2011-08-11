/**
*
*/
msh.effect.FadeEffect = function() {
}

msh.effect.FadeEffect.putFade = function() {
    var div = $('fadeEffect') ;
    if (div == null) {
        div = document.createElement("div") ;
        div.id = "fadeEffect";

        document.body.insertBefore(div,document.body.firstChild);

//        document.body.appendChild(div) ;
    }
    if(div.style.zIndex==null) {
        div.style.zIndex = 0 ;
    }
    if(div.style.zIndex==1) {
        div.style.zIndex = 2 ;
    }
    div.style.zIndex++ ;
}

msh.effect.FadeEffect.getIndex = function() {
    var div = $('fadeEffect') ;
    if(div.style.zIndex==null) {
        div.style.zIndex = 0 ;
    }
    return div.style.zIndex ;
}

msh.effect.FadeEffect.pushFade = function() {
    var div = $('fadeEffect') ;
    if(div!=null) {
        if(div.style.zIndex==null) {
            div.style.zIndex = 0 ;
        }
        if(div.style.zIndex==2) {
            div.style.zIndex = 1 ;
        }

        div.style.zIndex-- ;
        if(div.style.zIndex==0) {
            div.parentNode.removeChild(div) ;
        }
    }
}