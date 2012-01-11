var tablearrow = {
    Version: '1.0'
}

var theTableArrow = null ;

tablearrow.TableArrow = function(aTableId) {

    var theTable = document ; //$(aTableId) ;
    var theRows  = null ;
    var theFirstSel = 0 ;
    // последний выбранный элемент
    var theLastSelectedIndex = null ;


    //document.onkey = hello ;

    //function hello(aEvent) {
    //	return false ;
    //}

    // конструктор
    theRows = getRows() ;
    if(theRows!=null && theRows.length!=0) {
        try { document.attachEvent('onkeydown',onKey); } catch (e) {
            try { document.addEventListener("keypress",onKey,false); } catch (e) {}
        }

        selectFirst() ;
    }
    
    this.onCheckBoxClickAll = function(aIdParamName) {
    	var check = aIdParamName.checked ;
    	aIdParamName.checked = false ;
    	var clazz = aIdParamName.id ;
    	var rows = getRows() ;
    	var atr ;
        for(var i=0; i<rows.length; i++) {
            var row = rows[i] ;
            
            if(row!=null && Element.hasClassName(row, clazz) ) {
	             if ((check && Element.hasClassName(row, "inserted"))
		            || (!check && !Element.hasClassName(row, "inserted"))) {
	            } else {
	            	insert(row) ;
	            }
            }
        }
        //aIdParamName.checked = false;
    }
    this.onCheckBoxClickInvert = function(aIdParamName) {
    	var check = aIdParamName.checked ;
     	aIdParamName.checked = false ;
    	var clazz = aIdParamName.id ;
    	var rows = getRows() ;
    	var atr ;
        for(var i=0; i<rows.length; i++) {
            var row = rows[i] ;
            
            if(row!=null && Element.hasClassName(row, clazz) ) {
	            	insert(row) ;
            }
        }
        //aIdParamName.checked = false;
    }

    /**
     * Получение строки параметров
     * Например: id=123&id=234
     * @return null если нет выделенных
     */
    this.getInsertedIdsAsParams = function(aIdParamName,aClass) {
        var ids = this.getInsertedIds(aClass) ;
        if (ids) {
            var str = "";
            for (var i = 0; i < ids.length; i++) {
            	if (ids[i]!="inserted") {
            		if (aIdParamName!=null && aIdParamName!="") {
    	                str += aIdParamName+"=" + ids[i];
    	                if (i != ids.length - 1) {
    	                    str += "&";
    	                }
            		} else {
    	                str += ids[i];
    	                if (i != ids.length - 1) {
    	                    str += ",";
    	                }
            		}
                }
            }
            return str ;
        } else {
            return null ;
        }
    }

    
    this.remove = function() {
        try { document.detachEvent('onkeydown',onKey); } catch (e) {
            try { document.removeEventListener("keypress",onKey,false); } catch (e) {}
        }
    }

    this.onCheckBoxClick = function(aCheckBox) {
        var row = aCheckBox.parentNode.parentNode ;
        insert(row) ;
    }

    //
    this.getInsertedIds = function (clazz) {
        var ret  = new Array();
        var rows = getRows() ;
        for(var i=0; i<rows.length; i++) {
            var row = rows[i] ;
            if(row!=null && (clazz==null||clazz=="" || Element.hasClassName(row, clazz)) && Element.hasClassName(row, "inserted")) {
                var tokens = row.className.split(' ');
                if(tokens.length>1) {
                    ret.push(tokens[1]) ;
                }
            } else {
            }
        }
        return ret.length!=0 ? ret : null ;
    }

    /** Все строки всех таблиц*/
    function getRows() {
        if(theRows==null) {
            theRows = theTable.getElementsByTagName("tr") ;
        }
        return theRows ;
    }

    /**
     * Выбрать строку в таблице
     */
    function select(aRow) {
        Element.addClassName(aRow, "selected") ;

    }

    function deselect(aRow) {
    	Element.removeClassName(aRow, "selected") ;
    }

    function insert(aRow) {
        var classes = aRow.className.split(' ');
        var checkBox = $(classes[0]+'_'+classes[1]) ;

        if(Element.hasClassName(aRow, "inserted")) {
            Element.removeClassName(aRow, "inserted") ;
            if(checkBox) checkBox.checked = false ;
        } else {
            Element.addClassName(aRow, "inserted") ;
            if(checkBox) checkBox.checked = true ;
        }
    }

    function insertCurrent() {
        var row = getSelectedRow() ;
        if(row) {
            insert(row) ;
        }
    }


    function getSelectedIndex() {
        if (theLastSelectedIndex == null) {
            var rows = getRows() ;
            for (var i = 0; i < rows.length; i++) {
                if (Element.hasClassName(rows[i], "selected")) {
                    theLastSelectedIndex = i ;
                    break ;
                }
            }
        }
        return theLastSelectedIndex;
    }

    function isUndefinedRowAction(aRow) {
    	try { return aRow.onclick == undefined } catch (e) {} 
    	try { return aRow.onclick == null } catch (e) {} 
    	return false ;
    }

    function setSelectedIndex(aIndex) {
        var rows  = getRows() ;
//        for (var i=0; i<rows.length; i++) {
//            deselect(rows[i]) ;
//         }
        deselect(rows[theLastSelectedIndex]) ;
        select(rows[aIndex]) ;
        theLastSelectedIndex = aIndex ;
    }

    function selectLast() {
        var rows = getRows() ;
        setSelectedIndex(rows.length-1) ;
    }

    function selectFirst() {
        var rows = getRows() ;
        theFirstSel = 0 ;
        for (var i=0; i<rows.length; i++) {
            if(!isUndefinedRowAction(rows[i])) {
                theFirstSel = i ;
                break ;
            }
        }
        setSelectedIndex(theFirstSel) ;
    }

    function getNextAllowedIndex(aIndex) {
        var rows = getRows() ;
        for (var i=aIndex; i<rows.length; i++) {
            if(!isUndefinedRowAction(rows[i])) {
                return i ;
            }
        }
        return null ;
    }

    function getPreviousAllowedIndex(aIndex) {
        var rows = getRows() ;
        for (var i=aIndex; i>=theFirstSel; i--) {
            if(!isUndefinedRowAction(rows[i])) {
                return i ;
            }
        }
        return null ;
    }

    function selectPageDown() {
        // TODO нажатие на кнопку вперед
        if(getSelectedIndex()!=getRows().length-1) {
             for(var i=0; i<10; i++) {
                selectNext() ;
                if(getSelectedIndex()==getRows().length-1) {
                    break ;
                }
             }
        }
    }

    function selectPageUp() {
        // TODO нажатие на кнопку назад
        if(getSelectedIndex()!=theFirstSel) {
             for(var i=0; i<10; i++) {
                selectPrevious() ;
                if(getSelectedIndex()==theFirstSel) {
                    break ;
                }
             }
        }
    }

    function selectNext() {
        var selIndex = getSelectedIndex() ;
        if(selIndex==null) {
            selectFirst() ;
        } else {
            var sel = getNextAllowedIndex(selIndex+1) ;
            if(sel!=null && sel < getRows().length) {
                setSelectedIndex(sel) ;
            } else {
                selectFirst() ;
            }
        }
    }

    function selectPrevious() {
        var selIndex = getSelectedIndex() ;
        if(selIndex==null) {
            selectLast() ;
        } else {
            var sel = getPreviousAllowedIndex(selIndex-1) ;
            if(sel!=null && sel>=theFirstSel) {
                setSelectedIndex(sel) ;
            } else {
                selectLast() ;
            }
        }
    }

    function getSelectedRow() {
        var sel = getSelectedIndex() ;
        if(sel!=null) {
            return getRows()[sel] ;
        } else {
            return null ;
        }
    }

    function enter() {
        var tr = getSelectedRow() ;
        if(tr!=null) {
            if(!isUndefinedRowAction(tr)) {
                try {
                	var size = tr.childNodes.length-2 ;
                	tr.childNodes[size].onclick() ;
                } catch (e) {
                    tr.childNodes[0].onclick() ;
                }
            }
        }
    }

    function onKey(aEvent) {
        var keyCode = aEvent.keyCode ;
        if(40 == keyCode) { // KEY_DOWN
            selectNext() ;
        } else if ( 38 == keyCode) { // KEY_UP
            selectPrevious() ;
        } else if ( eventutil.VK_INSERT == keyCode) {
            insertCurrent() ;
            selectNext() ;
        } else if( 13 == keyCode ) { // ENTER
            enter() ;
        } else if( 35 == keyCode ) { // END
            selectLast() ;
        } else if (36 == keyCode) { //  HOME
            selectFirst() ;
        } else if( 34 == keyCode ) { // PAGE DOWN
            selectPageDown() ;
        } else if (33 == keyCode) { //  PAGE UP
            selectPageUp() ;
        } else if(112 == keyCode) {
        	window.status = keyCode ;
	     return false ;
        }
        //alert(keyCode) ;
        return false ;

    }



}
