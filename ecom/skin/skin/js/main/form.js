
var theDefaultTimeOut ;
var theDefaultTimeOutCnt=4 ;
var theDefaultFieldName ;
var theDefaultEvt ; 
function adjustMessage(text,aDiv) {
	var MessageObj = document.getElementById(aDiv);
	var isThisMozilla = true ;
	if (isThisMozilla) {var event=theDefaultEvt;}
	var scroll = getScrollXY() ;
	MessageObj.innerHTML = text;
	if (aDiv=="divInstantMessage") {
		MessageObj.style.top = (scroll[1]+50)+'px' ;
		MessageObj.style.left = '100px'
		MessageObj.style.visibility = "visible";
	}
}
function getScrollXY() {
  var scrOfX = 0, scrOfY = 0;
  if( typeof( window.pageYOffset ) == 'number' ) {
    scrOfY = window.pageYOffset;
    scrOfX = window.pageXOffset;
  } else if( document.body && ( document.body.scrollLeft || document.body.scrollTop ) ) {
    scrOfY = document.body.scrollTop;
    scrOfX = document.body.scrollLeft;
  } else if( document.documentElement && ( document.documentElement.scrollLeft || document.documentElement.scrollTop ) ) {
     scrOfY = document.documentElement.scrollTop;
    scrOfX = document.documentElement.scrollLeft;
  }
  return [ scrOfX, scrOfY ];
}
function getDefinition(term,evt,aDiv){
	if (aDiv==null||aDiv=="") aDiv="divInstantMessage" ;
	
	var MessageObj=document.getElementById(aDiv);
	MessageObj.innerHTML = "Загрузка..." ;
	var param = term.split("?") ;
	var query = param[1] ;
        mshaDoRequest(param[0], query, function () {onResponse1(aDiv)});
    //theDefaultEvt = evt ;
	
	
	return false;
}

function goToPage(aPage,aId,aTableCell) {
	//if (aTableCell==null) aTableCell="" ;
    if (aPage.indexOf('javascript:')!=-1) {
    	
    	var func = aPage.split("javascript:")[1] ;
    	if (func.indexOf('void(0)')>-1) {
    		return ;
    	} else {
	    	if (func.indexOf('(')==-1) {
	    		func = func + '(';
	    	} else if (!func.lastIndexOf(',')) {
	    		func = func + "," ;
	    	} 
	    	event(func+"'"+aId+"','"+aTableCell+"')") ;
	    	alert(func+"'"+aId+"'"+")");
    	}
    } else {
    	var delim = aPage.indexOf('?')==-1 ? '?' : '&' ;
    	var url = aPage+delim+'id='+aId+'&tmp='+Math.random() ;
    	if (aTableCell==null) {
            window.location = url ;
    	} else {
    		getDefinition(url+aTableCell+"&short=Short") ;
    	}
    }
}
function onResponse1(aDiv) {
	
    var aResponse = theMshaHttpRequest;
    if (theMshaHttpRequest.readyState == 4) {
        //alert(theMshaHttpRequest.status) ;
        var MessageObj = document.getElementById(aDiv);
        theIsSearching = false;
        if (theMshaHttpRequest.status == 200) {
            
            //MessageObj.innerHTML = ;
            
            adjustMessage(aResponse.responseText,aDiv);
        } else {
        	adjustMessage(aResponse.status + " " + aResponse.statusText+" "+aResponse.responseText,aDiv);
        }
    } else {

    }
}

function hideMessage(){
	var MessageObj=document.getElementById('divInstantMessage');
	MessageObj.style.visibility="hidden";
	MessageObj.innerHTML = "" ;
}
var funcemergencymessage = {
		func: function() {
			clearTimeout(theDefaultTimeOut) ;
			VocService.getEmergencyMessages( {
		        callback: function(aName) {
		        	
		        	viewEmergencyUserMessage(aName) ;
		        	
		        }
		    } ) ;
		}
}
function viewEmergencyUserMessage(aJsonId) {
	//alert(aJsonId) ;
	var fldJson = JSON.parse(aJsonId) ;
	//alert(fldJson) ;
	var cnt = fldJson.params.length ;
	var txt="";var ids = "" ;
	//alert(cnt) ;
	if (cnt>0) {
	    for (var ind=0;ind<cnt;ind++) {
	    	var param = fldJson.params[ind] ;
	    	txt += param.messageTitle+" "+param.messageText+" от "+param.infoReceipt+".\n" ;
	    	if (ids!="") ids += "," ; ids+=param.id ;
	    }
	    
	    VocService.checkEmergencyMessages(ids,txt, {
	        callback: function(aName) {
	        	alert(txt) ;
	        	--theDefaultTimeOutCnt ;
	        	if (theDefaultTimeOutCnt>0) theDefaultTimeOut = setTimeout(funcemergencymessage.func,180000) ;
	        }}
	    ) ;
	    
	}
}
function hideUserMessage(aId) {
	VocService.hiddenMessage(aId, {
        callback: function(aName) {
        	if ($("userMessagePop"+aId)) $("userMessagePop"+aId).style.display="none";
        }
    } ) ;

}
function checkUserMessage(aId) {
	VocService.checkMessage(aId, {
        callback: function(aName) {
        	if ($("userMessageContainer"+aId)) $("userMessageContainer"+aId).style.display="none";
        },
        errorHandler:function(message) {
        	alert("Ошибка при обработке сообщения!") ;
        },
        warningHandler:function(message) {
        	alert("Ошибка при обработке сообщения!") ;
        }
    } ) ;
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
