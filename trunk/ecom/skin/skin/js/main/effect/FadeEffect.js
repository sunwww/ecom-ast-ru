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
        var ua = navigator.userAgent.toLowerCase();
        var isOpera = (ua.indexOf('opera')  > -1);
        var isIE = (!isOpera && ua.indexOf('msie') > -1);
        var height= Math.max(document.compatMode != 'CSS1Compat' ? document.body.scrollHeight : document.documentElement.scrollHeight, 

        (((document.compatMode || isIE) && !isOpera) ? (document.compatMode == 'CSS1Compat') ? document.documentElement.clientHeight : document.body.clientHeight : (document.parentWindow || document.defaultView).innerHeight)
        );	 
        div.style.height = height+"px" ;
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
msh.effect.FadeEffect.pushFadeAll = function() {
    var div = $('fadeEffect') ;
    if(div!=null) {
        div.parentNode.removeChild(div) ;
    }
}
