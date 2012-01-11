

var theDefaultFieldName ;
var theDefaultEvt ; 
function adjustMessage(text) {
	var MessageObj = document.getElementById('divInstantMessage');
	var isThisMozilla = true ;
	if (isThisMozilla) {var event=theDefaultEvt;}
	var scroll = getScrollXY() ;

	//var rightedge = document.body.clientWidth-clX-scroll[0];
	//var bottomedge = document.body.clientHeight-event.clientY-scroll[1];

	MessageObj.innerHTML = text;
	//	+document.body.scrollTop+' left '
	//	+event.clientY+'--'+document.body.scrollLeft+' ';	
	//MessageObj.style.left = (event.clientX-(MessageObj.offsetWidth/2)) +'px' ;
	//MessageObj.style.top = (event.clientY+10)+'px';
	//if (rightedge < MessageObj.offsetWidth) {
	//	MessageObj.style.left = (scroll[0] + clX - MessageObj.offsetWidth) +'px' ;
		
	//} else {
	//	MessageObj.style.left = (scroll[0] + clX)+'px' ;}
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
function getCheckedRadio(aFrm,aFieldName) {
	var radioGrp = aFrm[aFieldName];
	
	if (radioGrp) {
		if (radioGrp.length) {
			for(i=0; i < radioGrp.length; i++){
			  if (radioGrp[i].checked == true){
			    return radioGrp[i].value;
			  }
			}
		} else {
			if (radioGrp.checked == true){
				return radioGrp.value;
			}
		}
	}
	return "" ;
}
function getCheckedCheckBox(aFrm,aFieldName,aRazd) {
	var checkBoxGrp = aFrm.elements[aFieldName];
	var checkValue = ""
		if (checkBoxGrp) {
			if (checkBoxGrp.length) {
				for(i=0; i < checkBoxGrp.length; i++){
					if (checkBoxGrp[i].checked == true){
						checkValue = checkValue+aRazd+checkBoxGrp[i].value;
					}
				}
			} else {
				if (checkBoxGrp.checked == true){
					checkValue = checkValue+aRazd+checkBoxGrp.value;
				}
			}
			
		} 
	if (checkValue!="") {
		checkValue=checkValue.substring(aRazd.length) ;
	}
	return checkValue ;
}
function setCheckedAllRadio(aFrm,aFieldName,aCheck) {
	var radioGrp = aFrm[aFieldName];
	if (radioGrp) {
		if (radioGrp.length) {
			radioGrp[0].checked = aCheck;
		} else {
			radioGrp.checked = aCheck;
		}
	}
}

function setCheckedAllCheckBox(aFrm,aFieldName,aCheck) {
	var checkBoxGrp = aFrm.elements[aFieldName];
	if (checkBoxGrp) {
		if (checkBoxGrp.length) {
			for(i=0; i < checkBoxGrp.length; i++){
				  checkBoxGrp[i].checked = aCheck;
				}
		} else {
			checkBoxGrp.checked = aCheck;
		}
	}
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
function getCurrentDate() {
	var dt = new Date() ;
	return format2day(dt.getDate())+"."+format2day(dt.getMonth()+1)+"."+dt.getFullYear() ;
}
function format2day(aCnt) {
	if (aCnt>9) {
		return aCnt ;
	} else {
		return "0"+aCnt ;
	}
}
