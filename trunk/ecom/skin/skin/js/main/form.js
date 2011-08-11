

var theDefaultFieldName ;
var theDefaultEvt ; 
function adjustMessage(text) {
	var MessageObj = document.getElementById('divInstantMessage');
	var isThisMozilla = true ;
	if (isThisMozilla) {var event=theDefaultEvt;}
	var scroll = getScrollXY() ;
	var rightedge = document.body.clientWidth-event.clientX-scroll[0];
	var bottomedge = document.body.clientHeight-event.clientY-scroll[1];

	MessageObj.innerHTML = text;
	//	+document.body.scrollTop+' left '
	//	+event.clientY+'--'+document.body.scrollLeft+' ';	
	//MessageObj.style.left = (event.clientX-(MessageObj.offsetWidth/2)) +'px' ;
	//MessageObj.style.top = (event.clientY+10)+'px';
	if (rightedge < MessageObj.offsetWidth) {
		MessageObj.style.left = (scroll[0] + event.clientX - MessageObj.offsetWidth) +'px' ;
		
	} else {
		MessageObj.style.left = (scroll[0] + event.clientX)+'px' ;}
	//if (bottomedge < MessageObj.offsetHeight) {	
	//	MessageObj.style.top = (scroll[1] -10 + event.clientY - MessageObj.offsetHeight)+'px' ;
	//} else {
		//MessageObj.style.top = (scroll[1]+10+event.clientY)+'px' ;
		MessageObj.style.top = (scroll[1]+50)+'px' ;
		MessageObj.style.left = '100px'
	//}
	MessageObj.style.visibility = "visible";
}
function getScrollXY() {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    //Netscape compliant
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    //DOM compliant
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
    //IE6 standards compliant mode
    scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  return [ scrOfX, scrOfY ];
}
function getDefinition(term,evt){
	var MessageObj=document.getElementById('divInstantMessage');
	var param = term.split("?") ;
	//alert(param[1]) ;
	var query = param[1] ;
        mshaDoRequest(param[0], query, onResponse1);
    theDefaultEvt = evt ;
	
	MessageObj.innerHTML = "Загрузка..." ;
	return false;
}
function goToPage(aPage,aId) {
	var delim = aPage.indexOf('?')==-1 ? '?' : '&' ;
    var url = aPage+delim+'id='+aId+'&tmp='+Math.random() ;
    window.location = url ;
}
function onResponse1() {
	
    var aResponse = theMshaHttpRequest;
    if (theMshaHttpRequest.readyState == 4) {
        //alert(theMshaHttpRequest.status) ;
        var MessageObj = document.getElementById('divInstantMessage');
        theIsSearching = false;
        if (theMshaHttpRequest.status == 200) {
            
            //MessageObj.innerHTML = ;
            
            adjustMessage(aResponse.responseText);
        } else {
        	adjustMessage(aResponse.status + " " + aResponse.statusText+" "+aResponse.responseText);
        }
    } else {

    }
}

function hideMessage(){
	var MessageObj=document.getElementById('divInstantMessage');
	MessageObj.style.visibility="hidden";
	MessageObj.innerHTML = "" ;
}


function setFocusOnField(aFieldName) {
	theDefaultFieldName = aFieldName ;
	eventutil.addObserveListener(window, 'load', _zetFocusOnField) ;
}


function _zetFocusOnField() {
	try {
	   $(theDefaultFieldName).focus() ;
	} catch (e) {}
	try {
	   $(theDefaultFieldName).select() ;
	} catch (e) {}

}
