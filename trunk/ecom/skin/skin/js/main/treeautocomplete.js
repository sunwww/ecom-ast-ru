var ecom_tree_autocomplete = {
    Version: '0.0'
}

/*
* Реакция на события
*/
ecom_tree_autocomplete.Actions = function(aElement, aIdField, aView, aUrl, theVocKey, theVocTitle, theParent) {

    aElement.onblur = select;
    aElement.onfocus = onFocus ;
    
    eventutil.addEventListener(aElement, eventutil.EVENT_KEY_DOWN, onKey);
    //eventutil.addEventListener(aElement, eventutil.EVENT_KEY_UP, onKeyUp);
    eventutil.addEventListener(aElement, eventutil.EVENT_CLICK, onMouseClick);

    var theUrl = aUrl ;
    var theView = aView ;
    var theIsShow = false ;
    var theElement = aElement ;  // поле со значением
    var theLastText = theElement.value ;
    var theIdField = aIdField ; // поле с идентификатором
    var theVocValueEdit = null ;
    var theOnChangeCallback ;
    var theShowIdInName = false ;
    var theParentId = null ;
    var theCanShow = true ;

    function onFocus() {
        theCanShow = true ;
    }

    this.setVocKey = function(aVocKey) {
        theVocKey = aVocKey ;
        this.setVocId(theIdField.value) ;
    }

    this.setParentId = function(aParentId) {
        theParentId = aParentId ;
    }

    this.setUrl = function(aUrl) {
        theUrl = aUrl ;
        this.setVocId(theIdField.value) ;
    }

    this.setShowIdInName = function(aShow) {
        theShowIdInName = aShow ;
    }

    this.setParent = function(aParent) {
        theParent = aParent ;
    }

    this.addOnChangeCallback = function(aFunction) {
        theOnChangeCallback = aFunction ;
    }

    this.clearValuesWithNoEvents = function() {
        theElement.value = "" ;
        theIdField.value = "" ;
        if(theOnChangeCallback) theOnChangeCallback() ;
    }

    ///////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    //
    function getVocValueEdit() {
        if(theVocValueEdit==null) {
            theVocValueEdit = new msh.widget.VocValueEdit(null
                    , theVocKey, theVocTitle, {valueChanged: onNewValue}) ;
        }
        return theVocValueEdit ;
    }
    this.setVocId = function(aId) {
        theView.setSearching(true);
        VocService.getNameById(theVocKey, aId, theParentId, {
            callback: function(aName) {
                theIdField.value = aId ;
                theElement.value = aName ;
                theView.setSearching(false);
            },
            errorHandler:function(message) {
                theElement.value = message ;
                theView.setSearching(false);
            },
            warningHandler:function(message) {
                theElement.value = message ;
                theView.setSearching(false);
            }
        } ) ;


//        theIdField.value = aId ;
//        mshaDoRequestSync(theUrl, 'id=' + aId);
//        var aResponse = theMshaHttpRequest;
//        if (theMshaHttpRequest.status == 200) {
//            var names = aResponse.responseXML.getElementsByTagName("name") ;
//            if(names && names.length>0 && names[0].firstChild) {
//
//                theElement.value = names[0].firstChild.nodeValue ;
//            }
//        } else {
//            alert(aResponse.status + " " + aResponse.statusText);
//            error(aResponse.responseText);
//            throw new Exception(aResponse.responseText) ;
//        }
//        theView.setSearching(false);
    }

    function onVocIdResponse() {
        aResponse = theMshaHttpRequest;

        if (theMshaHttpRequest.readyState == 4) {

            if (theMshaHttpRequest.status == 200) {
                alert(aResponse.responseText) ;
            } else {
                alert(aResponse.status + " " + aResponse.statusText);
                error(aResponse.responseText);
            }
        } else {

        }
    }

    function onNewValue(aId, aName) {
        theElement.value = aName ;
        theIdField.value = aId ;
        //theLastText = theElement.value;
        theElement.focus() ;
        //theElement.select() ;
        findFromEnteredId() ;
        //findNext() ;
        //findFromEnteredId() ;
        //setBoxShowed(true);
    }

    function selectEmpty() {
        var canSendChangeEvent = false ;
        if (isBoxShowed()) {
            canSendChangeEvent = theIdField.value!="" ;
            theElement.value = "";
            theIdField.value = "";
            theLastText = "";
        }
        setBoxShowed(false);
        theView.hide();
        if(canSendChangeEvent && theOnChangeCallback) theOnChangeCallback() ;
    }

    function select() {
    	document.title = "sel"+theView.getPage()+"_"+document.title ;
        if (theView.getPage()>0 ) {
        	if (isBoxShowed()) {
        	 ;
        	} else {
        	}
        	theCanShow = true ;
        	switch(theView.getPage()) {
        		case 1:
        			theView.setPage(0) ;
        			findPrevious() ;
        			break ;
        		case 2:
        			theView.setPage(0) ;
        			findNext() ;
        			break ;
        		
        	}
        	//theView.setSelected(0) ;
        	
        	return false ;
        } else {
	    	document.title = "select_"+document.title ;
	        theCanShow = false ;
	        var canSendChangeEvent = false ;
	        if (isBoxShowed()) {
	            var id = theView.getSelectedId() ;
	            var name = theView.getSelectedName() ;
	            if (name == null) name = "";
	            canSendChangeEvent = theIdField.value!=id ;
	            if(theShowIdInName) {
	                if(id!=null && id!="") {
	                    theElement.value = "("+id+") "+name;
	                } else {
	                    theElement.value = name ;
	                }
	            } else {
	                theElement.value = name;
	            }
	            theIdField.value = id;
	            theLastText = theElement.value;
	
	        }
	        setBoxShowed(false);
	        theView.hide();
	        if(canSendChangeEvent && theOnChangeCallback) theOnChangeCallback() ;
        }
    }



    function findParentId(aAutocomplete) {
        //alert("auto="+aAutocomplete) ;
        if(aAutocomplete==null  || aAutocomplete=="undefined") {
            return null ;
        } else {
            if(aAutocomplete.getVocId()==null || aAutocomplete.getVocId()=="" || aAutocomplete.getVocId()=="undefined") {
                return findParentId(aAutocomplete.getParent()) ;
            } else {
                //alert(aAutocomplete.getVocId()) ;
                return aAutocomplete.getVocId() ;
            }
        }
    }

    function onMouseClick(aEvent) {
    	theCanShow = true ;
    	
    	findFromEnteredId();
    	theElement.select() ;
    }
/*
    function onKeyUp(aEvent) {
        theCanShow = true ;
        //alert("onKeyUp"+aEvent) ;
        var keyCode = aEvent.keyCode ;
        if (13 == keyCode || 9 == keyCode) { // ENTER
            select();
        } else {
            if (theLastText != theElement.value) {
                theLastText = theElement.value;
                findQuery();
            }
        }
    }
*/
    /*
    *  Начать поиск
    */
    function onKey(aEvent) {
        theCanShow = true ;
        
        
        
        document.title = "key_"+document.title ;
        
        var keyCode = aEvent.keyCode ;
        if (keyCode == eventutil.VK_ESCAPE || keyCode == eventutil.VK_DEL) { // пропускаем ESCAPE
            return selectEmpty();
        } else if(aEvent.ctrlKey || aEvent.shiftKey) {
            return false ;
        } else if(aEvent.altKey && keyCode==eventutil.VK_INSERT) {
            //alert("finded: "+findParentId(theParent!=null ? theParent : null )) ;
            getVocValueEdit().insertNewValue(findParentId(theParent!=null ? theParent : null )) ;
            return true ;
        } else {
            if (!isBoxShowed() && keyCode==eventutil.VK_PAGE_DOWN) { // если нет на экране
//                if (13 != keyCode && 9 != keyCode) { // ENTER
                    findFromEnteredId();
//                }
            } else {
//                alert(keyCode) ;
                switch(keyCode) {
                    case eventutil.VK_PAGE_DOWN:
                        findNext() ;
                        break;
                    case eventutil.VK_PAGE_UP:
                        findPrevious() ;
                        break ;
                    case eventutil.VK_LEFT:
                    	alert("НАЗАД") ;
                    	break ;
                    case eventutil.VK_RIGHT:
                    	alert("ВПЕРЕД") ;
                    	break ;
                    case eventutil.VK_DOWN:
                        if (! theView.selectNext()) {
                            findNext();
                        }
                        break ;
                    case eventutil.VK_UP:
                        if (! theView.selectPrevious()) {
                            findPrevious();
                        }
                        break ;
                    case eventutil.VK_ENTER:
                    case eventutil.VK_TAB:
                        select();
                        break ;
                    case eventutil.VK_HOME:
                        mshaDoRequest(theUrl, "", onResponse);
                        break ;
                }
            }
        }
    }

    function findNextFromFirst() {
		document.title = "findNextFromFirst_"+document.title ;
        var query = 'id='+createParentQuery() ;
        mshaDoRequest(theUrl, query, onResponse);
    }

    function findNext() {
//        alert(theParent) ;
		document.title = "findNext_"+document.title ;
        var query = 'id=' + theView.getLastId()+createParentQuery() ;
        mshaDoRequest(theUrl, query, onResponse);
    }

    function findPrevious() {
		document.title = "findPrevious_"+document.title ;
        mshaDoRequest(theUrl, 'direction=backward&id=' + theView.getFirstId(), onResponse);
    }

    function __findQuery() {
        if (theTimeout) {
            window.clearTimeout(theTimeout);
        }
        theTimeout = window.setTimeout(_findQuery, 500);
    }

    function findQuery() {
        if(theElement.value=="") {
            findNextFromFirst() ;
        } else {
            theView.setSearching(true);
            mshaDoRequest(theUrl, 'query=' + theElement.value+createParentQuery(), onResponse);
        }
    }

    function createParentQuery() {
        var query = "" ;
        if(theParent!=null) {
            var parentId = theParent.getVocIdForParent() ;
            if(parentId!=null) {
                query = "&parent="+parentId ;
            }
        }
        if(query=="") {
            if(theParentId!=null) {
                query = "&parent="+theParentId ;
            }
        }
        return query ;
    }

    function findFromEnteredId() {
		document.title = "findFromEnteredId_"+document.title ;
        mshaDoRequest(theUrl, 'id=' + theIdField.value+createParentQuery(), onResponse);
    }

   /*
    * Завершение поиска
    */
    function onResponse() {
		document.title = "response_"+document.title ;
        aResponse = theMshaHttpRequest;
        if (theMshaHttpRequest.readyState == 4) {
            //alert(theMshaHttpRequest.status) ;

            theIsSearching = false;
            if (theMshaHttpRequest.status == 200) {
                if(theCanShow) {
                    theView.showBox(aResponse.responseXML);
    //                alert(aResponse.responseXML) ;
                    //alert(aResponse.responseXML) ;
                    setBoxShowed(true);
                }
                theView.setSearching(false);
            } else {
                alert(aResponse.status + " " + aResponse.statusText);
                error(aResponse.responseText);
            }
        } else {

        }
    }

    function isBoxShowed() {
		document.title = "isBoxShowed_"+document.title ;
        return theIsShow;
    }

    function setBoxShowed(aShowed) {
        theIsShow = aShowed;
    }


}

ecom_tree_autocomplete.Autocomplete = function() {
    var theNameField ;
    var theIdField ;
    var theDiv ;
    var theUrl ;
    var theAction = null ;
    var theVocKey ;
    var theVocTitle ;
    var theParent ;
    var theOnChangeCallback ;
    var theThis = this ;


    this.getParent = function() {
        return theParent ;
    }

    this.setVocTitle = function(aVocTitle) {
        theVocTitle = aVocTitle ;
    }

    this.setVocKey = function(aVocKey) {
        theVocKey = aVocKey ;
        if(theAction!=null) {
            theAction.setVocKey(aVocKey) ;
        }
    }

    this.setNameFieldId = function(aNameFieldId) {
        theNameField = $(aNameFieldId);
    }
    this.setIdFieldId = function (aIdFieldId) {
        theIdField = $(aIdFieldId);
    }
    this.setDivId = function (aDivId) {
        theDiv = $(aDivId);
    }

    this.setUrl = function (aUrl) {
        theUrl = aUrl;
        if(theAction!=null) {
            theAction.setUrl(aUrl) ;
        }
    }

    this.setParentId = function(aParentId) {
        theAction.setParentId(aParentId) ;
    }

    this.setParent = function(aParent) {
        theParent = aParent ;
        theAction.setParent(aParent) ;
        theParent.addOnChangeCallback(parentOnChange) ;
    }

    function parentOnChange() {
        //theAction.setVocId("") ;
        theAction.clearValuesWithNoEvents() ; //alert(theThis.getVocId())

    }

    this.addOnChangeCallback = function(aFunction) {
        theAction.addOnChangeCallback(aFunction) ;
        //theOnChangeCallback = aFunction ;
    }

    this.build = function () {
        var view = new msh.widget.TreeAutocompleteTableView(theNameField, theDiv) ;//new msh_autocomplete.View(theNameField, theDiv) ;
        theAction = new ecom_tree_autocomplete.Actions(theNameField, theIdField, view, theUrl, theVocKey, theVocTitle, theParent) ;
        try {
            theNameField.setAttribute("autocomplete", "off");
        } catch (e) {
        }
        // For minimize traffic check Editabled before insert or edit value
        //try {
        //    VocEditService.isVocEditabled(theVocKey, {
        //       callback: function(aEditabled) {
        //            if(aEditabled) view.setEditabled(true) ;
        //        }
        //    } ) ;
        //} catch (e) {
        //}
    }

    /* Получение фокуса*/
    this.requestFocus = function() {
    	alert("focus") ;
        theNameField.focus();
    }

    this.getVocIdForParent = function() {
        var ret = null ;
        var id = theIdField.value ;
        if(id==null || id=="") {
            if(theParent!=null) {
                ret = theParent.getVocIdForParent() ;
            }
        } else {
            ret = id ;
        }
        return ret ;
    }

    /**
    * Получение идентификатора из справочника
    */
    this.getVocId = function() {
        return theIdField.value ;
    }

    /**
    * Названия из справочника
    */
    this.getVocName = function() {
        return theNameField.value ;
    }

    this.setVocId = function(aId) {
        theAction.setVocId(aId) ;
    }

    this.setShowIdInName = function(aShow) {
        theAction.setShowIdInName(aShow) ;
    }
	
}

function showTreeAutocomplete(aNameField, aIdField, aUrl) {
    alert(aNameField);
}
