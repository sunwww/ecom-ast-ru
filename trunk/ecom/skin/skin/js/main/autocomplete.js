var theTimeout = null ;

function error(aHtml) {
	try {
		console.error("AutocompleteError!") ;
	} catch (e) {
	}
    //var w = window.open(""
    //        , ""
    //        , "height=500,width=600,scrollbars=yes") ;
    //w.document.write(aHtml);
}

var msh_autocomplete = {
    Version: '1.0'
}

var theMshaHttpRequest ;
var theIsSearching = false ;

// on !IE we only have to initialize it once
if (window.XMLHttpRequest) {
    theMshaHttpRequest = new XMLHttpRequest();
}

function mshaGetElementsByClassName(aElement, className) {
    var children = element.getElementsByTagName('*') ;
    var elements = new Array();

    for (var i = 0; i < children.length; i++) {
        var child = children[i];
        var classNames = child.className.split(' ');
        for (var j = 0; j < classNames.length; j++) {
            if (classNames[j] == className) {
                elements.push(child);
                break;
            }
        }
    }

    return elements;
}


function mshaDoRequestSync(aUrl, aQuery) {
    if (window.XMLHttpRequest) {
        theMshaHttpRequest = new XMLHttpRequest();
    } else if (window.ActiveXObject) { // branch for IE/Windows ActiveX version
        theMshaHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    theMshaHttpRequest.open("POST", aUrl, false);
    theMshaHttpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    theMshaHttpRequest.send(aQuery);
}


function mshaDoRequest(aUrl, aQuery, aCallback) {
    // old
//    try {
//        theMshaHttpRequest.abort() ;
//    } catch (e) {}
    if (theIsSearching) return;
    theIsSearching = true;
    // end old

    //    if(theMshaHttpRequest!=null && theMshaHttpRequest.readyState!=0) {
    //        theMshaHttpRequest.abort() ;
    //    }

    if (window.XMLHttpRequest) {
        theMshaHttpRequest = new XMLHttpRequest();
        //alert(window.XMLHttpRequest) ;
        // branch for IE/Windows ActiveX version
    } else if (window.ActiveXObject) {
        //alert("hello") ;
        theMshaHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    theMshaHttpRequest.onreadystatechange = aCallback;
    theMshaHttpRequest.open("POST", aUrl);
    theMshaHttpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
    theMshaHttpRequest.send(aQuery);
}


function debug(aMessage) {
    $('debug').value = $('debug').value + "\n" + aMessage;
}

/*
 * Представление
 */
msh_autocomplete.View = function(aElement, aDiv) {

    var theElement = aElement ;
    var theDiv = aDiv ;
    var theLastSelectedIndex = 0 ;
    ///////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    //
    this.setEditabled = function(aEditabled) {
        if(aEditabled) {
            Element.addClassName(theElement, "vocEditabled");
            if(theElement.title==null) {
                theElement.title="" ;
            }
            theElement.title = theElement.title + "  Добавить - [ALT+INSERT], изменить - [F2]" ;
        }
    }

    /*
    *  Создание падающего списка
    */
    this.showBox = function(aXml) {
        var div = document.createElement("div") ;
        div.className = "autocomplete";
        //div.setAttribute("class", "autocomplete") ;
        var ul = document.createElement("ul") ;
        var rows = aXml.getElementsByTagName("row") ;
        for (var i = 0; i < rows.length; i++) {
            var li = createLineElement(rows[i]) ;
            ul.appendChild(li);
        }
        div.appendChild(ul);
        theDiv.innerHTML = "";
        theDiv.appendChild(div);
        setSelected(0);
        theDiv.style.visibility = 'visible';
        theDiv.style.display = 'block';
    }

    this.setSearching = function (aSearching) {
        if (aSearching) {
            Element.addClassName(theElement, "searching");

        } else {
            Element.removeClassName(theElement, "searching");
        }
    }

    this.hide = function() {
        theDiv.innerHTML = "";
        theDiv.style.visibility = 'hidden';
        theDiv.style.display = 'none';
    }

    this.getLastId = function() {
        var lis = theDiv.getElementsByTagName("li") ;
        var li = lis[lis.length - 1] ;
        return getValue(li, "span");
    }

    this.getFirstId = function() {
        var lis = theDiv.getElementsByTagName("li") ;
        var li = lis[0] ;
        return getValue(li, "span");
    }

    this.selectNext = function() {
        return setSelected(theLastSelectedIndex + 1);
    }

    this.selectPrevious = function() {
        return setSelected(theLastSelectedIndex - 1);
    }

    this.getSelectedName = function() {
        return getSelectedBySpanClass('name');
    }

    this.getSelectedId = function() {
        return getSelectedBySpanClass('id');
    }

    function getSelectedBySpanClass(aSpanClass) {
        var lis = theDiv.getElementsByTagName("li") ;
        for (var i = 0; i < lis.length; i++) {
            if (lis[i].className == 'selected') {
                return getLiSpan(lis[i], aSpanClass);
            }
        }
        return null;
    }

    /* Если было выбрано - true */
    function setSelected(aIndex) {
        theLastSelectedIndex = aIndex;
        var lis = theDiv.getElementsByTagName("li") ;
        var selected = false ;
        for (var i = 0; i < lis.length; i++) {
            if (i == aIndex) {
                lis[i].className = "selected";
                selected = true;
            } else {
                lis[i].className = "";
            }
        }
        return selected;
    }

    ///////////////////////////////////////////////////////
    // PRIVATE FUNCTIONS
    //
    function createLineElement(aXmlRow) {
        var name = getValue(aXmlRow, 'name') ;
        var id = getValue(aXmlRow, 'id') ;

        var li = document.createElement("li") ;

        var idSpan = document.createElement("span") ;
        idSpan.className = "id";
        idSpan.appendChild(document.createTextNode(id));
        li.appendChild(idSpan);

        li.appendChild(document.createTextNode("    "));

        var nameSpan = document.createElement("span") ;
        nameSpan.className = "name";
        nameSpan.appendChild(document.createTextNode(name != "" ? name : " --- "));
        if (name == "") {
            nameSpan.className = 'name null';
        }
        li.appendChild(nameSpan);

        return li;
    }

    function getId(aParent) {
        return getLiSpan(aParent, 'id');
    }

    function getName(aParent) {
        return getLiSpan(aParent, 'name');
    }

    function getLiSpan(aParent, aSpanClass) {
        var elements = aParent.getElementsByTagName("span") ;
        for (i = 0; i < elements.length; i++) {
            var elm = elements[i] ;
            if (elm.className == aSpanClass) {
                return elm.firstChild.nodeValue;
            }
        }
        return null;
    }

    /*
     * Возвращает значение
     */
    function getValue(aParent, aNodeName) {
        var elements = aParent.getElementsByTagName(aNodeName) ;
        if (elements.length > 0 && elements[0].firstChild) {
            return elements[0].firstChild.nodeValue;
        } else {
            return "";
        }
    }
}


/*
* Реакция на события
*/
msh_autocomplete.Actions = function(aElement, aIdField, aView, aUrl, theVocKey, theVocTitle, theParent) {

    aElement.onblur = select;
    aElement.onfocus = onFocus ;

    eventutil.addEventListener(aElement, eventutil.EVENT_KEY_DOWN, onKey);
    eventutil.addEventListener(aElement, eventutil.EVENT_KEY_UP, onKeyUp);
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
    this.getParentId = function() {
        return theParentId  ;
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

    /*
    *  Начать поиск
    */
    function onKey(aEvent) {
        theCanShow = true ;
        //alert("onKeyDown"+aEvent) ;
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
//                if(eventutil.VK_PAGE_DOWN==keyCode) {
//                    findNext() ;
//                } if (40 == keyCode) { // KEY_DOWN
//                    if (! theView.selectNext()) {
//                        findNext();
//                    }
//                } else if (38 == keyCode) { // KEY_UP
//                    if (! theView.selectPrevious()) {
//                        findPrevious();
//                    }
//                } else if (13 == keyCode || 9 == keyCode) { // ENTER
//                    select();
//                } else {
//                    //alert(keyCode) ;
//                }
            }
        }
    }

    function findNextFromFirst() {
        var query = 'id='+createParentQuery() ;
        mshaDoRequest(theUrl, query, onResponse);
    }

    function findNext() {
//        alert(theParent) ;
        var query = 'id=' + theView.getLastId()+createParentQuery() ;
        mshaDoRequest(theUrl, query, onResponse);
    }

    function findPrevious() {
        mshaDoRequest(theUrl, 'direction=backward&id=' + theView.getFirstId()+createParentQuery(), onResponse);
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
        mshaDoRequest(theUrl, 'id=' + theIdField.value+createParentQuery(), onResponse);
    }

   /*
    * Завершение поиска
    */
    function onResponse() {
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
        return theIsShow;
    }

    function setBoxShowed(aShowed) {
        theIsShow = aShowed;
    }


}

msh_autocomplete.Autocomplete = function() {
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
    this.getParentId = function() {
        return theAction.getParentId() ;
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

        var view = new msh.widget.AutocompleteTableView(theNameField, theDiv) ;//new msh_autocomplete.View(theNameField, theDiv) ;
        theAction = new msh_autocomplete.Actions(theNameField, theIdField, view, theUrl, theVocKey, theVocTitle, theParent) ;
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

function showAutocompleteWindow(aIdField, aNameField, aSelectedId, aVocName) {
    //    window.open("autocompleteWindow.do?idField="+aIdField+"&nameField="+aNameField
    //                +"&selectedId="+aSelectedId
    //                +"&vocName="+aVocName
    //            ,"Выбор из справочника"
    //            , "height=500,width=400,scrollbars=yes");
    window.open("autocompleteWindow.do?idField=" + aIdField + "&nameField=" + aNameField
            + "&selectedId=" + aSelectedId
            + "&vocName=" + aVocName
            , ""
            , "height=500,width=400,scrollbars=yes");
}

function showAutocomplete(aNameField, aIdField, aUrl) {
    alert(aNameField);
}
