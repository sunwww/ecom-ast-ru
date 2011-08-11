
var theTabs = new Array();
var theLastSelectedTabId = null ;


function getElementsByClassName(classname){
        var rl = new Array();
        var re = new RegExp('(^| )'+classname+'( |$)');
        var ael = document.getElementsByTagName('*');
        var op = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;
        if (document.all && !op) ael = document.all;
        for(i=0, j=0 ; i<ael.length ; i++) {
                if(re.test(ael[i].className)) {
                        rl[j]=ael[i];
                        j++;
                }
        }
        return rl;
}

function showTab3(aTabId) {
    Form.enable(document.forms[0]) ;
    Element.removeClassName($('tabbedPaneContent'), "preview") ;
   //$('tabbedPaneContent').className = 'form' ; //setAttribute('class', 'form') ;

   var elements = getElementsByClassName('tabPane');
   var maxWidth = 100 ;
   for (var i = 0; i < elements.length; i++) {
        $(elements[i].id).style.visibility='hidden' ;
        $(elements[i].id).style.position = 'absolute' ;
        if(maxWidth < elements[i].clientWidth) {
           maxWidth = elements[i].clientWidth ;
        }
        if(elements[i].firstChild.className=='previewHeader') {
            //alert(1) ;
            elements[i].removeChild(elements[i].firstChild) ;
        }
        Element.removeClassName($(elements[i].id+'Link'), "selected") ;
   }

   Element.addClassName($(aTabId+'Link'), "selected") ;
   $(aTabId).style.visibility = "visible" ;
//   $('tabbedPane').style.width = (maxWidth + 40) +"px" ;// $(aTabId).style.width +"px" ;
//   $('tabbedPaneFooter').style.width = (maxWidth + 40) +"px" ;// $(aTabId).style.width +"px" ;
   $('tabbedPaneContent').style.height = ($(aTabId).clientHeight + 20) + 'px' ;
   $('tabbedPaneFooter').style.visibility = "visible" ;
   $('tabbedPaneHeader').style.visibility = 'visible' ;
}

function showTab(aTabName) {
    // скрываем старую панель
    if(theLastSelectedTabId!=null) {
        theTabs[theLastSelectedTabId].style.visibility='hidden' ;
        theTabs[theLastSelectedTabId].style.display='none' ;
        // и снимаем выделение с вкладки
        Element.removeClassName(theLastSelectedTabId+'Link', "selected") ;
    }
    // показываем выбранную панель
    theTabs[aTabName].style.visibility='visible' ;
    theTabs[aTabName].style.display='block' ;
    theLastSelectedTabId = aTabName ;
    // и выделяем вкладку
    Element.addClassName(theLastSelectedTabId+'Link', "selected") ;
}

function showPreview() {
   $('tabbedPaneHeader').style.visibility = 'hidden' ;
   Element.addClassName($('tabbedPaneContent'), "preview") ;
    //.setAttribute("preview") ;
   Form.disable(document.forms[0]) ;
   var elements = getElementsByClassName('tabPane');

   for (var i = 0; i < elements.length; i++) {
     var div = document.createElement("div") ;
     Element.addClassName(div, "previewHeader") ;
     var h2 = document.createElement("h2") ;
     h2.appendChild(document.createTextNode(elements[i].title)) ;

     var a = document.createElement("a") ;
     a.setAttribute("href", "javascript:showTab('"+elements[i].id+"')") ;
     a.appendChild(document.createTextNode("[Изменить]")) ;


     div.appendChild(h2) ;
     div.appendChild(a) ;
     elements[i].appendChild(div) ;

     elements[i].insertBefore(div, elements[i].firstChild) ;
     $('tabbedPaneHeader').style.visibility = 'hidden' ;

   }
}

function tabbedPaneInit() {
	eventutil.addObserveListener(window, 'load', _tabbedPaneInit) ;
}

function onKey(aEvent) {
    var keyCode = aEvent.keyCode ;
    //alert(keyCode) ;
    if(keyCode==9) {
        showTab('tabMain') ;
        return false ;
    }
    return true ;
}

function _tabbedPaneInit() {
   var ul =  document.createElement("ul");
   var elements = getElementsByClassName('tabPane');
   var isFirstAlreadyShow = true ;
   var firstTabId = null ;
   for (var i = 0; i < elements.length; i++) {
        if(i==0) firstTabId = elements[i].id ;

        var li = document.createElement("li") ;
        var a  = document.createElement("a") ;
        a.appendChild(document.createTextNode(elements[i].title)) ;
        // elements[i].title = "" ;
        a.setAttribute("href", "javascript:showTab('"+elements[i].id+"')") ;
        a.setAttribute("id", elements[i].id+'Link') ;
        li.appendChild(a) ;
        ul.appendChild(li) ;

        theTabs[elements[i].id] = elements[i] ;

        //if(isFirstAlreadyShow) {
        //    showTab(elements[i].id) ;
        //    isFirstAlreadyShow = false ;
        //}
   }

   $("tabbedPaneHeader").innerHTML = ""  ;
   $("tabbedPaneHeader").appendChild(ul) ;
   $("tabbedPaneHeader").appendChild(document.createElement("hr")) ;
   $('tabbedPaneFooter').style.visibility = "visible" ;
   // showPreview() ;


   showTab(firstTabId) ;
	$(theDefaultFieldName).focus() ;
	$(theDefaultFieldName).select() ;
   setFocusOnField(theDefaultFieldName) ;
}
