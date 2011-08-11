

msh.widget.TreeTableDialog = function(aName, aTitle, aControlObject) {
    this.theName = aName ;
    this.theDialog = null ;
    this.theTitle = aTitle ;
    this.theDialogId = aName + "Dialog" ;
    this.theDialog = null ;
    this.theRootPane = null ;
    this.theLastSearch = "" ;
    this.theData = null ;
    this.theGoFunction = null ;
    this.theObject = null ;
    this.theControlObject = aControlObject ;

    //
    this.thePageDown = this.createForwardTd() ;
    this.thePageUp = this.createBackTd() ;

    //
    this.init() ;
}


msh.widget.TreeTableDialog.prototype.init = function() {
    var div = document.createElement("div") ;
    div.id = this.theDialogId ;
    div.className = "dialog" ;

    var h2 = document.createElement("h2") ;
    h2.appendChild(document.createTextNode(this.theTitle)) ;

    var rootPane = document.createElement("div") ;
    rootPane.className = "rootPane treeTable" ;

    div.appendChild(h2) ;
    div.appendChild(rootPane) ;

    document.body.appendChild(div) ;
    this.theDialog = new msh.widget.Dialog(div)  ;
    this.theRootPane = rootPane ;

}

msh.widget.TreeTableDialog.prototype.setMessage = function(aMessage) {
    this.theRootPane.appendChild(document.createTextNode(aMessage)) ;
}
msh.widget.TreeTableDialog.prototype.show = function() {
    this.theDialog.show() ;
}

msh.widget.TreeTableDialog.prototype.updateContent = function(aData, aGoFunction, aObject) {
    this.theData = aData ;
    this.theGoFunction = aGoFunction ;
    this.theObject = aObject ;

    // create table
    var table = document.createElement("table") ;
    table.className = "tabview sel tableArrow" ;

    var tbody = document.createElement("tbody") ;
    table.appendChild(tbody) ;

    // header
    var headerRow = document.createElement("tr") ;
    tbody.appendChild(headerRow) ;
    for(i=0; i<aData.titles.length; i++) {
        var th = document.createElement("th") ;
        th.appendChild(document.createTextNode(aData.titles[i])) ;
        headerRow.appendChild(th) ;
    }
    // cells
    for(row=0; row<aData.rows.length; row++) {
        var tr = document.createElement("tr") ;
        tr.onclick = "" ;

        for(col=0; col<aData.rows[row].cols.length; col++) {
            var td = document.createElement("td") ;
            td.appendChild(document.createTextNode(aData.rows[row].cols[col])) ;
            td.onclick = aGoFunction.bind(aObject, null, aData.rows[row].id, true, null) ;
            tr.appendChild(td) ;
        }
        if(row==0) { // back
            tr.appendChild(this.thePageUp) ;
        }
        if(row==1) { // space
            var td = document.createElement("td") ;
            td.appendChild(document.createTextNode(" ")) ;
            td.style.cssText = "background-color: white; color: blue" ;
            td.rowSpan = aData.rows.length - 2;
            tr.appendChild(td) ;
        }
        if(row==aData.rows.length-1) { // back
//            tr.appendChild(this.createForwardTd(aData, aGoFunction, aObject)) ;
            tr.appendChild(this.thePageDown) ;// this.createForwardTd(aData, aGoFunction, aObject)) ;
        }
        tbody.appendChild(tr) ;
    }

    // path
    var ul = document.createElement("ul") ;
    for(i=0; i<aData.path.length; i++) {
        var li = document.createElement("li") ;
        li.appendChild(document.createTextNode(" >> ")) ;
        var a = document.createElement("a") ;
        li.appendChild(a) ;

        a.appendChild(document.createTextNode(aData.path[i].name)) ;
        a.onclick = aGoFunction.bind(aObject, aData.path[i].id, aData.path[i].parentId, true, null) ;
        a.href='#' ;
        li.appendChild(document.createTextNode(" ")) ;
        ul.appendChild(li) ;
    }

    // search
    var link = document.createElement("a") ;
    link.href="#" ;
    link.onclick = this.search.bind(this, aData.firstId, aData.firstParentId, aGoFunction, aObject) ;
    link.appendChild(document.createTextNode("Найти")) ;



    var okLink = document.createElement("input") ;
    okLink.value = 'Выбрать' ;
    okLink.type = "button" ;
    okLink.onclick = aObject.selectCurrent ;

    var clearLink = document.createElement("input") ;
    clearLink.value = 'Сбросить' ;
    clearLink.type = "button" ;
    clearLink.onclick = aObject.clear ;

    this.theRootPane.innerHTML = "" ;
    this.theRootPane.appendChild(ul) ;
    this.theRootPane.appendChild(document.createElement("hr")) ;
    this.theRootPane.appendChild(link) ;
    this.theRootPane.appendChild(document.createElement("hr")) ;
    this.theRootPane.appendChild(table) ;
    this.theRootPane.appendChild(okLink) ;
    this.theRootPane.appendChild(clearLink) ;


//    this.theRootPane.appendChild(link) ;
//    this.theRootPane.appendChild(document.createTextNode(" ")) ;
//    this.theRootPane.appendChild(nextLink) ;
}

msh.widget.TreeTableDialog.prototype.search = function(aFirstId, aParentId, aGoFunction, aObject) {
    this.theLastSearch = prompt("Найти", this.theLastSearch) ;
    var args = new Array() ;
    args.push(aFirstId) ;
    args.push(aParentId) ;
    args.push(true) ;
    args.push(this.theLastSearch) ;
    aGoFunction.apply(aObject, args) ;
    //aObject.aGoFunction(aFirstId, aParentId, true, this.theLastSearch) ;
}

msh.widget.TreeTableDialog.prototype.createBackTd = function() {
    var link = document.createElement("td") ;
    link.appendChild(document.createTextNode("▲")) ;
    link.style.cssText = "background-color: white; color: blue" ;
    link.onclick = this.theControlObject.onPagePrevious ;
    return link ;
}

//msh.widget.TreeTableDialog.prototype.onPageDown = function() {
//    this.theObject.go(this.theData.lastId, this.theData.lastParentId, true, null) ;
//}

//msh.widget.TreeTableDialog.prototype.onPageUp = function() {
//    this.theObject.go(this.theData.firstId, this.theData.lastParentId, false, null) ;
//}


msh.widget.TreeTableDialog.prototype.createForwardTd = function() {
    var nextLink = document.createElement("td") ;
    nextLink.style.cssText = "background-color: white; color: blue" ;
    nextLink.appendChild(document.createTextNode("▼")) ;
    nextLink.onclick = this.theControlObject.onPageNext ;
    return nextLink ;
}

//msh.widget.TreeTableDialog.prototype.createForwardTd2 = function(aData, aGoFunction, aObject) {
//    var nextLink = document.createElement("td") ;
//    nextLink.style.cssText = "background-color: white; color: blue" ;
//    nextLink.appendChild(document.createTextNode("▼")) ;
//    nextLink.onclick = this.onPageUp.bind(this) ;
//    return nextLink ;
//}

msh.widget.TreeTableDialog.prototype.hide = function() {
    this.theDialog.hide() ;
}
