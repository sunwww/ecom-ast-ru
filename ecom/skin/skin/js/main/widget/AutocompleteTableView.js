/**
 * Вывод списка в виде таблицы
 */
msh.widget.AutocompleteTableView = function(theElement, theDiv) {
   // var srcEventElement;
    ///////////////////////////////////////////////////////
    // PUBLIC FUNCTIONS
    //
    /**
     * Установить поле редактируемым
     */
    this.setEditabled = function(aEditabled) {
        if(aEditabled) {
            Element.addClassName(theElement, "vocEditabled");
            if(theElement.title==null) {
                theElement.title="" ;
            }
            theElement.title = theElement.title + "  Добавить - [ALT+INSERT], изменить - [F2]" ;
        }
    }

    /**
     * Поиск
     */
    this.setSearching = function (aSearching) {
        if (aSearching) {
            Element.addClassName(theElement, "searching");

        } else {
            Element.removeClassName(theElement, "searching");
        }
    }

    /**
     * Выделить следующий
     */
    this.selectNext = function() {
        //return setSelected(theLastSelectedIndex + 1);
    }


    /*
    *  Создание падающего списка
    */
    this.showBox = function(aXml) {
        var id = getValue(aXml, "requestId") ;
//        alert(id) ;
        var table = document.createElement("table") ;
        table.setAttribute("id", "doc_table1");//doc_table + 1 - если по строке, 0 - прокрутить вверх, 2 - прокрутить вниз
        var tbody = document.createElement("tbody") ;
        var rows = aXml.getElementsByTagName("row") ;
        if(rows.length==0) {
            var tr = createTr(0, null,null) ;//если одна строка (когда пустой справочник), выводить не нужно кнопки
            tbody.appendChild(tr);
        } else {
            for (var i = 0; i < rows.length; i++) {
                var flag=(i===rows.length-1 && rows.length>1)? true:false;  //надо чтоб не всегда выводились кнопки прокрутки
                if (rows.length==1) flag=null; //чтобы не выводились при поиске
                var tr = createTr(i, rows[i],flag) ;
                tbody.appendChild(tr);
            }
        }
        table.appendChild(tbody) ;


        theDiv.style.display = 'block';
        theDiv.className='autocomplete' ;
        if(theElement.clientWidth) {
            theDiv.style.width = theElement.clientWidth+"px" ;
            table.style.width = theElement.clientWidth+"px" ;
        } else {
            theDiv.style.width = "30em" ;
            table.style.width = "30em" ;
        }
        theDiv.style.visibility = 'visible';

        theDiv.innerHTML = "";
        //document.body.appendChild(theDiv);
        theDiv.appendChild(table);
        if(!setSelectedId(id)) setSelected(0) ;

//        theDiv.innerHTML = "<table><tr><td>heradf</td><td>asdf</td></tr></table>";

    }
    this.hide = function() {
        theDiv.innerHTML = "";
        theDiv.style.visibility = 'hidden';
        theDiv.style.display = 'none';
    }

    this.selectNext = function() {
        return setSelected(theLastSelectedIndex + 1);
    }

    this.selectPrevious = function() {
        return setSelected(theLastSelectedIndex - 1);
    }

    function setSelectedId(aId) {
        var finded = false ;
        var lis = theDiv.getElementsByTagName("tr") ;
        for (var i = 0; i < lis.length; i++) {
            var tr = lis[i] ;
            if(aId==lis[i].myId) {
                setSelected(i) ;
                finded = true ;
                break ;
            }
        }
        return finded ;
    }

    this.getSelectedId = function() {
        var trs = theDiv.getElementsByTagName("tr") ;
        for (var i = 0; i < trs.length; i++) {
            if (trs[i].className == 'selected') {
                return trs[i].myId ;
            }
        }
        return null ;
    }

    this.getSelectedName = function() {
        var trs = theDiv.getElementsByTagName("tr") ;
        for (var i = 0; i < trs.length; i++) {
            if (trs[i].className == 'selected') {
                return trs[i].myName ;
            }
        }
        return null ;
    }

    this.getLastId = function() {
        var trs = theDiv.getElementsByTagName("tr") ;
        if(trs.length>0) {
            var tr = trs[trs.length - 1] ;
            return tr.myId ;
        } else {
            return "" ;
        }
    }

    this.getFirstId = function() {
        var trs = theDiv.getElementsByTagName("tr") ;
        var tr = trs[0] ;
        return tr.myId ;
    }

    /////////////////////////////////////////////////////////
    // PRIVATE
    //
    function setSelected(aIndex) {
        setSrcEventElement(1);
        theLastSelectedIndex = aIndex;
        var lis = theDiv.getElementsByTagName("tr") ;
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
    //проставить id в table
    function setSrcEventElement(id) {
        var table = document.getElementById("doc_table0");
        if (table!=null)  table.id="doc_table"+id;
        else {
            table = document.getElementById("doc_table1");
            if (table!=null)table.id="doc_table"+id;
            else {
                table = document.getElementById("doc_table2");
                if (table!=null) table.id="doc_table"+id;
            }
        }
    }

    function test123() {
        alert("hello") ;
    }

    function createTr(aIndex,aXmlRow,aFlag) {
        var name = getValue(aXmlRow, 'name') ;
        if(name=="") name="-" ; // НЕТ STAC-109
        var id = getValue(aXmlRow, 'id') ;

        var tr = document.createElement("tr") ;
        tr.myId = id ;
        tr.myName = name ;

        var tdId = document.createElement("td") ;
        var tdName = document.createElement("td") ;

        tdId.appendChild(document.createTextNode(id)) ;
        tdName.appendChild(document.createTextNode(name)) ;

        tdId.className = 'id' ;
        tdName.className = 'name' ;


        tr.appendChild(tdId) ;
        tr.appendChild(tdName) ;
        if (aFlag!=null) {
            if (aIndex==0 || aFlag) {
                var tdButton=document.createElement("td") ;
                tdButton.style.width="5px";
                tdButton.innerHTML=(aIndex===0)? "<input padding=\"0\" margin='0' width='100%' height='100%' type=\"button\" value=\"ʌ\">":"<input type=\"button\" value=\"v\">";
                tr.appendChild(tdButton) ;
                tdButton.onmousedown = (aIndex===0)? setSrcEventElement.bind(this,0) : setSrcEventElement.bind(this,2); //0 - вверх, 1 - выбор, 2 - вниз
            }
        }
        tdId.onmousedown = setSelected.bind(this, aIndex) ;
        tdName.onmousedown = setSelected.bind(this, aIndex) ;
        return tr ;
    }

    /*
    * Возвращает значение
    */
    function getValue(aParent, aNodeName) {
        if(aParent==null) return "";
        var elements = aParent.getElementsByTagName(aNodeName) ;
        if (elements.length > 0 && elements[0].firstChild) {
            return elements[0].firstChild.nodeValue;
        } else {
            return "";
        }
    }
}